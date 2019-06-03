#autokey

PLAINTEXT = "RENDEZVOUS"
CIPHERTEXT = "MALVVMAFBHBUQPTSOXALTGVWWRG"
K = 8

def plaintextints(plaintext,k):
	keystream=[]
	for x in range(0,len(plaintext)):
		keystream.append(ord(plaintext[x])-65)
	return keystream

def keystreamints(plaintext,k):
	keystream = plaintextints(PLAINTEXT,K)
	keystream.insert(0,k)
	return keystream[:-1]
	
def encrypt(plaintext,k):
	ciphertext = ""
	for x in range(0,len(plaintext)):
		ciphertext = ciphertext + (chr((plaintextints(PLAINTEXT,K)[x] + keystreamints(PLAINTEXT,K)[x])%26+65))
	return ciphertext

def decrypt(ciphertext,k):
	plaintext = ""
	keyelem = k
	for character in ciphertext:
		#print (((ord(character)-65)-keyelem)%26)
		plaintext = plaintext + chr(((ord(character)-65)-keyelem)%26+65)
		keyelem = ((ord(character)-65)-keyelem)%26
	return plaintext

#print (plaintextints(PLAINTEXT,K))
#print (keystreamints(PLAINTEXT,K))
#print (encrypt(PLAINTEXT,K))
#print (decrypt(encrypt(PLAINTEXT,K),K))

for krange in range(1,26):
	print("k = " + str(krange) + " ||| " + decrypt(CIPHERTEXT,krange))
