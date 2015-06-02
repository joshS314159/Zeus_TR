package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;


import edu.sru.thangiah.zeus.core.DaysLinkedList;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.DoublyLinkedList;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.DoublyLinkedListCoreInterface;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.DoublyLinkedListInterface;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.ObjectInList;


public class TRDaysList
		extends DaysLinkedList
		implements java.io.Serializable, Cloneable, //DoublyLinkedList
		DoublyLinkedListCoreInterface<TRDay>, DoublyLinkedListInterface<TRDay> {


//private TRDay        head;
//private TRDay        tail;
private TRAttributes attributes = new TRAttributes();
private int dayNumber;


private DoublyLinkedList<TRDaysList, TRDay> doublyLinkedList = new DoublyLinkedList<>(this,
		TRDay.class);

//CONSTRUCTOR
public TRDaysList() {
	setUpHeadTail();
	setAttributes(new TRAttributes());
}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


@Override
public void setUpHeadTail() {
	super.setHead(new TRDay());
	super.setTail(new TRDay());
//	this.head = new TRDay();
//	this.tail = new TRDay();
	linkHeadTail();
}


//GETTER
public TRDay getHead() {
//	return this.head;
	return (TRDay) super.getHead();
}


//GETTER
public TRDay getTail() {
	return (TRDay) super.getTail();
}

@Override
public void setTail(final TRDay tail) {
	super.setTail(tail);
}

@Override
public void setHead(final TRDay head) {
	super.setHead(head);
}


public int getSize() {
	return doublyLinkedList.getSize();
}


//METHOD
//link the head and the tail together
public void linkHeadTail() {
	doublyLinkedList.linkHeadTail();
}//END LINK_HEAD_TAIL *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

@Override
public void setUpHeadTail(final TRDay head, final TRDay tail) {
	doublyLinkedList.setUpHeadTail(head, tail);
}

@Override
public TRDay getFirst() {
	return doublyLinkedList.getFirst();
}

@Override
public boolean insertAfterLastIndex(final TRDay theObject) {
	return doublyLinkedList.insertAfterLastIndex(theObject);
}

@Override
public TRDay getLast() {
	return doublyLinkedList.getLast();
}

@Override
public boolean removeLast() {
	return doublyLinkedList.removeLast();
}

@Override
public boolean removeFirst() {
	return doublyLinkedList.removeLast();
}

@Override
public int getIndexOfObject(final TRDay findMe) {
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
public boolean removeByObject(final TRDay findMe) {
	return doublyLinkedList.removeByObject(findMe);
}

@Override
public boolean insertAfterIndex(final TRDay insertMe, final int index) {
	return doublyLinkedList.insertAfterIndex(insertMe, index);
}

@Override
public TRDay getAtIndex(final int index) {
	return doublyLinkedList.getAtIndex(index);
}


@Override
public boolean insertAfterObject(final TRDay insertMe, final TRDay insertAfter) {
	return doublyLinkedList.insertAfterObject(insertMe, insertAfter);
}

@Override
public double getDistanceTravelledMiles() {
	return doublyLinkedList.getDistanceTravelledMiles();
}

@Override
public boolean setHeadNext(final TRDay nextHead) {
	return doublyLinkedList.setHeadNext(nextHead);
}

@Override
public boolean setTailPrevious(final TRDay previousTail) {
	return doublyLinkedList.setTailPrevious(previousTail);
}


public TRDaysList(final TRDaysList copyMe) throws InstantiationException, IllegalAccessException {
	setHead( new TRDay(copyMe.getHead()));
	setTail( new TRDay(copyMe.getTail()));
	setAttributes(new TRAttributes(copyMe.getAttributes()));

	TRDay theCopyMeDay = copyMe.getHead();
	TRDay newDay = getHead();
	while(theCopyMeDay.getNext() != copyMe.getTail()) {
		theCopyMeDay = theCopyMeDay.getNext();
		newDay.insertAfterCurrent(new TRDay(theCopyMeDay));
		newDay = newDay.getNext();
	}
}



public TRAttributes getAttributes() {
	return this.attributes;
}

//SETTER
public void setAttributes(final TRAttributes attributes) {
	this.attributes = attributes;
}



public boolean insertShipment(final TRShipment theShipment) {
	boolean status = true;
	int visitCounter = 0;

	for(int i = 0; i < this.getSize(); i++) {
		if(theShipment.getDaysVisited()[i]) {
			status = status && this.getAtIndex(i).getSubList().insertShipment(theShipment);
			visitCounter++;
		}
	}
//
//	if(!theShipment.isShipmentFullyScheduled()) {
//		for(int i = 0; i < theShipment.getVisitComb().length; i++) {
//			if(theShipment.isDayAllowedToBeScheduled(i) && !theShipment.isDayAlreadyScheduled(i)) {
//				if(this.getAtIndex(i).getSubList().insertShipment(theShipment)) {
//					theShipment.setDayAsScheduled(i);
//					status = status && true;
//				} else {
//					status = status && false;
//				}
////			status = status && this.getAtIndex(i).getSubList().insertShipment(theShipment);
//			}
//		}
//	}

	if(!status) {
		System.out.print("UNSUCCESSFUL INSERTION :: TRDaysList[insertShipment]");
	}
	return status;

}

//METHOD
//used by the gui to show problem information
public String getSolutionString() {
	return "Trucks Used = " + TRProblemInfo.noOfVehs + " | " + this.attributes.toDetailedString();
}

//METHOD
//



}
