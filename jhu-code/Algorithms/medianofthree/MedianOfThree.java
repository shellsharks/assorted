/* Algorithms EN.605.421.84.FA17 - Programming Assignment 2
 * Michael Sass (10/2/2017)
 *
 * Assignment:
 *
 * Given an array, a[i], . . . , a[j], with j − i ≥ 2, let k = ⌊(i + j)/2⌋ and choose as 
 * the partition element for Quicksort the median among a[i], a[j], a[k] (i.e., the value 
 * that would be in the middle if a[i], a[j], and a[k] were sorted). This is called 
 * median-of-three partitioning. (NOTE: There are several descriptions of the median-of-three 
 * method on the web.
 */
 
 /* Questions
  *
  * What is Quicksort "Normal" Pivot Process
  * Compare average and worst case run times of QuickSort with the two pivot processes?
  */

import java.io.*;

public class MedianOfThree {
	
	int[] arr;
	int exchanges = 0;
	int comparisons = 0;
	
	void initiate(int[] array) {
		arr = array;
	}
	
	/* Performs the quicksort recursively on a given partition (index low through index high) of a list.
	 * 
	 * Inputs:	low:	This is the index of the first item in the partition.
	 * 			high:	This is the index of the last item in the partition.
	 *			p: 		This is the pivot value. (Codes below...)
	 *						1: Use the first value of the partition as the pivot.
	 *						2: Use the median-of-three value of the partition as the pivot.
	 *						3: Use the last value of the partition as the pivot.
	 *						default: If any other value is chosen for "p", the median value will be used.
	 */
	private void quicksort(int low, int high, int p) {
			int i = low, j = high;
			int pivot;
			
			// This block identifies the "median-of-three" pivot value.
			int medianofthree = 0;
			if (p == 2) {
				int[] triple = {arr[low], arr[low + (high - low)/2], arr[high]};
				medianofthree = arr[low + (high - low)/2];
				if 		(triple[0] > triple[1] && triple[0] < triple[2]) { medianofthree = triple[0]; }
				else if (triple[1] > triple[0] && triple[1] < triple[2]) { medianofthree = triple[1]; }
				else if (triple[2] > triple[0] && triple[2] < triple[1]) { medianofthree = triple[2]; }
			}
			
			// Control block for which pivot value to use based on [p].
			switch (p) {
				case 1: pivot = arr[low]; break;
				case 2: pivot = medianofthree; break;
				case 3: pivot = arr[high]; break;
				default: pivot = arr[low + (high - low)/2]; break;
			}

			// Divide current partition into lists partitions.
			while (i <= j) {
				
				// If the current value from the left list is smaller than the pivot element then get the next element from the left list.
				// Each of these iterations (plus one additional) represents a comparison in the sort.
				while (arr[i] < pivot) { i++; comparisons++; } comparisons++; 
				
				// If the current value from the right list is larger than the pivot element then get the next element from the right list.
				// Each of these iterations (plus one additional) represents a comparison in the sort.
				while (arr[j] > pivot) { j--; comparisons++; } comparisons++;
				
				/* Once a value in the left list is found which is larger than the pivot,
				 * and a value in the right list is found which is smaller than the pivot,
				 * exchange the two values and continue moving through the list.
				 */
				if (i <= j) {
					if (arr[i] != arr[j]) { exchange(i, j); }
					i++;
					j--;
				}
			}
			
			// Recursively quicksort the sub-partitions.
			if (low < j) {
				quicksort(low, j, p); }
			if (i < high) {
				quicksort(i, high, p); }
	}
	
	/* Exchanges two elements in the list.
	 * Accepts indices i and j and exchanges the elements at those indices.
	 * Increments the exchanges value to keep track of total exchanges.
	 */
	private void exchange(int i, int j) {
			int temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
			exchanges++;
	}
	
	// This method is used to print a section of the array from the low index to the high index.
	String printArray(int low,int high) {
		String output = "{";
		for (int i = low; i < high; i++) {
			output += arr[i] + " ";
		}
		output += arr[high] + "}";
		return output;
	}
	
	// This method prints the current state of the array.
	String printArrayTotal() {
		String output = "{";
		for (int i = 0; i < arr.length - 1; i++) {
			output += arr[i] + " ";
		}
		output += arr[arr.length - 1] + "}";
		return output;
	}
	
	//Returns the name of the pivot selection method used.
	String returnMethod(int method) {
		String output = "";
		switch (method) {
			case 1: output = "First Value"; break;
			case 2: output = "Median-of-Three Value"; break;
			case 3: output = "Last Value"; break;
			default: output = "Middle Value"; break;
		}
		return output;
	}
	
	public static void main(String[] args) throws IOException {
		
		if (args.length != 3) { //check to see if proper arguments are made.
			System.out.println("Usage: MedianOfThree [inputfile] [outputfile] [type=1|2|3]\n\t1:low\n\t2:median-of-three\n\t3:high");
			System.exit(0);
		}
		
		File fout = new File(args[1]); // Create output file using user-specified name.
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			
		String cache = "";
		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
			String line = "";
			while((line = br.readLine())!= null) {
				cache += line + ";";
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Error: Input file not found");
			return;
		}
				
		MedianOfThree m = new MedianOfThree();
		
		String[] input = cache.split(";")[0].split(",");
		
		int[] inputs = new int[input.length];
			
		for (int i = 0; i < input.length; i++) {
			inputs[i] = Integer.parseInt(input[i]); //load values into the initial long array
		}
		
		m.initiate(inputs);
		
		System.out.println("\nInput Array: " + m.printArrayTotal() + "\n");
		bw.write("Input Array: " + m.printArrayTotal() + "\n\n");
		m.quicksort(0, m.arr.length-1, Integer.parseInt(args[2]));
		System.out.println("Sorted Output: " + m.printArrayTotal());
		System.out.println("\nComparisons: " + m.comparisons + "\nExchanges: " + m.exchanges + "\nPivot Selection Method Used: " + m.returnMethod(Integer.parseInt(args[2])));
		bw.write("Sorted Output: " + m.printArrayTotal());
		bw.write("\n\nComparisons: " + m.comparisons + "\nExchanges: " + m.exchanges + "\nPivot Selection Method Used: " + m.returnMethod(Integer.parseInt(args[2])));
		bw.close();
		
	}
	
}