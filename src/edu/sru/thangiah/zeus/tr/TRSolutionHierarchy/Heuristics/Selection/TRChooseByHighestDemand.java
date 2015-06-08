package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Selection;

import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.*;

/**
 * Created by jks1010 on 6/8/2015.
 */
public class TRChooseByHighestDemand extends TRShipmentsList {

	public TRShipment getSelectShipment(final TRDepotsList depotsList, final TRDepot theDepot,
	                                    final TRShipmentsList shipmentsList, final TRShipment theShipment) {
		//currDepotLL is the depot linked list of the problem
		//currDepot is the depot under consideration
		//currShipLL is the set of avaialble shipments
		TRShipment temp = shipmentsList.getFirst(); //point to the first shipment
		int highestDemand = -1;
		TRShipment highestShipment = null;

		while(temp != shipmentsList.getTail()){
			if(temp.getDemand() > highestDemand && !temp.getIsAssigned() && temp.getCanBeRouted()){
				highestDemand = temp.getDemand();
				highestShipment = temp;
			}
			temp = temp.getNext();
		}


		return highestShipment; //stub
	}




}



