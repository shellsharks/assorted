/* Algorithms EN.605.421.84.FA17 - Programming Assignment 1
 * Michael Sass (10/2/2017)
 *
 * Assignment:
 *
 *	Consider the following implementation of a set. We use a pair of arrays where one array is longer than the other, 
 *	and both arrays are kept sorted. The length of the shorter array is the square root of the length of the longer array; 
 *	although, it may have empty entries if it is not yet full. To add an element, you use insertion sort to insert the 
 *	element into the shorter array. If the short array fills up, re-allocate the shorter and longer arrays and merge 
 *	the two old arrays into the new longer array. The new short array is empty. Then, to find an element, use binary 
 *	search on both the long and the short arrays.
 *	
 */

import java.io.*;
import java.util.*;

public class twoarray {
	
	int[] longarray;	//stores the longer array (of size n)
	int[] shortarray;	//stores the shorter array (of size sqrt(n))
	int[] mergedarray;	//placeholder for the space consumed when merging the long array and short array
	int ctr;			//increment counter for keeping track of how many items are in the short array
	static int EMPTYNUM = 99999999;	//special value which functions as the "EMPTY" value in the integer arrays.
	//int search; //used for efficiency analysis of searches
	//int add; //used for efficiency analysis of inserts
	
	/* This function configures the arrays.
	 * As input, initiateArrays accepts an array which will be used as the long array.
	 * The short array space is allocated based on a sqrt calcuation with the long array length.
	 * The short array and merged array are allocated space and initialized with all "EMPTY" values.
	 */
	private void initiateArrays(int[] array) {
		longarray = array;
		shortarray = new int[(int)Math.ceil(Math.sqrt(longarray.length))];
		mergedarray = new int[(longarray.length + shortarray.length)];
		Arrays.fill(shortarray, EMPTYNUM);
		Arrays.fill(mergedarray, EMPTYNUM);
		ctr = 0; //when new arrays are initiated, counter is reset to 0
	}
	
	/* This is the insert function which inserts an element into the short array.
	 * Accepts an element (integer) as input.
	 * This function drives the insert and passes control to the sort function after each insert.
	 * If the short array fills up as a result of the insert, the merge function is called.
	 */
	private void insert(int element) {
		//add = 0; //used for efficiency analysis of inserts
		shortarray[ctr++] = element;
		insertionsort(shortarray, 0, ctr - 1);
		if (ctr == shortarray.length) { initiateArrays(merge(shortarray, longarray, shortarray.length, longarray.length, mergedarray)); }
	}
	
	/* Performs an insertion sort on the array which is passed to the function.
	 * Accepts an unsorted* array as input as well as the start and end indexes to perform the sort on.
	 * Has no return values.
	 */
	private void insertionsort(int[] array, int start, int end) {
		int back = start;
		for (int i = start; i <= back; i++) {
			for (int j = back; j > start; j--) {
				if (array[j] < array[j - 1]) {
					int temp = array[j];
					array[j] = array[j - 1];
					array[j - 1] = temp;
				}
				else { //Break out of iteration.
					//add++; //used for efficiency analysis of inserts
					break;
				}
				//add++; //used for efficiency analysis of inserts
			}
			if (back < end) { back++; }
		}
	}
	
	/* Merges two sorted arrays into one larger, sorted array.
	 * Accepts two sorted arrays as input as well as space for a third array.
	 * Also accepts two size values for the two arrays to be merged.
	 * Returns the merged array.
	 * Reference: http://www.geeksforgeeks.org/merge-two-sorted-arrays/
	 */
	int[] merge(int[] arr1, int[] arr2, int n1, int n2, int[] arr3) {
		System.out.println("Small array full! Merging small array into large array...");
		int i = 0, j = 0, k = 0;
	 
		// Traverse both shortarray and longarray
		while (i<n1 && j <n2) {
			// Check if current element of shortarray is smaller than current element of longarray.
			// If yes, store shortarray element and increment shortarray index. Otherwise do same with longarray.
			if (arr1[i] < arr2[j])
				arr3[k++] = arr1[i++];
			else
				arr3[k++] = arr2[j++];
		}
	 
		// Store remaining elements of shortarray
		while (i < n1)
			arr3[k++] = arr1[i++];
	 
		// Store remaining elements of longarray
		while (j < n2)
			arr3[k++] = arr2[j++];
		
		return arr3; //returns mergedarrasy.
	}
	
