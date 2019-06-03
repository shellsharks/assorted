#DES

S1 =[
    [14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7],
    [0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8],
    [4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0],
    [15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13]
    ]
S2 =[
    [15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10],
    [3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5],
    [0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15],
    [13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9],
    ]

X = "0100010101110101001110101101010101111011010111010111110010010100"
C = "0110110110101001001110100011111001111100001000101001010001100111"
L0 = X[:32]
R0 = X[-32:]
K0 = "0"*48
#print(K0)

L2 = X[:32]
R2 = X[-32:]

#we know...
#f(R0,K1) = R1 ^ L0

L1 = R0 #I KNOW THIS
R1 = L2 #L2 = R1
#R2 = L1 ^ f(R1,k2) #I can figure out k2


#R1 = L0 ^ f(R0,K1)

def xor(a,b):
	val = ""
	for i in range(0,64):
		val = val + str(int(a[i],2)^int(b[i],2))
	return val
	
def f(r,k):
	return expansion(r[:6])
	
	#ret = xor(expansion(r[:6]),k)
	#print(ret[:4],ret[-4:])
	#return sbox(ret[:4])+sbox(ret[-4:])
	
def sbox(input,s):
	row = int(input[0]+input[-1],2)
	column = int(input[1:-1],2)
	return s[row][column]

def expansion(r):
	output = ""
	output += r[0]
	output += r[1]
	output += r[3]
	output += r[2]
	output += r[3]
	output += r[2]
	output += r[4]
	output += r[5]
	return output

print(f(R0,K0))
z = "1001"
#print(z[:2])

print("L0","R0","=",L0,R0)
print("L1","R1","=",L1,R1)
print("L2","R2","=",L2,R2)

#print(sbox("101101",S1))
#print(expansion("100101"))
#print(xor(X,C))
#print(S1[0][2])
'''
print(L0,R0)
print(L2,R2)
print(len(X))
print(len(C))
'''