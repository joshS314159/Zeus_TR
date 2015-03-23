package edu.sru.thangiah.zeus.tr.trCostFunctions;


import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRDepot;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRDepotsList;


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


public class TRDepotCost
		extends TRAbstractCost
		implements java.io.Serializable {


//GETTER
public double getTotalDistance(Object o) {
	setTotalDistance(o);

	return ((TRDepot) o).getAttributes().getTotalDistance();
}//GETTER

    @Override
    public double getTotalTravelTime(Object o) {
        setTotalTravelTime(o);

        return ((TRDepot) o).getAttributes().getTotalTravelTime();
    }


    //SETTER
public void setTotalDistance(Object o) {
	TRDepot theDepot = (TRDepot) o;

    float totalDistance = (float) TRProblemInfo.truckLLLevelCostF.getTotalDistance(theDepot.getSubList());
    System.out.println("Total Distance:\t" + totalDistance);
    TRAttributes depotAttributes = theDepot.getAttributes();
    depotAttributes.setTotalDistance(totalDistance);
//    theDepot.getAttributes().setTotalDistance(totalDistance);

//	theDepot.getAttributes().setTotalDistance((float) TRProblemInfo.truckLLLevelCostF.getTotalDistance(theDepot.getSubList()));
}

    @Override
    public void setTotalTravelTime(Object o) {
        TRDepot theDepot = (TRDepot) o;

        float totalTravelTime = (float) TRProblemInfo.truckLLLevelCostF.getTotalTravelTime(theDepot.getSubList());
        System.out.println("\n\nTotal travel time for week:\t" + totalTravelTime + " minutes");
        TRAttributes depotAttributes = theDepot.getAttributes();
        depotAttributes.setTotalTravelTime(totalTravelTime);

    }

//GETTERpublic double getTotalCost(Object o) {
//	setTotalCost(o);

//	return ((TRDepot) o).getAttributes().getTotalCost();
//}
//




//CONSTRUCTOR
public void calculateTotalsStats(Object o) {
	//	setTotalDemand(o);
	setTotalDistance(o);
		setTotalCost(o);
}


    @Override
    public double getTotalCost(Object o) {
        setTotalCost(o);

        return ((TRDepot) o).getAttributes().getTotalCost();
    }

    public float getTotalDemand(Object o) {
	setTotalDemand(o);

	return (int) ((TRDepot) o).getAttributes().getTotalDemand();
}




//SETTER
public void setTotalCost(Object o) {
	TRDepot theDepot = (TRDepot) o;
	theDepot.getAttributes().setTotalCost(TRProblemInfo.truckLLLevelCostF.getTotalCost(theDepot.getSubList()));

    System.out.println("Total COST:\t" + theDepot.getAttributes().getTotalCost());
}




//SETTER
public void setTotalDemand(Object o) {
	TRDepot theDepot = (TRDepot) o;
	((TRDepot) o).getAttributes()
				 .setTotalDemand((int) ProblemInfo.truckLLLevelCostF.getTotalDemand(theDepot.getMainTrucks()));
}


}
