#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <errno.h>
#include <ctype.h>

#define MAX_LINE 1024
#define MAX_ARGS 64
#define MAX_PIPES 10
#define DELIM " \t\r\n\a"

void (*original_sigint_handler)(int); 

typedef struct {
    char *argv[MAX_ARGS];
    char *input_file;
    char *output_file;
    char *error_file;
    int output_append; // 0: >, 1: >>
    int error_append;  // 0: 2>, 1: 2>>
} CommandSegment;

void handle_sigchld(int sig) {
    while (waitpid(-1, NULL, WNOHANG) > 0);
}

void handle_sigint_shell(int sig) {
    printf("\nlsh> ");
    fflush(stdout);
}

void parse_redirections(char *segment, CommandSegment *cmd) {
    char *token;
    int i = 0;
    
    memset(cmd, 0, sizeof(CommandSegment));

    token = strtok(segment, DELIM);
    while (token != NULL) {
        if (strcmp(token, "<") == 0) {
            token = strtok(NULL, DELIM);
            if (token) cmd->input_file = strdup(token);
        } else if (strcmp(token, ">") == 0) {
            token = strtok(NULL, DELIM);
            if (token) cmd->output_file = strdup(token);
            cmd->output_append = 0;
        } else if (strcmp(token, ">>") == 0) {
            token = strtok(NULL, DELIM);
            if (token) cmd->output_file = strdup(token);
            cmd->output_append = 1;
        } else if (strcmp(token, "2>") == 0) {
            token = strtok(NULL, DELIM);
            if (token) cmd->error_file = strdup(token);
            cmd->error_append = 0;
        } else if (strcmp(token, "2>>") == 0) {
            token = strtok(NULL, DELIM);
            if (token) cmd->error_file = strdup(token);
            cmd->error_append = 1;
        } else {
            cmd->argv[i++] = strdup(token);
        }
        token = strtok(NULL, DELIM);
    }
    cmd->argv[i] = NULL;
}

char **parse_pipeline(char *line, int *background) {
    static char *segments[MAX_PIPES];
    char *pipeline_token;
    int i = 0;

    *background = 0;

    size_t len = strlen(line);
    if (len > 0 && line[len-1] == '\n') line[len-1] = '\0';

    char *amp = strrchr(line, '&');
    if (amp) {
        *background = 1;
        *amp = '\0';
    }

    len = strlen(line);
    while (len > 0 && isspace(line[len-1])) {
        line[len-1] = '\0';
        len--;
    }

    i = 0;
    pipeline_token = strtok(line, "|");
    while (pipeline_token != NULL && i < MAX_PIPES) {
        while (*pipeline_token && isspace(*pipeline_token)) pipeline_token++;
        char *end = pipeline_token + strlen(pipeline_token) - 1;
        while (end > pipeline_token && isspace(*end)) {
            *end = '\0';
            end--;
        }
        segments[i++] = pipeline_token;
        pipeline_token = strtok(NULL, "|");
    }
    segments[i] = NULL;
    return segments;
}

int execute_builtin(char **argv) {
    if (argv[0] == NULL) return 1;

    if (strcmp(argv[0], "exit") == 0) {
        printf("Opuszczam lsh.\n");
        exit(0);
    }

    if (strcmp(argv[0], "cd") == 0) {
        char *path = argv[1];
        if (path == NULL) path = getenv("HOME");
        if (path == NULL) {
             fprintf(stderr, "lsh: cd: brak zmiennej $HOME\n");
             return 1;
        }
        if (chdir(path) != 0) {
            perror("lsh: cd");
        }
        return 1;
    }

    return 0; 
}

