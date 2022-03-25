/**
 * Written by: Sofia Valiante 40191897 & Justin McIsaac 40192072
 * COMP 249
 * Assignment 3, Question 2
 * Due: March 25th, 2022
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * Exception Class
 */
public class CSVAttributeMissing extends Exception {
	
	/*
	 * Constructor for CSVAttributeMissing
	 */
	public CSVAttributeMissing(File currentFile, PrintWriter writeToExceptionsLog, String[] data) {
		super("Error: Input row cannot be parsed due to missing information. ");
		
		String dataAttribute = "";
		if(currentFile.getName().compareTo("covidStatistics-CSV format.csv") == 0) {
			try {
				if(data[0].compareToIgnoreCase("Age Group") != 0 || data[0] == null) {
					dataAttribute = "Age Group";
				}
				else if(data[1].compareToIgnoreCase("Hospitalized") != 0 || data[1] == null) {
					dataAttribute = "Hospitalized";
				}
				else if(data[2].compareToIgnoreCase("ICU") != 0 || data[2] == null){
					dataAttribute = "ICU";
				}
				else if(data[3].compareToIgnoreCase("Fully Vaccinated") != 0 || data[3] == null){
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
