package edu.sru.thangiah.zeus.pvrp.pvrpcostfunctions;


import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.pvrp.PVRPDay;
import edu.sru.thangiah.zeus.pvrp.PVRPProblemInfo;


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

public class PVRPDaysCostFunctions
		extends PVRPAbstractCostFunctions {

//GETTER
@Override
public double getTotalCost(Object o) {
	setTotalCost(o);

	return ((PVRPDay) o).getAttributes().getTotalCost();
}




//GETTER
@Override
public float getTotalDemand(Object o) {
	setTotalDemand(o);

	return (int) ((PVRPDay) o).getAttributes().getTotalDemand();
}




//GETTER
@Override
public double getTotalDistance(Object o) {
	setTotalDistance(o);

	return ((PVRPDay) o).getAttributes().getTotalDistance();
}




//SETTER
@Override
public void setTotalCost(Object o) {
	PVRPDay day = (PVRPDay) o;
	day.getAttributes().setTotalCost(ProblemInfo.nodesLLLevelCostF.getTotalCost(day.getNodesLinkedList()));
}




//SETTER
@Override
public void setTotalDemand(Object o) {
	PVRPDay day = (PVRPDay) o;
	day.getAttributes()
	   .setTotalDemand((int) PVRPProblemInfo.nodesLLLevelCostF.getTotalDemand(day.getNodesLinkedList()));


}




//SETTER
@Override
public void setTotalDistance(Object o) {
	PVRPDay day = (PVRPDay) o;
	day.getAttributes().setTotalDistance((float) ProblemInfo.nodesLLLevelCostF.
																					  getTotalDistance(
																							  day.getNodesLinkedList
																									  ()));
}




//CONSTRUCTOR
@Override
public void calculateTotalsStats(Object o) {
	setTotalDemand(o);
	setTotalDistance(o);
	setTotalCost(o);
}
}
