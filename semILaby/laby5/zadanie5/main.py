from math import *

def cos2(x):
    return cos(x/2);

def findZero(f, a, b, eps):
    s = (a+b)/2 
    while b - a > eps:
        if f(s)*f(a) > 0:
            a = s 
        else:
            b = s 
        s = (a+b)/2 
    return s 

def main():
    a = float(input("a: "))
    b = float(input("b: "))
    eps = float(input("eps: "))
    zero = findZero(cos2, a, b, eps)
    print(zero)

if __name__ == "__main__":
    main()

