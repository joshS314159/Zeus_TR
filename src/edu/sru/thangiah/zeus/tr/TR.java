package edu.sru.thangiah.zeus.tr;

//IMPORTS


import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.core.Settings;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Insertion.TRGreedyInsertion;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.*;
import edu.sru.thangiah.zeus.tr.trQualityAssurance.TRQA;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
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
public class TR {

private final int TYPE_NUMERIC = 0;
//track the CPU processing time
private final int TYPE_STRING  = 1;
//CLASS VARIABLES
private long startTime, endTime;

private TRShipmentsList mainShipments = new TRShipmentsList();

private TRDepotsList mainDepots = new TRDepotsList();
//depots linked list for the VRP problem

private boolean isMakeSeparateFile;


//CONSTRUCTOR
//this constructor will convert a width-delimited file into a XLSX file which will allow PVRP to read it




//CONSTRUCTOR
//this constructor is called by PVRPRoot and launches reading in data, routing vehicles, and printing out information
public TR(String dataFile, boolean isMakeSeparateFile, Object selectionTypeObject)
		throws IOException, InvalidFormatException, IllegalAccessException, InvocationTargetException,
			   InstantiationException, NoSuchMethodException {

	this.isMakeSeparateFile = isMakeSeparateFile;

	//VARIABLES
	TRProblemInfo.truckTypes = new Vector();
	//Truck types are placed into a vector

	//READ DATA
	readDataFromFile(TRProblemInfo.inputPath + dataFile);                          //reads data from file
	Settings.printDebug(Settings.COMMENT,
						"Read Data File: " + TRProblemInfo.inputPath + dataFile);    //store some debug data
	printDataToConsole();
	//prints the routes to the console


	//PROCESS PREPARATION

	if(mainShipments.getHead() == null) {
		Settings.printDebug(Settings.ERROR, "TR: Shipment linked list is empty");
	}
	//If the shipments linked list (our problem data) isn't null


	TRProblemInfo.selectShipType = selectionTypeObject;

	//		Settings.printDebug(Settings.COMMENT, selectionTypeObject.WhoAmI());

	//	PVRPProblemInfo.selectShipType = new PVRPClosestEuclideanDistToDepot();
	//	Settings.printDebug(Settings.COMMENT, PVRPClosestEuclideanDistToDepot.WhoAmI());
	//This sets our heuristic to choose the next shipment
	//there are several more commented out under this

	//	PVRPProblemInfo.selectShipType = new PVRPSmallestPolarAngleToDepot();
	//	Settings.printDebug(Settings.COMMENT, PVRPSmallestPolarAngleToDepot.WhoAmI());

	//	PVRPProblemInfo.selectShipType = new PVRPSmallestPolarAngleShortestDistToDepot();
	//	Settings.printDebug(Settings.COMMENT, PVRPSmallestPolarAngleShortestDistToDepot.WhoAmI());


	TRProblemInfo.insertShipType = new TRGreedyInsertion();
	Settings.printDebug(Settings.COMMENT, TRGreedyInsertion.WhoAmI());
	//Sets up our shipment insertion type -- we only have one hueristic for this


	startTime = System.currentTimeMillis();
	//We wanna time how long it takes to create our solution
	//Start time records the starting time of processing


	//PROCESSING
	createInitialRoutes();
	printRoutesToConsole();
	//this creates our initial solution routes
	//although this code doesn't have it, local optimization
	//would improve upon the initial routes created here


	//QUALITY CHECK
	Settings.printDebug(Settings.COMMENT, "Created Initial Routes ");
	//Get the initial solution
	//Depending on the Settings status, display information on the routes
	//Trucks used, total demand, dist, travel time and cost


	System.out.println("Starting QA");
	//creates a new object that checks out quality
	if(new TRQA(mainDepots, mainShipments).runQA() == false) {
		//if QA failed -- bad quality
		Settings.printDebug(Settings.ERROR, "SOME QA FAILED!");
	}
	else {
		//if QA was a success -- solution created properly
		Settings.printDebug(Settings.COMMENT, "QA succeeded");
	}
	//Check for the quality and integrity of the solution

	System.out.println("Routes that cannot be routed due to lack of local optimization:");
	TRShipment aShipment = mainShipments.getHead();
	while(aShipment != mainShipments.getTail()) {
		if(!aShipment.getCanBeRouted()) {
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


//public PVRP(String dataFile, boolean isMakeSeparateFile, Object selectionTypeObject, int moreStuffHere){}




public int readDataFromFile(final String TRFileName)
		throws IOException, InvalidFormatException, InstantiationException, IllegalAccessException,
			   InvocationTargetException {
	final int LOCATION = 0;
	final int PICKUP_POINT = 1;
	final int LATITUDE = 2;
	final int LONGITUDE = 3;
	final int FREQUENCY = 4;
	final int BINS = 5;
	final int DAY_ONE = 6;
	final int DAY_TWO = 7;
	final int DAY_THREE = 8;
	final int DAY_FOUR = 9;
	final int DAY_FIVE = 10;
	final int DAY_SIX = 11;
	final int BUILDING_TYPE = 12;
	final int CLASSES_NEARBY = 13;
	final int FOOD_NEARBY = 14;
	final int PICKUP_ORDER = 15;
	final int NUMBER_COLUMNS = 16;

	final int NUMBER_NON_NODE_ROWS = 2;

	final String TIP_CART = "tip-cart";

	final FileInputStream file = new FileInputStream(new File(TRFileName));

	XSSFWorkbook workbook = new XSSFWorkbook(file);
	XSSFSheet sheet = workbook.getSheetAt(0);
	Row row;
	Cell cell;


	TRProblemInfo.noOfShips = sheet.getPhysicalNumberOfRows() - NUMBER_NON_NODE_ROWS;
	int wut = TRProblemInfo.noOfShips;
	for(int rowCounter = 1; rowCounter < sheet.getPhysicalNumberOfRows(); rowCounter++) {
		if(rowCounter == 52) {
			int i = 5;
			int sumthin = sheet.getPhysicalNumberOfRows();
			int ere = 9;
		}
		row = sheet.getRow(rowCounter);
		TRShipment newShipment = new TRShipment();
		double latitude = Double.MAX_VALUE;

		for(int columnCounter = 0; columnCounter < NUMBER_COLUMNS; columnCounter++) {
			cell = row.getCell(columnCounter);

			switch(columnCounter) {
				case LOCATION:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					newShipment.setPickupPointName(cell.getStringCellValue());
					break;
				case PICKUP_POINT:
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					//					int test = (int) cell.getNumericCellValue();
					newShipment.setNodeNumber((int) cell.getNumericCellValue());
					//					newShipment.setNodeNumber(test);
					break;
				case LATITUDE:
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					latitude = cell.getNumericCellValue();
					//					newShipment.setLatitude(cell.getNumericCellValue());
					break;
				case LONGITUDE:
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					double longitude = cell.getNumericCellValue();
					newShipment.getCoordinates().setCoordinates(longitude, latitude);
					break;
				case FREQUENCY:
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					newShipment.setVisitFrequency((int) cell.getNumericCellValue());
					break;
				case BINS:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					if(cell.getStringCellValue().equals(TIP_CART)) {
						newShipment.setIsTipCart(true);
						newShipment.setNumberOfBins(-1);
					}
					else {
						newShipment.setIsTipCart(false);
						int numberBins = Integer.parseInt(cell.getStringCellValue());
						newShipment.setNumberOfBins(numberBins);
					}
					break;
				case DAY_ONE:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					newShipment.setSingleDayVisitation(cell.getStringCellValue());
					break;
				case DAY_TWO:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					newShipment.setSingleDayVisitation(cell.getStringCellValue());
					break;
				case DAY_THREE:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					newShipment.setSingleDayVisitation(cell.getStringCellValue());
					break;
				case DAY_FOUR:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					newShipment.setSingleDayVisitation(cell.getStringCellValue());
					break;
				case DAY_FIVE:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					newShipment.setSingleDayVisitation(cell.getStringCellValue());
					break;
				case DAY_SIX:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					newShipment.setSingleDayVisitation(cell.getStringCellValue());
					break;
				case BUILDING_TYPE:
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					newShipment.setBuildingType((int) cell.getNumericCellValue());
					break;
				case CLASSES_NEARBY:
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					newShipment.setNumberOfNearbyClasses((int) cell.getNumericCellValue());
					break;
				case FOOD_NEARBY:
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					newShipment.setNumberOfNearbyFoods((int) cell.getNumericCellValue());
					break;
				case PICKUP_ORDER:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					if(cell.getStringCellValue().equals("None")) {
						newShipment.setPickupOrder(cell.getStringCellValue());
					}
					else {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						newShipment.setRequiredPreviousPickupPoint((int) cell.getNumericCellValue());
					}
					break;
				default:
					break;
			}
		}
		//		newShipment.setVisitationCombination();
		//		try {
		System.out.println("Node Counter: " + rowCounter);
		mainShipments.insertAfterLastIndex(newShipment);
		//		}
		//		catch(IllegalAccessException e) {
		//			e.printStackTrace();
		//		}
		//		catch(InstantiationException e) {
		//			e.printStackTrace();
		//		}
	}
	System.out.println("Setting up depot");

	//	mainShipments.getTail().getPrev().rem
	TRShipment depotShipment = mainShipments.getLast();
	TRDepot theDepot = new TRDepot(depotShipment.getCoordinates());
	mainShipments.removeByObject(depotShipment);
	//	int position = mainShipments.getIndexOfObject(mainShipments.getLast());
	//	mainShipments.removeByIndex(position);
	mainDepots.insertAfterLastIndex(theDepot);

	//	for(int x = 0; x < TRProblemInfo.NUMBER_TRUCKS; x++){
	//	mainDepots.getFirst().setSubList(new TRTrucksList());
	//	Object test = mainDepots.getFirst().getSubList();
	//	if(test == null){
	//		System.out.print("wtf dude");
	//	}


	TRTruck theTruck = new TRTruck();
	TRDepot firstDepot = mainDepots.getFirst();
	TRTrucksList truckList = firstDepot.getSubList();
	truckList.insertAfterLastIndex(theTruck);
	theTruck = truckList.getFirst();
	//		mainDepots.getFirst().getSubList().insertAfterLastIndex(new TRTruck());

	TRDaysList daysList = theTruck.getSubList();
	for(int x = 0; x < TRProblemInfo.NUMBER_DAYS_SERVICED; x++) {
		TRDay temporaryDay = new TRDay();
        temporaryDay.setDayNumber(x);
		temporaryDay.getSubList().setStartEndDepot(theDepot, theDepot);
		temporaryDay.getSubList().setTruckType(new TRTruckType());
		daysList.insertAfterLastIndex(temporaryDay);

	}


	//	}
	//	mainDepots

	return -1;
}




//METHOD
//prints all shipments to the console
public void printDataToConsole() {
	try {
		//		mainShipments.printShipmentsToConsole();
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
	Settings.lockTrucks = true;                //we already created all the trucks we are allowed

	//VARIABLES
	TRDepot currentUsedDepot = null;        //current depot
	TRShipment currentUsedShip = null;        //current shipment
	TRShipment theShipment = null;


	if(TRProblemInfo.selectShipType == null) {
		Settings.printDebug(Settings.ERROR, "No selection shipment type has been assigned");
		//check if selection and insertion type methods have been selected
	}
	if(TRProblemInfo.insertShipType == null) {
		Settings.printDebug(Settings.ERROR, "No insertion shipment type has been assigned");
		//check if we selected an insertion shipment algorithm
	}


	//while we haven't assigned all the shipments
	while(!mainShipments.isAllShipsAssigned()) {
		currentUsedDepot = mainDepots.getFirst();
		//we only have one depot so get next always gets the same depot

		theShipment = mainShipments.getNextInsertShipment(mainDepots, currentUsedDepot, mainShipments,
														  currentUsedShip);
		//		TRProblemInfo.selectShipType
		//Send the entire mainDepots and mainShipments to get the next shipment to be inserted including the current
		// depot


		if(theShipment.getFrequency() == 0) {
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
/*
		if (theShipment.getIndex() == 1) {
			System.out.print("");
		}
		*/
//		if(!mainDepots.insertShipment(theShipment)) {
        if(!insertShipment(mainDepots, theShipment)){
			//The selected shipment couldn't be inserted in the selected location
			Settings.printDebug(Settings.COMMENT, "The Shipment: <" + theShipment.getNodeNumber() +
												  "> cannot be routed " +
												  "***********************************************");
			//			theShipment.setIsAssigned(true);
			//			theShipment.setCanBeRouted(false);
			//just bypass this and don't come back
			//WE NEED LOCAL OPTIMIZATION
		}
		else {
			Settings.printDebug(Settings.COMMENT, "The Shipment: <" + theShipment.getNodeNumber() +// " " + theShipment +
												  "> was routed");
			theShipment.setIsAssigned(true);    //this shipment has been assigned and we won't go back to it
			theShipment.setCanBeRouted(true);
		}


	}


	TRProblemInfo.depotLLLevelCostF.calculateTotalsStats(mainDepots);
	//	calculate all the cost, distance, demand, etc. information related to the solution we just created
}//CREATE INITIAL ROUTES ENDS
// HERE*********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    private boolean insertShipment(TRDepotsList depotsList, TRShipment insertMe){
        stepThroughDepots(depotsList, insertMe);
        return true;
    }

    private void stepThroughDepots(TRDepotsList depotsList, TRShipment insertMe) {
        TRDepot theDepot = depotsList.getFirst();

        while (theDepot != depotsList.getTail()) {
            stepThroughTrucks(theDepot.getSubList(), insertMe);
            theDepot = theDepot.getNext();
        }
    }


    private void stepThroughTrucks(TRTrucksList trucksList, TRShipment insertMe) {
        TRTruck theTruck = trucksList.getFirst();

        while (theTruck != trucksList.getTail()) {
            stepThroughDays(theTruck.getSubList(), insertMe);
            theTruck = theTruck.getNext();
        }
    }

    private void stepThroughDays(TRDaysList daysList, TRShipment insertMe) {

        for(int i = 0; i < daysList.getSize(); i++){
            if(insertMe.getDaysVisited()[i]){
                appendShipment((TRNodesList) daysList.getAtIndex(i).getSubList(), insertMe);
            }
        }
    }

    private void appendShipment(TRNodesList nodesList, TRShipment insertMe){
        nodesList.insertShipment(insertMe);
    }






























//METHOD
//this prints all the routes to the console
public void printPVRPRoutesToConsole() {

	//VARIABLES
	int dayCount;
	int truckCount;
	int nodeCount;

	TRDepot theDepot = mainDepots.getHead().getNext();
	//get the first and only depot
	TRTrucksList truckList = null;
	TRTruck theTruck = null;
	edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRDaysList daysList = null;
	TRDay theDay = null;
	TRNodesList nodesList = null;
	TRNode theNode = null;
	TRShipment theShipment = null;


	while(theDepot != mainDepots.getTail()) {
		//while there are more depots

		truckList = theDepot.getSubList();
		//get the trucks linked list inside of depot

		theTruck = truckList.getHead();
		//get the first truck from the truck linked list

		truckCount = 0;
		while(theTruck.getNext() != truckList.getTail()) {
			//while we have more trucks

			theTruck = theTruck.getNext();
			//get the next truck

			daysList = theTruck.getSubList();
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

				nodesList = theDay.getSubList();
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

					System.out.print(theShipment.getNodeNumber() + "-");
					//print the index
					nodeCount++;
				}
				dayCount++;
			}
			truckCount++;
		}
		break;
	}
}//PRINT ROUTES TO CONSOLE ENDS
// HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



public void printRoutesToConsole(){
    System.out.println("\n|_____THE ROUTE___________________________________________\n");

    final TRDepotsList depotsList = mainDepots;
    final TRDepot depotTail = depotsList.getTail();
    TRDepot depotStepper = depotsList.getFirst();

    while(depotStepper != depotTail){
        System.out.println("DEPOT #" + depotStepper.getDepotNum());

        final TRTrucksList truckList = depotStepper.getSubList();
        final TRTruck truckTail = truckList.getTail();
        TRTruck truckStepper = truckList.getFirst();

        while(truckStepper != truckTail){
            System.out.println("\tTRUCK #" + truckStepper.getTruckNum());

            final TRDaysList daysList = truckStepper.getSubList();
            final TRDay dayTail = daysList.getTail();
            TRDay dayStepper = daysList.getFirst();

            while(dayStepper != dayTail){
                System.out.println("\t\tDAY #" + dayStepper.getDayNumber());

                final TRNodesList nodesList = dayStepper.getSubList();
                final TRNode nodeTail = nodesList.getTail();
                TRNode nodeStepper = nodesList.getFirst();

                System.out.print("\t\t\t");
                while(nodeStepper != nodeTail && nodeStepper != null) {
                    System.out.print(nodeStepper.getShipment().getNodeNumber() + "-");
                    nodeStepper = nodeStepper.getNext();
                }
                System.out.println();
                dayStepper = dayStepper.getNext();
            }
            truckStepper = truckStepper.getNext();
        }
        depotStepper = depotStepper.getNext();
    }


}


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
			in = new FileInputStream(new File(ProblemInfo.outputPath + TRProblemInfo.shortSolutionOutputFile));
			//create a file input stream for the existing file

			workbook = new XSSFWorkbook(in);
			//re-create all workbooks based on existing file

			out = new FileOutputStream(new File((ProblemInfo.outputPath + TRProblemInfo.shortSolutionOutputFile)));
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
			out = new FileOutputStream(new File(ProblemInfo.outputPath + TRProblemInfo.shortSolutionOutputFile));
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

				//				theDay.setDayNumber(dayNumberTemp++);
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
				cell.setCellValue(theDay.getIndex());            //set the new cell to the day number associated with


				cell = row.createCell(columnCounter++);
				cell.setCellValue(theDay.getAttributes()
										.getTotalDemand());            //set the new cell to the day number associated
										// with


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
			in = new FileInputStream(new File(ProblemInfo.outputPath + TRProblemInfo.longSolutionOutputFile));
			workbook = new XSSFWorkbook(in);
			out = new FileOutputStream(new File((ProblemInfo.outputPath + TRProblemInfo.longSolutionOutputFile)));

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
			out = new FileOutputStream(new File(ProblemInfo.outputPath + TRProblemInfo.longSolutionOutputFile));
			workbook = new XSSFWorkbook();
		}


		if(!isSheetFound) {
			//if we didn't find a matching sheet or this is a new file
			//make a new sheet with the file name
			sheet = workbook.createSheet(file);
		}
	}
	else {
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

				if(usedDemand <= maxDemand) {    //is the used demand valid?
					cell.setCellStyle(PASSED_STYLE);    //if so color the cell green
				}
				else {
					cell.setCellStyle(FAILED_STYLE);    //else, color it red
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
				double maxDistance = ((TRTruckType) TRProblemInfo.truckTypes.elementAt(0)).getMaxDuration();
				cell.setCellValue(maxDistance);
				cell.setCellStyle(BOLD_CENTER_STYLE);

				cell = row.createCell(columnCounter++);
				cell.setCellValue("Used Distance:");
				cell.setCellStyle(BOLD_STYLE);

				cell = row.createCell(columnCounter++);
				double usedDistance = theDay.getAttributes().getTotalDistance();
				cell.setCellValue(usedDistance);
				//				cell.setCellStyle(BOLD_CENTER_STYLE);

				if(usedDistance <= maxDistance) {        //if used distance is valid
					cell.setCellStyle(PASSED_STYLE);    //color the cell green
				}
				else {
					cell.setCellStyle(FAILED_STYLE);    //else color the cell red
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
					double angle =
							calculateAngle(theShipment.getXCoord(), theDepot.getXCoord(), theShipment.getYCoord(),
										   theDepot.getYCoord());
					cell.setCellValue(angle);
					//distance from depot can be a great way to show the closest euclidean distance is working


					cell = row.createCell(columnCounter++);
					double distanceFromLast =
							calculateDistance(previousX, theShipment.getxCoord(), previousY, theShipment.getyCoord());
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

				row = sheet.createRow(rowCounter++);    //create a cell
				cell = row.createCell(0);
				cell.setCellType(TYPE_STRING);
				cell.setCellValue("Excel Summations");    //with this text
				cell.setCellStyle(BOLD_STYLE);


				//CELL EQUATION TO DO SUMMATION OF ALL DEMAND FOR THIS ROUTE
				cell = row.createCell(1);            //create a cell
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(demandFormula);    //set the demand formula
				cell.setCellStyle(BOLD_STYLE);

				//evaluation all formulas in the spreadsheet so we can compare values
				workbook.getCreationHelper().createFormulaEvaluator().evaluateAll();

				if((int) usedDemand == (int) cell.getNumericCellValue()) {
					//if our two demands match, then color the cell green
					cell.setCellStyle(PASSED_STYLE);
				}
				else {
					cell.setCellStyle(FAILED_STYLE);
				}


				//CELL EQUATION TO DO SUMMATION OF ALL DISTANCE FOR THIS ROUTE
				cell = row.createCell(4);
				cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(distanceFormula);
				cell.setCellStyle(BOLD_STYLE);

				//evaluation all formulas in the spreadsheet so we can compare values
				workbook.getCreationHelper().createFormulaEvaluator().evaluateAll();

				if((int) usedDistance == (int) cell.getNumericCellValue()) {
					//if our two demands match, then color the cell green
					cell.setCellStyle(PASSED_STYLE);
				}
				else {
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
		//otherwise the file doesn't exist so set up everything to create a new file
		out = new FileOutputStream(new File(ProblemInfo.outputPath + TRProblemInfo.comparisonOutputFile));
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