package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;

//import the parent class


import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.core.TruckLinkedList;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.DoublyLinkedListCoreInterface;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.DoublyLinkedListInterface;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.DoublyLinkedList;

import java.math.BigDecimal;


public class TRTrucksList
		extends TruckLinkedList
		implements java.io.Serializable, Cloneable,// DoublyLinkedList
		DoublyLinkedListInterface<TRTruck>, DoublyLinkedListCoreInterface<TRTruck> {


private DoublyLinkedList<TRTrucksList, TRTruck> doublyLinkedList = new DoublyLinkedList<>(this,
		TRTruck.class);

//CONSTRUCTOR
public TRTrucksList() {
	setUpHeadTail();
	setAttributes(new TRAttributes());
}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



@Override
public void setUpHeadTail() {
	setAttributes(new TRAttributes());
	super.setHead(new TRTruck());
	super.setTail(new TRTruck());
	linkHeadTail();
}

//GETTER
public TRTruck getHead() {
	return (TRTruck) super.getHead();
}

//GETTER
public TRTruck getTail() {
	return (TRTruck) super.getTail();
}

@Override
public void setTail(final TRTruck tail) {
	super.setTail(tail);
}

@Override
public void setHead(final TRTruck head) {
	super.setHead(head);
}

//METHOD
//link the head and the tail together
public void linkHeadTail() {
	doublyLinkedList.linkHeadTail();
}//END LINK_HEAD_TAIL *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

@Override
public void setUpHeadTail(final TRTruck head, final TRTruck tail) {
	doublyLinkedList.setUpHeadTail(head, tail);
}

public int getSize() {
	return doublyLinkedList.getSize();
}

@Override
public TRAttributes getAttributes() {
	return (TRAttributes) super.getAttributes();
}

//SETTER
//@Override
public void setAttributes(final TRAttributes attributes) {
	super.setAttributes(attributes);
}

@Override
public TRTruck getFirst() {
	return doublyLinkedList.getFirst();
}

@Override
public boolean insertAfterLastIndex(final TRTruck theObject) {
	return doublyLinkedList.insertAfterLastIndex(theObject);
}

@Override
public TRTruck getLast() {
	return doublyLinkedList.getLast();
}

@Override
public boolean removeLast() {
	return doublyLinkedList.removeLast();
}

@Override
public boolean removeFirst() {
	return doublyLinkedList.removeFirst();
}

@Override
public int getIndexOfObject(final TRTruck findMe) {
	return doublyLinkedList.getIndexOfObject(findMe);
}

@Override
public boolean isValidHeadTail() {
	return doublyLinkedList.isValidHeadTail();
}

@Override
public boolean removeByIndex(final int index) {
	return doublyLinkedList.removeByIndex(index);
}

@Override
public int getSizeWithHeadTail() {
	return doublyLinkedList.getSizeWithHeadTail();
}

public boolean isEmpty() {
	return doublyLinkedList.isEmpty();
}

@Override
public boolean removeByObject(final TRTruck findMe) {
	return doublyLinkedList.removeByObject(findMe);
}

@Override
public boolean insertAfterIndex(final TRTruck insertMe, final int index) {
	return doublyLinkedList.insertAfterIndex(insertMe, index);
}

@Override
public TRTruck getAtIndex(final int index) {
	return doublyLinkedList.getAtIndex(index);
}

@Override
public boolean insertAfterObject(final TRTruck insertMe, final TRTruck insertAfter) {
	return doublyLinkedList.insertAfterObject(insertMe, insertAfter);
}

@Override
public double getDistanceTravelledMiles() {
	return doublyLinkedList.getDistanceTravelledMiles();
}

@Override
public boolean setHeadNext(final TRTruck nextHead) {
	return doublyLinkedList.setHeadNext(nextHead);
}

@Override
public boolean setTailPrevious(final TRTruck previousTail) {
	return doublyLinkedList.setTailPrevious(previousTail);
}

public TRTrucksList(final TRTrucksList copyMe) throws InstantiationException, IllegalAccessException {
	setHead( new TRTruck(copyMe.getHead()));
	setTail( new TRTruck(copyMe.getTail()));
	setAttributes(new TRAttributes(copyMe.getAttributes()));

	TRTruck theCopyMeDepot = copyMe.getHead();
	TRTruck newDepot = getHead();
	while(theCopyMeDepot.getNext() != copyMe.getTail()) {
		theCopyMeDepot = theCopyMeDepot.getNext();
		newDepot.insertAfterCurrent(new TRTruck(theCopyMeDepot));
		newDepot = newDepot.getNext();
	}
}

private TRDay[][] findLowestDemandDays(final TRShipment theShipment){
	final int numberCombinations = theShipment.getNoComb();
	int[] currentCombination;
	final int numberDays = TRProblemInfo.noOfDays;
	final int scheduledDay = 1;
	TRDay[][] bestDayCombinations = new TRDay[numberCombinations][numberDays];


	for(int combinationStepper = 0; combinationStepper < numberCombinations; combinationStepper++){
		currentCombination = theShipment.getCurrentComb()[combinationStepper];

		for(int dayNumberStepper = 0; dayNumberStepper < numberDays; dayNumberStepper++){
			if(currentCombination[dayNumberStepper] == scheduledDay){
				int bestDemand = 999999999;
				TRDay bestDay = null;

				for(int truckStepper = 0; truckStepper < this.getSize(); truckStepper++){
					TRTruck currentTruck = (TRTruck) this.getAtIndex(truckStepper);
					TRDaysList daysList = currentTruck.getSubList();
					TRDay currentDay = (TRDay) daysList.getAtIndex(dayNumberStepper);
					int currentDemand = (int) currentDay.getDemand();

					if(currentDemand < bestDemand){
						bestDemand = currentDemand;
						bestDay = currentDay;
					}
				}
				bestDayCombinations[combinationStepper][dayNumberStepper] = bestDay;
			}
		}
	}
	return bestDayCombinations;
}

private TRDay[][] findLowestDistanceDays(final TRShipment theShipment){
	final int numberCombinations = theShipment.getNoComb();
	int[] currentCombination;
	final int numberDays = TRProblemInfo.noOfDays;
	final int scheduledDay = 1;
	TRDay[][] bestDayCombinations = new TRDay[numberCombinations][numberDays];


	for(int combinationStepper = 0; combinationStepper < numberCombinations; combinationStepper++){
		currentCombination = theShipment.getCurrentComb()[combinationStepper];

		for(int dayNumberStepper = 0; dayNumberStepper < numberDays; dayNumberStepper++){
			if(currentCombination[dayNumberStepper] == scheduledDay){
				int bestDistance = 999999999;
				TRDay bestDay = null;

				for(int truckStepper = 0; truckStepper < this.getSize(); truckStepper++){
					TRTruck currentTruck = (TRTruck) this.getAtIndex(truckStepper);
					TRDaysList daysList = currentTruck.getSubList();
					TRDay currentDay = (TRDay) daysList.getAtIndex(dayNumberStepper);
					int currentDistance = (int) TRProblemInfo.daysLevelCostF.getTotalDistance(currentDay);//.getDemand();

					if(currentDistance < bestDistance){
						bestDistance = currentDistance;
						bestDay = currentDay;
					}
				}
				bestDayCombinations[combinationStepper][dayNumberStepper] = bestDay;
			}
		}
	}
	return bestDayCombinations;
}



private TRDay[] findLowestDemandCombination(final TRShipment theShipment, final TRDay[][] bestDaysForEachCombo){
	int[] demandTotalForEachCombo = new int[bestDaysForEachCombo.length];
	final int numberCombinations = theShipment.getNoComb();
	final int numberDays = TRProblemInfo.noOfDays;

	for(int i = 0; i < demandTotalForEachCombo.length; i++){
		demandTotalForEachCombo[i] = 0;
	}

	for(int combinationStepper = 0; combinationStepper < numberCombinations; combinationStepper++){
		for(int dayStepper = 0; dayStepper < numberDays; dayStepper++){
			TRDay currentDay = bestDaysForEachCombo[combinationStepper][dayStepper];
			if(currentDay != null){
				demandTotalForEachCombo[combinationStepper] += currentDay.getDemand();
			}
		}
	}

	int bestDemand = 999999999;
	TRDay[] bestCombination = null;
	for(int i = 0; i < demandTotalForEachCombo.length; i++){
		if(demandTotalForEachCombo[i] < bestDemand){
			bestDemand = demandTotalForEachCombo[i];
			bestCombination = bestDaysForEachCombo[i];
		}
	}
	return bestCombination;
}

private TRDay[] findLowestDistanceCombination(final TRShipment theShipment, final TRDay[][] bestDaysForEachCombo){
	int[] distanceTotalForEachCombo = new int[bestDaysForEachCombo.length];
	final int numberCombinations = theShipment.getNoComb();
	final int numberDays = TRProblemInfo.noOfDays;

	for(int i = 0; i < distanceTotalForEachCombo.length; i++){
		distanceTotalForEachCombo[i] = 0;
	}

	for(int combinationStepper = 0; combinationStepper < numberCombinations; combinationStepper++){
		for(int dayStepper = 0; dayStepper < numberDays; dayStepper++){
			TRDay currentDay = bestDaysForEachCombo[combinationStepper][dayStepper];
			if(currentDay != null){
				distanceTotalForEachCombo[combinationStepper] += TRProblemInfo.daysLevelCostF.getTotalDistance(currentDay);//
				// .getDemand();
			}
		}
	}

	int bestDistance = 999999999;
	TRDay[] bestCombination = null;
	for(int i = 0; i < distanceTotalForEachCombo.length; i++){
		if(distanceTotalForEachCombo[i] < bestDistance){
			bestDistance = distanceTotalForEachCombo[i];
			bestCombination = bestDaysForEachCombo[i];
		}
	}
	return bestCombination;
}

private int[] createProperVisitationFormat(final TRDay[] bestCombination){
	int[] schedule = new int[bestCombination.length];
	for(int i = 0; i < schedule.length; i++){
		if(bestCombination[i] == null){
			schedule[i] = 0;
		}
		else{
			schedule[i] = 1;
		}
	}
	return schedule;
}

private boolean insertByDemand(final TRShipment theShipment){
	TRDay[][] bestDayCombinations = findLowestDemandDays(theShipment);
	TRDay[] bestCombination = findLowestDemandCombination(theShipment, bestDayCombinations);
	int[] properFormatSchedule = createProperVisitationFormat(bestCombination);
	boolean[] bruteForceDays = new boolean[TRProblemInfo.noOfDays];
	theShipment.setVisitComb(properFormatSchedule);
	boolean isInserted = true;

	for(int i = 0; i < bruteForceDays.length; i++){
		bruteForceDays[i] = false;
	}

	for (int i = 0; i < bestCombination.length; i++) {
		if (bestCombination[i] != null) {
			if(!bestCombination[i].insertShipment(theShipment)){
				bruteForceDays[i] = true;
				isInserted = false;
			}
//				status = status & bestCombination[i].insertShipment(theShipment);
		}
	}
	if(!isInserted){
		if(!insertByDemandBruteForce(theShipment, bruteForceDays)){
			for(int i = 0; i < TRProblemInfo.noOfDays; i++){
				if(bestCombination[i] != null) {
					bestCombination[i].getSubList().removeByShipment(theShipment);
				}
			}
			return false;
		}
		return true;
	}
	return isInserted;
}

private boolean insertByDistance(final TRShipment theShipment){
	TRDay[][] bestDayCombinations = findLowestDistanceDays(theShipment);
	TRDay[] bestCombination = findLowestDistanceCombination(theShipment, bestDayCombinations);
	int[] properFormatSchedule = createProperVisitationFormat(bestCombination);
	boolean[] bruteForceDays = new boolean[TRProblemInfo.noOfDays];
	theShipment.setVisitComb(properFormatSchedule);
	boolean isInserted = true;

	for(int i = 0; i < bruteForceDays.length; i++){
		bruteForceDays[i] = false;
	}

	for (int i = 0; i < bestCombination.length; i++) {
		if (bestCombination[i] != null) {
			if(!bestCombination[i].insertShipment(theShipment)){
				bruteForceDays[i] = true;
				isInserted = false;
			}
//				status = status & bestCombination[i].insertShipment(theShipment);
		}
	}
	if(!isInserted){
		if(!insertByDistanceBruteForce(theShipment, bruteForceDays)){
			for(int i = 0; i < TRProblemInfo.noOfDays; i++){
				if(bestCombination[i] != null) {
					bestCombination[i].getSubList().removeByShipment(theShipment);
				}
			}
			return false;
		}
		return true;
	}
	return isInserted;
}

private boolean insertByDemandBruteForce(final TRShipment theShipment, final boolean[] bruteForceDays){
	boolean isInserted = true;
	for(int i = 0; i < bruteForceDays.length; i++){
		if(bruteForceDays[i]){
			TRTruck currentTruck = this.getFirst();
			while(currentTruck != this.getTail() && !isInserted){
				TRDaysList daysList = currentTruck.getSubList();
				TRDay currentDay = daysList.getAtIndex(i);
				if(!currentDay.insertShipment(theShipment)){
					isInserted = false;
				}
				currentTruck = currentTruck.getNext();
			}
		}
	}

	return isInserted;
}

private boolean insertByDistanceBruteForce(final TRShipment theShipment, final boolean[] bruteForceDays){
	boolean isInserted = true;
	for(int i = 0; i < bruteForceDays.length; i++){
		if(bruteForceDays[i]){
			TRTruck currentTruck = this.getFirst();
			while(currentTruck != this.getTail() && !isInserted){
				TRDaysList daysList = currentTruck.getSubList();
				TRDay currentDay = daysList.getAtIndex(i);
				if(!currentDay.insertShipment(theShipment)){
					isInserted = false;
				}
				currentTruck = currentTruck.getNext();
			}
		}
	}

	return isInserted;
}

private double calculateRelativeDemand(final TRDay[] bestCombination){
	double summationValue = 0;
	double maxSummationValue = 0;
	for(int i = 0; i < bestCombination.length; i++){
		summationValue += TRProblemInfo.daysLLLevelCostF.getTotalDemand(bestCombination[i]);
		maxSummationValue = bestCombination[i].getMaxDemand();
	}
	final double relativeValue = summationValue / maxSummationValue;
	return relativeValue;
}

private double calculateRelativeDistance(final TRDay[] bestCombination){
	double summationValue = 0;
	double maxSummationValue = 0;
	for(int i = 0; i < bestCombination.length; i++){
		summationValue += TRProblemInfo.daysLevelCostF.getTotalDistance(bestCombination[i]);
		maxSummationValue += bestCombination[i].getMaxDistance();
	}
	final double relativeValue = summationValue / maxSummationValue;
	return relativeValue;
}





private TRDay[][] findLowestDemandDistanceDays(final TRShipment theShipment){
	final int numberCombinations = theShipment.getNoComb();
	int[] currentCombination;
	final int numberDays = TRProblemInfo.noOfDays;
	final int scheduledDay = 1;
	TRDay[][] bestDayCombinations = new TRDay[numberCombinations][numberDays];


	for(int combinationStepper = 0; combinationStepper < numberCombinations; combinationStepper++){
		currentCombination = theShipment.getCurrentComb()[combinationStepper];

		for(int dayNumberStepper = 0; dayNumberStepper < numberDays; dayNumberStepper++){
			if(currentCombination[dayNumberStepper] == scheduledDay){
//				int bestDemand = 999999999;
				double bestRelative = 999999999;
				TRDay bestDay = null;

				for(int truckStepper = 0; truckStepper < this.getSize(); truckStepper++){
					TRTruck currentTruck = (TRTruck) this.getAtIndex(truckStepper);
					TRDaysList daysList = currentTruck.getSubList();
					TRDay currentDay = (TRDay) daysList.getAtIndex(dayNumberStepper);
					double currentDemand = (double) TRProblemInfo.daysLevelCostF.getTotalDemand(currentDay);
					double currentDistance = TRProblemInfo.daysLevelCostF.getTotalDistance(currentDay);

					double maxDemand = currentDay.getMaxDemand();
					double maxDistance = currentDay.getMaxDistance();

					double relativeDemand = currentDemand / maxDemand;
					double relativeDistance = currentDistance / maxDistance;

					double relativeSum = relativeDemand + relativeDistance;

					if(relativeSum < bestRelative){
						bestRelative = relativeSum;
						bestDay = currentDay;
					}
				}
				bestDayCombinations[combinationStepper][dayNumberStepper] = bestDay;
			}
		}
	}
	return bestDayCombinations;
}


private TRDay[] findLowestDemandDistanceCombination(final TRShipment theShipment, final TRDay[][] bestDaysForEachCombo){
	int[] demandTotalForEachCombo = new int[bestDaysForEachCombo.length];
	double[] totalForEachCombo = new double[bestDaysForEachCombo.length];
	double[] relativeTotalForEachCombo = new double[bestDaysForEachCombo.length];
	final int numberCombinations = theShipment.getNoComb();
	final int numberDays = TRProblemInfo.noOfDays;

	for(int i = 0; i < relativeTotalForEachCombo.length; i++){
		relativeTotalForEachCombo[i] = 0;
	}

	for(int combinationStepper = 0; combinationStepper < numberCombinations; combinationStepper++){
		for(int dayStepper = 0; dayStepper < numberDays; dayStepper++){
			TRDay currentDay = bestDaysForEachCombo[combinationStepper][dayStepper];
			if(currentDay != null){
				double distance = TRProblemInfo.daysLevelCostF.getTotalDistance(currentDay);
				double demand = TRProblemInfo.daysLevelCostF.getTotalDemand(currentDay);

				double maxDistance = currentDay.getMaxDistance();
				double maxDemand = currentDay.getMaxDemand();

				totalForEachCombo[combinationStepper] += (maxDemand + maxDistance);
			}
		}
	}

	int bestDemand = 999999999;
	TRDay[] bestCombination = null;
	for(int i = 0; i < demandTotalForEachCombo.length; i++){
		if(demandTotalForEachCombo[i] < bestDemand){
			bestDemand = demandTotalForEachCombo[i];
			bestCombination = bestDaysForEachCombo[i];
		}
	}
	return bestCombination;
}




public boolean insertShipment(final TRShipment theShipment) {
//	TRDay[][] bestDayCombinations = findLowestDemandDistanceDays(theShipment);



//	TRDay[][] bestDayCombinations = findLowestDemandDays(theShipment);
//	TRDay[] bestCombination = findLowestDemandCombination(theShipment, bestDayCombinations);
//	final double relativeDemand = calculateRelativeDemand(bestCombination);
//
//
//	bestDayCombinations = findLowestDistanceDays(theShipment);
//	bestCombination = findLowestDistanceCombination(theShipment, bestDayCombinations);
//	final double relativeDistance = calculateRelativeDistance(bestCombination);





//
	if(!insertByDemand(theShipment)){
		return insertByDistance(theShipment);
	}
	return true;
//





}

//METHOD
//used by the gui to show problem information
public String getSolutionString() {
	return "Trucks Used = " + TRProblemInfo.noOfVehs + " | " + this.getAttributes().toDetailedString();
}

}
