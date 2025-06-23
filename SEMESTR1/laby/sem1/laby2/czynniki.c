#include <stdio.h>
void czyn(int n)
{
	int czy=0;
	if(n%2==0)
	{
		int q=0;
		while(n%2==0)
		{
			n/=2;
			q++;
		}
		printf("2");
		if(q>1){
		printf("^");
		printf("%d",q);
		}
		czy = 1;
	}
	for(int i=3;i*i<=n;i+=2)
	{
		if(n%i==0)
		{
			if(czy)
			{
				printf("*");
			}
			else
			{
				czy=1;
			}
			int k=0;
			while(n%i==0)
			{
				n/=i;
				k++;
			}
		
		printf("%d",i);
		if(k>1){
			printf("^");
			printf("%d",k);
		}
		}
	}
	if(n!=1)
	{
		printf("*");
		printf("%d",n);
	}
}
int main()
{
	int n;
	printf("Podaj liczbę: ");
	scanf("%d", &n);
	czyn(n);
	return 0;
}
