package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;


import edu.sru.thangiah.zeus.core.Depot;
import edu.sru.thangiah.zeus.core.DepotLinkedList;
import edu.sru.thangiah.zeus.core.Shipment;
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


	return (TRShipment) selectShip.getSelectShipment(depotList, theDepot, shipmentList, theShipment);
}




public Shipment getSelectShipment(DepotLinkedList currDepotLL, Depot currDepot, ShipmentLinkedList currShipmentLL,
								  Shipment currShip) {
	return null;
}

	public Shipment getSelectShipment(TRDepotsList currDepotLL, TRDepot currDepot, TRShipmentsList currShipmentLL,
								  TRShipment currShip) {
	return null;
}




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


	//make up the schedule of all shipments in list
	public void makeSchedule(){
		//if we are to keep the same day schedule as read in from the file, set the schedule for each shipment with this information
		if(TRProblemInfo.ARE_DAYS_BOUND){
			TRShipment ship = this.getFirst();
			while(ship != this.getTail()){
				ship.setVisitationSchedule();
                ship = ship.getNext();
			}
		}
		//if we are not to keep the same schedule that was read in, we need to redo things. This loop serves the purpose of taking
		//the frequency of each shipment and making a different schedule than the one that was read in
		else {
			//once the schedule is "balanced" we can exit the loop
			boolean isScheduleBalanced = false;
			while (!isScheduleBalanced) {
				//because shipments are read in from the file from smallest to highest frequency, we can schedule all stops that 
				//need the most visits first, the fill in the gaps with the shipments that require less visits. This is how we
				//"balance" the schedule.
				TRShipment ship = this.getLast();
				while (ship != this.getHead()) {
					ship.makeCustomVisitationSchedule();
					ship = (TRShipment) ship.getPrevious();
				}
				
				//once all schedules are made, we can check how balanced all of the days are in the new schedule. 
				
				//variables for the stop counts of each day
				int mCount, tCount, wCount, rCount, fCount, sCount;
				mCount = tCount = wCount = rCount = fCount = sCount = 0;
				ship = this.getFirst();
				//if a shipment is visited on a particular day, increase that days stop count
				while(ship != this.getTail()){
					boolean[] schedule = ship.getDaysVisited();
					for(int i = 0; i < schedule.length; i++){
						switch(i){
							case 0:
								if(schedule[i])
									mCount++;
								break;
							case 1:
								if(schedule[i])
									tCount++;
								break;
							case 2:
								if(schedule[i])
									wCount++;
								break;
							case 3:
								if(schedule[i])
									rCount++;
								break;
							case 4:
								if(schedule[i])
									fCount++;
								break;
							case 5:
								if(schedule[i])
									sCount++;
								break;
						}
					}
                    ship = ship.getNext();
				}
				//find the days with the most and least stops
				int leastStops = findMinVisitsDay(mCount, tCount, wCount,
						rCount, fCount, sCount);
				int mostStops = findMaxVisitsDay(mCount, tCount, wCount,
						rCount, fCount, sCount);

				//if the difference between most and least stops is greater than the value set in TRProblemInfo,
				//we need to try and set the schedules again. If it is within this range, the entire schedule is accepted. 
				if(mostStops - leastStops <= TRProblemInfo.MAX_DIFFERENCE_IN_DISTRIBUTION){
					isScheduleBalanced = true;
				}
			}
		}
	}

	//method to find the day that has the least stops. 
	private int findMinVisitsDay(int m, int t, int w, int r, int f, int s){
		//setting this variable above the amount of possible stops so it is always changed
		int leastStops = 99;
		if(TRProblemInfo.NUMBER_DAYS_SERVICED != TRProblemInfo.MAX_NUMBER_OF_DAYS_IN_SCHEDULE){

			if(leastStops > m)
				leastStops = m;
			if(leastStops > t)
				leastStops = t;
			if(leastStops > w)
				leastStops = w;
			if(leastStops > r)
				leastStops = r;
			if(leastStops > f)
				leastStops = f;
		}
		else{

			if(leastStops > m)
				leastStops = m;
			if(leastStops > t)
				leastStops = t;
			if(leastStops > w)
				leastStops = w;
			if(leastStops > r)
				leastStops = r;
			if(leastStops > f)
				leastStops = f;
			if(leastStops > s)
				leastStops = s;
		}

		return leastStops;
	}

	//method to find the day with the most visits
	private int findMaxVisitsDay(int m, int t, int w, int r, int f, int s){
		int mostStops = 1;

		//for 5 day schedule execute the loop inside if statement
		if(TRProblemInfo.NUMBER_DAYS_SERVICED != TRProblemInfo.MAX_NUMBER_OF_DAYS_IN_SCHEDULE){

			if(mostStops < m)
				mostStops = m;
			if(mostStops < t)
				mostStops = t;
			if(mostStops < w)
				mostStops = w;
			if(mostStops < r)
				mostStops = r;
			if(mostStops < f)
				mostStops = f;
		}
		//otherwise do the 6 day schedule
		else{

			if(mostStops < m)
				mostStops = m;
			if(mostStops < t)
				mostStops = t;
			if(mostStops < w)
				mostStops = w;
			if(mostStops < r)
				mostStops = r;
			if(mostStops < f)
				mostStops = f;
			if(mostStops < s)
				mostStops = s;
		}

		return mostStops;
	}
}

