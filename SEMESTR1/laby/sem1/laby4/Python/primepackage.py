def PrimeNumbers(n) :
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

def Prime(n):
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

def IsPrime(n) :
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
