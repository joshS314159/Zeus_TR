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


//    private double cycleThroughInsertionPoints(final TRNodesList nodesList, TRNode bestNode, final TRNode insertMe){
//        double bestDistance = 999999999;
//        double currentDistance = -1;
////        TRNode bestNode = null;
//
//        TRNode theNode = nodesList.getFirst();
//        while(theNode != nodesList.getTail()){
//            currentDistance = findDistanceAtInsertionPoint(nodesList, theNode, insertMe);
//            if(currentDistance < bestDistance){
//                bestDistance = currentDistance;
//                bestNode = theNode;
//            }
//            theNode = theNode.getNext();
//        }
//       return bestDistance;
//    }

    public boolean getInsertShipment(final TRDepotsList mainDepots, final TRShipment theShipment){

//		private boolean insertAtLowestDistanceLast(final TRShipment theShipment){
        TRNode insertMe = new TRNode(theShipment);

        final int[] combination = theShipment.getCurrentComb()[0];
        TRNode[] bestNodes = new TRNode[combination.length];

        for(int i = 0; i < combination.length; i++){
            double bestDistance = 999999999;
            double currentDistance = -1;

            if(combination[i] == 1){

                TRDepot theDepot = mainDepots.getFirst();
                while(theDepot != mainDepots.getTail()){
                    TRTrucksList trucksList = theDepot.getSubList();
                    TRTruck theTruck = trucksList.getFirst();

                    while(theTruck != trucksList.getTail()){
                        TRDaysList daysList = theTruck.getSubList();
                        TRDay theDay = daysList.getAtIndex(i);
                        TRNodesList nodesList = theDay.getSubList();

                        TRNode theNode = nodesList.getFirst();
                        while(theNode != nodesList.getTail()){
                            currentDistance = findDistanceAtInsertionPoint(nodesList, theNode, theShipment);

                            if(currentDistance < bestDistance){
                                bestDistance = currentDistance;
                                bestNodes[i] = theNode;
                            }
                            theNode = theNode.getNext();
                        }


//                        TRNode bestNode = null;

//                        currentDistance = cycleThroughInsertionPoints(nodesList, bestNode, insertMe);
//
//                        if(currentDistance < bestDistance){
//                            bestDistance = currentDistance;
//                            bestNodes[i] = bestNode;
//                        }
                        theTruck = theTruck.getNext();
                    }

                    theDepot = theDepot.getNext();
                }
            }
        }


        for(int i = 0; i < bestNodes.length; i++){
            if(bestNodes[i] != null){
                if(!bestNodes[i].insertAfterCurrent(new TRNode(theShipment))){
                    return false;
                }
//                TRProblemInfo.nodesLLLevelCostF.setTotalDemand(bestNodes[i]);
//                TRProblemInfo.nodesLLLevelCostF.setTotalDistance(bestNodes[i]);

            }
        }




        return true;
//		}

    }
}
