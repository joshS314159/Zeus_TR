package edu.sru.thangiah.zeus.tr.trWriteFile;

import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.tr.TRCoordinates;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Iterator;

/**
 * Created by library-tlc on 5/26/15.
 */
public class TRWriteFormat extends WriteFormat {
private String file;


public TRWriteFormat(final boolean isMakeSeparateFile, final TRDepotsList mainDepots){
	super();
	this.isMakeSeparateFile = isMakeSeparateFile;
	this.mainDepots = mainDepots;
	file = TRProblemInfo.problemFileName;
}


//METHOD
//writes a detailed report of the solution
public void writeLongSolution(/*String file*/) throws IOException {

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
		File theFile = new File(ProblemInfo.outputPath + TRProblemInfo.longSolutionOutputFile);
		//Does the file already exist?
		if(theFile.exists() && !theFile.isDirectory()) {
			//if so, prepare to just edit/modify the file
			in = new FileInputStream(new File(longSolutionPath + longSolutionFile));
			workbook = new XSSFWorkbook(in);
			out = new FileOutputStream(new File((longSolutionPath + longSolutionFile)));

			//does the sheet for the current problem already exist
			for(int x = 0; x < workbook.getNumberOfSheets(); x++) {
				if(workbook.getSheetAt(x).getSheetName().equals(file)) {
					isSheetFound = true;
					sheet = workbook.getSheetAt(x);
					break;
				}
			}
		}
		else {
			//if the file doesn't exist we are going to make a new one
			out = new FileOutputStream(new File(longSolutionPath + longSolutionFile));
			workbook = new XSSFWorkbook();
		}


		if(!isSheetFound) {
			//if we didn't find a matching sheet or this is a new file
			//make a new sheet with the file name
			sheet = workbook.createSheet(file);
		}
	}
	else {
		out = new FileOutputStream(new File(longSolutionPath + "long_solution_" + file));
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet(file);
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

	row = createRowNumber(sheet, rowCounter++);
	writeToNewCell(row, 0, "Filename", ITALICS_STLYE);
	writeToNewCell(row, 1, file);

	row = createRowNumber(sheet, rowCounter++);
	writeToNewCell(row, 0, "Selection Type", ITALICS_STLYE);
	writeToNewCell(row, 1, ProblemInfo.selectShipType.getClass().getCanonicalName().replace("edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Selection.", ""));


	row = createRowNumber(sheet, rowCounter++);
	writeToNewCell(row, 0, "Insertion Type", ITALICS_STLYE);
	writeToNewCell(row, 1, TRProblemInfo.insertShipType.getClass().getCanonicalName().replace("edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Insertion.", ""));


	row = createRowNumber(sheet, rowCounter++);
	writeToNewCell(row, 0, "Number Depots", ITALICS_STLYE);
	writeToNewCell(row, 1, TRProblemInfo.numDepots);

	row = createRowNumber(sheet, rowCounter++);
	writeToNewCell(row, 0, "Total Distance", ITALICS_STLYE);
	writeToNewCell(row, 1, (int) mainDepots.getAttributes().getTotalDistance());
	writeToNewCell(row, 2, "miles");


	row = createRowNumber(sheet, rowCounter++);
	writeToNewCell(row, 0, "Total Cost", ITALICS_STLYE);
	writeToNewCell(row, 1, (int) mainDepots.getAttributes().getTotalCost());

	row = createRowNumber(sheet, rowCounter++);
	writeToNewCell(row, 0, "Avg. Speed");
	writeToNewCell(row, 1, TRProblemInfo.averageVelocity);
	writeToNewCell(row, 2, "mph");

	row = createRowNumber(sheet, rowCounter++);
	writeToNewCell(row, 0, "Total Time");
	writeToNewCell(row, 1, (int) mainDepots.getAttributes().getTotalTravelTime());
	writeToNewCell(row, 2, "minutes");

	row = createRowNumber(sheet, rowCounter++);
	writeToNewCell(row, 0, "Start time", BOLD_STYLE);
	DecimalFormat timeFormat = new DecimalFormat("#00");
	writeToNewCell(row, 1, timeFormat.format(TRProblemInfo.startHour) + ":" + timeFormat.format(TRProblemInfo.startMinute), BOLD_STYLE);



	while(theDepot != mainDepots.getTail())
	//while we have more depots
	{
		//get the next depot at the bottom of this while

		truckList = theDepot.getSubList();    //get truck linked list
		theTruck = truckList.getHead().getNext();            //get the first truck


		row = createRowNumber(sheet, rowCounter++);
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
				createRowNumber(sheet, rowCounter++);
				createRowNumber(sheet, rowCounter++);
				createRowNumber(sheet, rowCounter++);


				row = createRowNumber(sheet, rowCounter++);
				{
					columnCounter = 0;    //reset counter value

					//PRINTS ALL DATA RELATED TO A SINGLE ROUTE
					//read early cells to get an idea of the process
					writeToNewCell(row, columnCounter++, "Depot #:", BOLD_STYLE);
					writeToNewCell(row, columnCounter++, theDepot.getDepotNum());

					writeToNewCell(row, columnCounter++, "Longitude:", BOLD_STYLE);
					writeToNewCell(row, columnCounter++, theDepot.getCoordinates().getLongitude());


					writeToNewCell(row, columnCounter++, "Latitude:", BOLD_STYLE);
					writeToNewCell(row, columnCounter++, theDepot.getCoordinates().getLatitude());
				}



				row = createRowNumber(sheet, rowCounter++);
				{
					columnCounter = 0;


					writeToNewCell(row, columnCounter++, "Day #:", BOLD_STYLE);
					writeToNewCell(row, columnCounter++, dayCounter, BOLD_STYLE);

					writeToNewCell(row, columnCounter++, "Truck #:", BOLD_STYLE);
					writeToNewCell(row, columnCounter++, theTruck.getTruckNum(), BOLD_CENTER_STYLE);

				}



				row = createRowNumber(sheet, rowCounter++);
				{
					columnCounter = 0;

					writeToNewCell(row, columnCounter++, "Total Cost:", BOLD_STYLE);
					writeToNewCell(row, columnCounter++, theDay.getAttributes().getTotalCost(), BOLD_CENTER_STYLE);


					writeToNewCell(row, columnCounter++, "Total Travel Time:", BOLD_STYLE);
					writeToNewCell(row, columnCounter++, theDay.getAttributes().getTotalTravelTime(), BOLD_CENTER_STYLE);

					writeToNewCell(row, columnCounter++, "Total Distance:", BOLD_STYLE);
					writeToNewCell(row, columnCounter++, theDay.getAttributes().getTotalDistance(), BOLD_CENTER_STYLE);
				}


				createRowNumber(sheet, rowCounter++);
				row = createRowNumber(sheet, rowCounter++);
				{
					columnCounter = 0;

					writeToNewCell(row, columnCounter++, "Route-Nodes");
					writeToNewCell(row, columnCounter++, "Bins");
					writeToNewCell(row, columnCounter++, "Distance from Depot");
					writeToNewCell(row, columnCounter++, "Angle from Depot");
					writeToNewCell(row, columnCounter++, "Distance from Last Node");
					writeToNewCell(row, columnCounter++, "Frequency");
				}






				theNode = nodesList.getHead();

				TRCoordinates previousCoordinates = theNode.getCoordinates();

				//				int tempComputedDemand;
				//				int temp
				while(true) {
					//infinite loop we break out of in an if-statement
					//get in here with all the node and start printing out
					//related trucks, etc. and the routes themselves
					//prints the head node (which is the depot) as a node
					//to be visited - this more thoroughly explains the route and
					//distance travelled; also prints tail node which is the depot


					row = createRowNumber(sheet, rowCounter++);
					columnCounter = 0;

					theShipment = theNode.getShipment();

					writeToNewCell(row, columnCounter++, theShipment.getNodeNumber());
					{

						if (theShipment.getNumberOfBins() == 0) {
							writeToNewCell(row, columnCounter++, "tip-cart");
						}
						else{
							writeToNewCell(row, columnCounter++, theShipment.getNumberOfBins());
						}

						writeToNewCell(row, columnCounter++, theDepot.getCoordinates().calculateDistanceThisMiles(theShipment.getCoordinates()));

						writeToNewCell(row, columnCounter++, theDepot.getCoordinates().calculateAngleBearing(theShipment.getCoordinates()));

						writeToNewCell(row, columnCounter++, previousCoordinates.calculateDistanceThisMiles(theShipment.getCoordinates()));

						writeToNewCell(row, columnCounter++, theShipment.countFrequency());
					}


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


				row = createRowNumber(sheet, rowCounter++);
				{
					cell = writeToNewCell(row, 0, "Summation", BOLD_STYLE);
					cell.setCellType(TYPE_STRING);

					cell = row.createCell(1);            //create a cell
					cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
					cell.setCellFormula(demandFormula);    //set the demand formula
					cell.setCellStyle(BOLD_STYLE);
					//evaluation all formulas in the spreadsheet so we can compare values
					workbook.getCreationHelper().createFormulaEvaluator().evaluateAll();


					cell = row.createCell(4);
					cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
					cell.setCellFormula(distanceFormula);
					cell.setCellStyle(BOLD_STYLE);
					//evaluation all formulas in the spreadsheet so we can compare values
					workbook.getCreationHelper().createFormulaEvaluator().evaluateAll();
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
// HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//METHOD
//writes the short solution to our problem
public void writeShortSolution(/*String file*/) throws IOException {


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

	if(!isMakeSeparateFile) {    //are we adding this to one big ultimate file
		//or will each problem have its own file
		//if the file we want to write to already exists then take
		//proper measures to update it
		File theFile = new File(ProblemInfo.outputPath + TRProblemInfo.shortSolutionOutputFile);

		if(theFile.exists() && !theFile.isDirectory()) {
			in = new FileInputStream(new File(shortSolutionPath + shortSolutionFile));
			//create a file input stream for the existing file

			workbook = new XSSFWorkbook(in);
			//re-create all workbooks based on existing file

			out = new FileOutputStream(new File((shortSolutionPath + shortSolutionFile)));
			//setup output file


			for(int x = 0; x < workbook.getNumberOfSheets(); x++) {
				if(workbook.getSheetAt(x).getSheetName().equals(file)) {
					isSheetFound = true;
					sheet = workbook.getSheetAt(x);
					break;
				}
			}
			//do we have a problem already in this workbook as a sheet
			//if so, edit it instead of creating a new one

		}
		else {
			//otherwise we must start from scratch as the file does not exist
			out = new FileOutputStream(new File(shortSolutionPath + shortSolutionFile));
			workbook = new XSSFWorkbook();
		}


		//if we didn't find the sheet or if we are making a new file create a sheet
		//with the file/problem name
		if(!isSheetFound) {
			sheet = workbook.createSheet(file);
		}
	}
	else {
		out = new FileOutputStream(new File(ProblemInfo.outputPath + "TR_short_solution_" + file));
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet(file);
	}
	//START WRITING TO THE FILE
	//this get messy but is pretty simple
	theDepot = theDepot.getNext();                //get the first (only) depot

	row = createRowNumber(sheet, rowCounter++);
	writeToNewCell(row, 0, "Filename");
	writeToNewCell(row, 1, file);

	row = createRowNumber(sheet, rowCounter++);
	writeToNewCell(row, 0, "Selection Type");
	writeToNewCell(row, 1, file);


	row = createRowNumber(sheet, rowCounter++);
	writeToNewCell(row, 0, "Selection Type");
	writeToNewCell(row, 1, ProblemInfo.selectShipType.getClass().getCanonicalName().replace("edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Selection.", ""));


	row = createRowNumber(sheet, rowCounter++);
	writeToNewCell(row, 0, "Insertion Type");
	writeToNewCell(row, 1, TRProblemInfo.insertShipType.getClass().getCanonicalName().replace("edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Insertion.", ""));


	createRowNumber(sheet, rowCounter++);
	createRowNumber(sheet, rowCounter++);

	row = createRowNumber(sheet, rowCounter++);
	{
		columnCounter = 0;
		writeToNewCell(row, columnCounter++, "Depot");
		writeToNewCell(row, columnCounter++, "Truck");
		writeToNewCell(row, columnCounter++, "Day");
		writeToNewCell(row, columnCounter++, "Demand");
		writeToNewCell(row, columnCounter++, "Nodes -->");
	}



	while(theDepot !=  mainDepots.getTail())                                //while we have more depots (only one in this problem)
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


				//				theDay.setDayNumber(dayNumberTemp++);
				//set the day number to the counter

				nodesList = theDay.getSubList();
				//get the nodes linked list (route) from the selected day
				//for the selected truck

				theNode = nodesList.getHead();    //get the head of the nodeLL
				//starting at head shows we always start at the depot
				theShipment = theNode.getShipment();        //get the shipment for the selected node


				row = createRowNumber(sheet, rowCounter++);
				{
					columnCounter = 0;
					writeToNewCell(row, columnCounter++, theDepot.getDepotNum());
					writeToNewCell(row, columnCounter++, theTruck.getTruckNum());
					writeToNewCell(row, columnCounter++, daysLinkedList.getIndexOfObject(theDay));
					writeToNewCell(row, columnCounter++, theDay.getAttributes().getTotalDemand());
				}



				//PRINT THE NODES FOR THE CURRENT ROUTE
				while(theNode != nodesList.getTail()) {
					writeToNewCell(row, columnCounter++, theShipment.getNodeNumber());

					theNode = theNode.getNext();
					//get the next node
					theShipment = theNode.getShipment();
					//get the shipment associated with the node

				}

				//SHOWS WE ALWAYS GO BACK TO THE DEPOT
				writeToNewCell(row, columnCounter++, theShipment.getIndex());



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
public void writeComparisonResults(/*String filename*/) throws IOException {

	//VARIABLES
	File theFile = new File(ProblemInfo.outputPath + TRProblemInfo.comparisonOutputFile);

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

        /*
        if(theFile.exists() && !theFile.isDirectory()) {
            //then set up the inputs and outputs properly to modify the file
            FileInputStream in = new FileInputStream(new File(ProblemInfo.outputPath + TRProblemInfo
                    .comparisonOutputFile));
            workbook = new XSSFWorkbook(in);
            out = new FileOutputStream(new File((ProblemInfo.outputPath + TRProblemInfo.comparisonOutputFile)));
            outputSheet = workbook.getSheetAt(0);
            didFileExist = true;
        }
        else {
        */
	//otherwise the file doesn't exist so set up everything to create a new file
	out = new FileOutputStream(new File(ProblemInfo.outputPath + TRProblemInfo.comparisonOutputFile));
	workbook = new XSSFWorkbook();
	outputSheet = workbook.createSheet("Comparisons");
	didFileExist = false;
//        }


	//INPUT VARIABLES
	File input = new File(TRProblemInfo.compareToInputPath + TRProblemInfo.compareToInputFile);
	FileInputStream inputStream = new FileInputStream(input);

	XSSFWorkbook inputWorkbook = new XSSFWorkbook(inputStream);
	XSSFSheet inputSheet = inputWorkbook.getSheetAt(0);
	Iterator<Row> inputRowIterator = inputSheet.iterator();
	Cell inputCell = null;
	Row inputRow = null;
	Iterator<Cell> inputCellIterator = null;

	double totalOldDistanceTravelled = 0;
	double totalOldTimeTravelled = 0;
	int dayCounter = 0;
	double[] oldDistanceTravelled = new double[TRProblemInfo.noOfDays];
	double[] oldTimeTravelled = new double[TRProblemInfo.noOfDays];

	for(int x = 0; x <= inputSheet.getLastRowNum(); x++){
		inputRow = inputSheet.getRow(x);
		if(inputRow != null) {
			inputCell = inputRow.getCell(0);
			if(inputCell != null && inputCell.getCellType() != Cell.CELL_TYPE_BLANK) {
				inputCell.setCellType(TYPE_STRING);
				String checkMe = inputCell.getStringCellValue();

				if (checkMe.equals("Total Distance")) {
					inputCell = inputRow.getCell(1);
					double distanceValue = Float.parseFloat(inputCell.getStringCellValue());
					oldDistanceTravelled[dayCounter] = distanceValue;
//                oldDistanceTravelled += distanceValue;
				} else if (checkMe.equals("Total Time")) {
					inputCell = inputRow.getCell(1);
					double timeValue = Float.parseFloat(inputCell.getStringCellValue());
					oldTimeTravelled[dayCounter++] = timeValue;
//                oldTimeTravelled += timeValue;
				}
			}
		}
	}

	for(int x = 0; x < oldDistanceTravelled.length; x++){
		totalOldDistanceTravelled += oldDistanceTravelled[x];
		totalOldTimeTravelled += oldTimeTravelled[x];
	}

	int cellCounter = 1;
	int rowCounter = 0;

	outputRow = outputSheet.createRow(rowCounter++);
	{
		outputCell = outputRow.createCell(cellCounter++);
		outputCell.setCellValue("Original Distance");

		outputCell = outputRow.createCell(cellCounter++);
		outputCell.setCellValue("New Distance");

//            cellCounter++;

		outputCell = outputRow.createCell(cellCounter++);
		outputCell.setCellValue("Original Time");

		outputCell = outputRow.createCell(cellCounter++);
		outputCell.setCellValue("New Time");
	}



	outputRow = outputSheet.createRow(rowCounter++);
	{
		cellCounter = 0;

		outputCell = outputRow.createCell(cellCounter++);
		outputCell.setCellValue("Total");

		outputCell = outputRow.createCell(cellCounter++);
		outputCell.setCellValue(totalOldDistanceTravelled);

		outputCell = outputRow.createCell(cellCounter++);
		outputCell.setCellValue(mainDepots.getAttributes().getTotalDistance());

//            cellCounter++;

		outputCell = outputRow.createCell(cellCounter++);
		outputCell.setCellValue(totalOldTimeTravelled);

		outputCell = outputRow.createCell(cellCounter++);
		outputCell.setCellValue(mainDepots.getAttributes().getTotalTravelTime());
	}


	rowCounter = 3;
	cellCounter = 0;
	TRDaysList daysList = mainDepots.getFirst().getSubList().getFirst().getSubList();

	for(int x = 0; x < TRProblemInfo.noOfDays; x++) {
		outputRow = outputSheet.createRow(rowCounter++);
		cellCounter = 0;
		{
			outputCell = outputRow.createCell(cellCounter++);
			outputCell.setCellValue("Day " + x);

			outputCell = outputRow.createCell(cellCounter++);
			outputCell.setCellValue(oldDistanceTravelled[x]);

			outputCell = outputRow.createCell(cellCounter++);
			outputCell.setCellValue(daysList.getAtIndex(x).getAttributes().getTotalDistance());


			outputCell = outputRow.createCell(cellCounter++);
			outputCell.setCellValue(oldTimeTravelled[x]);

			outputCell = outputRow.createCell(cellCounter++);
			outputCell.setCellValue(daysList.getAtIndex(x).getAttributes().getTotalTravelTime());
		}
	}


	workbook.write(out);        //write out workbook to the stream
	out.close();                //close the stream
	System.out.print("comparison outed");
}//COMPARE RESULTS ENDS

@Override
public void writeAll() throws IOException {
	writeLongSolution();
	writeShortSolution();
	writeComparisonResults();
}
// HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<





}
