package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Insertion;

import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRNode;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRNodesList;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRShipment;
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

	public boolean getInsertShipment(final TRNodesList theList, final TRShipment theShipment){
		TRNode insertMe = new TRNode(theShipment);


		int bestDistance = 999999999;
		boolean isRouteFound = false;

		TRNode theNode = theList.getHead();
		TRNode bestNodeToInsertAfter = null;

		while(theNode != theList.getTail()){
			theNode.insertAfterCurrent(insertMe);

			theList.getFeasibility().setRoute(theList);

			if(theList.getFeasibility().isFeasible()){
				int currentDistance = (int) TRProblemInfo.nodesLLLevelCostF.getTotalDistance(theList);
				if(currentDistance < bestDistance){
					bestDistance = currentDistance;
					bestNodeToInsertAfter = theNode;
					isRouteFound = true;
				}
			}
//			insertMe.removeThisObject();
			theList.removeByObject(insertMe);
			theNode = theNode.getNext();
		}
		if(isRouteFound){
			theList.insertAfterObject(insertMe, bestNodeToInsertAfter);
			theShipment.setIsAssigned(true);
		}
		theList.getFeasibility().setRoute(theList);
		TRProblemInfo.nodesLLLevelCostF.calculateTotalsStats(theList);

		return isRouteFound;
	}
}
