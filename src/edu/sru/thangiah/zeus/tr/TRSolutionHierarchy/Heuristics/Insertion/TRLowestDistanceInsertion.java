package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Insertion;

import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.*;
import org.apache.xmlbeans.impl.xb.xmlconfig.Extensionconfig;

/**
 * Created by jks1010 on 6/3/2015.
 */
public class TRLowestDistanceInsertion extends TRNodesList //implements InterfaceInsertion
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


	public boolean getInsertShipment(final TRDepotsList mainDepots, final TRNodesList theList, final TRShipment theShipment){
		TRNode insertMe = new TRNode(theShipment);
		double bestDistance = 999999999;
		double currentDistance = -1;
		TRNodesList bestNodesList = null;

		TRDepot theDepot = mainDepots.getFirst();
		while(theDepot != mainDepots.getTail()){
			TRTrucksList trucksList = theDepot.getSubList();
			TRTruck theTruck = trucksList.getFirst();

			while(theTruck != trucksList.getTail()){
				TRDaysList daysList = theTruck.getSubList();
				TRDay theDay = daysList.getFirst();

				while(theDay != daysList.getTail()){
					TRNodesList nodesList = theDay.getSubList();

					currentDistance = findDistanceAtInsertionPoint(nodesList, insertMe);

					if(currentDistance < bestDistance && !theShipment.getIsAssigned()){
						bestDistance = currentDistance;
						bestNodesList = nodesList;
					}

//					TRNode theNode = nodesList.getFirst();
//
//					while(theNode != nodesList.getTail()){
//
//						theNode = theNode.getNext();
//					}

					theDay = theDay.getNext();
				}

				theTruck = theTruck.getNext();
			}

			theDepot = theDepot.getNext();
		}

		boolean status = bestNodesList.insertAfterLastIndex(insertMe);
//		boolean status = bestNodesList.insertNode(insertMe);
		TRProblemInfo.nodesLLLevelCostF.calculateTotalsStats(bestNodesList);

		return status;


//		TRNode insertMe = new TRNode(theShipment);
//
//
//		int bestDistance = 999999999;
//		boolean isRouteFound = false;
//
//		TRNode theNode = theList.getHead();
//		TRNode bestNodeToInsertAfter = null;
//
//		while(theNode != theList.getTail()){
//			theNode.insertAfterCurrent(insertMe);
//
//			theList.getFeasibility().setRoute(theList);
//
//			if(theList.getFeasibility().isFeasible()){
//				int currentDistance = (int) TRProblemInfo.nodesLLLevelCostF.getTotalDistance(theList);
//				if(currentDistance < bestDistance){
//					bestDistance = currentDistance;
//					bestNodeToInsertAfter = theNode;
//					isRouteFound = true;
//				}
//			}
////			insertMe.removeThisObject();
//			theList.removeByObject(insertMe);
//			theNode = theNode.getNext();
//		}
//		if(isRouteFound){
//			theList.insertAfterObject(insertMe, bestNodeToInsertAfter);
//			theShipment.setIsAssigned(true);
//		}
//		theList.getFeasibility().setRoute(theList);
//		TRProblemInfo.nodesLLLevelCostF.calculateTotalsStats(theList);
//
//		return isRouteFound;
	}
}
