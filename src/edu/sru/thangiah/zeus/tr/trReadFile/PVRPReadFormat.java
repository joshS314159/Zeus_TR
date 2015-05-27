package edu.sru.thangiah.zeus.tr.trReadFile;

import edu.sru.thangiah.zeus.core.Day;
import edu.sru.thangiah.zeus.core.Truck;
import edu.sru.thangiah.zeus.tr.TRCoordinates;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.*;
import edu.sru.thangiah.zeus.tr.TRTruckType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by library-tlc on 5/26/15.
 */
public class PVRPReadFormat extends ReadFormat  {


public PVRPReadFormat(TRShipmentsList mainShipments, TRDepotsList mainDepots) {
	super(mainShipments, mainDepots);
}

@Override
public void readFiles() throws InvocationTargetException, InvalidFormatException, InstantiationException, IllegalAccessException, IOException {
	readDataFromFile();
}


	//METHOD
//read in the data from the requested file in Excel format
	public boolean readDataFromFile(/*String PVRPFileName*/)
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

		int list[] = new int[TRProblemInfo.MAX_COMBINATIONS];
		//this variable holds various day visit combinations that each node could find acceptable

		int currentCombination[][] = new int[TRProblemInfo.MAX_HORIZON][TRProblemInfo.MAX_COMBINATIONS];
		//contains the current day visit combination selected for the node
		TRDaysList mainDaysTemp = new TRDaysList();
		//contains a temporary list of our days

		FileInputStream file = new FileInputStream(new File(this.problemPath + this.problemFileName));        //problem being read
		XSSFWorkbook workbook =
				new XSSFWorkbook(file);                            				//make a new Excel workbook to read the data
		XSSFSheet sheet = workbook.getSheetAt(0);                                //get the zero-th sheet
		Iterator<Row> rowIterator = sheet.iterator();                            //an iterator for rows
		Row row = rowIterator.next();                                        	//gets the next row
		Iterator<Cell> cellIterator = row.cellIterator();                        //an iterator for columns
		Cell cell;                                                                //holds the current cell being read





//READ ROW 0
		//this while loop will work each cell of the zero-th row
		final int NUMBER_DEPOTS = 0;
		final int NUMBER_VEHICLES = 1;
		final int NUMBER_NODES = 2;
		final int DAYS_SERVICED_OVER = 3;
		while(cellIterator.hasNext())    //while we have a next cell
		{
			cell = cellIterator.next();                                //get the cell data
			cell.setCellType(TYPE_NUMERIC);                                    //set the cell type being read a numeric
			float currentCellValue = (float) cell.getNumericCellValue();    //extract cell data into a float

			switch(cellColumnCounter)                    //Finite State Machine
			{                                            //steps through each cell of row 0
				case NUMBER_DEPOTS:
					numberDepots = (int) currentCellValue;
					TRProblemInfo.NUMBER_DEPOTS = numberDepots;    //Set the number of depots to 1 for this problem
					break;    //this cell contains the number of depots (all our problems are 1)
				case NUMBER_VEHICLES:
					numberOfVehicles = (int) currentCellValue;
					TRProblemInfo.NUMBER_TRUCKS = numberOfVehicles;    //number of vehicles
					break;    //reads in the number of vehicles
				case NUMBER_NODES:
					numberOfNodes = (int) currentCellValue;
					TRProblemInfo.NUMBER_OF_NODES = numberOfNodes;    //number of shipments
					break;    //reads in the number of nodes
				case DAYS_SERVICED_OVER:
					daysServicedOver = (int) currentCellValue;
					TRProblemInfo.NUMBER_DAYS_SERVICED = daysServicedOver;    //number of days (horizon) or number of depots for PVRP
					break;    //reads in the horizon/number of days/days serviced over

				//PVRPProblem info is a class that holds various bits of information related to our problem
			}
			cellColumnCounter++;
			//increment cell counter so we can move through FSM
		}





		finalRowNumber = daysServicedOver + 1 + numberDepots + numberOfNodes;
		//calculate the last row in the spreadsheet

