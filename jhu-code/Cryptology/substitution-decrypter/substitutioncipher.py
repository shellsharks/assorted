#Usage python substitutioncipher.py [n-grams file]

import random, sys, signal, time, math

start_time = time.time() #Track time of execution

#Function for handling CTRL+C quits gracefully
def sigint_handler(signum, frame):
    print("\n\n" + str(time.time()-start_time) + " seconds elapsed")
    print("\nExiting! Thanks for using the script!")
    sys.exit()
signal.signal(signal.SIGINT, sigint_handler)


#-----Ciphertext Inputs are Provided Here-----
#Uncomment the ciphertext you want to run through the program, if you do not have the key/plaintext, please comment out the statistics block at the bottom of this script (Lines 128-134)

masterkey,plaintext="",""

#Example 1 (with no masterkey/plaintext provided)
#ciphertext = "DZUONRTPZDZGOFGKDUOVRYWRNDUVCMFFMRVQUGNKGVODZUNUMPVRWDZUDUNNMBFUFMLGNOKZGOFRVPKMVHUUVOUOZUNURVDZUUATGDRNMVDZUHRVDMVUVDYZMHZYRTFORVUOGQBUIVRYVGKGWNMHGDZUBGDDFUWRNUJMKDUVHUZGONUGHZUOGVUYHFMCGJRWWUNRHMDQGVODZUEMHDRNYGKVRDQUDMVKMPZD"

#Example 1
ciphertext,masterkey,plaintext = "DZUONRTPZDZGOFGKDUOVRYWRNDUVCMFFMRVQUGNKGVODZUNUMPVRWDZUDUNNMBFUFMLGNOKZGOFRVPKMVHUUVOUOZUNURVDZUUATGDRNMVDZUHRVDMVUVDYZMHZYRTFORVUOGQBUIVRYVGKGWNMHGDZUBGDDFUWRNUJMKDUVHUZGONUGHZUOGVUYHFMCGJRWWUNRHMDQGVODZUEMHDRNYGKVRDQUDMVKMPZD","GBHOUWPZMXIFCVRSANKDTEYJQL","THEDROUGHTHADLASTEDNOWFORTENMILLIONYEARSANDTHEREIGNOFTHETERRIBLELIZARDSHADLONGSINCEENDEDHEREONTHEEQUATORINTHECONTINENTWHICHWOULDONEDAYBEKNOWNASAFRICATHEBATTLEFOREXISTENCEHADREACHEDANEWCLIMAXOFFEROCITYANDTHEVICTORWASNOTYETINSIGHT"

#Example 2
#ciphertext,masterkey,plaintext = "STKSCKKOTWPLKKZOSCYUUKPLNWCKQKZSDYZPMASTKYCDTYSKQMIKCYZBMAACMOSWZPSTKNOKKEKPSMXKWZSMDWCPKWQTMSTKCLXWQFWZPMEYZMVOYZSTKAWPYZBXYBTSWIWOSOYXKZQKCKYBZKPMIKCSTKXWZPSTKXWZPYSOKXADWOWPKOMXWSYMZXYAKXKOODYSTMVSEMIKEKZSOMXMZKWZPQMXPSTWSSTKOUYCYSMAYSDWOZMSKIKZSTWSMAOWPZKOO","WLQPKABTYJFXEZMURCOSVIDGNH","THETREESHADBEENSTRIPPEDBYARECENTWINDOFTHEIRWHITECOVERINGOFFROSTANDTHEYSEEMEDTOLEANTOWARDEACHOTHERBLACKANDOMINOUSINTHEFADINGLIGHTAVASTSILENCEREIGNEDOVERTHELANDTHELANDITSELFWASADESOLATIONLIFELESSWITHOUTMOVEMENTSOLONEANDCOLDTHATTHESPIRITOFITWASNOTEVENTHATOFSADNESS"

#Example 3
#ciphertext,masterkey,plaintext = "OIXVVHVVYGWXUDJUHSHSAAFVIPXNVGOSJLDKCXUBGSDPLSJVHIVXVKURHSAAFVPJESOHSAAFVGUIUAEOIVASJVGOPKHSOIDVSWKURWSJEKURXGVAWPAUJVXSESJLSJLXVVJWSVAEGHSOIOIVGRJUJKURXWPCVEUJUOFVOXURFAVEWUXKURPXVSJVAKGSRDPJEKURXVPAXVPEKEVPEFXUOIVXGHIPOHVEUSJASWVVCIUVGSJVOVXJSOK","PFCEVWLISZYADJUBMXGORNHQKT","THREEWEEKSFROMNOWIWILLBEHARVESTINGMYCROPSIMAGINEWHEREYOUWILLBEANDITWILLBESOHOLDTHELINESTAYWITHMEIFYOUFINDYOURSELFALONERIDINGINGREENFIELDSWITHTHESUNONYOURFACEDONOTBETROUBLEDFORYOUAREINELYSIUMANDYOUREALREADYDEADBROTHERSWHATWEDOINLIFEECHOESINETERNITY"

