import sys
from primepackage import *

def main() :
    if len(sys.argv) < 2 :
        print("Złe dane!");
    elif sys.argv[1] == "pn" :
        print(PrimeNumbers(int(sys.argv[2])))
    elif sys.argv[1] == "pr" :
        print(Prime(int(sys.argv[2])))
    elif sys.argv[1] == "ip" :
        print(IsPrime(int(sys.argv[2])));
    else :
        print("Złe dane!");

if __name__ == "__main__" :
    main()
