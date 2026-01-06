#include <arpa/inet.h>
#include <errno.h>
#include <fcntl.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>

#define MAX_CLIENTS 10
#define BUF_SIZE 4096
#define LOGIN_LEN 32

typedef struct {
  int fd;
  int id;
  int logged;
  char login[LOGIN_LEN];
  char buf[BUF_SIZE];
  int buf_len;
} client_t;

client_t clients[MAX_CLIENTS];
int next_id = 1;

void die(const char *msg) {
  perror(msg);
  exit(1);
}

int set_nonblocking(int fd) {
  int flags = fcntl(fd, F_GETFL, 0);
  return fcntl(fd, F_SETFL, flags | O_NONBLOCK);
}

void send_all(int fd, const char *msg) {
  size_t len = strlen(msg);
  while (len > 0) {
    ssize_t n = send(fd, msg, len, 0);
    if (n <= 0) {
      if (errno == EINTR)
        continue;
      break;
    }
    msg += n;
    len -= n;
  }
}

void remove_client(int i) {
  close(clients[i].fd);
  clients[i].fd = -1;
  clients[i].logged = 0;
  clients[i].buf_len = 0;
}

client_t *find_by_login(const char *login) {
  for (int i = 0; i < MAX_CLIENTS; i++)
    if (clients[i].fd != -1 && clients[i].logged &&
        strcmp(clients[i].login, login) == 0)
      return &clients[i];
  return NULL;
}

void send_user_list(client_t *c) {
  send_all(c->fd, "USERS:\n");
  char line[128];
  for (int i = 0; i < MAX_CLIENTS; i++) {
    if (clients[i].fd != -1 && clients[i].logged) {
      snprintf(line, sizeof(line), "%d %s\n", clients[i].id, clients[i].login);
      send_all(c->fd, line);
    }
  }
  send_all(c->fd, ".\n");
}

void handle_line(client_t *c, char *line) {
  if (!c->logged) {
    strncpy(c->login, line, LOGIN_LEN - 1);
    c->login[LOGIN_LEN - 1] = 0;
    c->logged = 1;
    c->id = next_id++;

    char msg[128];
    snprintf(msg, sizeof(msg), "OK ID=%d\n", c->id);
    send_all(c->fd, msg);
    send_user_list(c);
    return;
  }

  if (strncmp(line, "SEND ", 5) == 0) {
    char *p = line + 5;
    char *login = strtok(p, " ");
    char *text = strtok(NULL, "");

    if (!login || !text) {
      send_all(c->fd, "ERROR bad_command\n");
      return;
    }

    client_t *dst = find_by_login(login);
    if (!dst) {
      send_all(c->fd, "ERROR user_not_found\n");
      return;
    }

    char msg[BUF_SIZE];
    snprintf(msg, sizeof(msg), "MSG %s %s\n", c->login, text);
    send_all(dst->fd, msg);
    return;
  }

  if (strcmp(line, "QUIT") == 0) {
    send_all(c->fd, "BYE\n");
    c->fd = -1;
    return;
  }

  send_all(c->fd, "ERROR unknown_command\n");
}


int main(int argc, char **argv) {
  if (argc != 2) {
    fprintf(stderr, "Usage: %s <port>\n", argv[0]);
    exit(1);
  }

  int port = atoi(argv[1]);

  int listen_fd = socket(AF_INET, SOCK_STREAM, 0);
  if (listen_fd < 0)
    die("socket");

  int yes = 1;
  setsockopt(listen_fd, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(yes));

  struct sockaddr_in addr = {.sin_family = AF_INET,
                             .sin_addr.s_addr = INADDR_ANY,
                             .sin_port = htons(port)};

  if (bind(listen_fd, (struct sockaddr *)&addr, sizeof(addr)) < 0)
    die("bind");

  if (listen(listen_fd, 5) < 0)
    die("listen");

  set_nonblocking(listen_fd);

  for (int i = 0; i < MAX_CLIENTS; i++)
    clients[i].fd = -1;

  printf("Server listening on port %d\n", port);

  while (1) {
    fd_set rfds;
    FD_ZERO(&rfds);
    FD_SET(listen_fd, &rfds);

    int maxfd = listen_fd;

    for (int i = 0; i < MAX_CLIENTS; i++) {
      if (clients[i].fd != -1) {
        FD_SET(clients[i].fd, &rfds);
        if (clients[i].fd > maxfd)
          maxfd = clients[i].fd;
      }
    }

    struct timeval tv = {5, 0}; 
    int ready = select(maxfd + 1, &rfds, NULL, NULL, &tv);
    if (ready < 0) {
      if (errno == EINTR)
        continue;
      die("select");
    }

    if (FD_ISSET(listen_fd, &rfds)) {
      int fd = accept(listen_fd, NULL, NULL);
      if (fd >= 0) {
        set_nonblocking(fd);
        for (int i = 0; i < MAX_CLIENTS; i++) {
          if (clients[i].fd == -1) {
            clients[i].fd = fd;
            clients[i].buf_len = 0;
            clients[i].logged = 0;
            break;
          }
        }
      }
    }

    for (int i = 0; i < MAX_CLIENTS; i++) {
      client_t *c = &clients[i];
      if (c->fd == -1)
        continue;

      if (FD_ISSET(c->fd, &rfds)) {
        ssize_t n =
            recv(c->fd, c->buf + c->buf_len, BUF_SIZE - c->buf_len - 1, 0);
        if (n <= 0) {
          remove_client(i);
          continue;
        }

        c->buf_len += n;
        c->buf[c->buf_len] = 0;

        char *line;
        while ((line = strchr(c->buf, '\n'))) {
          *line = 0;
          handle_line(c, c->buf);

          int rest = c->buf_len - (line - c->buf + 1);
          memmove(c->buf, line + 1, rest);
          c->buf_len = rest;

          if (c->fd == -1) {
            remove_client(i);
            break;
          }
        }
      }
    }
  }
}
