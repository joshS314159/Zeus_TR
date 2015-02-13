package edu.sru.thangiah.zeus.pvrp;


import edu.sru.thangiah.zeus.core.Attributes;
import edu.sru.thangiah.zeus.core.Nodes;
import edu.sru.thangiah.zeus.core.NodesLinkedList;
import edu.sru.thangiah.zeus.core.ProblemInfo;

//import the parent class




/**
 * PVRP (Periodic Vehicle Routing)
 * Routes vehicles to various nodes over a given number of days
 * <p/>
 * Version 1.0
 * <p/>
 * 10/17/14
 * <p/>
 * AUTHORS
 * Aaron Rockburn and Joshua Sarver
 * Slippery Rock University of Pa
 * CPSC 464 - Fall 2014
 * <p/>
 * COPYLEFT
 * Attribution-ShareAlike 4.0 International
 * <p/>
 * BASED ON
 * Dr. Sam R. Thangiah's work at Slippery Rock University of Pa
 * A Heuristic for the Periodic Vehicle Routing Problem by M. Gaudioso, G. Paletta
 * Chou
 * The Periodic Vehicle Routing Problem by S. Coene, A. Arnout and F. Spieksma
 * A Variable Neighborhood Search Heuristic for Periodic Routing Problems by Vera C. Hemmelmayr, Karl F. Doerner§,
 * Richard F. Hartl
 * <p/>
 * Methods are generally sorted by breadth-first order
 */


