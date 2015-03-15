package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;


import edu.sru.thangiah.zeus.core.Depot;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRCoordinates;

//import the parent class




/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 *
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class TRDepot
		extends Depot
		implements java.io.Serializable, Cloneable, ObjectInList {


//VARIABLES
//private float maxDistance = -1;
//private float maxDemand   = -1;

private TRDepot next;
private TRDepot previous;
private TRTrucksList trucksSubList = new TRTrucksList();
private TRAttributes  attributes = new TRAttributes();
private TRCoordinates coordinates;
//private int depotNumber;




public TRDepot(final TRDepot copyMe) {
	setAttributes(new TRAttributes(copyMe.getAttributes()));
	setCoordinates(new TRCoordinates(copyMe.getCoordinates()));
	setSubList(new TRTrucksList(copyMe.getSubList()));
}




public TRCoordinates getCoordinates() {
	return this.coordinates;
}




private void setCoordinates(final TRCoordinates coordinates) {
	this.coordinates = coordinates;
}




@Override
public TRTrucksList getSubList() {
	return this.trucksSubList;
}




@Override
public void setSubList(final DoublyLinkedList trucksSubList) {
	this.trucksSubList = (TRTrucksList) trucksSubList;
}




public TRDepot(final TRCoordinates coordinates) {
	this.coordinates = coordinates;
	setSubList(new TRTrucksList());
}




public TRDepot() {
	setAttributes(new TRAttributes());
}


//public TRDepot(final TRCoordinates coordinates) {
//		super(edu.sru.thangiah.zeus.trGeneric.TRSolutionHierarchy.TRTrucksList.class);

//	this.setCoordinates(coordinates);
//	setAttributes(new TRAttributes());
//	trucksSubList = new TRTrucksList(coordinates);
//}




public boolean insertShipment(final TRShipment theShipment) {
	boolean status = false;

	return this.getSubList().insertShipment(theShipment);
}//END INSERT_SHIPMENT *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




public TRDepot getNext() {
	return this.next;
}




public TRAttributes getAttributes() {
	return this.attributes;
}


//	@Override
//	public boolean createSubList() {
//		return false;
//	}




@Override
public void setAttributes(final TRAttributes attributes) {
	this.attributes = attributes;
}




@Override
public boolean insertAfterCurrent(final ObjectInList insertMe) {
	if(this.getNext() != null) {
		(insertMe).setPrevious(this);
		(insertMe).setNext(this.getNext());

		(this).setNext(insertMe);
		(insertMe).getNext().setPrevious(insertMe);
		return true;
	}
	return false;
}




@Override
public void setNext(final ObjectInList next) {
	this.next = (TRDepot) next;
}




@Override
public void linkAsHeadTail(final ObjectInList linkTwo) {
	this.setNext(linkTwo);
	(linkTwo).setPrevious(this);
	this.setPrevious(null);    //nothing comes before the head
	(linkTwo).setNext(null);        //nothing comes after the tail
}




@Override
public boolean removeThisObject() {
	if(this.getNext() != null || this.getPrevious() != null) {

		(this.getPrevious()).setNext(this.getNext());
		(this.getNext()).setPrevious(this.getPrevious());

		this.setPrevious(null);
		this.setNext((ObjectInList) null);
		return true;
	}
	return false;
}




@Override
public ObjectInList getPrevious() {
	return this.previous;
}




@Override
public void setPrevious(final ObjectInList previous) {
	this.previous = (TRDepot) previous;
}




@Override
public boolean isSubListEmpty() {
	if(getSubList() == null || getSubList().isEmpty()) {
		return true;
	}
	return false;
}




@Override
public double getDistanceTravelledMiles() {
	return 0;
}


}
