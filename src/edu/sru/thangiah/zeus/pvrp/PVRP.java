package edu.sru.thangiah.zeus.pvrp;

//IMPORTS


import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.pvrp.pvrpqualityassurance.PVRPQualityAssurance;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Vector;

import static java.lang.StrictMath.atan2;
import static java.lang.StrictMath.sqrt;


/**
 * PVRP (Periodic Vehicle Routing)
 * Routes vehicles to various nodes over a given number of days
 * <p/>
 * Version 1.0
 * <p/>
 * 10/17/14
 * <p/>
 * AUTHORS
 * Aaron Rockburn and Joshua Sarver
 * Slippery Rock University of Pa
 * CPSC 464 - Fall 2014
 * <p/>
 * COPYLEFT
 * Attribution-ShareAlike 4.0 International
 * <p/>
 * BASED ON
 * Dr. Sam R. Thangiah's work at Slippery Rock University of Pa
 * A Heuristic for the Periodic Vehicle Routing Problem by M. Gaudioso, G. Paletta
 * Chou
 * The Periodic Vehicle Routing Problem by S. Coene, A. Arnout and F. Spieksma
 * A Variable Neighborhood Search Heuristic for Periodic Routing Problems by Vera C. Hemmelmayr, Karl F. Doerner§,
 * Richard F. Hartl
 * <p/>
 * Methods are generally sorted by breadth-first order
 */