#Example 4
#ciphertext,masterkey,plaintext = "ROFERSLHREMLRUHISGLRUHRSZLILUHGPRREFISZPXWJSEZRLSHPXWJSFPZWJQSFSZEDPXWJSXSDLMDSQLPFHDPOEDHSZNEFWWPWJSWZUSSRASZPZREZGUHEUZSDLUHXEWJSZWPERUZISZSIHPFJUHYEFIWPERUZISZSIBLXSEFILBLDDJENSRONSFQSEFGSLFWJLHDLXSPZWJSFSMW","EYGISXQJLCVDRFPAKZHWUNBMOT","MYNAMEISMAXIMUSDECIMUSMERIDIUSCOMMANDEROFTHEARMIESOFTHENORTHGENERALOFTHEFELIXLEGIONSLOYALSERVANTTOTHETRUEEMPERORMARCUSAURELIUSFATHERTOAMURDEREDSONHUSBANDTOAMURDEREDWIFEANDIWILLHAVEMYVENGEANCEINTHISLIFEORTHENEXT"

#Example 5
#ciphertext,masterkey,plaintext = "VAJLBGALPWYTBPKPYNPWLBWSAAJLVWOHPLBQLAJSHOVALOHAYOHZBGAYOZYPYOWYPDYWOZAYGAYGPZKPTZYFZQPLONWYTTPTZGWOPTOAOHPULAUABZOZAYOHWOWFFEPYWLPGLPWOPTPRJWFYADDPWLPPYSWSPTZYWSLPWOGZKZFDWLOPBOZYSDHPOHPLOHWOYWOZAYALWYNYWOZAYBAGAYGPZKPTWYTBATPTZGWOPTGWYFAYSPYTJLPDPWLPEPOAYWSLPWOQWOOFPVZPFTAVOHWODWLDPHWKPGAEPOATPTZGWOPWUALOZAYAVOHWOVZPFTWBWVZYWFLPBOZYSUFWGPVALOHABPDHAHPLPSWKPOHPZLFZKPBOHWOOHWOYWOZAYEZSHOFZKPZOZBWFOASPOHPLVZOOZYSWYTULAUPLOHWODPBHAJFTTAOHZBQJOZYWFWLSPLBPYBPDPGWYYAOTPTZGWOPDPGWYYAOGAYBPGLWOPDPGWYYAOHWFFADOHZBSLAJYTOHPQLWKPEPYFZKZYSWYTTPWTDHABOLJSSFPTHPLPHWKPGAYBPGLWOPTZOVWLWQAKPAJLUAALUADPLOAWTTALTPOLWGOOHPDALFTDZFFFZOOFPYAOPYALFAYSLPEPEQPLDHWODPBWNHPLPQJOZOGWYYPKPLVALSPODHWOOHPNTZTHPLPZOZBVALJBOHPFZKZYSLWOHPLOAQPTPTZGWOPTHPLPOAOHPJYVZYZBHPTDALCDHZGHOHPNDHAVAJSHOHPLPHWKPOHJBVWLBAYAQFNWTKWYGPTZOZBLWOHPLVALJBOAQPHPLPTPTZGWOPTOAOHPSLPWOOWBCLPEWZYZYSQPVALPJBOHWOVLAEOHPBPHAYALPTTPWTDPOWCPZYGLPWBPTTPKAOZAYOAOHWOGWJBPVALDHZGHOHPNSWKPOHPFWBOVJFFEPWBJLPAVTPKAOZAYOHWODPHPLPHZSHFNLPBAFKPOHWOOHPBPTPWTBHWFFYAOHWKPTZPTZYKWZYOHWOOHZBYWOZAYJYTPLSATBHWFFHWKPWYPDQZLOHAVVLPPTAEWYTOHWOSAKPLYEPYOAVOHPUPAUFPQNOHPUPAUFPVALOHPUPAUFPBHWFFYAOUPLZBHVLAEOHPPWLOH","WQGTPVSHZICFEYAURLBOJKDXNM","FOURSCOREANDSEVENYEARSAGOOURFATHERSBROUGHTFORTHONTHISCONTINENTANEWNATIONCONCEIVEDINLIBERTYANDDEDICATEDTOTHEPROPOSITIONTHATALLMENARECREATEDEQUALNOWWEAREENGAGEDINAGREATCIVILWARTESTINGWHETHERTHATNATIONORANYNATIONSOCONCEIVEDANDSODEDICATEDCANLONGENDUREWEAREMETONAGREATBATTLEFIELDOFTHATWARWEHAVECOMETODEDICATEAPORTIONOFTHATFIELDASAFINALRESTINGPLACEFORTHOSEWHOHEREGAVETHEIRLIVESTHATTHATNATIONMIGHTLIVEITISALTOGETHERFITTINGANDPROPERTHATWESHOULDDOTHISBUTINALARGERSENSEWECANNOTDEDICATEWECANNOTCONSECRATEWECANNOTHALLOWTHISGROUNDTHEBRAVEMENLIVINGANDDEADWHOSTRUGGLEDHEREHAVECONSECRATEDITFARABOVEOURPOORPOWERTOADDORDETRACTTHEWORLDWILLLITTLENOTENORLONGREMEMBERWHATWESAYHEREBUTITCANNEVERFORGETWHATTHEYDIDHEREITISFORUSTHELIVINGRATHERTOBEDEDICATEDHERETOTHEUNFINISHEDWORKWHICHTHEYWHOFOUGHTHEREHAVETHUSFARSONOBLYADVANCEDITISRATHERFORUSTOBEHEREDEDICATEDTOTHEGREATTASKREMAININGBEFOREUSTHATFROMTHESEHONOREDDEADWETAKEINCREASEDDEVOTIONTOTHATCAUSEFORWHICHTHEYGAVETHELASTFULLMEASUREOFDEVOTIONTHATWEHEREHIGHLYRESOLVETHATTHESEDEADSHALLNOTHAVEDIEDINVAINTHATTHISNATIONUNDERGODSHALLHAVEANEWBIRTHOFFREEDOMANDTHATGOVERNMENTOFTHEPEOPLEBYTHEPEOPLEFORTHEPEOPLESHALLNOTPERISHFROMTHEEARTH"

