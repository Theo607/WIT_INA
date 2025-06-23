import sys

ans = 0

def set(n, i, position, colRow, colDiag1, colDiag2):
    global ans
    for j in range(1, n+1):
        if not(colRow[j-1] or colDiag1[i+j-1] or colDiag2[i-j-1+n]):
            position[i-1] = j 
            colRow[j-1] = True
            colDiag1[i+j-1] = True
            colDiag2[i-j-1+n] = True 
            if i < n:
                set(n, i+1, position, colRow, colDiag1, colDiag2)
            else:
                ans = ans + 1
                print(position)
            position[i-1] = 0
            colRow[j-1] = False
            colDiag1[i+j-1] = False
            colDiag2[i-j-1+n] = False 

def hetman(n):
    position = [0] * n
    colRow = [False] * n
    colDiag1 = [False] * (2*n)
    colDiag2 = [False] * (2*n)
    set(n, 1, position, colRow, colDiag1, colDiag2)


def main():
    hetman(int(sys.argv[1]))
    print("#Solutions: ", ans);

if __name__ == "__main__":
    main()
