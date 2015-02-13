package edu.sru.thangiah.zeus.pvrp;

//import the parent class


import edu.sru.thangiah.zeus.core.Nodes;


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


public class PVRPNodes
		extends Nodes
		implements java.io.Serializable, java.lang.Cloneable {

//VARIABLES
private PVRPNodes    previous;    //previous, next, theShipment all take precedence
private PVRPNodes    next;    //over the same names of base objects in the base class
private PVRPShipment theShipment;




//CONSTRUCTOR
public PVRPNodes() { this.theShipment = new PVRPShipment(); }




//CONSTRUCTOR
public PVRPNodes(PVRPShipment s) {
	this.theShipment = s;
}






//GETTER
public PVRPNodes getPrev() {
		return getPrevious();
	}

public PVRPNodes getPrevious(){ return this.previous; }


//SETTER
public void setPrevious(PVRPNodes p) {
	this.previous = p;
}




//SETTER
public PVRPNodes getNext() {
	return this.next;
}




//GETTER
public PVRPShipment getShipment() {
	return this.theShipment;
}




//GETTER
public int getIndex() {
	return this.theShipment.getIndex();
}




//GETTER
public int getDemand() {
	return this.theShipment.getDemand();
}





//SETTER
public void setNext(PVRPNodes n) {
	this.next = n;
}


}
