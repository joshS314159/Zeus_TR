//PVRP PROBLEM
//CPSC 464
//AARON ROCKBURN; JOSHUA SARVER

//***********	DECLARATION_S_OTHER
// **********************************************************************************\\
// FUNCTION_START >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//PACKAGE TITLE
package edu.sru.thangiah.zeus.pvrp.pvrpqualityassurance;


import edu.sru.thangiah.zeus.qualityassurance.QADepotLinkedList;


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


//CLASS
public class PVRPQADepotLinkedList
		extends QADepotLinkedList
		implements java.io.Serializable, java.lang.Cloneable {


//METHOD
//CHECKS THE DISTANCE TRAVELLED AGAINST THE MAXIMUM
public boolean checkDistanceConstraint() {
	for(int i = 0; i < getDepots().size(); i++) {
		System.out.println(">>>>>>>>>>>>>>>>>> DEPOT " + i);
		PVRPQADepot depot = (PVRPQADepot) getDepots().elementAt(i);
		if(!depot.checkDistanceConstraint(depot)) {
			return false;
		}
	}
	return true;
}//CHECK_DISTANCE_CONSTRAINTS ENDS HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//METHOD
//CHECK CURRENT CAPACITY AGAINST MAX CAPACITY
public boolean checkCapacityConstraint() {
	for(int i = 0; i < getDepots().size(); i++) {
		PVRPQADepot depot = (PVRPQADepot) getDepots().elementAt(i);
		if(!depot.checkCapacityConstraint()) {
			return false;
		}
	}
	return true;
}//CHECK_CAPACITY_CONSTRAINTS ENDS HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
}
