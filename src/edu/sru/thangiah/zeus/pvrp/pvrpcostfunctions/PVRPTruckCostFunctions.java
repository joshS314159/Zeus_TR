package edu.sru.thangiah.zeus.pvrp.pvrpcostfunctions;


import edu.sru.thangiah.zeus.pvrp.PVRPProblemInfo;
import edu.sru.thangiah.zeus.pvrp.PVRPTruck;


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


public class PVRPTruckCostFunctions
		extends PVRPAbstractCostFunctions
		implements java.io.Serializable {


//GETTER
public double getTotalCost(Object o) {
	setTotalCost(o);

	return ((PVRPTruck) o).getAttributes().getTotalCost();
}




//GETTER
public float getTotalDemand(Object o) {
	setTotalDemand(o);

	return (int) ((PVRPTruck) o).getAttributes().getTotalDemand();
}




//GETTER
public double getTotalDistance(Object o) {
	setTotalDistance(o);

	return ((PVRPTruck) o).getAttributes().getTotalDistance();
}




//SETTER
public void setTotalCost(Object o) {
	PVRPTruck truck = (PVRPTruck) o;
	truck.getAttributes().setTotalCost(PVRPProblemInfo.daysLLLevelCostF.getTotalCost(truck.getMainDays()));
}




//SETTER
public void setTotalDemand(Object o) {
	PVRPTruck truck = (PVRPTruck) o;
	truck.getAttributes().setTotalDemand(PVRPProblemInfo.daysLLLevelCostF.getTotalDemand(truck.getMainDays()));
}




//SETTER
public void setTotalDistance(Object o) {
	PVRPTruck truck = (PVRPTruck) o;
	truck.getAttributes().setTotalDistance(PVRPProblemInfo.daysLLLevelCostF.getTotalDistance(truck.getMainDays()));
}




//CONSTRUCTOR
public void calculateTotalsStats(Object o) {
	setTotalDemand(o);
	setTotalDistance(o);
	setTotalCost(o);
}
}
