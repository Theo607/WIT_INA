#include <stdio.h>
#include <signal.h>
#include <unistd.h>
#include <stdlib.h>

void signal_handler(int signum) {
    printf("\n[PID %d] Przechwycono sygnał: %d\n", getpid(), signum);
}

int main() {
    struct sigaction sa;
    sa.sa_handler = signal_handler;
    sigemptyset(&sa.sa_mask);
    sa.sa_flags = 0;

    printf("[PID %d] Rozpoczynam test obsługi sygnałów...\n", getpid());

    for (int i = 1; i <= 31; i++) {
        if (sigaction(i, &sa, NULL) == 0) {
            printf("Ustawiono handler dla sygnału %d.\n", i);
        } else {
            perror("BŁĄD przy ustawianiu handlera");
            printf("NIE można ustawić handlera dla sygnału: %d (np. SIGKILL, SIGSTOP).\n", i);
        }
    }

    printf("\nCzekam na sygnały (np. wyslij kill -1 %d). \n", getpid());
    while (1) {
        pause(); 
    }

    return 0;
}
