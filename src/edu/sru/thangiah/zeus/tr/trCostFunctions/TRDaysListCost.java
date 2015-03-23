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
public double getTotalDistance(Object o) {
	setTotalDistance(o);

	return ((TRDaysList) o).getAttributes().getTotalDistance();
}//GETTER

    @Override
    public double getTotalTravelTime(Object o) {
        setTotalTravelTime(o);

        return ((TRDaysList) o).getAttributes().getTotalTravelTime();
    }


    //GETTER
@Override
public void setTotalDistance(Object o) {
	TRDaysList daysList = (TRDaysList) o;
	TRDay theDay = daysList.getFirst();
	daysList.getAttributes().getTotalDistance();
//    daysList.getAttributes().setTotalDistance(0);

	while(theDay != daysList.getTail()) {
		if(!theDay.isSubListEmpty()) {
			daysList.getAttributes().setTotalDistance(daysList.getAttributes().getTotalDistance() + TRProblemInfo.daysLevelCostF.getTotalDistance(theDay));
		}
		theDay = theDay.getNext();
	}

}//GETTER@Override

    @Override
    public void setTotalTravelTime(Object o) {
        TRDaysList daysList = (TRDaysList) o;
        TRDay theDay = daysList.getFirst();
        daysList.getAttributes().getTotalTravelTime();
//    daysList.getAttributes().setTotalDistance(0);

        while(theDay != daysList.getTail()) {
            if(!theDay.isSubListEmpty()) {
                daysList.getAttributes().setTotalTravelTime(daysList.getAttributes().getTotalTravelTime() + TRProblemInfo.daysLevelCostF.getTotalTravelTime(theDay));
            }
            theDay = theDay.getNext();
        }
    }


    public double getTotalCost(Object o) {
    setTotalCost(o);

    return ((TRDaysList) o).getAttributes().getTotalCost();
}




//CONSTRUCTOR
@Override
public void calculateTotalsStats(Object o) {
	//	setTotalDemand(o);
	setTotalDistance(o);
		setTotalCost(o);
}




@Override
public float getTotalDemand(Object o) {
	setTotalDemand(o);

	return (int) ((TRDaysList) o).getAttributes().getTotalDemand();
}









//SETTER
@Override
public void setTotalCost(Object o) {
	TRDaysList daysList = (TRDaysList) o;
	TRDay theDay = ((TRDaysList) o).getFirst();
	double cost = 0;

	while(theDay != daysList.getTail()) {

		cost += TRProblemInfo.daysLevelCostF.getTotalCost(theDay);
        theDay = theDay.getNext();
	}

	daysList.getAttributes().setTotalCost(cost);
}




//SETTER
@Override
public void setTotalDemand(Object o) {
	TRDaysList daysList = (TRDaysList) o;
	TRDay theDay = daysList.getFirst();
	daysList.getAttributes().getTotalDemand();

	while(theDay != daysList.getTail()) {

		if(!theDay.isSubListEmpty()) {
			daysList.getAttributes().setTotalDemand(
					daysList.getAttributes().getTotalDemand() + TRProblemInfo.daysLevelCostF.getTotalDemand(theDay));
		}
		theDay = theDay.getNext();
	}
}


}
