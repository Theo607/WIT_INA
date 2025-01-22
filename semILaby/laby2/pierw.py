def czy_pierw(n) :
    if n == 2 :
        return True
    if n % 2 == 0 :
        return False
    i = 3
    while i*i<=n :
        if n % i == 0 :
            return False
        i = i + 2
    return True

def main():
    n = int(input("Podaj liczbę: "))
    if czy_pierw(n):
        print("Ta liczba jest pierwsza")
    else :
        print("Ta liczba nie jest pierwsza")

if __name__=="__main__":
    main()
