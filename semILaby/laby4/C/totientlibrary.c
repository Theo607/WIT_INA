#include <stdio.h>

unsigned Totient(unsigned n)
{
	unsigned totient=1;
	if(n%2==0)
	{
		while(n%2==0)
		{
			n/=2;
			totient*=2;
		}
		totient/=2;
	}
	for(int i=3;i*i<=n;i+=2)
	{
		if(n%i==0)
		{
			totient *= i-1;
			while(n%i==0)
			{
				n/=i;
				totient*=i;
			}
			totient/=i;
		}
	}
	if(n!=1)
	{
		totient*=n-1;
	}
	return totient;
}
