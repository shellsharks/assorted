#python squareAndMultiply [n]
#problem set 4, problem 3 --> use n = 18721

import sys

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

#for letter in range(0,26):
#    print(str(chr(letter+65)) + " = " + str(squareAndMultiply(letter,25,int(sys.argv[1]))))

#print(squareAndMultiply(5,1165,31847))