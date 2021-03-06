package edu.sru.thangiah.zeus.tr.trReadFile;

import edu.sru.thangiah.zeus.tr.TRCoordinates;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.*;
import edu.sru.thangiah.zeus.tr.TRTruckType;

//import java.util.IntSummaryStatistics;

/**
 * Created by library-tlc on 5/26/15.
 */
public abstract class ReadFormat implements ReadFormatInterface {


protected final int TYPE_NUMERIC = 0;
protected String problemPath;
protected String problemFileName;
protected TRShipmentsList mainShipments;
protected TRDepotsList mainDepots;

protected boolean isDistanceRestraint = false;
protected boolean isDemandRestraint = false;

protected int numberDepotsToMake = 0;
protected int maxDepotDemand = 0;
protected int maxDepotDistance = 0;
protected TRCoordinates depotCoordinates;

protected int numberTrucksToMake = 0;
protected int maxTruckDemand = 0;
protected int maxTruckDistance = 0;
protected boolean isMultipleTruckTypes = false;

protected int numberDaysToMake = 0;
protected int maxDayDemand = 0;
protected int maxDayDistance = 0;


private final int bigInteger = 999999999;


public ReadFormat(TRShipmentsList mainShipments, TRDepotsList mainDepots) {
	this.mainShipments = mainShipments;
	this.mainDepots = mainDepots;

	this.problemPath = TRProblemInfo.inputPath;
	this.problemFileName = TRProblemInfo.problemFileName;
}


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


public void createHierarchy(){
	if(!isDistanceRestraint){
		maxDepotDistance = bigInteger;
		maxTruckDistance = bigInteger;
		maxDayDistance   = bigInteger;
	}

	if(!isDemandRestraint){
		maxDayDemand = bigInteger;
		maxTruckDemand = bigInteger;
		maxDepotDemand = bigInteger;
	}

	if(numberDepotsToMake > 0 && numberDaysToMake > 0 && numberTrucksToMake > 0){
		TRTruckType tempTruckType = new TRTruckType(maxDayDistance, maxDayDemand);
		for(int i = 0; i < numberDepotsToMake; i++){
			mainDepots.insertAfterLastIndex(new TRDepot(depotCoordinates));
			mainDepots.getLast().setDepotNum(i);
			mainDepots.getAttributes().setMaxDemand(maxDepotDemand);
			mainDepots.getAttributes().setMaxDistance(maxDepotDistance);

			TRTrucksList trucksList = mainDepots.getLast().getSubList();

			for(int j = 0; j < numberTrucksToMake; j++){
				trucksList.insertAfterLastIndex(new TRTruck());
				trucksList.getLast().setTruckType(tempTruckType);
				trucksList.getLast().setTruckNum(j);
				trucksList.getAttributes().setMaxDemand(maxTruckDemand);
				trucksList.getAttributes().setMaxDistance(maxTruckDistance);

				TRDaysList daysList = trucksList.getLast().getSubList();

				for(int k = 0; k < numberDaysToMake; k++){
					daysList.insertAfterLastIndex(new TRDay());
					daysList.getLast().setDayNumber(k);
					daysList.getAttributes().setMaxDistance(maxDayDistance);
					daysList.getAttributes().setMaxDemand(maxDayDemand);
					daysList.getLast().getSubList().setStartEndPoints(depotCoordinates, depotCoordinates);

					if(!isMultipleTruckTypes){

//						tempTruckType.setMaxDistance(maxTruckDistance);
//						tempTruckType.setMaxCapacity(maxTruckDemand);
						TRNodesList nodesList = daysList.getLast().getSubList();
						nodesList.setTruckType(tempTruckType);
						nodesList.getFeasibility().setMaxDemand(maxDayDemand);
						nodesList.getFeasibility().setMaxDistance(maxDayDistance);
//						daysList.getHead().getSubList().setTruckType(new TRTruckType());
//						daysList.getTail().getSubList().setTruckType(new TRTruckType());
						TRProblemInfo.truckTypes.add(tempTruckType);
					}
					else{
						//code here for multiple truck types
					}

				}

			}

		}
	}
}



}
