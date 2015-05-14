package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;


import edu.sru.thangiah.zeus.core.DepotLinkedList;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;
//import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Insertion.TRGreedyInsertionRevised;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

//import the parent class




public class TRDepotsList
		extends DepotLinkedList
		implements java.io.Serializable, Cloneable, DoublyLinkedList {

//VARIABLES
private TRDepot      head;     //takes precedence over the base class head and tail
private TRDepot      tail;
    private TRAttributes  attributes = new TRAttributes();     //takes precedence over the base class attributes




//CONSTRUCTOR
public TRDepotsList() {
	setUpHeadTail();
	setAttributes(new TRAttributes());
}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




@Override
public void setUpHeadTail() {
	this.head = new TRDepot();
	this.tail = new TRDepot();
	super.setHead(null);
	super.setTail(null);
	super.setAttributes(null);
	//	setHead((ObjectInList) new TRDepot());
	//	setTail((ObjectInList) new TRDepot());
	linkHeadTail();
}




//GETTER
@Override
public TRDepot getHead() {
	return this.head;
}




//GETTER
@Override
public TRDepot getTail() {
	return this.tail;
}




//METHOD
//link the head and the tail together
public void linkHeadTail() {
	getHead().linkAsHeadTail(getTail());
}//END LINK_HEAD_TAIL *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




@Override
public TRAttributes getAttributes() {
	return this.attributes;
}




//METHOD
//used by the gui to show problem information
public String getSolutionString() {
	return "Trucks Used = " + TRProblemInfo.noOfVehs + " | " + this.attributes.toDetailedString();
}




//SETTER
@Override
public void setAttributes(final TRAttributes attributes) {
	this.attributes = attributes;
}




@Override
public boolean setHead(final ObjectInList head) {
	//	return getHead().replaceThisWith((TRDepot) head);
	if(head != null) {
		head.setPrevious(getTail().getPrevious());
		head.getPrevious().setNext(head);
		getHead().setPrevious(null);
		getHead().setNext((ObjectInList) null);
		this.head = (TRDepot) head;
		return true;
	}
	return false;
}




@Override
public TRDepot getFirst() {
	if(isEmpty() || !isValidHeadTail()) {
        System.out.println("ERROR: getFirst() is null/invalid");
		return null;
	}

	return this.getHead().getNext();
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
public TRDepot getLast() {
	if(isEmpty() || !isValidHeadTail()) {
		return null;
	}
	return (TRDepot) getTail().getPrevious();
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
	TRDepot theDay = this.getHead();

	if(!isEmpty() && isValidHeadTail()) {
		while(theDay != findMe) {
			theDay = theDay.getNext();
			counter++;
			if(theDay == tail) {
				return -1;
			}
		}
		return counter;
	}
	return -1;
}




@Override
public boolean setTail(final ObjectInList tail) {
	//	return getTail().replaceThisWith((TRDepot) tail);
	if(tail != null) {
		tail.setPrevious(getTail().getPrevious());
		tail.getPrevious().setNext(tail);
		getTail().setPrevious(null);
		getTail().setNext((ObjectInList) null);
		this.tail = (TRDepot) tail;
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




//METHOD
//inserts a shipment
@Override
public boolean insertShipment(final TRShipment theShipment) {
    return false;

//    TRGreedyInsertionRevised insertion = new TRGreedyInsertionRevised();
//
//    insertion.getInsertShipment(this, theShipment);
//
//    return true;
//	boolean status = false;
//    final int LARGE_INT = 999999999;
//    int lowestDistance = LARGE_INT;
//    TRDepot lowestDepot = null;
//
//	TRDepot theDepot = this.getFirst();
//
//
//    while(theDepot != this.getTail()){
//        TRDepot copyDepot = new TRDepot(theDepot);
//
//        if(copyDepot.insertShipment(theShipment)){
//            TRProblemInfo.depotLLLevelCostF.calculateTotalsStats(this);
//            final int DEPOT_DISTANCE = (int) copyDepot.getAttributes().getTotalDistance();
//            if(DEPOT_DISTANCE < lowestDistance){
//               lowestDistance = DEPOT_DISTANCE;
//                lowestDepot = theDepot;
//            }
//        }
//
//        theDepot = theDepot.getNext();
//    }
//
//    if(lowestDistance != LARGE_INT && lowestDepot != null){
//        lowestDepot.insertShipment(theShipment);
//        return true;
//    }
//    return false;
//
//
//
//
//	while(theDepot != this.getTail()) {
//		if(theDepot.insertShipment(theShipment)) {
//            if(theDepot.getAttributes().getTotalDistance() < lowestDistance){
//                lowestDistance = (int) theDepot.getAttributes().getTotalDistance();
//                lowestDepot = theDepot;
//            }
//            theDepot.
//		}
//		theDepot = theDepot.getNext();
//	}

//	return false;

	/*
	boolean status = false;

	TRDepot depot = getHead().getNext();
	TRTrucksList truckLL = null;

//	double  test = tempDepotLL.getAttributes().getTotalDemand();
	while (depot != this.getTail()) {
		//Get truck to insert the shipment
		//while we have more depots

		truckLL = depot.getSubList();
		//get the trucks linked ist

		status = truckLL.insertShipment(theShipment);
		//insert the shipment into the trucks linked list

		if (status) {
			break;    //if it inserted into the list okay then break
		}
		depot = depot.getNext();
	}
	return status;    //return true if inserted OK
	*/
}//END INSERT_SHIPMENT *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

//@Override
//public boolean insertShipment(final TRShipment theShipment) {
//	return false;
//}




@Override
public boolean removeByIndex(final int index) {
	int counter = -1;
	TRDepot theDay = this.getHead();

	while(index >= 0 && index < getSize() && isValidHeadTail()) {
		theDay = theDay.getNext();
		counter++;
		if(counter == index) {
			return theDay.removeThisObject();
		}
	}
	return false;
}




public int getSize() {
	TRDepot theDepot = getHead();
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




public boolean isEmpty() {
	if(getSize() == 0) {
		return true;
	}
	return false;
}




@Override
public boolean removeByObject(final ObjectInList findMe) {
	TRDepot theDepot = getHead();
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
	TRDepot theDepot = getHead();

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
	TRDepot theDepot = getHead();

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
	TRDepot theDepot = head;
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




@Override
public void setUpHeadTail(final ObjectInList head, final ObjectInList tail) {
	this.head = new TRDepot();
	this.tail = new TRDepot();
	linkHeadTail();
}




public TRDepotsList(final TRDepotsList copyMe) {
	setHead((ObjectInList) new TRDepot(copyMe.getHead()));
	setTail((ObjectInList) new TRDepot(copyMe.getTail()));
	setAttributes(new TRAttributes(copyMe.getAttributes()));

	TRDepot theCopyMeDepot = copyMe.getHead();
	TRDepot newDepot = getHead();
	while(theCopyMeDepot.getNext() != copyMe.getTail()) {
		theCopyMeDepot = theCopyMeDepot.getNext();
		newDepot.insertAfterCurrent(new TRDepot(theCopyMeDepot));
		newDepot = newDepot.getNext();
	}
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
	TRDepot theDepot;

	int rowCounter = 0;                //counts our rows
	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet sheet = workbook.createSheet("Customer Data");    //create a worksheet
	Row row;
	Cell cell;
	int columnCounter = 0;            //counts our columns
	final String NULL_VALUE = "-";        //a null value for would-be empty cellss


	//CREATE THE HEADER ROWS
	//NEW ROW
	row = sheet.createRow(rowCounter++);
	cell = row.createCell(columnCounter++);        //Column 0
	cell.setCellValue("0_NAME");

	cell = row.createCell(columnCounter++);        //C1
	cell.setCellValue("1_INDEX");

	cell = row.createCell(columnCounter++);        //C2
	cell.setCellValue("2_LONG");

	cell = row.createCell(columnCounter++);        //C3
	cell.setCellValue("3_LAT");

	cell = row.createCell(columnCounter++);        //C4
	cell.setCellValue("COST");

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
	cell.setCellValue(TRProblemInfo.numDepots);

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
		//		cell.setCellValue(theDepot.getDepotNum());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theDepot.getCoordinates().getLongitude());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theDepot.getCoordinates().getLatitude());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theDepot.getAttributes().getTotalCost());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theDepot.getAttributes().getTotalDistance());

		cell = row.createCell(columnCounter++);
		//		cell.setCellValue(theDepot.getMaxDemand());

		cell = row.createCell(columnCounter++);
		//		cell.setCellValue(theDepot.getMaxDistance());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theDepot.getSubList().getSize());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(NULL_VALUE);


		TRTrucksList truckLL = theDepot.getSubList();
		//extract trucks linked list from depot
		TRTruck theTruck = truckLL.getHead().getNext();
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
			//			cell.setCellValue(theTruck.getTruckType().getMaxCapacity());

			cell = row.createCell(columnCounter++);
			//			cell.setCellValue(theTruck.getTruckType().getMaxDuration());

			cell = row.createCell(columnCounter++);
			cell.setCellValue(theTruck.getSubList().getSize());

			cell = row.createCell(9);
			cell.setCellValue(NULL_VALUE);

			dayCount = 0;
			TRDaysList daysLL = theTruck.getSubList();
			TRDay theDay = daysLL.getHead().getNext();
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
				//				cell.setCellValue(theDay.getMaxDemand());

				cell = row.createCell(columnCounter++);
				//				cell.setCellValue(theDay.getMaxDistance());

				cell = row.createCell(columnCounter++);
				cell.setCellValue(theDay.getSubList().getSize() + 2);

				cell = row.createCell(columnCounter++);
				cell.setCellValue(NULL_VALUE);

				TRNodesList nodesLL = theDay.getSubList();
				TRNode theNode = nodesLL.getHead();
				while(true) {
					//loops through every node for the chosen day and prints it
					//breaks out of this infinite loop by checking if we finally
					//printed the tail

					TRShipment theShipment = theNode.getShipment();

					columnCounter = 0;

					//PRINT ALL NODE INFORMATION
					//NEW ROW
					row = sheet.createRow(rowCounter++);
					cell = row.createCell(columnCounter++);    //create new cell number X
					cell.setCellValue("Node");    //set each cell value to the correct value from the linked list

					cell = row.createCell(columnCounter++);    //create new cell number X
					cell.setCellValue(
							theShipment.getNodeNumber());    //set each cell value to the correct value from the linked list

					cell = row.createCell(columnCounter++);
					cell.setCellValue(theShipment.getCoordinates().getLongitude());


					cell = row.createCell(columnCounter++);
					cell.setCellValue(theShipment.getCoordinates().getLatitude());


					cell = row.createCell(columnCounter++);
					cell.setCellValue(NULL_VALUE);

					cell = row.createCell(columnCounter++);
					cell.setCellValue(NULL_VALUE);


					cell = row.createCell(columnCounter++);
					cell.setCellValue(theShipment.getNumberOfBins());

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

}
