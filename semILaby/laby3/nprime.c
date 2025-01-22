#include <stdio.h>
unsigned nprime(unsigned  n)
{
	//printf("dvdv");
	unsigned tab[n];
	tab[0] = 2;
	if(n==1)
	{
		return 2;
	}
	int j=1;
	int i=3;
	int k=0;
	int czy=1;
	while(j<=n-1)
	{
		//printf("%d", j);
		k=0;
		czy=1;
		while(k<j&&k*k<=i)
		{
			if(i%tab[k]==0)
			{
				czy=0;
				k=j;
			}
			k++;
		}
		if(czy)
		{
			tab[j]=i;
			j++;
		}
		i=i+2;
	}
	return tab[n-1];
}
int main()
{
	unsigned n;
	printf("Wczytaj n: ");
	scanf("%d", &n);
	//printf("%d", n);
	int odp = nprime(n);
	printf("%d",odp);
	return 0;
}
