#python moduloinversetester.py [a] [m]
#problem set 4, question 1

import sys

max = 99999 #max number to iterate up to

#modulo inverse = a^-1 mod m
def calcInverse(a,m):
    for i in range(1,max):
        if (((a*i)-1) % m == 0):
            return "multiplicative inverse is " + str(i)
            
print(calcInverse(int(sys.argv[1]),int(sys.argv[2])))