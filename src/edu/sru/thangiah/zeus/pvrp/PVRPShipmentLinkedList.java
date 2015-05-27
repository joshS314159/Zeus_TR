package edu.sru.thangiah.zeus.pvrp;


import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.vrp.VRPDepot;
import edu.sru.thangiah.zeus.vrp.VRPDepotLinkedList;
import edu.sru.thangiah.zeus.vrp.VRPShipment;
import edu.sru.thangiah.zeus.vrp.VRPShipmentLinkedList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;


/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 *
 * @author Sam R. Thangiah
 * @version 2.0
 */
//import the parent class


public class PVRPShipmentLinkedList
		extends ShipmentLinkedList
		implements java.io.Serializable, java.lang.Cloneable {

private PVRPShipment head;
private PVRPShipment tail;




/**
 * Constructor for the shipment linked list
 * Define it as below
 */
public PVRPShipmentLinkedList() {
	//SUB CLASS
	this.head = new PVRPShipment();
	this.tail = new PVRPShipment();

	linkHeadTail();

	setNumShipments(0);

}




public void linkHeadTail() {
	this.head.setNext(this.tail);
	this.tail.setPrevious(this.head);
	this.head.setPrevious(null);
	this.tail.setNext(null);
}




public PVRPShipment getHead() {
	return this.head;
}




public PVRPShipment getTail() {
	return this.tail;
}




public void setTail(PVRPShipment tail) {
	this.tail = tail;
}







public boolean isAllShipsAssigned() {
	PVRPShipment ship = this.head.getNext();
	while(ship != this.tail) {
		if(!ship.getIsAssigned()) {
			return false;
		}
		ship = ship.getNext();
	}
	return true;
}








public void setHead(PVRPShipment head) {
	this.head = head;
}




/**
 * insert the shipment into the linked list
 * Constructor
 *
 * @param ind    index
 * @param x      x-coordinate
 * @param y      y-coordinate
 * @param q      demand
 * @param d      service time
 * @param e      frequency
 * @param comb   number of combination
 * @param vComb  list of combinations (vector)
 * @param cuComb number of combinations (matrix)
 */


//INSERT_SHIPMENT >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
public void insertShipment(int node, double x, double y, double DUMMY, int demandQ, int frequency, int numberCombos,
						   int[] comboList, int[][] currentCombo) {
	if(comboList.length <= ProblemInfo.MAX_COMBINATIONS) {
		//create an instance of the Shipment
		PVRPShipment thisShip =
				new PVRPShipment(node, x, y, DUMMY, demandQ, frequency, numberCombos, comboList, currentCombo);
		//add the instance to the linked list - in this case it is added at the end of the list
		//the total number of shipments is incremented in the insert
		insertLast(thisShip);
	}
	else {
		System.out.println("PVRPShipmentLinkedList: Maximum number of combinations exceeded");
	}
}




public boolean insertLast(PVRPShipment shipment) {
	if(this.head.getNext() == this.tail) {
		this.head.setNext(shipment);
		this.tail.setPrevious(shipment);
		shipment.setPrevious(this.head);
		shipment.setNext(this.tail);
	}
	else {
		shipment.setNext(this.tail);
		shipment.setPrevious(this.tail.getPrev());
		this.tail.getPrev().setNext(shipment);
		this.tail.setPrevious(shipment);
	}
	this.setNumShipments(this.getNumShipments() + 1);
	this.setTotalDemand(this.getTotalDemand() + shipment.getDemand());
	return true;
}
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


/**
 * Returns the first shipment in the linked list
 * @return first shipment


/**
 * Returns the tail shipment in the linked list
 * @return first shipment
 */


/**
 * This method will get the next shipment that is to be inserted based on the
 * type of shipment selection that has been defined in the main method
 * for the variable ProblemInfo.selectShipType
 *
 * @param currDepot      current depot being considered for the shipment
 * @param currDepotLL    Depot linked list of the problem being solved
 * @param currShipmentLL shipment linked list from which the shipment to be
 *                       insertion is to be selected
 * @return PVRPShipment the shipment to be inserted
 */

public PVRPShipment getNextInsertShipment(PVRPDepotLinkedList currDepotLL, PVRPDepot currDepot,
										  PVRPShipmentLinkedList currShipmentLL, PVRPShipment currShip) {

	PVRPShipmentLinkedList selectShip = (PVRPShipmentLinkedList) PVRPProblemInfo.selectShipType;


	return (PVRPShipment) selectShip.getSelectShipment((DepotLinkedList) currDepotLL, (Depot) currDepot, (ShipmentLinkedList) currShipmentLL, (Shipment) currShip);
}




/**
 * This is a stub - Leave it as it is
 * The concrere getShipmentToInsert will be declared by the class inheriting this
 * class and implementing the actual insertion of shipment  *
 *
 * @param currShipmentLL shipment linked list from which the shipment to be
 *                       insertion is to be selected
 * @return PVRPShipment the shipment to be inserted
 */

//public PVRPShipment getSelectShipment(PVRPDepotLinkedList currDepotLL, PVRPDepot currDepot,
//									  PVRPShipmentLinkedList currShipmentLL, PVRPShipment currShip) {
//	return null;
//}
//

public Shipment getSelectShipment(final DepotLinkedList depotsList, final Depot theDepot,
								  final ShipmentLinkedList shipmentsList, final Shipment theShipment) { return  null;}


/**
 * Print out the shipment linked list to the console
 *
 * @param out PrintStream stream to output to
 *///PRINT_PVRP_SHIPMENTS_TO_CONSOLE
// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
public void printShipmentsToConsole() {
	System.out.println(this.getNumShipments());

	//		Shipment ship = getHead();
	PVRPShipment PVRPShip = getHead();
	System.out.println("Node\tDemand\tX-Coordinate\tY-Coordinate\tFrequency\tCombos");

	while(PVRPShip != getTail()) {
		//			PVRPShip = ship;
		System.out.println(PVRPShip.getIndex() + " " +
						   PVRPShip.getDemand() + " " + PVRPShip.getXCoord() + " " +
						   PVRPShip.getYCoord() + " " + PVRPShip.getFrequency() + " " + PVRPShip.getNoComb());
		PVRPShip = PVRPShip.getNext();
	}
}
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




/**
 * Writes the shipments to the print stream
 *
 * @param out PrintStream stream to output to
 */
//WRITE_PVRP_SHIPMENTS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
public void writeShipments(FileOutputStream out)
		throws IOException {

	//VARIABLES
	//		Shipment ship = super.getHead();		//the linked list head
	//
	//		PVRPShipment PVRPShip;

	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet sheet = workbook.createSheet("Customer Data");    //create a worksheet


	int rowCounter = 0;        //tracks our rows
	int previousGetIndex = -10;

	Row row = sheet.createRow(rowCounter);
	rowCounter++;
	Cell cell = row.createCell(0);
	cell.setCellValue(getNumShipments());

	//        ship = ship.getNext();								//get the next shipment
	//        PVRPShip = (PVRPShipment) ship;

	PVRPShipment PVRPShip = getHead().getNext();
	while(PVRPShip !=
		  getTail())                                //while we aren'numberOfDays at the tail of the doubly linked list
	{


		//            PVRPShip = (PVRPShipment) ship;


		row = sheet.createRow(rowCounter);        //make a new row
		int cellTracker = 0;                        //tracks which cell we are currently at


		while(cellTracker != 5)                    //while we haven'numberOfDays written all the cells for the given
		// row
		{
			cell = row.createCell(cellTracker);    //create new cell number X
			switch(cellTracker)                        //SWITCH statement
			{
				case 0:
					cell.setCellValue(
							PVRPShip.getIndex());    //set each cell value to the correct value from the linked list
					break;
				case 1:
					cell.setCellValue(PVRPShip.getxCoord());
					break;
				case 2:
					cell.setCellValue(PVRPShip.getyCoord());

					break;
				case 3:
					cell.setCellValue(PVRPShip.getDemand());
					break;
				case 4:
					cell.setCellValue(PVRPShip.getFrequency());
					break;
				/**        case 5:								??????????????????????????MUST IMPLEMENT FOR ALL
				 * VARIABLES OF PVRP
				 cell.setCellValue(PVRPShip.getExtraVariable());
				 break;
				 case 6:
				 cell.setCellValue(PVRPShip.getIndex());
				 break;
				 case 7:*/
			}
			cellTracker++;            //increment our cell tracker
		}
		rowCounter++;                //move onto the next row
		PVRPShip = PVRPShip.getNext();                                //get the next shipment

	}

	workbook.write(out);
	out.close();

}
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



public PVRPShipment getShipmentAtPosition(int position){
	PVRPShipment theShipment = this.getHead();
	int count = -1;

	if(position >= 0 && position < this.getSize()) {
		while (count != position) {
			theShipment = theShipment.getNext();
			count++;
			if (theShipment == this.getTail()) {
				return null;
			}
		}
	}
	else{
		return null;
	}
	return theShipment;
}

public int getSize(){
	PVRPShipment theShipment = this.getHead();
	int sizeCounter = 0;

	if(!isEmpty()) {
		while (theShipment.getNext() != this.getTail()) {
			theShipment = theShipment.getNext();
			sizeCounter++;
		}
		return sizeCounter;
	}
	return 0;
}



public PVRPShipment getFirstShipment(){
	if(!isEmpty()){
		return this.getHead().getNext();
	}
	return null;

}

public PVRPShipment getLastShipment(){
	if(!isEmpty()) {
		return this.getTail().getPrev();
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


//******************************************************************************************************\\
////>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> CLOSEST_EUCLIDEAN_DISTANCE STARTS HERE <<<<<<<<<<<<<<<<<<<<<<<<\\\\
//******************************************************************************************************\\



//Select the shipment with the shortest distance to the depot
class PVRPClosestEuclideanDistToDepot
		extends PVRPShipmentLinkedList {

//The WhoAmI methods gives the id of the assigned object
//It is a static method so that it can be accessed without creating an object
public static String WhoAmI() {
	return ("Selection Type: Closest euclidean distance to depot");
}




public PVRPShipment getSelectShipment(PVRPDepotLinkedList currentDepotList, PVRPDepot currentDepot,
									  PVRPShipmentLinkedList currentShipmentList, PVRPShipment currentShipment) {
	//currDepotLL is the depot linked list of the problem
	//currDepot is the depot under consideration
	//currShipLL is the set of avaialble shipments
	boolean isDiagnostic = false;
	PVRPShipment temp = currentShipmentList.getHead().getNext(); //point to the first shipment
	PVRPShipment foundShipment = null; //the shipment found with the criteria
	//double angle;
	//double foundAngle = 360; //initial value
	double distance;
	double foundDistance = 200; //initial distance
	double depotX, depotY;

	//Get the X and Y coordinate of the depot
	depotX = currentDepot.getXCoord();
	depotY = currentDepot.getYCoord();

	while(temp != currentShipmentList.getTail()) {
		if(isDiagnostic) {
			System.out.print("Shipment " + temp.getIndex() + " ");

			if(((temp.getXCoord() - depotX) >= 0) && ((temp.getYCoord() - depotX) >= 0)) {
				System.out.print("Quadrant I ");
			}
			else if(((temp.getXCoord() - depotX) <= 0) && ((temp.getYCoord() - depotY) >= 0)) {
				System.out.print("Quadrant II ");
			}
			else if(((temp.getXCoord()) <= (0 - depotX)) && ((temp.getYCoord() - depotY) <= 0)) {
				System.out.print("Quadrant III ");
			}
			else if(((temp.getXCoord() - depotX) >= 0) && ((temp.getYCoord() - depotY) <= 0)) {
				System.out.print("Quadrant VI ");
			}
			else {
				System.out.print("No Quadrant");
			}
		}

		//if the shipment is assigned, skip it
		if(temp.getIsAssigned()) {
			if(isDiagnostic) {
				System.out.println("has been assigned");
			}

			temp = temp.getNext();

			continue;
		}
		/** @todo Associate the quadrant with the distance to get the correct shipment.
		 * Set up another insertion that takes the smallest angle and the smallest distance */
		distance = calcDist(depotX, temp.getXCoord(), depotY, temp.getYCoord());

		if(isDiagnostic) {
			System.out.println("  " + distance);
		}

		//check if this shipment should be tracked
		if(foundShipment == null) { //this is the first shipment being checked
			foundShipment = temp;
			foundDistance = distance;
		}
		else {
			if(distance < foundDistance) { //found an angle that is less
				foundShipment = temp;
				foundDistance = distance;
			}
		}
		temp = temp.getNext();
	}
	return foundShipment; //stub
}

}

//******************************************************************************************************\\
////>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SMALLEST_POLAR_ANGLE_TO_DEPOT STARTS HERE <<<<<<<<<<<<<<<<<<<<<<<<\\\\
//******************************************************************************************************\\


//Select the shipment with the smallest polar coordinate angle to the depot
class PVRPSmallestPolarAngleToDepot
		extends PVRPShipmentLinkedList {

//The WhoAmI methods gives the id of the assigned object
//It is a static method so that it can be accessed without creating an object
public static String WhoAmI() {
	return ("Selection Type: Smallest polar angle to the depot");
}




public PVRPShipment getSelectShipment(PVRPDepotLinkedList currentDepotList, PVRPDepot currentDepot,
									  PVRPShipmentLinkedList currentShipmentList, PVRPShipment currentShipment) {
	//currDepotLL is the depot linked list of the problem
	//currDepot is the depot under consideration
	//currShipLL is the set of avaialble shipments
	boolean isDiagnostic = false;
	PVRPShipment temp = currentShipmentList.getHead().getNext(); //point to the first shipment


	PVRPShipment foundShipment = null; //the shipment found with the criteria
	double angle;
	double foundAngle = 360; //initial value
	//double distance;
	//double foundDistance = 200; //initial distance
	double depotX, depotY;
	int type = 2;

	//Get the X and Y coordinate of the depot
	depotX = currentDepot.getXCoord();
	depotY = currentDepot.getYCoord();

	while(temp != currentShipmentList.getTail()) {

		if(isDiagnostic) {
			System.out.println("Temp is " + temp);
			System.out.println("Tail is " + getTail());
			System.out.print("Shipment " + temp.getIndex() + " ");

			if(((temp.getXCoord() - depotX) >= 0) && ((temp.getYCoord() - depotX) >= 0)) {
				System.out.print("Quadrant I ");
			}
			else if(((temp.getXCoord() - depotX) <= 0) && ((temp.getYCoord() - depotY) >= 0)) {
				System.out.print("Quadrant II ");
			}
			else if(((temp.getXCoord()) <= (0 - depotX)) && ((temp.getYCoord() - depotY) <= 0)) {
				System.out.print("Quadrant III ");
			}
			else if(((temp.getXCoord() - depotX) >= 0) && ((temp.getYCoord() - depotY) <= 0)) {
				System.out.print("Quadrant VI ");
			}
			else {
				System.out.print("No Quadrant");
			}
		}

		//if the shipment is assigned, skip it
		if(temp.getIsAssigned()) {
			if(isDiagnostic) {
				System.out.println("has been assigned");
			}

			temp = temp.getNext();

			continue;
		}

		angle = calcPolarAngle(depotX, depotX, temp.getXCoord(), temp.getYCoord());

		if(isDiagnostic) {
			System.out.println("  " + angle);
		}

		//check if this shipment should be tracked
		if(foundShipment == null) { //this is the first shipment being checked
			foundShipment = temp;
			foundAngle = angle;
		}
		else {
			if(angle < foundAngle) { //found an angle that is less
				foundShipment = temp;
				foundAngle = angle;
			}
		}
		temp = temp.getNext();
	}
	return foundShipment; //stub
}


}


//******************************************************************************************************\\
////>>>>>>>>>>>>>>>>>>>>>> SMALLEST_POLAR_ANGLE_SHORTEST_DISTANCE STARTS HERE <<<<<<<<<<<<<<<<<<<<<<<<\\\\
//******************************************************************************************************\\

//Select the shipment with the smallest polar coordinate angle and the
//shortest distance to the depot
class PVRPSmallestPolarAngleShortestDistToDepot
		extends PVRPShipmentLinkedList {

//The WhoAmI methods gives the id of the assigned object
//It is a static method so that it can be accessed without creating an object
public static String WhoAmI() {
	return ("Selection Type: Smallest polar angle, shortest distance to the depot");
}




public PVRPShipment getSelectShipment(PVRPDepotLinkedList currentDepotList, PVRPDepot currentDepot,
									  PVRPShipmentLinkedList currentShipmentList, PVRPShipment currentShipment) {
	//currDepotLL is the depot linked list of the problem
	//currDepot is the depot under consideration
	//currShipLL is the set of avaialble shipments
	boolean isDiagnostic = false;
	PVRPShipment temp = currentShipmentList.getHead().getNext(); //point to the first shipment
	PVRPShipment foundShipment = null; //the shipment found with the criteria
	double angle;
	double foundAngle = 360; //initial value
	double distance;
	double foundDistance = 200; //initial distance
	double depotX, depotY;
	int type = 2;

	//Get the X and Y coordinate of the depot
	depotX = currentDepot.getXCoord();
	depotY = currentDepot.getYCoord();

	while(temp != currentShipmentList.getTail()) {
		if(isDiagnostic) {
			System.out.print("Shipment " + temp.getIndex() + " ");

			if(((temp.getXCoord() - depotX) >= 0) && ((temp.getYCoord() - depotX) >= 0)) {
				System.out.print("Quadrant I ");
			}
			else if(((temp.getXCoord() - depotX) <= 0) && ((temp.getYCoord() - depotY) >= 0)) {
				System.out.print("Quadrant II ");
			}
			else if(((temp.getXCoord()) <= (0 - depotX)) && ((temp.getYCoord() - depotY) <= 0)) {
				System.out.print("Quadrant III ");
			}
			else if(((temp.getXCoord() - depotX) >= 0) && ((temp.getYCoord() - depotY) <= 0)) {
				System.out.print("Quadrant VI ");
			}
			else {
				System.out.print("No Quadrant");
			}
		}

		//if the shipment is assigned, skip it
		if(temp.getIsAssigned()) {
			if(isDiagnostic) {
				System.out.println("has been assigned");
			}

			temp = temp.getNext();

			continue;
		}

		distance = calcDist(depotX, temp.getXCoord(), depotY, temp.getYCoord());
		angle = calcPolarAngle(depotX, depotX, temp.getXCoord(), temp.getYCoord());

		if(isDiagnostic) {
			System.out.println("  " + angle);
		}

		//check if this shipment should be tracked
		if(foundShipment == null) { //this is the first shipment being checked
			foundShipment = temp;
			foundAngle = angle;
			foundDistance = distance;
		}
		else {
			//if angle and disnace are smaller than what had been found
			//if (angle <= foundAngle && distance <= foundDistance) {
			if(angle + distance <= foundAngle + foundDistance) {
				//if ((angle*.90)+ (distance * 0.1)  <= (foundAngle*0.9) + (foundDistance*0.1)) {
				foundShipment = temp;
				foundAngle = angle;
				foundDistance = distance;
			}
		}
		temp = temp.getNext();
	}
	return foundShipment; //stub
}


}