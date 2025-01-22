#include <stdio.h>
#include <stdbool.h>
int wczytaj()
{
	int n;
	printf("Podaj liczbę ");
	scanf("%d", &n);
	if(n<2)
	{
		printf("Złe dane!");
		return 0;
	}
	return n;
}
int main()
{
	int n=wczytaj();
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
			//printf("%d", i);
		}
		else
		{
			//printf("0");
		}
	}
	printf("%d", odp);
	return 0;
}
