package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;


import edu.sru.thangiah.zeus.core.Day;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRCoordinates;


public class TRDay
		extends Day
		implements java.io.Serializable, Cloneable, ObjectInList {


//VARIABLES
private TRAttributes  attributes = new TRAttributes();            //takes precedence over the base class attributes type
private TRDay        previous;    //takes precedence over the base class previous and next type
private TRDay        next;
private TRNodesList nodesSubList = new TRNodesList();
private TRCoordinates homeDepotCoordinates;
private int           dayNumber;




public TRDay(final TRDay copyMe) {
	setAttributes(new TRAttributes(copyMe.getAttributes()));
	setHomeDepotCoordinates(new TRCoordinates(copyMe.getHomeDepotCoordinates()));
	setSubList(new TRNodesList(copyMe.getSubList()));
	setHomeDepotCoordinates(new TRCoordinates(copyMe.getHomeDepotCoordinates()));
	setDayOfWeek(copyMe.getDayOfWeek());
}




public TRCoordinates getHomeDepotCoordinates() {
	return homeDepotCoordinates;
}


    public String toString()
    {
        String s = "#" + this.dayNumber;

        return s;
    }


public void setHomeDepotCoordinates(final TRCoordinates homeDepotCoordinates) {
	this.homeDepotCoordinates = homeDepotCoordinates;
}




@Override
public TRNodesList getSubList() {
	return nodesSubList;
}




@Override
public void setSubList(final DoublyLinkedList sublist) {
	this.nodesSubList = (TRNodesList) sublist;
}




public TRAttributes getAttributes() {
	return this.attributes;
}




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




public TRDay getNext() {
	return this.next;
}




@Override
public void setNext(final ObjectInList next) {
	this.next = (TRDay) next;
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
	this.previous = (TRDay) previous;
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




public TRDay() {
	setAttributes(new TRAttributes());
}




public TRDay(final TRCoordinates homeDepotCoordinates) {
	setAttributes(new TRAttributes());

	setSubList(new TRNodesList(homeDepotCoordinates));
	//	setHomeDepotCoordinates(homeDepotCoordinates);
}




public TRDay(final TRCoordinates coordinates, final int dayNumber) {
	setHomeDepotCoordinates(coordinates);
	setDayNumber(dayNumber);
}




public boolean setDayNumber(final int dayNumber) {
	if(dayNumber >= 0) {
		this.dayNumber = dayNumber;
		return true;
	}
	return false;
}




public boolean insertShipment(final TRShipment theShipment) {
	boolean status = false;

	return this.getSubList().insertShipment(theShipment);
}//END INSERT_SHIPMENT *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




public int getDayNumber() {
	return dayNumber;
}


}




