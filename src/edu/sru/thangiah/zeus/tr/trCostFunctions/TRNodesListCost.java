package edu.sru.thangiah.zeus.tr.trCostFunctions;


import edu.sru.thangiah.zeus.tr.TRPenaltiesList;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRNode;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRNodesList;


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


public class TRNodesListCost
		extends TRAbstractCost
		implements java.io.Serializable {


//GETTER
public double getTotalCost(Object o) {
    setTotalCost(o);

    return ((TRNodesList) o).getAttributes().getTotalCost();
}


    public void setPenaltiesList(final TRPenaltiesList penaltiesList){
        this.setPenaltiesList(penaltiesList);
    }




//GETTER
public float getTotalDemand(Object o) {
	setTotalDemand(o);

	return (int) ((TRNodesList) o).getAttributes().getTotalDemand();
}




//GETTER
public double getTotalDistance(Object o) {
	setTotalDistance(o);

	return ((TRNodesList) o).getAttributes().getTotalDistance();
}

    @Override
    public double getTotalTravelTime(Object o) {
        setTotalTravelTime(o);

        return ((TRNodesList) o).getAttributes().getTotalTravelTime();
    }


    //SETTER
public void setTotalCost(Object o) {
	TRNodesList nodesList = (TRNodesList) o;
	double cost = 0;

	if(nodesList.getHead().getNext() != nodesList.getTail()) {
		cost = nodesList.getTruckType().getFixedCost();
//        System.out.println("\n\nVARIABLE COST#$#$#$#$#$#$\t\t" + nodesList.getTruckType().getVariableCost());
		cost += (nodesList.getTruckType().getVariableCost() * getTotalDistance(o));
	}


	nodesList.getAttributes().setTotalCost(cost);
}




//SETTER
public void setTotalDemand(Object o) {
	TRNodesList nodesList = (TRNodesList) o;
	TRNode theNode = nodesList.getHead().getNext();
	int distance = 0;

	while(theNode != nodesList.getTail()) {
		distance += theNode.getShipment().getNumberOfBins();
		theNode = theNode.getNext();
	}

	nodesList.getAttributes().setTotalDemand(distance);
}




//SETTER
public void setTotalDistance(Object o) {
	TRNodesList nodesList = (TRNodesList) o;
	TRNode leftNode = nodesList.getHead();
	TRNode rightNode = nodesList.getFirst();

	if(nodesList == null) {
		System.out.println();
	}

    if(nodesList.isEmpty()){
        nodesList.getAttributes().setTotalDistance(0);
        return;
    }

    float distance = 0;

    while(rightNode != null){
        distance += leftNode.getCoordinates().calculateDistanceThisMiles(rightNode.getCoordinates());

        leftNode = rightNode;
        rightNode = rightNode.getNext();
    }

	nodesList.getAttributes().setTotalDistance(distance);
}

    @Override
    public void setTotalTravelTime(Object o) {
        TRNodesList nodesList = (TRNodesList) o;
        TRNode leftNode = nodesList.getHead();
        TRNode rightNode = nodesList.getFirst();
        TRPenaltiesList penaltiesListCopy = new TRPenaltiesList(TRProblemInfo.mainPenalties);

        int totalDelay = 0;

        if(nodesList == null) {
            System.out.println();
        }

        float distance = 0;

        while(rightNode != null){
            distance += leftNode.getCoordinates().calculateDistanceThisMiles(rightNode.getCoordinates());

            leftNode = rightNode;
            rightNode = rightNode.getNext();


            if(TRProblemInfo.addPenaltyPerBin) {
                totalDelay += (leftNode.getShipment().getDelayType().getDelayTimeInMinutes() * leftNode.getShipment().getNumberOfBins());
            }
            else{
                totalDelay += (leftNode.getShipment().getDelayType().getDelayTimeInMinutes());
            }
        }

//        nodesList.getAttributes().setTotalDistance(distance);

        int totalTravelTime = (int) (distance / (TRProblemInfo.averageVelocity / 60.0));


        int startTimeInMinutes = (TRProblemInfo.startHour * 60) + TRProblemInfo.startMinute;
        int endTimeInMinutes = startTimeInMinutes + totalTravelTime + totalDelay;

        System.out.println("Total travel time without time-range penalties and delays:\t" + (totalTravelTime) + " minutes");
        System.out.println("Total travel time without time-range penalties:\t" + (endTimeInMinutes - startTimeInMinutes) + " minutes");

        int endHourTime = endTimeInMinutes / 60;
        int endMinuteTime = endTimeInMinutes % 60;

        int totalPenalty = penaltiesListCopy.getPenaltyTimeForRange(TRProblemInfo.startHour, TRProblemInfo.startMinute, endHourTime, endMinuteTime);


        int penalty = -1;
        int startHourTime = -1;
        int startMinuteTime = -1;
        while(penalty != 0){
            startTimeInMinutes = endTimeInMinutes + 1;
            endTimeInMinutes = startTimeInMinutes + totalPenalty;

            startHourTime = startTimeInMinutes / 60;
            startMinuteTime = startTimeInMinutes % 60;

            endHourTime = endTimeInMinutes / 60;
            endMinuteTime = endTimeInMinutes % 60;


            penalty = penaltiesListCopy.getPenaltyTimeForRange(startHourTime, startMinuteTime, endHourTime, endMinuteTime);
            totalPenalty += penalty;
        }

        startHourTime = TRProblemInfo.startHour;
        startMinuteTime = TRProblemInfo.startMinute;
        startTimeInMinutes = (startHourTime * 60) + startMinuteTime;

        endTimeInMinutes = startTimeInMinutes + totalPenalty + totalDelay + totalTravelTime;

        totalTravelTime = endTimeInMinutes - startTimeInMinutes;


        System.out.println("Total travel time:\t" + totalTravelTime + " minutes");
        nodesList.getAttributes().setTotalTravelTime(totalTravelTime);



    }






    //CONSTRUCTOR
public void calculateTotalsStats(Object o) {
//	setTotalDemand(o);
	setTotalDistance(o);
	setTotalCost(o);
}
}
