/* Michael Sass - Data Structures(605.202) - Lab 1
 * 
 * Assume a Machine has a single register seven instructions
 *		LD	A	Places the operand A in the register
 *		ST	A	Places the contents of the register into the variable A
 *		AD	A	Adds the contents of the variable A to the register
 *		SB	A	Subtracts the contents of the variable A from the register
 *		ML	A	Multiples the contents of the register by the variable A
 *		DV	A	Divides the contents of the register by the variable A
 *		XP	A	Raises the contents of the register to the exponent in the variable A
 *
 * The program below accepts a list of valid postfix expressions containing single letter
 * operands and the operators +, -, *, / and $ then prints a sequence of machine-like 
 * instructions to evaluate the expression and leaves the result in the register.
 *
 * Variables take the form of TEMPn as temporary variables.
 *
 * Usage: 	PostFixEvaluator [inputfile] [outputfile]
 *			The inputfile is expected to contain valid postfix expressions on separate lines
 *			The outputfile can be any named file
 *
*/

import java.util.*;
import java.io.*;

public class PostFixEvaluator {
	
	static String OPERATORS[] = {"+", "-", "*", "/", "$"}; // Stores operator symbols
	static String INS[] = {"AD", "SB", "ML", "DV", "XP", "LD", "ST"}; // Stores instruction symbols
	
	public static void main(String[] args) throws IOException {
		
		/* The program requires explicit use of named files for both input and output.
		 * If the program does not have 2 parameters, the application will exit.
		 * If an output file which already exists is specified, the file will be overwritten.
		*/
		if (args.length != 2) {
			System.out.println("Usage: PostFixEvaluator [InputFile] [OutputFile]");
			return;
		}
		
		/* This block creates the output file and other output related I/O elements. */
		File fout = new File(args[1]); //Creates the output file with the name specified via the command line
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		//Opens postfix expression input file
		//Try statement will catch errors when input file is invalid or nonexistent
		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
			
			String line; // This holds the postfix expression
			
			/* This try statement will catch null pointer exceptions.
			 *
			 * The for loop below iterates through every line of the input file.
			 *
			 * The uneloquent way of iterating through every line is to create the for
			 * loop which iterates up to 9999999 times. When the last line is encountered
			 * a null pointer will be referenced then caught - exiting the loop.
			 *
			 * If the post-fix input has more than 9999999 lines, this program will not evaluate
			 * each of them.
			*/
			try {
				for (int j = 0; j < 9999999; j++) {
					
					String stage[] = {"","",""}; // This variable stages the "expressions" (example: A * B)
					int stackpointer = 0; // Keeps track of the top of the stack
					int tempn = 1; // Keeps track of which TEMPn variable we must use next
					String TEMP = "TEMP"; // Stores "TEMP" in case we want to change this string for the instruction outputs
					
					line = br.readLine(); // Reads in the next line of the postfix input file
					
					/* This boolean value holds the true/false error condition.
					 * If an error is encountered, the noerror will be changed to false.
					 * When the noerror condition is false, the output for the invalid postfix
					 * expression will not be written to the output file.
					*/
					Boolean noerror = true; 
					
					/* The output String caches the instruction output for the postfix expression
					 * if an error is encountered, the output is thrown out. If no error is encountered
					 * the output is parsed and written to the output document. Parsing is performed on
					 * the ";" delimeter.
					*/
					String output = "Expression: " + line + ";";
					
					/* This try-catch block will catch postfix expression errors where operators
					 * are either not in the correct positions or too many operators are encountered
					 * within the expression itself. In these cases, an ArrayIndexOutOfBoundsException
					 * will be thrown in these cases, caught and an error generated.
					*/
					try {
						
						String stack[] = new String[line.length()]; // Initializes stack. Stack size is set to length of expression. 
						int length = line.length(); // This computes the length of the postfix expression
						
						for (int i = 0; i < length; i++) { // Iterates through entire postfix expression character by character
							
							String chr = line.substring(i,i+1); // Reads in current character
							
							/* The clauses below catch and ignore spaces and tabs.
							 * This will eliminate weird errors where trailing spaces throw off
							 * expressions.
							*/
							if (chr.equals(" ")) {
								continue;
							}
							if (chr.equals("\t")) {
								continue;
							}
							
							if (Arrays.asList(OPERATORS).contains(chr)) { // If character read is an operator
								
								stage[2] = stack[--stackpointer]; // Pop the operand stack --> Sets first value in expression
								stage[1] = chr; // Sets the operator in the expression
								stage[0] = stack[--stackpointer]; // Pop the operand stack --> Sets the second value in expression
								
								output += INS[5] + " " + stage[0] + ";";
								
								switch (chr) { // Determine operator
									case "+": // If operator is '+', print "AD [operand]"
										output += INS[0] + " " + stage[2] + ";";
										break;
									case "-": // If operator is '-', print "SB [operand]"
										output += INS[1] + " " + stage[2] + ";";
										break;
									case "*": // If operator is '*', print "ML [operand]"
										output += INS[2] + " " + stage[2] + ";";
										break;
									case "/": // If operator is '/', print "DV [operand]"
										output += INS[3] + " " + stage[2] + ";";
										break;
									case "$": // If operator is '$', print "XP [operand]"
										output += INS[4] + " " + stage[2] + ";";
										break;
									default:
										break;
								}
								
								stack[stackpointer] = TEMP + Integer.toString(tempn++); // Push TEMPn onto the stack
								output += INS[6] + " " + stack[stackpointer++] + ";";
							}
							else { // If character read is an operand
								stack[stackpointer++] = chr; // Push operand on top of stack and increment stack pointer
							}
						}
						
						/* At the end of any postfix expression, the stackpointer should be at 1 which means
						 * only one value is left on the stack. This should be the final evaluated TEMPn variable.
						 * If the value is greater than 1, not enough operators were included in the postfix
						 * expression.
						 *
						 * This will set the noerror condition to false which will then cause an error to be
						 * written to the output file instead of the instructions for that postfix expression.
						*/
						if (stackpointer > 1) {
							bw.write("ERROR: Invalid postfix expression (Not enough operators): " + line);
							bw.newLine(); bw.newLine();
							noerror = false;
						}
						
						/* The noerror condition, if true will parse the output for a postfix expression
						 * Parsing is done based on the ";" delimeter
						*/
						if (noerror) {
							String[] outputs = output.split(";");
							for (int k = 0; k < outputs.length; k++) {
								bw.write(outputs[k]);
								bw.newLine();
							}
							bw.newLine();
						}	
						
					} catch (ArrayIndexOutOfBoundsException e) {
						bw.write("ERROR: Invalid postfix expression (Too many operators): " + line);
						bw.newLine(); bw.newLine();
					}
				}
				
			} catch (NullPointerException e) {
				
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Error: Invalid File");
		}
		
		bw.close(); //Closes output file.
	}
}