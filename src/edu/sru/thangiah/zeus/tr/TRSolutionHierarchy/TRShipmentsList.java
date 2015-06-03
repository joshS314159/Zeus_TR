package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;


import edu.sru.thangiah.zeus.core.Depot;
import edu.sru.thangiah.zeus.core.DepotLinkedList;
import edu.sru.thangiah.zeus.core.Shipment;
import edu.sru.thangiah.zeus.core.ShipmentLinkedList;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.DoublyLinkedList;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.DoublyLinkedListCoreInterface;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.DoublyLinkedListInterface;
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
		implements java.io.Serializable, Cloneable, //DoublyLinkedList
		DoublyLinkedListInterface<TRShipment>, DoublyLinkedListCoreInterface<TRShipment> {

//private TRShipment   head;
//private TRShipment   tail;
private TRAttributes attributes = new TRAttributes();


private DoublyLinkedList<TRShipmentsList, TRShipment> doublyLinkedList = new DoublyLinkedList<>(this,
		TRShipment.class);

//CONSTRUCTOR
public TRShipmentsList() {
	setUpHeadTail();
	setAttributes(new TRAttributes());
}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


@Override
public void setUpHeadTail() {
	//	setHead((ObjectInList) new TRShipment());
	//	setTail((ObjectInList) new TRShipment());
//	this.head = new TRShipment();
//	this.tail = new TRShipment();
	super.setTail(new TRShipment());
	super.setHead(new TRShipment());
	linkHeadTail();
}


//METHOD
//link the head and the tail together
public void linkHeadTail() {
	getHead().linkAsHeadTail(getTail());
}//END LINK_HEAD_TAIL *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

@Override
public void setUpHeadTail(final TRShipment head, final TRShipment tail) {
	doublyLinkedList.setUpHeadTail(head, tail);
}


//GETTER
public TRShipment getHead() {
	return (TRShipment) super.getHead();
}


//GETTER
public TRShipment getTail() {
	return (TRShipment) super.getTail();
}

@Override
public void setTail(final TRShipment tail) {
	super.setTail(tail);
}

@Override
public void setHead(final TRShipment head) {
	super.setHead(head);
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


//public boolean isAll


public boolean isEmpty() {
	return doublyLinkedList.isEmpty();
}

@Override
public boolean removeByObject(final TRShipment findMe) {
	return doublyLinkedList.removeByObject(findMe);
}

@Override
public boolean insertAfterIndex(final TRShipment insertMe, final int index) {
	return doublyLinkedList.insertAfterIndex(insertMe, index);
}

@Override
public TRShipment getAtIndex(final int index) {
	return doublyLinkedList.getAtIndex(index);
}

@Override
public TRShipment getFirst() {
	return doublyLinkedList.getFirst();
}

@Override
public boolean insertAfterLastIndex(final TRShipment theObject) {
	return doublyLinkedList.insertAfterLastIndex(theObject);
}

@Override
public TRShipment getLast() {
	return doublyLinkedList.getLast();
}

@Override
public boolean removeLast() {
	return doublyLinkedList.removeLast();
}

@Override
public boolean removeFirst() {
	return doublyLinkedList.removeFirst();
}

@Override
public int getIndexOfObject(final TRShipment findMe) {
	return doublyLinkedList.getIndexOfObject(findMe);
}

@Override
public boolean isValidHeadTail() {
	return doublyLinkedList.isValidHeadTail();
}


@Override
public boolean removeByIndex(final int index) {
	return doublyLinkedList.removeByIndex(index);
}

public int getSize() {
	return doublyLinkedList.getSize();
}

@Override
public int getSizeWithHeadTail() {
	return doublyLinkedList.getSizeWithHeadTail();
}

@Override
public boolean insertAfterObject(final TRShipment insertMe, final TRShipment insertAfter) {
	return doublyLinkedList.insertAfterObject(insertMe, insertAfter);
}

@Override
public double getDistanceTravelledMiles() {
	return doublyLinkedList.getDistanceTravelledMiles();
}

@Override
public boolean setHeadNext(final TRShipment nextHead) {
	return doublyLinkedList.setHeadNext(nextHead);
}

@Override
public boolean setTailPrevious(final TRShipment previousTail) {
	return doublyLinkedList.setTailPrevious(previousTail);
}


public TRShipmentsList(final TRShipmentsList copyMe) {
	setHead( new TRShipment(copyMe.getHead()));
	setTail( new TRShipment(copyMe.getTail()));
	setAttributes(new TRAttributes(copyMe.getAttributes()));

	TRShipment theCopyMeShipment = copyMe.getHead();
	TRShipment newShipment = getHead();
	while(theCopyMeShipment.getNext() != copyMe.getTail()) {
		theCopyMeShipment = theCopyMeShipment.getNext();
		newShipment.insertAfterCurrent(new TRShipment(theCopyMeShipment));
		newShipment = newShipment.getNext();
	}
}


public TRAttributes getAttributes() {
	return this.attributes;
}

//SETTER
public void setAttributes(final TRAttributes attributes) {
	this.attributes = attributes;
}


//@Override
public boolean insertShipment(TRShipment insertShipment) {
	return doublyLinkedList.insertAfterLastIndex(insertShipment);
}

//METHOD
//used by the gui to show problem information
public String getSolutionString() {
	return "Trucks Used = " + TRProblemInfo.noOfVehs + " | " + this.attributes.toDetailedString();
}


public TRShipment getNextInsertShipment(final TRDepotsList depotList, final TRDepot theDepot,
                                        final TRShipmentsList shipmentList, final TRShipment theShipment) {

	TRShipmentsList selectShip = (TRShipmentsList) TRProblemInfo.selectShipType;


	return (TRShipment) selectShip.getSelectShipment(depotList, theDepot, shipmentList, theShipment);
}

public Shipment getSelectShipment(TRDepotsList currDepotLL, TRDepot currDepot, TRShipmentsList currShipmentLL,
                                  TRShipment currShip) {
	return null;
}

public Shipment getSelectShipment(DepotLinkedList currDepotLL, Depot currDepot, ShipmentLinkedList currShipmentLL,
                                  Shipment currShip) {
	return null;
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
					cell.setCellValue(theShipment.getCoordinates().getA());
					break;
				case 2:
					cell.setCellValue(theShipment.getCoordinates().getB());

					break;
				case 3:
//                        cell.setCellValue(theShipment.getDemand());
					break;
				case 4:
					cell.setCellValue(theShipment.countFrequency());
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

