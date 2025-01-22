#include "Prime.h"
#include <stdio.h>
#include <string.h>
unsigned to_int(char word[])
{
	unsigned n=0;
	unsigned base=1;
	//printf("%ld",strlen(word));
	//printf(" ");
	for(int i=strlen(word)-1;i>=0;i--)
	{
		unsigned val = word[i]-'0';
		//printf ("%d",val);
		val *= base;
		n += val;
		base *= 10;
	}
	/*printf(" ");
	printf("%d",n);
	printf(" ");*/
	return n;
}
int main(int argc, char *argv[])
{
	if(argc < 2)
	{
		printf("Złe dane!");
		return 0;
	}
	else if(!strcmp(argv[1], "pn"))
	{
		printf("%d",prime_numbers(to_int(argv[2])));
		return 0;
	}
	else if(!strcmp(argv[1],"pr"))
	{
		printf("%d",prime(to_int(argv[2])));
		return 0;
	}
	else if(!strcmp(argv[1],"ip"))
	{
		bool ans = is_prime(to_int(argv[2]));
		if(ans)
		{
			printf("true");
		}
		else
		{
			printf("false");
		}
		return 0;
	}
	else
	{
		printf("Złe dane!");
		return 0;
	}
	return 0;
}
