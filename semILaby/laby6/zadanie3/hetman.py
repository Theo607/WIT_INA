import perm

def checkDiagonals(tab, i, n):
    x = tab[i] - 1
    y = tab[i] + 1
    j = i - 1
    k = i + 1
    while j>= 0 or k < n:
        if j >= 0:
            if tab[j] == x or tab[j] == y:
                return False
            j--
        if k < n:
            if tab[k] == x or tab[k] == y:
                return False
            k++
        x--
        y++
    return True


