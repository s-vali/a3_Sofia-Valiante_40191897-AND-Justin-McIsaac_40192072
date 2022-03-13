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
	
	public static void ConvertCSVtoHTML(Scanner inputStream, PrintWriter outputStream) {
		
		int count = 0;
		while(inputStream.hasNextLine()) { 
			String line = inputStream.nextLine();
			String[] values = line.split(",");
			count++;
			if(count >= 4) {
				System.out.println();
			}
			for(int i = 0; i < values.length; i++) {
				System.out.print(values[i] + " | ");
			}
		}
	}
	
	public static void writeToExceptionsLog(String exceptionErrorEncountered, String filename) {
		
		PrintWriter ouputExceptionsLog = null;
		try {
			//Open or create new Exceptions.log file
			ouputExceptionsLog = new PrintWriter(new FileOutputStream("Exceptions.log", true));
			
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
	
	public static void closeFile(Scanner inputFile) {
		inputFile.close();
		System.exit(0);
	}
	
// -------------------------------------------------------------------------------------------------------------------------------------------------------
// main()	
	
	public static void main(String[] args) {	
		
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
				if(inputFileCS != null) {
					inputFileCS.close();
				}
				if(inputFileDL != null) {
					inputFileDL.close();
				}
				if(outputFileCS != null) {
					File deleteOutputFileCS = new File("covidStatistics.html");
					deleteOutputFileCS.delete();
				}
				if(!(outputFileDL == null)) {
					File deleteOutputFileDL = new File("doctorList.html");
					deleteOutputFileDL.delete();
				}
				System.exit(0);
			}	
		}
	}
}
		
	
