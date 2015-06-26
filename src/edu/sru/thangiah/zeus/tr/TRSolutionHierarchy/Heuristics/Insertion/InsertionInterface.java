package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Insertion;

import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRNodesList;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRShipment;

/**
 * Created by jks1010 on 6/26/2015.
 */
public interface InsertionInterface {
	boolean getInsertShipment(TRNodesList currentNodeLL, TRShipment theShipment);
}
