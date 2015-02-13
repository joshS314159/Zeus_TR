package edu.sru.thangiah.zeus.pvrp;

//import the parent class


import edu.sru.thangiah.zeus.core.Feasibility;


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


public class PVRPFeasibility
		extends Feasibility
		implements java.io.Serializable, java.lang.Cloneable {


//VARIABLES
private PVRPNodesLinkedList thisRoute;




//CONSTRUCTOR
public PVRPFeasibility() {
}


//CONSTRUCTOR
//ORIGINAL NOTE FROM Dr. Sam's VRP




/**
 * Constructor for the feasibilty, will send the constants as well as a
 * pointer to the route that will be checked
 *
 * @param maxd      max duration
 * @param maxq      max capacity
 * @param thisRoute the route
 */
public PVRPFeasibility(double maxd, float maxq, PVRPNodesLinkedList thisRoute) {
	maxDuration = maxd;
	maxCapacity = maxq;
	this.thisRoute = thisRoute;
}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//METHOD
//ORIGINAL NOTE




/**
 * This function checks the feasibility of the route.
 *
 * @return true if feasible, false if not.
 */
public boolean isFeasible() {
	double currentDistance;
	double currentDemand;

	if(thisRoute == null) {
		//if our root is empty
		System.exit(1);
		//osterich algorithm
	}

	currentDistance = PVRPProblemInfo.nodesLLLevelCostF.getTotalDistance(thisRoute);
	currentDemand = PVRPProblemInfo.nodesLLLevelCostF.getTotalDemand(thisRoute);


	if((currentDistance <= maxDuration) && (currentDemand <= maxCapacity)) {
		return true;
		//if our current distance and demand are NOT within the restraints
	}
	else {
		//else, our trucks are good
		return false;
	}
}//END IS_FEASIBLE *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//METHOD
//sets the route to
public void setRoute(PVRPNodesLinkedList nodes) {
	this.thisRoute = nodes;
}


}
