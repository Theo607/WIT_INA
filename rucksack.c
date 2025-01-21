#include <stdio.h>
#include <stdlib.h>
int maks(int m, int n) {
  if(n > m){
    return n;
  }
  return m;
}
int maks2(int n, int m, int k){
  int l = maks(n, m);
  return maks(l, k);
}
int main() {
  int n;
  int m;
  printf("Podaj n: ");
  scanf("%d", &n);
  printf("Podaj m: ");
  scanf("%d", &m);
  int tab[n][m];
  int temp[n][m];
  for(int i = 0; i < n; i++){
    for(int j = 0; j < m; j++) {
      tab[i][j] = rand()%10;
    }
  }
  temp[0][0] = tab[0][0];
  
  for(int i = 1; i < n; i++){
    temp[i][0] = temp[i-1][0] + tab[i][0];
  }
  for(int j = 1; j < m; j++) {
    temp[0][j] = temp[0][j-1] + tab[0][j];
  }
  
  for(int i = 1; i < n; i++) {
    for(int j = 1; j < m; j++) {

      temp[i][j] = maks2(temp[i-1][j-1], temp[i-1][j], temp[i][j-1]) + tab[i][j];
    }
  }
  for(int i = 0; i < n; i++){
     for(int j = 0; j < m; j++) {
      printf("%d ", tab[i][j]);
    }
    printf("\n");
  }
  printf("\n");
  for(int i = 0; i < n; i++){
     for(int j = 0; j < m; j++) {
      printf("%d ", temp[i][j]);
    }
    printf("\n");
  }
}
