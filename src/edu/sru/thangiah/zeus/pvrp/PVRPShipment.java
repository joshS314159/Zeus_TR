package edu.sru.thangiah.zeus.pvrp;

//import the parent class


import edu.sru.thangiah.zeus.core.Shipment;


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


public class PVRPShipment
		extends Shipment
		implements java.io.Serializable, java.lang.Cloneable {

//VARIABLES
private double       extraVariable;
private PVRPShipment previous;
private PVRPShipment next;
private int          index;
private boolean canBeRouted = false;




//CONSTRUCTOR
public PVRPShipment() {

//VARIABLES
	this.index = -1;

}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//CONSTRUCTOR
public PVRPShipment(int index, double depotX, double depotY, int demand) {
	this.index = index;
	this.setxCoord(depotX);
	this.setyCoord(depotY);
	this.setDemand(demand);
}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//CONSTRUCTOR
public PVRPShipment(int customerNumber, double xCoordinate, double yCoordinate, double DUMMY, int demand, int frequency,
					int numberCombinations, int list[], int currentCombination[][]) {
	//PASSED EXPLANATION
	//customerNumber == nodeNumber/nodeIndex/shipmentIndex
	//DUMMY == that variable back in PVRP that we aren't sure what it is
	//numberCombinations is the number of varying day combinations a node would like
	//currentCombination is the current chosen <<<decoded>>> combination

	int combinationTemp[][] = new int[numberCombinations][PVRPProblemInfo.noOfDays];


	for(int i = 0; i < numberCombinations; i++) {
		for(int j = 0; j < PVRPProblemInfo.noOfDays; j++) {
			combinationTemp[i][j] = currentCombination[i][j];
			//re-adjusts size of array to fit the currentCombination
			//data size
			//ex. if MAX_HORIZON == 14, then there are null cells in the old array
		}
	}

	setIndex(customerNumber);       //set node number
	setxCoord(xCoordinate);         //set x coordinate
	setyCoord(yCoordinate);            //set y coordinate
	setDemand(demand);                //set the demand for the node
	setFrequency(frequency);        //set the visit frequency for the node
	setCurrentComb(combinationTemp);            //set combs
	setNoComb(numberCombinations);

	extraVariable = Math.random();        //VRP had this...not sure what is does exactly
}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




//GETTER
public double getExtraVariable() {
	return extraVariable;
}




//SETTER
public void setPrevious(PVRPShipment s) {
	this.previous = s;
}

public void setPrev(PVRPShipment s){setPrevious(s);}



//SETTER
public void setIndex(int index) {
	this.index = index;
}




//GETTER
public int getIndex() {
	return this.index;
}




//GETTER
public PVRPShipment getPrev() {
	return getPrevious();
}

public PVRPShipment getPrevious(){return this.previous; }




//GETTER
public PVRPShipment getNext() {
	return this.next;
}





//SETTER
public void setNext(PVRPShipment s) {
	this.next = s;
}




//GETTER
//public PVRPNodes getTempNext() {
//	return this.tempNext;
//}




//SETTER
//public void setTempNext(PVRPNodes tempNext) { this.tempNext = tempNext; }
//
//

    public void setCanBeRouted(boolean var){
        canBeRouted = var;
    }

    public boolean getCanBeRouted(){
        return canBeRouted;
    }
}
