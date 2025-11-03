#include <stdio.h>
#include <stdbool.h>
int Max(int tab[], int n)
{
	int ans = -1;
	for(int i=0;i<n;i++)
	{
		if(tab[i]>ans)
		{
			ans = tab[i];
		}
	}

	return ans;
}
void BackTrack(int coins[], int CoinIndex[], int n, int x, int ammount)
{
	int HowMany[n];
	for(int i=0;i<n;i++)
	{
		HowMany[i]=0;
	}
	printf("%d",x);
	printf(" ==> ");
	printf("%d\n", ammount);
	while(x>0)
	{
		HowMany[CoinIndex[x]]++;
		x-=coins[CoinIndex[x]];
	}
	for(int i=0;i<n;i++)
	{

		if(HowMany[i]!=0){
		printf("    %d",HowMany[i]);
		printf(" x ");
		printf("%d\n", coins[i]);
}
	}
	//printf("\n");
}
void Rest(int coins[], int spares[], int n, int m)
{
	int reach = Max(spares,m);
	//bool CanGive[reach+1];
	int Ammount[reach+1];
	int CoinIndex[reach+1];
	for(int i=0;i<reach+1;i++)
	{
		//CanGive[i]=false;
		CoinIndex[i]=-1;
		Ammount[i]=-1;
	}
	//CanGive[0]=true;
	Ammount[0]=0;
	for(int i=0;i<n;i++)
	{
		for(int j=0;j<reach+1;j++)
		{
			if(Ammount[j]!=-1)
			{
				if(j+coins[i]<=reach)
				{
					//CanGive[j+coins[i]]=true;
					if(Ammount[j+coins[i]] == -1 || Ammount[j+coins[i]]>=Ammount[j]+1)
					{
						Ammount[j+coins[i]]=Ammount[j]+1;
						CoinIndex[j+coins[i]]=i;
					}
				}
			}
		}
	}

	/*for(int i=0;i<reach+1;i++)
	{
		printf("%d ", i);
	}
	printf("\n");
	for(int i=0;i<reach+1;i++)
	{
		if(CanGive[i])
		{
			if(i<10){
			printf("1 ");
			}
			else
			{
				printf("1  ");
			}
		}
		else
		{
			if(i<10){
			printf("0 ");
			}
			else
			{
				printf("0  ");
			}
		}
	}
	printf("\n");
	for(int i=0;i<reach+1;i++)
	{
		printf("%d ", Ammount[i]);
	}
	printf("\n");*/

	for(int i=0;i<m;i++)
	{
		if(Ammount[spares[i]]!=-1)
		{
			BackTrack(coins,CoinIndex,n,spares[i],Ammount[spares[i]]);
		}
		else
		{
			printf("%d ", spares[i]);
			printf("==>");
			printf(" No solution!\n");
		}
	}

}
int main(int argc, char *argv[])
{

	FILE *InFile;
	InFile = fopen(argv[1], "r");
	int n,m;
	char e[100];
	fgets(e, 100, InFile);
	sscanf(e, "%d", &n);
	int coins[n];
	for(int i=0;i<n;i++)
	{
		fgets(e, 100, InFile);
		sscanf(e, "%d", &coins[i]);
	}
	m = argc-2;
	int spares[m];
	for(int i=0;i<m;i++)
	{
		sscanf(argv[i+2], "%d", &spares[i]);
	}
	Rest(coins,spares,n,m);
}
