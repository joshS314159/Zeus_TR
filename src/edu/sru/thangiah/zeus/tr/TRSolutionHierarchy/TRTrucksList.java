package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;

//import the parent class


import edu.sru.thangiah.zeus.core.TruckLinkedList;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.DoublyLinkedListCoreInterface;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.DoublyLinkedListInterface;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.DoublyLinkedList;


public class TRTrucksList
		extends TruckLinkedList
		implements java.io.Serializable, Cloneable,// DoublyLinkedList
		DoublyLinkedListInterface<TRTruck>, DoublyLinkedListCoreInterface<TRTruck> {


private DoublyLinkedList<TRTrucksList, TRTruck> doublyLinkedList = new DoublyLinkedList<>(this,
		TRTruck.class);

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
	setAttributes(new TRAttributes());
	super.setHead(new TRTruck());
	super.setTail(new TRTruck());
//	this.head = new TRTruck();
//	this.tail = new TRTruck();
	//	setHead((ObjectInList) new TRTruck());
	//	setTail((ObjectInList) new TRTruck());
	linkHeadTail();
}

//GETTER
public TRTruck getHead() {
	return (TRTruck) super.getHead();
}

//GETTER
public TRTruck getTail() {
	return (TRTruck) super.getTail();
}

@Override
public void setTail(final TRTruck tail) {
	super.setTail(tail);
}

@Override
public void setHead(final TRTruck head) {
	super.setHead(head);
}

//METHOD
//link the head and the tail together
public void linkHeadTail() {
	doublyLinkedList.linkHeadTail();
}//END LINK_HEAD_TAIL *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

@Override
public void setUpHeadTail(final TRTruck head, final TRTruck tail) {
	doublyLinkedList.setUpHeadTail(head, tail);
}

public int getSize() {
	return doublyLinkedList.getSize();
}

@Override
public TRAttributes getAttributes() {
	return (TRAttributes) super.getAttributes();
}

//SETTER
//@Override
public void setAttributes(final TRAttributes attributes) {
	super.setAttributes(attributes);
}

@Override
public TRTruck getFirst() {
	return doublyLinkedList.getFirst();
}

@Override
public boolean insertAfterLastIndex(final TRTruck theObject) {
	return doublyLinkedList.insertAfterLastIndex(theObject);
}

@Override
public TRTruck getLast() {
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
public int getIndexOfObject(final TRTruck findMe) {
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

@Override
public int getSizeWithHeadTail() {
	return doublyLinkedList.getSizeWithHeadTail();
}

public boolean isEmpty() {
	return doublyLinkedList.isEmpty();
}

@Override
public boolean removeByObject(final TRTruck findMe) {
	return doublyLinkedList.removeByObject(findMe);
}

@Override
public boolean insertAfterIndex(final TRTruck insertMe, final int index) {
	return doublyLinkedList.insertAfterIndex(insertMe, index);
}

@Override
public TRTruck getAtIndex(final int index) {
	return doublyLinkedList.getAtIndex(index);
}

@Override
public boolean insertAfterObject(final TRTruck insertMe, final TRTruck insertAfter) {
	return doublyLinkedList.insertAfterObject(insertMe, insertAfter);
}

@Override
public double getDistanceTravelledMiles() {
	return doublyLinkedList.getDistanceTravelledMiles();
}

@Override
public boolean setHeadNext(final TRTruck nextHead) {
	return doublyLinkedList.setHeadNext(nextHead);
}

@Override
public boolean setTailPrevious(final TRTruck previousTail) {
	return doublyLinkedList.setTailPrevious(previousTail);
}

public TRTrucksList(final TRTrucksList copyMe) throws InstantiationException, IllegalAccessException {
	setHead( new TRTruck(copyMe.getHead()));
	setTail( new TRTruck(copyMe.getTail()));
	setAttributes(new TRAttributes(copyMe.getAttributes()));

	TRTruck theCopyMeDepot = copyMe.getHead();
	TRTruck newDepot = getHead();
	while(theCopyMeDepot.getNext() != copyMe.getTail()) {
		theCopyMeDepot = theCopyMeDepot.getNext();
		newDepot.insertAfterCurrent(new TRTruck(theCopyMeDepot));
		newDepot = newDepot.getNext();
	}
}


public boolean insertShipment(final TRShipment theShipment) {
	boolean status = false;

	TRTruck truck = this.getFirst();
	TRDaysList daysLL = null;

//	double  test = tempDepotLL.getAttributes().getTotalDemand();
	while(truck != this.getTail() && !status) {
		//Get truck to insert the shipment
		//while we have more depots

		daysLL = truck.getSubList();
		//get the trucks linked ist

		status = daysLL.insertShipment(theShipment);
		//insert the shipment into the trucks linked list
//
//		if(status) {
//			break;    //if it inserted into the list okay then break
//		}
		truck = truck.getNext();
	}
	return status;    //return true if inserted OK
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


//METHOD
//used by the gui to show problem information
public String getSolutionString() {
	return "Trucks Used = " + TRProblemInfo.noOfVehs + " | " + this.getAttributes().toDetailedString();
}

}
