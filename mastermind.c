#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>

int correctNums(int guessArr[], int numArr[]) {
  int tmp = 0;
  for(int i = 0; i < 4; i++) {
    if(guessArr[i] == numArr[i])
      tmp = tmp + 1;
  }
  return tmp;
}

int min(int a, int b) {
  if(a < b)
    return a;
  return b;
}

int wrongNums(int guessArr[], int numArr[]) {
  int hist1[6];
  int hist2[6];
  
  for(int i = 0; i < 6; i++) {
    hist1[i] = 0;
    hist2[i] = 0;
  }

  for(int i = 0; i < 4; i++) {
    for(int j = 0;j < 6; j++) {
      if(guessArr[i] == j + 1)
        hist1[j] = hist1[j] + 1;
      if(numArr[i] == j + 1)
        hist2[j] = hist2[j] + 1;
    }
  }

  int tmp = 0;

  for(int i = 0; i < 6; i++) {
    tmp = tmp + min(hist1[i], hist2[i])
  }
  tmp = tmp - correctNums(guessArr, numArr);
  return temp;
}

int main() {

}
