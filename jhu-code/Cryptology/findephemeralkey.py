import sys
from squareandmultiply import squareAndMultiply

p = 31847
alpha = 5
beta = 25703
m1 = 8990
m2 = 31415
sig1 = (23972,31396)
sig2 = (23972,20481)
#a = ? #private key a

max = 99999

def calcInverse(a,m):
    for i in range(1,max):
        if (((a*i)-1) % m == 0):
            return i
            

k = ((m1 - m2) * calcInverse(sig1[1] - sig2[1],p-1)) % (p - 1)

print(k)

r = squareAndMultiply(alpha,k,p)

print(r)

a =

'''
s1 = (calcInverse(k,p-1) * (m1 - (a * sig1[0]))) % (p-1)
s2 = (calcInverse(k,p-1) * (m2 - (a * sig2[0]))) % (p-1)

print(s1,s2)
'''

