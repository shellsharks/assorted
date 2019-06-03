/* Michael Sass - Data Structures 605.202 - Lab 2
 * 
 * Towers of Hanoi (Recursion / Iteration Lab) 
 *
 * This program solves the classic "Towers of Hanoi" problem using both recursive and iterative methods.
 * The list of moves needed to solve the problem given a tower of size n is printed to an output file
 * using the following structure... "Move disk i from tower X to tower Y"
 *
 * i is a number from 1 to n and "X" and "Y" represent 2 different towers A, B or C.
 */

import java.util.Scanner;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.io.*;

public class Hanoi {
	
	//this n value is set to a large number but is overwritten by cmd line input
	static int n = 9999;
	
	//This String is used to cache output.
	String output = "";
	
	/* The three towers of Hanoi (A, B and C) are initialized here.
	 * Each tower is given 2 extra elements
	 * tower[0] holds the "name" index of the tower
	 * tower[n+1] holds the stack pointer index
	 */
	int[] towera = new int[n + 2];
	int[] towerb = new int[n + 2];
	int[] towerc = new int[n + 2];
	
	//The name map is used to lookup the tower name based on "name" index
	String[] names = {"Empty","A","B","C"};
	
	//Solves the towers of Hanoi recursively
	public void recursive(int n, int[] from, int[] aux, int[] to) {
		if (n == 1) {
			move(from, to);
		} else {
			recursive(n - 1, from, to, aux);
			move(from, to);
			recursive(n - 1, aux, from, to);
		}
	}
	
	//Solves the towers of Hanoi iteratively
	public void iterative(int n, int[] from, int[] aux, int[] to) {
		double moves = Math.pow(2, n) - 1; //calculates number of moves
		
		//If n is even, switch the auxiliary and destination poles.
		if (n % 2 == 0) {
			int[] temp = aux;
			aux = to;
			to = temp;
		}
		
		for (int i = 1; i <= moves; i++) {
			if (i % 3 == 1) {
				move(from, to);
			}
			else if (i % 3 == 2) {
				move(from, aux);
			}
			else if (i % 3 == 0) {
				move(aux, to);
			}
		}
	}
	
	private void move(int[] from, int[] to) {
		
		/* The conditional below is the gatekeeper for valid moves.
		 *
		 * If the disk to be moved from source tower to destination tower
		 * is larger than the current disk on the destination tower then
		 * this conditional will prevent the move.
		 */
		if (from[from[n+1]] > to[to[n+1]]) {
			move(to, from);
			return;
		}
		
		//Prints the move. (Format: "Move disk [#] from tower [source] to tower [destination]")
		output(0,"Move disk " + from[from[n + 1]] + " from tower " + names[from[0]] + " to tower " + names[to[0]]);
		
		/* Modifies stack pointers
		 *
		 * The index "above" the current destination stack pointer is set to the
		 * index of the source stack pointer.
		 * The source tower stack pointer is incremented (in this case moved "down" in the array)
		 * The destination tower stack pointer is decremented (in this case moved "up" in the array)
		 */
		to[--to[n+1]] = from[from[n+1]++];
		
		//set the value of the recently vacated index in the source array tower to "0"
		from[from[n+1]-1] = 0;
	}
	
	private void loadDisks(int n) {
		
		//Sets the name index for the three towers (this is used to map tower names)
		towera[0] = 1;
		towerb[0] = 2;
		towerc[0] = 3;
		
		//Loads the start tower with all the disks (disk n at the bottom, disk 1 at the top)
		for (int i = 1; i < n + 1; i++) {
			towera[i] = i;
		}
		
		//Initializes the stack pointers for each tower
		towera[n + 1] = 1;
		towerb[n + 1] = n + 1;
		towerc[n + 1] = n + 1;
	}
	
