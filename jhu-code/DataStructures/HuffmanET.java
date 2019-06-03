/* Michael Sass - Data Structures 605.202 - Lab 3
 *
 * Huffman Encoding Tree
 *
 * This program accepts a huffman frequency table and a set of strings to be encoded and/or decoded. Using the frequency table
 * a huffman tree is generated and the user-supplied strings are encoded and/or decoded.
 *
 * The usage is "Usage: HuffmanET [opt] [InputFile] [OutputFile] [FrequencyFile]"
 * 		[opt] --> can be "1" or "2"
 *			[1] --> Encodes a single string by building a custom huffman tree based on character frequency of the supplied string.
 *			[2] --> Uses the supplied frequency table to build the huffman tree and encode/decode supplied strings.
 *		[InputFile] --> A list of strings to be encoded/decoded
 *		[OutputFile] --> Name of file where all output will be generated.
 *			The output contains the following...
 *				-A list of nodes traversed using preorder traversal.
 *				-A list of each character and its associated encoding from the huffman tree.
 *				-A list of encoded/decoded strings.
 *				-A list of metrics including execution time, number of nodes, number of leaves and tree depth.
 *		[FrequencyFile] --> The file containing the character frequencies (format: [character] [frequency])
 *
 * The general execution flow is as follows...
 *
 * (1) Build the frequency table using supplied string [option 1] OR ingest supplied frequency table [option 2]
 * (2) Build the Node priority queue which will hold all the nodes
 * (3) Using the Node priority queue, build the huffman tree.
 * (4) Traverse the tree building the code table.
 * (5) Using the code table, encode the unencoded strings and decode the encoded strings.
 * (6) Generate output.
 */

import java.io.*;
import java.util.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;

/* FrequencyRecord objects represent each record in the frequency table.
 * 
 * 		String character 	--> This represents the character of the frequency record (Ex: "a")
 * 		int frequency 		--> This represents the frequency of the frequency record (Ex: "19")
 */
class FrequencyRecord {
		String character;
		int frequency;
		
		public FrequencyRecord(String c, int f) {
			this.character = c;
			this.frequency = f;
		}
}

/* CodeRecord objects reprsent each record in the final code table.
 *
 * 		String character	--> This represents the character in the code table (Ex: "a")
 * 		String code			--> This represents the encoded characters in the code table (Ex: "11111")
 */
class CodeRecord {
	String character;
	String code;
	
	public CodeRecord(String chr, String cod) {
		this.character = chr;
		this.code = cod;
	}
}

/* Node objects represent each node (and leaf) in the Huffman Tree.
 * 
 * 		Node left				--> This is the left-child of the given node.
 * 		Node right				--> This is the right-child of the given node.
 * 		Node parent				--> This is the parent of the given node.
 *		FrequencyRecord data	--> This is the data of the node. Each node contains a FrequencyRecord.
 */
class Node {
	Node left;
	Node right;
	Node parent;
	FrequencyRecord data;
	
	public Node(Node l, Node r, FrequencyRecord d) {
		this.left = l;
		this.right = r;
		this.data = d;
	}
}

/* The HuffmanET object holds all items necessary for building the Huffman Tree and using that tree to
 * encode and decode strings.
 *
 * 		String in		--> HuffmanET accepts input strings (both encoded and decoded)
 * 		String inTab	--> HuffmanET accepts a Frequency Table as input
 * 		int opt			--> HuffmanET has two execution modes (described in top block)
 *
 * Key data elements include...
 * 		FrequencyRecord[] freqTable	--> This array holds each of the records in the frequency table
 * 		Node[] nodeQueue			--> This array holds each of the nodes in the priority queue
 * 		CodeRecord[] codeTable		--> This array holds each of the records in the final code table
 */
public class HuffmanET {
	String input;
	String inputTable;
	String output = "";
	int option;
	
	public HuffmanET(String in, String inTab, int opt) {
		this.input = in;
		this.inputTable = inTab;
		this.option = opt;
	}
	
