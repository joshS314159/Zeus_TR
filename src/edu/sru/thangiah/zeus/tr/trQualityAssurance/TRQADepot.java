//TR PROBLEM
//CPSC 464
//AARON ROCKBURN; JOSHUA SARVER

//***********	DECLARATION_S_OTHER
// **********************************************************************************\\
// FUNCTION_START >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//PACKAGE TITLE
package edu.sru.thangiah.zeus.tr.trQualityAssurance;


import edu.sru.thangiah.zeus.qualityassurance.QADepot;


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
//CLASS
public class TRQADepot
		extends QADepot
		implements java.io.Serializable, Cloneable {

//VARIABLES
private double maxDistance;
private double maxDemand;




//METHOD
//CHECKS THE DISTANCE TRAVELLED AGAINST THE MAXIMUM
public boolean checkDistanceConstraint(TRQADepot depot) {
	for(int i = 0; i < getTrucks().size(); i++) {
		//			System.out.println("_____________TRUCK" + i);
		TRQATruck truck = (TRQATruck) getTrucks().elementAt(i);
		if(!truck.checkDistanceConstraint(truck)) {
			return false;
		}
	}
	return true;
}//CHECK_DISTANCE_CONSTRAINTS ENDS HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//METHOD
//CHECKS THE CAPACITY AGAINST THE MAXIMUM
public boolean checkCapacityConstraint() {
	for(int i = 0; i < getTrucks().size(); i++) {
		TRQATruck truck = (TRQATruck) getTrucks().elementAt(i);
		if(!truck.checkCapacityConstraint(truck)) {
			return false;
		}
	}
	return true;
}//CHECK_CAPACITY_CONSTRAINTS ENDS HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//GETTER
public double getMaxDistance() {
	return this.maxDistance;
}




//SETTER
public void setMaxDistance(double maxDistance) {
	this.maxDistance = maxDistance;
}




//GETTER
public double getMaxDemand() {
	return this.maxDemand;
}




//SETTER
public void setMaxDemand(double maxDemand) {
	this.maxDemand = maxDemand;
}
}
