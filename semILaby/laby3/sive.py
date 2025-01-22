def primenumbers(n) :
    def create_sieve(s, n) :
        for i in range (n+1) :
            s.append(True)
        s[0] = False
        s[1] = False
    def compute_sieve(s) :
        i = 2
        while i*i<= len(s):
            if s[i] :
                j = i * i
                while j<len(s) :
                    s[j] = False
                    j = j + i
            i = i+1
    def count_primes(s) :
        c = 0
        for e in s :
            if e :
                c = c + 1
        return c
    s = []
    create_sieve(s, n)
    compute_sieve(s)
    return count_primes(s)
def main() :
    n = int(input("Wczytaj liczbę: "))
    if n < 2 :
        print("Złe dane!")
    else :
        print(primenumbers(n))

if __name__ == "__main__":
    main()
