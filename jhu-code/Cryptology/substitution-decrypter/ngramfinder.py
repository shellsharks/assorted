#Usage: python ngramfinder.py [input reference text] [n] [output file]

import re, sys
from collections import Counter

print('\nngramfinder script running on input ' + str(sys.argv[1]) + ' finding ' + sys.argv[2] + '-grams...')
ngram = int(sys.argv[2]) #What "n" n-gram should we harvest from the reference doc? (e.g. bigram:2, trigram:3, quadgram:4)

with open(sys.argv[1], 'r') as file: #open the file provided as a command line argument
    inputstring = file.read().replace('\n', '') #read the file into a variable (removing all new line characters)

print('removing non-alpha characters from input...')
outputstring = re.sub('[^A-Za-z]+', '', inputstring).upper() #remove all non-alpha characters from input

print('harvesting n-grams...')
grams = Counter() #storage for the ngrams and their counts
for x in range(0,len(outputstring)-ngram+1): #iterate through all n-grams in the input file
    grams[outputstring[x:x+ngram]] += 1 #increment/add ngrams to grams Counter() storage

print('sorting n-grams by count...\n')
outputfile = open(sys.argv[3], "w") #open output file
for x,y in sorted(grams.items(), key=lambda tup: tup[1], reverse=True): #sort n-gram container by count
    outputfile.write(str(x) + ' ' + str(y) + '\n') #write n-grams to output file

print("n-grams successfully harvested!")
print(str(len(set(grams.elements()))) + " unique n-grams found.")
print(str(len(list(grams.elements()))) + " total n-grams found.\n\nTop 5 n-grams were...")
for x in grams.most_common(5):
    print(str(x[0]) + " " + str(x[1]))
print('\nn-gram output saved at \'' + str(sys.argv[3]) + '\'')
outputfile.close() #close outputfile