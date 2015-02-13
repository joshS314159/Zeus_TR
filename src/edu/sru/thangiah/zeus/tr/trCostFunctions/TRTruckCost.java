package edu.sru.thangiah.zeus.tr.trCostFunctions;


import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRTruck;


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


public class TRTruckCost
		extends TRAbstractCost
		implements java.io.Serializable {


//GETTER
public double getTotalDistance(Object o) {
	setTotalDistance(o);

	return ((TRTruck) o).getAttributes().getTotalDistance();
}//GETTER




//SETTER
public void setTotalDistance(Object o) {
	TRTruck truck = (TRTruck) o;
	truck.getAttributes().setTotalDistance(TRProblemInfo.daysLLLevelCostF.getTotalDistance(truck.getSubList()));
}//GETTERpublic double getTotalCost(Object o) {
	setTotalCost(o);

	return ((TRTruck) o).getAttributes().getTotalCost();
}




//CONSTRUCTOR
public void calculateTotalsStats(Object o) {
	//	setTotalDemand(o);
	setTotalDistance(o);
	//	setTotalCost(o);
}




public float getTotalDemand(Object o) {
	setTotalDemand(o);

	return (int) ((TRTruck) o).getAttributes().getTotalDemand();
}









//SETTER
public void setTotalCost(Object o) {
	TRTruck truck = (TRTruck) o;
	truck.getAttributes().setTotalCost(TRProblemInfo.daysLLLevelCostF.getTotalCost(truck.getSubList()));
}




//SETTER
public void setTotalDemand(Object o) {
	TRTruck truck = (TRTruck) o;
	truck.getAttributes().setTotalDemand(TRProblemInfo.daysLLLevelCostF.getTotalDemand(truck.getMainDays()));
}


}
