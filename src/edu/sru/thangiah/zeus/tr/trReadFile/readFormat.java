package edu.sru.thangiah.zeus.tr.trReadFile;

import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRDepotsList;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRShipmentsList;

/**
 * Created by library-tlc on 5/26/15.
 */
public abstract class ReadFormat implements ReadFormatInterface{
protected String problemPath;
protected String problemFileName;

protected TRShipmentsList mainShipments;
protected TRDepotsList mainDepots;

public ReadFormat(final TRShipmentsList mainShipments, final TRDepotsList mainDepots){
	this.mainShipments = mainShipments;
	this.mainDepots = mainDepots;

	this.problemPath = TRProblemInfo.inputPath;
	this.problemFileName = TRProblemInfo.problemFileName;
}
//
//public void setProblemPathAndName(final String problemPath, final String problemFileName){
//	this.problemPath = problemPath;
//	this.problemFileName = problemFileName;
//}



}
