def main():
    n=int(input("Wczytaj liczbę: "))
    pod=int(input("Wczytaj jej podstawę: "))
    czy = n
    palindrom = 0
    while n>0 :
        q = n%pod
        palindrom = palindrom*pod+q
        n=n//pod
    if palindrom==czy :
        print(f"Ta liczba jest palindromem")
    else :
        print(f"Ta liczba nie jest palindromem")
if __name__=="__main__":
    main()
