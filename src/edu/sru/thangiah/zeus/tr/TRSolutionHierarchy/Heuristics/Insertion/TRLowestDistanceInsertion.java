package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Insertion;

import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRDay;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRNode;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRNodesList;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRShipment;

/**
 * Created by jks1010 on 6/25/2015.
 */
public class TRLowestDistanceInsertion extends TRNodesList {

	public static String WhoAmI() {
		return ("Insertion Type: LowestDistance insertion heuristic");
	}//END WHO_AM_I *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

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

	public boolean getInsertShipment(final TRNodesList currentNodeLL, final TRShipment theShipment) {
		TRNode insertAfter = currentNodeLL.getHead();
		double currentDistance = -1;
		double bestDistance = 999999999;
		TRNode bestInsertAfter = null;

		while(insertAfter != currentNodeLL.getTail()){
			currentDistance = findDistanceAtInsertionPoint(currentNodeLL, insertAfter, theShipment);

			if(currentDistance < bestDistance){
				bestDistance = currentDistance;
				bestInsertAfter = insertAfter;
			}

			insertAfter = insertAfter.getNext();
		}

		if(bestInsertAfter != null) {
			currentNodeLL.insertAfterObject(new TRNode(theShipment), bestInsertAfter);
//			insertAfter.insertAfterCurrent(new TRNode(theShipment));
			TRProblemInfo.nodesLLLevelCostF.setTotalDemand(currentNodeLL);
			TRProblemInfo.nodesLLLevelCostF.setTotalDistance(currentNodeLL);
			return true;
		}

		return false;
	}

}