	FrequencyRecord[] freqTable;
	Node[] nodeQueue;
	int queueSize; //Set separately as this size variable needs to decrement as the nodes are combined/built
	CodeRecord[] codeTable;
	
	/* The start method builds the Node Queue by first building (or ingesting) the frequency table
	 * Secondly, the code table array is inititialized to the size of the frequency table
	 */
	private void start() {
		nodeQueue = new Node[buildFreqTable(input, inputTable, option)];
		queueSize = nodeQueue.length;
		codeTable = new CodeRecord[nodeQueue.length];
	}
	
	/*This function is used to count the distinct elements in a given string (used for execution option 1)*/
	public static long countUniqueCharacters(String in) {
		return in.chars()
            .distinct()
            .count();
	}
	 
	/* The function accepts three parameters.
	 *		(1) - String in  --> Takes the user supplied input strings
	 *		(2) - String tab --> Takes the user supplied frequency table
	 *		(3) - int option --> Takes the user supplied execution option (1 or 2)
	 
	 *	This function builds the frequency table one of two ways...	
	 *	(OPTION 1)
	 *		If option 1 is chosen, a frequency table is built using the supplied input string.
	 *	(OPTION 2)
	 *		If option 2 is chosen, the supplied frequency table is ingested and the freqTable array is populated.
	 */
	private int buildFreqTable(String in, String tab, int option) {
		if (option == 1) { // Option 1
			freqTable = new FrequencyRecord[(int)countUniqueCharacters(in)]; // frequency table array initialized to the size of unique characters in input string
			String[] parsedInput = in.split("");
			int freqIndex = 0;
			freqTable[0] = new FrequencyRecord(parsedInput[0], 1); // Set first record of frequency table to first character in string and frequency = 1
			for (int i = 1; i < parsedInput.length; i++) {
				for (int j = 0; j <= freqIndex; j++) {
					if (parsedInput[i].equals(freqTable[j].character)) {
						(freqTable[j].frequency)++; // Increment frequency of character is multiple of same character is found
						break;
					}
					else if (j == freqIndex) {
						freqTable[++freqIndex] = new FrequencyRecord(parsedInput[i], 1);
						break;
					}
				}
			}
		}
		if (option == 2 || option != 1) { // Option 2 - ingests user supplied frequency table
			String[] parsed = tab.split(":");
			freqTable = new FrequencyRecord[parsed.length];
			for (int i = 0; i < parsed.length; i++) {
				freqTable[i] = new FrequencyRecord((parsed[i].split(" "))[0], Integer.parseInt((parsed[i].split(" "))[1]));
			}
		}
		return freqTable.length; // returns the length for use building the node queue
	}
	
	/* This function prints each node currently in the node priority queue --> [character]:[frequency]
	 * This function is not used in the production app
	 */
	private void printTable(int in) {
		for (int k = 0; k < in; k++) {
			System.out.println(nodeQueue[k].data.character + ":" + nodeQueue[k].data.frequency);
		}
	}
	
	/* This function builds the initial node queue, loading all the starting leaf nodes into the queue.
	 * Each record in the frequency table is loaded into the data element of a tree node.
	 */
	private void buildQueue() {
		for (int i = 0; i < freqTable.length; i++) {
			nodeQueue[i] = new Node(null, null, freqTable[i]);
		}
	}
	
