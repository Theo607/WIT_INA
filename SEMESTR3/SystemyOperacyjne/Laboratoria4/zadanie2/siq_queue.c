#include <stdio.h>
#include <signal.h>
#include <unistd.h>
#include <pthread.h>
#include <stdatomic.h>

#define NUM_SIGNALS 10000
#define TEST_SIGNAL SIGUSR1

volatile sig_atomic_t signal_count = 0;

void sig_handler(int s) {
    signal_count++;
}

void* spammer(void* arg) {
    pid_t pid = getpid();
    for (int i = 0; i < NUM_SIGNALS; i++) {
        kill(pid, TEST_SIGNAL);
    }
    return NULL;
}

int main() {
    struct sigaction sa = {0};
    sa.sa_handler = sig_handler;
    sigaction(TEST_SIGNAL, &sa, NULL);

    pthread_t t;
    pthread_create(&t, NULL, spammer, NULL);

    pthread_join(t, NULL);

    usleep(50000);

    printf("Wysłano:   %d\n", NUM_SIGNALS);
    printf("Obsłużono: %d\n", signal_count);

    return 0;
}

