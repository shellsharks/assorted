 /* Michael Sass - Data Structures (605.202) - Lab 4
  * Sort Comparison (Quicksorts)
  *
  * Base quicksort code from - http://www.vogella.com/tutorials/JavaAlgorithmsQuicksort/article.html
  *
  * Lars Vogel (2016) Quicksort (Version 0.7, 04.10.2016) [Computer Program]. Available at
	http://www.vogella.com/tutorials/JavaAlgorithmsQuicksort/article.html (Accessed at 30 April 2017)
  *
  * This program uses a recursive Quicksort (aka Partition Exchange Sort) to sort a list of integers.
  * The quicksort will drop into an insertion sort if a partition of size k or less is reached.
  * 	- the value k defaults to (2) unless specified by the user using the advanced execution mode.
  * The quicksort can use three different pivot methods.
  * 	1 - The first value of the partition is the pivot.
  * 	2 - The median-of-three value of the partition is the pivot.
  *		3 - The last value of the partition is the pivot.
  *
  * The application has several execution methods, these are detailed below.
  *		(Simple Execution)		Quicksort
								::Will sort a randomly generated list of size [size=5] using default p=1 and k=2 values.
  *		(Secondary Execution)	Quicksort [p] [k] [size]
								::Will sort a randomly generated list of size [size] using user-submitted p and k values.
  *		(Standard Execution)	Quicksort [inputfile] [outputfile]
								::Will sort a user-selected input file and generate a sorted output file using default p=1 and k=2 values 
  *		(Advanced Execution)	Quicksort [p] [k] [inputfile] [outputfile]
								::Will sort a user-selected input file and generate a sorted output file using user-selected p and k values.
  */

import java.util.Arrays;
import java.util.Random;
import java.io.*;

public class Quicksort  {
	private int[] numbers; // Holds the list which is being sorted.
	private int number; // Holds the number of items in the list.
	
	private static int size = 5; // Holds default size of list if randomly generated list is used.
	private final static int MAX = 99; // Holds max value of individual item in list if randomly generated list is used.
	private static int p = 1; // Initializes pivot. (Default value of 1)
	private static int k = 2; // Initializes partition size stopping case. (Default value of 2)
	private int exchanges = 0; // Initializes number of exchanges.
	private int comparisons = 0; // Initializes number of comparisons.
	
	/* Sets up the randomly generated list.
	 * Accepts a "size" value from the user via the console and creates a randomly generated list of size [size].
	 * Uses the "MAX" variable to determine the highest value for any one element in the randomly generated list.
	 */
	public void setUp() {
			numbers = new int[size];
			Random generator = new Random();
			for (int i = 0; i < numbers.length; i++) {
					numbers[i] = generator.nextInt(MAX);
			}
	}
	
	/* Initializes sorting process.
	 * Performs error-checking for null lists.
	 *
	 * Accepts the list to be sorted, the pivot [p] and the partition size stopping case [k].
	 * Using input, kick-off quicksort.
	 */
	public void sort(int[] values, int p, int k) {
			if (values == null || values.length == 0) { return; } // Check for empty or null array.
			this.numbers = values;
			number = values.length;
			quicksort(0, number - 1, p, k);
	}
	
	/* Performs the quicksort recursively on a given partition (index low through index high) of a list.
	 * 
	 * Inputs:	low:	This is the index of the first item in the partition.
	 * 			high:	This is the index of the last item in the partition.
	 *			p: 		This is the pivot value. (Codes below...)
	 *						1: Use the first value of the partition as the pivot.
	 *						2: Use the median-of-three value of the partition as the pivot.
	 *						3: Use the last value of the partition as the pivot.
	 *			k:		This is the partition size stopping case (when to initiate insertion sort).
	 */
	private void quicksort(int low, int high, int p, int k) {
			int i = low, j = high;
			int pivot;
			
			// This block identifies the "median-of-three" pivot value.
			int medianofthree = 0;
			if (p == 2) {
				int[] triple = {numbers[low], numbers[low + (high - low)/2], numbers[high]};
				medianofthree = numbers[low + (high - low)/2];
				if 		(triple[0] > triple[1] && triple[0] < triple[2]) { medianofthree = triple[0]; }
				else if (triple[1] > triple[0] && triple[1] < triple[2]) { medianofthree = triple[1]; }
				else if (triple[2] > triple[0] && triple[2] < triple[1]) { medianofthree = triple[2]; }
			}
			
			// Control block for which pivot value to use based on [p].
			switch (p) {
				case 1: pivot = numbers[low]; break;
				case 2: pivot = medianofthree; break;
				case 3: pivot = numbers[high]; break;
				default: pivot = numbers[low + (high - low)/2]; break;
			}
							
			// Control block for when to drop into insertion sort.
			// If a partition size is <= k then perform insertion sort on said partition.
			if (high - low <= k - 1) { insertionsort(low, high); return; }

			// Divide current partition into lists partitions.
			while (i <= j) {
				
					// If the current value from the left list is smaller than the pivot element then get the next element from the left list.
					// Each of these iterations (plus one additional) represents a comparison in the sort.
					while (numbers[i] < pivot) { i++; comparisons++; } comparisons++;
					
					// If the current value from the right list is larger than the pivot element then get the next element from the right list.
					// Each of these iterations (plus one additional) represents a comparison in the sort.
					while (numbers[j] > pivot) { j--; comparisons++; } comparisons++;
					
					/* Once a value in the left list is found which is larger than the pivot,
					 * and a value in the right list is found which is smaller than the pivot,
					 * exchange the two values and continue moving through the list.
					 */
					if (i <= j) {
						if (numbers[i] != numbers[j]) { exchange(i, j); }
						i++;
						j--;
					}
			}
			
			// Recursively quicksort the sub-partitions.
			if (low < j)
				quicksort(low, j, p, k);
			if (i < high)
				quicksort(i, high, p, k);
	}
	
