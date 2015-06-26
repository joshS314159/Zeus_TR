package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Selection;

import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRDepot;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRDepotsList;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRShipment;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRShipmentsList;

/**
 * Created by jks1010 on 6/26/2015.
 */
public interface SelectionInterface {
	TRShipment getSelectShipment(final TRDepotsList depotsList, final TRDepot theDepot,
	                                    final TRShipmentsList shipmentsList, final TRShipment theShipment);
}
