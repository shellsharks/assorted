#python rsadecrypt6-13.py [modulus "n"] [public key exponent "e"] [encrypted value]
#problem set 4, question 2

import sys

#value to decrypt
encrypted = int(sys.argv[3])

#public parameters
n = int(sys.argv[1]) #modulus for public/private keys
e = int(sys.argv[2]) #public key exponent
b = e #e is referred to as "b" in the text

#private parameters
pq = [] #two distinct prime numbers, initialized to empty list
phin = 0 #carmichael's totient function phi(n)=(p-1)(q-1), initialized to zero
d = 0 #private key exponent, initialized to zero

print("n = " + str(n))
print("e OR b = " + str(e))

def factorn(n):
    for i in range(2,n/2):
        if(n%i == 0):
            pq.append(i)
            factorn(n/i)

factorn(n)
print("p and q = " + str(pq[0]) + "," + str(pq[1]))

def phi(n):
    return (n[0]-1) * (n[1]-1)

phin = phi(pq)
print("phi(n) = " + str(phin))

max = 99999 #max number to iterate up to

def calcInverse(e,phin):
    for d in range(1,max):
        if (((e*d)-1) % phin == 0):
            return d

d = calcInverse(e,phin)
print("d = " + str(d))

#m = encrypted^d mod n
def squareAndMultiply(base,exponent,modulus):
    #Converting the exponent to its binary form
    binaryExponent = []
    while exponent != 0:
        binaryExponent.append(exponent%2)
        exponent = exponent/2
    #Appllication of the square and multiply algorithm
    result = 1
    binaryExponent.reverse()
    for i in binaryExponent:
        if i == 0:
            result = (result*result) % modulus
        else:
            result = (result*result*base) % modulus
        #print i,"\t",result
    return result

znelement = squareAndMultiply(encrypted,d,n)
print("The element of Zn once decoded from ciphertext = " + str(znelement))

decoded = []
def zdecode(encoded,r):
    if r < 0:
        return None
    for numdex in range(0,max):
        if encoded - (numdex * (26**r)) < 0:
            decoded.append(chr(numdex-1+65))
            return zdecode(encoded - ((numdex-1) * (26**r)),r-1)

            
zdecode(znelement,2)
print("The three decoded characters are \"" + str(decoded) + "\"")