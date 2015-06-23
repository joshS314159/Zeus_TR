package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Insertion;

import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.*;

/**
 * Created by library-tlc on 6/18/15.
 */
public class TRLowestDistanceInsertionExhaustive {



    private double findDistanceAtInsertionPoint(final TRNodesList nodesList, final TRNode insertAfter, final TRShipment insertMe){
        TRNode insertNode = new TRNode(insertMe);
        boolean isFeasible = false;
        nodesList.insertAfterObject(insertNode, insertAfter);

        nodesList.getFeasibility().setRoute(nodesList);
        isFeasible = nodesList.getFeasibility().isFeasible();


//        nodesList.insertAfterLastIndex(insertMe);
//        TRProblemInfo.nodesLLLevelCostF.setTotalDemand(nodesList);
//        TRProblemInfo.nodesLLLevelCostF.setTotalDistance(nodesList);



        final double distance = nodesList.getAttributes().getTotalDistance();

        nodesList.removeByObject(insertNode);
        TRProblemInfo.nodesLLLevelCostF.setTotalDemand(nodesList);
        TRProblemInfo.nodesLLLevelCostF.setTotalDistance(nodesList);

        if(!isFeasible){ return 999999999; }
        return distance;
    }


    public boolean getInsertShipment(final TRDepotsList mainDepots, final TRShipment theShipment){

//		private boolean insertAtLowestDistanceLast(final TRShipment theShipment){
        TRNode insertMe = new TRNode(theShipment);
        boolean isSolution = false;

        final int numberCombinations = theShipment.getNoComb();
        TRNode[][] bestNodes = new TRNode[numberCombinations][theShipment.getCurrentComb()[0].length];
        double[][] distances = new double[numberCombinations][theShipment.getCurrentComb()[0].length];


        for(int h = 0; h < numberCombinations; h++) {
            final int[] combination = theShipment.getCurrentComb()[h];

            for(int i = 0; i < combination.length; i++) {
                double bestDistance = 999999999;
                double currentDistance = -1;

                if(combination[i] == 1) {

                    TRDepot theDepot = mainDepots.getFirst();
                    while(theDepot != mainDepots.getTail()) {
                        TRTrucksList trucksList = theDepot.getSubList();
                        TRTruck theTruck = trucksList.getFirst();

                        while(theTruck != trucksList.getTail()) {
                            TRDaysList daysList = theTruck.getSubList();
                            TRDay theDay = daysList.getAtIndex(i);
                            TRNodesList nodesList = theDay.getSubList();

                            TRNode theNode = nodesList.getFirst();
                            while(theNode != nodesList.getTail()) {
                                currentDistance = findDistanceAtInsertionPoint(nodesList, theNode, theShipment);
//                                boolean isValidRouteFound = false;
                                if(currentDistance < bestDistance) {
//                                    isValidRouteFound = true;
                                    bestDistance = currentDistance;
                                    distances[h][i] = bestDistance;
                                    bestNodes[h][i] = theNode;
                                }
                                theNode = theNode.getNext();
                            }

                            theTruck = theTruck.getNext();
                        }

                        theDepot = theDepot.getNext();
                    }
                }
            }
        }
//
//        TRNode[] bestCombination = findBestCombination(theShipment, bestNodes, mainDepots);

//        if(isSolution)
        int bestCombinationNumber = -1;
        double bestDistance = 999999999;
        for(int i = 0; i < distances.length; i++){
            double currentDistance = 0;
            for(int j = 0; j < distances[i].length; j++){
                currentDistance += distances[i][j];
            }



            if(currentDistance < bestDistance && doCombinationsMatch(theShipment.getCurrentComb()[i], bestNodes[i])){
                bestDistance = currentDistance;
                bestCombinationNumber = i;
            }
        }

        if(bestCombinationNumber == -1){
            System.out.println("better algorithm needed");
            System.exit(1);
        }
        TRNode[] bestCombination = bestNodes[bestCombinationNumber];
        for(int i = 0; i < bestCombination.length; i++){
            if(bestCombination[i] != null){
                if(!bestCombination[i].insertAfterCurrent(new TRNode(theShipment))){
                    return false;
                }
            }
        }
//
//        TRProblemInfo.depotLLLevelCostF.setTotalDemand(mainDepots);
//        TRProblemInfo.depotLLLevelCostF.setTotalDistance(mainDepots);



//
//        for(int i = 0; i < bestNodes[0].length; i++){
//            if(bestNodes[6][i] != null){
//                if(!bestNodes[6][i].insertAfterCurrent(new TRNode(theShipment))){
//                    return false;
//                }
//
//
//            }
//        }

//                TRProblemInfo.nodesLLLevelCostF.setTotalDemand(bestNodes[i]);
//                TRProblemInfo.nodesLLLevelCostF.setTotalDistance(bestNodes[i]);


        return true;
//		}

    }

    private boolean doCombinationsMatch(final int[] theCombination, final TRNode[] assessedCombination){
        if(theCombination.length != assessedCombination.length){
            return false;
        }

        final int visitedDay = 1;
        final int nonVisitedDay = 0;

        int[] formattedAssessedCombination = new int[assessedCombination.length];
        for(int i = 0; i < assessedCombination.length; i++){
            if(assessedCombination[i] != null){
                formattedAssessedCombination[i] = visitedDay;
            }
            else{
                formattedAssessedCombination[i] = nonVisitedDay;
            }
        }


        for(int i = 0; i < formattedAssessedCombination.length; i++){
            if(formattedAssessedCombination[i] != theCombination[i]){
                return false;
            }
        }
        return true;
    }
}
