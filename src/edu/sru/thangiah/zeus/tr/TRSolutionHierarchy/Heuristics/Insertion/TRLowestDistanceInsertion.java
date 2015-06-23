package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Insertion;

import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.*;
import org.apache.xmlbeans.impl.xb.xmlconfig.Extensionconfig;

/**
 * Created by jks1010 on 6/3/2015.
 */
public class TRLowestDistanceInsertion //extends TRNodesList //implements InterfaceInsertion
 {
//	@Override
	public static String WhoAmI() {
		return ("TRLowestDistanceInsertion (Greedy, revised)");
	}

	 private double findDistanceAtInsertionPoint(final TRNodesList nodesList, final TRNode insertMe){
		 nodesList.insertNode(insertMe);
		 TRProblemInfo.nodesLLLevelCostF.calculateTotalsStats(nodesList);

		 final double distance = nodesList.getAttributes().getTotalDistance();

		 nodesList.removeByObject(insertMe);
		 TRProblemInfo.nodesLLLevelCostF.calculateTotalsStats(nodesList);

		 return distance;

	 }

	 private double findDistanceAtLastPoint(final TRNodesList nodesList, final TRNode insertMe){
		 nodesList.insertAfterLastIndex(insertMe);
		 TRProblemInfo.nodesLLLevelCostF.setTotalDemand(nodesList);
		 TRProblemInfo.nodesLLLevelCostF.setTotalDistance(nodesList);

		 final double distance = nodesList.getAttributes().getTotalDistance();

		 nodesList.removeByObject(insertMe);
		 TRProblemInfo.nodesLLLevelCostF.setTotalDemand(nodesList);
		 TRProblemInfo.nodesLLLevelCostF.setTotalDistance(nodesList);

		 return distance;

	 }

	public boolean getInsertShipment(final TRDepotsList mainDepots, final TRShipment theShipment){

//		private boolean insertAtLowestDistanceLast(final TRShipment theShipment){
			TRNode insertMe = new TRNode(theShipment);

			final int[] combination = theShipment.getCurrentComb()[0];
			TRNodesList[] bestNodes = new TRNodesList[combination.length];

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

							currentDistance = findDistanceAtLastPoint(nodesList, insertMe);

							if(currentDistance < bestDistance){
								bestDistance = currentDistance;
								bestNodes[i] = nodesList;
							}
							theTruck = theTruck.getNext();
						}

						theDepot = theDepot.getNext();
					}
				}
			}


			for(int i = 0; i < bestNodes.length; i++){
				if(bestNodes[i] != null){
					bestNodes[i].insertAfterLastIndex(new TRNode(theShipment));
					TRProblemInfo.nodesLLLevelCostF.setTotalDemand(bestNodes[i]);
					TRProblemInfo.nodesLLLevelCostF.setTotalDistance(bestNodes[i]);

				}
			}




			return true;
//		}

	}
}
