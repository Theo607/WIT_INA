#include <stdio.h>
#include <stdbool.h>
unsigned int sieve(unsigned int n)
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
int main()
{
	unsigned int n;
	scanf("%d", &n);
	printf("%d",sieve(n));
	return 0;
}
