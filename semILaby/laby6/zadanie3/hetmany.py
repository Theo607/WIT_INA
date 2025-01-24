def findInd(tab, val, start, end):
    #zaczynamy szukac od a
    ite = a 
    tempVal = tab[a]
    ind = a #domyslnie a jest nasza odpowiedzia
    #szukamy az do indeksu b
    while i <= b:
        if tab[i] > val and tab[i] < tempVal:
            tempVal = tab[i]
            ind = i 
        i = i + 1

    return ind

def SwapArr(tab, ind1, ind2):
    c = tab[a]
    tab[a] = tab[b]
    tab[b] = c 

def nextPerm(tab):
    l = n - 1
    while tab[l] < tab[l-1] and l > 0:
        l--
    if l == 0:
        return False
    i = findInd(tab, tab[l-1], l, n - 1)
    
