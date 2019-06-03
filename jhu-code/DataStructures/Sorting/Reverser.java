
import java.io.*;

public class Reverser {

	public static void main(String[] args) throws IOException {
		
		if (args.length != 2) {
			System.out.println("Usage: Reverser [inputfile] [outputfile");
		}
		else {
			
			File fout = new File(args[1]); // Create output file using user-specified name.
			FileOutputStream fos = new FileOutputStream(fout);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
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
			
			String[] list = cache.split(";"); // Splits input integer Strings into an array.
			
			for (int i = list.length - 1; i >= 0; i--) {
				bw.write(list[i]);
				if (i > 0) { bw.newLine(); }
			}
			
			bw.close();
		}
	}

}