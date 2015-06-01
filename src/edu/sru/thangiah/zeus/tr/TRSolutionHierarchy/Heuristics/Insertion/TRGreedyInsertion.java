package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Insertion;


import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRNode;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRNodesList;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRShipment;


/**
 * Created by joshuasarver on 1/14/15.
 */
public class TRGreedyInsertion
		extends TRNodesList {

final double MAX_DOUBLE = 999999999;
//METHOD
//The WhoAmI methods gives the id of the assigned object
//It is a static method so that it can be accessed without creating an object
public static String WhoAmI() {
	return ("Insertion Type: Linear greedy insertion heuristic");
}//END WHO_AM_I *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//METHOD-GETTER
//gets the location to insert the passed shipment




/**
 * @aaron comment
 */
public boolean getInsertShipment(TRNodesList currentNodeLL, TRShipment theShipment) {
	//currentNodeLL is the reference to the current node linked list being considered for insertion
	//theShipment is the shipment to be inserted
	TRNode nodeToInsert = new TRNode(theShipment);

	// the route is empty
	if(currentNodeLL.getHead().getNext() == currentNodeLL.getTail()) {
		currentNodeLL.insertAfterLastIndex(nodeToInsert);


		//if its not feasible, return route to what it was and return false
		currentNodeLL.getFeasibility().setRoute(currentNodeLL);
		if(!currentNodeLL.getFeasibility().isFeasible()) {
			currentNodeLL.emptyList();
			currentNodeLL.getFeasibility().setRoute(currentNodeLL);
			return false;
		}
	}
	// the route is not empty
	else {


		TRNode theNode = currentNodeLL.getHead();
		TRNode bestNodeToInsertAfter = null;
		double bestDistance = MAX_DOUBLE;
		boolean isValidSolutionFound = false;

		while(theNode != currentNodeLL.getTail()){
			theNode.insertAfterCurrent(nodeToInsert);

			currentNodeLL.getFeasibility().setRoute(currentNodeLL);



			if(!currentNodeLL.getFeasibility().isFeasible()){
				currentNodeLL.removeByObject(nodeToInsert);
				currentNodeLL.getFeasibility().setRoute(currentNodeLL);
			}
			else{
				double temporaryDistance = TRProblemInfo.nodesLLLevelCostF.getTotalDistance(currentNodeLL);
				if(temporaryDistance < bestDistance){
					bestDistance = temporaryDistance;
					bestNodeToInsertAfter = theNode;
					isValidSolutionFound = true;
				}
				currentNodeLL.removeByObject(nodeToInsert);
				currentNodeLL.getFeasibility().setRoute(currentNodeLL);
			}

			theNode = theNode.getNext();
		}


		if(!isValidSolutionFound){
			return false;
		}
		else{
			bestNodeToInsertAfter.insertAfterCurrent(nodeToInsert);
			currentNodeLL.getFeasibility().setRoute(currentNodeLL);
		}
	}

	theShipment.setIsAssigned(true);
	TRProblemInfo.nodesLLLevelCostF.calculateTotalsStats(currentNodeLL);

	return true;
}




}
