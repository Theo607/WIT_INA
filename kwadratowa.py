import math
def main():
    a = float(input("Podaj współczynnik a: "))
    b = float(input("Podaj współczynnik b: "))
    c = float(input("Podaj współczynnik c: "))

    if a == 0 :
        lin = -c/b
        print(f"To równanie jest liniowe z rozwiązaniem: {lin}")
    else :
        delta = b*b
        delta = delta - (4*a*c)
        if delta >= 0 :
            delta1 = math.sqrt(delta)
        if delta < 0 :
            print("Nie istnieją rozwiązania rzeczywiste tej funkcji")
        if delta == 0 :
            odp = -b/(2*a)
            print(f"Jest jedno rozwiązanie tej funkcji i wynosi: {odp}")
        if delta > 0 :
            odp1 = -b-delta1
            odp1 = odp1/(2*a)
            odp2 = -b+delta1
            odp2 = odp2/(2*a)
            print(f"Pierwszym rozwiązaniem tej funkcji jest: {odp1}")
            print(f"A drugim: {odp2}")

if __name__ == "__main__":
    main()
