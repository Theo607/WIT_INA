#include <stdio.h>
#include <math.h>

typedef double (*functype)(double);

double square1(double x){
  return (x - 1.0) * (x + 2.0);
}

double findZero(functype f, double a, double b, double eps){
  double s = (a+b)/2;
  while(b - a > eps){
    if(f(s)*f(a) > 0){
      a = s;
    }
    else {
      b = s;
    }
    s = (a+b)/2;
  }
  return s;
}

double cos2(double x){
  return cos(x/2);
}

int main() {
  double a;
  double b;
  double eps;
  printf("a: ");
  scanf("%lf", &a);
  printf("b: ");
  scanf("%lf", &b);
  printf("eps: ");
  scanf("%lf", &eps);
  double zero = findZero(cos2, a, b, eps);
  printf("\n%lf\n", zero);
//  printf("%f\n", findZero(sin, 1.0, 4.0, 0.0001));
//  printf("%f\n", sin(findZero(sin, 1.0, 4.0, 0.0001)));
}
