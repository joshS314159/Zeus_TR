package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.UpperLevelInsertion;

import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRDepotsList;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRShipment;

/**
 * Created by jks1010 on 6/26/2015.
 */
public interface UpperLevelInsertionInterface {
	boolean insertShipmentHeuristic(final TRDepotsList mainDepots, final TRShipment theShipment);
}
