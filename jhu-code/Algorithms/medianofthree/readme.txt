Algorithms EN.605.421.84.FA17 - Programming Assignment 2
Michael Sass

Assignment:

	1. Given an array, a[i], . . . , a[j], with j − i ≥ 2, let k = ⌊(i + j)/2⌋ and choose as the partition 
	element for Quicksort the median among a[i], a[j], a[k] (i.e., the value that would be in the middle if 
	a[i], a[j], and a[k] were sorted). This is called median-of-three partitioning.
		(a) [15 points] Write pseudocode for median-of-three partitioning.
		(b) [15 points] What is the running time of median-of-three partitioning? Justify your answer.
		(c) [20 points] What is the running time of Quicksort if you use median-of-three partitioning on an 
		input set that is already sorted? Justify your answer.
		(d) [50 points] Implement Quicksort using a normal pivot process and the median-of-three process 
		described above. Test your run time analysis of medium-of-three, and then compare the average and
		worst case run times of Quicksort with the two pivot processes. Note that you must implement all 
		of these algorithms from scratch. Also remember that CPU time is not a valid measure for testing 
		run time. You must use something such as the number of comparisons.

		
Execution:
		-To use, reference "Usage" section below...
		-Compiling can be done using commandline...
			javac MedianOfThree.java
		-JDK v1.8.0_121

		
Usage: MedianOfThree [inputfile] [outputfile] [type=1|2|3]
	
	-the [inputfile] is just a comma separated list of integers on a single line
	-an example input file is shown below... (with no beginning or trailing blank spaces)
	
		17,35,42,33,8,12,18,88,76,5,19,43,56,22,355,77,2,4,15,30
		
	-input file can not accept/handle blank lines.
	-output file can be any unique name.
	-the "type parameter" can take the following values...
		1: If a one is provided, the quicksort will use a "first value" pivot
		2: If a two is provided, the quicksort will use a "median-of-three" pivot
		3: If a three is provided, the quicksort will use a "last value" pivot
		*any other integer: For any other provided integer value, a "median" pivot will be used.

	
Implementation Notes:
	
	-Notepad++ was used as the code editor (not Eclipse), compilation and execution instructions are included in this readme.
	-Pivot selection methods implemented include "first value", "last value", "median-of-three" and "median value".
	-Robust error checking/handling is not implemented. Follow this readme and there should be no usage issues!
	-Sample Inputs/Outputs are stored in the IO folder.
	
	
Output: (Example Output)
	
	Input Array: {17 35 42 33 8 12 18 88 76 5 19 43 56 22 355 77 2 4 15 30}

	Sorted Output: {2 4 5 8 12 15 17 18 19 22 30 33 35 42 43 56 76 77 88 355}

	Comparisons: 116
	Exchanges: 21
	Pivot Selection Method Used: Median-of-Three Value
	