/**
 * Written by: Sofia Valiante 40191897 & Justin McIsaac 40192072
 * COMP 249
 * Assignment 3, Question 2
 * Due: March 25th, 2022
 */

import java.io.File;
import java.io.PrintWriter;

/*
 * Exception Class
 */

public class CSVDataMissing extends Exception {	

	/*
	 * Constructor for CSVDataMissing
	 */
	public CSVDataMissing(File currentFile, PrintWriter writeToExceptionsLog, String[] data, int lineNb) {
		
		//Print out error message to console
		super("Error: Input row cannot be parsed due to missing information. ");
		
		//Print error message to log file
		String dataAttribute = "";
		//Attributes of file covidStatistics-CSV format.csv
		if(currentFile.getName().compareTo("covidStatistics-CSV format.csv") == 0) {
			try {
				if(data[0].isBlank()) {
					dataAttribute = "Age Group";
				}
				else if(data[1].isBlank()) {
					dataAttribute = "Hospitalized";
				}
				else if(data[2].isBlank()){
					dataAttribute = "ICU";
				}
				else if(data[3].isBlank()){
					dataAttribute = "Fully Vaccinated";
				}
			}
			catch(ArrayIndexOutOfBoundsException e) {
				dataAttribute = "Fully Vaccinated";
			}	
		}
		//Attributes of file doctorList-CSVformat.csv
		if(currentFile.getName().compareTo("doctorList-CSVformat.csv") == 0) {
			//The data fields at these positions must only be what is in the excel table, cannot be empty field
			try {
				if(data[0].isBlank()) {
					dataAttribute = "Doctor";
				}
				else if(data[1].isBlank()) {
					dataAttribute = "Specialty";
				}
				else if(data[2].isBlank()){
					dataAttribute = "Patient Line";
				}
				else if(data[3].isBlank()){
					dataAttribute = "Office Number";
				}
			}
			//If last data field is missing, then .split() will only create an array of size 3. Must account for last field
			catch(ArrayIndexOutOfBoundsException e) {
				dataAttribute = "Office Number";
			}
		}
		writeToExceptionsLog.println("WARNNING: In file " + currentFile.getName() + " line " + lineNb + " is not converted to HTML: missing data: " + dataAttribute + ". ");
		writeToExceptionsLog.flush();
	}
}
