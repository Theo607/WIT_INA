#include <stdio.h>
#include <stdbool.h>

int digSum(int n){
  int ans = 0;
  while(n > 0){
    ans += n%10;
    n /= 10;
  }
  return ans;
}

int next(int n){
  return n + 2*digSum(n);
}

bool check(int n){
  int current = n;
  int next = 0;
  while(true){
    next = current + 2*digSum(current);
    if(current == 2025 || next == 2025)
      return true;
    current = next;
    if(next > 2025)
      return false;
  } 
}

bool checkPrint(int n){
  int current = n;
  int next = 0;
  printf("%d  ", current);
  while(true){
    next = current + 2*digSum(current);
    if(current == 2025 || next == 2025)
      return true;
    
    current = next;
    printf("%d  ", current);
    if(next > 2025)
      return false;
  }

}


int main(){
  int count = 0;
  for(int i = 1; i < 2025; i++){
    if(check(i)){
      count++;
      printf("%d\n", i);
    }
  }
  printf("%d", count);
  for(int i = 1; i < 2025; i++){
    if(check(i)){
      printf("\n");
      printf("\n");
      printf("\n");
      checkPrint(i);
    }
  }
}

