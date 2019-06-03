/* Algorithms EN.605.421.84.FA17 - Programming Assignment 3
 * Michael Sass (11/24/2017)
 *
 * Assignment:

 * You are consulting for a group of people (who would prefer not to be mentioned here by name)
 * whose job consists of monitoring and analyzing electronic signals coming from ships in the 
 * Atlantic ocean. They want a fast algorithm for a basic primitive that arises frequently: 
 * "untangling" a superposition of two known signals. Specifically, they are picturing a situation 
 * in which each of two ships is emitting a short sequence of 0s and 1s over and over, and they 
 * want to make sure that the signal they are hearing is simply an interleaving of these two 
 * emissions, with nothing extra added in.

 * This describes the whole problem; we can make it a little more explicit as follows. Given a string 
 * x consisting of 0s and 1s, we write xk to denote k copies of x concatenated together. We say that 
 * string x′ is a repetition of x if it is a prefix of xk for some number k. So x′ = 10110110110 is 
 * a repetition of x = 101.

 * We say that a string s is an interleaving of x and y if its symbols can be partitioned into two 
 * (not necessarily contiguous) subsequence s′ and s′′ so that s′ is a repetition of x and s′′ is 
 * a repetition of y. (So each symbol in s must belong to exactly one of s′ and s′′.) For example, 
 * if x = 101 and y = 00, then s = 100010101 is an interleaving of x and y since characters 
 * 1, 2, 5, 7, 8, and 9 form 101101—a repetition of x—and the remaining characters 3, 4, 6 form 
 * 000—a repetition of y. In terms of our application, x and y are the repeating sequences from the 
 * two ships, and s is the signal we are listening to. We want to make sure s "unravels" into simple 
 * repetitions of x and y.

 * (a) Give an efficient algorithm that takes strings s, x, and y and decides if s is an 
 * inter-leaving of x and y. Derive the computational complexity of your algorithm.

 * (b) Implement your algorithm above and test its run time to verify your analysis. 
 * Remember that CPU time is not a valid measure for testing run time. You must use something such 
 * as the number of comparisons.
*/

import java.io.*;

public class interleaved {
	
	static int comparisons = 0; //used to track comparisons
	
	/* Determines if a string 's' is an interleaving of two strings 'x' and 'y'.
	 * 
	 * Inputs:	s:	This is the interleaved string.
	 * 			x:	This is the first of two smaller strings being evaluated.
	 *			y: 	This is the second of two smaller strings being evaluated.
	 *
	 * Outputs:	Returns true or false if s is an interleaving of x and y.
	 */
    public static boolean interleaving(String s, String x, String y) {
		
		comparisons = 0; //resets comparisons counter.
		
		int n = s.length(); //Suppose s has n characters total
		String xprime = repeat(x, n); //Repetition x' of x consisting of exactly n characters
		String yprime = repeat(y, n); //Repetition y' of y consisting of exactly of n characters

		/*RECCURENCE: (M[i,j] = YES) <=> [(M[i-1,j) = YES) /\ (s[i+j] = x'[i])] \/ [(M[i,j-1) = YES) /\ (s[i+j] = y'[j])] */
		
		boolean[][] M = new boolean[n][n]; // 2-Dimensional array to hold M[i][j]
		
		M[0][0] = true; //Sets M[0][0] to true (this is always the case).
		
		for (int k = 1; k <= n; k++) { // for k = 1,...,n do...
			
			// The following two for loops are run separately from the main for loop because
			// the "i-1" and "j-1" values throw errors when run in the main for loop.
			// These two for loops essentially solve the first row and first column of M[i][j] matrix.
			for (int j = 1; j < n; j++) { // Solves first row of M[i][j] matrix.
				if (j == k) {
					comparisons++;
					if (M[0][j-1] == true && s.substring(j-1,j).equals(yprime.substring(j-1,j)))
						M[0][j] = true;
				}
			}
			
			for (int i = 1; i < n; i++) { // Solves first column of M[i][j] matrix.
				if (i == k) {
					comparisons++;
					if (M[i-1][0] == true && s.substring(i-1,i).equals(xprime.substring(i-1,i)))
						M[i][0] = true;
				}
			}
			
			//for all pairs (i,j) such that i + j = k do...
			for (int i = 1; i < n; i++) {
				for (int j = 1; j < n; j++) {
					if (i + j == k) {
						comparisons++;
						//if M[i-1,j]=YES and s[i+j]=x'[i] then
						if (M[i-1][j] == true && s.substring(i+j-1,i+j).equals(xprime.substring(i-1,i)))
							M[i][j] = true;
						//else if M[i,j-1]=YES and s[i+j]=y'[j] then
						else if (M[i][j-1] == true && s.substring(i+j-1,i+j).equals(yprime.substring(j-1,j)))
							M[i][j] = true;
						else
							M[i][j] = false;
					}
				}
			}
		}
		
		//return YES if and only if there is some pair (i,j) with i+j=n where M[i,j] = YES
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i + j == n && M[i][j] == true) { return true; }
			}
		}
		return false;
	}
	
	/* Returns a self-repeating string "string'" which is n in length.
	 *
	 * Inputs:	str: String to be repeated.
	 *			len: length of the output string.
	 *
	 * Outputs:	String which is n in length and is a repeating version of input str.
	 */
	public static String repeat(String str, int len) {
		if (str.length() >= len) { return str; } // If length of input string is > length, return string.
		String output = "";
		for (int i = 1; i <= (len / str.length()); i++) { output += str; }
		return output += str.substring(0,(len - output.length()));
	}
	
	/* Prints the state of the M[i][j] matrix
	 *
	 * Inputs:	M:	This is the M[i][j] two-dimensional boolean array matrix.
	 *			n:	This is the size of the matrix (both columns and rows).
	 */
	public static void printM(boolean[][] M, int n) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) { System.out.print(M[i][j] + "\t"); }
			System.out.print("\n");
		}
	}
	
	/* Drives the interleaving function and prints/formats results.
	 * 
	 * Inputs:	s,x,y:	Same as interleaving function.
	 * Outputs:	Outputs/formats a string which describes results of test.
	 */
	public static String run(String s, String x, String y) {
		if (interleaving(s,x,y))
			return "TRUE! \"" + s + "\" is an interleaving of \"" + x + "\" and \"" + y + "\". >>> Comparisons:" + comparisons + "\n";
		else
			return "FALSE! \"" + s + "\" is NOT an interleaving of \"" + x + "\" and \"" + y + "\". >>> Comparisons:" + comparisons + "\n";
	}
	
	/* Main function:
	 *		-Performs I/O
	 *		-Performs error checking
	 *		-Drives execution
	 */
	public static void main(String[] args) throws IOException {
		
		if (args.length != 2) { //check to see if proper arguments are made.
			System.out.println("Usage: interleaved [inputfile] [outputfile]");
			System.exit(0);
		}
		
		File fout = new File(args[1]); // Create output file using user-specified name.
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			
		String cache = "";
		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
			String line = "";
			while((line = br.readLine())!= null) { cache += line + ";"; }
		} catch (FileNotFoundException e) {
			System.out.println("Error: Input file not found"); return;
		}
		
		String[] inputs = cache.split(";");
		
		for (int i = 0; i < inputs.length; i++)
			bw.write(run(inputs[i].split(",")[0],inputs[i].split(",")[1],inputs[i].split(",")[2]));
			
		bw.close();
	}
}