package edu.sru.thangiah.zeus.pvrp;


import edu.sru.thangiah.zeus.core.DepotLinkedList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

//import the parent class




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


public class PVRPDepotLinkedList
		extends DepotLinkedList
		implements java.io.Serializable, java.lang.Cloneable {

//VARIABLES
private PVRPDepot      head;     //takes precedence over the base class head and tail
private PVRPDepot      tail;
private PVRPAttributes attributes;     //takes precedence over the base class attributes




//CONSTRUCTOR
public PVRPDepotLinkedList() {
	this.head = new PVRPDepot();        //link head and tail
	this.tail = new PVRPDepot();
	this.head.setNext(this.tail);
	this.tail.setPrev(this.head);
	this.attributes = new PVRPAttributes();    //set attributes
}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//METHOD
//inserts the depot into the position before the tail
public boolean insertDepotLast(PVRPDepot depot) {
	if(this.head.getNext() == this.tail) {
		//if the depot after the head is the tail
		//then our list is currently empty (but not for long :)
		this.head.setNext(depot);    //set head next to passed
		this.tail.setPrev(depot);    //set tail prev to passed
		depot.setPrev(this.head);    //set passed prev to head
		depot.setNext(this.tail);    //set passed next to tail
	}
	else {
		//otherwise we already got stuff in here
		depot.setNext(this.tail);            //set passed next to tail
		depot.setPrev(this.tail.getPrevious());    //set passed prev to current prev
		this.tail.getPrevious().setNext(depot);    //set prev's next to passed
		this.tail.setPrev(depot);            //set tail prev to passed
	}
	return true;
}//END INSERT_DEPOT_LAST *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//METHOD
//inserts a shipment
public boolean insertShipment(PVRPShipment theShipment) {
	boolean status = false;

	PVRPDepot depot = getHead().getNext();
	PVRPTruckLinkedList truckLL = null;

//	double  test = tempDepotLL.getAttributes().getTotalDemand();
	while(depot != this.getTail()) {
		//Get truck to insert the shipment
		//while we have more depots

		truckLL = depot.getMainTrucks();
		//get the trucks linked ist

		status = truckLL.insertShipment(theShipment);
		//insert the shipment into the trucks linked list

		if(status) {
			break;    //if it inserted into the list okay then break
		}
		depot = depot.getNext();
	}
	return status;    //return true if inserted OK
}//END INSERT_SHIPMENT *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

//METHOD
//used by the gui to show problem information
public String getSolutionString()
{
	return
				"Trucks Used = " + PVRPProblemInfo.noOfVehs + " | " + this.attributes.toDetailedString();
}


//GETTER
public PVRPDepot getHead() {
	return this.head;
}




//GETTER
public PVRPDepot getTail() {
	return this.tail;
}




//SETTER
public void setTail(PVRPDepot tail) {
	this.tail = tail;
}




//METHOD
//link the head and the tail together
public void linkHeadTail() {
	this.head.setNext(this.tail);    //head next to tail
	this.tail.setPrev(this.head);    //tail prev to head
	this.head.setPrev(null);        //head prev to null (firstmost)
	this.tail.setNext(null);        //tail next to null (lastmost)
}//END LINK_HEAD_TAIL *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




public PVRPAttributes getAttributes() {
	return this.attributes;
}




//SETTER
public void setAttributes(PVRPAttributes attributes) { this.attributes = attributes; }






//SETTER
public void setHead(PVRPDepot head) {
	this.head = head;
}




//METHOD
//
public void printDepotLinkedList(FileOutputStream out)
		throws IOException {
	writeOutData(out);

}//END PRINT_DEPOT_LINKED_LIST *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//METHOD
//writes out the entire depot linked list
//hierarchy in Excel format
//this will probably be used to print <<TEMP>> files
//thus it is printed for utility and not prettiness
//all empty cells are set to '-1'
//these means an easier algorithm since it doesn't
//have to read pretty format
public void writeOutData(FileOutputStream out)
		throws IOException {
	PVRPDepot theDepot;

	int rowCounter = 0;                //counts our rows
	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet sheet = workbook.createSheet("Customer Data");    //create a worksheet
	Row row;
	Cell cell;
	int columnCounter = 0;            //counts our columns
	final int NULL_VALUE = -1;        //a null value for would-be empty cellss


	//CREATE THE HEADER ROWS
	//NEW ROW
	row = sheet.createRow(rowCounter++);
	cell = row.createCell(columnCounter++);        //Column 0
	cell.setCellValue("0_NAME");

	cell = row.createCell(columnCounter++);        //C1
	cell.setCellValue("1_INDEX");

	cell = row.createCell(columnCounter++);        //C2
	cell.setCellValue("2_X");

	cell = row.createCell(columnCounter++);        //C3
	cell.setCellValue("3_Y");

	cell = row.createCell(columnCounter++);        //C4
	cell.setCellValue("4_DEMAND");

	cell = row.createCell(columnCounter++);        //C5
	cell.setCellValue("5_DISTANCE");

	cell = row.createCell(columnCounter++);        //C6
	cell.setCellValue("6_MAX_DEMAND");

	cell = row.createCell(columnCounter++);        //C7
	cell.setCellValue("7_MAX_DISTANCE");

	cell = row.createCell(columnCounter++);        //C8
	cell.setCellValue("8_SUB_CLASS_SIZE");

	cell = row.createCell(columnCounter++);        //C9

	cell.setCellValue("9_FREQUENCY");

	//PRINT THE NUMBER OF DEPOTS
	//NEW ROW
	columnCounter = 0;
	row = sheet.createRow(rowCounter++);
	cell = row.createCell(columnCounter++);
	cell.setCellValue("NUM_DEPOTS");

	cell = row.createCell(columnCounter++);
	cell.setCellValue(NULL_VALUE);

	cell = row.createCell(columnCounter++);
	cell.setCellValue(NULL_VALUE);

	cell = row.createCell(columnCounter++);
	cell.setCellValue(NULL_VALUE);

	cell = row.createCell(columnCounter++);
	cell.setCellValue(NULL_VALUE);

	cell = row.createCell(columnCounter++);
	cell.setCellValue(NULL_VALUE);

	cell = row.createCell(columnCounter++);
	cell.setCellValue(NULL_VALUE);

	cell = row.createCell(columnCounter++);
	cell.setCellValue(NULL_VALUE);

	cell = row.createCell(columnCounter++);
	cell.setCellValue(PVRPProblemInfo.numDepots);

	cell = row.createCell(columnCounter++);
	cell.setCellValue(NULL_VALUE);

	theDepot = getHead().getNext();
	while(theDepot != getTail()) {
		//while we have more depots

		columnCounter = 0;

		//PRINT DEPOT INFORMATION
		//NEW ROW
		row = sheet.createRow(rowCounter++);        //make a new row
		cell = row.createCell(columnCounter++);
		cell.setCellValue("Depot");

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theDepot.getDepotNum());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theDepot.getXCoord());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theDepot.getYCoord());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theDepot.getAttributes().getTotalDemand());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theDepot.getAttributes().getTotalDistance());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theDepot.getMaxDemand());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theDepot.getMaxDistance());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theDepot.getMainTrucks().getSize());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(NULL_VALUE);


		PVRPTruckLinkedList truckLL = theDepot.getMainTrucks();
		//extract trucks linked list from depot
		PVRPTruck theTruck = truckLL.getHead().getNext();
		//extract single truck from the linked list
		while(theTruck != truckLL.getTail()) {

			columnCounter = 0;
			int dayCount = 0;

			//PRINT TRUCK INFORMATION
			//NEW ROW
			row = sheet.createRow(rowCounter++);
			cell = row.createCell(columnCounter++);
			cell.setCellValue("Truck");

			cell = row.createCell(columnCounter++);
			cell.setCellValue(theTruck.getTruckNum());

			cell = row.createCell(columnCounter++);
			cell.setCellValue(NULL_VALUE);

			cell = row.createCell(columnCounter++);
			cell.setCellValue(NULL_VALUE);

			cell = row.createCell(columnCounter++);
			cell.setCellValue(theTruck.getAttributes().getTotalDemand());

			cell = row.createCell(columnCounter++);
			cell.setCellValue(theTruck.getAttributes().getTotalDistance());

			cell = row.createCell(columnCounter++);
			cell.setCellValue(theTruck.getTruckType().getMaxCapacity());

			cell = row.createCell(columnCounter++);
			cell.setCellValue(theTruck.getTruckType().getMaxDuration());

			cell = row.createCell(columnCounter++);
			/** @todo fix this...numberOfDays have a random -2 in here*/
			cell.setCellValue(theTruck.getMainDays().getSize() - 2);

			cell = row.createCell(9);
			cell.setCellValue(NULL_VALUE);

			dayCount = 0;
			PVRPDayLinkedList daysLL = theTruck.getMainDays();
			PVRPDay theDay = daysLL.getHead().getNext();
			while(theDay != daysLL.getTail()) {


				//PRINT DAY INFORMATION
				//NEW ROW
				columnCounter = 0;
				row = sheet.createRow(rowCounter++);
				cell = row.createCell(columnCounter++);
				cell.setCellValue("Day");

				cell = row.createCell(columnCounter++);
				cell.setCellValue(dayCount++);

				cell = row.createCell(columnCounter++);
				cell.setCellValue(NULL_VALUE);

				cell = row.createCell(columnCounter++);
				cell.setCellValue(NULL_VALUE);

				cell = row.createCell(columnCounter++);
				cell.setCellValue(theDay.getAttributes().getTotalDemand());

				cell = row.createCell(columnCounter++);
				cell.setCellValue(theDay.getAttributes().getTotalDistance());

				cell = row.createCell(columnCounter++);
				cell.setCellValue(theDay.getMaxDemand());

				cell = row.createCell(columnCounter++);
				cell.setCellValue(theDay.getMaxDistance());

				cell = row.createCell(columnCounter++);
				cell.setCellValue(theDay.getNodesLinkedList().getSize() + 2);

				cell = row.createCell(columnCounter++);
				cell.setCellValue(NULL_VALUE);

				PVRPNodesLinkedList nodesLL = theDay.getNodesLinkedList();
				PVRPNodes theNode = nodesLL.getHead();
				while(true) {
					//loops through every node for the chosen day and prints it
					//breaks out of this infinite loop by checking if we finally
					//printed the tail

					PVRPShipment theShipment = theNode.getShipment();

					columnCounter = 0;

					//PRINT ALL NODE INFORMATION
					//NEW ROW
					row = sheet.createRow(rowCounter++);
					cell = row.createCell(columnCounter++);    //create new cell number X
					cell.setCellValue("Node");    //set each cell value to the correct value from the linked list

					cell = row.createCell(columnCounter++);    //create new cell number X
					cell.setCellValue(
							theShipment.getIndex());    //set each cell value to the correct value from the linked list

					cell = row.createCell(columnCounter++);
					cell.setCellValue(theShipment.getxCoord());


					cell = row.createCell(columnCounter++);
					cell.setCellValue(theShipment.getyCoord());


					cell = row.createCell(columnCounter++);
					cell.setCellValue(theShipment.getDemand());

					cell = row.createCell(columnCounter++);
					cell.setCellValue(NULL_VALUE);


					cell = row.createCell(columnCounter++);
					cell.setCellValue(NULL_VALUE);

					cell = row.createCell(columnCounter++);
					cell.setCellValue(NULL_VALUE);

					cell = row.createCell(columnCounter++);
					cell.setCellValue(NULL_VALUE);

					cell = row.createCell(columnCounter++);
					cell.setCellValue(theShipment.getFrequency());


					if(theNode == nodesLL.getTail()) {
						//if we have read and printed the tail
						//break before we go into null territory
						break;
					}

					theNode = theNode.getNext();
				}
				theDay = theDay.getNext();
			}
			theTruck = theTruck.getNext();
		}
		theDepot = theDepot.getNext();
	}

	workbook.write(out);        //write out to stream
	out.close();                //close the stream
}//END WRITE_OUT_DATA *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



public PVRPDepot getDepotAtPosition(int position){
	PVRPDepot theDepot = this.getHead();
	int count = -1;

	if(position >= 0 && position < this.getSize()) {
		while (count != position) {
			theDepot = theDepot.getNext();
			count++;
			if (theDepot == this.getTail()) {
				return null;
			}
		}
	}
	else{
		return null;
	}
	return theDepot;
}

public int getSize(){
	PVRPDepot theDepot = this.getHead();
	int sizeCounter = 0;

	if(!isEmpty()) {
		while (theDepot.getNext() != this.getTail()) {
			theDepot = theDepot.getNext();
			sizeCounter++;
		}
		return sizeCounter;
	}
	return 0;
}


public PVRPDepot getFirstDepot(){
	if(!isEmpty()){
		return this.getHead().getNext();
	}
	return null;

}

public PVRPDepot getLastDepot(){
	if(!isEmpty()) {
		return this.getTail().getPrevious();
	}
	return null;
}

public boolean isEmpty(){
	if(this.getHead().getNext() == this.getTail() || this.getHead().getNext() == null){
		return true;
	}
	return false;
}


}