	/* The sort function uses a bubble-sort-type sorting method using the following priority criteria for the queue...
	 *	(1) - Frequency - lower frequency nodes are higher priority
	 *	(2) - Character length - nodes with less characters (Ex: 'w' versus 'cw') are higher priority
	 *	(3) - Alphabetical - characters (or character groups) are lower priority if they are earlier in the alphabet
	 *
	 * The function first does a pass based on frequency (sort priority (1))
	 * The function then does a second pass where sort priorities (2) and (3) are considered
	 */
	private void sort(int length) {
		int length2 = length;
		while (length > 1) {
			for (int i = 0; i < length - 1; i++) {
				if (nodeQueue[i].data.frequency > nodeQueue[i+1].data.frequency) { // Compare frequency weight and switch nodes if necessary
					Node temp = nodeQueue[i];
					nodeQueue[i] = nodeQueue[i+1];
					nodeQueue[i+1] = temp;
				}
			}
			length--;
		}

		while (length2 > 1) {
			for (int i = 0; i < length2 - 1; i++) {
				if (nodeQueue[i].data.frequency == nodeQueue[i+1].data.frequency) {
					if ((nodeQueue[i].data.character).compareTo(nodeQueue[i+1].data.character) > 0) { // Compare alphabetic sort priority
						Node temp = nodeQueue[i];
						nodeQueue[i] = nodeQueue[i+1];
						nodeQueue[i+1] = temp;
					}
					if (nodeQueue[i].data.character.length() > nodeQueue[i+1].data.character.length()) { // Compare character length
						Node temp = nodeQueue[i];
						nodeQueue[i] = nodeQueue[i+1];
						nodeQueue[i+1] = temp;
					}
				}
			}
			length2--;
		}
	}
	
	/* This function builds the huffman tree.
	 *	(1) The top 2 priority nodes are combined using the combineNodes function
	 *	(2) The first element in the node queue is set to the combined node
	 *	(3) The other nodes in the node queue are shifted down 1
	 *	(4) The size of the node queue is decremented by 1 and the build tree function is called again
	 *	(5) The function is run until only one final parent node is left (this will be the top node of the huffman tree)
	 */
	private void buildTree(int size) {
		Node combined = combineNodes(nodeQueue[0], nodeQueue[1]);
		nodeQueue[0] = combined;
		
		for (int i = 1; i < size - 1; i++) {
			nodeQueue[i] = nodeQueue[i+1];
		}
		queueSize--;
				
		sort(size-1);
		
		if (size > 2) {
			buildTree(size - 1);
		}
	}
	
	/* This function combines two nodes (a left-child and a right-child) to form a parent node.
	 * 	(1) A new parent node is created combining the characters of a left and right child node and adding the frequencies of the two
	 *	(2) Characters for the letter group label are sorted alphabetically
	 *	(3) The new parent node is created and the left and right child nodes are set based on the priority model
	 *	(4) The left and right child nodes have their parent node set to the newly created combined node
	 */
	private Node combineNodes(Node left, Node right) {
		FrequencyRecord data;

		data = new FrequencyRecord(left.data.character + right.data.character, left.data.frequency + right.data.frequency);
		
		char[] alphabetical = data.character.toCharArray();
		for (int i = alphabetical.length - 1; i > 0 ; i--) {
			for (int j = 0; j < i; j++) {
				if ((Character.toString(alphabetical[j])).compareTo(Character.toString(alphabetical[j+1])) > 0) {
					char temp = alphabetical[j];
					alphabetical[j] = alphabetical[j+1];
					alphabetical[j+1] = temp;
				}
			}
		}
		data.character = String.valueOf(alphabetical);
		
		Node combined = new Node(left, right, data);
		left.parent = combined;
		right.parent = combined;
		return combined;
	}
	
	int numNodes = 0; // This is used to store/count the number of unique nodes in the tree.
	
