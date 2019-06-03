#generator
from __future__ import print_function

import sys

m = int(sys.argv[1])

def generator(m):
    generators = []
    print("i =      1 2 3 4 5 6 7 8 9 10 11 12")
    for n in range(2,m):
        order = 0
        orderfound = False
        print("n = " + str(n) + " | ", end=' ')
        for i in range(1,m):
            val = (n**i)%m
            print(str(val), end=' ')
            if val == 1 and orderfound == False:
                orderfound = True
                order = i
                if i == 12: generators.append(n)
                    
        print(" | order(" + str(n) + ") = " + str(order))
    
    print("Generators: " + str(generators))
    return

print(generator(m))


