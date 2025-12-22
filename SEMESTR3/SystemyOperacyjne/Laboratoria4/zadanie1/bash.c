#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main() {
    printf("Proba uruchomienia powloki Bash z uprawnieniami roota...\n");
    if (setuid(0) != 0) {
        perror("Nie mozna ustawic UID na root (0). Sprawdz uprawnienia pliku.");
        return 1;
    }
    execl("/bin/bash", "bash", "-i", NULL);
    perror("Blad podczas uruchamiania execl");
    return 1;
}
