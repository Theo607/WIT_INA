#include <stdio.h>
#include <string.h>
#include <stdbool.h>
void PrintVariation(int v[], int l)
{
	for(int i=0;i<l;i++)
	{
		printf("%d ",v[i]);
	}
	printf("\n");
}
void AnswerGuess(int *a, int *b)
{
	printf("Ile zgodnych?: ");
	int n;
	scanf("%d", &n);
	printf("Ile na złym miejscu: ");
	int m;
	scanf("%d", &m);
	*a = n;
	*b = m;
}
bool Check(int tab[], int l, int a, int b)
{
	int count = 0;
	for(int i=0;i<l;i++)
	{
		if(tab[i]==a)
		{
			count++;
		}
	}
	if(count == b)
	{
		return true;
	}
	return false;
}
int End(int l, int base)
{
	int o = base-1;
	int end = o;
	int w=base;
	for(int i=1;i<l;i++)
	{
		end+=w*o;
		w*=base;
	}
	return end;
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
	//printf("%d", count);
	/*for(int i=0;i<end+1;i++)
	{
		for(int j=0;j<l;j++)
		{
			printf("%d ", arr[i][j]);
		}
		printf("\n");
	}*/
	//End of Preparation;
	int a,b;
	int id=0;
	while(!Ind[id])
	{
		id++;
	}
	for(int i=0;i<l;i++)
	{
		printf("%d ", arr[id][i]);
	}
	printf("\n");
	AnswerGuess(&a, &b);
	for(int i=0;i<end+1;i++)
	{
		Ind[i]=Check(arr[i],l,1,a);
	}
	id=0;
	while(!Ind[id])
	{
		id++;
	}
	/*for(int i=0;i<l;i++)
	{
		printf("%d ", arr[id][i]);
	}*/
		for(int i=0;i<end+1;i++)
	{
		if(Ind[i])
		{
			PrintVariation(arr[i],l);
		}
	}
}
int main()
{
	int l, base;
	scanf("%d", &l);
	scanf("%d", &base);
	MasterMind(l, base);
	return 0;
}
