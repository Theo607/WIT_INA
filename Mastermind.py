def zgadnij(x):
	def zwieksz(s,c,r, n):
		i=0
		g=n
		while( g>0 and i<4):
			if(c[3-i] == False and s[3-i]>r):
				s[3-i]=s[3-i]+1
				g=g-1
			i=i+1
	def zmien(s, b, c, n,u,m):
		i=0
		j=0
		p=0
		f=3
		g=3;
		while(i<len(s)):
			if c[i]  == False or n == 4 :
				j=i+1;
				while(j<len(s)):
					if ((c[j] == False and s[j]>s[i] and s[j] != b[i]) or n == 4) :
						s[j], s[i] = s[i], s[j]
						f=i
						i=4
						g=j
						break
					j=j+1
			i=i+1
		if(n>1):
			if(n == 4):
				for z in range (4):
					if(z != g and z != f):
						q=p
						p=z
				s[p],s[q] = s[q],s[p]
			else:
				k=f+1;
				while(k<3):
					if(c[k] == False):
						l=k+1;
						while l < 4 :
							if( s[k]<s[l] and c[k] == False):
								s[k],s[l] = s[k],s[l]
								k=100
								l=100
							l=l+1
					k=k+1
	def lockin(s,b,c,n):
		g=n
		i=0
		while(g>0 and i<len(c)):
			if(c[i] == False and s[i] == b[i]):
				c[i] = True
				g=g-1
			i=i+1
	liczba=[]
	backup=[]
	poprz=[]
	locked=[]
	backup2=[]
	for i in range (4):
		liczba.append(0)
		backup.append(0)
		poprz.append(0)
		backup2.append(0)
		locked.append(0)
	for i in range(0,4):
		backup[i] = 0
		liczba[i] = 1
		poprz[i]=0
		locked[i]=False
	zgodne=0
	nietu=0
	r=0
	xn=0
	xg=0
	powtorki=0
	praw = True
	win = False
	while( praw ):
		print("proba nr ", r+1)
		print(" czy to twoja liczba ")
		for i in range(len(liczba)):
			print(liczba[i])
			poprz[i]=liczba[i]
		xn=nietu
		xg=zgodne
		zgodne = int(input("zgodne "))
		nietu = int(input("Nie na swej pozycji "))
		if(nietu == 4):
			for i in range (4):
				locked[i] = False;

		if(xn +xg > zgodne + nietu):
			print("oszust")
			praw = False
			continue
		if(zgodne == 4):
			print(" WYGRALEM  ")
			win = True
			praw = False
			continue
		if(zgodne + nietu > 4):
			praw = False
			print("oszust")
			continue
		zwieksz(liczba,locked,r,4-(nietu + zgodne))
		if((xg==zgodne and xn==nietu)) :
			lockin(liczba, backup,locked,zgodne)
		if(nietu>0) :
			zmien(liczba,backup,locked,nietu,backup2,nietu+zgodne)
		powtorki = 0
		for i in range(len(liczba)):
			if(liczba[i] > 9):
				print("oszust")
				praw = False
			if(liczba[i] == poprz[i]):
				powtorki=powtorki+1
			backup2[i] = backup[i]
			backup[i] = poprz[i]

		r=r+1
		if(powtorki == 4 and win == False):
			print("oszust")
			praw = False
def main():
	zgadnij(2)

if __name__=="__main__":
	main()
