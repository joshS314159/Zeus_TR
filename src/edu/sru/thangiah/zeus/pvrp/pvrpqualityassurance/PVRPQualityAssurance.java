//PVRP PROBLEM
//CPSC 464
//AARON ROCKBURN; JOSHUA SARVER

//***********	DECLARATION_S_OTHER
// **********************************************************************************\\
// FUNCTION_START >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//PACKAGE TITLE
package edu.sru.thangiah.zeus.pvrp.pvrpqualityassurance;


//IMPORTS

//import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;


import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.pvrp.PVRPDepotLinkedList;
import edu.sru.thangiah.zeus.pvrp.PVRPShipmentLinkedList;
import edu.sru.thangiah.zeus.qualityassurance.QualityAssurance;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;


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
public class PVRPQualityAssurance
		extends QualityAssurance {

private final int TYPE_NUMERIC = 0;
private final int TYPE_STRING = 1;
//VARIABLES
PVRPDepotLinkedList      mainDepots;
PVRPShipmentLinkedList   mainShipments;
PVRPQADepotLinkedList    pvrpQADepots;
PVRPQAShipmentLinkedList pvrpQAShipments;
File                     shipmentFile;
//poi, our excel reader, sometimes must set the cell type
File                     solutionFile;
//poi, our excel reader, sometimes must set the cell type




//CONSTRUCTOR
public PVRPQualityAssurance(PVRPDepotLinkedList mainDepots, PVRPShipmentLinkedList mainShipments)
		throws IOException {
	this.mainDepots = mainDepots;
	this.mainShipments = mainShipments;
	pvrpQAShipments = new PVRPQAShipmentLinkedList();
	pvrpQADepots = new PVRPQADepotLinkedList();

	//Write out the information that is in the data structures. This does not read the original file
	//Might need another function that reads in the original files and checks if they are correct


	writeTempFiles();
	readShipmentFile();
	readSolutionFile();
}//PVRP_QA ENDS HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//METHOD
//this method writes files to our TEMP folder
//it writes the list of shipments and the current solution
//then QA reads them back in for when doing data comparisons, etc.
public void writeTempFiles() {
	FileOutputStream out = null;
	try {
		//prepare to print out our TEMP shipment file
		shipmentFile = new File(ProblemInfo.tempFileLocation + "/Temp_PVRP_Shipments.xlsx");
		out = new FileOutputStream(shipmentFile);
		mainShipments.writeShipments(out);            //write the shipments list


		//prepare to print out our TEMP solution file
		solutionFile = new File(ProblemInfo.tempFileLocation + "/Temp_PVRP_Solutions.xlsx");
		out = new FileOutputStream(solutionFile);
		mainDepots.printDepotLinkedList(out);        //write solution file
	}
	catch(IOException ioe) {
		ioe.printStackTrace();
	}
	finally {
		try {
			if(out != null) {
				out.close();
			}
		}
		catch(Exception ioe) {
			ioe.printStackTrace();
		}
	}
}//WRITE_FILES ENDS HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//METHOD
//reads in the shipment list file we printed in
//'writeTempFiles'
public void readShipmentFile()
		throws IOException {
	//VARIABLES
	FileInputStream file = new FileInputStream(new File(String.valueOf(shipmentFile)));
	XSSFWorkbook workbook = new XSSFWorkbook(file);        //create a workbook
	XSSFSheet sheet = workbook.getSheetAt(0);            //get a worksheet
	Iterator<Row> rowIterator = sheet.iterator();        //an iterator for rows
	Cell cell;                                            //holds a single cell
	int numberShipmentsNodes = -1;                        //number of shipments
	final int NUMBER_COLUMNS = 5;                        //number of columns to read over

	Row row = rowIterator.next();                        //get the next row
	Iterator<Cell> cellIterator = row.cellIterator();    //an iterator for cells
	PVRPQAShipment theShipment = null;                    //a pvrpQAshipment

	//READ THE NUMBER OF SHIPMENTS
	cell = cellIterator.next();                //GET THE FIRST CELL
	cell.setCellType(TYPE_NUMERIC);            //ENSURE IT IS OF NUMERIC TYPE
	float currentCellValue = (float) cell.getNumericCellValue();
	//EXTRACT THE NUMERIC CELL VALUE
	numberShipmentsNodes = (int) currentCellValue;


	//READ IN ALL THE NODES
	for(int rowCounter = 0; rowCounter < numberShipmentsNodes; rowCounter++) {
		//ITERATE OVER EVERY ROW
		//GET THE NEXT ROW
		row = rowIterator.next();
		cellIterator = row.cellIterator();

		theShipment = new PVRPQAShipment();

		for(int columnCounter = 0; columnCounter < NUMBER_COLUMNS; columnCounter++) {
			//ITERATE OVER EVERY COLUMN
			cell = cellIterator.next();                            //get the cell data
			cell.setCellType(TYPE_NUMERIC);
			currentCellValue = (float) cell.getNumericCellValue();    //extract cell data into int

			switch(columnCounter)                                            //Finite State Machine
			//i love these things
			{
				case 0:
					theShipment.setIndex((int) currentCellValue);
					break;    //this node/shipment index


				case 1:
					theShipment.setX(currentCellValue);
					break;    //x coordinate


				case 2:
					theShipment.setY(currentCellValue);
					break;    //y coordinate

				case 3:
					theShipment.setDemand(currentCellValue);
					break; //the demand

				case 4:
					theShipment.setFrequency((int) currentCellValue);
					break;    //the frequency
				default:
					break;
			}
		}
		pvrpQAShipments.getShipments().add(theShipment);
		//ADD THE JUST READ SHIPMENT
	}
	file.close();
}//READ_SHIPMENT_FILE ENDS HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//METHOD
//reads our solution files (the routes for each truck
//each day, etc.)
//THIS WILL PROBABLY BE ABLE TO UNDERSTAND IF YOU FOLLOW
//ALONG WITH THE OUTPUT
public void readSolutionFile()
		throws IOException {
	//VARIABLES
	FileInputStream file = new FileInputStream(new File(String.valueOf(solutionFile)));
	XSSFWorkbook workbook = new XSSFWorkbook(file);        //NEW WORKBOOK
	XSSFSheet sheet = workbook.getSheetAt(0);            //GET SHEET 0
	Iterator<Row> rowIterator = sheet.iterator();        //an iterator for rows

	Cell cell = null;                                //HOLDS A SINGLE CELL
	Row row;                                //HOLDS A SINGLE ROW
	row = rowIterator.next();                //GET THE NEXT ROW - row 0
	Iterator<Cell> cellIterator;            //CREATE A CELL/COLUMN ITERATOR

	PVRPQADepot theDepot;            //HOLDS A DEPOT QA
	PVRPQATruck theTruck;            //HOLDS A TRUCK QA
	PVRPQADay theDay;                //HOLDS A DAY QA
	PVRPQANode theNode;                //HOLDS A NODE QA
	PVRPQANode depotAsNode;
	//TREATS DEPOT AS A VISTED NODE (FIRST LOCATION AND LAST LOCATION)

	final int NUMBER_COLUMNS = 10;

	int numberOfDepots = 0;                    //THE NUMBER OF DEPOTS IN THIS SOLUTION
	int numberOfTrucks = 0;                    //NUMBER OF TRUCKS IN THIS SOLUTION
	int numberOfDays = 0;                    //NUMBER OF DAYS IN THIS SOLUTION
	int numberOfNodes = 0;                    //NUMBER OF NODES IN THIS SOLUTION

	row = rowIterator.next();                //GET THE NEXT ROW - row 1
	cellIterator = row.cellIterator();        //GET A CELL ITERATOR FOR NEW ROW


	for(int h = 0; h < NUMBER_COLUMNS - 1; h++) {
		//skip over 'blank' cells (represented by -1)
		cell = cellIterator.next();
	}


	//READ IN THE NUMBER OF DEPOTS
	cell.setCellType(TYPE_NUMERIC);
	numberOfDepots = (int) cell.getNumericCellValue();


	//ITERATE OVER THE SHEET READING IN THE DATA
	for(int depotCounter = 0; depotCounter < numberOfDepots; depotCounter++) {
		//LOOP UNTIL CORRECT NUMBER OF DEPOTS ARE READ
		depotAsNode = new PVRPQANode();
		row = rowIterator.next();                //NEXT ROW
		cellIterator = row.cellIterator();        //RESET CELL ITERATOR
		theDepot = new PVRPQADepot();

		//READ DEPOT INFORMATION
		for(int depotColumn = 0; depotColumn < NUMBER_COLUMNS; depotColumn++) {
			//sometimes a table has whitespace cells that look blank and
			//poi tries to keep reading

			cell = cellIterator.next();                            //get the cell data
			cell.setCellType(TYPE_NUMERIC);
			float currentCellValue = (float) cell.getNumericCellValue();    //extract cell data into int

			switch(depotColumn) {
				case 1:        //READ DEPOT INDEX
					theDepot.setIndex((int) currentCellValue);
					depotAsNode.setIndex((int) currentCellValue);
					break;
				case 2:        //READ X COORDINATE
					theDepot.setX(currentCellValue);
					depotAsNode.setX(currentCellValue);
					break;
				case 3:        //READ Y COORDINATE
					theDepot.setY(currentCellValue);
					depotAsNode.setY(currentCellValue);
					break;
				case 4:        //READ DEMAND
					theDepot.setDemand(currentCellValue);
					depotAsNode.setDemand(currentCellValue);
					break;
				case 5:        //READ DISTANCE
					theDepot.setDistance(currentCellValue);
					break;
				case 6:        //READ MAX DEMAND
					theDepot.setMaxDemand(currentCellValue);
					break;
				case 7:        //READ MAX DISTANCE
					theDepot.setMaxDistance(currentCellValue);
					break;
				case 8:        //READ NUMBER OF TRUCKS
					numberOfTrucks = (int) currentCellValue;
					break;
				default:
					break;
			}
			depotAsNode.setType(null);
			//DEPOT HAS NO NODE TYPE
			//IT IS SIMPLY THE FIRST AND LAST NODE TO 'VISIT'
		}

		//READ TRUCK INFORMATION
		for(int truckCounter = 0; truckCounter < numberOfTrucks; truckCounter++) {
			//LOOP UNTIL WE READ IN ALL TRUCKS
			row = rowIterator.next();                //GET NEXT ROW
			cellIterator = row.cellIterator();        //NEW CELL ITERATOR
			theTruck = new PVRPQATruck();            //MAKE NEW TRUCK

			for(int truckColumn = 0; truckColumn < NUMBER_COLUMNS; truckColumn++) {
				//READ TRUCKS OUT THE WIDTH OF THE SHEET
				cell = cellIterator.next();                            //get the cell data
				cell.setCellType(TYPE_NUMERIC);
				float currentCellValue = (float) cell.getNumericCellValue();    //extract cell data into int
				switch(truckColumn) {
					case 1:    //READ INDEX
						theTruck.setIndex((int) currentCellValue);
						break;
					case 2:    //READ TRUCK TYPE
						theTruck.setType("0.0");
						break; //IS ALWAYS THIS VALUE FOR PVRP
					case 4:    //READ DEMAND
						theTruck.setDemand(currentCellValue);
						break;
					case 5:    //READ DISTANCE
						theTruck.setDistance(currentCellValue);
						break;
					case 6:    //READ MAX DEMAND
						theTruck.setMaxDemand(currentCellValue);
						break;
					case 7:    //READ MAX DISTANCE
						theTruck.setMaxDistance(currentCellValue);
						break;
					case 8:    //READ HORIZON/NUMBER OF DAYS
						numberOfDays = (int) currentCellValue;
					default:
						break;
				}
			}

			//READ DAY INFORMATION
			for(int dayCounter = 0; dayCounter < numberOfDays; dayCounter++) {
				//LOOP UNTIL WE'VE READ THE NUMBER OF DAYS WE HAVE FOR THIS SOLUTION

				row = rowIterator.next();                //GET NEXT ROW
				cellIterator = row.cellIterator();        //RESET CELL ITERATOR
				theDay = new PVRPQADay();                //NEW DAY

				for(int dayColumn = 0; dayColumn < NUMBER_COLUMNS; dayColumn++) {

					cell = cellIterator.next();                  //get the cell data
					cell.setCellType(TYPE_NUMERIC);
					float currentCellValue = (float) cell.getNumericCellValue();    //extract cell data into int

					switch(dayColumn) {
						case 1:    //READ DAY NUMBER
							theDay.setDayNumber((int) currentCellValue);
							break;
						case 4:    //READ DAY DEMAND
							theDay.setDemand(currentCellValue);
							break;
						case 5:    //READ DAY DISTANCE
							theDay.setDistance(currentCellValue);
						case 6:    //READ MAX DEMAND
							theDay.setMaxDemand(currentCellValue);
							break;
						case 7:    //READ MAX DISTANCE
							theDay.setMaxDistance(currentCellValue);
							break;
						case 8:    //READ NUMBER OF NODES
							numberOfNodes = (int) currentCellValue;
						default:
							break;
					}
				}

				//READ NODE INFORMATION
				for(int nodeCounter = 0; nodeCounter < numberOfNodes; nodeCounter++) {
					//CYCLE THROUGH EVERY NODE FOR THIS DAY-TRUCK-DEPOT
					//AND PRINT IT
					//THIS WILL PRINT OUT MULTIPLE NODES IN A ROW
					//UNLIKE THE PREVIOUS FOR LOOPS

					row = rowIterator.next();            //NEXT ROW
					cellIterator = row.cellIterator();
					theNode = new PVRPQANode();            //NEW NODE


					for(int nodeColumn = 0; nodeColumn < NUMBER_COLUMNS; nodeColumn++) {
						cell = cellIterator.next();                            //get the cell data
						cell.setCellType(TYPE_NUMERIC);
						float currentCellValue = (float) cell.getNumericCellValue();    //extract cell data into int

						switch(nodeColumn) {
							case 1:    //READ INDEX
								theNode.setIndex((int) currentCellValue);
								break;
							case 2:    //READ X COORD
								theNode.setX(currentCellValue);
								break;
							case 3:    //READ Y COORD
								theNode.setY(currentCellValue);
								break;
							case 4:    //READ DEMAND
								theNode.setDemand(currentCellValue);
								break;
							default:    //WE HAVE NO NODAL TYPE
								theNode.setType(null);
								break;
						}
					}
					theDay.getNodes().add(theNode);
					//WE'VE READ IN A NODE, ADD IT TO THE DAY
				}
				theTruck.getDays().add(theDay);
				//WE'VE READ AN ENTIRE DAY, ADD IT TO A TRUCK
			}
			theDepot.getTrucks().add(theTruck);
			//WE'VE READ AN ENTIRE TRUCK, ADD IT TO A DEPOT
		}
		pvrpQADepots.getDepots().add(theDepot);
		//WE'VE READ AN ENTURE DEPOT, ADD IT TO THE DEPOTLL
	}
}//READ_SHIPMENT_FILE ENDS HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//METHOD
//runs QA on our data now that we've read
//everything back in
public boolean runQA() {
	boolean isDistanceGood;
	boolean isDemandGood;
	boolean isServicedGood;

	//Area all the customer being serviced and are they serviced only once
	System.out.print("Check on customer service freuqency: ");

	isServicedGood = pvrpQAShipments.customerServicedRequestedFrequency(pvrpQADepots);
	//RUN QA TO SEE IF REQUESTED FREQUENCY IS MET

	//DID WE PASS THIS CHECK
	if(isServicedGood) {
		System.out.println("Passed");
	}
	else {
		System.out.println("Failed");
	}


	//Check on maximum travel time of truck
	System.out.print("Check on maximum distance of trucks: ");
	isDistanceGood = pvrpQADepots.checkDistanceConstraint();
//	isGood = isGood && pvrpQADepots.checkDistanceConstraint();
	//MAKE SURE WE ARE WITHIN OUR DISTANCE CONSTRAINTS

//	System.out.println("Depot can t")


	//DID WE PASS THIS CHECK
	if(isDistanceGood) {
		System.out.println("Passed");
	}
	else {
		System.out.println("Failed");
	}


	//Check on maximum demand of a truck
	System.out.print("Check on maximum demand of trucks: ");
//	isDemandGood = isGood && pvrpQADepots.checkCapacityConstraint();
	isDemandGood = pvrpQADepots.checkCapacityConstraint();
	//MAKE SURE WE ARE WITHIN OUR CAPACITY CONSTRAINT

	//DID WE PASS THIS CHECK
	if(isDemandGood) {
		System.out.println("Passed");
	}
	else {
		System.out.println("Failed");
	}

	return isDemandGood && isDistanceGood && isServicedGood;
	//DID WE PASS OR FAIL?
}//RUN QA ENDS HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
}
