import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * Exception Class
 */
public class CSVAttributeMissing extends Exception {
	/**
	 * This is the exception class that is thrown when there is a missing attribute within a CSV file.
	 * @param currentFile
	 * @param writeToExceptionsLog
	 * @param data
	 */
	public CSVAttributeMissing(File currentFile, PrintWriter writeToExceptionsLog, String[] data) {
		super("Error: Input row cannot be parsed due to missing information. ");
		//putting just data from cells[i] doesnt mean anything bc it doesn't exist in the array at the index, 
		//so will get IndexOutOfBounds exception and also i need to check which file it is to assume
		//which data entry is missing
		
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
		writeToExceptionsLog.println("ERROR: In file " + currentFile.getName() + ". Missing attribute: " + dataAttribute + ". File is not converted to HTML. ");		
		writeToExceptionsLog.flush();
	}
}
