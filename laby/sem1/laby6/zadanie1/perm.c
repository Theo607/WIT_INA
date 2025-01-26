#include <stdio.h>
#include <stdbool.h>
int FindIndex(int tab[], int x, int a, int b)
{
	int i=a;
	int ans=tab[a];
	int ind=i;
	while(i<=b)
	{
		if(tab[i]>x)
		{
			if(tab[i]<ans)
			{
				ans=tab[i];
				ind = i;
			}
		}
		i++;
	}
	return ind;
}
/*int Factorial(int n)
{
	int ans=1;
	for(int i=2;i<=n;i++)
	{
		ans*=i;
	}
	return ans;
}*/
void Swap(int tab[], int a, int b)
{
	int c;
	c = tab[a];
	tab[a] = tab[b];
	tab[b] = c;
}
void Rev(int tab[], int a, int b)
{
	while(a<b)
	{
		Swap(tab,a,b);
		a++;
		b--;
	}
}
bool NextPermutation(int tab[], int n)
{
	int l = n-1;
	while((tab[l]<tab[l-1])&&l>0)
	{
		l--;
	}
	if(l==0)
	{
		//printf("-1");
		return false;
	}
	int i = FindIndex(tab, tab[l-1], l, n-1);
	Swap(tab, l-1, i);
	Rev(tab, l, n-1);
	/*for(int i=0;i<n;i++)
	{
		printf("%d ", tab[i]);
	}*/
	return true;
}
