
c = [(3781,14409),(31552,3930),(27214,15442)]
#c = [(3781,14409),(31552,3930),(27214,15442),(5809,30274),(5400,31486),(19936,721),(27765,29284),(29820,7710),(31590,26470),(3781,14409)] #(gamma,delta)

max = 99999 #max number to iterate up to

#modulo inverse = a^-1 mod m
def calcInverse(a,m):
    for i in range(1,max):
        if (((a*i)-1) % m == 0):
            return i
            #return "multiplicative inverse is " + str(i)

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

p = 31847 #mod p
alpha = 5 #g
a = 7899 #k
beta = 18074 #gamma?

gamma = squareAndMultiply(alpha,a,p)

decoded = []
def zdecode(encoded,r):
    if r < 0:
        return None
    for numdex in range(0,max):
        if encoded - (numdex * (26**r)) < 0:
            decoded.append(chr(numdex-1+65))
            return zdecode(encoded - ((numdex-1) * (26**r)),r-1)

for tup in c:
    m = (tup[1] * calcInverse(squareAndMultiply(tup[0],a,p),p) ) % p
    print(m)
    zdecode(m,2)
    print("The three decoded characters are \"" + str(decoded) + "\"")