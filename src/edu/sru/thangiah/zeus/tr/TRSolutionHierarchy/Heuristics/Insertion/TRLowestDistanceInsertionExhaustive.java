package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Insertion;

import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.*;

/**
 * Created by library-tlc on 6/18/15.
 */
public class TRLowestDistanceInsertionExhaustive {



    private double findDistanceAtInsertionPoint(final TRNodesList nodesList, final TRNode insertAfter, final TRShipment insertMe){
        TRNode insertNode = new TRNode(insertMe);
        nodesList.insertAfterObject(insertNode, insertAfter);
//        nodesList.insertAfterLastIndex(insertMe);
        TRProblemInfo.nodesLLLevelCostF.setTotalDemand(nodesList);
        TRProblemInfo.nodesLLLevelCostF.setTotalDistance(nodesList);

        final double distance = nodesList.getAttributes().getTotalDistance();

        nodesList.removeByObject(insertNode);
        TRProblemInfo.nodesLLLevelCostF.setTotalDemand(nodesList);
        TRProblemInfo.nodesLLLevelCostF.setTotalDistance(nodesList);

        return distance;
    }

    private TRNode[] findBestCombination(final TRShipment theShipment, final TRNode[][] bestNodes, final
                                              TRDepotsList mainDepots){
//        TRNode[][] sortedBest = bestNodes;
        final TRNode insertMe = new TRNode(theShipment);

        double bestDistance = 999999999;
        TRNode[] bestCombination = null;
        double currentDistance = 999999999;


        for(int i = 0; i < bestNodes.length; i++){
            boolean isSuccessful = true;
            for(int j = 0; j < bestNodes[i].length; j++){
                if(bestNodes[i][j] != null) {
                    if(!bestNodes[i][j].insertAfterCurrent(new TRNode(theShipment))){
                        isSuccessful = false;
                    }
                }
            }

            if(isSuccessful) {
                TRProblemInfo.depotLLLevelCostF.setTotalDemand(mainDepots);
                TRProblemInfo.depotLLLevelCostF.setTotalDistance(mainDepots);


//                currentDistance = mainDepots.getAttributes().getTotalDistance();
                currentDistance =  TRProblemInfo.depotLLLevelCostF.getTotalDistance(mainDepots);
                if(currentDistance < bestDistance) {
                    bestDistance = currentDistance;
                    bestCombination = bestNodes[i];
                }
            }
//            final double distance = mainDepots.getAttributes().getTotalDistance();
//            final double demand = mainDepots.getAttributes().getTotalDemand();

            for(int j = 0; j < bestNodes[i].length; j++){
                if(bestNodes[i][j] != null){
                    if(bestNodes[i][j].getNext().getShipment() == theShipment) {
                        bestNodes[i][j].getNext().removeThisObject();
                    }
                    else{
                        int a = 5;
                    }
                }
            }

            TRProblemInfo.depotLLLevelCostF.setTotalDemand(mainDepots);
            TRProblemInfo.depotLLLevelCostF.setTotalDistance(mainDepots);
        }

        return bestCombination;
    }



    public boolean getInsertShipment(final TRDepotsList mainDepots, final TRShipment theShipment){

//		private boolean insertAtLowestDistanceLast(final TRShipment theShipment){
        TRNode insertMe = new TRNode(theShipment);

        final int numberCombinations = theShipment.getNoComb();
        TRNode[][] bestNodes = new TRNode[numberCombinations][theShipment.getCurrentComb()[0].length];


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

                                if(currentDistance < bestDistance) {
                                    bestDistance = currentDistance;
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
//
//        for(int i = 0; i < bestCombination.length; i++){
//            if(bestCombination[i] != null){
//                if(!bestCombination[i].insertAfterCurrent(new TRNode(theShipment))){
//                    return false;
//                }
//            }
//
//        }
//
//        TRProblemInfo.depotLLLevelCostF.setTotalDemand(mainDepots);
//        TRProblemInfo.depotLLLevelCostF.setTotalDistance(mainDepots);



//
        for(int i = 0; i < bestNodes[0].length; i++){
            if(bestNodes[6][i] != null){
                if(!bestNodes[6][i].insertAfterCurrent(new TRNode(theShipment))){
                    return false;
                }


            }
        }

//                TRProblemInfo.nodesLLLevelCostF.setTotalDemand(bestNodes[i]);
//                TRProblemInfo.nodesLLLevelCostF.setTotalDistance(bestNodes[i]);


        return true;
//		}

    }
}
