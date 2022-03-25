/**
 * Written by: Sofia Valiante 40191897 & Justin McIsaac 40192072
 * COMP 249
 * Assignment 3
 * Due: March 25th, 2022
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This program's purpose is to transfer information from excel tables to a hospital's 
 * website in HTML table format, in the best way possible.
 */
public class DriverIOTest {
	
// -------------------------------------------------------------------------------------------------------------------------------------------------------
// static methods	
	
	/**
	 * This static method transfers information from a CSV file to HTML format. It first prints all the necessary code
	 * to the HTML file before beginning the table. The method runs through each line of the CSV files. For each line, 
	 * it uses the delimiter "," to break up the String into a String array and will perform the appropriate code for the
	 * line. First it will begin with a <table>, then the first line of the CSV will be the title of the table. The second line
	 * will be the title columns and will use <th>. The following lines will use <tr> until the last line has been reached,
	 * in which case the data will be a footnote. 
	 * 
	 * @param inputStream
	 * @param outputStream
	 * @param currentFile
	 * @param writeToExceptionsLog
	 * @throws CSVAttributeMissing
	 * @throws CSVDataMissing
	 */
	public static void ConvertCSVtoHTML(Scanner inputStream, PrintWriter outputStream, File currentFile, PrintWriter writeToExceptionsLog) throws CSVAttributeMissing, CSVDataMissing {
		
		outputStream.print("<!DOCTYPE html>"); //all HTML code before <table>
		outputStream.print("<html>");
		outputStream.print("<style>");
		outputStream.print("table { font-family: arial, sans-serif; border-collapse: collapse; } "
				+ "td, th { border: 1px solid #000000;text-align: left;padding: 8px;} "
				+ "tr:nth-child(even) {background-color: #dddddd;}"
				+ "span{font-size: small;}");
		outputStream.print("</style>");
		outputStream.print("<body>");
		
		int lineNb = 0;
		final int FIRST_INDEX = 0;
		final int NB_OF_COL = 4;
		outputStream.print("<table>");
		while(inputStream.hasNextLine()) { 
			String line = inputStream.nextLine();
			//line = line.replaceAll("\\s", "");
			String[] cells = line.split(",");
			lineNb++;
			
			if(lineNb == 1) { //first line in the input file
				outputStream.print("<caption>" + cells[FIRST_INDEX] + "</caption>");
			} 
			else if(lineNb == 2) { //second line in the input file
				outputStream.print("<tr>");
				int i = 0;
				try {
					for(i = 0; i < NB_OF_COL; i++) {
						if(cells[i].replaceAll("\\s", "") == "") { //remove all white space from cell, if cell is empty throw appropriate exception
							throw new CSVAttributeMissing(currentFile, writeToExceptionsLog, cells);
						}
						else {
							outputStream.print("<th>" + cells[i] + "</th>");	
						}
					}
					outputStream.print("</tr>");
				}
				catch(ArrayIndexOutOfBoundsException e) {
					throw new CSVAttributeMissing(currentFile, writeToExceptionsLog, cells);
				}
			}
			else if(inputStream.hasNextLine() == false) { //when end of file has been reached
				outputStream.print("</table>"); //end of table
				outputStream.print("<span>" + cells[FIRST_INDEX] + "</span>");
			}
			else { //all lines in between the second line and the end of file line
				outputStream.print("<tr>");
				int i = 0;
				try {
					for(i = 0; i < NB_OF_COL; i++) {
						if(cells[i].replaceAll("\\s", "") == "") {
							throw new CSVDataMissing(currentFile, writeToExceptionsLog, cells, lineNb);
						}
						else {
							outputStream.print("<td>" + cells[i] + "</td>");
						}
					}
					outputStream.print("</tr>");
				}
				catch(ArrayIndexOutOfBoundsException e) {
					throw new CSVDataMissing(currentFile, writeToExceptionsLog, cells, lineNb);
				}
			}
		}	
		outputStream.print("</body>");
		outputStream.print("</html>");	
	}
	
// -------------------------------------------------------------------------------------------------------------------------------------------------------
// main()	
	
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {	
		
		/*
		 * Variables and Objects
		 */
		//Scanner and PrintWriter objects
		Scanner userInput = null;
		Scanner inputFileCS = null; //global variable
		Scanner inputFileDL = null;
		PrintWriter outputFileCS = null;
		PrintWriter outputFileDL = null;
		PrintWriter writeToExceptionsLog = null;
		BufferedReader readFile = null;
		 
		//File Objects
		File covidStatisticsCSV = null;
		File doctorListCSV = null;
	
		//Strings to keep track of current file
		String filename = "";
		String filetype = "";
		boolean closeFile = false;
		int Attempts = 2;
		
		/*
		 * Opening message
		 */
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("       Welcome To DriverIO by: Sofia Valiante & Justin McIsaac ");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println();
		
		/*
		 * Open the input files and create output files
		 */
		try {
			//Creating Exception.log
			filename = "Exceptions.log";
			filetype = "writing";
			System.out.println("Attempting to create " + filename + "... ");
			writeToExceptionsLog = new PrintWriter(new FileOutputStream(filename));
			System.out.println("Attempt successful! ");	
			System.out.println();
			
			//Opening covidStatistics-CSV format.csv
			covidStatisticsCSV = new File("covidStatistics-CSV format.csv");
			filename = covidStatisticsCSV.getName();
			filetype = "reading";
			System.out.println("Opening the input file " + covidStatisticsCSV.getName() + "... ");
			inputFileCS = new Scanner(new FileInputStream(covidStatisticsCSV.getName()));
			System.out.println("Opened successfully! ");
			System.out.println();

			//Open doctorList-CSVformat.csv
			doctorListCSV = new File("doctorList-CSVformat.csv");
			filename = doctorListCSV.getName();
			filetype = "reading";
			System.out.println("Opening the input file " + doctorListCSV.getName() + "... ");
			inputFileDL = new Scanner(new FileInputStream(doctorListCSV.getName()));
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
			ConvertCSVtoHTML(inputFileCS, outputFileCS, covidStatisticsCSV, writeToExceptionsLog);
			ConvertCSVtoHTML(inputFileDL, outputFileDL, doctorListCSV, writeToExceptionsLog);
			System.out.println("Attempt successful! ");
			
			//When ConvertCSVToHTML method has completed, close all files to flush the data
			outputFileCS.close();
			outputFileDL.close();
			inputFileCS.close();
			inputFileDL.close();
			writeToExceptionsLog.close();
						
		}	
		catch(FileNotFoundException e) {
			//Out-print to Exceptions.log and to console
			System.out.print("Could not open file " + filename + " for ");
			writeToExceptionsLog.print("Could not open file " + filename + " for ");
			if(filetype.compareToIgnoreCase("reading") == 0) {
				System.out.println(filetype + ". ");
				writeToExceptionsLog.println(filetype + ". ");
				System.out.print("Please check that the file exists and is readable. ");
				writeToExceptionsLog.println("Please check that the file exists and is readable. ");
			} 
			if(filetype.compareToIgnoreCase("writing") == 0) {
				System.out.println(filetype + ". ");
				writeToExceptionsLog.println(filetype + ". ");
				System.out.print("Please check that the file exists or can be created. ");
				writeToExceptionsLog.println("Please check that the file exists and is readable. ");
			}
			System.out.println("This program will terminate after closing any opened files. ");
			writeToExceptionsLog.println("This program will terminate after closing any opened files. ");
			//Close file and program
			closeFile = true;	
		}
		catch(CSVAttributeMissing e) {
			System.out.println(e);
			closeFile = true;
		}
		catch(CSVDataMissing e) {
			System.out.println(e);
			closeFile = true;
		}
		catch(IOException e) {
			//Out-print to Exceptions.log
			writeToExceptionsLog.println("IOException encountered. ");
			writeToExceptionsLog.println("This program will terminate after closing any opened files. ");
			writeToExceptionsLog.println();
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
				writeToExceptionsLog.close();
				System.exit(0); //terminates the program
			}
			
			//Display one of the files to the console
			System.out.println();
			userInput = new Scanner(System.in);
			while (Attempts > 0) {
				try {		
					System.out.print("Enter the name of the file you would like to read from: ");
					filename = userInput.nextLine();
					readFile = new BufferedReader(new FileReader(filename));
					String line = readFile.readLine();
					while (line != null) {
						System.out.println(line);
						line = readFile.readLine();
					}
					System.out.println();
				}
				catch (FileNotFoundException e) {
					Attempts--;
					System.out.print(filename + " was not found.");
					if (Attempts > 0)
						System.out.print(" Try again, ");
				}
				catch (IOException e) {
					Attempts--;
					System.out.print(filename + " was not found. ");
					if (Attempts > 0)
						System.out.println(" Try again, ");
				}
			}
			if(userInput != null) {
				userInput.close();
			}	
		}
		
		/*
		 * End of program message
		 */
		System.out.println(); System.out.println();
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("            Program Has Completed           ");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
		
	}
}
		
	