//CLASS
public class PVRP {

private final int TYPE_NUMERIC = 0;
//track the CPU processing time
private final int TYPE_STRING = 1;
//CLASS VARIABLES
private long startTime, endTime;

private PVRPShipmentLinkedList mainShipments = new PVRPShipmentLinkedList();

private PVRPDepotLinkedList mainDepots;
//depots linked list for the VRP problem

private boolean isMakeSeparateFile;


//TRASH ROUTING TEMPORARY CONSTRUCTOR
//public PVRP(String dataFile, boolean isMakeSeparateFile, Object selectionObjectType, TRShipmentsList mainShipments) throws IOException, InvalidFormatException {
//	this.mainShipments = (PVRPShipmentLinkedList) mainShipments;
//	this.isMakeSeparateFile = isMakeSeparateFile;
//
//	//VARIABLES
//	PVRPProblemInfo.truckTypes = new Vector();
//	//Truck types are placed into a vector
//
//	//READ DATA
////	readDataFromFile(PVRPProblemInfo.inputPath + dataFile);                          //reads data from file
////	Settings.printDebug(Settings.COMMENT,
////							   "Read Data File: " + PVRPProblemInfo.inputPath + dataFile);    //store some debug data
//	printDataToConsole();
//	//prints the routes to the console
//
//
//	//PROCESS PREPARATION
//
//	if(mainShipments.getHead() == null) {
//		Settings.printDebug(Settings.ERROR, "PVRP: Shipment linked list is empty");
//	}
//	//If the shipments linked list (our problem data) isn't null
//
//
//	PVRPProblemInfo.selectShipType = selectionObjectType;
//
//
//	PVRPProblemInfo.insertShipType = new PVRPLinearGreedyInsertShipment();
//	Settings.printDebug(Settings.COMMENT, PVRPLinearGreedyInsertShipment.WhoAmI());
//	//Sets up our shipment insertion type -- we only have one hueristic for this
//
//
//	startTime = System.currentTimeMillis();
//	//We wanna time how long it takes to create our solution
//	//Start time records the starting time of processing
//
//
//	//PROCESSING
//	createInitialRoutes();
//	printRoutesToConsole();
//	//this creates our initial solution routes
//	//although this code doesn't have it, local optimzation
//	//would improve upon the initial routes created here
//
//
//	//QUALITY CHECK
//	Settings.printDebug(Settings.COMMENT, "Created Initial Routes ");
//	//Get the initial solution
//	//Depending on the Settings status, display information on the routes
//	//Trucks used, total demand, dist, travel time and cost
//
//
//	System.out.println("Starting QA");
//	//creates a new object that checks out quality
//	if(new PVRPQualityAssurance(mainDepots, mainShipments).runQA() == false) {
//		//if QA failed -- bad quality
//		Settings.printDebug(Settings.ERROR, "SOME QA FAILED!");
//	}
//	else {
//		//if QA was a success -- solution created properly
//		Settings.printDebug(Settings.COMMENT, "QA succeeded");
//	}
//	//Check for the quality and integrity of the solution
//
//	System.out.println("Routes that cannot be routed due to lack of local optimization:");
//	PVRPShipment aShipment = mainShipments.getHead();
//	while(aShipment != mainShipments.getTail()){
//		if(!aShipment.getCanBeRouted()){
//			System.out.println("Shipment " + aShipment.getIndex());
//		}
//		aShipment = aShipment.getNext();
//	}
//
//
//
//	System.out.println("\nWRITING SOLUTION AND COMPARISON FILES\n");
//	//CLEAN UP AND GUI
//	writeShortSolution(dataFile);
//	writeLongSolution(dataFile);
//	//Write the short and long solutions to our problems in Excel format
//	//long solution is far more detailed than short solution; both can be useful
//
//
//	compareResults(dataFile);
//	//writes an Excel file that compares our results to some results
//	//from various research papers
//
//	System.out.println("\nLAUNCHING GUI\n");
//
//	new ZeusGui(mainDepots, mainShipments);
//
//
//}


//CONSTRUCTOR
//this constructor will convert a width-delimited file into a XLSX file which will allow PVRP to read it
public PVRP(String fileLocation, String outputLocation, String delimiter, String sheetName) {
	TextToXLXS convert =
			new TextToXLXS(fileLocation, outputLocation, sheetName);           //creates construct with informaiton
	try {
		convert.convertToXLXS();
	}
	catch(java.io.IOException e) {
		e.printStackTrace();
	}
	try {
		convert.writeToFile();                    //actually converts the file
	}
	catch(java.io.IOException e) {
		e.printStackTrace();
	}
}

public PVRP(){

}


//CONSTRUCTOR
//this constructor is called by PVRPRoot and launches reading in data, routing vehicles, and printing out information
public PVRP(String dataFile, boolean isMakeSeparateFile, Object selectionTypeObject)
		throws IOException, InvalidFormatException, IllegalAccessException, InvocationTargetException,
					   InstantiationException, NoSuchMethodException {

	this.isMakeSeparateFile = isMakeSeparateFile;

	//VARIABLES
	PVRPProblemInfo.truckTypes = new Vector();
	//Truck types are placed into a vector

	//READ DATA
	readDataFromFile(PVRPProblemInfo.inputPath + dataFile);                          //reads data from file
	Settings.printDebug(Settings.COMMENT,
						"Read Data File: " + PVRPProblemInfo.inputPath + dataFile);    //store some debug data
	printDataToConsole();
	//prints the routes to the console


	//PROCESS PREPARATION

	if(mainShipments.getHead() == null) {
		Settings.printDebug(Settings.ERROR, "PVRP: Shipment linked list is empty");
	}
	//If the shipments linked list (our problem data) isn't null


		PVRPProblemInfo.selectShipType = selectionTypeObject;

//		Settings.printDebug(Settings.COMMENT, selectionTypeObject.WhoAmI());

//	PVRPProblemInfo.selectShipType = new PVRPClosestEuclideanDistToDepot();
//	Settings.printDebug(Settings.COMMENT, PVRPClosestEuclideanDistToDepot.WhoAmI());
	//This sets our heuristic to choose the next shipment
	//there are several more commented out under this

//	PVRPProblemInfo.selectShipType = new PVRPSmallestPolarAngleToDepot();
//	Settings.printDebug(Settings.COMMENT, PVRPSmallestPolarAngleToDepot.WhoAmI());

//	PVRPProblemInfo.selectShipType = new PVRPSmallestPolarAngleShortestDistToDepot();
//	Settings.printDebug(Settings.COMMENT, PVRPSmallestPolarAngleShortestDistToDepot.WhoAmI());


	PVRPProblemInfo.insertShipType = new PVRPLinearGreedyInsertShipment();
	Settings.printDebug(Settings.COMMENT, PVRPLinearGreedyInsertShipment.WhoAmI());
	//Sets up our shipment insertion type -- we only have one hueristic for this


	startTime = System.currentTimeMillis();
	//We wanna time how long it takes to create our solution
	//Start time records the starting time of processing


	//PROCESSING
	createInitialRoutes();
	printRoutesToConsole();
	//this creates our initial solution routes
	//although this code doesn't have it, local optimzation
	//would improve upon the initial routes created here


	//QUALITY CHECK
	Settings.printDebug(Settings.COMMENT, "Created Initial Routes ");
	//Get the initial solution
	//Depending on the Settings status, display information on the routes
	//Trucks used, total demand, dist, travel time and cost


	System.out.println("Starting QA");
	//creates a new object that checks out quality
	if(new PVRPQualityAssurance(mainDepots, mainShipments).runQA() == false) {
		//if QA failed -- bad quality
		Settings.printDebug(Settings.ERROR, "SOME QA FAILED!");
	}
	else {
		//if QA was a success -- solution created properly
		Settings.printDebug(Settings.COMMENT, "QA succeeded");
	}
	//Check for the quality and integrity of the solution

    System.out.println("Routes that cannot be routed due to lack of local optimization:");
    PVRPShipment aShipment = mainShipments.getHead();
    while(aShipment != mainShipments.getTail()){
        if(!aShipment.getCanBeRouted()){
            System.out.println("Shipment " + aShipment.getIndex());
        }
        aShipment = aShipment.getNext();
    }



	System.out.println("\nWRITING SOLUTION AND COMPARISON FILES\n");
	//CLEAN UP AND GUI
	writeShortSolution(dataFile);
	writeLongSolution(dataFile);
	//Write the short and long solutions to our problems in Excel format
	//long solution is far more detailed than short solution; both can be useful


	compareResults(dataFile);
	//writes an Excel file that compares our results to some results
	//from various research papers

//	System.out.println("\nLAUNCHING GUI\n");
//
//			new ZeusGui(mainDepots, mainShipments);


} //PVRP ENDS
// HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


public PVRP(String dataFile, boolean isMakeSeparateFile, Object selectionTypeObject, int moreStuffHere){

}


//METHOD
//read in the data from the requested file in Excel format
public int readDataFromFile(String PVRPFileName)
		throws IOException, InvalidFormatException, InstantiationException, IllegalAccessException,
			   InvocationTargetException {

//VARIABLES
	int numberOfVehicles = 0;
	int numberOfNodes    = 0;
	int daysServicedOver = 0;
	int maxDistanceD     = 0;
	int maxDemandQ       = 0;
	int numberDepots = -1;
	int nodeNumber = -1;
	int demandQ = -1;
	int frequency = -1;
	int numberTruckTypes = 1;
	int type = 0;                    //trucks type, all the same for our problems
	int numberCombinations = -1;    //number of combinations for node
	int cellColumnCounter = 0;        //tracks our current cell for current row
	int finalRowNumber = 0;            //the last row in the speadsheet

	double DUMMY = 0;
	double xCoordinates = -1;
	double yCoordinates = -1;
	double depotXCoordinates = -1;
	double depotYCoordinates = -1;

	String truckTypeString = "1";
	//type of truck needed; we only have one type for our problem set

	int list[] = new int[PVRPProblemInfo.MAX_COMBINATIONS];
	//this variable holds various day visit combinations that each node could find acceptable

	int currentCombination[][] = new int[PVRPProblemInfo.MAX_HORIZON][PVRPProblemInfo.MAX_COMBINATIONS];
	//contains the current day visit combination selected for the node
	PVRPDayLinkedList mainDaysTemp = new PVRPDayLinkedList();
	//contains a temporary list of our days

	FileInputStream file = new FileInputStream(new File(PVRPFileName));        //problem being read
	XSSFWorkbook workbook =
			new XSSFWorkbook(file);                            				//make a new Excel workbook to read the data
	XSSFSheet sheet = workbook.getSheetAt(0);                                //get the zero-th sheet
	Iterator<Row> rowIterator = sheet.iterator();                            //an iterator for rows
	Row row = rowIterator.next();                                        	//gets the next row
	Iterator<Cell> cellIterator = row.cellIterator();                        //an iterator for columns
	Cell cell;                                                                //holds the current cell being read





//READ ROW 0
	//this while loop will work each cell of the zero-th row
	while(cellIterator.hasNext())    //while we have a next cell
	{
		cell = cellIterator.next();                                //get the cell data
		cell.setCellType(TYPE_NUMERIC);                                    //set the cell type being read a numeric
		float currentCellValue = (float) cell.getNumericCellValue();    //extract cell data into a float

		switch(cellColumnCounter)                    //Finite State Machine
		{                                            //steps through each cell of row 0
			case 0:
				numberDepots = (int) currentCellValue;
				PVRPProblemInfo.numDepots = numberDepots;    //Set the number of depots to 1 for this problem
				break;    //this cell contains the number of depots (all our problems are 1)
			case 1:
				numberOfVehicles = (int) currentCellValue;
				PVRPProblemInfo.noOfVehs = numberOfVehicles;    //number of vehicles
				break;    //reads in the number of vehicles
			case 2:
				numberOfNodes = (int) currentCellValue;
				PVRPProblemInfo.noOfShips = numberOfNodes;    //number of shipments
				break;    //reads in the number of nodes
			case 3:
				daysServicedOver = (int) currentCellValue;
				PVRPProblemInfo.noOfDays = daysServicedOver;    //number of days (horizon) or number of depots for PVRP
				break;    //reads in the horizon/number of days/days serviced over

			//PVRPProblem info is a class that holds various bits of information related to our problem
		}
		cellColumnCounter++;
		//increment cell counter so we can move through FSM
	}





	finalRowNumber = daysServicedOver + 1 + numberDepots + numberOfNodes;
	//calculate the last row in the spreadsheet

	PVRPProblemInfo.fileName = PVRPFileName;    //name of the file being read in
	PVRPProblemInfo.probType = type;            //problem type

	Vector custTypes = new Vector();
	//Obtain the different customer types
	for(int ct = 0; ct < 1; ct++) {
		custTypes.add(new Integer(1));
	}
	//we don't have different customer type for our problem set
	//but is here if ever needed





//READ ROWS PERTAINING TO DAY INFORMATION
	row = rowIterator.next();                //get the next row
	cellIterator = row.cellIterator();        //an iterator for columns

	//while we are past the header row but not into nodes/depots
	//there is one row of this data per day serviced over
	int dayNumberCounter = 0;
	while(row.getRowNum() > 0 && row.getRowNum() <= daysServicedOver) {
		cellColumnCounter = 0;                //reset our column counter
		while(cellIterator.hasNext())        //while we have a next cell
		{
			cell = (Cell) cellIterator.next();                                //get the cell data
			cell.setCellType(
					TYPE_NUMERIC);                                    //ensure we are going to be requesting numeric
					// data
			float currentCellValue = (float) cell.getNumericCellValue();    //extract cell data into float
			switch(cellColumnCounter)                                        //Finite State Machine -- reads each cell
			// of the current row
			{
				case 0:
					maxDistanceD = (int) currentCellValue;
					break;        //the first value is maximum distance allowed by the truck

				//MAX CAP
				case 1:
					maxDemandQ = (int) currentCellValue;
					break;        //the second value is the maximum demand allowed by the truck

			}
			cellColumnCounter++;    //increment cell counter so we can move through FSM
		}

		if(maxDemandQ == 0) {        //if there is no maximum capacity, set it to a very large number
			maxDemandQ = 99999999;
		}

		if(maxDistanceD == 0) {    //if there is no max distance, set it to a very large number
			maxDistanceD = 9999999;
		}

		PVRPDay day = new PVRPDay(0, numberOfVehicles, daysServicedOver, maxDistanceD*numberOfVehicles, maxDemandQ*numberOfVehicles, depotXCoordinates,
								  depotYCoordinates, dayNumberCounter++);    //create a new day for the currently read information
		mainDaysTemp.insertLastDay(day);                //insert the just created day into our temporary list of days
		row = rowIterator.next();        //get the next row
		cellIterator = row.cellIterator();    //an iterator for columns

	}


	for(int i = 0; i < numberTruckTypes; i++) {
		PVRPTruckType truckType = new PVRPTruckType(i, maxDistanceD, maxDemandQ, truckTypeString);
		PVRPProblemInfo.truckTypes.add(truckType);
	}





//READ DEPOT INFORMATION
	cellColumnCounter = 0;
	//reset our column counter
	while(cellIterator.hasNext() && row.getRowNum() == daysServicedOver + 1 &&
		  row.getRowNum() <= daysServicedOver + numberDepots + 1)
	//we only have one depot --  if we had more depots this will read them all
	//+1 includes the header row
	{
		cell = cellIterator.next();                    //get the next cell
		cell.setCellType(TYPE_NUMERIC);                //set the type to be numeric
		float currentCellContents = (float) cell.getNumericCellValue();
		//get the numeric cell value

		switch(cellColumnCounter) {
			case 0:
				nodeNumber = (int) currentCellContents;
				break;    //the depot node number (starts at zero)
			case 1:
				depotXCoordinates = (double) currentCellContents;
				break;    //the depot x coordinate
			case 2:
				depotYCoordinates = (double) currentCellContents;
				break;    //the depot y coordinate
			default:
				break;

		}
		cellColumnCounter++;        //increment what column we are one
	}

	mainDepots = new PVRPDepotLinkedList();        //create a new depot linked list for our solution
	PVRPDepot depotTemporary = new PVRPDepot(nodeNumber, depotXCoordinates, depotYCoordinates,
											 maxDistanceD * numberOfVehicles * daysServicedOver,
											 maxDemandQ * numberOfVehicles *
											 daysServicedOver);
	//creates a new temporary depot to be inserted in the depot linked list
	//calculates the total maximum distance for the depot by (singleDayMaxDistance)(numberVehicles)(numberDays)
	//calculates the total maximum demand for the entire depot by (maxDemandPerDay)(numberVehicles)(numberDays)
	mainDepots.insertDepotLast(depotTemporary);    //insert our newly read depot in the depot linked list








	depotTemporary = mainDepots.getHead().getNext();
	for(int i = 0; i < PVRPProblemInfo.noOfVehs; i++) {
		PVRPTruckType ttype = (PVRPTruckType) PVRPProblemInfo.truckTypes.elementAt(0);
		PVRPTruck truckTemp = new PVRPTruck(ttype, depotTemporary.getXCoord(), depotTemporary.getYCoord());
		truckTemp.setTruckType(ttype);
		//we only have one type of truck
		depotTemporary.getMainTrucks()
					  .insertTruckLast(new PVRPTruck(ttype, depotTemporary.getXCoord(), depotTemporary.getYCoord()));

		//insert the newly read truck data into the trucks linked list (which is underneath each depot read in; one in
		// our case)
	}


	PVRPDayLinkedList daysLL = mainDaysTemp;
	PVRPTruckLinkedList truckLL = mainDepots.getHead().getNext().getMainTrucks();
	//get the Truck linked list from inside mainDepots

	PVRPTruck truck = truckLL.getHead().getNext();
	//get the first truck from inside the truck list






	/** @todo Aaron_Comment */
	//steps through each depot and then truck to create the correct number of days for each
	for(int i = 0; i < depotTemporary.getMainTrucks().getSize(); i++) {
		PVRPDay tempDay = daysLL.getHead().getNext();

		while(tempDay != mainDaysTemp.getTail()) {    //while we aren't at the end of the list
			truck.getMainDays().insertLastDay(        //insert the day into the days linked list
													  new PVRPDay(tempDay.getNodes(), tempDay.getNumberTrucks(),
																  tempDay.getPlanningPeriod(),
																  tempDay.getMaxDistance(),
																  tempDay.getMaxDemand(), depotXCoordinates,
																  depotYCoordinates, tempDay.getDayNumber()));
			if(tempDay.getNext() == mainDaysTemp.getTail()) {
				//if we are at the last day in the list
				break;
			}
			else {
				//otherwise we have more days we can read in
				tempDay = tempDay.getNext();
			}
		}
		truck = truck.getNext();            //get the next truck in the list
	}







	Truck truckTemp = mainDepots.getHead().getNext().getMainTrucks().getHead();
	for(int i = 0; i < numberOfVehicles; i++) {
		truckTemp = truckTemp.getNext();
		Day day = truckTemp.getMainDays().getHead();
		//get the first day from the days linked list in the truck

		for(int x = 0; x < daysServicedOver; x++) {
			PVRPDay tempDay = (PVRPDay) day.getNext();
			day = day.getNext();
			//get the next day

			PVRPNodesLinkedList tempNLL = tempDay.getNodesLinkedList();

			tempNLL.setTruckType((PVRPTruckType) PVRPProblemInfo.truckTypes.get(0));
			tempNLL.setFeasibility(new PVRPFeasibility(tempNLL.getTruckType().getMaxDuration(),
													   tempNLL.getTruckType().getMaxCapacity(), tempNLL));
		}
	}









	while(row.getRowNum() > daysServicedOver && row.getRowNum() < finalRowNumber && rowIterator.hasNext()) {

		row = rowIterator.next();            //get the next row
		cellIterator = row.cellIterator();        //an iterator for columns
		cellColumnCounter = 0;                    //reset our column counter
		int combinationListIndex = 0;            //keep track of our index in the combinations list

		boolean hasEnteredRow = false;            //keeps from re-reading the last row several times over
		while(cellIterator.hasNext())            //while we have another cell
		{
			cell = cellIterator.next();            //get the next cell
			cell.setCellType(TYPE_NUMERIC);        //set the cell type to number
			float currentCellContents = (float) cell.getNumericCellValue();
			//get the numeric cell value as a float

			switch(cellColumnCounter) {
				//Allows us to step through each cell in a row
				case 0:
					nodeNumber = (int) currentCellContents;
					break;    //the node number for the node
				case 1:
					xCoordinates = (double) currentCellContents;
					break;    //the x coordinate for the node
				case 2:
					yCoordinates = (double) currentCellContents;
					break;    //the y coordinate for the node
				case 3:
					DUMMY = (double) currentCellContents;
					/** @todo what is this */
					//usually all zeros
					//haven't found a use for this
					break;
				case 4:
					demandQ = (int) currentCellContents;
					break;    //the demand for the node
				case 5:
					frequency = (int) currentCellContents;
					break;    //the number of days a node needs visited
				case 6:
					numberCombinations = (int) currentCellContents;
					break;    //the number of visit combinations a node allows/has
				default:
					//we default here because it's the last value and can have
					//varying sizes
					list[combinationListIndex] = (int) currentCellContents;
					//read the cell contents into the list of combinations
					combinationListIndex++;
					//next index for the next combination
					break;

			}
			hasEnteredRow = true;        //we've been in the FSM reading columns/cells
			cellColumnCounter++;        //go to the next cell/column
		}

		if(!hasEnteredRow) {
			//ensures we don't enter information if we
			//haven't been through the FSM to read new information
			//so we don't re-insert old information
			break;
		}






		for(int l = 0; l < numberCombinations; l++) {
			currentCombination[l] = mainShipments.getCurrentComb(list, l, daysServicedOver);
		}
		//this decodes each combination for a node into a simple to read array (1 == visit me on day X; 0 == don't
		// visit me today)









		Integer custType = (Integer) custTypes
				.elementAt(0);        //we only have on customer type so we can just get at index zero
		mainShipments
				.insertShipment(nodeNumber, xCoordinates, yCoordinates, DUMMY, demandQ, frequency, numberCombinations,
								list, currentCombination);
		//insert the just read shipment into the mainShipments list holds our all our problem info read in from Excel

	}

	return 1;
	//whoo! done with one method in one class
}//READ DATA FROM FILE ENDS
// HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




















//METHOD
//prints all shipments to the console
public void printDataToConsole() {
	try {
		mainShipments.printShipmentsToConsole();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
}//PRINT DATA TO CONSOLE ENDS
// HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//METHOD
//creates the initial routes for our problem
//because we don't have any local optimization our
//create initial routes is our only route creation/manipulation
//we have
public void createInitialRoutes()
		throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
	Settings.lockTrucks = true;				//we already created all the trucks we are allowed

	//VARIABLES
	PVRPDepot currentUsedDepot = null;        //current depot
	PVRPShipment currentUsedShip = null;        //current shipment
	PVRPShipment theShipment = null;


	if(PVRPProblemInfo.selectShipType == null) {
		Settings.printDebug(Settings.ERROR, "No selection shipment type has been assigned");
		//check if selection and insertion type methods have been selected
	}
	if(PVRPProblemInfo.insertShipType == null) {
		Settings.printDebug(Settings.ERROR, "No insertion shipment type has been assigned");
		//check if we selected an insertion shipment algorithm
	}


	//while we haven't assigned all the shipments
	while(!mainShipments.isAllShipsAssigned()) {
		currentUsedDepot = mainDepots.getHead().getNext();
		//we only have one depot so get next always gets the same depot

		theShipment = mainShipments.getNextInsertShipment(mainDepots, currentUsedDepot, mainShipments,
														  currentUsedShip);
		//Send the entire mainDepots and mainShipments to get the next shipment to be inserted including the current
		// depot



		if(theShipment.getFrequency() == 0){
			//this node is in the list but wouldn't
			//like to be serviced... p11.xlsx, node 131
			//does this

			theShipment.setAssigned(true);
			continue;
		}

		if(theShipment == null) {
			//shipment is null, print error message
			//we couldn't choose shipment
			Settings.printDebug(Settings.COMMENT, "No shipment was selected");
		}

        if(theShipment.getIndex() == 1){
            System.out.print("");
        }
		if(!mainDepots.insertShipment(theShipment)) {
			//The selected shipment couldn't be inserted in the selected location
			Settings.printDebug(Settings.COMMENT, "The Shipment: <" + theShipment.getIndex() +
												  "> cannot be routed ***********************************************");
			theShipment.setIsAssigned(true);
            theShipment.setCanBeRouted(false);
			//just bypass this and don't come back
			//WE NEED LOCAL OPTIMIZATION
		}
		else {
			Settings.printDebug(Settings.COMMENT, "The Shipment: <" + theShipment.getIndex() +// " " + theShipment +
												  "> was routed");
			theShipment.setIsAssigned(true);    //this shipment has been assigned and we won't go back to it
            theShipment.setCanBeRouted(true);
		}


	}


	PVRPProblemInfo.depotLLLevelCostF.calculateTotalsStats(mainDepots);
	//	calculate all the cost, distance, demand, etc. information related to the solution we just created
}//CREATE INITIAL ROUTES ENDS HERE*********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//METHOD
//this prints all the routes to the console
public void printRoutesToConsole() {

//VARIABLES
	int dayCount;
	int truckCount;
	int nodeCount;

	PVRPDepot theDepot = mainDepots.getHead().getNext();
	//get the first and only depot
	PVRPTruckLinkedList truckList = null;
	PVRPTruck theTruck = null;
	PVRPDayLinkedList daysList = null;
	PVRPDay theDay = null;
	PVRPNodesLinkedList nodesList = null;
	PVRPNodes theNode = null;
	PVRPShipment theShipment = null;







	while(theDepot != mainDepots.getTail()) {
		//while there are more depots

		truckList = theDepot.getMainTrucks();
		//get the trucks linked list inside of depot

		theTruck = truckList.getHead();
		//get the first truck from the truck linked list

		truckCount = 0;
		while(theTruck.getNext() != truckList.getTail()) {
			//while we have more trucks

			theTruck = theTruck.getNext();
			//get the next truck

			daysList = theTruck.getMainDays();
			//get the days linked list from the current truck

			theDay = daysList.getHead().getNext();
			//get the selected day from the linked list

			dayCount = 0;
			System.out.println("\nTruck " + truckCount);

			while(theDay.getNext() != daysList.getTail()) {
				//while there are more days in the linked list

				if(dayCount != 0) {
					theDay = theDay.getNext();
					//and dayCount isn't zero we get the next day
				}

				nodesList = theDay.getNodesLinkedList();
				//get the nodes linked list (the route) from the day

				theNode = nodesList.getHead().getNext();
				//get the first node

				nodeCount = 0;
				System.out.println("\nDay " + dayCount);
				//print the day count

				while(theNode.getNext() != nodesList.getTail()) {
					//while there are more nodes in the linked list
					if(nodeCount != 0) {
						//and there are more than zero nodes
						theNode = theNode.getNext();
						//get the next node
					}

					theShipment = theNode.getShipment();
					//get the shipment

					System.out.print(theShipment.getIndex() + "-");
					//print the index
					nodeCount++;
				}
				dayCount++;
			}
			truckCount++;
		}
		break;
	}
}//PRINT ROUTES TO CONSOLE ENDS HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
// HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//METHOD
//writes the short solution to our problem
public void writeShortSolution(String file)
		throws IOException {


//VARIABLES

	FileOutputStream out = null;
	FileInputStream in = null;

	XSSFWorkbook workbook = null;    //holds our workbook
	XSSFSheet sheet = null;            //holds our sheet
	Row row = null;                    //a row from our sheet
	Cell cell = null;                //a cell from our row

	boolean isSheetFound = false;    //sometimes we look for a specific sheet to edit

	PVRPDepot theDepot = null;

	int rowCounter = 0;       //keeps track of the current row we are editing
	int columnCounter = 0;    //keeps track of the current column we are editing
	int dayNumberTemp = 0;    //counter to assign numbers to days under trucks

	PVRPTruckLinkedList truckList = null;
	PVRPTruck theTruck = null;
	PVRPDayLinkedList daysLinkedList = null;
	PVRPDay theDay = null;
	PVRPNodesLinkedList nodesList = null;
	PVRPNodes theNode = null;
	PVRPShipment theShipment = null;







	theDepot = mainDepots.getHead();
	//get the head of the depots linked list

	if(!isMakeSeparateFile) {	//are we adding this to one big ultimate file
								//or will each problem have its own file
		//if the file we want to write to already exists then take
		//proper measures to update it
		File theFile = new File(ProblemInfo.outputPath + PVRPProblemInfo.shortSolutionOutputFile);
		
		if (theFile.exists() && !theFile.isDirectory()) {
			in = new FileInputStream(new File(ProblemInfo.outputPath + PVRPProblemInfo.shortSolutionOutputFile));
			//create a file input stream for the existing file

			workbook = new XSSFWorkbook(in);
			//re-create all workbooks based on existing file

			out = new FileOutputStream(new File((ProblemInfo.outputPath + PVRPProblemInfo.shortSolutionOutputFile)));
			//setup output file


			for (int x = 0; x < workbook.getNumberOfSheets(); x++) {
				if (workbook.getSheetAt(x).getSheetName().equals(file)) {
					isSheetFound = true;
					sheet = workbook.getSheetAt(x);
					break;
				}
			}
			//do we have a problem already in this workbook as a sheet
			//if so, edit it instead of creating a new one

		} else {
			//otherwise we must start from scratch as the file does not exist
			out = new FileOutputStream(new File(ProblemInfo.outputPath + PVRPProblemInfo.shortSolutionOutputFile));
			workbook = new XSSFWorkbook();
		}


		//if we didn't find the sheet or if we are making a new file create a sheet
		//with the file/problem name
		if (!isSheetFound) {
			sheet = workbook.createSheet(file);
		}
	}
	else{
		out = new FileOutputStream(new File(ProblemInfo.outputPath +  "short_solution_" + file));
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet(file);
	}
	//START WRITING TO THE FILE
	//this get messy but is pretty simple
	theDepot = theDepot.getNext();                //get the first (only) depot
	row = sheet.createRow(rowCounter++);        //create a row at rowCounter and increment the counter
	cell = row.createCell(0);                //create the 0 cell/column
	cell.setCellValue("Filename");            //set the cell to the text

	cell = row.createCell(1);                //create the 1 cell/column
	cell.setCellValue(file);                //set to the name of our problem/file

	row = sheet.createRow(rowCounter++);        //create a new row
	cell = row.createCell(0);                //create column at 0
	cell.setCellValue("Selection Type");    //set value to text

	cell = row.createCell(1);                //create column at zero
	cell.setCellValue(
			ProblemInfo.selectShipType.getClass().getCanonicalName().replace("edu.sru.thangiah.zeus.pvrp.PVRP", ""));
	//set the value to the select shipment type while removing all the class location information

	row = sheet.createRow(rowCounter++);        //create a new row
	cell = row.createCell(0);                //create column at 0
	cell.setCellValue("Insertion Type");    //set text

	cell = row.createCell(1);                //create column at 1
	cell.setCellValue(
			ProblemInfo.insertShipType.getClass().getCanonicalName().replace("edu.sru.thangiah.zeus.pvrp.PVRP", ""));
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

		truckList = theDepot.getMainTrucks();
		//get the truck linked list from the depot
		theTruck = truckList.getHead().getNext();
		//get the head of the truck linked list

		while(theTruck != truckList.getTail()) {
			//while we have more trucks
			//we get the next truck at the bottom of this while

			daysLinkedList = theTruck.getMainDays();
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

				nodesList = theDay.getNodesLinkedList();
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
//writes a detailed report of the solution
public void writeLongSolution(String file)
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

	PVRPTruckLinkedList truckList;
	PVRPTruck theTruck;
	PVRPDayLinkedList daysList;
	PVRPDay theDay;
	PVRPNodesLinkedList nodesList;
	PVRPNodes theNode;
	PVRPShipment theShipment;






	PVRPDepot theDepot = mainDepots.getHead();

	if(!isMakeSeparateFile) {
		File theFile = new File(ProblemInfo.outputPath + PVRPProblemInfo.longSolutionOutputFile);
		//Does the file already exist?
		if(theFile.exists() && !theFile.isDirectory()) {
			//if so, prepare to just edit/modify the file
			in = new FileInputStream(new File(ProblemInfo.outputPath + PVRPProblemInfo.longSolutionOutputFile));
			workbook = new XSSFWorkbook(in);
			out = new FileOutputStream(new File((ProblemInfo.outputPath + PVRPProblemInfo.longSolutionOutputFile)));

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
			out = new FileOutputStream(new File(ProblemInfo.outputPath + PVRPProblemInfo.longSolutionOutputFile));
			workbook = new XSSFWorkbook();
		}


		if(!isSheetFound) {
			//if we didn't find a matching sheet or this is a new file
			//make a new sheet with the file name
			sheet = workbook.createSheet(file);
		}
	}
	else{
		out = new FileOutputStream(new File(ProblemInfo.outputPath + "long_solution_" + file));
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
	row = sheet.createRow(rowCounter++);    //create new row
	cell = row.createCell(0);            //create column 0
	cell.setCellValue("Filename");        //set cell to string
	cell.setCellStyle(ITALICS_STLYE);        //italicize this cell
	cell.setCellStyle(CENTER_STYLE);        //center this cell

	cell = row.createCell(1);
	cell.setCellValue(file);            //set the file name


	//NEW ROW
	row = sheet.createRow(rowCounter++);
	cell = row.createCell(0);
	cell.setCellValue("Selection Type");    //set text
	cell.setCellStyle(ITALICS_STLYE);        //set style

	cell = row.createCell(1);
	cell.setCellValue(
			ProblemInfo.selectShipType.getClass().getCanonicalName().replace("edu.sru.thangiah.zeus.pvrp.PVRP", ""));
	//set text to select ship type to class name without the class location information


	//NEW ROW
	row = sheet.createRow(rowCounter++);
	cell = row.createCell(0);
	cell.setCellValue("Insertion_Type");    //set text
	cell.setCellStyle(ITALICS_STLYE);        //set style

	cell = row.createCell(1);
	cell.setCellValue(ProblemInfo.insertShipType.getClass().getCanonicalName()
														 .replace("edu.sru.thangiah.zeus.pvrp" + ".PVRP", ""));
	//set text to select ship type to class name without the class location information


	//NEW ROW
	row = sheet.createRow(rowCounter++);
	cell = row.createCell(0);
	cell.setCellValue("Number_Depots");    //set text
	cell.setCellStyle(ITALICS_STLYE);    //set style

	cell = row.createCell(1);
	cell.setCellValue(PVRPProblemInfo.numDepots);
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

		truckList = theDepot.getMainTrucks();    //get truck linked list
		theTruck = truckList.getHead().getNext();            //get the first truck


		row = sheet.createRow(rowCounter++);        //make a new row
		while(theTruck != truckList.getTail()) {
			//while we have more trucks
			//get new truck at bottom of this while

			daysList = theTruck.getMainDays();
			//get the days linked list for the current truck

			theDay = daysList.getHead().getNext();


			int dayCounter = 0;
			while(theDay != daysList.getTail()) {
				//while we have more days in the current truck

				nodesList = theDay.getNodesLinkedList();
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
				cell.setCellValue(theDepot.getDepotNum());    //set number
				cell.setCellStyle(BOLD_CENTER_STYLE);        //set style

				cell = row.createCell(columnCounter++);
				cell.setCellValue("X:");                    //set text
				cell.setCellStyle(BOLD_STYLE);                //set style

				cell = row.createCell(columnCounter++);
				cell.setCellValue(theDepot.getXCoord());    //set number
				cell.setCellStyle(BOLD_CENTER_STYLE);        //set style

				cell = row.createCell(columnCounter++);
				cell.setCellValue("Y:");                    //set text
				cell.setCellStyle(BOLD_STYLE);                //set style

				cell = row.createCell(columnCounter++);
				cell.setCellValue(theDepot.getYCoord());    //set number
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
				cell.setCellValue(theTruck.getTruckNum());
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
				double maxDistance = ((PVRPTruckType) PVRPProblemInfo.truckTypes.elementAt(0)).getMaxDuration();
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

				double previousX = theNode.getShipment().getXCoord();
				double previousY = theNode.getShipment().getYCoord();

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





					cell = row.createCell(columnCounter++);
					double distanceFromDepot =
							calculateDistance(theDepot.getXCoord(), theShipment.getxCoord(), theDepot.getYCoord(),
											  theShipment.getyCoord());
					cell.setCellValue(distanceFromDepot);
					//distance from depot can be a great way to show the closest euclidean distance is working




					cell = row.createCell(columnCounter++);
					double angle = calculateAngle(theShipment.getXCoord(), theDepot.getXCoord(),
							theShipment.getYCoord(), theDepot.getYCoord());
					cell.setCellValue(angle);
					//distance from depot can be a great way to show the closest euclidean distance is working




					cell = row.createCell(columnCounter++);
					double distanceFromLast =
							calculateDistance(previousX, theShipment.getxCoord(), previousY,
									theShipment.getyCoord());
					cell.setCellValue(distanceFromLast);
					//show the linear distance we are from the last node






					cell = row.createCell(columnCounter++);
					cell.setCellValue(theShipment.getFrequency());    //the frequency the node wants visited

					if(theNode == nodesList.getTail()) {
						//if we are at the tail node (and have printed it already)
						//break out of the while loop
						break;
					}

					previousX = theShipment.getxCoord();
					previousY = theShipment.getyCoord();

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
// HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//METHOD
//this method prints an excel file which compares this program's
//solution(s) to that of known research
public void compareResults(String filename)
		throws IOException, InvalidFormatException {

//VARIABLES
	File theFile = new File(ProblemInfo.outputPath + PVRPProblemInfo.comparisonOutputFile);

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
				new FileInputStream(new File(ProblemInfo.outputPath + PVRPProblemInfo.comparisonOutputFile));
		workbook = new XSSFWorkbook(in);
		out = new FileOutputStream(new File((ProblemInfo.outputPath + PVRPProblemInfo.comparisonOutputFile)));
		outputSheet = workbook.getSheetAt(0);
		didFileExist = true;
	}
	else {
		//otherwise the file doesn't exist so set up everything to create a new file
		out = new FileOutputStream(new File(ProblemInfo.outputPath + PVRPProblemInfo.comparisonOutputFile));
		workbook = new XSSFWorkbook();
		outputSheet = workbook.createSheet("Comparisons");
		didFileExist = false;
	}


//INPUT VARIABLES
	File input = new File(PVRPProblemInfo.compareToInputPath + PVRPProblemInfo.compareToInputFile);
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

		if(problemName.equals(filename.replace(".xlsx", ""))) {
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




//METHOD
//this method calculates the euclidean distance between two points of data
public double calculateDistance(double x1, double x2, double y1, double y2) {
	double xs = (x2 - x1) * (x2 - x1);        //no, this isn't haskell
	double ys = (y2 - y1) * (y2 - y1);
	return sqrt(xs + ys);                    //return the rooted solution
}//CALCULATE DISTANCE ENDS
// HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



//METHOD
//this method calculates the angle between two points of data
	public double calculateAngle(double x1, double x2, double y1, double y2) {
		double xs = (x2 - x1);        //no, this isn't haskell
		double ys = (y2 - y1);
		return atan2(ys, xs);
	}//CALCULATE DISTANCE ENDS
// HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



//METHOD
//this method writes out a data file of the shipments
//linked list in Excel but is no longer needed due to
//long and short solution methods
public void writeDataFile(String file)
		throws IOException {

	FileOutputStream out = new FileOutputStream(new File(file));
	System.out.print("WRITING DATA FILE");
	mainDepots.writeOutData(out);
}//WRITE DATA FILE ENDS

} //End of PVRP file