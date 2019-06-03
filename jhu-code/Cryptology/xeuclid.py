#Extended Euclid Algorithm --> python xeuclid [base|a] [modulus|b|m]
#problem set 4, question 1

import math,sys

def xeuclid(a,b):
    a0 = a
    b0 = b
    t0 = 0
    t = 1
    s0 = 1
    s = 0
    q = math.floor(a0/b0)
    r = a0 - (q * b0)
        
    while r > 0:
        temp = t0 - (q * t)
        t0 = t
        t = temp
        temp = s0 - (q * s)
        s0 = s
        s = temp
        a0 = b0
        b0 = r
        q = math.floor(a0/b0)
        r = a0 - (q * b0)
    
    r = b0
    return [r,s,t]

print(int(xeuclid(int(sys.argv[1]),int(sys.argv[2]))[1]))