def Pascal(n,k):
    def preparePascal(s, n):
        i = 0
        while i<n+1 :
            s.append(0)
            i = i+1
        s[0] = 1
    def ComputePascal(s, n):
        i = 1
        while i<=n :
            a = 1
            j = 1
            while j<= i :
                b = s[j]
                s[j] = s[j] + a
                a = b
                j = j + 1
            i = i + 1
    s = []
    preparePascal(s, n)
    ComputePascal(s, n)
    return s[k]
def main() :
    n = int(input("Wczytaj górną liczbę dwumianu: "))
    k = int(input("Wczytaj dolną liczbę dwumianu: "))
    print(Pascal(n,k))

if __name__ == "__main__":
    main()
