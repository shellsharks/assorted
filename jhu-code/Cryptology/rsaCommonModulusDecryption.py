#python rsaCommonModulusDecryption [n] [b1] [b2] [y1] [y2]
#hw 4, problem 4

import sys

n = int(sys.argv[1])
b1 = int(sys.argv[2]) 
b2 = int(sys.argv[3])
y1 = int(sys.argv[4])
y2 = int(sys.argv[5])

x1 = 0

max = 99999 #max number to iterate up to

#modulo inverse = a^-1 mod m
def modInverse(a,m):
    for i in range(1,max):
        if (((a*i)-1) % m == 0):
            return i

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

def rsaCommonModulusDecryption(n,b1,b2,y1,y2):
    c1 = modInverse(b1,b2)
    c2 = (c1 * b1 - 1)/b2
    x1 = modInverse(squareAndMultiply(y2,c2,n),n) * squareAndMultiply(y1,c1,n)
    #print("c1:" + str(c1) + ", c2:" + str(c2) + ", x1:" + str(x1))
    return x1

x1 = rsaCommonModulusDecryption(n,b1,b2,y1,y2)
print("x1 = " + str(x1))

#print("y1 = " + str(squareAndMultiply(x1,b1,18721)))
#print("y2 = " + str(squareAndMultiply(x1,b2,18721)))