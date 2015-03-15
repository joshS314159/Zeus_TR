package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;


import edu.sru.thangiah.zeus.core.ShipmentLinkedList;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;
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


public class TRShipmentsList
		extends ShipmentLinkedList
		implements java.io.Serializable, Cloneable, DoublyLinkedList {

private TRShipment   head;
private TRShipment   tail;
    private TRAttributes  attributes = new TRAttributes();




//CONSTRUCTOR
public TRShipmentsList() {
	setUpHeadTail();
	setAttributes(new TRAttributes());
}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




@Override
public void setUpHeadTail() {
	//	setHead((ObjectInList) new TRShipment());
	//	setTail((ObjectInList) new TRShipment());
	this.head = new TRShipment();
	this.tail = new TRShipment();
	linkHeadTail();
}




//METHOD
//link the head and the tail together
public void linkHeadTail() {
	getHead().linkAsHeadTail(getTail());
}//END LINK_HEAD_TAIL *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//GETTER
public TRShipment getHead() {
	return this.head;
}




//GETTER
public TRShipment getTail() {
	return this.tail;
}




public boolean isAllShipsAssigned() {
	//	public boolean isAllShipsAssigned() {
	int counter = 0;
	TRShipment ship = this.getHead().getNext();
	while(ship != this.getTail()) {
		if(!ship.getIsAssigned()) {
			counter++;
		}
		ship = ship.getNext();
	}
	if(counter == 0) {
		return true;
	}
	System.out.println("\n" + counter + " more shipments need assigned -- TRShipmentsList\n");

	return false;
}




public boolean isEmpty() {
	if(getSize() == 0) {
		return true;
	}
	return false;
}




@Override
public void setUpHeadTail(final ObjectInList head, final ObjectInList tail) {
	setHead(head);
	setTail(tail);
	linkHeadTail();
}




@Override
public TRAttributes getAttributes() {
	return this.attributes;
}




//SETTER
@Override
public void setAttributes(final TRAttributes attributes) {
	this.attributes = attributes;
}




@Override
public boolean setHead(final ObjectInList head) {
	//	return getHead().replaceThisWith((TRShipment) head);
	if(head != null) {
		head.setPrevious(getTail().getPrevious());
		head.getPrevious().setNext(head);
		getHead().setPrevious(null);
		getHead().setNext((ObjectInList) null);
		this.head = (TRShipment) head;
		return true;
	}
	return false;
}




@Override
public TRShipment getFirst() {
	if(isEmpty() || !isValidHeadTail()) {
        System.out.println("ERROR: getFirst() is null/invalid");
		return null;
	}
	return getHead().getNext();
}




@Override
public boolean insertAfterLastIndex(final ObjectInList theObject) {
	if(!isValidHeadTail()) {
		return false;
	}

	if(isEmpty()) {
		return getHead().insertAfterCurrent(theObject);
	}
	//otherwise we already got stuff in here
	return getLast().insertAfterCurrent(theObject);
}




@Override
public TRShipment getLast() {
	if(isEmpty() || !isValidHeadTail()) {
		return null;
	}
	return (TRShipment) getTail().getPrevious();
}




@Override
public boolean removeLast() {
	if(!isEmpty() && isValidHeadTail()) {
		return getTail().getPrevious().removeThisObject();
	}
	return false;
}




@Override
public boolean removeFirst() {
	if(!isEmpty() && isValidHeadTail()) {
		return getHead().getNext().removeThisObject();
	}
	return false;
}




@Override
public int getIndexOfObject(final ObjectInList findMe) {
	int counter = -1;
	TRShipment theShipment = this.getHead();

	if(!isEmpty() && isValidHeadTail()) {
		while(theShipment != findMe) {
			theShipment = theShipment.getNext();
			counter++;
			if(theShipment == tail) {
				return -1;
			}
		}
		return counter;
	}
	return -1;
}




@Override
public boolean setTail(final ObjectInList tail) {
	//	return getTail().replaceThisWith((TRShipment) tail);
	if(tail != null) {
		tail.setPrevious(getTail().getPrevious());
		tail.getPrevious().setNext(tail);
		getTail().setPrevious(null);
		getTail().setNext((ObjectInList) null);
		this.tail = (TRShipment) tail;
		return true;
	}
	return false;

}




@Override
public boolean isValidHeadTail() {
	if(getHead() == null || getHead().getNext() == null || getHead().getPrevious() != null ||
	   getTail().getPrevious() == null || getTail() == null || getTail().getNext() != null) {
		return false;
	}
	return true;
}




@Override
public boolean insertShipment(TRShipment insertShipment) {
	return false;
}




//@Override
//public boolean insertShipment() {
//	return false;
//}
//
@Override
public boolean removeByIndex(final int index) {
	int counter = -1;
	TRShipment theShipment = this.getHead();

	while(index >= 0 && index < getSize() && isValidHeadTail()) {
		theShipment = theShipment.getNext();
		counter++;
		if(counter == index) {
			return theShipment.removeThisObject();
		}
	}
	return false;
}




public int getSize() {
	TRShipment theDepot = getHead();
	int sizeCounter = 0;

	if(!isValidHeadTail()) {
		return -1;
	}

	while(theDepot.getNext() != getTail()) {
		theDepot = theDepot.getNext();
		sizeCounter++;
	}

	return sizeCounter;
}




@Override
public int getSizeWithHeadTail() {
	if(isValidHeadTail()) {
		return getSize() + 2;
	}
	return -1;
}




@Override
public boolean removeByObject(final ObjectInList findMe) {
	TRShipment theDepot = getHead();
	while(theDepot.getNext() != getTail() && isValidHeadTail()) {
		theDepot = theDepot.getNext();
		if(theDepot == findMe) {
			theDepot.removeThisObject();
			return true;
		}
	}
	return false;
}




@Override
public boolean insertAfterIndex(final ObjectInList insertMe, final int index) {
	int counter = -1;
	TRShipment theDepot = getHead();

	while(index >= 0 && index < getSize() && !isEmpty() && isValidHeadTail()) {
		theDepot = theDepot.getNext();
		counter++;
		if(counter == index) {
			theDepot.insertAfterCurrent(insertMe);
			return true;
		}
	}
	return false;
}




@Override
public ObjectInList getAtIndex(final int index) {
	int counter = -1;
	TRShipment theDepot = getHead();

	while(index >= 0 && index < getSize() && !isEmpty() && isValidHeadTail()) {
		theDepot = theDepot.getNext();
		counter++;
		if(counter == index) {
			return theDepot;
		}
	}
	return null;
}




@Override
public boolean insertAfterObject(final ObjectInList insertMe, final ObjectInList insertAfter) {
	TRShipment theDepot = head;
	while(!isEmpty() && isValidHeadTail()) {
		theDepot = theDepot.getNext();
		if(theDepot == insertAfter) {
			return insertAfter.insertAfterCurrent(insertMe);
			//			return true;
		}
	}
	return false;
}




@Override
public double getDistanceTravelledMiles() {
	return 0;
}




public TRShipmentsList(final TRShipmentsList copyMe) {
	setHead((ObjectInList) new TRShipment(copyMe.getHead()));
	setTail((ObjectInList) new TRShipment(copyMe.getTail()));
	setAttributes(new TRAttributes(copyMe.getAttributes()));

	TRShipment theCopyMeShipment = copyMe.getHead();
	TRShipment newShipment = getHead();
	while(theCopyMeShipment.getNext() != copyMe.getTail()) {
		theCopyMeShipment = theCopyMeShipment.getNext();
		newShipment.insertAfterCurrent(new TRShipment(theCopyMeShipment));
		newShipment = newShipment.getNext();
	}
}




//METHOD
//used by the gui to show problem information
public String getSolutionString() {
	return "Trucks Used = " + TRProblemInfo.noOfVehs + " | " + this.attributes.toDetailedString();
}




public TRShipment getNextInsertShipment(final TRDepotsList depotList, final TRDepot theDepot,
										final TRShipmentsList shipmentList, final TRShipment theShipment) {

	TRShipmentsList selectShip = (TRShipmentsList) TRProblemInfo.selectShipType;


	return selectShip.getSelectShipment(depotList, theDepot, shipmentList, theShipment);
}




public TRShipment getSelectShipment(TRDepotsList currDepotLL, TRDepot currDepot, TRShipmentsList currShipmentLL,
									TRShipment currShip) {
	return null;
}


//METHOD
//inserts a shipment
//public boolean insertShipment(final TRShipment theShipment) {
//	boolean status = false;
//
//	TRShipment depot = getHead().getNext();
//	TRTrucksList truckLL = null;
//
////	double  test = tempDepotLL.getAttributes().getTotalDemand();
//	while (depot != this.getTail()) {
//		//Get truck to insert the shipment
//		//while we have more depots
//
//		truckLL = depot.getSubList();
//		//get the trucks linked ist
//
//		status = truckLL.insertShipment(theShipment);
//		//insert the shipment into the trucks linked list
//
//		if (status) {
//			break;    //if it inserted into the list okay then break
//		}
//		depot = depot.getNext();
//	}
//	return status;    //return true if inserted OK
//}//END INSERT_SHIPMENT *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

//METHOD
//
//public void printDepotLinkedList(FileOutputStream out)
//		throws IOException {
//	writeOutData(out);
//
//}//END PRINT_DEPOT_LINKED_LIST *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

//METHOD
//writes out the entire depot linked list
//hierarchy in Excel format
//this will probably be used to print <<TEMP>> files
//thus it is printed for utility and not prettiness
//all empty cells are set to '-1'
//these means an easier algorithm since it doesn't
//have to read pretty format
//public void writeOutData(FileOutputStream out)
//		throws IOException {
//	TRShipment theDepot;
//
//	int rowCounter = 0;                //counts our rows
//	XSSFWorkbook workbook = new XSSFWorkbook();
//	XSSFSheet sheet = workbook.createSheet("Customer Data");    //create a worksheet
//	Row row;
//	Cell cell;
//	int columnCounter = 0;            //counts our columns
//	final int NULL_VALUE = -1;        //a null value for would-be empty cellss
//
//
//	//CREATE THE HEADER ROWS
//	//NEW ROW
//	row = sheet.createRow(rowCounter++);
//	cell = row.createCell(columnCounter++);        //Column 0
//	cell.setCellValue("0_NAME");
//
//	cell = row.createCell(columnCounter++);        //C1
//	cell.setCellValue("1_INDEX");
//
//	cell = row.createCell(columnCounter++);        //C2
//	cell.setCellValue("2_X");
//
//	cell = row.createCell(columnCounter++);        //C3
//	cell.setCellValue("3_Y");
//
//	cell = row.createCell(columnCounter++);        //C4
//	cell.setCellValue("4_DEMAND");
//
//	cell = row.createCell(columnCounter++);        //C5
//	cell.setCellValue("5_DISTANCE");
//
//	cell = row.createCell(columnCounter++);        //C6
//	cell.setCellValue("6_MAX_DEMAND");
//
//	cell = row.createCell(columnCounter++);        //C7
//	cell.setCellValue("7_MAX_DISTANCE");
//
//	cell = row.createCell(columnCounter++);        //C8
//	cell.setCellValue("8_SUB_CLASS_SIZE");
//
//	cell = row.createCell(columnCounter++);        //C9
//
//	cell.setCellValue("9_FREQUENCY");
//
//	//PRINT THE NUMBER OF DEPOTS
//	//NEW ROW
//	columnCounter = 0;
//	row = sheet.createRow(rowCounter++);
//	cell = row.createCell(columnCounter++);
//	cell.setCellValue("NUM_DEPOTS");
//
//	cell = row.createCell(columnCounter++);
//	cell.setCellValue(NULL_VALUE);
//
//	cell = row.createCell(columnCounter++);
//	cell.setCellValue(NULL_VALUE);
//
//	cell = row.createCell(columnCounter++);
//	cell.setCellValue(NULL_VALUE);
//
//	cell = row.createCell(columnCounter++);
//	cell.setCellValue(NULL_VALUE);
//
//	cell = row.createCell(columnCounter++);
//	cell.setCellValue(NULL_VALUE);
//
//	cell = row.createCell(columnCounter++);
//	cell.setCellValue(NULL_VALUE);
//
//	cell = row.createCell(columnCounter++);
//	cell.setCellValue(NULL_VALUE);
//
//	cell = row.createCell(columnCounter++);
//	cell.setCellValue(TRProblemInfo.numDepots);
//
//	cell = row.createCell(columnCounter++);
//	cell.setCellValue(NULL_VALUE);
//
//	theDepot = getHead().getNext();
//	while (theDepot != getTail()) {
//		//while we have more depots
//
//		columnCounter = 0;
//
//		//PRINT DEPOT INFORMATION
//		//NEW ROW
//		row = sheet.createRow(rowCounter++);        //make a new row
//		cell = row.createCell(columnCounter++);
//		cell.setCellValue("Depot");
//
//		cell = row.createCell(columnCounter++);
//		cell.setCellValue(theDepot.getDepotNum());
//
//		cell = row.createCell(columnCounter++);
//		cell.setCellValue(theDepot.getXCoord());
//
//		cell = row.createCell(columnCounter++);
//		cell.setCellValue(theDepot.getYCoord());
//
//		cell = row.createCell(columnCounter++);
//		cell.setCellValue(theDepot.getAttributes().getTotalDemand());
//
//		cell = row.createCell(columnCounter++);
//		cell.setCellValue(theDepot.getAttributes().getTotalDistance());
//
//		cell = row.createCell(columnCounter++);
////		cell.setCellValue(theDepot.getMaxDemand());
//
//		cell = row.createCell(columnCounter++);
////		cell.setCellValue(theDepot.getMaxDistance());
//
//		cell = row.createCell(columnCounter++);
//		cell.setCellValue(theDepot.getMainTrucks().getSize());
//
//		cell = row.createCell(columnCounter++);
//		cell.setCellValue(NULL_VALUE);
//
//
//		TRTrucksList truckLL = theDepot.getSubList();
//		//extract trucks linked list from depot
//		TRTruck theTruck = truckLL.getHead();
//		//extract single truck from the linked list
//		while (theTruck != truckLL.getTail()) {
//
//			columnCounter = 0;
//			int dayCount = 0;
//
//			//PRINT TRUCK INFORMATION
//			//NEW ROW
//			row = sheet.createRow(rowCounter++);
//			cell = row.createCell(columnCounter++);
//			cell.setCellValue("Truck");
//
//			cell = row.createCell(columnCounter++);
//			cell.setCellValue(theTruck.getTruckNum());
//
//			cell = row.createCell(columnCounter++);
//			cell.setCellValue(NULL_VALUE);
//
//			cell = row.createCell(columnCounter++);
//			cell.setCellValue(NULL_VALUE);
//
//			cell = row.createCell(columnCounter++);
//			cell.setCellValue(theTruck.getAttributes().getTotalDemand());
//
//			cell = row.createCell(columnCounter++);
//			cell.setCellValue(theTruck.getAttributes().getTotalDistance());
//
//			cell = row.createCell(columnCounter++);
////			cell.setCellValue(theTruck.getTruckType().getMaxCapacity());
//
//			cell = row.createCell(columnCounter++);
////			cell.setCellValue(theTruck.getTruckType().getMaxDuration());
//
//			cell = row.createCell(columnCounter++);
//			/** @todo fix this...numberOfDays have a random -2 in here*/
//			cell.setCellValue(theTruck.getMainDays().getSize() - 2);
//
//			cell = row.createCell(9);
//			cell.setCellValue(NULL_VALUE);
//
//			dayCount = 0;
//			TRDaysList daysLL = theTruck.getSubList();
//			TRDay theShipment = daysLL.getHead().getNext();
//			while (theShipment != daysLL.getTail()) {
//
//
//				//PRINT DAY INFORMATION
//				//NEW ROW
//				columnCounter = 0;
//				row = sheet.createRow(rowCounter++);
//				cell = row.createCell(columnCounter++);
//				cell.setCellValue("Day");
//
//				cell = row.createCell(columnCounter++);
//				cell.setCellValue(dayCount++);
//
//				cell = row.createCell(columnCounter++);
//				cell.setCellValue(NULL_VALUE);
//
//				cell = row.createCell(columnCounter++);
//				cell.setCellValue(NULL_VALUE);
//
//				cell = row.createCell(columnCounter++);
//				cell.setCellValue(theShipment.getAttributes().getTotalDemand());
//
//				cell = row.createCell(columnCounter++);
//				cell.setCellValue(theShipment.getAttributes().getTotalDistance());
//
//				cell = row.createCell(columnCounter++);
////				cell.setCellValue(theShipment.getMaxDemand());
//
//				cell = row.createCell(columnCounter++);
////				cell.setCellValue(theShipment.getMaxDistance());
//
//				cell = row.createCell(columnCounter++);
//				cell.setCellValue(theShipment.getNodesLinkedList().getSize() + 2);
//
//				cell = row.createCell(columnCounter++);
//				cell.setCellValue(NULL_VALUE);
//
//				TRNodesList nodesLL = theShipment.getSubList();
//				TRNode theNode = nodesLL.getHead();
//				while (true) {
//					//loops through every node for the chosen day and prints it
//					//breaks out of this infinite loop by checking if we finally
//					//printed the tail
//
//					TRShipment theShipment = theNode.getShipment();
//
//					columnCounter = 0;
//
//					//PRINT ALL NODE INFORMATION
//					//NEW ROW
//					row = sheet.createRow(rowCounter++);
//					cell = row.createCell(columnCounter++);    //create new cell number X
//					cell.setCellValue("Node");    //set each cell value to the correct value from the linked list
//
//					cell = row.createCell(columnCounter++);    //create new cell number X
//					cell.setCellValue(
//											 theShipment.getIndex());    //set each cell value to the correct value
// from the linked list
//
//					cell = row.createCell(columnCounter++);
//					cell.setCellValue(theShipment.getxCoord());
//
//
//					cell = row.createCell(columnCounter++);
//					cell.setCellValue(theShipment.getyCoord());
//
//
//					cell = row.createCell(columnCounter++);
//					cell.setCellValue(theShipment.getDemand());
//
//					cell = row.createCell(columnCounter++);
//					cell.setCellValue(NULL_VALUE);
//
//
//					cell = row.createCell(columnCounter++);
//					cell.setCellValue(NULL_VALUE);
//
//					cell = row.createCell(columnCounter++);
//					cell.setCellValue(NULL_VALUE);
//
//					cell = row.createCell(columnCounter++);
//					cell.setCellValue(NULL_VALUE);
//
//					cell = row.createCell(columnCounter++);
//					cell.setCellValue(theShipment.getFrequency());
//
//
//					if (theNode == nodesLL.getTail()) {
//						//if we have read and printed the tail
//						//break before we go into null territory
//						break;
//					}
//
//					theNode = theNode.getNext();
//				}
//				theShipment = theShipment.getNext();
//			}
//			theTruck = theTruck.getNext();
//		}
//		theDepot = theDepot.getNext();
//	}
//
//	workbook.write(out);        //write out to stream
//	out.close();                //close the stream
//}//END WRITE_OUT_DATA *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



@Override
public boolean setHeadNext(final ObjectInList nextHead) {
	if(this.getHead().getNext() == this.getTail()){
		return false;
	}
	this.getHead().setNext((ObjectInList) nextHead);
	return true;

}

@Override
public boolean setTailPrevious(final ObjectInList nextTail){
	if(this.getTail().getPrevious() == this.getHead()){
		return false;
	}
	this.getTail().setPrevious((ObjectInList) nextTail);
	return true;

}


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

        TRShipment theShipment = this.getFirst();
        while(theShipment != getTail())                                //while we aren'numberOfDays at the tail of the doubly linked list
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
                                theShipment.getNodeNumber());    //set each cell value to the correct value from the linked list
                        break;
                    case 1:
                        cell.setCellValue(theShipment.getCoordinates().getLongitude());
                        break;
                    case 2:
                        cell.setCellValue(theShipment.getCoordinates().getLatitude());

                        break;
                    case 3:
//                        cell.setCellValue(theShipment.getDemand());
                        break;
                    case 4:
                        cell.setCellValue(theShipment.getFrequency());
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
            theShipment = theShipment.getNext();                                //get the next shipment

        }

        workbook.write(out);
        out.close();

    }
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

}

