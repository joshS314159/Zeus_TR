package edu.sru.thangiah.zeus.pvrp;


import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.core.Truck;

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

public class PVRPTruck
		extends Truck
		implements java.io.Serializable, java.lang.Cloneable {


//VARIABLES
private PVRPTruck           previous;
private PVRPTruck           next;
private PVRPTruckType       truckType;
private PVRPNodesLinkedList mainNodes;
private PVRPDayLinkedList   mainDays;
private PVRPAttributes      attributes;




//CONSTRUCTOR
public PVRPTruck() {
	this.setTruckNum(-1);

	this.mainDays = new PVRPDayLinkedList();
	this.attributes = new PVRPAttributes();
}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//CONSTRUCTOR
public PVRPTruck(PVRPTruckType truckType, double depX, double depY) {
	this.attributes = new PVRPAttributes();
	setDepotX(depX);
	setDepotY(depY);

	this.truckType = truckType;

	this.attributes = new PVRPAttributes();

	setTruckNum(ProblemInfo.numTrucks++);

	this.mainDays = new PVRPDayLinkedList();

}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//GETTER
public PVRPTruck getPrevious() { return this.previous; }

public PVRPTruck getPrev() { return getPrevious(); }




//SETTER
public void setPrevious(PVRPTruck t) { this.previous = t; }

public void setPrev(PVRPTruck t){ setPrevious(t);}




//GETTER
public PVRPTruck getNext() { return this.next; }




//SETTER
public void setNext(PVRPTruck t) { this.next = t; }




//GETTER
public PVRPNodesLinkedList getMainNodes() { return this.mainNodes; }




//SETTER
//public void setMainNodes(PVRPNodesLinkedList nll) { this.mainNodes = nll; }




//GETTER
public PVRPDayLinkedList getMainDays() { return this.mainDays; }




//SETTER
public void setMainDays(PVRPDayLinkedList mainDays) { this.mainDays = mainDays; }




//GETTER
public PVRPTruckType getTruckType() { return this.truckType; }





//METHOD
//are the main days empty?
public boolean isEmptyMainDays() {
	if((this.mainDays.getHead() == this.mainDays.getTail()) || (this.mainDays.getSize() <= 2)) {
		//if the head == the tail we are empty
		//or if the size <=2 it means we have a head and tail or less
		return true;
	}
	return false;
}//END IS_EMPTY_MAIN_DAYS *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//GETTER
public PVRPAttributes getAttributes() { return this.attributes; }




//SETTER
public void setAttributes(PVRPAttributes attributes) { this.attributes = attributes; }





//SETTER
public void setTruckType(PVRPTruckType truckType) { this.truckType = truckType; }


//METHOD
//truck information for the gui
public String toString()
{
	String s = "#" + this.getTruckNum() + " " +
			" Max Q=" + this.truckType.getMaxCapacity() + " Max Dist=" + this.truckType.getMaxDistance();



	return s;
}





}
