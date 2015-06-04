package edu.sru.thangiah.zeus.tr.trWriteFile;

import edu.sru.thangiah.zeus.tr.TR;
import edu.sru.thangiah.zeus.tr.TRCoordinates;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.*;
import edu.sru.thangiah.zeus.tr.TRTruckType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by library-tlc on 5/26/15.
 */
public class PVRPWriteFormat  extends  WriteFormat{

	public PVRPWriteFormat(final boolean isMakeSeparateFile, final TRDepotsList mainDepots){
		super();
		this.isMakeSeparateFile = isMakeSeparateFile;
		this.mainDepots = mainDepots;
//		file = TRProblemInfo.problemFileName;
	}




	@Override
//METHOD
//writes a detailed report of the solution
public void writeLongSolution()
		throws IOException {

//VARIABLES

	FileInputStream in;
	FileOutputStream out;
	int rowCounter = 0;
	int columnCounter = 0;

	XSSFWorkbook workbook;
	XSSFSheet sheet = null;
	Row row;
	Cell cell;

	boolean isSheetFound = false;

	TRTrucksList truckList;
	TRTruck theTruck;
	TRDaysList daysList;
	TRDay theDay;
	TRNodesList nodesList;
	TRNode theNode;
	TRShipment theShipment;






	TRDepot theDepot = mainDepots.getHead();

	if(!isMakeSeparateFile) {
		File theFile = new File(TRProblemInfo.outputPath + TRProblemInfo.longSolutionOutputFile);
		//Does the file already exist?
		if(theFile.exists() && !theFile.isDirectory()) {
			//if so, prepare to just edit/modify the file
			in = new FileInputStream(new File(TRProblemInfo.outputPath + TRProblemInfo.longSolutionOutputFile));
			workbook = new XSSFWorkbook(in);
			out = new FileOutputStream(new File((TRProblemInfo.outputPath + TRProblemInfo.longSolutionOutputFile)));

			//does the sheet for the current problem already exist
			for(int x = 0; x < workbook.getNumberOfSheets(); x++) {
				if(workbook.getSheetAt(x).getSheetName().equals(TRProblemInfo.problemFileName)) {
					isSheetFound = true;
					sheet = workbook.getSheetAt(x);
					break;
				}
			}
		}
		else {
			//if the file doesn't exist we are going to make a new one
			out = new FileOutputStream(new File(TRProblemInfo.outputPath + TRProblemInfo.longSolutionOutputFile));
			workbook = new XSSFWorkbook();
		}


		if(!isSheetFound) {
			//if we didn't find a matching sheet or this is a new file
			//make a new sheet with the file name
			sheet = workbook.createSheet(TRProblemInfo.problemFileName);
		}
	}
	else{
		out = new FileOutputStream(new File(TRProblemInfo.outputPath + "long_solution_" + TRProblemInfo.problemFileName));
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet(TRProblemInfo.problemFileName);
	}

	//CREATE CELL STYLES
	final CellStyle BOLD_STYLE = workbook.createCellStyle();
	Font boldFont = workbook.createFont();
	boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	BOLD_STYLE.setFont(boldFont);
	//creates a style that will bold cells

	final CellStyle BOLD_CENTER_STYLE = workbook.createCellStyle();
	Font boldCenterFont = workbook.createFont();
	boldCenterFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	BOLD_CENTER_STYLE.setFont(boldFont);
	BOLD_CENTER_STYLE.setAlignment(CellStyle.ALIGN_CENTER);
	//creates a style that will bold and center cells

	final CellStyle ITALICS_STLYE = workbook.createCellStyle();
	Font italicFont = workbook.createFont();
	italicFont.setItalic(true);
	ITALICS_STLYE.setFont(italicFont);
	//creates a style that will italicize cells

	final CellStyle CENTER_STYLE = workbook.createCellStyle();
	CENTER_STYLE.setAlignment(CellStyle.ALIGN_CENTER);
	//creates a style that will center cells

	final CellStyle PASSED_STYLE = workbook.createCellStyle();
	PASSED_STYLE.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
	PASSED_STYLE.setFillPattern(CellStyle.FINE_DOTS);

	final CellStyle FAILED_STYLE = workbook.createCellStyle();
	FAILED_STYLE.setFillBackgroundColor(IndexedColors.RED.getIndex());
	FAILED_STYLE.setFillPattern(CellStyle.FINE_DOTS);


	theDepot = theDepot.getNext();

	//START WRITING TO EXCEL SHEET
	//NEW ROW
	row = sheet.createRow(rowCounter++);    //create new row
	cell = row.createCell(0);            //create column 0
	cell.setCellValue("Filename");        //set cell to string
	cell.setCellStyle(ITALICS_STLYE);        //italicize this cell
	cell.setCellStyle(CENTER_STYLE);        //center this cell

	cell = row.createCell(1);
	cell.setCellValue(TRProblemInfo.problemFileName);            //set the file name


	//NEW ROW
	row = sheet.createRow(rowCounter++);
	cell = row.createCell(0);
	cell.setCellValue("Selection Type");    //set text
	cell.setCellStyle(ITALICS_STLYE);        //set style

	cell = row.createCell(1);
	cell.setCellValue(
			TRProblemInfo.selectShipType.getClass().getCanonicalName().replace("edu.sru.thangiah.zeus.pvrp.PVRP", ""));
	//set text to select ship type to class name without the class location information


	//NEW ROW
	row = sheet.createRow(rowCounter++);
	cell = row.createCell(0);
	cell.setCellValue("Insertion_Type");    //set text
	cell.setCellStyle(ITALICS_STLYE);        //set style

	cell = row.createCell(1);
	cell.setCellValue(TRProblemInfo.insertShipType.getClass().getCanonicalName()
			.replace("edu.sru.thangiah.zeus.pvrp" + ".PVRP", ""));
	//set text to select ship type to class name without the class location information


	//NEW ROW
	row = sheet.createRow(rowCounter++);
	cell = row.createCell(0);
	cell.setCellValue("Number_Depots");    //set text
	cell.setCellStyle(ITALICS_STLYE);    //set style

	cell = row.createCell(1);
	cell.setCellValue(TRProblemInfo.numDepots);
	//get and set value


	//NEW ROW
	row = sheet.createRow(rowCounter++);
	cell = row.createCell(0);
	cell.setCellValue("Total_Distance");    //set text
	cell.setCellStyle(ITALICS_STLYE);        //set style

	cell = row.createCell(1);
	cell.setCellValue(mainDepots.getAttributes().getTotalDistance());
	//get and set value


	//NEW ROW
	row = sheet.createRow(rowCounter++);
	cell = row.createCell(0);
	cell.setCellValue("Total_Demand");    //set text
	cell.setCellStyle(ITALICS_STLYE);    //set style

	cell = row.createCell(1);
	cell.setCellValue(mainDepots.getAttributes().getTotalDemand());
	//get and set value


	//NEW ROW
	row = sheet.createRow(rowCounter++);
	cell = row.createCell(0);
	cell.setCellValue("Total_Cost");    //set text
	cell.setCellStyle(ITALICS_STLYE);    //set style

	cell = row.createCell(1);
	cell.setCellValue(mainDepots.getAttributes().getTotalCost());
	//get and set total cost


	while(theDepot != mainDepots.getTail())
	//while we have more depots
	{
		//get the next depot at the bottom of this while

		truckList = theDepot.getSubList();    //get truck linked list
		theTruck = truckList.getHead().getNext();            //get the first truck


		row = sheet.createRow(rowCounter++);        //make a new row
		while(theTruck != truckList.getTail()) {
			//while we have more trucks
			//get new truck at bottom of this while

			daysList = theTruck.getSubList();
			//get the days linked list for the current truck

			theDay = daysList.getHead().getNext();


			int dayCounter = 0;
			while(theDay != daysList.getTail()) {
				//while we have more days in the current truck

				nodesList = theDay.getSubList();
				for(int x = 0; x < 4; x++) {
					row = sheet.createRow(rowCounter++);
					//create four new rows
				}

				columnCounter = 0;    //reset counter value

				//PRINTS ALL DATA RELATED TO A SINGLE ROUTE
				//read early cells to get an idea of the process
				cell = row.createCell(columnCounter++);
				cell.setCellValue("Depot #:");            //set text
				cell.setCellStyle(BOLD_STYLE);            //set style

				cell = row.createCell(columnCounter++);
				cell.setCellValue(mainDepots.getIndexOfObject(theDepot));    //set number
				cell.setCellStyle(BOLD_CENTER_STYLE);        //set style

				cell = row.createCell(columnCounter++);
				cell.setCellValue("X:");                    //set text
				cell.setCellStyle(BOLD_STYLE);                //set style

				cell = row.createCell(columnCounter++);
				cell.setCellValue(theDepot.getCoordinates().getA());    //set number
				cell.setCellStyle(BOLD_CENTER_STYLE);        //set style

				cell = row.createCell(columnCounter++);
				cell.setCellValue("Y:");                    //set text
				cell.setCellStyle(BOLD_STYLE);                //set style

				cell = row.createCell(columnCounter++);
				cell.setCellValue(theDepot.getCoordinates().getB());    //set number
				cell.setCellStyle(BOLD_CENTER_STYLE);        //set style


				columnCounter = 0;

				row = sheet.createRow(rowCounter++);
				cell = row.createCell(columnCounter++);
				cell.setCellValue("Day #:");                //set text
				cell.setCellStyle(BOLD_STYLE);            //set style
				//ETC ....
				cell = row.createCell(columnCounter++);
				cell.setCellValue(dayCounter);
				cell.setCellStyle(BOLD_CENTER_STYLE);


				row = sheet.createRow(rowCounter++);
				columnCounter = 0;
				cell = row.createCell(columnCounter++);
				cell.setCellValue("Truck #:");
				cell.setCellStyle(BOLD_STYLE);

				cell = row.createCell(columnCounter++);
				cell.setCellValue(truckList.getIndexOfObject(theTruck));
				cell.setCellStyle(BOLD_CENTER_STYLE);

				cell = row.createCell(columnCounter++);
				cell.setCellValue("Max Demand:");
				cell.setCellStyle(BOLD_STYLE);

				cell = row.createCell(columnCounter++);
				double maxDemand = theTruck.getTruckType().getMaxCapacity();
				cell.setCellValue(maxDemand);
				cell.setCellStyle(BOLD_CENTER_STYLE);

				cell = row.createCell(columnCounter++);
				cell.setCellValue("Used Demand:");
				cell.setCellStyle(BOLD_STYLE);

				cell = row.createCell(columnCounter++);
				double usedDemand = theDay.getAttributes().getTotalDemand();
				cell.setCellValue(usedDemand);
//				cell.setCellStyle(BOLD_CENTER_STYLE);

				if(usedDemand <= maxDemand){	//is the used demand valid?
					cell.setCellStyle(PASSED_STYLE);	//if so color the cell green
				}else{
					cell.setCellStyle(FAILED_STYLE);	//else, color it red
				}


				row = sheet.createRow(rowCounter++);
				columnCounter = 0;
				cell = row.createCell(columnCounter++);
				cell.setCellValue("Total Cost:");
				cell.setCellStyle(BOLD_STYLE);

				cell = row.createCell(columnCounter++);
				cell.setCellValue(theDay.getAttributes().getTotalCost());
				cell.setCellStyle(BOLD_CENTER_STYLE);


				cell = row.createCell(columnCounter++);
				cell.setCellValue("Max Distance:");
				cell.setCellStyle(BOLD_STYLE);

				cell = row.createCell(columnCounter++);
				double maxDistance = theTruck.getTruckType().getMaxDistance();
				cell.setCellValue(maxDistance);
				cell.setCellStyle(BOLD_CENTER_STYLE);

				cell = row.createCell(columnCounter++);
				cell.setCellValue("Used Distance:");
				cell.setCellStyle(BOLD_STYLE);

				cell = row.createCell(columnCounter++);
				double usedDistance = theDay.getAttributes().getTotalDistance();
				cell.setCellValue(usedDistance);
//				cell.setCellStyle(BOLD_CENTER_STYLE);

				if(usedDistance <= maxDistance){		//if used distance is valid
					cell.setCellStyle(PASSED_STYLE);	//color the cell green
				}else{
					cell.setCellStyle(FAILED_STYLE);	//else color the cell red
				}




				columnCounter = 0;
				for(int x = 0; x < 2; x++) {
					row = sheet.createRow(rowCounter++);
				}
				cell = row.createCell(columnCounter++);    //create new cell number X
				cell.setCellValue("Route-Nodes");    //set each cell value to the correct value from the linked list

				cell = row.createCell(columnCounter++);    //create new cell number X
				cell.setCellValue("Demand");    //set each cell value to the correct value from the linked list

				cell = row.createCell(columnCounter++);    //create new cell number X
				cell.setCellValue(
						"Distance from Depot");    //set each cell value to the correct value from the linked list


				cell = row.createCell(columnCounter++);    //create new cell number X
				cell.setCellValue(
						"Angle from Depot");    //set each cell value to the correct value from the linked list

				cell = row.createCell(columnCounter++);    //create new cell number X
				cell.setCellValue(
						"Distance from Last Node");    //set each cell value to the correct value from the linked list

				cell = row.createCell(columnCounter++);    //create new cell number X
				cell.setCellValue("Frequency");    //set each cell value to the correct value from t


				theNode = nodesList.getHead();

				TRCoordinates previousCoordinates = theNode.getShipment().getCoordinates();
//				double previousX = theNode.getShipment().getXCoord();
//				double previousY = theNode.getShipment().getYCoord();

//				int tempComputedDemand;
//				int temp
				while(true) {
					//infinite loop we break out of in an if-statement
					//get in here with all the node and start printing out
					//related trucks, etc. and the routes themselves
					//prints the head node (which is the depot) as a node
					//to be visited - this more thoroughly explains the route and
					//distance travelled; also prints tail node which is the depot



					columnCounter = 0;

					row = sheet.createRow(rowCounter++);

					theShipment = theNode.getShipment();




					cell = row.createCell(columnCounter++);    //create new cell number X
					cell.setCellValue(theShipment.getIndex());    //print the node shipment index





					cell = row.createCell(columnCounter++);
					cell.setCellValue(theShipment.getDemand());    //print the node shipment demand
//					cell.setCellValue(theShipment.getNumberOfBins());    //print the node shipment demand





					cell = row.createCell(columnCounter++);
					double distanceFromDepot =
							theDepot.getCoordinates().calculateDistanceThisMiles(theShipment.getCoordinates());
					cell.setCellValue(distanceFromDepot);
					//distance from depot can be a great way to show the closest euclidean distance is working




					cell = row.createCell(columnCounter++);
					double angle = theDepot.getCoordinates().calculateAngleBearing(theShipment.getCoordinates());
					cell.setCellValue(angle);
					//distance from depot can be a great way to show the closest euclidean distance is working




					cell = row.createCell(columnCounter++);
					double distanceFromLast = previousCoordinates.calculateDistanceThisMiles(theShipment.getCoordinates());
					cell.setCellValue(distanceFromLast);
					//show the linear distance we are from the last node






					cell = row.createCell(columnCounter++);
					cell.setCellValue(theShipment.getFrequency());    //the frequency the node wants visited

					if(theNode == nodesList.getTail()) {
						//if we are at the tail node (and have printed it already)
						//break out of the while loop
						break;
					}

					previousCoordinates = theShipment.getCoordinates();
					theNode = theNode.getNext();    //grab the next node
				}


				//SUMMATIONS
				//this section runs a summation over the listed distances and listed
				//demands -- these values MUST match the listed values in the day header or else
				//something is wrong
				int lastRowCalculations = rowCounter;
				int firstRowCalculations = rowCounter - nodesList.getSize() - 1;


				String demandFormula = "SUM(B" + firstRowCalculations + ":B" + lastRowCalculations + ")";
				String distanceFormula = "SUM(E" + firstRowCalculations + ":E" + lastRowCalculations + ")";

				row = sheet.createRow(rowCounter++);	//create a cell
				cell = row.createCell(0);
				cell.setCellType(TYPE_STRING);
				cell.setCellValue("Excel Summations");	//with this text
				cell.setCellStyle(BOLD_STYLE);


				//CELL EQUATION TO DO SUMMATION OF ALL DEMAND FOR THIS ROUTE
				cell = row.createCell(1);			//create a cell
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(demandFormula);	//set the demand formula
				cell.setCellStyle(BOLD_STYLE);

				//evaluation all formulas in the spreadsheet so we can compare values
				workbook.getCreationHelper().createFormulaEvaluator().evaluateAll();

				if((int) usedDemand == (int) cell.getNumericCellValue()){
					//if our two demands match, then color the cell green
					cell.setCellStyle(PASSED_STYLE);
				}else{
					cell.setCellStyle(FAILED_STYLE);
				}



				//CELL EQUATION TO DO SUMMATION OF ALL DISTANCE FOR THIS ROUTE
				cell = row.createCell(4);
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(distanceFormula);
				cell.setCellStyle(BOLD_STYLE);

				//evaluation all formulas in the spreadsheet so we can compare values
				workbook.getCreationHelper().createFormulaEvaluator().evaluateAll();

				if((int) usedDistance == (int) cell.getNumericCellValue()){
					//if our two demands match, then color the cell green
					cell.setCellStyle(PASSED_STYLE);
				}else{
					cell.setCellStyle(FAILED_STYLE);
				}


				theDay = theDay.getNext();            //grab the next day
				dayCounter++;
			}
			theTruck = theTruck.getNext();            //grab the next truck
		}
		theDepot = theDepot.getNext();                //grab the next depot
	}

	//auto size the columns so they don't look like hell
	for(int x = 0; x < 7; x++) {
		sheet.autoSizeColumn(x);
	}

	workbook.write(out);        //write the workbook to our outstream
	out.close();                //write the day to disk
}//WRITE LONG SOLUTION ENDS

