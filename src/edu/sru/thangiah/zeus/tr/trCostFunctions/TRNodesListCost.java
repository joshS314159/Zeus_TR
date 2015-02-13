package edu.sru.thangiah.zeus.tr.trCostFunctions;


import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRNode;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRNodesList;


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


public class TRNodesListCost
		extends TRAbstractCost
		implements java.io.Serializable {


//GETTER
public double getTotalCost(Object o) {
	setTotalCost(o);

	return ((TRNodesList) o).getAttributes().getTotalCost();
}




//GETTER
public float getTotalDemand(Object o) {
	setTotalDemand(o);

	return (int) ((TRNodesList) o).getAttributes().getTotalDemand();
}




//GETTER
public double getTotalDistance(Object o) {
	setTotalDistance(o);

	return ((TRNodesList) o).getAttributes().getTotalDistance();
}




//SETTER
public void setTotalCost(Object o) {
	TRNodesList nodesList = (TRNodesList) o;
	double cost = 0;

	if(nodesList.getHead().getNext() != nodesList.getTail()) {
		cost = nodesList.getTruckType().getFixedCost();
		cost += (nodesList.getTruckType().getVariableCost() * getTotalDistance(o));
	}


	nodesList.getAttributes().setTotalCost(cost);
}




//SETTER
public void setTotalDemand(Object o) {
	TRNodesList nodesList = (TRNodesList) o;
	TRNode theNode = nodesList.getHead().getNext();
	int distance = 0;

	while(theNode != nodesList.getTail()) {
		distance += theNode.getDemand();
		theNode = theNode.getNext();
	}

	nodesList.getAttributes().setTotalDemand(distance);
}




//SETTER
public void setTotalDistance(Object o) {
	TRNodesList nodesList = (TRNodesList) o;
	TRNode leftNode = null;
	TRNode rightNode = nodesList.getHead();

	if(nodesList == null) {
		System.out.println();
	}

	float distance = 0;
	do {
		leftNode = rightNode;
		rightNode = rightNode.getNext();

		distance += (float) leftNode.getCoordinates().calculateDistanceThisMiles(rightNode.getCoordinates());


	} while(rightNode != nodesList.getTail() && rightNode != null);

	nodesList.getAttributes().setTotalDistance(distance);
}




//CONSTRUCTOR
public void calculateTotalsStats(Object o) {
//	setTotalDemand(o);
	setTotalDistance(o);
//	setTotalCost(o);
}
}
