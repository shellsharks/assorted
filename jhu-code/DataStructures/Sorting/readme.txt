README

INFO
	Author: Michael Sass
	Class: Data Structions 605.202
	Assignment: Lab 4 - Sort Comparison
	
DETAILS
	This readme details two distinct recursive sorting algorithms (Quicksort and Heap Sort).
	
	A Quicksort (or Partition Exchange Sort) divides the data into 2 partitions separated by a pivot.
	The first partition contains all the items which are smaller than the pivot.
	The remaining items are in the other partition.
	
	There are four versions of Quicksort used for this assignment, these are listed below...
		(1) Select the first item of the partition as the pivot. Treat a partition of size one and two as a stopping case.
		(2) Select the first item of the partition as the pivot. Process down to a stopping case of a partition of size k = 100. Use an insertion sort to finish.
		(3) Select the first item of the partition as the pivot. Process down to a stopping case of a partition of size k = 50. Usean insertion sort to finish.
		(4) Select the median-of-three as the pivot. Treat a partition of size one and two as a stopping case.
	
	---
	
	A Heap Sort is based on the concept of a heap. It has two phases:
		1. Build the heap
		2. Extract the elements in sorted order from the heap.
		
	This program accesses the system clock to time values for the sort execution
	
CITATION (Code used from following sources) (Full citations in the individual readmes below)
	For Quicksort: http://www.vogella.com/tutorials/JavaAlgorithmsQuicksort/article.html
	For Heapsort: http://quiz.geeksforgeeks.org/heap-sort/
	
CONDUCTING TESTS
	There are 5 total sorts that will be compared for this assignment. To run them, use the commands below.
	
	(1) - Quicksort 1 2 [inputfile] [outputfile]	- Performs a quicksort with the first item as the pivot and a stopping case of k <= 2
	(2) - Quicksort 1 100 [inputfile] [outputfile]	- Performs a quicksort with the first item as the pivot and a stopping case of k <= 100
	(3) - Quicksort 1 50 [inputfile] [outputfile]	- Performs a quicksort with the first item as the pivot and a stopping case of k <= 50
	(4) - Quicksort 2 2 [inputfile] [outputfile]	- Performs a quicksort with the median-of-three item as the pivot and a stopping case of k <= 2
	(5) - Heapsort [inputfile] [outputfile]			- Performs a heap sort

--------------------------------------------------------------------------------------------------------------------

NAME
	Quicksort

EXECUTION METHODS
	(Simple Execution) - Quicksort
		-Will sort a randomly generated list of size [size=5] using default p=1 and k=2 values.
								
	(Secondary Execution) - Quicksort [p] [k] [size]
		-Will sort a randomly generated list of size [size] using user-submitted p and k values.
								
	(Standard Execution) - Quicksort [inputfile] [outputfile]
		-Will sort a user-selected input file and generate a sorted output file using default p=1 and k=2 values 
								
	(Advanced Execution) - Quicksort [p] [k] [inputfile] [outputfile]
		-Will sort a user-selected input file and generate a sorted output file using user-selected p and k values.
		
	(Help Menu) - Quicksort help OR Quicksort -h
		-Will print help/readme to console.

DESCRIPTION
	This program uses a recursive Quicksort (aka Partition Exchange Sort) to sort a list of integers.
	
	The quicksort will drop into an insertion sort if a partition of size k or less is reached.
		- the value k defaults to (2) unless specified by the user using the advanced execution mode.
		
	The quicksort can use three different pivot methods.
		1 - The first value of the partition is the pivot.
		2 - The median-of-three value of the partition is the pivot.
		3 - The last value of the partition is the pivot.
	
	Following Options are Available:
	
	[p]
		This represents the pivot value (unless user-specified, default value is 1)
		1 - This sets the first item of the partition as the pivot
		2 - This sets the median-of-three item of the partition as the pivot
		3 - This sets the last item of the partition as the pivot
	
	[k]
		This represents the partition size (k) stopping case.
	
	[size]
		This represents the size of the list to be generated. (Used for simple and secondary execution methods)
	
	[inputfile]
		This specifies the input file which contains an unsorted list.
	
	[outputfile]
		This specifies the name of the output file to be generated which will contain the sorted list.
		
	help OR -h
		Will print help/readme to console.
	
EXAMPLES
	To perform a quicksort where the first value of the partition is the pivot and the stopping case is k = 2 with a randomly generated list of size 10
		Quicksort 1 2 10
	
	To perform a quicksort where the median-of-three value of the partition is the pivot and the stopping case is k = 50 with a randomly generated list of size 50
		Quicksort 2 50 50
	
	To perform a quicksort where the last value of the partition is the pivot and the stopping case is k = 100. Uses user-specified input and output files.
		Quicksort 3 100 [inputfile] [outputfile]
	
	To perform a quicksort where the first value of the partition is the pivot and the stopping case is k = 2. Uses user-specified input and output files.
		Quicksort 1 2 [inputfile] [outputfile]
		
CITATION
	A bulk of the sort code is thanks to - http://www.vogella.com/tutorials/JavaAlgorithmsQuicksort/article.html
	
	Lars Vogel (2016) Quicksort (Version 0.7, 04.10.2016) [Computer Program]. Available at
	http://www.vogella.com/tutorials/JavaAlgorithmsQuicksort/article.html (Accessed at 30 April 2017)

BUGS
	N/A

--------------------------------------------------------------------------------------------------------------------

NAME
	Heapsort

EXECUTION METHODS
	Heapsort [inputfile] [outputfile]
		-This executes the heapsort on the user-specified input file and generates a sorted list in the output file.
	
	Heapsort help OR Heapsort -h
		-Will print help/readme to console.

DESCRIPTION
	This program uses a recursive heap sort to sort a list of integers.
	Heap Sort based on the concept of a heap. It has two phases:
		1 - Build the heap
		2 - Extract the elements in sorted order from the heap.
	
	Following Options are Available:
	
	[inputfile]
		This specifies the input file which contains an unsorted list.
	
	[outputfile]
		This specifies the name of the output file to be generated which will contain the sorted list.
		
	help OR -h
		Will print help/readme to console.
		
CITATION
	The sort code has been sourced from - http://quiz.geeksforgeeks.org/heap-sort/
	
	HeapSort [Computer Program]. Available at http://quiz.geeksforgeeks.org/heap-sort/ (Accessed 30 April 2017)
		
BUGS
	N/A
