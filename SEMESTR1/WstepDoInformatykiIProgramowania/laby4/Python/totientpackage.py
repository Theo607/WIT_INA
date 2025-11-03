def Totient(n):
    totient = 1
    i = 3
    if n % 2 == 0 :
        while n % 2 == 0 :
            n = n//2
            totient = totient*2;
        totient = totient//2
    while i*i<=n :
        if n % i == 0 :
            totient = totient * (i-1)
            while n % i == 0 :
                n = n//i
                totient = totient*i
            totient = totient//i
        i = i+2
    if n != 1 :
        totient = totient * (n-1)
    return totient
