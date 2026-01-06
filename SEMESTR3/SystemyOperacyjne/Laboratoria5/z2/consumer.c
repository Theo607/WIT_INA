#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <unistd.h>
#include <semaphore.h>

#define SHM_NAME "/my_shm_example"
#define SEM_EMPTY "/sem_empty"
#define SEM_FULL "/sem_full"
#define BUFFER_SIZE 10

typedef struct {
    int buffer[BUFFER_SIZE];
    int in;
    int out;
} shared_data;

int main() {
    
    int shm_fd = shm_open(SHM_NAME, O_RDWR, 0666);
    if (shm_fd == -1) { perror("shm_open"); exit(1); }

    shared_data *data = mmap(NULL, sizeof(shared_data),
                              PROT_READ | PROT_WRITE, MAP_SHARED, shm_fd, 0);
    if (data == MAP_FAILED) { perror("mmap"); exit(1); }

 
    sem_t *empty = sem_open(SEM_EMPTY, 0);
    sem_t *full  = sem_open(SEM_FULL, 0);
    if (empty == SEM_FAILED || full == SEM_FAILED) { perror("sem_open"); exit(1); }

    
    for (int i = 1; i <= 50; i++) {
        sem_wait(full); 

        int value = data->buffer[data->out];
        printf("Consumed: %d\n", value);
        data->out = (data->out + 1) % BUFFER_SIZE;

        sem_post(empty);
        usleep(150000);
    }

    
    munmap(data, sizeof(shared_data));
    close(shm_fd);

    
    shm_unlink(SHM_NAME);
    sem_unlink(SEM_EMPTY);
    sem_unlink(SEM_FULL);

    return 0;
}

