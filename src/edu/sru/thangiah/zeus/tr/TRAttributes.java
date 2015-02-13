package edu.sru.thangiah.zeus.tr;

//import the parent class


import edu.sru.thangiah.zeus.core.Attributes;


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


public class TRAttributes
		extends Attributes
		implements java.io.Serializable, Cloneable {

public TRAttributes(final TRAttributes copyMe) {
	setTotalDemand(copyMe.getTotalDemand());
	setTotalDistance(copyMe.getTotalDistance());
	setTotalCost(copyMe.getTotalCost());
	setTotalTravelTime(copyMe.getTotalTravelTime());
	setMaxTravelTime(copyMe.getMaxTravelTime());
	setAvgTravelTime(copyMe.getAvgTravelTime());
	//		private double totalDemand = 0.0D;
	//		private double totalDistance = 0.0D;
	//		private double totalCost = 0.0D;
	//		private double totalTravelTime = 0.0D;
	//		private double maxTravelTime = 0.0D;
	//		private double avgTravelTime = 0.0D;
}




public TRAttributes() {
}


}
