M = 2647
W = 1036
ciphertext = [6368,7879,2050,1745,2714,3439,4007,4680,4246,2450,7501]
A = [3,5,11,20,41,83,165,329,662,1321]

'''
B = []
	
def calcB(a):
	for x in a:
		B.append((W*x)%M)

calcB(A)
print(B)
'''

def modInverse(a, m) : 
    a = a % m; 
    for x in range(1, m) : 
        if ((a * x) % m == 1) : 
            return x 
    return 1

def decrypt(c):
	b = (c * modInverse(W,M))%M
	i = 0
	binrep = ""
	
	for x in A[::-1]:
		if (x+i) <= b:
			i = i + x
			binrep = binrep + "1"
		else:
			binrep = binrep + "0"
	
	binrep=binrep[::-1]
	return int(binrep, base=2)
	
def digraph(num):
	return [chr(int((num-(num%26))/26)+65),chr(num%26+65)]

for x in ciphertext: print(digraph(decrypt(x)))

print(modInverse(W,M))