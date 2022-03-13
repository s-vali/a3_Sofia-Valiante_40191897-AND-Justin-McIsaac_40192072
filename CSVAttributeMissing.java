import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * Exception Class
 */
public class CSVAttributeMissing {
	
	PrintWriter outprintToConsoleLog = null;
	
	public CSVAttributeMissing() {
		System.out.println("Error: Input row cannot be parsed due to missing information. ");
		
		try {
			outprintToConsoleLog = new PrintWriter(new FileOutputStream("Exceptions.log", true));
		}
		catch(FileNotFoundException e) {
			
		}
		catch(IOException e) {
			
		}
		
	}

}
