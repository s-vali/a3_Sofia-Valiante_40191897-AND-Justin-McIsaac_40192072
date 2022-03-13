import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DriverIO {
	
// -------------------------------------------------------------------------------------------------------------------------------------------------------
// static methods	
	
	public static void ConvertCSVtoHTML(Scanner inputStream, PrintWriter outputStream) throws CSVAttributeMissing, CSVDataMissing {
		
		outputStream.print("<!DOCTYPE html>");
		outputStream.print("<html>");
		outputStream.print("<style>");
		outputStream.print("table { font-family: arial, sans-serif; border-collapse: collapse; } "
				+ "td, th { border: 1px solid #000000;text-align: left;padding: 8px;} "
				+ "tr:nth-child(even) {background-color: #dddddd;}"
				+ "span{font-size: small;}");
		outputStream.print("</style>");
		outputStream.print("<body>");
		
		int lineNb = 0;
		outputStream.print("<table>");
		while(inputStream.hasNextLine()) { 
			String line = inputStream.nextLine();
			String[] cells = line.split(",");
			lineNb++;
			
			if(lineNb == 1) { //first line in the input file
				outputStream.print("<caption>" + cells[0] + "</caption>");
			} 
			else if(lineNb == 2) { //second line in the input file
				outputStream.print("<tr>");
				for(int i = 0; i < cells.length; i++) {
					if(cells[i] == "") {
						throw new CSVAttributeMissing();
					} else {
						outputStream.print("<th>" + cells[i] + "</th>");
					}
				}
				outputStream.print("</tr>");
			}
			else if(inputStream.hasNextLine() == false) { //when end of file has been reached
				outputStream.print("</table>"); //end of table
				outputStream.print("<span>" + cells[0] + "</span>");
			}
			else { //all lines in between the second line and the end of file line
				outputStream.print("<tr>");
				for(int i = 0; i < cells.length; i++) {
					if(cells[i] == "") {
						throw new CSVDataMissing();
					} else {
						outputStream.print("<td>" + cells[i] + "</td>");
					}
				}
				outputStream.print("</tr>");
			}
		}
		
		outputStream.print("</body>");
		outputStream.print("</html>");	
	}
	
	public static void writeToExceptionsLog(String exceptionErrorEncountered, String filename) {
		
		PrintWriter ouputExceptionsLog = null;
		try {
			//Open or create new Exceptions.log file
			ouputExceptionsLog = new PrintWriter(new FileOutputStream("Exceptions.log"));
			
			//Write to Exceptions.log
			switch(exceptionErrorEncountered) {
				case "FileNotFoundException": {
					ouputExceptionsLog.println("Could not open input file " + filename + " for reading. ");
					ouputExceptionsLog.println("Please check that the file exists and is readable. This program will terminate after closing any opened files. ");
					ouputExceptionsLog.println();
				}
				case "IOException": {
					ouputExceptionsLog.println("IOException encountered. ");
					ouputExceptionsLog.println("This program will terminate after closing any opened files. ");
					ouputExceptionsLog.println();
				}
				
				ouputExceptionsLog.close();
			}

		}
		catch(FileNotFoundException e) {
			System.out.println("The file 'Exceptions.log' could not be found or is not readable. ");
			System.out.println("This program will terminate after closing any opened files. ");
			ouputExceptionsLog.close();
			System.exit(0);
		}
		catch(IOException e) {
			System.out.println("IOException encountered. ");
			System.out.println("This program will terminate after closing any opened files. ");
			ouputExceptionsLog.close();
			System.exit(0);
		}
	}
	
// -------------------------------------------------------------------------------------------------------------------------------------------------------
// main()	
	
	public static void main(String[] args) throws CSVAttributeMissing, CSVDataMissing {	
		
		/*
		 * Variables and Objects
		 */
		Scanner inputFileCS = null; //global variable
		Scanner inputFileDL = null;
		PrintWriter outputFileCS = null;
		PrintWriter outputFileDL = null;
		String filename = "";
		String filetype = "";
		boolean closeFile = false;
		
		/*
		 * Open the input files and create output files
		 */
		try {
			//Opening covidStatistics-CSV format.csv
			filename = "covidStatistics-CSV format.csv";
			filetype = "reading";
			System.out.println("Opening the input file " + filename + "... ");
			inputFileCS = new Scanner(new FileInputStream(filename));
			System.out.println("Opened successfully! ");
			System.out.println();

			//Open doctorList-CSVformat.csv
			filename = "doctorList-CSVformat.csv";
			filetype = "reading";
			System.out.println("Opening the input file " + filename + "... ");
			inputFileDL = new Scanner(new FileInputStream(filename));
			System.out.println("Opened successfully! ");
			System.out.println();
			
			//Creating/opening covidStatistics.html
			filename = "covidStatistics.html";
			filetype = "writing";
			System.out.println("Attempting to open/create " + filename + "... ");
			outputFileCS = new PrintWriter(new FileOutputStream(filename));
			System.out.println("Attempt successful! ");
			System.out.println();
			
			//Creating/opening doctorList.html
			filename = "doctorList.html";
			filetype = "writing";
			System.out.println("Attempting to open/create " + filename + "... ");
			outputFileDL = new PrintWriter(new FileOutputStream(filename));
			System.out.println("Attempt successful! ");	
			System.out.println();
			
			//All files have been opened and created: call convertCSVtoHTML method
			System.out.println("Attempting to convert CSV to HTML... ");	
			ConvertCSVtoHTML(inputFileCS, outputFileCS);
			ConvertCSVtoHTML(inputFileDL,outputFileDL);
			
			//When ConvertCSVToHTML method has completed, close all files to flush the data
			outputFileCS.close();
			outputFileDL.close();
			inputFileCS.close();
			inputFileDL.close();
			
			//Display one of the files to the console - REQUIREMENT 5
			
		}	
		catch(FileNotFoundException e) {
			//Out-print to Exceptions.log
			writeToExceptionsLog("FileNotFoundException", filename);
			//Out-print to console
			System.out.print("Could not open file " + filename + " for ");
			if(filetype.compareToIgnoreCase("reading") == 0) {
				System.out.println(filetype + ". ");
				System.out.print("Please check that the file exists and is readable. ");
			} 
			if(filetype.compareToIgnoreCase("writing") == 0) {
				System.out.println(filetype + ". ");
				System.out.print("Please check that the file exists or can be created. ");
			}
			System.out.println("This program will terminate after closing any opened files. ");
			//Close file and program
			closeFile = true;
			
		}
		catch(IOException e) {
			//Out-print to Exceptions.log
			writeToExceptionsLog("IOException", filename);
			//Out-print to console
			System.out.println("IOException encountered. ");
			System.out.println("This program will terminate after closing any opened files. ");
			//Close file and program
			closeFile = true;
		}
		finally {
			if(closeFile) {
				//Close the files one at a time if one file failed to open/create
				if(inputFileCS != null) {
					inputFileCS.close();
				}
				if(inputFileDL != null) {
					inputFileDL.close();
				}
				if(outputFileCS != null) {
					File deleteOutputFileCS = new File("covidStatistics.html");
					deleteOutputFileCS.delete(); //deletes the file from the directory
				}
				if(outputFileDL != null) {
					File deleteOutputFileDL = new File("doctorList.html");
					deleteOutputFileDL.delete();
				}
				System.exit(0); //terminates the program
			}	
		}
	}
}
		
	