public class PVRPNodesLinkedList
		extends NodesLinkedList
		implements java.io.Serializable, java.lang.Cloneable {

//VARIABLES
private PVRPNodes       head;
private PVRPNodes       tail;
private PVRPTruckType   truckType;
private PVRPAttributes  attributes;
private PVRPFeasibility feasibility;




//CONSTRUCTOR
public PVRPNodesLinkedList() {
	this.head = new PVRPNodes();
	this.tail = new PVRPNodes();
	this.linkHeadTail();

	this.attributes = new PVRPAttributes();
}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//CONSTRUCTOR
public PVRPNodesLinkedList(double depotX, double depotY) {
	this.head = new PVRPNodes(new PVRPShipment(0, depotX, depotY, 0));
	this.tail = new PVRPNodes(new PVRPShipment(-1, depotX, depotY, 0));
	//add the depot as a shipment/node to the head/tail of nodes linked list
	//depot in head is signified by '0' and in tail as '-1'
	//so last node for all trucks is '-1'

	this.linkHeadTail();
	//assign the PVRP attributes
	this.attributes = new PVRPAttributes();
}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//SETTER
public void setHeadNext(PVRPNodes head) { this.getHead().setNext(head); }




//METHOD
//usually would insert a specific node...
//IS JUST A STUB HERE
public PVRPNodes insertNodes(PVRPNodes insertNode) {
	System.out.println("ERROR: InsertNodes was called in from NodesLinkedList");
	return null;
}//END INSERT_NODES *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//METHOD
	//removes a node by the specified index
	public Nodes removeNodeByIndex(int index)
	{
		PVRPNodes currentNode = this.head.getNext();
		PVRPNodes nodeBeforeRemoveNode = null;
		while (currentNode != this.tail)
		{
			if (currentNode.getIndex() == index)
			{
				nodeBeforeRemoveNode = currentNode.getPrev();
				currentNode.getPrev().setNext(currentNode.getNext());
				currentNode.getNext().setPrev(currentNode.getPrev());
				currentNode.setNext(null);
				currentNode.setPrev(null);

				break;
			}
			currentNode = currentNode.getNext();
		}
		return currentNode;
	}




// SETTER
public PVRPFeasibility getFeasibility() { return this.feasibility; }




//METHOD
//insert a specific node after a specified node
public PVRPNodes insertAfterNodes(PVRPNodes afterThis, PVRPNodes toInsert) {
	PVRPNodes currentNode = this.head;  //get the head from the linked list

	//afterThis: look for this in the linked list - we will insert
	//toInsert after this

	while(currentNode != this.tail) {
		//keep stepping through until we reach the tail

		if(currentNode == afterThis) {
			//we have stepped through and found
			//the node we want to insert after

			//relink everything properly to
			//insert toInsert
			toInsert.setPrevious(currentNode);
			toInsert.setNext(currentNode.getNext());
			currentNode.setNext(toInsert);
			toInsert.getNext().setPrevious(toInsert);
			break;
		}
		currentNode = currentNode.getNext();
		//get the next node
	}
	return currentNode;
}//END INSERT_AFTER_NODES *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//METHOD
//inserts shipment into the nodes linked list with the
//selected insertion type
public boolean insertShipment(PVRPShipment theShipment) {
	//method for inserting the shipment into a truck
	PVRPNodesLinkedList status = (PVRPNodesLinkedList) ProblemInfo.insertShipType;

	return status.getInsertShipment(this, theShipment);
}//END INSERT_SHIPMENT *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//GETTER
public PVRPNodes getHead() { return this.head; }




//GETTER
public boolean getInsertShipment(PVRPNodesLinkedList pvrpNodesLinkedList, PVRPShipment theShipment) { return false; }




//GETTER-METHOD
//returns the total demand of all the nodes in
//this linked list
public int getTotalDemand() {
	int demand = 0;
	PVRPNodes theNode = this.getHead();
	//the head (depot) doesn't have a demand so we
	//can skip it

	while(theNode.getNext() != this.getTail() && theNode.getNext() != null) {
		theNode = theNode.getNext();
		//until we reach the tail
		demand += theNode.getDemand();
	}
	return demand;
}//END GET_TOTAL_DEMAND *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//GETTER
public PVRPNodes getTail() { return this.tail; }









//GETTER
public double getCost() { return this.attributes.getTotalCost(); }







//SETTER
public void setHead(PVRPNodes headVal) { this.head = headVal; }




//SETTER
public void setTail(PVRPNodes tailVal) { this.tail = tailVal; }




//METHOD
//links the head and tail of this
//linked list together
public void linkHeadTail() {
	this.head.setNext(this.tail);        //node after head is tail
	this.tail.setPrevious(this.head);    //node before tail is head
	this.head.setPrevious(null);        //nothing comes before head (firstmost)
	this.tail.setNext(null);            //nothing comes after tail (lastmost)
}//END LINK_HEAD_TAIL *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//GETTER
public PVRPAttributes getAttributes() { return this.attributes; }




//SETTER
public void setAttributes(PVRPAttributes attributes) { this.attributes = attributes; }




//GETTER
public PVRPTruckType getTruckType() { return this.truckType; }








//METHOD-GETTER
//should be singular...
//either way, gets a node and returns it based
//on a requested specific position
public PVRPNodes getNodesAtPosition(int position) {
	PVRPNodes current = this.head;
	int currentPosition = 0;

	while((current != null) && (currentPosition < position)) {
		//while we haven't reached the end of the list
		//and the currentPosition is less than the requested
		//position
		current = current.getNext();
		currentPosition++;
	}
	return current;
}//END GET_NODES_AT_POSITION *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//METHOD-GETTER
//same thing as getNodeAtPosition
public PVRPNodes getNodeByIndex(int position) {
	return getNodesAtPosition(position);
}//END GET_NODE_BY_INDEX *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//METHOD-GETTER
//gets the route taken and returns
//it as a buffered string
public String getRouteString() {
	StringBuffer buffer = new StringBuffer();
	PVRPNodes theNode = getHead();

	while(theNode != null) {
		buffer.append(theNode.getIndex()).append(" ");
		theNode = theNode.getNext();
	}
	return buffer.toString();
}//END GET_ROUTE_STRING *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//SETTER
public void setFeasibility(PVRPFeasibility feasibilityVal) { this.feasibility = feasibilityVal; }




//SETTER
public void setTruckType(PVRPTruckType truckTypeVal) { this.truckType = truckTypeVal; }



public PVRPNodes getNodeAtPosition(int position){
	PVRPNodes theNode = this.getHead();
	int count = -1;

	if(position >= 0 && position < this.getSize()) {
		while (count != position) {
			theNode = theNode.getNext();
			count++;
			if (theNode == this.getTail()) {
				return null;
			}
		}
	}
	else{
		return null;
	}
	return theNode;
}

public int getSize(){
	PVRPNodes theNode = this.getHead();
	int sizeCounter = 0;

	if(!isEmpty()) {
		while (theNode.getNext() != this.getTail()) {
			theNode = theNode.getNext();
			sizeCounter++;
		}
		return sizeCounter;
	}
	return 0;
}



public PVRPNodes getFirstShipment(){
	if(!isEmpty()){
		return this.getHead().getNext();
	}
	return null;

}

public PVRPNodes getLastShipment(){
	if(!isEmpty()) {
		return this.getTail().getPrev();
	}
	return null;
}

public boolean isEmpty(){
	if(this.getHead().getNext() == this.getTail() || this.getHead().getNext() == null){
		return true;
	}
	return false;
}



}//END PVRP_NODES_LINKED_LIST *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//******************************************************************************************************\\
////>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> LINEAR_GREEDY_INSERT_SHIPMENT STARTS HERE <<<<<<<<<<<<<<<<<<<<<<<<\\\\
//******************************************************************************************************\\
class PVRPLinearGreedyInsertShipment
		extends PVRPNodesLinkedList {

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
public boolean getInsertShipment(PVRPNodesLinkedList currentNodeLL, PVRPShipment theShipment) {
	PVRPNodes theNodeTemp;
	//currentNodeLL is the reference to the current node linked list being considered for insertion
	//theShipment is the shipment to be inserted
	PVRPNodes nodeToInsert = new PVRPNodes(theShipment);

	// the route is empty
	if(currentNodeLL.getHead().getNext() == currentNodeLL.getTail()) {
		currentNodeLL.setHeadNext(nodeToInsert);
		currentNodeLL.getTail().setPrevious(nodeToInsert);
		nodeToInsert.setPrevious(currentNodeLL.getHead());
		nodeToInsert.setNext(currentNodeLL.getTail());

		//if its not feasible, return route to what it was and return false
		if(!currentNodeLL.getFeasibility().isFeasible()) {
			//remove the inserted node
			theNodeTemp = (PVRPNodes) currentNodeLL.getHead().getNext();
			theNodeTemp.setNext(null);
			theNodeTemp.setPrevious(null);

			//point the head and tail to each other
			currentNodeLL.setHeadNext(currentNodeLL.getTail());
			currentNodeLL.getTail().setPrevious(currentNodeLL.getHead());

			return false;
		}
	}
	// the route is not empty
	else {
//		double cost = Double.MAX_VALUE;
		double distance = Double.MAX_VALUE;
//		PVRPNodes costCell = null; //cell after which the new cell was inserted to achieve cost
		PVRPNodes distanceCell = null;

		PVRPNodes prevCell = currentNodeLL.getHead();
		PVRPNodes nextCell = currentNodeLL.getHead().getNext();

		while(nextCell != currentNodeLL.getTail()) {
			//insert the cell after current prevCell
			prevCell.setNext(nodeToInsert);
			nodeToInsert.setPrevious(prevCell);
			nodeToInsert.setNext(nextCell);
			nextCell.setPrevious(nodeToInsert);

			//check to see if the new route is feasible
			if(currentNodeLL.getFeasibility().isFeasible()) {
				//calculate the cost
//				double tempCost = ProblemInfo.nodesLLLevelCostF.getTotalCost(currentNodeLL);
				double tempDistance = ProblemInfo.nodesLLLevelCostF.getTotalDistance(currentNodeLL);

				if(tempDistance < distance){
					distance = tempDistance;
					distanceCell = prevCell;
				}


				//decide if this cell should be saved
//				if(tempCost < cost) {
//					cost = tempCost;
//					costCell = prevCell;
//				}
			}
//			else{
//				System.out.println("ERROR: Node " + nodeToInsert.getShipment().getIndex() + " is unable to be inserted" +
//						" due to capacity and distance restrictions >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//			}
			//remove the new cell
			prevCell.setNext(nextCell);
			nextCell.setPrevious(prevCell);
			nodeToInsert.setNext(null);
			nodeToInsert.setPrevious(null);

			//set prevCell and nextCell to the next cells in linked list
			prevCell = nextCell;
			nextCell = prevCell.getNext();
			//print out current route

		}


//		if(costCell != null) {
//			prevCell = costCell;
//			nextCell = prevCell.getNext();
//			prevCell.setNext(nodeToInsert);
//			nodeToInsert.setPrevious(prevCell);
//			nodeToInsert.setNext(nextCell);
//			nextCell.setPrevious(nodeToInsert);
//		}
		if(distanceCell != null) {
			prevCell = distanceCell;
			nextCell = prevCell.getNext();
			prevCell.setNext(nodeToInsert);
			nodeToInsert.setPrevious(prevCell);
			nodeToInsert.setNext(nextCell);
			nextCell.setPrevious(nodeToInsert);
		}
		else {
			return false;
		}
	}

	theShipment.setIsAssigned(true);
	ProblemInfo.nodesLLLevelCostF.calculateTotalsStats(currentNodeLL);

	return true;
}
}