	/* crawlTree method traverses the tree in preorder traversal format
	 * This method uses the following parameters...
	 *	(1) Node current	- This is the current node in the traversal process
	 *	(2) String code		- This holds the current code (Ex: 0101) based on where we are in the tree.
	 *	(3) Boolean isLeft	- If the current node is a left-child this will be true
	 *	(4) Boolean goup	- If we need to traverse up in the tree this will be true
	 *	(5) int index		- This holds the current index of the code table
	 * 
	 * This method does the following things...
	 *	- Using the node structure pointers (left, right, parent) we can move freely through the tree
	 *	- When an undiscovered node is traversed, the data:frequency is outputted
	 *	- When an undiscovered node is traversed, the numNodes variable is incremented which stores total unique nodes
	 *	- The tree is traversed in preorder format
	 *	- When a tree is traversed to the left, a "0" is added to the code, when to the right, a "1" is added to the code
	 *	- Conversely, when a tree is traversed upwards the last "0" or "1" to be added is removed.
	 *	- When a leaf is encountered, the leafs character and current code is added to the code table
	 */
	private void crawlTree(Node current, String code, Boolean isLeft, Boolean goup, int index) {	
		if (index == codeTable.length) { return; } // Traversal ends when final leaf has been identified.
		
		if (current.parent == null) { // Start at the top node
			output("--Preorder Tree Traversal--");
			output(current.data.character + ":" + current.data.frequency);
			numNodes++;
			crawlTree(current.left, code + "0", true, false, index); // Traverse to left-child, add "0" to code
		}
		
		else if (isLeft == false && goup == true) {
			if ((current.data.character).equals(current.parent.right.data.character)) { // Need to traverse back up the tree
				crawlTree(current.parent, code.substring(0, code.length()-1), isLeft, true, index); // Traverse back up tree
			}
			else {
				crawlTree(current.parent.right, code.substring(0, code.length()-1) + 1, isLeft, false, index); // Traverse to right brother of current node
			}
		}
		
		else if (current.left != null) { // If a left-child node exists
			output(current.data.character + ":" + current.data.frequency);
			numNodes++;
			crawlTree(current.left, code + "0", true, false, index); // Traverse to left-child node
		}
		
		else if (current.left == null) { // If current node is a leaf
			output(current.data.character + ":" + current.data.frequency);
			numNodes++;
			codeTable[index++] = new CodeRecord(current.data.character, code); // Add character and code to code table
			if (isLeft) { //Go to right brother
				crawlTree(current.parent.right, code.substring(0, code.length()-1) + "1", false, false, index); // Traverse to right brother of current node
			}
			else {
				crawlTree(current, code, isLeft, true, index); // If current node is right-child, we need to start traversing up.
			}
		}
	}
	
	/* This function prints each record of the code table using the format [character]:[code] */
	private void printCodeTable() {
		output("\n--Code Table--");
		for (int i = 0; i < codeTable.length; i++) {
			output(codeTable[i].character + ":" + codeTable[i].code);
		}
	}
	
	/* This function encodes user-supplied unencoded strings
	 *	- Characters not in the frequency table are ignored
	 *	- Capitalized characters are convered to lower-case form
	 */
	private String encode(String unencoded) {
		String encoded = "";
		for (int i = 0; i < unencoded.length(); i++) {
			for (int j = 0; j < codeTable.length; j++) {
				if (((unencoded.substring(i, i+1)).toLowerCase()).equals(codeTable[j].character)) { //Convert to lowercase
					encoded += codeTable[j].code;
					break;
				}
			}
		}
		encoded = unencoded + " when encoded is... " + encoded;
		output(encoded);
		return encoded;
	}
	
	/* This function decoded user-supplied encoded strings
	 * This function iterates through each encoded character incrementing the cached value until a match is found in the code table
	 */
	private String decode(String encoded) {
		String decoded = "";
		String stage = ""; // Code cache
		for (int i = 0; i < encoded.length(); i++) {
			stage += encoded.substring(i, i+1);
			for (int j = 0; j < codeTable.length; j++) {
				if (stage.equals(codeTable[j].code)) {
					decoded += codeTable[j].character;
					stage = "";
				}
			}
		}
		decoded = encoded + " when decoded is... " + decoded;
		output(decoded);
		return decoded;
	}
	
	/* Everything sent to the output function is appended to the final output and separated by "$$" */
	private void output(String in) {
		output += in;
		output += "$$";
	}
	
	/* The metrics function outputs assorted metrics for the application including...
	 *	(1) Execution time
	 *	(2) Number of nodes in the huffman tree
	 * 	(3) Number of leaf nodes in the huffman tree
	 *	(4) Depth of the huffman tree
	 */
	private String metrics(long s, long e) {
		NumberFormat formatter = new DecimalFormat("#0.00000");
		int depth = codeTable[0].code.length();
		
		for (int i = 1; i < codeTable.length; i++) {
			if (codeTable[i].code.length() > depth) {
				depth = codeTable[i].code.length();
			}
		}
		
		String output = "";
		output += "\n--Metrics--\n";
		output += "Execution time is " + formatter.format((e - s) / 1000d) + " seconds.";
		output += "\nThere are " + numNodes + " nodes in the huffman tree.";
		output += "\nThere are " + freqTable.length + " leaves in the huffman tree.";
		output += "\nDepth of the huffman tree is " + (depth - 1) + ".";
		return output;
	}
	
