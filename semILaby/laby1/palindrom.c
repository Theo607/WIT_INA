=#include <stdio.h>
int main()
{
	int n;
	printf("Wczytaj liczbę: ");
	scanf("%d", &n);
	int pod;
	printf("Wczytaj podstawę liczby: ");
	scanf("%d", &pod);

	int czy = n;
	int palindrom = 0;
	while(n>0)
	{
		int q = n%pod;
		palindrom = palindrom*pod+q;
		n/=pod;
	}
	if(palindrom==czy)
	{
		printf("Ta liczba jest palindromem\n");
	}
	else
	{
		printf("Ta liczba nie jest palindromem\n");
	}
	return 0;
}
