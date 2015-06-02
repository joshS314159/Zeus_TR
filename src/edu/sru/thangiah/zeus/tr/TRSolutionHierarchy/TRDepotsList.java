package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;


import edu.sru.thangiah.zeus.core.DepotLinkedList;
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

//import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Insertion.TRGreedyInsertionRevised;

//import the parent class


public class TRDepotsList
		extends DepotLinkedList
		implements java.io.Serializable, Cloneable,// DoublyLinkedList
		DoublyLinkedListInterface<TRDepot>, DoublyLinkedListCoreInterface<TRDepot> {

//VARIABLES
//private TRDepot      head;     //takes precedence over the base class head and tail
//private TRDepot      tail;
//    private TRAttributes  attributes = new TRAttributes();     //takes precedence over the base class attributes


private DoublyLinkedList<TRDepotsList, TRDepot> doublyLinkedList = new DoublyLinkedList<>(this, TRDepot.class);

//CONSTRUCTOR
public TRDepotsList() {
	setUpHeadTail();
	setAttributes(new TRAttributes());
}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

@Override
public void setUpHeadTail() {
//	setHead(new TRDepot());
//	setTail(new TRDepot());
//	this.head = new TRDepot();
//	this.tail = new TRDepot();
	super.setHead(new TRDepot());
	super.setTail(new TRDepot());
	super.setAttributes(null);
	//	setHead((ObjectInList) new TRDepot());
	//	setTail((ObjectInList) new TRDepot());
	linkHeadTail();
}

//GETTER
@Override
public TRDepot getHead() {
	return (TRDepot) super.getHead();
}

//GETTER
@Override
public TRDepot getTail() {
	return (TRDepot) super.getTail();
}

@Override
public void setTail(final TRDepot tail) {
	super.setTail(tail);
}

@Override
public void setHead(final TRDepot head) {
	super.setHead(head);
}

//METHOD
//link the head and the tail together
public void linkHeadTail() {
	doublyLinkedList.linkHeadTail();
//	getHead().linkAsHeadTail(getTail());
}//END LINK_HEAD_TAIL *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

@Override
public void setUpHeadTail(final TRDepot head, final TRDepot tail) {
	doublyLinkedList.setUpHeadTail(head, tail);
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

//METHOD
//used by the gui to show problem information
public String getSolutionString() {
	return "Trucks Used = " + TRProblemInfo.noOfVehs + " | " + this.getAttributes().toDetailedString();
}

@Override
public TRDepot getFirst() {
	return doublyLinkedList.getFirst();
}

@Override
public boolean insertAfterLastIndex(final TRDepot theObject) {
	return doublyLinkedList.insertAfterLastIndex(theObject);
}

@Override
public TRDepot getLast() {
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
public int getIndexOfObject(final TRDepot findMe) {
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

public boolean isEmpty() {
	return doublyLinkedList.isEmpty();
}

@Override
public boolean removeByObject(final TRDepot findMe) {
	return doublyLinkedList.removeByObject(findMe);
}

@Override
public boolean insertAfterIndex(final TRDepot insertMe, final int index) {
	return doublyLinkedList.insertAfterIndex(insertMe, index);
}

@Override
public TRDepot getAtIndex(final int index) {
	return doublyLinkedList.getAtIndex(index);
}


@Override
public boolean insertAfterObject(final TRDepot insertMe, final TRDepot insertAfter) {
	return doublyLinkedList.insertAfterObject(insertMe, insertAfter);
}

@Override
public double getDistanceTravelledMiles() {
	return doublyLinkedList.getDistanceTravelledMiles();
}

@Override
public boolean setHeadNext(final TRDepot nextHead) {
	return doublyLinkedList.setHeadNext(nextHead);
}

@Override
public boolean setTailPrevious(final TRDepot previousTail) {
	return doublyLinkedList.setTailPrevious(previousTail);
}

public TRDepotsList(final TRDepotsList copyMe) throws InstantiationException, IllegalAccessException {
	setHead(new TRDepot(copyMe.getHead()));
	setTail( new TRDepot(copyMe.getTail()));
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
//inserts a shipment
//@Override
public boolean insertShipment(final TRShipment theShipment) {
	boolean status = false;

	TRDepot depot = this.getFirst();
	TRTrucksList truckLL = null;

//	double  test = tempDepotLL.getAttributes().getTotalDemand();
	while(depot != this.getTail() && !status) {
		//Get truck to insert the shipment
		//while we have more depots


		truckLL = depot.getSubList();
		//get the trucks linked ist

		status = truckLL.insertShipment(theShipment);
		//insert the shipment into the trucks linked list
//
//		if(status) {
//			break;    //if it inserted into the list okay then break
//		}
		depot = depot.getNext();
	}
	return status;    //return true if inserted OK

}//END INSERT_SHIPMENT *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

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
	cell.setCellValue("2_X/LONG");

	cell = row.createCell(columnCounter++);        //C3
	cell.setCellValue("3_Y/LAT");

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
	if(this.getFirst().getCoordinates().getIsCartesian()) {
		cell.setCellValue("cartesian");
	} else {
		cell.setCellValue("geographic");
	}


	cell = row.createCell(columnCounter++);
	if(this.getFirst().getCoordinates().getIsCartesian()) {
		cell.setCellValue("cartesian");
	} else {
		cell.setCellValue("geographic");
	}

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

	theDepot = this.getFirst();
	while(theDepot != this.getTail()) {
		//while we have more depots

		columnCounter = 0;

		//PRINT DEPOT INFORMATION
		//NEW ROW
		row = sheet.createRow(rowCounter++);        //make a new row
		cell = row.createCell(columnCounter++);
		cell.setCellValue("Depot");

		cell = row.createCell(columnCounter++);
				cell.setCellValue(this.getIndexOfObject(theDepot));

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theDepot.getCoordinates().getA());

		cell = row.createCell(columnCounter++);
		cell.setCellValue(theDepot.getCoordinates().getB());

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
		TRTruck theTruck = truckLL.getFirst();
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
			cell.setCellValue(truckLL.getIndexOfObject(theTruck));

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
			TRDay theDay = daysLL.getFirst();
			while(theDay != daysLL.getTail()) {


				//PRINT DAY INFORMATION
				//NEW ROW
				columnCounter = 0;
				row = sheet.createRow(rowCounter++);
				cell = row.createCell(columnCounter++);
				cell.setCellValue("Day");

				cell = row.createCell(columnCounter++);
				cell.setCellValue(daysLL.getIndexOfObject(theDay));

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
//					if(theShipment)
					final double coordinates[] = theShipment.getCoordinates().getCoordinateSet();
					final double a = coordinates[0];
					final double b = coordinates[1];
					cell.setCellValue(a);


					cell = row.createCell(columnCounter++);
					cell.setCellValue(b);


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



}