	/* Exchanges two elements in the list.
	 * Accepts indices i and j and exchanges the elements at those indices.
	 * Increments the exchanges value to keep track of total exchanges.
	 */
	private void exchange(int i, int j) {
			int temp = numbers[i];
			numbers[i] = numbers[j];
			numbers[j] = temp;
			exchanges++;
	}
	
	/* Performs an insertion sort on a partition (elements between indices "start" and "end).
	 */
	private void insertionsort(int start, int end) {
		int back = start;
		for (int i = start; i <= back; i++) {
			for (int j = back; j > start; j--) {
				if (numbers[j] < numbers[j - 1]) {
					int temp = numbers[j];
					numbers[j] = numbers[j - 1];
					numbers[j - 1] = temp;
					exchanges++; // Increment exchanges counter.
				}
				else { //Break out of iteration.
					comparisons++;
					break;
				}
				comparisons++; // Increment comparisons counter.
			}
			if (back < end) { back++; }
		}
	}
	
	/* Prints the current state of a list [values] including partition markers and up/down pointer markers.
	 * Inputs:	values:	The list in its current state.
	 *			left:	The left side of the partition.
	 *			right:	The right side of the partition.
	 *			down:	The down pointer element.
	 *			up:		The up pointer element.
	 *
	 * Example: (12D, 25, 33, 37, 48, 57, 86, 92U)
	 */
	public String print(int[] values, int left, int right, int down, int up) {
		String output = "";
		for (int i = 0; i < values.length; i++) {
			if (i == left) { output += "("; }
			output += values[i];
			if (i == down) { output += "D"; }
			if (i == up) { output += "U"; }
			if (i == right) { output += ")"; }
			if (i < values.length - 1) { output += ", "; }
		}
		return output;
	}
	
	/* Prints the current state of a list [values].
	 */
	public String print(int[] values) {
		String output = "";
		for (int i = 0; i < values.length; i++) {
			output += values[i];
			if (i < values.length - 1) { output += ", "; }
		}
		return output;
	}
	
	/* Outputs the statistics of the sort.
	 * Accepts the time it took to perform the sort as input.
	 * The metrics outputted to the console are detailed below.
	 * 		Quicksort Time: [The amount of time (in nanoseconds) it took to perform the sort.]
	 * 		Numbers of items in list: [The number of items in the list.]
	 * 		Pivot: [First Item | Median-of-Three | Last Item]
	 * 		Partition Size Stopping Case: [the k value which represents partition size stopping case.]
	 * 		Exchanges: [Number of exchanges performed during the sort.]
	 * 		Comparisons: [Number of comparisons performed during the sort.]
	 */
	private String metrics(long time) {
		String output = "";
		output += "Quicksort Time: " + time;
		output += "\nNumbers of items in list: " + numbers.length;
		output += "\nPivot: ";
		switch (p) {
			case 1: output += "First Item"; break;
			case 2: output += "Median-of-Three"; break;
			case 3: output += "Last Item"; break;
			default: break;
		}
		output += "\nPartition Size Stopping Case: " + k;
		output += "\nExchanges: " + exchanges;
		output += "\nComparisons: " + comparisons;
		return output;
	}
	
