def czyn(n):
    czy = 0
    i = 3
    if n % 2 == 0 :
        q = 0
        while n % 2 == 0 :
            n = n//2
            q = q+1
        print("2")
        if q > 1 :
            print("^")
            print(f"{q}")
        czy = 1
    while i*i<=n :
        if n % i == 0 :
            if czy == 1 :
                print("*")
            else :
                czy = 1
            k = 0
            while n % i == 0 :
                n = n//i
                k = k+1
            print(f"{i}")
            if k>1 :
                print("^")
                print(f"{k}")
        i = i+2
    if n != 1 :
        print("*")
        print(f"{n}")
def main():
    n = int(input("Podaj liczbę: "))
    czyn(n)
if __name__ == "__main__":
    main()
