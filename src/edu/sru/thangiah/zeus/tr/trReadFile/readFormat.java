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

	protected final int TYPE_NUMERIC = 0;

protected TRShipmentsList mainShipments;
protected TRDepotsList mainDepots;

public ReadFormat( TRShipmentsList mainShipments,  TRDepotsList mainDepots){
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
public String getProblemPath() {
	return problemPath;
}

	public void setProblemPath(final String problemPath) {
		this.problemPath = problemPath;
	}

	public String getProblemFileName() {
		return problemFileName;
	}

	public void setProblemFileName(final String problemFileName) {
		this.problemFileName = problemFileName;
	}


}
