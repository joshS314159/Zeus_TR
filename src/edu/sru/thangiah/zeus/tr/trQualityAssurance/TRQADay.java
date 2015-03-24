package edu.sru.thangiah.zeus.tr.trQualityAssurance;


import edu.sru.thangiah.zeus.core.Settings;
import edu.sru.thangiah.zeus.tr.TRCoordinates;

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


public class TRQADay {

private Vector nodes       = new Vector();    //holds a vector full of nodes
private double demand      = -1;                //the demand for the day
private double distance    = -1;            //the distance for the day
private double maxDemand   = -1;            //the max demand for a day
private double maxDistance = -1;        //the max distance for a day
private int    dayNumber   = -1;                //the day number the current day is




//CONSTRUCTOR
/*public boolean checkDistanceConstraint(TRQADay day) {
	//VARIABLES
	boolean isDiagnostic = true;        //print diagnostic information?
	boolean status = true;                //return value
	double totalDistance = 0;            //
	double distance = 0;                //
	TRQANode nodeOne;                    //these are for
	TRQANode nodeTwo;                    //distance calculations
	int distanceOne;                    //
	int distanceTwo;                    //


	//IF HAVE NO NODES
	if(getNodes().size() == 0) {
		return status;
	}


	//COMPUTE DISTANCE FROM DEPOT TO FIRST NODE
	nodeOne = (TRQANode) getNodes().elementAt(0);


	//COMPUTE TOTAL TRAVEL DISTANCE IN THE ROUTE
	for(int i = 1; i < getNodes().size(); i++) {
		nodeTwo = (TRQANode) getNodes().elementAt(i);


		//PYTHAGOREAN THEROEM
		distance = (float) Math.sqrt((double) (nodeOne.getX() - nodeTwo.getX()) * (nodeOne.getX() - nodeTwo.getX()) +
									 ((nodeOne.getY() - nodeTwo.getY()) * (nodeOne.getY() - nodeTwo.getY())));
		totalDistance += distance;
		if(isDiagnostic) {
			System.out.println("    Distance from " + nodeOne.getIndex() + " to " +
							   nodeTwo.getIndex() + " = " + distance);
		}
		nodeOne = nodeTwo;
	}


	//Convert the distance to integer values for comparison. The convertion
	//takes up to a precison of 3 decimal places. Comapring floats can lead
	//to inaccurate results as errors start occuring after the 4th decimal
	//place

	//converting to ints because floats can be inaccurate
	//and depending on the situation floats have been used
	//over doubles
	//less than one mile variation in distances isn't a
	//quality problem to worry about
	distanceOne = (int) (day.getDistance());
	distanceTwo = (int) (totalDistance);

	if(isDiagnostic) {
		//System.out.println("    Distance from " + node1.getIndex() + " to " +
		//                   node2.getIndex() + " = " + truck.getDistance());
		System.out.println("   Truck distance " + day.getDistance() +
						   " : Computed distance " + totalDistance);

	}

	//DO OUR DISTANCES MATCH
	//because the int conversion chops off the decimal we will consider
	//our results to be OKAY if the distances are within one mile of
	//each other, so if distanceOne == 359 then distanceTwo can be
	//358, 359, or 360
	if(distanceOne == distanceTwo || distanceOne == distanceTwo - 1 || distanceTwo == distanceOne - 1) {
		status = true;
	}
	else {
		//	if(distanceOne != distanceTwo) {
		Settings.printDebug(Settings.ERROR, "Truck # " + day.getDayNumber() +
											" distance does not match computed distance " +
											day.getDistance() + " " + totalDistance);
		status = false;
		return status;
	}

	//DOES THIS EXCEED MAXIUMUM DISTANCE
	if(totalDistance > day.getMaxDistance()) {
		Settings.printDebug(Settings.ERROR, "Truck # " + day.getDayNumber() +
											"distance exceeds maximum distance " +
											totalDistance + " " + getMaxDistance());
		status = false;
		return status;
	}

	return status;
}//CHECK_DISTANCE_CONSTRAINTS ENDS HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
*/
public boolean checkDistanceConstraint(TRQADay day) {
	//VARIABLES
	boolean isDiagnostic = true;        //print diagnostic information?
	boolean status = true;                //return value
	double totalDistance = 0;            //
	double distance = 0;                //
	TRQANode nodeOne;                    //these are for
	TRQANode nodeTwo;                    //distance calculations
	int distanceOne;                    //
	int distanceTwo;                    //


	//IF HAVE NO NODES
	if (getNodes().size() == 0) {
		return status;
	}


	//COMPUTE DISTANCE FROM DEPOT TO FIRST NODE
	nodeOne = (TRQANode) getNodes().elementAt(0);


	//COMPUTE TOTAL TRAVEL DISTANCE IN THE ROUTE
	for (int i = 1; i < getNodes().size(); i++) {
		nodeTwo = (TRQANode) getNodes().elementAt(i);

		TRCoordinates firstPoint = new TRCoordinates(nodeOne.getY(), nodeOne.getX());
		TRCoordinates secondPoint = new TRCoordinates(nodeTwo.getY(), nodeTwo.getX());
		distance = firstPoint.calculateDistanceThisMiles(secondPoint);

		totalDistance += distance;
		if (isDiagnostic) {
			System.out.println("    Distance from " + nodeOne.getIndex() + " to " +
					nodeTwo.getIndex() + " = " + distance);
		}
		nodeOne = nodeTwo;
	}


	//Convert the distance to integer values for comparison. The convertion
	//takes up to a precison of 3 decimal places. Comapring floats can lead
	//to inaccurate results as errors start occuring after the 4th decimal
	//place

	//converting to ints because floats can be inaccurate
	//and depending on the situation floats have been used
	//over doubles
	//less than one mile variation in distances isn't a
	//quality problem to worry about
	distanceOne = (int) (day.getDistance());
	distanceTwo = (int) (totalDistance);

	if (isDiagnostic) {
		//System.out.println("    Distance from " + node1.getIndex() + " to " +
		//                   node2.getIndex() + " = " + truck.getDistance());
		System.out.println("   Truck distance " + day.getDistance() +
				" : Computed distance " + totalDistance);

	}

	//DO OUR DISTANCES MATCH
	//because the int conversion chops off the decimal we will consider
	//our results to be OKAY if the distances are within one mile of
	//each other, so if distanceOne == 359 then distanceTwo can be
	//358, 359, or 360
	if (distanceOne == distanceTwo || distanceOne == distanceTwo - 1 || distanceTwo == distanceOne - 1) {
		status = true;
	} else {
		//	if(distanceOne != distanceTwo) {
		Settings.printDebug(Settings.ERROR, "Truck # " + day.getDayNumber() +
				" distance does not match computed distance " +
				day.getDistance() + " " + totalDistance);
		status = false;
		return status;
	}

	//DOES THIS EXCEED MAXIUMUM DISTANCE
	if (totalDistance > day.getMaxDistance() && day.getMaxDistance() > 0) {
		Settings.printDebug(Settings.ERROR, "Truck # " + day.getDayNumber() +
				"distance exceeds maximum distance " +
				totalDistance + " " + getMaxDistance());
		status = false;
		return status;
	}

	return status;
}



//GETTER
public Vector getNodes() {
	return this.nodes;
}




//SETTER
public void setNodes(Vector nodes) {
	this.nodes = nodes;
}




//GETTER
public double getDistance() {
	return this.distance;
}




//SETTER
public void setDistance(double distance) {
	this.distance = distance;
}




//GETTER
public int getDayNumber() {
	return this.dayNumber;
}




//SETTER
public void setDayNumber(int dayNumber) {
	this.dayNumber = dayNumber;
}




//GETTER
public double getMaxDistance() {
	return this.maxDistance;
}




//SETTER
public void setMaxDistance(double maxDistance) {
	this.maxDistance = maxDistance;
}




//METHOD
//THIS CHECKS THE CAPACITY CONSTRAINT OF A PASSED DAY
public boolean checkCapacityConstraint(TRQADay day) {
	//VARIABLES
	boolean isDiagnostic = true;            //
	boolean status = true;                    //return boolean
	double totalCapacity = 0;                //total capacity
	double capacity = 0;                    //
	TRQANode node;                        //
	int gotCapacity;
	int computedCapacity;


	//DO WE HAVE NODES TO PROCESS
	if(getNodes().size() == 0) {
		return false;
	}


	//COMPUTE TOTAL DISTANCE OF THE ROUTE
	//...JUST USE GETTERS HERE AND SUMMATION
	for(int i = 1; i < getNodes().size() - 1; i++) {
		node = (TRQANode) getNodes().elementAt(i);
		capacity = node.getDemand();
		if(isDiagnostic) {
			System.out.println("    Node " + node.getIndex() + " demand: " +
							   +capacity);
		}
		totalCapacity += capacity;
	}


	//Convert the capacity to integer values for comparison. The convertion
	//takes up to a precison of 3 decimal places. Comapring floats can lead
	//to inaccurate results as errors start occuring after the 4th decimal
	//place
	gotCapacity = (int) (day.getDemand() * 1000);
	computedCapacity = (int) (totalCapacity * 1000);


	if(isDiagnostic) {
		System.out.println("   Truck capacity " + day.getDemand() +
						   " : Computed capacity " + totalCapacity);
	}


	//DOES TRUCK CAPACITY FROM GETTER MATCH OUR COMPUTED CAPACITY
	if(gotCapacity != computedCapacity) { //check up to 3 decimal placess
		Settings.printDebug(Settings.ERROR, "Truck # " + day.getIndex() +
											" capacity does not match computed capacity " +
											day.getDemand() + " " + totalCapacity);
		status = false;
		return status;
	}


	//DOES THE COMPUTEd CAPACITY EXCEED OUR MAXIMUM CAPACITY
	if(totalCapacity > day.getMaxDemand()) {
		Settings.printDebug(Settings.ERROR, "Truck # " + day.getDayNumber() +
											"distance exceeds maximum capacity " +
											totalCapacity + " " + day.getMaxDemand());
		status = false;
		return status;
	}
	return status;
}//CHECK_CAPACITY_CONSTRAINTS ENDS HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//GETTER
public double getDemand() {
	return this.demand;
}




//SETTER
public void setDemand(double demand) {
	this.demand = demand;
}




//GETTER
public int getIndex() {
	return getDayNumber();
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