		TRProblemInfo.fileName = this.problemFileName;    //name of the file being read in
		TRProblemInfo.probType = type;            //problem type

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
		final int MAX_DISTANCE = 0;
		final int MAX_DEMAND = 1;
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
					case MAX_DISTANCE:
						maxDistanceD = (int) currentCellValue;
						break;        //the first value is maximum distance allowed by the truck

					//MAX CAP
					case MAX_DEMAND:
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

			TRDay day = new TRDay();/*(0, numberOfVehicles, daysServicedOver, maxDistanceD*numberOfVehicles, maxDemandQ*numberOfVehicles, depotXCoordinates,
					depotYCoordinates, dayNumberCounter++);    //create a new day for the currently read information*/
			day.setNumberOfTrucks(numberOfVehicles);
			day.setDaysServicedOver(daysServicedOver);
			day.setMaxDistance(maxDistanceD*numberOfVehicles);
			day.setMaxDemand(maxDemandQ * numberOfVehicles);

			TRCoordinates tempCoordinates = new TRCoordinates(depotXCoordinates, depotYCoordinates);
				tempCoordinates.setIsCartesian(true);
				day.setHomeDepotCoordinates(tempCoordinates);
			day.setDayNumber(dayNumberCounter++);


			mainDaysTemp.insertLastDay(day);                //insert the just created day into our temporary list of days
			row = rowIterator.next();        //get the next row
			cellIterator = row.cellIterator();    //an iterator for columns

		}


		for(int i = 0; i < numberTruckTypes; i++) {
			TRTruckType truckType = new TRTruckType(i, maxDistanceD, maxDemandQ, truckTypeString);
			TRProblemInfo.truckTypes.add(truckType);
		}





//READ DEPOT INFORMATION
		cellColumnCounter = 0;
		//reset our column counter
		final int NODE_NUMBER = 0;
		final int DEPOT_X = 1;
		final int DEPOT_Y = 2;
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
				case NODE_NUMBER:
					nodeNumber = (int) currentCellContents;
					break;    //the depot node number (starts at zero)
				case DEPOT_X:
					depotXCoordinates = (double) currentCellContents;
					break;    //the depot x coordinate
				case DEPOT_Y:
					depotYCoordinates = (double) currentCellContents;
					break;    //the depot y coordinate
				default:
					break;

			}
			cellColumnCounter++;        //increment what column we are one
		}

		mainDepots = new TRDepotsList();        //create a new depot linked list for our solution
		TRCoordinates tempCoordinates = new TRCoordinates();
			tempCoordinates.setCoordinates(depotXCoordinates, depotYCoordinates);
			tempCoordinates.setIsCartesian(true);
		TRDepot depotTemporary = new TRDepot(tempCoordinates);//(nodeNumber, depotXCoordinates, depotYCoordinates,
