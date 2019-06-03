Algorithms EN.605.421.84.FA17 - Programming Assignment 3
Michael Sass

Assignment:

	You are consulting for a group of people (who would prefer not to be mentioned here by name) 
	whose job consists of monitoring and analyzing electronic signals coming from ships in the 
	Atlantic ocean. They want a fast algorithm for a basic primitive that arises frequently: 
	"untangling" a superposition of two known signals. Specif- ically, they are picturing a 
	situation in which each of two ships is emitting a short sequence of 0s and 1s over and over, 
	and they want to make sure that the signal they are hearing is simply an interleaving of these 
	two emissions, with nothing extra added in.
	
	This describes the whole problem; we can make it a little more explicit as follows. Given a 
	string x consisting of 0s and 1s, we write xk to denote k copies of x concatenated together. 
	We say that string x′ is a repetition of x if it is a prefix of xk for some number k. So 
	x′ = 10110110110 is a repetition of x = 101.
	
	We say that a string s is an interleaving of x and y if its symbols can be partitioned into two 
	(not necessarily contiguous) subsequence s′ and s′′ so that s′ is a repetition of x and s′′ is 
	a repetition of y. (So each symbol in s must belong to exactly one of s′ and s′′.) For example, 
	if x = 101 and y = 00, then s = 100010101 is an interleaving of x and y since characters 
	1, 2, 5, 7, 8, and 9 form 101101—a repetition of x—and the remaining characters 3, 4, 6 form 
	000—a repetition of y. In terms of our application, x and y are the repeating sequences from 
	the two ships, and s is the signal we are listening to. We want to make sure s "unravels" into 
	simple repetitions of x and y.
	
	(a) Give an efficient algorithm that takes strings s, x, and y and decides if s is an 
	interleaving of x and y. Derive the computational complexity of your algorithm.

	(b) Implement your algorithm above and test its run time to verify your analysis. 
	Remember that CPU time is not a valid measure for testing run time. You must use something such 
	as the number of comparisons.

		
Execution:
	-To use, reference "Usage" section below...
	-Compiling can be done using commandline...
		javac MedianOfThree.java
	-JDK v1.8.0_121

		
Usage: interleaved [inputfile] [outputfile]
	
	-the [inputfile] is formatted with separate sets of strings that want to be run as detailed below...
		
		[first interleaved string],[first string 1],[first string 2]
		[second interleaved string],[second string 1],[second string 2]
		
	-an example input file is shown below... (with no beginning or trailing blank spaces)
	
		cmoownkey,cow,monkey
		100010101,101,00
		XXZXXXY,XXY,XXZ
		aadbbcbcac,aabcc,dbbca
		aadbbbaccc,aabcc,dbbca
		
	-input file can not accept/handle blank lines.
	-output file can be any unique name.

	
Implementation Notes:
	
	-Notepad++ was used as the code editor (not Eclipse), compilation and execution instructions are included in this readme.
	-Robust error checking/handling is not implemented. Follow this readme and there should be no usage issues!
	-Sample Inputs/Outputs are stored in the IO folder.
	
	
Output: (Example Output)
	
	TRUE! "100010101" is an interleaving of "101" and "00". >>> Comparisons:52

	
References:
	http://www.homeandlearn.co.uk/java/multi-dimensional_arrays.html
	