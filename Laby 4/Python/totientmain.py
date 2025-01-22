import sys
from totientpackage import *

def main() :
    if (len(sys.argv) > 1) :
        for i in range(len(sys.argv)-1):
            print("totient(",sys.argv[i+1],") = ",Totient(int(sys.argv[i+1])))
    else :
        print("Złe dane!")

if __name__=="__main__":
    main()
