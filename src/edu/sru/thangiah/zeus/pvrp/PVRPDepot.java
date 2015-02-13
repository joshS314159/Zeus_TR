package edu.sru.thangiah.zeus.pvrp;


import edu.sru.thangiah.zeus.core.Depot;

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

public class PVRPDepot
		extends Depot
		implements java.io.Serializable, java.lang.Cloneable {


//VARIABLES
private float maxDistance = -1;
private float maxDemand   = -1;

private PVRPDepot           next;
private PVRPDepot           prev;
private PVRPTruckLinkedList mainTrucks;
private PVRPAttributes      attributes;





public PVRPDepot() {
	this.setDepotNum(-1);
	this.setXCoord(-1.0D);
	this.setYCoord(-1.0D);
	//set the attributes and the truck linked list
	setAttributes(new PVRPAttributes());
	setMainTrucks(new PVRPTruckLinkedList());
}




/**
 * Constructor. Creates the depot
 *
 * @param d depot number
 * @param x x-coordinate
 * @param y y-coordinate
 */
public PVRPDepot(int d, double x, double y, float maxDistance, float maxDemand) {
	this.setDepotNum(d);
	this.setXCoord(x);
	this.setYCoord(y);

	this.maxDistance = maxDistance;
	this.maxDemand = maxDemand;

	this.attributes = new PVRPAttributes();
	this.mainTrucks = new PVRPTruckLinkedList();

}




/**
 * Returns the truck linked list
 *
 * @return main trucks
 */


public PVRPDepot getPrevious() {
	return this.prev;
}

public PVRPDepot getPrev() {
	return getPrevious();
}




public PVRPDepot getNext() {
	return this.next;
}




public void setNext(PVRPDepot d) {
	this.next = d;
}




public PVRPTruckLinkedList getMainTrucks() {
	return this.mainTrucks;
}




public void setMainTrucks(PVRPTruckLinkedList tll) {
	this.mainTrucks = tll;
}




public PVRPAttributes getAttributes() {
	return this.attributes;
}




public void setAttributes(PVRPAttributes attributes) {
	this.attributes = attributes;
}




public void setPrev(PVRPDepot d) {
	this.prev = d;
}

public void setPrevious(PVRPDepot d){setPrev(d);}




/**
 * Returns the next depot in the linked list
 *
 * @return next depot
 */


public float getMaxDistance() {
	return this.maxDistance;
}




public float getMaxDemand() {
	return this.maxDemand;
}


//METHOD
//the gui finds this useful
//prints depot info to each dropdown tab
public String toString()
{
	return
			"#" + this.getDepotNum() + " (" + this.getxCoord() + ", " + this.getyCoord() + ")" + this.attributes;
}



}
