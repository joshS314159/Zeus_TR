package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;


import edu.sru.thangiah.zeus.core.DaysLinkedList;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;


public class TRDaysList
		extends DaysLinkedList
		implements java.io.Serializable, Cloneable, DoublyLinkedList {

private TRDay        head;
private TRDay        tail;
    private TRAttributes  attributes = new TRAttributes();
private int          dayNumber;




//CONSTRUCTOR
public TRDaysList() {
	setUpHeadTail();
	setAttributes(new TRAttributes());
}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




@Override
public void setUpHeadTail() {
	this.head = new TRDay();
	this.tail = new TRDay();
	linkHeadTail();
}




//GETTER
public TRDay getHead() {
	return this.head;
}




//GETTER
public TRDay getTail() {
	return this.tail;
}




public int getSize() {
	TRDay theDay = getHead();
	int sizeCounter = 0;

	if(!isValidHeadTail()) {
		return -1;
	}

	while(theDay.getNext() != getTail()) {
		theDay = theDay.getNext();
		sizeCounter++;
	}

	return sizeCounter;
}




//METHOD
//link the head and the tail together
public void linkHeadTail() {
	getHead().linkAsHeadTail(getTail());
}//END LINK_HEAD_TAIL *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




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
	//	return getHead().replaceThisWith((TRDay) head);
	if(head != null) {
		head.setPrevious(getTail().getPrevious());
		head.getPrevious().setNext(head);
		getHead().setPrevious(null);
		getHead().setNext((ObjectInList) null);
		this.head = (TRDay) head;
		return true;
	}
	return false;
}




@Override
public TRDay getFirst() {
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
public TRDay getLast() {
	if(isEmpty() || !isValidHeadTail()) {
		return null;
	}
	return (TRDay) getTail().getPrevious();
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
	TRDay theDay = this.getHead();

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
	//	return getTail().replaceThisWith((TRDay) tail);
	if(tail != null) {
		tail.setPrevious(getTail().getPrevious());
		tail.getPrevious().setNext(tail);
		getTail().setPrevious(null);
		getTail().setNext((ObjectInList) null);
		this.tail = (TRDay) tail;
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
//    TRDay lowestDay = null;
//
//    TRDay theDay = this.getFirst();
//
//
//    while(theDay != this.getTail()){
//        TRDay copyDay = new TRDay(theDay);
//
//        if(copyDay.insertShipment(theShipment)){
//            copyDay.getAttributes().getTotalDistance();
//            TRProblemInfo.daysLLLevelCostF.calculateTotalsStats(this);
//            final int DEPOT_DISTANCE = (int) copyDay.getAttributes().getTotalDistance();
//            if(DEPOT_DISTANCE < lowestDistance){
//                lowestDistance = DEPOT_DISTANCE;
//                lowestDay = theDay;
//            }
//        }
//
//        theDay = theDay.getNext();
//    }
//
//    if(lowestDistance != LARGE_INT && lowestDay != null){
//        lowestDay.insertShipment(theShipment);
//        return true;
//    }
//    return false;
//
//
//
//
//
//
//
//
	boolean status = false;

	TRDay theDay = this.getFirst();

	while(theDay != this.getTail()) {
		if(theDay.insertShipment(theShipment)) {
			return true;
		}
		theDay = theDay.getNext();
	}
	return false;
}

//@Override
//public boolean insertShipment() {
//	return false;
//}

//METHOD
//inserts a shipment




@Override
public boolean removeByIndex(final int index) {
	int counter = -1;
	TRDay theDay = this.getHead();

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
	TRDay theDay = getHead();
	while(theDay.getNext() != getTail() && isValidHeadTail()) {
		theDay = theDay.getNext();
		if(theDay == findMe) {
			theDay.removeThisObject();
			return true;
		}
	}
	return false;
}




@Override
public boolean insertAfterIndex(final ObjectInList insertMe, final int index) {
	int counter = -1;
	TRDay theDay = getHead();

	while(index >= 0 && index < getSize() && !isEmpty() && isValidHeadTail()) {
		theDay = theDay.getNext();
		counter++;
		if(counter == index) {
			theDay.insertAfterCurrent(insertMe);
			return true;
		}
	}
	return false;
}




@Override
public ObjectInList getAtIndex(final int index) {
	int counter = -1;
	TRDay theDay = getHead();

	while(index >= 0 && index < getSize() && !isEmpty() && isValidHeadTail()) {
		theDay = theDay.getNext();
		counter++;
		if(counter == index) {
			return theDay;
		}
	}
	return null;
}




@Override
public boolean insertAfterObject(final ObjectInList insertMe, final ObjectInList insertAfter) {
	TRDay theDay = head;
	while(!isEmpty() && isValidHeadTail()) {
		theDay = theDay.getNext();
		if(theDay == insertAfter) {
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




public TRDaysList(final TRDaysList copyMe) {
	setHead((ObjectInList) new TRDay(copyMe.getHead()));
	setTail((ObjectInList) new TRDay(copyMe.getTail()));
	setAttributes(new TRAttributes(copyMe.getAttributes()));

	TRDay theCopyMeDay = copyMe.getHead();
	TRDay newDay = getHead();
	while(theCopyMeDay.getNext() != copyMe.getTail()) {
		theCopyMeDay = theCopyMeDay.getNext();
		newDay.insertAfterCurrent(new TRDay(theCopyMeDay));
		newDay = newDay.getNext();
	}
}




//METHOD
//used by the gui to show problem information
public String getSolutionString() {
	return "Trucks Used = " + TRProblemInfo.noOfVehs + " | " + this.attributes.toDetailedString();
}

//METHOD
//



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
