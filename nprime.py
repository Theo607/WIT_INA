def nprime(n):
    def preparePrimes(p, n):
        i = 0
        while i<n :
            p.append(0)
            i = i + 1
        p[0] = 2
    def ComputePrimes(p, n):
        j = 1
        i = 3
        k = 0
        czy = True
        while j<=n-1 :
            k = 0
            czy = True
            while k<j and k*k<=i :
                if i%p[k] == 0 :
                    czy = False
                    k = j
                k = k + 1
            if czy :
                p[j] = i
                j = j + 1
            i = i + 2

    p = []
    preparePrimes(p, n)
    ComputePrimes(p, n)
    return p[n-1]
def main():
    n = int(input("Wczytaj liczbę n: "))
    print(nprime(n))

if __name__=="__main__":
    main()