	/* One-stop-shop for different error codes and helpful messages. (Details below)
	 * Codes:	1 - Explains standard usage for accepting unsorted input and generating sorted output.
	 * 			2 - Explains advanced usage for accepting unsorted input and generating sorted output.
	 * 			3 - The [p] value needs to be a 1, 2 or 3 which represents the different pivot possibilites.
	 * 			4 - The [k] value has to be an integer.
	 * 			5 - The [k] value has to be a positive integer.
	 * 			6 - Explains the secondary usage for doing a quicksort on a randomly generated list.
	 * 			7 - When using secondary usage, the [size] parameter has to be a positive integer.
	 * 			8 - If an invalid file is chosen for input, this error will fire.
	 */
	private static void errors(int code) {
		String error = "";
		switch (code) {
			case 1:	error = "Expected Usage: Quicksort [InputFile] [OutputFile]"; break;
			case 2: error = "Expected Usage: Quicksort [p = 1|2|3] [k] [InputFile] [OutputFile]"; break;
			case 3: error = "Error: [p] must be 1, 2 or 3"; break;
			case 4: error = "Error: [k] must be an integer"; break;
			case 5: error = "Error: [k] must be positive integer"; break;
			case 6: error = "Expected Usage: Quicksort [p = 1|2|3] [k] [size]"; break;
			case 7: error = "Error: [size] must be a positive integer"; break;
			case 8: error = "Error: Invalid file"; break;
			default: break;
		}
	}
	
	/* Outputs the help text to the command line.
	 * Can be ran using "Quicksort help" OR "Quicksort -h"
	 */
	public static void help() {
		String output = "";
		
		output += "NAME";
		output += "\n    Quicksort\n";
		output += "\nEXECUTION METHODS";
		output += "\n    (Simple Execution) - Quicksort";
		output += "\n        -Will sort a randomly generated list of size [size=5] using default p=1 and k=2 values.\n";
		output += "\n    (Secondary Execution) - Quicksort [p] [k] [size]";
		output += "\n        -Will sort a randomly generated list of size [size] using user-submitted p and k values.\n";
		output += "\n    (Standard Execution) - Quicksort [inputfile] [outputfile]";
		output += "\n        -Will sort a user-selected input file and generate a sorted output file using default p=1 and k=2 values\n";
		output += "\n    (Advanced Execution) - Quicksort [p] [k] [inputfile] [outputfile]";
		output += "\n        -Will sort a user-selected input file and generate a sorted output file using user-selected p and k values.\n";
		output += "\n    (Help Menu) - Quicksort help OR Quicksort -h";
		output += "\n        -Will print help/readme to console.\n";
		output += "\nDESCRIPTION";
		output += "\n    This program uses a recursive Quicksort (aka Partition Exchange Sort) to sort a list of integers.\n";
		output += "\n    The quicksort will drop into an insertion sort if a partition of size k or less is reached.";
		output += "\n        - the value k defaults to (2) unless specified by the user using the advanced execution mode.\n";
		output += "\n    The quicksort can use three different pivot methods.";
		output += "\n        1 - The first value of the partition is the pivot.";
		output += "\n        2 - The median-of-three value of the partition is the pivot.";
		output += "\n        3 - The last value of the partition is the pivot.\n";
		output += "\n    Following Options are Available:\n";
		output += "\n    [p]";
		output += "\n        This represents the pivot value (unless user-specified, default value is 1)";
		output += "\n        1 - This sets the first item of the partition as the pivot";
		output += "\n        2 - This sets the median-of-three item of the partition as the pivot";
		output += "\n        3 - This sets the last item of the partition as the pivot\n";
		output += "\n    [k]";
		output += "\n        This represents the partition size (k) stopping case.\n";
		output += "\n    [size]";
		output += "\n        This represents the size of the list to be generated. (Used for simple and secondary execution methods)\n";
		output += "\n    [inputfile]";
		output += "\n        This specifies the input file which contains an unsorted list.\n";
		output += "\n    [outputfile]";
		output += "\n        This specifies the name of the output file to be generated which will contain the sorted list.\n";
		output += "\n    help OR -h";
		output += "\n        Will print help/readme to console.\n";
		output += "\nEXAMPLES";
		output += "\n    To perform a quicksort where the first value of the partition is the pivot and the stopping case is k = 2 with a randomly generated list of size 10";
		output += "\n        Quicksort 1 2 10\n";
		output += "\n    To perform a quicksort where the median-of-three value of the partition is the pivot and the stopping case is k = 50 with a randomly generated list of size 50";
		output += "\n        Quicksort 2 50 50\n";
		output += "\n    To perform a quicksort where the last value of the partition is the pivot and the stopping case is k = 100. Uses user-specified input and output files.";
		output += "\n        Quicksort 3 100 [inputfile] [outputfile]\n";
		output += "\n    To perform a quicksort where the first value of the partition is the pivot and the stopping case is k = 2. Uses user-specified input and output files.";
		output += "\n        Quicksort 1 2 [inputfile] [outputfile]\n";
		output += "\nCITATION";
		output += "\n    A bulk of the sort code is thanks to - http://www.vogella.com/tutorials/JavaAlgorithmsQuicksort/article.html\n";
		output += "\n    Lars Vogel (2016) Quicksort (Version 0.7, 04.10.2016) [Computer Program]. Available at http://www.vogella.com/tutorials/JavaAlgorithmsQuicksort/article.html (Accessed at 30 April 2017)\n";
		output += "\nBUGS";
		output += "\n    N/A";
		
		System.out.println(output);
	}
	
