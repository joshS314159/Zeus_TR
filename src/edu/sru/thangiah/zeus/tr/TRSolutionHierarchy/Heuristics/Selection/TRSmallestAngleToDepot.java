package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Selection;


import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRDepot;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRDepotsList;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRShipment;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRShipmentsList;


/**
 * Created by joshuasarver on 1/14/15.
 */
public class TRSmallestAngleToDepot
		extends TRShipmentsList {

public TRShipment getSelectShipment(final TRDepotsList depotsList, final TRDepot theDepot,
									final TRShipmentsList shipmentsList, final TRShipment theShipment) {
	//currDepotLL is the depot linked list of the problem
	//currDepot is the depot under consideration
	//currShipLL is the set of avaialble shipments
	boolean isDiagnostic = false;
	TRShipment temp = shipmentsList.getHead().getNext(); //point to the first shipment


	TRShipment foundShipment = null; //the shipment found with the criteria
	double angle;
	double foundAngle = 360; //initial value
	//double distance;
	//double foundDistance = 200; //initial distance
	double depotX, depotY;
	int type = 2;

	//Get the X and Y coordinate of the depot
	depotX = theDepot.getXCoord();
	depotY = theDepot.getYCoord();

	while(temp != shipmentsList.getTail()) {

		if(isDiagnostic) {
			System.out.println("Temp is " + temp);
			System.out.println("Tail is " + getTail());
			System.out.print("Shipment " + temp.getIndex() + " ");

			if(((temp.getXCoord() - depotX) >= 0) && ((temp.getYCoord() - depotX) >= 0)) {
				System.out.print("Quadrant I ");
			}
			else if(((temp.getXCoord() - depotX) <= 0) && ((temp.getYCoord() - depotY) >= 0)) {
				System.out.print("Quadrant II ");
			}
			else if(((temp.getXCoord()) <= (0 - depotX)) && ((temp.getYCoord() - depotY) <= 0)) {
				System.out.print("Quadrant III ");
			}
			else if(((temp.getXCoord() - depotX) >= 0) && ((temp.getYCoord() - depotY) <= 0)) {
				System.out.print("Quadrant VI ");
			}
			else {
				System.out.print("No Quadrant");
			}
		}

		//if the shipment is assigned, skip it
		if(temp.getIsAssigned()) {
			if(isDiagnostic) {
				System.out.println("has been assigned");
			}

			temp = temp.getNext();

			continue;
		}

		angle = calcPolarAngle(depotX, depotX, temp.getXCoord(), temp.getYCoord());

		if(isDiagnostic) {
			System.out.println("  " + angle);
		}

		//check if this shipment should be tracked
		if(foundShipment == null) { //this is the first shipment being checked
			foundShipment = temp;
			foundAngle = angle;
		}
		else {
			if(angle < foundAngle) { //found an angle that is less
				foundShipment = temp;
				foundAngle = angle;
			}
		}
		temp = temp.getNext();
	}
	return foundShipment; //stub
}

}
