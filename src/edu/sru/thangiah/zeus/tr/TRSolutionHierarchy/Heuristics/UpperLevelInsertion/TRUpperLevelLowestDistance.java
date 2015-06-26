package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.UpperLevelInsertion;

import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.*;

/**
 * Created by library-tlc on 6/18/15.
 */
public class TRUpperLevelLowestDistance implements UpperLevelInsertionInterface {



    public boolean insertShipmentHeuristic(final TRDepotsList mainDepots, final TRShipment theShipment){

        final int numberCombinations = theShipment.getNoComb();
        TRNode[][] bestNodes = new TRNode[numberCombinations][theShipment.getCurrentComb()[0].length];
        TRNodesList[][] bestNodesList = new TRNodesList[numberCombinations][theShipment.getCurrentComb()[0].length];
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
//                            TRNode insertNode = new TRNode(insertMe);
                            if(nodesList.insertShipment(theShipment)){
                                currentDistance =  TRProblemInfo.nodesLLLevelCostF.getTotalDistance(nodesList);

                                if(currentDistance < bestDistance) {
                                    bestDistance = currentDistance;
                                    distances[h][i] = bestDistance;
                                    bestNodesList[h][i] = nodesList;
                                }

                                nodesList.removeByShipment(theShipment);
                                TRProblemInfo.nodesLLLevelCostF.setTotalDistance(nodesList);
                            }
                            theTruck = theTruck.getNext();
                        }
                        theDepot = theDepot.getNext();
                    }
                }
            }
        }

        return doInsertion(distances, theShipment, bestNodes, bestNodesList);
    }


    private boolean doInsertion(final double[][] distances, final TRShipment theShipment, final TRNode[][] bestNodes, final TRNodesList[][] bestNodesList){

        int bestCombinationNumber = -1;
        double bestDistance = 999999999;
        for(int i = 0; i < distances.length; i++){
            double currentDistance = 0;
            for(int j = 0; j < distances[i].length; j++){
                currentDistance += distances[i][j];
            }

            if (currentDistance < bestDistance && doCombinationsMatch(theShipment.getCurrentComb()[i], bestNodesList[i])) {
                bestDistance = currentDistance;
                bestCombinationNumber = i;
            }
        }

        if(bestCombinationNumber == -1){
            System.out.println("better algorithm needed");
            System.exit(1);
        }


            TRNodesList[] bestCombination = bestNodesList[bestCombinationNumber];
            for (int i = 0; i < bestCombination.length; i++) {
                if (bestCombination[i] != null) {
                    if (!bestCombination[i].insertShipment(theShipment)) {
                        return false;
                    }
                }
            }

        return true;

    }

    private boolean doCombinationsMatch(final int[] theCombination, final Object[] assessedCombination){
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