//				maxDistanceD * numberOfVehicles * daysServicedOver,
//				maxDemandQ * numberOfVehicles *
//						daysServicedOver);
		depotTemporary.setNodeNumber(nodeNumber);
		depotTemporary.setMaxDemand(maxDemandQ * numberOfVehicles);
		depotTemporary.setMaxDistance(maxDistanceD * numberOfVehicles * daysServicedOver);
		//creates a new temporary depot to be inserted in the depot linked list
		//calculates the total maximum distance for the depot by (singleDayMaxDistance)(numberVehicles)(numberDays)
		//calculates the total maximum demand for the entire depot by (maxDemandPerDay)(numberVehicles)(numberDays)
		mainDepots.insertDepotLast(depotTemporary);    //insert our newly read depot in the depot linked list








		depotTemporary = mainDepots.getFirst();
		for(int i = 0; i < TRProblemInfo.noOfVehs; i++) {
			TRTruckType ttype = (TRTruckType) TRProblemInfo.truckTypes.elementAt(0);
			tempCoordinates = new TRCoordinates(depotTemporary.getCoordinates());

			TRTruck tempTruck = new TRTruck();
				tempTruck.setHomeDepotCoordinates(tempCoordinates);
				tempTruck.setTruckType(ttype);

//			TRTruck truckTemp = new TRTruck(ttype, depotTemporary.getXCoord(), depotTemporary.getYCoord());
//			truckTemp.setTruckType(ttype);
			//we only have one type of truck
			depotTemporary.getSubList()
					.insertTruckLast(tempTruck);

			//insert the newly read truck data into the trucks linked list (which is underneath each depot read in; one in
			// our case)
		}


		TRDaysList daysLL = mainDaysTemp;
		TRTrucksList truckLL = mainDepots.getFirst().getSubList();
		//get the Truck linked list from inside mainDepots

		TRTruck truck = truckLL.getFirst();
		//get the first truck from inside the truck list






		//steps through each depot and then truck to create the correct number of days for each
		for(int i = 0; i < depotTemporary.getSubList().getSize(); i++) {
			TRDay tempDay = daysLL.getFirst();

			while(tempDay != mainDaysTemp.getTail()) {    //while we aren't at the end of the list
				tempDay = new TRDay();
				tempDay.
				truck.getSubList().insertLastDay(        //insert the day into the days linked list
						new TRDay(tempDay.getSubList(), tempDay.getNumberOfTrucks(),
								tempDay.getDaysServicedOver(),
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







		TRTruck truckTemp = mainDepots.getFirst().getSubList().getHead();
		for(int i = 0; i < numberOfVehicles; i++) {
			truckTemp = truckTemp.getNext();
			TRDay day = truckTemp.getSubList().getHead();
			//get the first day from the days linked list in the truck

			for(int x = 0; x < daysServicedOver; x++) {
				TRDay tempDay = (TRDay) day.getNext();
				day = day.getNext();
				//get the next day

				TRNodesList tempNLL = tempDay.getSubList();

				tempNLL.setTruckType((PVRPTruckType) TRProblemInfo.truckTypes.get(0));
				tempNLL.setFeasibility(new PVRPFeasibility(tempNLL.getTruckType().getMaxDuration(),
						tempNLL.getTruckType().getMaxCapacity(), tempNLL));
			}
		}








//		final int NODE_NUMBER = 0;
		final int X_COOORDINATE = 1;
		final int Y_COORDINATE = 2;
//		final int  = 3;
		final int DEMAND = 4;
		final int FREQUENCY = 5;
		final int NUMBER_COMBINATIONS = 6;
		while(row.getRowNum() > daysServicedOver && row.getRowNum() < finalRowNumber && rowIterator.hasNext()) {
			TRShipment newShipment = new TRShipment();
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
					case NODE_NUMBER:
						nodeNumber = (int) currentCellContents;
						newShipment.setNodeNumber(nodeNumber);
						break;    //the node number for the node
					case X_COOORDINATE:
						xCoordinates = (double) currentCellContents;
						break;    //the x coordinate for the node
					case Y_COORDINATE:
						yCoordinates = (double) currentCellContents;
						tempCoordinates = new TRCoordinates(xCoordinates, yCoordinates);
						tempCoordinates.setIsCartesian(true);
						newShipment.setCoordinates(tempCoordinates);
						break;    //the y coordinate for the node
					case 3:
						DUMMY = (double) currentCellContents;
						/** @todo what is this */
						//usually all zeros
						//haven't found a use for this
						break;
					case DEMAND:
						demandQ = (int) currentCellContents;
						newShipment.setDemand(demandQ);
						break;    //the demand for the node
					case FREQUENCY:
						frequency = (int) currentCellContents;
						newShipment.setFrequency(frequency);
						break;    //the number of days a node needs visited
					case NUMBER_COMBINATIONS:
						numberCombinations = (int) currentCellContents;
						newShipment.setNoComb(numberCombinations);
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
					.insertShipment(newShipment);
			//insert the just read shipment into the mainShipments list holds our all our problem info read in from Excel

		}

		return true;
		//whoo! done with one method in one class
	}//READ DATA FROM FILE ENDS
// HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<







}