#Example 6
#ciphertext,masterkey,plaintext = "SOTHNZKEKTRHZTOJREKTAJVORJNLHCOEJIYTHOTCJBEJPHDTCJBINJBVJFTDSFVLJNVJFTRBYYKBALKTNTCJBINTOOTVPTEJCJBNZKTOEHFVKTYVPTESAKELJBYVKHMTQTTFYSDTEKTOBFJFPCKTHNERJNHEKJBOHFVCTHNOLKHESOSESFPTEKHECJBKHETOJPBZK","HQZVTRAKSWDYPFJIXNOEBMLUCG","ISEARCHTHEFACESOFTHEGODSFORWAYSTOPLEASEYOUTOMAKEYOUPROUDONEKINDWORDONEFULLHUGWHEREYOUPRESSEDMETOYOURCHESTANDHELDMETIGHTWOULDHAVEBEENLIKETHESUNONMYHEARTFORATHOUSANDYEARSWHATISITINMETHATYOUHATESOMUCH"
#---------------- ----------------#

ciphertext = ciphertext.upper() #convert ciphertext input to all caps

#Global Variables
ngrams = {} #stores the n-gram log probabilities
ngramlen = 0 #stores the length of any given n-gram
ngramsum = 0 #stores the sum of n-grams in any given input text file
ngramnotfound = 0 #initializing the ngramnotfound variable which holds the worst log probability value (given ngram is not found)

#Function to decrypt ciphertext given a provided key
def decipher(key,ciphertext):
    alpha = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'
    keymap = {}
    for letter in alpha:
        keymap[letter] = key.index(letter) #keymap(A):Index of where letter is found in key
    plaintext = ''
    for letter in ciphertext:
        plaintext += alpha[keymap[letter]]
    return plaintext

#Calculate all the log probabilities for each n-gram in a provided n-gram text file
def loadNgrams(ngramfile):
    global ngramlen, ngramsum, ngramnotfound, ngrams
    for item in file(ngramfile): #iterate through each line in the n-gram text file
        ngram,count = item.split(' ') #create a tuple for each line (ngram,ngram count)
        ngrams[ngram] = int(count) #add the values of the tuple to the ngrams dictionary {ngram:ngram count, etc...}
        ngramsum += int(count) #the sum of the counts all n-grams based on the n-gram text file
    ngramlen = len(ngram) #the length of the n-gram, for example a quadgram has a length "ngramlen" of 4
    for item in ngrams: #iterate through each of the pairs in the ngrams dictionary
        ngrams[item] = math.log(float(ngrams[item])/ngramsum,10) #set the value of each item in the dictionary to the log probability of that item in the ngrams set
    ngramnotfound = math.log(0.0000000001/ngramsum,10) #worst log probability value, this is to prevent errors with log function
