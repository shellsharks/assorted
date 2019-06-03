/* Michael Sass - Data Structures 605.202 - Lab 4
 * Sort Comparison (Heapsort)
 *
 * Base heapsort code from - http://quiz.geeksforgeeks.org/heap-sort/
 *
 * HeapSort [Computer Program]. Available at http://quiz.geeksforgeeks.org/heap-sort/ (Accessed 30 April 2017)
 *
 * This program uses a recursive heap sort to sort a list of integers.
 * Heap Sort based on the concept of a heap. It has two phases:
 *	1 - Build the heap
 *	2 - Extract the elements in sorted order from the heap.
 *
 * To execute, run the following in the console...
 *		Heapsort [inputfile] [outputfile]
 */
 
import java.io.*;
 
public class Heapsort
{
	/* This function drives the two-part heap sort.
	 * 		First the heap is built.
	 *		Second, the elements in the heap are extracted in order.
	 */
    public void sort(int arr[])
    {
        int n = arr.length;
 
        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);
 
        // One by one extract an element from heap
        for (int i=n-1; i>=0; i--)
        {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
 
            // Call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }
 
	/* Recursively "heapifies" a subtree.
	 * Uses the i-node as the root and n as the size of the heap.
	 */
    void heapify(int arr[], int n, int i)
    {
        int largest = i;  // Initialize largest as root
        int l = 2*i + 1;  // left = 2*i + 1
        int r = 2*i + 2;  // right = 2*i + 2
 
        // If left child is larger than root
        if (l < n && arr[l] > arr[largest])
            largest = l;
 
        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest])
            largest = r;
 
        // If largest is not root
        if (largest != i)
        {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
 
            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }
 
	/* Prints the elements of the list array in a comma separated list. */
    static void printArray(int arr[])
    {
		String output = "";
        for (int i = 0; i < arr.length; ++i) {
            output += arr[i];
			if (i < arr.length - 1) { output += ", "; }
		}
        System.out.println(output);
    }
	
	/*
	 *
	 */
	public static void help() {
		String output = "";
		
		output += "NAME";
		output += "\n    Heapsort\n";
		output += "\nEXECUTION METHODS";
		output += "\n    Heapsort [inputfile] [outputfile]";
		output += "\n        -This executes the heapsort on the user-specified input file and generates a sorted list in the output file.\n";
		output += "\n    Heapsort help OR Heapsort -h";
		output += "\n        -Will print help/readme to console.\n";
		output += "\nDESCRIPTION";
		output += "\n    This program uses a recursive heap sort to sort a list of integers.";
		output += "\n    Heap Sort based on the concept of a heap. It has two phases:";
		output += "\n        1 - Build the heap";
		output += "\n        2 - Extract the elements in sorted order from the heap.\n";
		output += "\n    Following Options are Available:\n";
		output += "\n    [inputfile]";
		output += "\n        This specifies the input file which contains an unsorted list.\n";
		output += "\n    [outputfile]";
		output += "\n        This specifies the name of the output file to be generated which will contain the sorted list.\n";
		output += "\n    help OR -h";
		output += "\n        Will print help/readme to console.\n";
		output += "\nCITATION";
		output += "\n    The sort code has been sourced from - http://quiz.geeksforgeeks.org/heap-sort/\n";
		output += "\n    HeapSort [Computer Program]. Available at http://quiz.geeksforgeeks.org/heap-sort/ (Accessed 30 April 2017)\n";
		output += "\nBUGS";
		output += "\n    N/A";
		
		System.out.println(output);
	}
 
	/* Main driver program.
	 *
	 * General Flow...
	 *	1. Console input validation.
	 *	2. Initialize Heapsort class instance.
	 *	3. Create output file using user-specified name.
	 *	4. Ingest user-specified input-file and generate unsorted list array.
	 *	5. Exceute heapsort on unsorted list array.
	 *	6. Parse list array and write sorted list to output file.
	 *	7. Print sorted list and sort metrics/statistics to console.
	 */
    public static void main(String args[]) throws IOException {
        
		// Console input validation.
		try {
			if (args.length == 1) {
				if (args[0].toLowerCase().equals("help") || args[0].toLowerCase().equals("-h")) {
					help(); return; // Looks for help command via console.
				}
				else {
					System.out.println("Expected usage: Heapsort [inputfile] [outputfile]");
					System.out.println("Program readme: Heapsort -h OR Heapsort help");
					return;
				}
			}
			else if (args.length != 2) {
				System.out.println("Expected usage: Heapsort [inputfile] [outputfile]");
				System.out.println("Program readme: Heapsort -h OR Heapsort help");
				return;
			}
		} catch (NumberFormatException e) {}
		
		Heapsort ob = new Heapsort(); // Initialize Heapsort class instance.
        
		File fout = new File(args[1]); // Create output file using user-specified name.
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		// Ingest user-specified input-file and generate unsorted list array.
		String cache = "";
		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
			String line = "";
			while((line = br.readLine())!= null) {
				cache += line + ";";
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error: Input file not found");
			return;
		}
		String[] strlist = cache.split(";"); // Splits input integer Strings into an array.
		int[] arr = new int[strlist.length];
		for (int i = 0; i < strlist.length; i++) {
			arr[i] = Integer.parseInt(strlist[i]); // Creates integer array from String integer array.
		}
				
		long startTime = System.nanoTime();
		ob.sort(arr); // Exceute heapsort on unsorted list array.
		long stopTime = System.nanoTime();
		long elapsedTime = stopTime - startTime;
		
		bw.write("Heapsort Time: " + elapsedTime + "\nNumber of items in list: " + arr.length + "\nSorted List:\n");
		
		// Parse list array and write sorted list to output file.
		for (int i = 0; i < arr.length; i++) {
			bw.write(Integer.toString(arr[i]));
			if (i < arr.length - 1) { bw.newLine(); }
		}
		
		bw.close();
 
		// Print sorted list and sort metrics/statistics to console.
		System.out.println("\nHeapsort Time: " + elapsedTime + "\nNumber of items in list: " + arr.length);
    }
}