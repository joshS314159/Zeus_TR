package edu.sru.thangiah.zeus.tr.trCostFunctions;


import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.tr.TR;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRTruck;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRTrucksList;


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


public class TRTrucksListCost
		extends TRAbstractCost
		implements java.io.Serializable {


//GETTER
public double getTotalDistance(Object o) {
	setTotalDistance(o);

	return ((TRTrucksList) o).getAttributes().getTotalDistance();
}//GETTER

    @Override
    public double getTotalTravelTime(Object o) {
        setTotalTravelTime(o);

        return ((TRTrucksList) o).getAttributes().getTotalTravelTime();
    }


    //SETTER
public void setTotalDistance(Object o) {
	TRTrucksList truckList = (TRTrucksList) o;
	TRTruck theTruck = truckList.getFirst();

	truckList.getAttributes().getTotalDistance();
//    truckList.getAttributes().setTotalDistance(0);


	while(theTruck != truckList.getTail()) {
		if(!theTruck.isSubListEmpty()) {
			truckList.getAttributes().setTotalDistance(truckList.getAttributes().getTotalDistance() + TRProblemInfo.truckLevelCostF.getTotalDistance(theTruck));
		}
		theTruck = theTruck.getNext();

	}
}//GETTERpublic double getTotalCost(Object o) {

    @Override
    public void setTotalTravelTime(Object o) {
        TRTrucksList truckList = (TRTrucksList) o;
        TRTruck theTruck = truckList.getFirst();

        truckList.getAttributes().getTotalTravelTime();
//        truckList.getAttributes().setTotalTravelTime(0);


        while (theTruck != truckList.getTail()) {
            if (!theTruck.isSubListEmpty()) {
                truckList.getAttributes().setTotalTravelTime(truckList.getAttributes().getTotalTravelTime() + TRProblemInfo.truckLevelCostF.getTotalTravelTime(theTruck));
            }
            theTruck = theTruck.getNext();
        }
    }


    //CONSTRUCTOR
public void calculateTotalsStats(Object o) {
	//	setTotalDemand(o);
	setTotalDistance(o);
		setTotalCost(o);
}


    @Override
    public double getTotalCost(Object o) {
        setTotalCost(o);

        return ((TRTrucksList) o).getAttributes().getTotalCost();
    }

    public float getTotalDemand(Object o) {
	setTotalDemand(o);

	return (int) ((TRTrucksList) o).getAttributes().getTotalDemand();
}




//GETTER
public void setTotalCost(Object o) {
	TRTrucksList truckList = (TRTrucksList) o;
	TRTruck theTruck = truckList.getFirst();

	truckList.getAttributes().setTotalCost(0);

	while(theTruck != truckList.getTail()) {
		if(!theTruck.isSubListEmpty()) {
			truckList.getAttributes()
					 .setTotalCost(truckList.getAttributes().getTotalCost() + TRProblemInfo.truckLevelCostF.
																												 getTotalCost(
																														 theTruck));
		}

		theTruck = theTruck.getNext();
	}
}




//SETTER
public void setTotalDemand(Object o) {
	TRTrucksList truckList = (TRTrucksList) o;
	TRTruck theTruck = truckList.getFirst();

	truckList.getAttributes().getTotalDemand();

	while(theTruck != truckList.getTail()) {

		if(!theTruck.isSubListEmpty()) {
			truckList.getAttributes()
					 .setTotalDemand(truckList.getAttributes().getTotalDemand() + TRProblemInfo.truckLevelCostF.
																													 getTotalDemand(
																															 theTruck));
		}
		theTruck = theTruck.getNext();

	}

}


}