	/* binarySearch function will find and return index of a search term in an array. (returns -1 if not found)
	 * As input, the function accepts the array to search, beginning and end indexes as search bounds and the element to search for
	 * Reference: http://www.geeksforgeeks.org/binary-search/
	 */
	int binarySearch(int[] array, int l, int r, int element) {
        if (r>=l) {
            int mid = l + (r - l)/2;
 
            // If the element is present at the middle itself
            if (array[mid] == element) {
				// search++; //used for efficiency analysis of searches
				return mid;
			}
 
            // If element is smaller than mid, then it can only be present in left subarray
            if (array[mid] > element) {
				//search++; //used for efficiency analysis of searches
				return binarySearch(array, l, mid-1, element);
			}
 
            // Else the element can only be present in right subarray
            return binarySearch(array, mid+1, r, element);
        }
 
        // We reach here when element is not present in array
        return -1;
    }
	
	/* This function drives the searching of both the short and long arrays.
	 * As input, this function accepts the element to search for.
	 * As output, this function will output a string detailing if the element was found and where.
	 */
	String searchBoth(int element) {
		//search = 0; //used for efficiency analysis of searches
		int num = binarySearch(shortarray, 0, shortarray.length-1, element); //search short array
		if (num != -1) {
			return element + " found in the short array at index " + num + ".";
		}
		num = binarySearch(longarray, 0, longarray.length-1, element); //search long array
		if (num != -1) {
			return element + " found in the long array at index " + num + ".";
		}
		return element + " not found in either array.";
	}
	
	/* Utility function which prints the state of an array to console.
	 *
	 */
	private String printArray(int[] array) {
		String output = "";
		for (int i = 0; i<array.length-1; i++) {
			if (array[i] == EMPTYNUM) { output+="E "; }
			else { output += array[i] + " "; }
		}
		if (array[array.length-1] == EMPTYNUM) { output+="E"; }
		else { output += array[array.length-1]; }
		return output;
	}
	
	public static void main(String[] args) throws IOException {
		
		if (args.length != 2) { //check to see if proper arguments are made.
			System.out.println("Usage: twoarray [inputfile] [outputfile");
		}
		else {
		
			twoarray t = new twoarray(); //implement new instance of the twoarray class
			
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
			
			//This block ingests the input into the initial array items, insert items and search items.
			String[] inputs = cache.split(";inserts;")[0].split(";");
			String[] inserts = cache.split(";inserts;")[1].split(";searches;")[0].split(";");
			String[] searches = cache.split(";searches;")[1].split(";");
			//---
			
			int[] initial_array = new int[inputs.length];
			
			for (int i = 0; i < inputs.length; i++) {
				initial_array[i] = Integer.parseInt(inputs[i]); //load values into the initial long array
			}
			
			//###OUTPUT SECTION### (Outputting both to console and to file.)
			System.out.println("\nArray loaded: {" + t.printArray(initial_array) + "}");
			bw.write("\nArray loaded: {" + t.printArray(initial_array) + "}\n");
			
			System.out.println("Sorting loaded array...\n");
			bw.write("Sorting loaded array...\n\n");
			t.insertionsort(initial_array, 0, initial_array.length - 1); //sorts initial loaded array
			t.initiateArrays(initial_array);
			
			System.out.println("###Initial Array States###");
			bw.write("###Initial Array States###\n");
			System.out.println("Long Array: {" + t.printArray(t.longarray) + "}");
			bw.write("Long Array: {" + t.printArray(t.longarray) + "}\n");
			System.out.println("Short Array: {" + t.printArray(t.shortarray) + "}\n");
			bw.write("Short Array: {" + t.printArray(t.shortarray) + "}\n\n");
			System.out.println("\n###Insert Phase###\n");
			bw.write("\n###Insert Phase###\n\n");
			
			for (int i = 0; i < inserts.length; i++) {
				System.out.println("Inserting " + inserts[i] + " into short array...");
				bw.write("Inserting " + inserts[i] + " into short array...\n");
				t.insert(Integer.parseInt(inserts[i])); //inserts all the insert items
				System.out.println("Long Array: {" + t.printArray(t.longarray) + "}");
				bw.write("Long Array: {" + t.printArray(t.longarray) + "}\n");
				System.out.println("Short Array: {" + t.printArray(t.shortarray) + "}\n");
				bw.write("Short Array: {" + t.printArray(t.shortarray) + "}\n\n");
			}
			
			System.out.println("\n###Let's Search!###\n");
			bw.write("\n###Let's Search!###\n\n");
			
			for (int i = 0; i < searches.length; i++) {
				System.out.println("Searching for " + searches[i] + "...");
				bw.write("Searching for " + searches[i] + "...\n");
				System.out.println(t.searchBoth(Integer.parseInt(searches[i])) + "\n"); //searches for items
				bw.write(t.searchBoth(Integer.parseInt(searches[i])) + "\n\n");
			}
			
			//###END OUTPUT SECTION###
			
			bw.close();
		
		}
	
	}
	
}