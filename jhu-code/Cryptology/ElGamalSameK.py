c = [(1430,697),(1430,1113)]

gamma1 = c[0][0]
delta1 = c[0][1]
gamma2 = c[1][0]
delta2 = c[1][1]
p = 2357 #Alice's public parameter p
m1 = 2035

max = 99999

#modulo inverse = a^-1 mod m
def calcInverse(a,m):
    for i in range(1,max):
        if (((a*i)-1) % m == 0):
            return i
            #return "multiplicative inverse is " + str(i)

def squareAndMultiply(base,exponent,modulus):
    binaryExponent = []
    while exponent != 0:
        binaryExponent.append(exponent%2)
        exponent = exponent/2
    result = 1
    binaryExponent.reverse()
    for i in binaryExponent:
        if i == 0:
            result = (result*result) % modulus
        else:
            result = (result*result*base) % modulus
    return result

#for a in range(1,9999):
    #m = (c[0][1] * calcInverse(squareAndMultiply(c[0][0],a,p),p) ) % p
    #if m == m1:
        #print a
        
m2 = (calcInverse(697,2357) * 1113 * 2035) % 2357
print(m2)