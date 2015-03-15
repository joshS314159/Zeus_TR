package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;

//import the parent class


import edu.sru.thangiah.zeus.core.TruckLinkedList;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;


public class TRTrucksList
		extends TruckLinkedList
		implements java.io.Serializable, Cloneable, DoublyLinkedList {

    private TRAttributes  attributes = new TRAttributes();
private TRTruck      head;
private TRTruck      tail;




//CONSTRUCTOR
public TRTrucksList() {
	setUpHeadTail();
	setAttributes(new TRAttributes());
}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//public TRTrucksList(final TRCoordinates homeDepotCoordinates) {
//	setUpHeadTail();
//	setAttributes(new TRAttributes());
//}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//public void setUpHeadTail(final TRCoordinates homeDepotCoordinates) {
//	this.head = new TRTruck(homeDepotCoordinates);
//	this.tail = new TRTruck(homeDepotCoordinates);
//	setHead((ObjectInList) new TRTruck());
//	setTail((ObjectInList) new TRTruck());
//	linkHeadTail();
//}




@Override
public void setUpHeadTail() {
	this.head = new TRTruck();
	this.tail = new TRTruck();
	//	setHead((ObjectInList) new TRTruck());
	//	setTail((ObjectInList) new TRTruck());
	linkHeadTail();
}




//GETTER
public TRTruck getHead() {
	return this.head;
}




//GETTER
public TRTruck getTail() {
	return this.tail;
}




//METHOD
//link the head and the tail together
public void linkHeadTail() {
	getHead().linkAsHeadTail(getTail());
}//END LINK_HEAD_TAIL *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




public int getSize() {
	TRTruck theTruck = getHead();
	int sizeCounter = 0;

	if(!isValidHeadTail()) {
		return -1;
	}

	while(theTruck.getNext() != getTail()) {
		theTruck = theTruck.getNext();
		sizeCounter++;
	}

	return sizeCounter;
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
	//	return getHead().replaceThisWith((TRTruck) head);
	if(head != null) {
		head.setPrevious(getTail().getPrevious());
		head.getPrevious().setNext(head);
		getHead().setPrevious(null);
		getHead().setNext((ObjectInList) null);
		this.head = (TRTruck) head;
		return true;
	}
	return false;
}




@Override
public TRTruck getFirst() {
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
public TRTruck getLast() {
	if(isEmpty() || !isValidHeadTail()) {
		return null;
	}
	return (TRTruck) getTail().getPrevious();
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
	TRTruck theDay = this.getHead();

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
	//	return getTail().replaceThisWith((TRTruck) tail);
	if(tail != null) {
		tail.setPrevious(getTail().getPrevious());
		tail.getPrevious().setNext(tail);
		getTail().setPrevious(null);
		getTail().setNext((ObjectInList) null);
		this.tail = (TRTruck) tail;
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
public boolean insertShipment(final TRShipment theShipment) {
//    boolean status = false;
//    final int LARGE_INT = 999999999;
//    int lowestDistance = LARGE_INT;
//    TRTruck lowestTruck = null;
//
//    TRTruck theTruck = this.getFirst();
//
//
//    while(theTruck != this.getTail()){
//        TRTruck copyTruck = new TRTruck(theTruck);
//
//        if(copyTruck.insertShipment(theShipment)){
//            TRProblemInfo.truckLLLevelCostF.calculateTotalsStats(this);
//            final int DEPOT_DISTANCE = (int) copyTruck.getAttributes().getTotalDistance();
//            if(DEPOT_DISTANCE < lowestDistance){
//                lowestDistance = DEPOT_DISTANCE;
//                lowestTruck = theTruck;
//            }
//        }
//
//        theTruck = theTruck.getNext();
//    }
//
//    if(lowestDistance != LARGE_INT && lowestTruck != null){
//        lowestTruck.insertShipment(theShipment);
//        return true;
//    }
//    return false;
//
//
	boolean status = false;

	TRTruck theTruck = this.getFirst();

	while(theTruck != this.getTail()) {
		if(theTruck.insertShipment(theShipment)) {
			return true;
		}
		theTruck = theTruck.getNext();
	}
	return false;
}




@Override
public boolean removeByIndex(final int index) {
	int counter = -1;
	TRTruck theDay = this.getHead();

	while(index >= 0 && index < getSize() && isValidHeadTail()) {
		theDay = theDay.getNext();
		counter++;
		if(counter == index) {
			return theDay.removeThisObject();
		}
	}
	return false;
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
	TRTruck theTruck = getHead();
	while(theTruck.getNext() != getTail() && isValidHeadTail()) {
		theTruck = theTruck.getNext();
		if(theTruck == findMe) {
			theTruck.removeThisObject();
			return true;
		}
	}
	return false;
}




@Override
public boolean insertAfterIndex(final ObjectInList insertMe, final int index) {
	int counter = -1;
	TRTruck theTruck = getHead();

	while(index >= 0 && index < getSize() && !isEmpty() && isValidHeadTail()) {
		theTruck = theTruck.getNext();
		counter++;
		if(counter == index) {
			theTruck.insertAfterCurrent(insertMe);
			return true;
		}
	}
	return false;
}




@Override
public ObjectInList getAtIndex(final int index) {
	int counter = -1;
	TRTruck theTruck = getHead();

	while(index >= 0 && index < getSize() && !isEmpty() && isValidHeadTail()) {
		theTruck = theTruck.getNext();
		counter++;
		if(counter == index) {
			return theTruck;
		}
	}
	return null;
}




@Override
public boolean insertAfterObject(final ObjectInList insertMe, final ObjectInList insertAfter) {
	TRTruck theTruck = head;
	while(!isEmpty() && isValidHeadTail()) {
		theTruck = theTruck.getNext();
		if(theTruck == insertAfter) {
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
public boolean setHeadNext(final ObjectInList nextHead) {
	if(this.getHead().getNext() == this.getTail()) {
		return false;
	}
	this.getHead().setNext(nextHead);
	return true;

}




@Override
public boolean setTailPrevious(final ObjectInList nextTail) {
	if(this.getTail().getPrevious() == this.getHead()) {
		return false;
	}
	this.getTail().setPrevious(nextTail);
	return true;

}




@Override
public void setUpHeadTail(final ObjectInList head, final ObjectInList tail) {
	this.head = (TRTruck) head;
	this.tail = (TRTruck) tail;
	//	setHead(head);
	//	setTail(tail);
	linkHeadTail();
}

//METHOD
//inserts a shipment
//public boolean insertShipment(final TRShipment theShipment) {
//	boolean status = false;
//
//	TRTruck depot = getHead().getNext();
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
/*
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
	TRTruck theTruck;

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
	cell.setCellValue(TRProblemInfo.numDepots);

	cell = row.createCell(columnCounter++);
	cell.setCellValue(NULL_VALUE);

	theTruck = getHead().getNext();
	while (theTruck != getTail()) {
		//while we have more depots

		columnCounter = 0;

		//PRINT DEPOT INFORMATION
		//NEW ROW
		row = sheet.createRow(rowCounter++);        //make a new row
		cell = row.createCell(columnCounter++);
		cell.setCellValue("Depot");

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theTruck.getDepotNum());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theTruck.getXCoord());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theTruck.getYCoord());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theTruck.getAttributes().getTotalDemand());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theTruck.getAttributes().getTotalDistance());

		cell = row.createCell(columnCounter++);
//		cell.setCellValue(theTruck.getMaxDemand());

		cell = row.createCell(columnCounter++);
//		cell.setCellValue(theTruck.getMaxDistance());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theTruck.getMainTrucks().getSize());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(NULL_VALUE);


		TRTrucksList truckLL = theTruck.getSubList();
		//extract trucks linked list from depot
		TRTruck theTruck = truckLL.getHead();
		//extract single truck from the linked list
		while (theTruck != truckLL.getTail()) {

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
			/** @todo fix this...numberOfDays have a random -2 in here*/
			/*cell.setCellValue(theTruck.getMainDays().getSize() - 2);

			cell = row.createCell(9);
			cell.setCellValue(NULL_VALUE);

			dayCount = 0;
			TRDaysList daysLL = theTruck.getSubList();
			TRDay theDay = daysLL.getHead().getNext();
			while (theDay != daysLL.getTail()) {


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
				cell.setCellValue(theDay.getNodesLinkedList().getSize() + 2);

				cell = row.createCell(columnCounter++);
				cell.setCellValue(NULL_VALUE);

				TRNodesList nodesLL = theDay.getSubList();
				TRNode theNode = nodesLL.getHead();
				while (true) {
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
											 theShipment.getIndex());    //set each cell value to the correct value
											 from the linked list

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


					if (theNode == nodesLL.getTail()) {
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
		theTruck = theTruck.getNext();
	}

	workbook.write(out);        //write out to stream
	out.close();                //close the stream
}//END WRITE_OUT_DATA *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


*/




public TRTrucksList(final TRTrucksList copyMe) {
	setHead((ObjectInList) new TRTruck(copyMe.getHead()));
	setTail((ObjectInList) new TRTruck(copyMe.getTail()));
	setAttributes(new TRAttributes(copyMe.getAttributes()));

	TRTruck theCopyMeDepot = copyMe.getHead();
	TRTruck newDepot = getHead();
	while(theCopyMeDepot.getNext() != copyMe.getTail()) {
		theCopyMeDepot = theCopyMeDepot.getNext();
		newDepot.insertAfterCurrent(new TRTruck(theCopyMeDepot));
		newDepot = newDepot.getNext();
	}
}




//METHOD
//used by the gui to show problem information
public String getSolutionString() {
	return "Trucks Used = " + TRProblemInfo.noOfVehs + " | " + this.attributes.toDetailedString();
}

}
