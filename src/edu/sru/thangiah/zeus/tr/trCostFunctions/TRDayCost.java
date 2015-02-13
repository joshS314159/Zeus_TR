package edu.sru.thangiah.zeus.tr.trCostFunctions;


import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRDay;


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

public class TRDayCost
		extends TRAbstractCost {

//GETTER
@Override
public double getTotalDistance(Object o) {
	setTotalDistance(o);

	return ((TRDay) o).getAttributes().getTotalDistance();
}//GETTER




//SETTER
@Override
public void setTotalDistance(Object o) {
	TRDay day = (TRDay) o;
	day.getAttributes().setTotalDistance((float) TRProblemInfo.nodesLLLevelCostF.
																					  getTotalDistance(
																							  day.getSubList()));
}//GETTER@Override




//CONSTRUCTOR
@Override
public void calculateTotalsStats(Object o) {
	//	setTotalDemand(o);
	setTotalDistance(o);
	//	setTotalCost(o);
}




public double getTotalCost(Object o) {
	setTotalCost(o);

	return ((TRDay) o).getAttributes().getTotalCost();
}




@Override
public float getTotalDemand(Object o) {
	setTotalDemand(o);

	return (int) ((TRDay) o).getAttributes().getTotalDemand();
}









//SETTER
@Override
public void setTotalCost(Object o) {
	TRDay day = (TRDay) o;
	day.getAttributes().setTotalCost(ProblemInfo.nodesLLLevelCostF.getTotalCost(day.getNodesLinkedList()));
}




//SETTER
@Override
public void setTotalDemand(Object o) {
	TRDay day = (TRDay) o;
	day.getAttributes().setTotalDemand((int) TRProblemInfo.nodesLLLevelCostF.getTotalDemand(day.getSubList()));


}


}
