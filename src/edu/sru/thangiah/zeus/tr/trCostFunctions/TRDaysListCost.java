package edu.sru.thangiah.zeus.tr.trCostFunctions;


import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRDay;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRDaysList;


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

public class TRDaysListCost
		extends TRAbstractCost {


//GETTER
@Override
public double getTotalCost(Object o) {
	setTotalCost(o);

	return ((TRDaysList) o).getAttributes().getTotalCost();
}




//GETTER
@Override
public float getTotalDemand(Object o) {
	setTotalDemand(o);

	return (int) ((TRDaysList) o).getAttributes().getTotalDemand();
}




//GETTER
@Override
public double getTotalDistance(Object o) {
	setTotalDistance(o);

	return ((TRDaysList) o).getAttributes().getTotalDistance();
}




//SETTER
@Override
public void setTotalCost(Object o) {
	TRDaysList daysList = (TRDaysList) o;
	TRDay theDay = ((TRDaysList) o).getHead();
	double cost = 0;

	while(theDay.getNext() != daysList.getTail()) {
		theDay = theDay.getNext();
		cost += TRProblemInfo.daysLevelCostF.getTotalCost(theDay);
	}

	daysList.getAttributes().setTotalCost(cost);
}




//SETTER
@Override
public void setTotalDemand(Object o) {
	TRDaysList daysList = (TRDaysList) o;
	TRDay theDay = daysList.getHead();
	daysList.getAttributes().getTotalDemand();

	while(theDay.getNext() != daysList.getTail()) {
		theDay = theDay.getNext();
		if(!theDay.isSubListEmpty()) {
			daysList.getAttributes().setTotalDemand(
					daysList.getAttributes().getTotalDemand() + TRProblemInfo.daysLevelCostF.getTotalDemand(theDay));
		}
	}
}




//GETTER
@Override
public void setTotalDistance(Object o) {
	TRDaysList daysList = (TRDaysList) o;
	TRDay theDay = daysList.getHead();
	daysList.getAttributes().getTotalDistance();

	while(theDay.getNext() != daysList.getTail()) {
		theDay = theDay.getNext();
		if(!theDay.isSubListEmpty()) {
			daysList.getAttributes().setTotalDistance(daysList.getAttributes().getTotalDistance() +
													  TRProblemInfo.daysLevelCostF.getTotalDistance(theDay));
		}
	}

}




//CONSTRUCTOR
@Override
public void calculateTotalsStats(Object o) {
//	setTotalDemand(o);
	setTotalDistance(o);
//	setTotalCost(o);
}
}
