package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Insertion;

import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRDay;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRNode;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRNodesList;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRShipment;

/**
 * Created by jks1010 on 6/25/2015.
 */
public class TRLowestDistanceInsertion extends TRNodesList implements InsertionInterface{

	public static String WhoAmI() {
		return ("Insertion Type: LowestDistance insertion heuristic");
	}//END WHO_AM_I *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private double findDistanceAtInsertionPoint(final TRNodesList nodesList, final TRNode insertAfter, final TRShipment insertMe){
		TRNode insertNode = new TRNode(insertMe);
		boolean isFeasible = false;
		nodesList.insertAfterObject(insertNode, insertAfter);

		nodesList.getFeasibility().setRoute(nodesList);
		isFeasible = nodesList.getFeasibility().isFeasible();

		final double distance = nodesList.getAttributes().getTotalDistance();

		nodesList.removeByObject(insertNode);
		TRProblemInfo.nodesLLLevelCostF.setTotalDemand(nodesList);
		TRProblemInfo.nodesLLLevelCostF.setTotalDistance(nodesList);

		if(!isFeasible){ return 999999999; }
		return distance;
	}


	public boolean getInsertShipment(final TRNodesList nodesList, final TRShipment theShipment) {
		double bestDistance = 999999999;
		double currentDistance = -1;
		TRNode theNode = nodesList.getFirst();
		TRNode bestNode = null;

		while(theNode != nodesList.getTail()) {
			currentDistance = findDistanceAtInsertionPoint(nodesList, theNode, theShipment);
			if(currentDistance < bestDistance) {
				bestDistance = currentDistance;
//				distances[h][i] = bestDistance;
				bestNode = theNode;
			}
			theNode = theNode.getNext();
		}

		return nodesList.insertAfterObject(new TRNode(theShipment), bestNode);

	}

}
