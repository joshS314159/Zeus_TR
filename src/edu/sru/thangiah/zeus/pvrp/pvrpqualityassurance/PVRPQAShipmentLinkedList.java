//PVRP PROBLEM
//CPSC 464
//AARON ROCKBURN; JOSHUA SARVER

//***********	DECLARATION_S_OTHER
// **********************************************************************************\\
// FUNCTION_START >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//PACKAGE TITLE
package edu.sru.thangiah.zeus.pvrp.pvrpqualityassurance;

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


import edu.sru.thangiah.zeus.qualityassurance.QAShipmentLinkedList;


//CLASS
public class PVRPQAShipmentLinkedList
		extends QAShipmentLinkedList
		implements java.io.Serializable, java.lang.Cloneable {




//METHOD
//CHECKS TO SEE IF EACH NODE IS SERVICED THE REQUESTED
//NUMBER OF TIMES
public boolean customerServicedRequestedFrequency(PVRPQADepotLinkedList qaDepots) {
	//loop through all the shipments and mark which are serviced and count the number of times
	//the customers are serviced. Each shipment should be serviced no more than its requested
	//frequency
	boolean didPassTest = true;

	//COUNT VISITED FREQUENCIES FOR EACH NODE
	for(int i = 0; i < qaDepots.getDepots().size(); i++) {
		PVRPQADepot theDepot = (PVRPQADepot) qaDepots.getDepots().elementAt(i);
		//get a depot from linked list
		for(int j = 0; j < theDepot.getTrucks().size(); j++) {
			PVRPQATruck theTruck = (PVRPQATruck) theDepot.getTrucks().elementAt(j);
			//get a truck from linked list
			for(int k = 0; k < theTruck.getDays().size(); k++) {
				PVRPQADay theDay = (PVRPQADay) theTruck.getDays().elementAt(k);
				//get day from linked list
				for(int l = 0; l < theDay.getNodes().size(); l++) {
					PVRPQANode theNode = (PVRPQANode) theDay.getNodes().elementAt(l);
					//get node from linked list
					for(int m = 0; m < getShipments().size(); m++) {
						PVRPQAShipment theShipment = (PVRPQAShipment) getShipments().elementAt(m);
						//get the list of shipments; cycle through every possible shipment for
						//each routed shipment

						if(theShipment.getIndex() == theNode.getIndex()) {
							//every time we find a routed node and find it on the list
							//of all shipments, increment the counter
							theShipment.setServecount(theShipment.getServecount() + 1);
							break;
							//found our shipment, we can leave the loop and start with
							//the next shipment
						}
					}
				}
			}
		}
	}


	//CHECK OUR DATA -- COMPARE IT
	for(int n = 0; n < getShipments().size(); n++) {
		PVRPQAShipment s = (PVRPQAShipment) getShipments().elementAt(n);

		if(s.getServecount() != s.getFrequency()) {
			//okay, we didn't meet this node's frequency requirement (or overdid it)
			edu.sru.thangiah.zeus.core.Settings.printDebug(edu.sru.thangiah.zeus.core.Settings.ERROR,
														   "\n<<<<<< FAIL: Shipment " + s.getIndex() +
														   " needs serviced " +
														   s.getFrequency() + " BUT is serviced " + s.getServCount() +
														   " >>>>>>>>>>");
			didPassTest = false;
		}
		else {
			System.out.println(
					"PASS: Shipment " + s.getIndex() + " is serviced " + s.getServCount() + "/" + s.getFrequency() +
					"  time(s)");
		}
	}
	return didPassTest;

}//customerServicedRequestedFrequency ENDS HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
}
