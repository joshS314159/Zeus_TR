package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;


import edu.sru.thangiah.zeus.core.NodesLinkedList;
import edu.sru.thangiah.zeus.tr.*;

//import the parent class




public class TRNodesList
		extends NodesLinkedList
		implements java.io.Serializable, Cloneable, DoublyLinkedList {

//private TRNode       head;
//private TRNode       tail;
private TRTruckType truckType;
//private TRAttributes  attributes;
private TRFeasibility feasibility = new TRFeasibility();
private TRNode       head;
private TRNode       tail;
    private TRAttributes  attributes = new TRAttributes();




//CONSTRUCTOR
public TRNodesList() {
	setUpHeadTail();
	setAttributes(new TRAttributes());
}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




@Override
public void setUpHeadTail() {
	this.head = new TRNode();
	this.tail = new TRNode();
	//	setHead((ObjectInList) new TRNode());
	//	setTail((ObjectInList) new TRNode());
	linkHeadTail();
}




@Override
public void setUpHeadTail(final ObjectInList head, final ObjectInList tail) {
	setHead(head);
	setTail(tail);
	linkHeadTail();
}




public TRNodesList(final TRNodesList copyMe) {
	setHead((ObjectInList) new TRNode(copyMe.getHead()));
	setTail((ObjectInList) new TRNode(copyMe.getTail()));
	setAttributes(new TRAttributes(copyMe.getAttributes()));
	setFeasibility(new TRFeasibility(copyMe.getFeasibility()));

	TRNode theCopyMeNode = copyMe.getHead();
	TRNode newNode = getHead();
	while(theCopyMeNode.getNext() != copyMe.getTail()) {
		theCopyMeNode = theCopyMeNode.getNext();
		newNode.insertAfterCurrent(new TRNode(theCopyMeNode));
		newNode = newNode.getNext();
	}
}




public TRFeasibility getFeasibility() {
	return this.feasibility;
}




//GETTER
public TRNode getHead() {
	return this.head;
}




//GETTER
public TRNode getTail() {
	return this.tail;
}


//@Override
//public boolean insertShipment(final TRShipment theShipment) {
//	boolean status = false;
//
//	TRNode theNode = this.getFirst();
//
//	while(theNode != this.getTail()){
//		if(theNode.insertShipment(theShipment)){
//			return true;
//		}
//		theNode = theNode.getNext();
//	}
//	return false;
//}
//




//METHOD
//link the head and the tail together
public void linkHeadTail() {
	getHead().linkAsHeadTail(getTail());
}//END LINK_HEAD_TAIL *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




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
	//	return getHead().replaceThisWith((TRNode) head);
	if(head.getNext() == null) {

	}

	if(head != null) {
		TRNode next = getHead().getNext();
		//		head.setPrevious(getTail().getPrevious());
		this.head = (TRNode) head;
		//		head.setPrevious(null);
		//		head.getPrevious().setNext(head);
		getHead().setPrevious(null);
		getHead().setNext((ObjectInList) next);
		//		this.head = (TRNode) head;
		return true;
	}
	return false;
}




@Override
public TRNode getFirst() {
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
public TRNode getLast() {
	if(isEmpty() || !isValidHeadTail()) {
		return null;
	}
	return (TRNode) getTail().getPrevious();
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
	TRNode theNode = this.getHead();

	if(!isEmpty() && isValidHeadTail()) {
		while(theNode != findMe) {
			theNode = theNode.getNext();
			counter++;
			if(theNode == tail) {
				return -1;
			}
		}
		return counter;
	}
	return -1;
}




@Override
public boolean setTail(final ObjectInList tail) {
	//	return getTail().replaceThisWith((TRNode) tail);
	if(tail != null) {
		tail.setPrevious(getTail().getPrevious());
		tail.getPrevious().setNext(tail);
		getTail().setPrevious(null);
		getTail().setNext((ObjectInList) null);
		this.tail = (TRNode) tail;
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


public boolean insertShipmentLast(final TRShipment theShipment){
    return this.insertAfterLastIndex(new TRNode(theShipment));
}

//@Override
public boolean insertShipment(final TRShipment theShipment) {
	//method for inserting the shipment into a truck
	TRNodesList status = (TRNodesList) TRProblemInfo.insertShipType;

	return status.getInsertShipment(this, theShipment);
}




@Override
public boolean removeByIndex(final int index) {
	int counter = -1;
	TRNode theNode = this.getHead();

	while(index >= 0 && index < getSize() && isValidHeadTail()) {
		theNode = theNode.getNext();
		counter++;
		if(counter == index) {
			return theNode.removeThisObject();
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
	TRNode theDepot = getHead();
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
	TRNode theDepot = getHead();

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
public void emptyList(){
	this.getHead().setNext((ObjectInList) this.getTail());
	this.getTail().setPrevious(this.getHead());

	this.getHead().setPrevious(null);
	this.getTail().setNext((ObjectInList) null);
}


@Override
public ObjectInList getAtIndex(final int index) {
	int counter = -1;
	TRNode theDepot = getHead();

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
	TRNode theDepot = head;
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
	TRNode theNodeStepThrough = this.getHead();
	double distanceTravelledMiles = 0;
	TRNode pointOne = null;
	TRNode pointTwo = this.getHead();

	if(!isEmpty()) {
		while(pointTwo != this.getTail()) {
			//			for(int x = 0; x < this.getSizeWithHeadTail(); x++) {
			pointOne = pointTwo;
			pointTwo = pointTwo.getNext();
			distanceTravelledMiles +=
					pointOne.getHomeDepotCoordinates().calculateDistanceThisMiles(pointTwo.getHomeDepotCoordinates());

		}
		return distanceTravelledMiles;
	}
	return -1;
}




//@Override
public boolean getInsertShipment(final TRNodesList nodesList, final TRShipment theShipment) {
	return false;
}




public int getSize() {
	TRNode theDepot = getHead();
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




//@Override
private void setFeasibility(final TRFeasibility feasibility) {
	this.feasibility = feasibility;
}




public TRNodesList(final TRCoordinates coordinates) {
	this.head = new TRNode(coordinates);
	this.tail = new TRNode(coordinates);
	linkHeadTail();
	//	setHead((ObjectInList) new TRNode(coordinates));
	//	setTail((ObjectInList) new TRNode(coordinates));
}




public void setStartEndDepot(final TRDepot startDepot, final TRDepot endDepot) {
	setStartEndPoints(startDepot.getCoordinates(), endDepot.getCoordinates());
}




public void setStartEndPoints(final TRCoordinates startCoordinates, final TRCoordinates endCoordinates) {
	this.head = new TRNode(new TRShipment(startCoordinates));
	this.tail = new TRNode(new TRShipment(endCoordinates));
	linkHeadTail();
}




//METHOD
//used by the gui to show problem information
public String getSolutionString() {
	return "Trucks Used = " + TRProblemInfo.noOfVehs + " | " + this.attributes.toDetailedString();
}




//@Override
public TRNode insertNode(final TRNode insertNode) {
	System.out.println("ERROR: InsertNodes was called in from NodesLinkedList");
	return null;
}




public double getTotalDemand() {
	double demand = 0;
	TRNode theNode = this.getHead();
	//the head (depot) doesn't have a demand so we
	//can skip it

	while(theNode.getNext() != this.getTail() || theNode.getNext() != null) {
		theNode = theNode.getNext();
		//until we reach the tail
		demand += theNode.getDemand();
	}
	return demand;
}//END GET_TOTAL_DEMAND *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//GETTER


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




}//END PVRP_NODES_LINKED_LIST *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
