
import java.util.concurrent.*;
import java.io.*;

public class Generator {
	
	static String list = "";
	
	private static void output(int input) {
		list += input + ";";
	}
	
	public static void main(String[] args) throws IOException {
		
		if (args.length == 3) {
			
			int number = Integer.parseInt(args[0]);
			String[] scope = args[1].split("-");
			
			File fout = new File(args[2]); // Create output file using user-specified name.
			FileOutputStream fos = new FileOutputStream(fout);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			
			ThreadLocalRandom.current().ints(Integer.parseInt(scope[0]), Integer.parseInt(scope[1])).distinct().limit(number).forEach(Generator::output);
			
			String[] numbers = list.split(";");
			
			for (int i = 0; i < numbers.length; i++) {
				bw.write(numbers[i]);
				if (i < numbers.length - 1) { bw.newLine(); }
			}
			
			bw.close();
		}
		else {
			System.out.println("Usage: Generator [number of values] [lowest possible value]-[highest possible value] [output file]");
			System.out.println("Example: Generator 50 1-100 inputlist.txt");
			return;
		}
	}
}