#! /usr/bin/python
from collections import Counter

print('Hello, world!')

prob = {"A":.082,"B":.015,"C":.028,"D":.043,"E":.127,"F":.022,"G":.020,"H":.061,"I":.070,"J":.002,"K":.008,"L":.040,"M":.024,"N":.067,"O":.075,"P":.019,"Q":.001,"R":.060,"S":.063,"T":.091,"U":.028,"V":.010,"W":.023,"X":.001,"Y":.020,"Z":.001}
#cprob = {"A":.082,"B":.015,"C":.028,"D":.043,"E":.127,"F":.022,"G":.020,"H":.061,"I":.070,"J":.002,"K":.008,"L":.040,"M":.024,"N":.067,"O":.075,"P":.019,"Q":.001,"R":.060,"S":.063,"T":.091,"U":.028,"V":.010,"W":.023,"X":.001,"Y":.020,"Z":.001}


ciphertext1 = "DZUONRTPZDZGOFGKDUOVRYWRNDUVCMFFMRVQUGNKGVODZUNUMPVRWDZUDUNNMBFUFMLGNOKZGOFRVPKMVHUUVOUOZUNURVDZUUATGDRNMVDZUHRVDMVUVDYZMHZYRTFORVUOGQBUIVRYVGKGWNMHGDZUBGDDFUWRNUJMKDUVHUZGONUGHZUOGVUYHFMCGJRWWUNRHMDQGVODZUEMHDRNYGKVRDQUDMVKMPZD"
#              THE-----HTH-----TE-A-----TEA------A-E----A-THE-E--A--THETE-----E-------H----A---A-EEA-E-HE-E-ATHEE---T---ATHE--AT-AE-T-H--H-------E----E-------------THE--TT-E---E---TE--EH---E--HE---E----------E----T----THE---T-------T-ET-A---HT

ciphertext2 = "STKSCKKOTWPLKKZOSCYUUKPLNWCKQKZSDYZPMASTKYCDTYSKQMIKCYZBMAACMOSWZPSTKNOKKEKPSMXKWZSMDWCPKWQTMSTKCLXWQFWZPMEYZMVOYZSTKAWPYZBXYBTSWIWOSOYXKZQKCKYBZKPMIKCSTKXWZPSTKXWZPYSOKXADWOWPKOMXWSYMZXYAKXKOODYSTMVSEMIKEKZSOMXMZKWZPQMXPSTWSSTKOUYCYSMAYSDWOZMSKIKZSTWSMAOWPZKOO"
#              ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

def main():
	#print(sorted(prob, key=prob.get, reverse=True))
	freq(ciphertext1)
	problist = []
	for k,v in prob.items():
		problist.append((k,v))
	print(sorted(problist, key=lambda x: x[1], reverse=True))

def freq(text):
	frequency = Counter()
	for c in text:
		frequency[c] += 1
	print(frequency)
	
	#frequency = []
	#for c in text:
	#	if c not in [item[0] for item in frequency]:
	#		frequency.append([c,0])
	#		print(frequency)
	#	else:
	#		print("sup")
	
	
	
	#print(frequency)
	

main()