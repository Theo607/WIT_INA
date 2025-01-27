def PrintVariat(v, l):
	for i in range(0,l):
		print(v[i])
def Odp():
	c=int(input("Ile zgodnych ? "))
	d=int(input("ile na zlym miejscu ? "))

	return c,d
def min(a,b):
	if(a<b):
		return a
	return b
def End( l, base):
	o = base-1
	end = o
	w=base
	for i in range(1,l):
		end+=w*o;
		w*=base;
	return end
def PermutationCheck(p,g,l,base,a,b):
	perm = []
	guess = []

	for i in range(0,base+1):
		perm.append(0)
		guess.append(0)
	pozycje = 0
	for i in range(0,l):
		if(p[i] >g[i] or p[i]< g[i]):
			perm[p[i]]=perm[p[i]]+1
			guess[g[i]]=guess[g[i]]+1
		else:
			pozycje=pozycje+1
	zgodne = 0
	for i in range(0,base+1):
		zgodne=zgodne+min(perm[i],guess[i])
	if(pozycje == a and zgodne == b):
		return True
	else:
		return False
def zgadnij(l, base):
	end = End(l,base)
	n=0
	first = 1
	val = base
	tab = [[0 for i in range(l)] for j in range(end+1)]
	for i in range(1,l):
		first = first+val
		val= val*base
	variat=[]
	for i in range(0,l):
		variat.append(0)
	count = 0
	while( n<=end):
		k = n
		q = 0
		while(k>0):
			variat[q] = k%base
			k = k//base
			q=q+1
		if(q==l or n<first):
			j=l-1
			while(j>-1):
				tab[count][j] = variat[l-j-1]+1
				j=j-1
			count=count+1
		n=n+1
	Ind=[]
	for i in range(0,end+1):
		Ind.append(True)
		Ind[i]=True
	id=0
	proby=0
	limit = base + (l//2)
	while(proby<limit):
		PrintVariat(tab[id],l)
		a,b = Odp()
		if(a<0  or b<0 or a+b>l):
			print("Niepoprawne Dane")
			continue
		if(a==l):
			print("Wyraglem")
			break
		for i in range(0,end+1):
			if(Ind[i]):
				Ind[i]=PermutationCheck(tab[i],tab[id],l,base,a,b)
		id=0
		while(id<=end and Ind[id] == False and id<=end):
			id=id+1
		if(id>end):
			print("Oszukujesz!!!!!!!  ")
			break
		proby=proby+1
	if(proby== limit):
		print("przegralem")


def main():
	l=int(input("podaj dlugosc kodu "))
	baza=int(input("podaj baze kodu "))
	zgadnij(l, baza)

if __name__=="__main__":
	main()