	/* Displays the towers using the format shown below.
	 * 
	 * There are 3 columns represented, one for each of the towers; A, B and C
	 * Row 1 represents the tower names
	 * For a towers of Hanoi of n = 6 disks, the tower would be presented as seen below
	 * The final row represents the current stack pointer for the respective tower
	 * The stack pointer references the index in the tower array which holds the lowest non-zero value
	 * If the stack pointer = n + 1 then the tower is empty.
	 * 
	 * A	B	 C
	 * ---	---	 ---
	 * 1	0	 0
	 * 2	0	 0
	 * 3	0	 0
	 * 4	0	 0
	 * 5	0	 0
	 * 6	0	 0
	 * ---	---	 ---
	 * 1	7	 7
	 * 
	 */
	private void displayTowers() {
		String towers = "";
		
		towers += "\n" + names[towera[0]] + "\t" + names[towerb[0]] + "\t" + names[towerc[0]];
		towers += "\n---\t" + "---\t" + "---\n";
		
		for (int i = 1; i < n + 1; i++) {
			towers += towera[i] + "\t" + towerb[i] + "\t" + towerc[i] + "\n";
		}
		
		towers += "\n---\t" + "---\t" + "---\n";
		towers += towera[n + 1] + "\t" + towerb[n + 1] + "\t" + towerc[n + 1] + "\n\n";
		
		output += towers;
	}
	
	/* Returns various metrics for the application
	 * 
	 * 	-	How many moves it takes to solve the puzzle
	 *	-	The execution time in seconds
	 * 
	 */
	private String metrics(int n, long s, long e) {
		NumberFormat formatter = new DecimalFormat("#0.00000");
		double moves = Math.pow(2, n) - 1;
		return "Tower of Hanoi has " + n + " disks.\nIt took " + (int)moves + " moves to solve the puzzle.\nExecution time is " + formatter.format((e - s) / 1000d) + " seconds.";
	}
	
	//Caches output to a String for output to a file and outputs moves to System.out.
	private void output(int mode, String text) {
		//System.out.println(text); //CAN BE TURNED ON TO PRINT MOVES TO COMMAND LINE
		output += text + "\n";
	}
	
	/* The main driver for the towers of hanoi application
	 */
	public static void main(String[] args) throws IOException {
		
		//Error handling is all contained here.
		try {
		
			if (args.length != 3) {
				System.out.println("Usage: PostFixEvaluator -[i|r] [n] [OutputFile]");
				return;
			}
			else if ((Integer.parseInt(args[1]) < 1)) {
				System.out.println("Error: [n] must be a postive integer");
				System.out.println("Usage: PostFixEvaluator -[i|r] [n] [OutputFile]");
				return;
			}
		
		} catch (NumberFormatException e) {
			System.out.println("Error: [n] must be an integer");
			System.out.println("Usage: PostFixEvaluator -[i|r] [n] [OutputFile]");
			return;
		}
		
		File fout = new File(args[2]); //Creates the output file with the name specified via the command line
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		Hanoi hanoi = new Hanoi(); //initializes a new towers of hanoi instance
		hanoi.n = Integer.parseInt(args[1]); //accepts n as input from the command line
		hanoi.loadDisks(hanoi.n); //loads the disks onto the starting tower

		long start = System.currentTimeMillis(); //start the timer
		
		//This control structure controls whether the problem is solves iteratively or recursively.
		if (args[0].equals("-i")) {
			hanoi.output += "\nProblem solved iteratively.\nMoves shown below...\n\n";hanoi.iterative(hanoi.n, hanoi.towera, hanoi.towerc, hanoi.towerb);
		}
		else if (args[0].equals("-r")) {
			hanoi.output += "\nProblem solved recursively.\nMoves shown below...\n\n";hanoi.recursive(hanoi.n, hanoi.towera, hanoi.towerc, hanoi.towerb);
		}
		else {
			System.out.println("No switch given for solve method...defaulting to recursive...");
			hanoi.output += "\nProblem solved recursively.\nMoves shown below...\n\n";hanoi.recursive(hanoi.n, hanoi.towera, hanoi.towerc, hanoi.towerb);
		}
		
		long end = System.currentTimeMillis(); //end the timer
		
		/* Prints the metrics of the application with the following format...
		 *
		 * Tower of Hanoi has [n] disks.
		 * It took [# of moves] moves to solve the puzzle.
		 * Execution time is [# of seconds] seconds.
		 */
		hanoi.output = hanoi.metrics(hanoi.n, start, end) + hanoi.output;
				
		bw.write(hanoi.output); //writes output to text file
		bw.close();
		System.out.println("Success! Application Completed. Output saved in \"" + args[2] + "\" file on disk.");
	}
}