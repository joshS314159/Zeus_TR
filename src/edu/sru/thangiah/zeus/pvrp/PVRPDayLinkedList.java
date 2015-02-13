package edu.sru.thangiah.zeus.pvrp;


import edu.sru.thangiah.zeus.core.DaysLinkedList;


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


public class PVRPDayLinkedList
		extends DaysLinkedList
		implements java.io.Serializable, java.lang.Cloneable {

//VARIABLES
private PVRPAttributes attributes = null;    //takes precedence over the base class attributes type

private PVRPDay head = null;    //takes precedence over the base class head and tail
private PVRPDay tail = null;




//CONSTRUCTOR
public PVRPDayLinkedList() {
	this.head = new PVRPDay();    //set head
	this.tail = new PVRPDay();    //set tail
	this.head.setNext(this.tail);    //set the next and
	this.tail.setPrevious(this.head);    //previous node
	//to head and tail since there is no other data yet

	this.attributes = new PVRPAttributes();
}//END CONSTRUCTOR*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//METHOD STUB
//this method should insert a PVRPDay
//but is not allowed to be called from the object
//and thus print and error
public PVRPDay insertDay(PVRPDay insertNode) {
	System.out.println("ERROR: InsertDay was called in from DayLinkedList");
	return null;
}//END INSERT_DAY *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//METHOD
//our favorite insert method
//this inserts a day at the end of the linked list
//right before the tail
public boolean insertLastDay(PVRPDay day) {
	//find the tail
	if(this.head.getNext() == this.tail) {
		//either the list only has a head or tail
		//and sets next and prev to it
		this.head.setNext(day);
		this.tail.setPrevious(day);
		day.setPrevious(this.head);
		day.setNext(this.tail);
	}
	else {
		//or our list isn't empty

		day.setNext(this.tail);
		//set the next day of the passed day to the tail of
		//the linked list

		day.setPrevious(this.tail.getPrev());
		//and set the prev to node to the day before
		//the tail

		this.tail.getPrev().setNext(day);
		this.tail.setPrevious(day);
		//then set up the tail appropriately in the same fashion
	}
	return true;


}//END INSERT_LAST_DAY *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<





//GETTER
public PVRPDay getHead() { return this.head; }




//GETTER
public PVRPDay getTail() { return this.tail; }










//METHOD
//links the head and the tail together
public void linkHeadTail() {
	this.head.setNext(this.tail);
	this.tail.setPrevious(this.head);
	this.head.setPrevious(null);    //nothing comes before the head
	this.tail.setNext(null);        //nothing comes after the tail
}//END LINK_HEAD_TAIL *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//SETTER
public void setTail(PVRPDay tail) { this.tail = tail; }




//SETTER
public void setHead(PVRPDay head) { this.head = head; }




//GETTER
public PVRPAttributes getAttributes() { return this.attributes; }




//SETTER
public void setAttributes(PVRPAttributes attributes) { this.attributes = attributes; }


public PVRPDay getDayAtPosition(int position){
	PVRPDay theDay = this.getHead();
	int count = -1;

	if(position >= 0 && position < this.getSize()) {
		while (count != position) {
			theDay = theDay.getNext();
			count++;
			if (theDay == this.getTail()) {
				return null;
			}
		}
	}
	else{
		return null;
	}
	return theDay;
}

public int getSize(){
	PVRPDay theDay = this.getHead();
	int sizeCounter = 0;

	if(!isEmpty()) {
		while (theDay.getNext() != this.getTail()) {
			theDay = theDay.getNext();
			sizeCounter++;
		}
		return sizeCounter;
	}
	return 0;

}

public PVRPDay getFirstDay(){
	if(!isEmpty()){
		return this.getHead().getNext();
	}
	return null;

}

public PVRPDay getLastDay(){
	if(!isEmpty()) {
		return this.getTail().getPrev();
	}
	return null;
}

public boolean isEmpty(){
	if(this.getHead().getNext() == this.getTail() || this.getHead().getNext() == null){
		return true;
	}
	return false;
}

}
