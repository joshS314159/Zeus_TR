package edu.sru.thangiah.zeus.pvrp.pvrpcostfunctions;


import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.pvrp.PVRPTruck;
import edu.sru.thangiah.zeus.pvrp.PVRPTruckLinkedList;


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


public class PVRPTruckLLCostFunctions
		extends PVRPAbstractCostFunctions
		implements java.io.Serializable {


//GETTER
public double getTotalCost(Object o) {
	setTotalCost(o);

	return ((PVRPTruckLinkedList) o).getAttributes().getTotalCost();
}




//GETTER
public float getTotalDemand(Object o) {
	setTotalDemand(o);

	return (int) ((PVRPTruckLinkedList) o).getAttributes().getTotalDemand();
}




//GETTER
public double getTotalDistance(Object o) {
	setTotalDistance(o);

	return ((PVRPTruckLinkedList) o).getAttributes().getTotalDistance();
}




//GETTER
public void setTotalCost(Object o) {
	PVRPTruckLinkedList truckList = (PVRPTruckLinkedList) o;
	PVRPTruck theTruck = truckList.getHead();

	truckList.getAttributes().setTotalCost(0);

	while(theTruck != truckList.getTail()) {
		if(!theTruck.isEmptyMainDays()) {
			truckList.getAttributes()
					 .setTotalCost(truckList.getAttributes().getTotalCost() + ProblemInfo.truckLevelCostF.
																												 getTotalCost(
																														 theTruck));
		}

		theTruck = theTruck.getNext();
	}
}




//SETTER
public void setTotalDemand(Object o) {
	PVRPTruckLinkedList truckList = (PVRPTruckLinkedList) o;
	PVRPTruck theTruck = truckList.getHead();

	truckList.getAttributes().getTotalDemand();

	while(theTruck.getNext() != truckList.getTail()) {
		theTruck = theTruck.getNext();
		if(!theTruck.isEmptyMainDays()) {
			truckList.getAttributes()
					 .setTotalDemand(truckList.getAttributes().getTotalDemand() + ProblemInfo.truckLevelCostF.
																													 getTotalDemand(
																															 theTruck));
		}

	}

}




//SETTER
public void setTotalDistance(Object o) {
	PVRPTruckLinkedList truckList = (PVRPTruckLinkedList) o;
	PVRPTruck theTruck = truckList.getHead();

	truckList.getAttributes().getTotalDistance();

	while(theTruck.getNext() != truckList.getTail()) {
		theTruck = theTruck.getNext();
		if(!theTruck.isEmptyMainDays()) {
			truckList.getAttributes()
					 .setTotalDistance(truckList.getAttributes().getTotalDistance() + ProblemInfo.truckLevelCostF.
																														 getTotalDistance(
																																 theTruck));
		}

	}
}




//CONSTRUCTOR
public void calculateTotalsStats(Object o) {
	setTotalDemand(o);
	setTotalDistance(o);
	setTotalCost(o);
}
}
