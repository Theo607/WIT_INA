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
int min(int a, int b) {
  if(a < b)
    return a;
  return b;
}
bool PermutationCheck(int p[], int g[], int l, int base, int a, int b)
{
	int perm[base+1];
	int guess[base+1];

	for(int i=0;i<base+1;i++)
	{
		perm[i]=0;
		guess[i]=0;
	}
	int positions = 0;
	for(int i=0;i<l;i++)
	{
		if(p[i]!=g[i])
		{
			perm[p[i]]++;
			guess[g[i]]++;
		}
		else
		{
			positions++;
		}
	}
	int correct = 0;

	for(int i=0;i<base+1;i++)
	{
		correct+=min(perm[i],guess[i]);
	}
	if(positions == a && correct == b)
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
	int attempts = 0;
	int limit = base+l/2;
	while(attempts<limit)
	{
		PrintVariation(arr[id],l);
		AnswerGuess(&a, &b);
		if((a<0||a>l)||((b<0||b>l))||a+b>l)
		{
			printf("Niepoprawne liczby\n");
			continue;
		}
		if(a == l)
		{
			printf("Wygrałem!\n");
			break;
		}
		for(int i=0;i<end+1;i++)
		{
			/*printf("%d ", i);
			printf("%d\n", id);*/
			if(Ind[i]){
			Ind[i]=PermutationCheck(arr[i],arr[id],l,base,a,b);
			}
			//printf("test\n");
		}
		id=0;
		while(!Ind[id]&&id<=end)
		{
			id++;
		}
		if(id>end)
		{
			printf("Skłamałeś!\n");
			break;
		}
		attempts++;
	}
		if(attempts==limit)
		{
			printf("Przegrałem");
		}
}
int to_int(char word[])
{
	int n=0;
	int base=1;
	for(int i=strlen(word)-1;i>=0;i--)
	{
		int val=word[i]-'0';
		val*=base;
		n+=val;
		base*=10;
	}
	return n;
}
int main(int argc, char*argv[])
{
	if(argc != 3)
	{
		printf("Złe dane");
		return 0;
	}
	int l, base;
	/*scanf("%d", &l);
	scanf("%d", &base);*/
	l = to_int(argv[1]);
	base = to_int(argv[2]);
	if(l<1||base<1)
	{
		printf("Złe dane!");
		return 0;
	}

	MasterMind(l, base);
	return 0;
}