void execute_pipeline(char **pipe_segments, int background) {
    int i = 0;
    int status;
    int num_segments = 0;
    pid_t pids[MAX_PIPES];

    while (pipe_segments[num_segments] != NULL) num_segments++;
    if (num_segments == 0) return;

    int pipefds[num_segments - 1][2];

    for (i = 0; i < num_segments; i++) {
        CommandSegment cmd;
        parse_redirections(pipe_segments[i], &cmd);
        
        if (i == 0 && num_segments == 1 && execute_builtin(cmd.argv)) {
             return;
        }

        if (cmd.argv[0] == NULL) {
            fprintf(stderr, "lsh: Pusta komenda w potoku.\n");
            return;
        }
        
        if (i < num_segments - 1) {
            if (pipe(pipefds[i]) == -1) {
                perror("lsh: pipe error");
                return;
            }
        }

        pids[i] = fork();

        if (pids[i] == 0) {
            signal(SIGINT, original_sigint_handler);

            if (cmd.input_file != NULL) {
                int fd_in = open(cmd.input_file, O_RDONLY);
                if (fd_in == -1) {
                    perror("lsh: open input error");
                    exit(EXIT_FAILURE);
                }
                dup2(fd_in, STDIN_FILENO);
                close(fd_in);
            } else if (i > 0) {
                dup2(pipefds[i-1][0], STDIN_FILENO);
            }

            if (cmd.output_file != NULL) {
                int flags = O_WRONLY | O_CREAT;
                flags |= (cmd.output_append ? O_APPEND : O_TRUNC);
                int fd_out = open(cmd.output_file, flags, 0644);
                if (fd_out == -1) {
                    perror("lsh: open output error");
                    exit(EXIT_FAILURE);
                }
                dup2(fd_out, STDOUT_FILENO);
                close(fd_out);
            } else if (i < num_segments - 1) {
                dup2(pipefds[i][1], STDOUT_FILENO);
            }

            if (cmd.error_file != NULL) {
                int flags = O_WRONLY | O_CREAT;
                flags |= (cmd.error_append ? O_APPEND : O_TRUNC);
                int fd_err = open(cmd.error_file, flags, 0644);
                if (fd_err == -1) {
                    perror("lsh: open error output error");
                    exit(EXIT_FAILURE);
                }
                dup2(fd_err, STDERR_FILENO);
                close(fd_err);
            }
            
            for (int j = 0; j < num_segments - 1; j++) {
                close(pipefds[j][0]);
                close(pipefds[j][1]);
            }

            if (execvp(cmd.argv[0], cmd.argv) == -1) {
                fprintf(stderr, "lsh: command not found: %s\n", cmd.argv[0]);
            }
            exit(EXIT_FAILURE);

        } else if (pids[i] < 0) {
            perror("lsh: fork error");
            return;
        }

        if (i < num_segments - 1) close(pipefds[i][1]);
        if (i > 0) close(pipefds[i-1][0]);
    }

    if (!background) {
        for (i = 0; i < num_segments; i++) {
             waitpid(pids[i], &status, 0); 
        }
    } else {
        printf("[lsh] Proces w tle (potok) PID: %d ... %d\n", pids[0], pids[num_segments-1]);
    }
}

int main(int argc, char **argv) {
    char *line;
    char **pipe_segments;
    int background;

    struct sigaction sa_chld;
    sa_chld.sa_handler = handle_sigchld;
    sigemptyset(&sa_chld.sa_mask);
    sa_chld.sa_flags = SA_RESTART | SA_NOCLDSTOP;
    if (sigaction(SIGCHLD, &sa_chld, NULL) == -1) {
        perror("sigaction SIGCHLD");
        exit(EXIT_FAILURE);
    }

    original_sigint_handler = signal(SIGINT, SIG_DFL);
    signal(SIGINT, handle_sigint_shell);

    while (1) {
        printf("lsh> ");
        line = NULL;
        size_t len = 0;

        if (getline(&line, &len, stdin) == -1) {
            if (feof(stdin)) {
                printf("\n");
                free(line);
                break;
            } else {
                perror("lsh: getline error");
                continue;
            }
        }
        
        pipe_segments = parse_pipeline(line, &background);

        if (pipe_segments[0] != NULL) {
            execute_pipeline(pipe_segments, background);
        }

        free(line);
    }

    return 0;
}

