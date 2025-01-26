#include <stdio.h>
#include <math.h>
int main()
{
	float a,b,c;
	printf("Wczytaj wartości a,b,c ");
	scanf("%f", &a);
	scanf("%f", &b);
	scanf("%f", &c);
	if(a==0)
	{
		printf("To równanie jest liniowe z rozwiązaniem: %f", (-1.0)*c/b);
		return 0;
	}
	float delta;
	delta = b*b;
	float frag = 4*a*c;
	delta -=frag;
	float delta1 = sqrt(delta);
	if(delta < 0)
	{
		printf("Nie ma rozwiązań rzeczywistych");
	}
	if(delta == 0)
	{
		float odp;
		odp = -b/(2*a);
		printf("Jest jedno miejsce zerowe: %f", odp);
	}
	if(delta>0)
	{
		float odp1;
		odp1=-b-delta1;
		odp1=odp1/(2*a);
		float odp2;
		odp2=-b+delta1;
		odp2=odp2/(2*a);
		printf("Pierwsze miejsce zerowe to: %f\n", odp1);
		printf("A drugie to: %f\n", odp2);
	}
return 0;
}
