package edu.sru.thangiah.zeus.tr;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.StringTokenizer;


/**
 * Created by joshuasarver on 11/10/14.
 */
public class TextToXLXS {

FileOutputStream out = null;
private String       fileLocation;
private String       outputLocation;
private String       sheetName;
private XSSFWorkbook workbook;




public TextToXLXS(String fileLocation, String outputLocation, String sheetName) {
	this.fileLocation = fileLocation;
	this.sheetName = sheetName;
	this.outputLocation = outputLocation;
}




public void convertToXLXS()
		throws IOException {
	FileInputStream fis;
	InputStreamReader isr;
	BufferedReader br;
	fis = new FileInputStream(fileLocation);
	isr = new InputStreamReader(fis);
	br = new BufferedReader(isr);


	File output = new File(outputLocation);
	out = new FileOutputStream(output);

	this.workbook = new XSSFWorkbook();
	XSSFSheet sheet = workbook.createSheet(sheetName);    //create a worksheet
	Row row;
	Cell cell;


	//This section will get the initial information from the data file
	//Read in the first line from the file
	String readLn;
	StringTokenizer st;
	double currentValue;

	//read in the first line
	//			readLn = br.readLine();
	//print out the line that was read
	//System.out.println("This is s:" + s);

	//			st = new StringTokenizer(readLn);

	//		while(st.hasMoreElements()) {
	int rowCounter = 0;
	int columnCounter = 0;
	while((readLn = br.readLine()) != null) {
		//			readLn = br.readLine();
		st = new StringTokenizer(readLn);
		row = sheet.createRow(rowCounter++);
		columnCounter = 0;
		while(st.hasMoreTokens()) { //while there are more tokens
			currentValue = Double.parseDouble(st.nextToken());
			//				Double.paq
			cell = row.createCell(columnCounter++);
			cell.setCellValue(currentValue);
		}
	}
	//		}


}




public void writeToFile()
		throws IOException {
	workbook.write(out);
	out.close();
}


}
