#include <stdio.h>
#include <stdbool.h>

unsigned prime_numbers(unsigned n)
{
	if (n==0)
	{
		return 0;
	}
	bool sit[n+1];
	for(int i=2;i<=n;i++)
	{
		sit[i]=true;
	}
	for(int i=2;i*i<=n;i++)
	{
		if(sit[i])
		{
			for(int j=i*i;j<=n;j+=i)
			{
				sit[j]=false;
			}
		}
	}
	int odp = 0;
	for(int i=2;i<=n;i++)
	{
		if(sit[i])
		{
			odp++;
		}
	}
	return odp;
}

unsigned prime(unsigned n)
{
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
bool is_prime(unsigned n)
{
	if(n==2)
	{
		return true;
	}
	if(n%2==0)
	{
		return false;
	}
	for(int i=3;i*i<=n;i+=2)
	{
		if(n%i==0)
		{
			return false;
		}
	}
	return true;
}
