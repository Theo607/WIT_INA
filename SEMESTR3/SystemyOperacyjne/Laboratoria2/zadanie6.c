#include <stdio.h>

int main() {
  for (int i = 0; i < 256; i++) {
    // terminal moze wyswietlic najwiecj 256 kolorow
    //  ze wzgledu na to jak kodowane sa znaki ascii
    printf("\033[38;5;%dmhello world\n", i);
  }
  return 0;
}
