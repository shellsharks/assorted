from __future__ import print_function

ciphertext = "LRPFYTRSUXRFLMPIXJWCRLREFKWGPYLKCIFIJXJHDDXUXYCJQINGUVTIRRPKVPRXUXPKDSPQUVQDKMAZFIIMRGHKVTNPNBEWCRLRGVKHLSJMFFJLXUXRBICPBIVOCFSHKCOICVUXNOHAVBNTVTBMAMYSHLSJBEHWCHERTFTCOOXUVTUIAMWCGUEEWFBZLIRLRBSCPOHNGPLHJAVBWCVRTTVTBXUXCOHRSSMYSHKEYEUOGINHGZDTPXEXVGWCGEHLQWCHDNZSIJCGHJHTYHLAZGQPINMYOCBXUXEFPGWRWYWBQIYYJZDUPLTERAMSXXUCJRXUXPKTPIENEBXLKBGKVTNPNBEVPPVLBEUIFINGKSAMTRTERIFINGKSAMTRFFJTBPVDVDWYRGHDGXLXUXJBDUEAWTWGAPRWRBSULRXCSSYRQMYSSPCCHNRTPFYXNOQMYGMYSBGRGAVQDJHZHFBAGKUMRBSRLRBIPGCEGAJADIIQIRZTJCVGKVTASYWRGXDXUXPPJPRRWNWIFWBFVWCLIEYZFTYRQMYSLMPIXJHLGWGXUOCBXHKESSYRQEVOERMATJWACRPXJIRFXUTKHWCCFXVATBSSTECIFIEPFFABIAMZFTRLRRDCKCHQHNBIFIITCZTWEAWKIGLIQTERBMZRWWOGMYGHEHWCTYTZBJLXVEKVTWARKVHWCWZTCZTQXBYWWVSVRLZBIFEGWZALFMGXESHQEAWKVTLXUXPRXQECIVOGCH"
#ciphertext = "googleoo"
#ciphertext = "WHENTHECLOCKSTRIKESTWELVEATTACK"
#ciphertext = "vptnvffuntshtarptymjwzirappljmhhqvsubwlzzygvtyitarptyiougxiuydtgzhhvvmumshwkzgstfmekvmpkswdgbilvjljmglmjfqwioiivknulvvfemioiemojtywdsajtwmtcgluysdsumfbieugmvalvxkjduetukatymvkqzhvqvgvptytjwwldyeevquhlulwpkt"
#ciphertext = "VPXZGIAXIVWPUBTTMJPWIZITWZT"

#ciphertext = "VURZJUGRGGUGVGJQKEOAGUGKKQVWQP"
#ciphertext = "TSPXHSEPEESETEHOICMYESEIIOTUON"

engfreq = {'A':.082,'B':.015,'C':.028,'D':.043,'E':.127,'F':.022,'G':.020,'H':.061,'I':.070,'J':.002,'K':.008,'L':.040,'M':.024,'N':.067,'O':.075,'P':.019,'Q':.001,'R':.060,'S':.063,'T':.091,'U':.028,'V':.010,'W':.023,'X':.001,'Y':.020,'Z':.001}

n = 7
masterkey = "ENTROPY"

def char_frequency(str1):
	dict = {}
	for n in str1:
		keys = dict.keys()
		if n in keys:
			dict[n] += 1
		else:
			dict[n] = 1
	return dict


def nth_letter(input, n):
	nthCharList = []
	for x in range(0,n):
		nthCharList.append(input[x:][::n])
	return nthCharList


def iofc(input):
	
	ci_total = 0
	ci_1_total = 0

	for value in char_frequency(input).values():

		ci_total = ci_total + value
		if value > 1: ci_1_total = ci_1_total + (value * (value - 1))
	
	return (ci_1_total / ((len(input) - 1) * len(input)))
	
	
def chistat(input):
	chival = 0
	for pair in char_frequency(input).items():
		chival = chival + ((pair[1] - (engfreq[pair[0].upper()] * len(input)))**2)/(engfreq[pair[0].upper()] * len(input))
	return chival


def vigmatrix(plain,key):
	return (chr(((((ord(plain)-65)%26)+((ord(key)-65)%26))%26)+65))


def chiTheLine(input):
	input=input.upper()
	print(input)
	print("-----------------------")
	for x in range(0,26):
		print(str(chr(range(0,26)[(0-x)]+65)) + " ||| ",end="")
		sequence = ""
		for y in input:
			sequence = sequence + vigmatrix(y,chr(x+65))
		print (sequence,end="")
		print(" ||| " + str(chistat(sequence)))
	print("-----------------------\n\n")
	

#---------------------------------------------
## OUTPUT OUTPUT OUTPUT ##
print("\nFrequency of characters in \"" + ciphertext + "\"")
print(sorted(char_frequency(ciphertext).items(), key=lambda x: x[1], reverse=True))


## OUTPUT OUTPUT OUTPUT ##
print ("")
print ("nth character strings: ", end="")
print (nth_letter(ciphertext,n))
print ("")


## OUTPUT OUTPUT OUTPUT ##
for list in nth_letter(ciphertext,n):
	print("IC: ",end="")
	print(iofc(list))
print ("")
	

for list in nth_letter(ciphertext,n):
	print (chiTheLine(list))
	
	
#ENTROPY


#plaintext
#HEWOKETOHEARWOLVESINTHELOWHILLSTOTHEWESTOFTHEHOUSEANDHEKNEWTHATTHEYWOULDBECOMINGOUTONTOTHEPLAININTHENEWSNOWTORUNTHEANTELOPEANHOURLATERHEWASCROUCHEDINTHESNOWINTHEDRYCREEKBEDHEWENTFORWARDONKNEESANDELBOWSANDWHENHEREACHEDTHELASTOFTHESMALLDARKJUNIPERTREESHECROUCHEDQUIETLYTOSTEADYHISBREATHANDTHENRAISEDHIMSELFSLOWLYANDLOOKEDOUTTHEYWERERUNNINGONTHEPLAINHARRYINGTHEANTELOPEANDTHEANTELOPEMOVEDLIKEPHANTOMSINTHESNOWANDCIRCLEDANDWHEELEDANDTHEDRYPOWDERBLEWABOUTTHEMINTHECOLDMOONLIGHTANDTHEIRBREATHSMOKEDPALELYINTHECOLDASIFTHEYBURNEDWITHSOMEINNERFIREANDTHEWOLVESTWISTEDANDTURNEDANDLEAPTINASILENCESUCHTHATTHEYSEEMEDOFANOTHERWORLDENTIRETHEYMOVEDDOWNTHEVALLEYANDTURNEDANDMOVEDFAROUTONTHEPLAINUNTILTHEYWERETHESMALLESTOFFIGURESINTHATDIMWHITENESSANDTHENTHEYDISAPPEARED
