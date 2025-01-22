#include <stdio.h>
int pascal(int n, int k)
{
	int tab[n+1];
	for(int i=0;i<n+1;i++)
	{
		tab[i]=0;
	}
	tab[0]=1;
	for(int i=1;i<=n;i++)
	{
		int a=1;
		for(int j=1;j<=i;j++)
		{
			int b=tab[j];
			tab[j]=tab[j]+a;
			a=b;
		}
	}
	return tab[k];
}
int main()
{
	printf("Podaj liczby dwumianu: ");
	int n,k;
	scanf("%d",&n);
	scanf("%d",&k);
	printf("%d",pascal(n,k));
	return 0;
}
