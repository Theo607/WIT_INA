import sys

def findInd(tab, val, start, end):
    #zaczynamy szukac od a
    ite = start 
    tempVal = tab[start]
    ind = start #domyslnie a jest nasza odpowiedzia
    #szukamy az do indeksu b
    while ite <= end:
        if tab[ite] > val and tab[ite] < tempVal:
            tempVal = tab[ite]
            ind = ite 
        ite = ite + 1

    return ind

def swapArr(tab, ind1, ind2):
    temp = tab[ind1]
    tab[ind1] = tab[ind2]
    tab[ind2] = temp 

def reverse(tab, ind1, ind2):
    while ind1 < ind2:
        swapArr(tab, ind1, ind2)
        ind1 = ind1 + 1 
        ind2 = ind2 - 1 

def nextPerm(tab, n):
    l = n - 1
    while tab[l] < tab[l-1] and l > 0:
        l = l - 1
    if l == 0:
        return False
    i = findInd(tab, tab[l-1], l, n - 1)
    swapArr(tab, l-1, i)
    reverse(tab, l, n-1)
    return True

def checkDiag(tab, i, n):
    x = tab[i]-1
    y = tab[i]+1 
    j = i-1
    k = i+1 
    while j >= 0 or k < n:
        if j>= 0:
            if tab[j] == x or tab[j] == y:
                return False
            j = j-1
        if k < n:
            if tab[k] == x or tab[k] == y:
                return False
            k = k+1
        x = x-1
        y = y+1 
    return True 

def checkPerm(tab, n):
    for i in range(0, n):
        if not checkDiag(tab, i, n):
            return False
    return True 

def main():
    size = int(sys.argv[1])
    ans = 0
    tab = [0] * size 

    for i in range(0, size):
        tab[i] = i+1 
    while nextPerm(tab, size):
        if checkPerm(tab,size):
            ans = ans+1 
            print(tab)
    if size == 1:
        ans = 1 

    print("#Solutions: ", ans)

if __name__ == "__main__":
    main()
