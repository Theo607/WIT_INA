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

void MasterMind(int l, int base)
{
	int end = End(l, base);
	int n = 0;
	int first = 1;
	int val = base;
	int arr[end+1][l];
	for(int i=1;i<l;i++)
	{
		first+=val;
		val*=base;
	}
	int variation[l];
	for(int i=0;i<l;i++)
	{
		variation[i]=0;
	}
	int count = 0;
	while(n<=end)
	{
		int k = n;
		int q = 0;
		while(k>0)
		{
			variation[q] = k%base;
			k /= base;
			q++;
		}
		if(q==l || n < first)
		{
			//PrintVariation(variation, l);
			for(int i=l-1;i>=0;i--)
			{
				arr[count][i] = variation[l-i-1]+1;
			}
			count++;
		}
		n++;
	}
	bool Ind[end+1];
	for(int i=0;i<end+1;i++)
	{
		Ind[i] = true;
	}
}
int main() {

}