	//METHOD
//writes the short solution to our problem
	public void writeShortSolution()
			throws IOException {


//VARIABLES

		FileOutputStream out = null;
		FileInputStream in = null;

		XSSFWorkbook workbook = null;    //holds our workbook
		XSSFSheet sheet = null;            //holds our sheet
		Row row = null;                    //a row from our sheet
		Cell cell = null;                //a cell from our row

		boolean isSheetFound = false;    //sometimes we look for a specific sheet to edit

		TRDepot theDepot = null;

		int rowCounter = 0;       //keeps track of the current row we are editing
		int columnCounter = 0;    //keeps track of the current column we are editing
		int dayNumberTemp = 0;    //counter to assign numbers to days under trucks

		TRTrucksList truckList = null;
		TRTruck theTruck = null;
		TRDaysList daysLinkedList = null;
		TRDay theDay = null;
		TRNodesList nodesList = null;
		TRNode theNode = null;
		TRShipment theShipment = null;







		theDepot = mainDepots.getHead();
		//get the head of the depots linked list

		if(!isMakeSeparateFile) {	//are we adding this to one big ultimate file
			//or will each problem have its own file
			//if the file we want to write to already exists then take
			//proper measures to update it
			File theFile = new File(TRProblemInfo.outputPath + TRProblemInfo.shortSolutionOutputFile);

			if (theFile.exists() && !theFile.isDirectory()) {
				in = new FileInputStream(new File(TRProblemInfo.outputPath + TRProblemInfo.shortSolutionOutputFile));
				//create a file input stream for the existing file

				workbook = new XSSFWorkbook(in);
				//re-create all workbooks based on existing file

				out = new FileOutputStream(new File((TRProblemInfo.outputPath + TRProblemInfo.shortSolutionOutputFile)));
				//setup output file


				for (int x = 0; x < workbook.getNumberOfSheets(); x++) {
					if (workbook.getSheetAt(x).getSheetName().equals(TRProblemInfo.problemFileName)) {
						isSheetFound = true;
						sheet = workbook.getSheetAt(x);
						break;
					}
				}
				//do we have a problem already in this workbook as a sheet
				//if so, edit it instead of creating a new one

			} else {
				//otherwise we must start from scratch as the file does not exist
				out = new FileOutputStream(new File(TRProblemInfo.outputPath + TRProblemInfo.shortSolutionOutputFile));
				workbook = new XSSFWorkbook();
			}


			//if we didn't find the sheet or if we are making a new file create a sheet
			//with the file/problem name
			if (!isSheetFound) {
				sheet = workbook.createSheet(TRProblemInfo.problemFileName);
			}
		}
		else{
			out = new FileOutputStream(new File(TRProblemInfo.outputPath +  "short_solution_" + TRProblemInfo.problemFileName));
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet(TRProblemInfo.problemFileName);
		}
		//START WRITING TO THE FILE
		//this get messy but is pretty simple
		theDepot = theDepot.getNext();                //get the first (only) depot
		row = sheet.createRow(rowCounter++);        //create a row at rowCounter and increment the counter
		cell = row.createCell(0);                //create the 0 cell/column
		cell.setCellValue("Filename");            //set the cell to the text

		cell = row.createCell(1);                //create the 1 cell/column
		cell.setCellValue(TRProblemInfo.problemFileName);                //set to the name of our problem/file

		row = sheet.createRow(rowCounter++);        //create a new row
		cell = row.createCell(0);                //create column at 0
		cell.setCellValue("Selection Type");    //set value to text

		cell = row.createCell(1);                //create column at zero
		cell.setCellValue(
				TRProblemInfo.selectShipType.getClass().getCanonicalName().replace("edu.sru.thangiah.zeus.pvrp.PVRP", ""));
		//set the value to the select shipment type while removing all the class location information

		row = sheet.createRow(rowCounter++);        //create a new row
		cell = row.createCell(0);                //create column at 0
		cell.setCellValue("Insertion Type");    //set text

		cell = row.createCell(1);                //create column at 1
		cell.setCellValue(
				TRProblemInfo.insertShipType.getClass().getCanonicalName().replace("edu.sru.thangiah.zeus.pvrp.PVRP", ""));
		//set cell value to the insert shipment type while removing all the class location information


		for(int x = 0; x < 3; x++) {
			row = sheet.createRow(rowCounter++);
			//create three new blank/spacing rows
		}


		//Create a header for depot information
		cell = row.createCell(columnCounter++);
		cell.setCellValue("Depot");

		//Create a header for truck information
		cell = row.createCell(columnCounter++);
		cell.setCellValue("Truck");

		//Create header for Day information
		cell = row.createCell(columnCounter++);
		cell.setCellValue("Day");


		//Create header for Demand information
		cell = row.createCell(columnCounter++);
		cell.setCellValue("Demand");

		//Create header for all nodes on route
		cell = row.createCell(columnCounter++);
		cell.setCellValue("Nodes -->");


		while(theDepot !=
				mainDepots.getTail())                                //while we have more depots (only one in this problem)
		{

			truckList = theDepot.getSubList();
			//get the truck linked list from the depot
			theTruck = truckList.getHead().getNext();
			//get the head of the truck linked list

			while(theTruck != truckList.getTail()) {
				//while we have more trucks
				//we get the next truck at the bottom of this while

				daysLinkedList = theTruck.getSubList();
				//get the days linked list from the current truck

				theDay = daysLinkedList.getHead().getNext();
				//get the first day from the days linked list
				//in the selected truck


				//a counter to assign each day a day number
				//starts at zero for each truck
				while(theDay != daysLinkedList.getTail()) {
					//while we have more days
					//get the next day at the bottom of this while
					columnCounter = 0;

					theDay.setDayNumber(dayNumberTemp++);
					//set the day number to the counter

					nodesList = theDay.getSubList();
					//get the nodes linked list (route) from the selected day
					//for the selected truck

					theNode = nodesList.getHead();    //get the head of the nodeLL
					//starting at head shows we always start at the depot
					theShipment = theNode.getShipment();        //get the shipment for the selected node


					row = sheet.createRow(rowCounter++);    //create a new row
					cell = row.createCell(columnCounter++);        //create a new cell
					cell.setCellValue(theDepot.getDepotNum());    //set it to the depot number it is associated with

					cell = row.createCell(columnCounter++);
					cell.setCellValue(theTruck.getTruckNum());        //set new cell to truck number associated with
					//
					cell = row.createCell(columnCounter++);
					cell.setCellValue(theDay.getDayNumber());            //set the new cell to the day number associated with


					cell = row.createCell(columnCounter++);
					cell.setCellValue(theDay.getAttributes().getTotalDemand());            //set the new cell to the day number associated with


					//PRINT THE NODES FOR THE CURRENT ROUTE
					while(theNode != nodesList.getTail()) {
						cell = row.createCell(columnCounter++);    //create new cell number X
						cell.setCellValue(theShipment.getIndex());    //set the index of the shipment/node

						theNode = theNode.getNext();
						//get the next node
						theShipment = theNode.getShipment();
						//get the shipment associated with the node

					}

					//SHOWS WE ALWAYS GO BACK TO THE DEPOT
					cell = row.createCell(columnCounter++);
					cell.setCellValue(theShipment.getIndex());


					theDay = theDay.getNext();        //get the next day
				}
				theTruck = theTruck.getNext();        //get the next truck
			}
			theDepot = theDepot.getNext();        //get the next depot
		}

		workbook.write(out);            //write the workbook to the out stream
		out.close();                    //write the file to disk
	}//WRITE SHORT SOLUTION ENDS
// HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//METHOD
//this method prints an excel file which compares this program's
//solution(s) to that of known research
	public void writeComparisonResults()
			throws IOException {

//VARIABLES
		File theFile = new File(TRProblemInfo.outputPath + TRProblemInfo.comparisonOutputFile);

		XSSFWorkbook workbook = null;
		Row outputRow = null;
		Cell outputCell = null;
		Iterator<Cell> cellIterator = null;

		boolean didFileExist = false;
		String currentStringCellValue;
		String problemName;
		double currentNumericCellValue;
		double bestDistance = 999999999;
		double thisDistance;
		int problemRow = -1;
		final int MAX_COLUMN_NUMBER = 8;
		final int THIS_DISTANCE_COLUMN = 9;
		final int THIS_PERCENTAGE_COLUMN = 10;


//OUTPUT VARIABLES
		FileOutputStream out = null;
		XSSFSheet outputSheet = null;
		//if the file already exists
		if(theFile.exists() && !theFile.isDirectory()) {
			//then set up the inputs and outputs properly to modify the file
			FileInputStream in =
					new FileInputStream(new File(TRProblemInfo.outputPath + TRProblemInfo.comparisonOutputFile));
			workbook = new XSSFWorkbook(in);
			out = new FileOutputStream(new File((TRProblemInfo.outputPath + TRProblemInfo.comparisonOutputFile)));
			outputSheet = workbook.getSheetAt(0);
			didFileExist = true;
		}
		else {
			//otherwise the file doesn't exist so set up everything to create a new file
			out = new FileOutputStream(new File(TRProblemInfo.outputPath + TRProblemInfo.comparisonOutputFile));
			workbook = new XSSFWorkbook();
			outputSheet = workbook.createSheet("Comparisons");
			didFileExist = false;
		}


//INPUT VARIABLES
		File input = new File(TRProblemInfo.compareToInputPath + TRProblemInfo.compareToInputFile);
		FileInputStream inputStream = new FileInputStream(input);

		XSSFWorkbook inputWorkbook = new XSSFWorkbook(inputStream);
		XSSFSheet inputSheet = inputWorkbook.getSheetAt(0);
		Iterator<Row> inputRowIterator = inputSheet.iterator();
		Cell inputCell = null;
		Row inputRow = null;
		Iterator<Cell> inputCellIterator = null;








		//IF THE FILE DID NOT EXIST
		if(didFileExist == false) {
			//here we copy everything from the base comparison file
			//by iterating over every row and column
			//into this new file which will contain our results
			//obviously, if the file already existed we wouldn't
			//have to copy this over


			for(int x = 0; x < 2; x++) {
				//this for loop only handles the first two
				//rows which are all text
				//and prints them out to the new file
				inputRow = inputRowIterator.next();
				inputCellIterator = inputRow.cellIterator();
				outputRow = outputSheet.createRow(x);
				while(inputCellIterator.hasNext()) {
					inputCell = inputCellIterator.next();
					inputCell.setCellType(TYPE_STRING);
					currentStringCellValue = inputCell.getStringCellValue();
					outputCell = outputRow.createCell(inputCell.getColumnIndex());
					outputCell.setCellType(TYPE_STRING);
					outputCell.setCellValue(currentStringCellValue);
				}
			}//end for

			//then we need to add header information regarding our problem
			outputCell = outputRow.createCell(outputRow.getPhysicalNumberOfCells() + 1);
			outputCell.setCellValue("CPSC_464");

			outputCell = outputRow.createCell(outputRow.getPhysicalNumberOfCells() + 1);
			outputCell.setCellValue("%_Worse");

			for(int x = 2; x < inputSheet.getPhysicalNumberOfRows(); x++) {
				//this for loop here cycles through everything else
				//which is all numeric type (except for column 0)
				//and print them out to the new file
				inputRow = inputRowIterator.next();
				inputCellIterator = inputRow.cellIterator();
				outputRow = outputSheet.createRow(x);
				while(inputCellIterator.hasNext()) {
					inputCell = inputCellIterator.next();
					if(inputCell.getColumnIndex() == 0) {
						inputCell.setCellType(TYPE_STRING);
						currentStringCellValue = inputCell.getStringCellValue();

						outputCell = outputRow.createCell(inputCell.getColumnIndex());
						outputCell.setCellType(TYPE_STRING);
						outputCell.setCellValue(currentStringCellValue);

					}
					else {
						inputCell.setCellType(TYPE_NUMERIC);
						currentNumericCellValue = inputCell.getNumericCellValue();

						outputCell = outputRow.createCell(inputCell.getColumnIndex());
						outputCell.setCellType(TYPE_NUMERIC);
						outputCell.setCellValue(currentNumericCellValue);

					}

				}
			}

		}


		//FINDING THE PROBLEM WE ARE TRYING TO SOLVE
		//this goes through column zero which contains the problem name
		//and searches for our problem
		//once it finds our problem it finds the best solution and stores that value
		for(int x = 0; x < outputSheet.getPhysicalNumberOfRows(); x++) {
			outputRow = outputSheet.getRow(x);    //cycle through the rows
			outputCell = outputRow.getCell(0);    //stay at column zero

			problemName = outputCell.getStringCellValue();
			//get the string value of the problem name

			if(problemName.equals(TRProblemInfo.problemFileName.replace(".xlsx", ""))) {
				//remove the file extensions from the name during comparison
				//if true we can start searching for the best solution from the
				//research papers

				problemRow = outputRow.getRowNum();
				//get the row we are on an store it

				cellIterator = outputRow.cellIterator();
				//iterate through the cells

				while(cellIterator.hasNext() && outputCell.getColumnIndex() <= MAX_COLUMN_NUMBER) {
					//don't go any further than MAX_COLUMN; this contains our solutions

					outputCell = cellIterator.next();

					if(outputCell.getCellType() == outputCell.CELL_TYPE_NUMERIC) {
						//if of type numeric (not string just to be safe)
						//then get a temporary distance and compare it to our lowest distance
						double distanceTemporary = outputCell.getNumericCellValue();
						if(distanceTemporary < bestDistance) {
							bestDistance = distanceTemporary;
						}
					}
				}
			}
		}


		//PRINT THE COMPARISON DATA
		thisDistance = mainDepots.getAttributes().getTotalDistance();
		//get the total distance from the depot

		outputRow = outputSheet.getRow(problemRow);
		//get stored row to compare our problem number to

		outputCell = outputRow.createCell(THIS_DISTANCE_COLUMN);
		//create cell at a specific column number
		outputCell.setCellType(TYPE_NUMERIC);
		outputCell.setCellValue(thisDistance);

		outputCell = outputRow.createCell(THIS_PERCENTAGE_COLUMN);
		//create cell at specific column number
		outputCell.setCellType(TYPE_NUMERIC);
		outputCell.setCellValue(((thisDistance / bestDistance) * 100) - 100);
		//prints our percentage under/over the best solution
		//thus far we have always been over (+ value)


		//go through and autosize all our columns for easy reading
		for(int x = 0; x <= outputRow.getPhysicalNumberOfCells(); x++) {
			outputSheet.autoSizeColumn(x);
		}


		workbook.write(out);        //write out workbook to the stream
		out.close();                //close the stream
	}//COMPARE RESULTS ENDS
// HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<





@Override
public void writeAll() throws IOException {
	writeLongSolution();
	writeShortSolution();
	writeComparisonResults();
}
}
