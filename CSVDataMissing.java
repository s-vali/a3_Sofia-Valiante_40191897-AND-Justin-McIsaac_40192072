import java.io.File;
import java.io.PrintWriter;

/*
 * Exception Class
 */

public class CSVDataMissing extends Exception {	

	public CSVDataMissing(File currentFile, PrintWriter writeToExceptionsLog, String[] data, int lineNb) {
		
		//Print out error message to console
		super("Error: Input row cannot be parsed due to missing information. ");
		
		//Print error message to log file
		String dataAttribute = "";
		if(currentFile.getName().compareTo("covidStatistics-CSV format.csv") == 0) {
			try {
				if(data[0].compareToIgnoreCase("Age Group") != 0) {
					dataAttribute = "Age Group";
				}
				else if(data[1].compareToIgnoreCase("Hospitalized") != 0) {
					dataAttribute = "Hospitalized";
				}
				else if(data[2].compareToIgnoreCase("ICU") != 0){
					dataAttribute = "ICU";
				}
				else if(data[3].compareToIgnoreCase("Fully Vaccinated") != 0){
					dataAttribute = "Fully Vaccinated";
				}
			}
			catch(ArrayIndexOutOfBoundsException e) {
				dataAttribute = "Fully Vaccinated";
			}	
		}
		if(currentFile.getName().compareTo("doctorList-CSVformat.csv") == 0) {
			try {
				if(data[0].compareToIgnoreCase("Doctor") != 0) {
					dataAttribute = "Doctor";
				}
				else if(data[1].compareToIgnoreCase("Specialty") != 0) {
					dataAttribute = "Specialty";
				}
				else if(data[2].compareToIgnoreCase("Patient Line") != 0){
					dataAttribute = "Patient Line";
				}
				else if(data[3].compareToIgnoreCase("Office Number") != 0){
					dataAttribute = "Office Number";
				}
			}
			catch(ArrayIndexOutOfBoundsException e) {
				dataAttribute = "Office Number";
			}
		}
		writeToExceptionsLog.println("WARNNING: In file " + currentFile.getName() + " line " + lineNb + " is not converted to HTML: missing data: " + dataAttribute + ". ");
		writeToExceptionsLog.flush();
	}
}
