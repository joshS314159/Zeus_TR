package edu.sru.thangiah.zeus.pvrp;


import edu.sru.thangiah.zeus.core.Attributes;
import edu.sru.thangiah.zeus.core.Day;


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

public class PVRPDay
		extends Day
		implements java.io.Serializable, java.lang.Cloneable {

//VARIABLES
PVRPAttributes attributes;            //takes precedence over the base class attributes type
private int nodes, numberTrucks, dayNumber, planningPeriod;

private float maxDistance = -1, maxDemand = -1;

private PVRPDay previous,    //takes precedence over the base class previous and next type
		next;
private PVRPNodesLinkedList mainNodes;    //takes precedence over the base class main nodes type




//CONSTRUCTOR
//this method creates a new day
//and creates attributes for it
public PVRPDay() {
	this.attributes = new PVRPAttributes();
}//CONSTRUCTOR ENDS HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//CONSTUCTOR
//this method creates a new day based
//on passed attributes (usually more useful than the above
//constructor)
public PVRPDay(int nodes, int numberTrucks, int planningPeriod, float maxDistance, float maxDemand,
			   double depX, double depY, int dayNumber) {


	this.attributes = new PVRPAttributes();

	this.nodes = nodes;
	this.numberTrucks = numberTrucks;
	this.planningPeriod = planningPeriod;
	this.maxDistance = maxDistance;
	this.maxDemand = maxDemand;
	this.dayNumber = dayNumber;

	setNodesLinkedList(new PVRPNodesLinkedList(depX, depY));


	setAttributes(new PVRPAttributes());

}//CONSTRUCTOR ENDS
// HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//GETTER
public PVRPDay getPrev() { return getPrevious(); }

public PVRPDay getPrevious() { return this.previous; }




//GETTER
public PVRPDay getNext() {
	return this.next;

}





//GETTER
public PVRPNodesLinkedList getNodesLinkedList() {
	return this.mainNodes;
}




//SETTER
public void setNodesLinkedList(PVRPNodesLinkedList nll) { this.mainNodes = nll; }




//METHOD
//just checks to see if main nodes (which each day has) is empty
public boolean isEmpty() {
	if((this.mainNodes == null) || (this.mainNodes.getSize() <= 2)) {
		//if size is <= 2 we just have a head and a tail and no real info
		return true;
	}
	return false;
}//IS EMPTY ENDS HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//SETTER
public void setNext(PVRPDay t) { this.next = t; }




//SETTER
public void setPrevious(PVRPDay t) {
	this.previous = t;
}

public void setPrev(PVRPDay t){ setPrevious(t);}




//GETTER
public Attributes getAttributes() {
	return this.attributes;
}




//SETTER
public void setAttributes(PVRPAttributes attributes) {
	this.attributes = attributes;
}




//GETTER
public int getDayNumber() { return this.dayNumber; }




//SETTER
public void setDayNumber(int dayNumber) {
	this.dayNumber = dayNumber;
}




//GETTER
public float getMaxDistance() {
	return this.maxDistance;
}




//GETTER
public float getMaxDemand() {
	return this.maxDemand;
}




//GETTER
public int getNodes() { return this.nodes; }




//GETTER
public int getNumberTrucks() { return this.numberTrucks; }




//GETTER
public int getPlanningPeriod() { return this.planningPeriod; }




//METHOD
//just checks to see if main nodes (which each day has) is empty
//pretty much does the same thing as above, just with getters
//allows either to be called and to take precedence over the base methods
public boolean isEmptyMainNodes() {
	{
		if((this.getNodesLinkedList().getHead() == this.getNodesLinkedList().getTail()) ||
		   (this.getNodesLinkedList().getSize() <= 2)) {
			return true;
		}
		return false;
	}
}//IS EMPTY MAIN NODES ENDS HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//METHOD
//the gui uses this to show day information...
public String toString()
{
	String s = "#" + this.dayNumber;

	return s;
}


}




