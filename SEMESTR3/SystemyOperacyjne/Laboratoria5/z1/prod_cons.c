#include <pthread.h>
#include <semaphore.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int *buffer;
int N;
int M;
int head = 0;
int tail = 0;
int produced_count = 0;
int consumed_count = 0;
int done = 0;
int producers_done = 0;

pthread_mutex_t mutex;
sem_t empty;
sem_t full;

void put_item(int item) {
  buffer[head] = item;
  head = (head + 1) % N;
}

int get_item() {
  int item = buffer[tail];
  tail = (tail + 1) % N;
  return item;
}

void *producer(void *arg) {
  while (1) {
    pthread_mutex_lock(&mutex);
    if (produced_count >= M) {
      pthread_mutex_unlock(&mutex);
      break;
    }
    int item = produced_count;
    produced_count++;
    pthread_mutex_unlock(&mutex);

    sem_wait(&empty);

    pthread_mutex_lock(&mutex);
    put_item(item);
    printf("Producer put: %d\n", item);
    pthread_mutex_unlock(&mutex);

    sem_post(&full);
    usleep(100000);
  }

  return NULL;
}

void *consumer(void *arg) {
  while (1) {
    sem_wait(&full);

    pthread_mutex_lock(&mutex);

    if (consumed_count >= M && producers_done) {
      pthread_mutex_unlock(&mutex);
      break;
    }

    int item = get_item();
    consumed_count++;
    printf("Consument got: %d\n", item);

    pthread_mutex_unlock(&mutex);
    sem_post(&empty);
  }
  return NULL;
}

int main(int argc, char *argv[]) {
  if (argc != 3) {
    printf("Usage: %s <M> <N>\n", argv[0]);
    return 1;
  }

  M = atoi(argv[1]);
  N = atoi(argv[2]);

  buffer = malloc(N * sizeof(int));
  if (!buffer) {
    perror("malloc");
    return 1;
  }

  pthread_mutex_init(&mutex, NULL);
  sem_init(&empty, 0, N);
  sem_init(&full, 0, 0);

  pthread_t prod[2], cons[2];

  for (int i = 0; i < 2; i++) {
    pthread_create(&prod[i], NULL, producer, NULL);
  }

  for (int i = 0; i < 2; i++) {
    pthread_create(&cons[i], NULL, consumer, NULL);
  }

  for (int i = 0; i < 2; i++) {
    pthread_join(prod[i], NULL);
  }
  pthread_mutex_lock(&mutex);
  producers_done = 1;
  pthread_mutex_unlock(&mutex);

  for (int i = 0; i < 2; i++)
    sem_post(&full);

  for (int i = 0; i < 2; i++) {
    pthread_join(cons[i], NULL);
  }

  free(buffer);
  pthread_mutex_destroy(&mutex);
  sem_destroy(&empty);
  sem_destroy(&full);

  printf("All produced and consumed.\n");
  return 0;
}