	/* This is the main driver for the quicksort application.
	 *
	 * General flow...
	 *	1. Initialize Quicksort instance.
	 *	2. Perform command line input validation/ingestion.
	 *	3. Crawl input and build unsorted list array.
	 *	4. Perform quicksort on ingested list using user-specified conditions.
	 *	5. Write output to file (or console).
	 *	6. Print metrics/statistics to console.
	 */
	public static void main(String[] args) throws IOException {
		Quicksort sorter = new Quicksort();
		String cache = "";
		boolean go = true;
		
		//--------------------------------------------------------------------
		//This block performs input validation / ingestion and error generation.
		int loc = 0;
		try {
			if (args.length == 0) { // Will run quicksort on a randomly generated list of size [size] using default p and k.
				sorter.setUp();
			}
			else if (args[0].toLowerCase().equals("help") || args[0].toLowerCase().equals("-h")) {
				help(); return; // Looks for help command via console.
			}
			else if (args.length == 4 || args.length == 3) {
				try {
					p = Integer.parseInt(args[0]);
					k = Integer.parseInt(args[1]);
				} catch (NumberFormatException nfe) {
					System.out.println("Error: Consult help menu - \"Quicksort help\"");
					return;
				}
				loc = 1;
				if (Integer.parseInt(args[0]) != 1 && Integer.parseInt(args[0]) != 2 && Integer.parseInt(args[0]) != 3) {
					if (args.length == 4) { errors(3); errors(2); return; }
					if (args.length == 3) { errors(3); errors(6); return; }
				}
				loc = 2;
				if (Integer.parseInt(args[1]) < 0) {
					errors(5); if (args.length == 3) { errors(6); } else { errors(2); }
					return;
				}
				if (args.length == 3) {
					try {
						size = Integer.parseInt(args[2]);
					}
					catch (NumberFormatException nfe) {
						System.out.println("Error: Consult help menu - \"Quicksort help\"");
						return;
					}
					if (size > 0) { sorter.setUp(); }
					else { errors(7); errors(6); return; }
				}
			}
			else if (args.length != 2) {
				errors(1); errors(2); errors(6); return;
			}
		} catch (NumberFormatException e) {
			if (loc == 1 && args.length == 4) { errors(3); errors(2); }
			else if (loc == 2 && args.length == 4) { errors(4); errors(2); }
			else if (loc == 1 && args.length == 3) { errors(3); errors(6); }
			else if (loc == 2 && args.length == 3) { errors(4); errors(6); }
			return;
		}
		//--------------------------------------------------------------------
		try {
		if (args.length == 2 || args.length == 4) {
			int outval = args.length - 1;
			File fout = new File(args[outval]); // Creates the output file with the name specified via the command line.
			FileOutputStream fos = new FileOutputStream(fout);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			
			// Ingests input file and puts integers into a String file.
			try (BufferedReader br = new BufferedReader(new FileReader(args[outval - 1]))) {
				String line = "";
				while((line = br.readLine())!= null) {
					cache += line + ";";
				}
			} catch (FileNotFoundException e) {
				errors(8);
			}
			
			String[] strlist = cache.split(";"); // Splits input integer Strings into an array.
			int[] list = new int[strlist.length];
			for (int i = 0; i < strlist.length; i++) {
				list[i] = Integer.parseInt(strlist[i]); // Creates integer array from String integer array.
			}
						
			long startTime = System.nanoTime();
			sorter.sort(list, p, k); // Sorts list using quicksort.
			long stopTime = System.nanoTime();
			long elapsedTime = stopTime - startTime;
			
			bw.write(sorter.metrics(elapsedTime) + "\nSorted List:\n"); // Print Metrics
			
			// Writes sorted list to output file.
			for (int i = 0; i < sorter.numbers.length; i++) {
				bw.write(Integer.toString(sorter.numbers[i]));
				if (i < sorter.numbers.length - 1) { bw.newLine(); }
			}
			
			System.out.println("\n" + sorter.metrics(elapsedTime)); // Print metrics.
			
			go = false;
			bw.close();
		}
		} catch (NumberFormatException nfe) {
			System.out.println("Error: Input file not found.");
			return;
		}
		
		// This block drives the quicksort if secondary execution method is used.
		if (go) {
			System.out.println("Original List: " + sorter.print(sorter.numbers));
			long startTime = System.nanoTime();
			sorter.sort(sorter.numbers, p, k);
			long stopTime = System.nanoTime();
			long elapsedTime = stopTime - startTime;
			System.out.println("Sorted List: " + sorter.print(sorter.numbers));
			System.out.println(sorter.metrics(elapsedTime));
		}
	}
}