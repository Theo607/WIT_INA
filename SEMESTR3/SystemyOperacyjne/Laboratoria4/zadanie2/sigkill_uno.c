#include <stdio.h>
#include <signal.h>
#include <unistd.h>
#include <errno.h>

int main() {
    pid_t init_pid = 1;
    int signum = SIGKILL; 

    printf("Probuję wysłać sygnał %d (SIGKILL) do procesu init (PID 1)...\n", signum);

    if (kill(init_pid, signum) == -1) {
        if (errno == EPERM) {
            printf("Sukces! Jądro poprawnie zablokowało wysłanie sygnału do PID 1.\n");
            printf("Błąd: EPERM (Operation not permitted).\n");
        } else {
            perror("Wystąpił inny błąd podczas wysyłania sygnału");
        }
        return 0;
    }

    printf("BŁĄD ZABEZPIECZEŃ: Sygnał prawdopodobnie dotarł do procesu init, co jest niepokojące.\n");
    return 1;
}
