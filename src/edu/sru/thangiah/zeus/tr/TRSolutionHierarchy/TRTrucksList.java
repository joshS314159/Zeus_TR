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
//used by the gui to show problem information
public String getSolutionString() {
	return "Trucks Used = " + TRProblemInfo.noOfVehs + " | " + this.getAttributes().toDetailedString();
}

}
