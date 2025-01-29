import sys

def Max(t, n):
    ans = -1
    for i in range (0,n):
        if(t[i]>ans):
            ans = t[i]
    return ans

def BackTrack(coins, CoinIndex, n, x, ammount) :
    HowMany = []
    for i in range (0,n):
        HowMany.append(0)
    print(x, " ==> ", ammount)
    while (x>0):
        HowMany[CoinIndex[x]]=HowMany[CoinIndex[x]]+1
        x -= coins[CoinIndex[x]]
    for i in range (0,n):
        if (HowMany[i]!=0):
            print("    ", HowMany[i], " x ", coins[i])

def Rest(coins, spares, n, m):
    reach = Max(spares,m)
    Ammount = []
    CoinIndex = []
    for i in range (0,reach+1):
        CoinIndex.append(-1)
        Ammount.append(-1)
    Ammount[0]=0
    for i in range (0,n):
        for j in range (0,reach+1):
            if (Ammount[j]!=-1):
                if(j+coins[i]<=reach):
                    if(Ammount[j+coins[i]] == -1 or Ammount[j+coins[i]]>=Ammount[j]+1):
                        Ammount[j+coins[i]]=Ammount[j]+1
                        CoinIndex[j+coins[i]]=i
    for i in range (0,m):
        if (Ammount[spares[i]]!=-1):
            BackTrack(coins,CoinIndex,n,spares[i],Ammount[spares[i]])
        else:
            print(spares[i], " ==> ", "No solution!")

def main():
    in_file = open(sys.argv[1], "r")
    n = in_file.readline()
   # print(n)
    k = int(n)
   # print(k)
    coins = []
    spares = []
   # print(len(sys.argv))
    for i in range (0,k):
        s = in_file.readline()
        coins.append(int(s))
    m = len(sys.argv)-2
    for i in range (0,m):
       # print(i)
        spares.append(int(sys.argv[i+2]))
    Rest(coins, spares, k, m)

if __name__=="__main__":
    main()