	/* Main() function
	 *	- Performs I/O (Accepting user input and writing output to file)
	 *	- Error checking for I/O
	 *	- Performs timing for application execution in milliseconds
	 *	- Intantiates HuffmanET object using execution method of user's choice.
	 *	- Drives application from start to finish.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 4) {
			System.out.println("Error: Too many options.\nUsage: HuffmanET [opt] [InputFile] [OutputFile] [FrequencyFile]");
			return;
		}
		
		String theinput = "";
		String thetable = "";
		
		try (BufferedReader br = new BufferedReader(new FileReader(args[1]))) { // Reads in the input file strings
			try {
				String line;
				for (int i = 0; i < 100; i++) {
					while ((line = br.readLine()) != null) {
						theinput += line;
						theinput += ":"; // separates each string by a ":" character
					}
				}
			} catch (NullPointerException e) {}
		} catch (FileNotFoundException e) { System.out.println("Error: Invalid Input File"); return; }
		
		try (BufferedReader br2 = new BufferedReader(new FileReader(args[3]))) { // Reads in user-supplied frequency table
			try {
				String line;
				for (int i = 0; i < 100; i++) {
					while ((line = br2.readLine()) != null) {
						thetable += line;
						thetable += ":"; // separates each string by a ":" character
					}
				}
			} catch (NullPointerException e) {}
		} catch (FileNotFoundException e) { System.out.println("Error: Invalid Frequency File"); return; }
		
		File fout = new File(args[2]); //Creates the output file with the name specified via the command line
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		//START TIMER
		long start = System.currentTimeMillis(); //start the timer
		
		HuffmanET huff = new HuffmanET("","",0); // Instantiates a new HuffmanET object
		try {
			if (Integer.parseInt(args[0]) == 2) { // If option 2 is chosen by user
				huff = new HuffmanET(theinput, thetable, Integer.parseInt(args[0]));
			}
			else if (Integer.parseInt(args[0]) == 1) { // If option 1 is chosen by user
				if ((theinput.split(":")).length > 1) {
					System.out.println("Error: Only one input is allowed when using this option.");
					return;
				}
				huff = new HuffmanET((theinput.split(":"))[0], thetable, Integer.parseInt(args[0]));
			}
			else { // Default option is "2"
				huff = new HuffmanET(theinput, thetable, Integer.parseInt(args[0]));
			}
		} catch (NumberFormatException e) { System.out.println("Error: Invalid option given"); return; }
		
		huff.start(); // starts program execution
		huff.buildQueue(); // builds node queue
		huff.sort(huff.queueSize); // performs initial sort for node queue
		huff.buildTree(huff.queueSize); // builds the tree using the priority queue and sorting methods
		huff.crawlTree(huff.nodeQueue[0], "", false, false, 0); // crawls the tree building the code table
		
		long end = System.currentTimeMillis();
		// END TIMER
		
		huff.printCodeTable(); // outputs final code table
		
		String[] allinput = (theinput).split(":"); // takes input and creates colon delimeted input string
		huff.output += "\n--Encoded and Decoded Strings--\n";
		for (int i = 0; i < allinput.length; i++) {
			if (allinput[i].substring(0, 1).equals("0") || allinput[i].substring(0, 1).equals("1")) { // decode encoded strings
				huff.decode(allinput[i]);
			}
			else {
				huff.encode(allinput[i]); // encode the unencoded strings
			}
		}
		huff.output += huff.metrics(start, end); // output program metrics
		String[] alloutput = (huff.output).split("\\$\\$"); // generate output and write to final output string

		for (int i = 0; i < alloutput.length; i++) { // write all output to final text file
			bw.write(alloutput[i]);
			bw.newLine();
		}
	
		bw.close(); // close output file
		System.out.println("Execution Completed"); // program execution completed
	}
}