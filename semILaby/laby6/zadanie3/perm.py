def findIndex(tab, x, a, b):
    i = a 
    ans = tab[a]
    ind = i 
    while i <= b:
        if tab[i] > x:
            if tab[i] < ans:
                ans = tab[i]
                ind = i 
        i++
    return ind 

'''
def factorial(n):
    ans = 1
    for i in (2 .. n + 1):
        ans *= i
    return ans
'''

def swap(tab, a, b):
    c = tab[a]
    tab[a] = tab[b]
    tab[b] = c 

def rev(tab, a, b):
    while a < b:
        swap(tab, a, b)
        a++
        b--

def nextPermutation(tab, n):
    l = n - 1
    while tab[l] < tab[l-1] and l > 0:
        #print("-1")
        l--
    if l == 0:
        return False

    i = findIndex(tab, tab[l - 1], l, n - 1)
    swap(tab, l-1, i)
    rev(tab, l, n-1)
'''
   for i in (0, n):
       print(tab[i])
'''
    return True



