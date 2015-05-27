package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;


import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.core.Truck;
import edu.sru.thangiah.zeus.pvrp.PVRPAttributes;
import edu.sru.thangiah.zeus.pvrp.PVRPDayLinkedList;
import edu.sru.thangiah.zeus.pvrp.PVRPTruckType;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRCoordinates;
import edu.sru.thangiah.zeus.tr.TRTruckType;

//import the parent class




/**
 * PVRP (Periodic Vehicle Routing)
 * Routes vehicles to various nodes over a given number of days
 * <p/>
 * Version 1.0
 * <p/>
 * 10/17/14
 * <p/>
 * AUTHORS
 * Aaron Rockburn and Joshua Sarver
 * Slippery Rock University of Pa
 * CPSC 464 - Fall 2014
 * <p/>
 * COPYLEFT
 * Attribution-ShareAlike 4.0 International
 * <p/>
 * BASED ON
 * Dr. Sam R. Thangiah's work at Slippery Rock University of Pa
 * A Heuristic for the Periodic Vehicle Routing Problem by M. Gaudioso, G. Paletta
 * Chou
 * The Periodic Vehicle Routing Problem by S. Coene, A. Arnout and F. Spieksma
 * A Variable Neighborhood Search Heuristic for Periodic Routing Problems by Vera C. Hemmelmayr, Karl F. Doerner§,
 * Richard F. Hartl
 * <p/>
 * Methods are generally sorted by breadth-first order
 */

public class TRTruck
		extends Truck
		implements java.io.Serializable, Cloneable, ObjectInList {

private boolean isTruckOnRoad = false;
private TRTruckType  truckType;
private TRTruck      next;
private TRTruck      previous;
private TRAttributes  attributes = new TRAttributes();
private TRDaysList daysSubList = new TRDaysList();
private TRCoordinates homeDepotCoordinates;
//private TRTruckType truckType = new TRTruckType();




public TRTruck(final TRTruck copyMe) {
	setIsTruckOnRoad(copyMe.getIsTruckOnRoad());
	setTruckType(new TRTruckType(copyMe.getTruckType()));
	setAttributes(new TRAttributes(copyMe.getAttributes()));
	setSubList(new TRDaysList(copyMe.getSubList()));
	setHomeDepotCoordinates(new TRCoordinates(copyMe.getHomeDepotCoordinates()));

}



public boolean getIsTruckOnRoad() {
	return this.isTruckOnRoad;
}




public void setIsTruckOnRoad(final boolean isTruckOnRoad) {
	this.isTruckOnRoad = isTruckOnRoad;
}




@Override
public TRDaysList getSubList() {
	return this.daysSubList;
}




@Override
public void setSubList(final DoublyLinkedList subList) {
	this.daysSubList = (TRDaysList) subList;
}




public TRCoordinates getHomeDepotCoordinates() {
	return homeDepotCoordinates;
}




public void setHomeDepotCoordinates(final TRCoordinates homeDepotCoordinates) {
	this.homeDepotCoordinates = homeDepotCoordinates;
}




@Override
public TRTruckType getTruckType() {
	return truckType;
}




public TRTruck(final TRCoordinates homeDepotCoordinates) {
	setAttributes(new TRAttributes());
	setHomeDepotCoordinates(homeDepotCoordinates);
}




//CONSTRUCTOR
public TRTruck() {
	setAttributes(new TRAttributes());
}

    public String toString() {
        String s = "#" + 1 + " "/* + " Time" + this.truckType.getMaxCapacity() + " Max Dist =" + this.truckType.getMaxDistance()*/;
        return s;
    }


public void setTruckType(final TRTruckType truckType) {
	this.truckType = truckType;
}




public boolean insertShipment(final TRShipment theShipment) {
	boolean status = false;

	return this.getSubList().insertShipment(theShipment);
}//END INSERT_SHIPMENT *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<public TRTruck


// getNext() {


//return this.next;
//}




public TRAttributes getAttributes() {
	return this.attributes;
}




@Override
public void setAttributes(final TRAttributes attributes) {
	this.attributes = attributes;
}




public TRTruck getNext() {
	return this.next;
}




@Override
public void setNext(final ObjectInList next) {
	this.next = (TRTruck) next;
}




@Override
public boolean insertAfterCurrent(final ObjectInList insertMe) {
	if(this.getNext() != null) {
		insertMe.setPrevious(this);
		insertMe.setNext(this.getNext());

		this.setNext(insertMe);
		insertMe.getNext().setPrevious(insertMe);
		return true;
	}
	return false;
}




@Override
public void linkAsHeadTail(final ObjectInList linkTwo) {
	this.setNext(linkTwo);
	linkTwo.setPrevious(this);
	this.setPrevious(null);    //nothing comes before the head
	linkTwo.setNext(null);        //nothing comes after the tail
}




@Override
public boolean removeThisObject() {
	if(this.getNext() != null || this.getPrevious() != null) {

		this.getPrevious().setNext(this.getNext());
		this.getNext().setPrevious(this.getPrevious());

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
	this.previous = (TRTruck) previous;
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
