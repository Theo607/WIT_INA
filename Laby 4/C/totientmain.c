#include "totientlibrary.h"
#include <stdio.h>
#include <string.h>
unsigned to_int(char word[])
{
	unsigned n=0;
	unsigned base=1;
	for(int i=strlen(word)-1;i>=0;i--)
	{
		unsigned val = word[i]-'0';
		val *= base;
		n += val;
		base *= 10;
	}
	return n;
}

int main(int argc, char *argv[])
{
	if(argc > 1){
	for (int i = 1; i<argc; i++)
	{
		printf("totient(");
		printf("%s",argv[i]);
		printf( ") = ");
		printf("%d",Totient(to_int(argv[i])));
		printf("\n");
	}
	}
	else
	{
		printf("Złe dane!");
	}
	return 0;
}
