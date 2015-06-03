package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;


import edu.sru.thangiah.zeus.core.NodesLinkedList;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRCoordinates;
import edu.sru.thangiah.zeus.tr.TRFeasibility;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.DoublyLinkedList;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.DoublyLinkedListCoreInterface;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.DoublyLinkedListInterface;

//import the parent class


public class TRNodesList
		extends NodesLinkedList
		implements java.io.Serializable, Cloneable,// DoublyLinkedList {
		DoublyLinkedListInterface<TRNode>, DoublyLinkedListCoreInterface<TRNode> {


//private TRNode       head;
//private TRNode       tail;
//	private TRTruckType truckType;
//private TRAttributes  attributes;
//	private TRFeasibility feasibility = new TRFeasibility();
//private TRNode       head;
//private TRNode       tail;
//    private TRAttributes  attributes = new TRAttributes();

private DoublyLinkedList<TRNodesList, TRNode> doublyLinkedList = new DoublyLinkedList<>(this,
		TRNode.class);

//CONSTRUCTOR
public TRNodesList() {
	setUpHeadTail();
	setAttributes(new TRAttributes());
	super.setFeasibility(new TRFeasibility());
}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

@Override
public void setUpHeadTail() {
	super.setHead(new TRNode());
	super.setTail(new TRNode());
//	this.head = new TRNode();
//	this.tail = new TRNode();
	//	setHead((ObjectInList) new TRNode());
	//	setTail((ObjectInList) new TRNode());
	linkHeadTail();
}

@Override
public void setUpHeadTail(final TRNode head, final TRNode tail) {
	doublyLinkedList.setUpHeadTail(head, tail);
}

@Override
public TRNode getFirst() {
	return doublyLinkedList.getFirst();
}

@Override
public boolean insertAfterLastIndex(final TRNode theObject) {
	return doublyLinkedList.insertAfterLastIndex(theObject);
}

@Override
public TRNode getLast() {
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
public int getIndexOfObject(final TRNode findMe) {
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
public boolean removeByObject(final TRNode findMe) {
	return doublyLinkedList.removeByObject(findMe);
}

@Override
public boolean insertAfterIndex(final TRNode insertMe, final int index) {
	return doublyLinkedList.insertAfterIndex(insertMe, index);
}

@Override
public TRNode getAtIndex(final int index) {
	return doublyLinkedList.getAtIndex(index);
}


@Override
public boolean insertAfterObject(final TRNode insertMe, final TRNode insertAfter) {
	return doublyLinkedList.insertAfterObject(insertMe, insertAfter);
}

@Override
public double getDistanceTravelledMiles() {
	return doublyLinkedList.getDistanceTravelledMiles();
}

@Override
public boolean setHeadNext(final TRNode nextHead) {
	return doublyLinkedList.setHeadNext(nextHead);
}

@Override
public boolean setTailPrevious(final TRNode previousTail) {
	return doublyLinkedList.setTailPrevious(previousTail);
}

public TRNodesList(final TRNodesList copyMe) {
	setHead( new TRNode(copyMe.getHead()));
	setTail( new TRNode(copyMe.getTail()));
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

public TRNodesList(final TRCoordinates coordinates) {
	super.setHead(new TRNode(coordinates));
	super.setTail(new TRNode(coordinates));
//	this.head = new TRNode(coordinates);
//	this.tail = new TRNode(coordinates);
	linkHeadTail();
	setAttributes(new TRAttributes());
	super.setFeasibility(new TRFeasibility());
	//	setHead((ObjectInList) new TRNode(coordinates));
	//	setTail((ObjectInList) new TRNode(coordinates));
}

public TRFeasibility getFeasibility() {
	return (TRFeasibility) super.getFeasibility();
}

//@Override
private void setFeasibility(final TRFeasibility feasibility) {
	super.setFeasibility((TRFeasibility) feasibility);
}

//GETTER
public TRNode getHead() {
	return (TRNode) super.getHead();
}

//GETTER
public TRNode getTail() {
	return (TRNode) super.getTail();
}

@Override
public void setTail(final TRNode tail) {
	super.setTail(tail);
}

//METHOD
//link the head and the tail together
public void linkHeadTail() {
	doublyLinkedList.linkHeadTail();
}//END LINK_HEAD_TAIL *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

@Override
public TRAttributes getAttributes() {
	return (TRAttributes) super.getAttributes();
}

//SETTER
//@Override
public void setAttributes(final TRAttributes attributes) {
	super.setAttributes(attributes);
}

public int getSize() {
	return doublyLinkedList.getSize();
}


@Override
public void setHead(final TRNode head) {
	super.setHead(head);
}


public boolean insertShipmentLast(final TRShipment theShipment) {
	return this.insertAfterLastIndex(new TRNode(theShipment));
}

//@Override
public boolean insertShipment(final TRShipment theShipment) {
	//method for inserting the shipment into a truck
	TRNodesList status = (TRNodesList) TRProblemInfo.insertShipType;

	return status.getInsertShipment(this, theShipment);
}

//@Override
public boolean getInsertShipment(final TRNodesList nodesList, final TRShipment theShipment) {
	return false;
}

public void setStartEndDepot(final TRDepot startDepot, final TRDepot endDepot) {
	setStartEndPoints(startDepot.getCoordinates(), endDepot.getCoordinates());
}


public void setStartEndPoints(final TRCoordinates startCoordinates, final TRCoordinates endCoordinates) {
	super.setHead(new TRNode(new TRShipment(startCoordinates)));
	super.setTail(new TRNode(new TRShipment(endCoordinates)));
	linkHeadTail();
}


//METHOD
//used by the gui to show problem information
public String getSolutionString() {
	return "Trucks Used = " + TRProblemInfo.noOfVehs + " | " + this.getAttributes().toDetailedString();
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

	while(theNode.getNext() != this.getTail() && theNode.getNext() != null) {
		theNode = theNode.getNext();
		//until we reach the tail
		demand += theNode.getDemand();
	}
	return demand;
}//END GET_TOTAL_DEMAND *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//GETTER



}//END PVRP_NODES_LINKED_LIST *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
