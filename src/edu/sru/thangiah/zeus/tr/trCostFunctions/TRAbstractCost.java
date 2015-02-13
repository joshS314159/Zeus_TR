package edu.sru.thangiah.zeus.tr.trCostFunctions;


import edu.sru.thangiah.zeus.core.CostFunctions;


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


abstract public class TRAbstractCost
		implements CostFunctions {

/**
 * Methods not used by TR in the computation of cost can be declared
 * here with dummy methods. Then have the cost functions in the TR
 * inherit this class instead of the CostFunctions class.
 */


public double getTotalTravelTime(Object o) {

	return -1;
}




public double getMaxTravelTime(Object o) {

	return -1;
}


//SETTER
public void setTotalDemand(Object o) {

}


//GETTER
public float getTotalDemand(Object o) {
	return -1;
}


public double getAvgTravelTime(Object o) {
	return -1;
}




public float getTotalScore(Object o) {
	return -1;
}




public void setTotalTravelTime(Object o) {

}




public void setMaxTravelTime(Object o) {
}




public void setAvgTravelTime(Object o) {
}




public void setTotalScore(Object o) {
}


//GETTER
public void setTotalCost(Object o) {}


public double getTotalCost(Object o) { return -1; }


}
