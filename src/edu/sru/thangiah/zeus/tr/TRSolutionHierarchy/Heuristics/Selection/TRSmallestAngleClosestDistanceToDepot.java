package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Selection;


import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRDepot;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRDepotsList;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRShipment;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRShipmentsList;


/**
 * Created by joshuasarver on 1/14/15.
 */
public class TRSmallestAngleClosestDistanceToDepot
		extends TRShipmentsList {


//The WhoAmI methods gives the id of the assigned object
//It is a static method so that it can be accessed without creating an object
public static String WhoAmI() {
	return ("Selection Type: Smallest polar angle, shortest distance to the depot");
}




public TRShipment getSelectShipment(final TRDepotsList depotsList, final TRDepot theDepot,
									final TRShipmentsList shipmentsList, final TRShipment theShipment) {

	TRShipment selectedShipment = null;
	TRShipment currentShipment = shipmentsList.getFirst();

	double currentAngle = -1;
	double bestAngle = 999999999;

	double currentDistance = -1;
	double bestDistance = 999999999;

	while(currentShipment != shipmentsList.getTail()){
		currentAngle = theDepot.getCoordinates().calculateAngleBearing(currentShipment.getCoordinates());
		currentDistance = theDepot.getCoordinates().calculateDistanceThisMiles(currentShipment.getCoordinates());

		if(currentAngle < bestAngle && currentDistance < bestDistance){
			if(currentShipment.getCanBeRouted() && !currentShipment.getIsAssigned()){
				selectedShipment = currentShipment;
				bestAngle = currentAngle;
				bestDistance = currentDistance;
			}
		}
		currentShipment = currentShipment.getNext();
	}

	return selectedShipment;


	//currDepotLL is the depot linked list of the problem
	//currDepot is the depot under consideration
	//currShipLL is the set of avaialble shipments
//	boolean isDiagnostic = false;
//	TRShipment temp = shipmentsList.getHead().getNext(); //point to the first shipment
//	TRShipment foundShipment = null; //the shipment found with the criteria
//	double angle;
//	double foundAngle = 360; //initial value
//	double distance;
//	double foundDistance = 200; //initial distance
//	double depotX, depotY;
//	int type = 2;
//
//	//Get the X and Y coordinate of the depot
//	depotX = theDepot.getXCoord();
//	depotY = theDepot.getYCoord();
//
//	while(temp != shipmentsList.getTail()) {
//		if(isDiagnostic) {
//			System.out.print("Shipment " + temp.getIndex() + " ");
//
//			if(((temp.getXCoord() - depotX) >= 0) && ((temp.getYCoord() - depotX) >= 0)) {
//				System.out.print("Quadrant I ");
//			}
//			else if(((temp.getXCoord() - depotX) <= 0) && ((temp.getYCoord() - depotY) >= 0)) {
//				System.out.print("Quadrant II ");
//			}
//			else if(((temp.getXCoord()) <= (0 - depotX)) && ((temp.getYCoord() - depotY) <= 0)) {
//				System.out.print("Quadrant III ");
//			}
//			else if(((temp.getXCoord() - depotX) >= 0) && ((temp.getYCoord() - depotY) <= 0)) {
//				System.out.print("Quadrant VI ");
//			}
//			else {
//				System.out.print("No Quadrant");
//			}
//		}
//
//		//if the shipment is assigned, skip it
//		if(temp.getIsAssigned() || !temp.getCanBeRouted()) {
//			if(isDiagnostic) {
//				System.out.println("has been assigned");
//			}
//
//			temp = temp.getNext();
//
//			continue;
//		}
//
//		distance = calcDist(depotX, temp.getXCoord(), depotY, temp.getYCoord());
//		angle = calcPolarAngle(depotX, depotX, temp.getXCoord(), temp.getYCoord());
//
//		if(isDiagnostic) {
//			System.out.println("  " + angle);
//		}
//
//		//check if this shipment should be tracked
//		if(foundShipment == null) { //this is the first shipment being checked
//			foundShipment = temp;
//			foundAngle = angle;
//			foundDistance = distance;
//		}
//		else {
//			//if angle and disnace are smaller than what had been found
//			//if (angle <= foundAngle && distance <= foundDistance) {
//			if(angle + distance <= foundAngle + foundDistance) {
//				//if ((angle*.90)+ (distance * 0.1)  <= (foundAngle*0.9) + (foundDistance*0.1)) {
//				foundShipment = temp;
//				foundAngle = angle;
//				foundDistance = distance;
//			}
//		}
//		temp = temp.getNext();
//	}
//	return foundShipment; //stub
}


}
