Algorithms EN.605.421.84.FA17 - Programming Assignment 1
Michael Sass

Assignment:

	Consider the following implementation of a set. We use a pair of arrays where one array is longer than the other, 
	and both arrays are kept sorted. The length of the shorter array is the square root of the length of the longer array; 
	although, it may have empty entries if it is not yet full. To add an element, you use insertion sort to insert the 
	element into the shorter array. If the short array fills up, re-allocate the shorter and longer arrays and merge 
	the two old arrays into the new longer array. The new short array is empty. Then, to find an element, use binary 
	search on both the long and the short arrays.

Execution:
		-To use, reference "Usage" section below...
		-Compiling can be done using commandline...
			javac twoarray.java
		-JDK v1.8.0_121

Usage: twoarray [inputfile] [outputfile]
	
	-the [inputfile] must adhere to the following format
	
		[list of numbers separated by new lines]
		inserts
		[list of numbers to be inserted separated by new lines]
		searches
		[list of numbers to be searched for separated by new lines]
		
	-an example input file is shown below... (with no beginning or trailing blank spaces)
	
		0
		5
		12
		7
		2
		4
		-6
		55
		28
		inserts
		3
		2
		4
		searches
		4
		55
		888
		
	-input file can not accept/handle blank lines.
	-input file MUST specify atleast one insert and one search.
	-output file can be any unique name.

Implementation Notes:
	
	-the long array is referred to as "longarray", "long array" or "longer array"
	-the short array is referred to as "shortarray", "short array" or "shorter array"
	-length of the shorter array is determined by the square root of the longer array. If the square root
		of the longer array is not an integer, the value for the short array length is the value of the square
		root of the longer array rounded up.
	-Empty entries in the short array are denoted as an "E" in the output. A special integer value (99999999) is
		used in the short array to represent an empty placeholder.
	-Modified insertion sort is used to insert new elements into the short array. This modified sort
		truncates the total length of the short array by not counting empty values. For example, the array
		{1, 2, E, E} is not a 4 length array but rather a 2 length. The insertion sort will not iterate over
		the empty nodes.
	-Given a short array of length 4, the 4th element (index 3) will never contain a searchable value.
		This is because when the short array fills up, the directions are to merge it into the larger array.
	-Merging the small array and the large array is done using a merge sort similar to this reference
		(http://www.geeksforgeeks.org/merge-two-sorted-arrays/)
	-Searches are perfored with binary search where the short array is searched first before long array.
		(Reference for binary search: http://www.geeksforgeeks.org/binary-search/)
		
	-Robust error checking/handling is not implemented. Follow this readme and there should be no usage issues!
	
	
Output: (Example Output)
	
	Array loaded: {0 5 12 7}
	Sorting loaded array...

	###Initial Array States###
	Long Array: {0 5 7 12}
	Short Array: {E E}


	###Insert Phase###

	Inserting 5 into short array...
	Long Array: {0 5 7 12}
	Short Array: {5 E}

	Inserting -8 into short array...
	Small array full! Merging small array into large array...
	Long Array: {-8 0 5 5 7 12}
	Short Array: {E E E}

	Inserting 7 into short array...
	Long Array: {-8 0 5 5 7 12}
	Short Array: {7 E E}


	###Let's Search!###

	Searching for 5...
	5 found in the long array at index 2.

	Searching for 7...
	7 found in the short array at index 0.

	Searching for 15...
	15 not found in either array.
	