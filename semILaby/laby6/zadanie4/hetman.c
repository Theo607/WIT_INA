#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>

int ans = 0;

void set(int n,int i, int position[], bool colRow[], bool colDiag1[], bool colDiag2[]) {
  for(int j = 1; j <= n; j++){
    if(!(colRow[j-1] || colDiag1[i+j-1] || colDiag2[i-j-1+n])){
      position[i-1] = j;
      colRow[j-1] = true;
      colDiag1[i+j-1] = true;
      colDiag2[i-j-1+n] = true;
      if(i < n) {
        set(n, i+1, position, colRow, colDiag1, colDiag2);
      }
      else {
        ans++;
        for(int k = 1; k <= n; k++) {
          printf("%d ", position[k-1]);
        }
        printf("\n");
      }
      position[i-1] = 0;
      colRow[j-1] = false;
      colDiag1[i+j-1] = false;
      colDiag2[i-j-1+n] = false;
    }
  }
}

void hetman(int n){
  int position[n];
  bool colRow[n];
  bool colDiag1[2*n-1];
  bool colDiag2[2*n-1];

  for(int i = 0; i < n; i++) {
    position[i] = 0;
    colRow[i] = false;
  }
  
  for(int i = 0; i < 2*n-1; i++) {
    colDiag1[i] = false;
    colDiag2[i] = false;
  }
  
  set(n, 1, position, colRow, colDiag1, colDiag2);
}

int main(int argc, char* argv[]){
  hetman(atoi(argv[1]));
  printf("\n#solutions: %d", ans);
}
