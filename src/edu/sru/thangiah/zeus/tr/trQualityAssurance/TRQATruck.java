//TR PROBLEM
//CPSC 464
//AARON ROCKBURN; JOSHUA SARVER


//PACKAGE TITLE
package edu.sru.thangiah.zeus.tr.trQualityAssurance;


import edu.sru.thangiah.zeus.qualityassurance.QATruck;

import java.util.Vector;


/**
 * TR (Periodic Vehicle Routing)
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


public class TRQATruck
		extends QATruck
		implements java.io.Serializable, Cloneable {

//VARIABLES
private Vector days = new Vector();




//METHOD
//checks the current distance travelled against the maximum
public boolean checkDistanceConstraint(TRQATruck truck) {
	System.out.println("\n<<<TRUCK>>>\t" + truck.getIndex() + " has " +
					   getDays().size() + " days");

	for(int i = 0; i < getDays().size(); i++) {
		TRQADay day = (TRQADay) getDays().elementAt(i);
		System.out.println("|-- DAY " + day.getDayNumber());
		if(!day.checkDistanceConstraint(day)) {
			return false;
		}
	}

	return true;
}//CHECK_CAPACITY_CONSTRAINT ENDS HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//GETTER
public Vector getDays() {
	return this.days;
}




//SETTER
public void setDays(Vector days) {
	this.days = days;
}




//METHOD
//checks the current capacity against the maximum
public boolean checkCapacityConstraint(TRQATruck truck) {
	System.out.println("\n<<<TRUCK>>>\t" + truck.getIndex() + " has " +
					   getDays().size() + " days");

	for(int i = 0; i < getDays().size(); i++) {

		TRQADay day = (TRQADay) getDays().elementAt(i);
		System.out.println("Evaluating Day\t" + day.getDayNumber());
		if(!day.checkCapacityConstraint(day)) {
			return false;
		}
	}
	return true;
}//CHECK_CAPACITY_CONSTRAINT ENDS HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


}