loadNgrams(sys.argv[1]) #run calc log probabilities function

#Calculate the score of each ngram in the input text
#fitness function - https://pdfs.semanticscholar.org/74e6/3f7f64c5a691b8b7c80fde75f07407f664c4.pdf
def calcscore(inputtext):
    global ngramlen, ngramnotfound, ngrams
    score = 0 #initialize score vairable
    for ngram in range(0,len(inputtext)-ngramlen+1): #iterate through each ngram of the ciphertext
        if inputtext[ngram:ngram+ngramlen] in ngrams:
            score += ngrams[inputtext[ngram:ngram+ngramlen]] #if the ngram is in our list, increment the score with the log probability value from the ngrams dictionary
        else: 
            score += ngramnotfound #if the ngram doesn't exist, add ngramnotfound value (worst log probability value)
    return score

#initial candidate key is just the alphabet A-Z (this is shuffled immediately)
alphakey = list('ABCDEFGHIJKLMNOPQRSTUVWXYZ')
candidatescore = (len(ciphertext)-ngramlen+1) * ngramnotfound #assuming even 1 quadgram is found, the new score will be better than this.
currentkey = alphakey

print("\nAttempting to break substituion cipher...\nPress \'CTRL+C\' to quit!!!\n\n")

rnd = 1 #initalizing round variable
bigiterator = 0
while True: #endless loop (must be terminated by user with ctrl+c)
    
    #Start completely new (randomized) candidate key
    random.shuffle(currentkey) #create a random (shuffled) version of the 26 character alphabet candidate key
    currentscore = calcscore(decipher(currentkey,ciphertext)) #calculate score of the decrypted plaintext text using the shuffled key
    
    #Switch letters in the randomized candidate key successively to get better decrypted score values
    iterator,candidateiteration,reset = 0,0,0
    while iterator < 1000: #do this many substitutions - https://d-nb.info/1153797542/34
        bigiterator += 1
        newkey = currentkey[:] #clone currentkey into newkey
        keyletter1,keyletter2 = random.sample(range(0,26),2)
        newkey[keyletter1],newkey[keyletter2] = newkey[keyletter2],newkey[keyletter1] #swap two characters in the cloned key
        newscore = calcscore(decipher(newkey,ciphertext)) #determine score of the new key
        if newscore > currentscore: #if the new score is better...
            currentscore,currentkey = newscore,newkey #new score to beat is this
            candidateiteration = iterator
            iterator = 0 #reset the counter to 0
            reset += 1
        iterator += 1 #increment the counter
       
    #at the end of each round, print best new score of that round (if a new score was found)
    if currentscore > candidatescore:
        candidatescore = currentscore
        print(str(time.time()-start_time) + " seconds elapsed")
        print("Best score " + str(candidatescore) + " found after " + str(reset) + " resets on iteration " + str(candidateiteration) + " of randomized key round " + str(rnd) + ": " + str(bigiterator) + " total iterations")        
        print("Current Key: \'" + ''.join(currentkey) + "\'")
        print("Decrypted Text Using This Key:\n\'" + decipher(currentkey,ciphertext) + "\'")
        
        ######################
        ###STATISTICS BLOCK###
        ######################
        #Block for analyzing accuracy //comment out if not providing master key and plaintext
        diff1,diff2 = 0.0,0.0
        for x in range(0,len(masterkey)):
            if ''.join(currentkey)[x:x+1] != masterkey[x:x+1]:diff1 += 1.0
        for x in range(0,len(plaintext)):
            if decipher(currentkey,ciphertext)[x:x+1] != plaintext[x:x+1]:diff2 += 1.0
        print "Key Find Rate: " + str(int(26-diff1))+"/26",str(int(round((26-diff1)/26,2)*100))+"%"
        print "Plaintext Decipher Rate: " + str(int(len(plaintext)-diff2))+"/" + str(len(plaintext)),str(int(round((len(plaintext)-diff2)/len(plaintext),2)*100))+"%\n"
        #Block for analyzing accuracy
        
    
    rnd += 1 #increment round value