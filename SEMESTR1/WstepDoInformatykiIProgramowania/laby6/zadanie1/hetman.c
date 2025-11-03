#include "perm.h"
#include <stdio.h>
#include <stdbool.h>
#include <string.h>
bool CheckDiagonals(int tab[], int i, int n)
{
	int x = tab[i]-1;
	int y = tab[i]+1;
	int j = i-1;
	int k = i+1;
	while(j>=0||k<n)
	{
	/*printf("%d ", x);
	printf("%d ", y);
	printf("%d ", j);
	printf("%d ", k);*/
		if(j>=0)
		{
			if(tab[j]==x||tab[j]==y)
			{
				//printf("Lewo \n");
				return false;
			}
			j--;
		}
		if(k<n)
		{
			if(tab[k]==x||tab[k]==y)
			{
				/*printf("\n");
				printf("%d ", tab[k]);
				printf("%d ", tab[j]);
				printf("%d ", tab[j-1]);
				printf("Prawo \n");*/
				return false;
			}
			k++;
		}
		x--;
		y++;
	}
	//printf("\n");
	return true;
}
bool CheckPermutation(int tab[], int n)
{
	for(int i=0;i<n;i++)
	{
		if(!CheckDiagonals(tab, i, n))
		{
			return false;
		}
	}
	return true;
}
void PrepareArray(int tab[], int n)
{
	for(int i=0;i<n;i++)
	{
		tab[i] = i+1;
	}
}
void WritePermutation(int tab[], int n)
{
	for(int i=0;i<n;i++)
	{
		printf("%d ", tab[i]);
	}
	printf("\n");
}
void FindSolutions(int tab[], int n)
{
	PrepareArray(tab, n);
//	int c = Factorial(n);
	int ans = 0;
	//for(int i=0;i<c;i++)
		if(CheckPermutation(tab, n))
		{
			ans++;
			WritePermutation(tab, n);
		}
	while(NextPermutation(tab,n))
	{
		if(CheckPermutation(tab, n))
		{
			ans++;
			WritePermutation(tab, n);
		}
		//NextPermutation(tab, n);
	}
	printf("Number of solutions: ");
	printf("%d\n", ans);
}
int to_int(char word[])
{
	int n=0;
	int base=1;
	for(int i=strlen(word)-1;i>=0;i--)
	{
		int val=word[i]-'0';
		val*=base;
		n+=val;
		base*=10;
	}
	//printf("%d\n", n);
	return n;
}
int main(int argc, char *argv[])
{
	//printf("%d\n", argc);
	if(argc != 2)
	{
		printf("Złe dane!\n");
		return 0;
	}
	if(to_int(argv[1])<1)
	{
		printf("Złe dane!\n");
		return 0;
	}
	int n=to_int(argv[1]);
	/*if(n<4)
	{
		printf("Nie istnieją rozwiązania dla tych danych");
		return 0;
	}*/
	int tab[n];
	FindSolutions(tab, n);
	/*int test[4] = {2,4,1,3};
	WritePermutation(test, 4);
	if(CheckPermutation(test, 4))
	{
		printf("Test poprawny \n");
	}
	else
	{
		printf("Test niepoprawny \n");
	}*/
	return 0;
}
