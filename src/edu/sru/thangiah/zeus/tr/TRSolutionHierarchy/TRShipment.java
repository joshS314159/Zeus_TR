package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;

//import the parent class


import edu.sru.thangiah.zeus.core.Shipment;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRCoordinates;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;


public class TRShipment
		extends Shipment
		implements java.io.Serializable, Cloneable, ObjectInList {

final boolean isVisitSunday = false;
//VARIABLES
private TRShipment previous;
private TRShipment next;
private int        customerIndex;
private boolean canBeRouted = false;
    private TRAttributes  attributes = new TRAttributes();
private TRCoordinates homeDepotCoordinates;
private TRCoordinates coordinates;
private int           numberOfBins;
private boolean       isTipCart;
private boolean[] daysVisited = new boolean[TRProblemInfo.NUMBER_DAYS_SERVICED];
private int     buildingType;
private int     numberOfNearbyClasses;
private int     numberOfNearbyFoods;
private boolean isPickupOrder;
private int     nodeNumber;
private boolean isAssigned;
private int     demand;
private int     visitFrequency = 0;
private String  pickupPointName;
private int     requiredPreviousPickupPoint;
    private TRDelayType delayType = new TRDelayType();



//CONSTRUCTOR
public TRShipment(final int customerIndex, final TRCoordinates coordinates, int demand) {
	setIndex(customerIndex);
	setCoordinates(coordinates);
	setDemand(demand);
	setAttributes(new TRAttributes());
	setHomeDepotCoordinates(new TRCoordinates());
	//	setFrequency();
	setFrequency(0);
	daysVisited = new boolean[TRProblemInfo.NUMBER_DAYS_SERVICED];
	for(int x = 0; x < TRProblemInfo.NUMBER_DAYS_SERVICED; x++) {
		daysVisited[x] = false;
	}

}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




public TRShipment(final TRShipment copyMe) {
	setAttributes(new TRAttributes(copyMe.getAttributes()));
	setHomeDepotCoordinates(new TRCoordinates(copyMe.getHomeDepotCoordinates()));
	setCoordinates(new TRCoordinates(copyMe.getCoordinates()));
	setCanBeRouted(copyMe.getCanBeRouted());
	setCustomerIndex(copyMe.getCustomerIndex());
	setSubList(new TRShipmentsList(null));
	setFrequency(copyMe.getFrequency());
}

//public TRShipment(final TRCoordinates homeDepotCoordinates){
//	setAttributes(new TRAttributes());
//	setHomeDepotCoordinates(homeDepotCoordinates);
//}


    public void setDelayType(final TRDelayType delayType){
        this.delayType = delayType;
    }

    public TRDelayType getDelayType(){
        return this.delayType;
    }


public TRCoordinates getHomeDepotCoordinates() {
	return homeDepotCoordinates;
}




public void setHomeDepotCoordinates(final TRCoordinates homeDepotCoordinates) {
	this.homeDepotCoordinates = homeDepotCoordinates;
}




public TRCoordinates getCoordinates() {
	return this.coordinates;
}




public void setCoordinates(final TRCoordinates coordinates) {
	this.coordinates = coordinates;
}




public boolean getCanBeRouted() {
	return canBeRouted;
}




//SETTER
public boolean setCustomerIndex(final int index) {
	if(index >= 0) {
		this.customerIndex = index;
		return true;
	}
	return false;
}




//GETTER
public int getCustomerIndex() {
	return this.customerIndex;
}




public int getFrequency() {
	int counter = 0;
	for(int x = 0; x < daysVisited.length; x++) {
		if(daysVisited[x] == true) {
			counter++;
		}
	}
	return counter;
}




public boolean getIsAssigned() {
	return isAssigned;
}




public void setIsAssigned(final boolean isAssigned) {
	this.isAssigned = isAssigned;
}




public TRShipment getNext() {
	return this.next;
}




@Override
public void setNext(final ObjectInList next) {
	this.next = (TRShipment) next;
}




@Override
public void linkAsHeadTail(final ObjectInList linkTwo) {
	this.setNext(linkTwo);
	(linkTwo).setPrevious(this);
	this.setPrevious(null);    //nothing comes before the head
	(linkTwo).setNext(null);        //nothing comes after the tail
}




@Override
public boolean removeThisObject() {
	if(this.getNext() != null || this.getPrevious() != null) {

		(this.getPrevious()).setNext(this.getNext());
		(this.getNext()).setPrevious(this.getPrevious());

		this.setPrevious(null);
		this.setNext((ObjectInList) null);
		return true;
	}
	return false;
}




@Override
public ObjectInList getPrevious() {
	return this.previous;
}




@Override
public void setPrevious(final ObjectInList previous) {
	this.previous = (TRShipment) previous;
}




@Override
public boolean isSubListEmpty() {
	return true;
}




@Override
public double getDistanceTravelledMiles() {
	return 0;
}




public int getDemand() {
	return demand;
}




public String getPickupPointName() {
	return pickupPointName;
}




public void setPickupPointName(final String setPickupPointName) {
	this.pickupPointName = setPickupPointName;
}




public void setCanBeRouted(boolean canBeRouted) {
	this.canBeRouted = canBeRouted;
}




public TRShipment(final TRCoordinates coordinates) {
	//	this.coordinates = coordinates;
	setCoordinates(coordinates);
}




public TRShipment() {
	setAttributes(new TRAttributes());
	setCoordinates(new TRCoordinates());
	setFrequency(0);
}




@Override
public TRShipmentsList getSubList() {
	return null;
}




@Override
public void setSubList(final DoublyLinkedList subList) {
	//null
}




public TRAttributes getAttributes() {
	return this.attributes;
}




@Override
public void setAttributes(final TRAttributes attributes) {
	this.attributes = attributes;
}




@Override
public boolean insertAfterCurrent(final ObjectInList insertMe) {
	if(this.getNext() != null) {
		(insertMe).setPrevious(this);
		(insertMe).setNext(this.getNext());

		(this).setNext(insertMe);
		(insertMe).getNext().setPrevious(insertMe);
		return true;
	}
	return false;
}




public int getVisitFrequency() {
	return visitFrequency;
}


//public int getIndex() {
//	return index;
//}




public boolean setVisitFrequency(final int visitFrequency) {
	if(visitFrequency >= 0) {
		this.visitFrequency = visitFrequency;
		return true;
	}
	return false;
}




public int getNumberOfBins() {
	return numberOfBins;
}




public void setNumberOfBins(final int numberOfBins) {
	if(numberOfBins >= 0) {
		this.numberOfBins = numberOfBins;
	}
}




public boolean[] getDaysVisited() {
	return daysVisited;
}


//public void setDaysVisited(boolean[] daysVisited) {
//	this.daysVisited = daysVisited;
//}




public int getBuildingType() {
	return buildingType;
}




public void setBuildingType(final int buildingType) {
	if(buildingType >= 0) {
		this.buildingType = buildingType;
	}
}




public int getNumberOfNearbyClasses() {
	return numberOfNearbyClasses;
}




public void setNumberOfNearbyClasses(final int numberOfNearbyClasses) {
	if(numberOfNearbyClasses >= 0) {
		this.numberOfNearbyClasses = numberOfNearbyClasses;
	}
}




public int getNumberOfNearbyFoods() {
	return numberOfNearbyFoods;
}




public void setNumberOfNearbyFoods(final int numberOfNearbyFoods) {
	if(numberOfNearbyFoods >= 0) {
		this.numberOfNearbyFoods = numberOfNearbyFoods;
	}
}




public boolean getIsPreviousPickupOrder() {
	return isPickupOrder;
}




public void setIsPreviousPickupOrder(final boolean isPickupOrder) {
	this.isPickupOrder = isPickupOrder;
}




public void setRequiredPreviousPickupPoint(final int pickupOrder) {
	if(pickupOrder >= 0 && pickupOrder <= TRProblemInfo.noOfShips) {
		isPickupOrder = true;
		requiredPreviousPickupPoint = pickupOrder;
	}
}




public boolean setNodeNumber(final int nodeNumber) {
	if(nodeNumber > 0) {
		this.nodeNumber = nodeNumber;
        this.setIndex(nodeNumber);
		return true;
	}
	return false;
	//	this.index;
	//}
}




public int getNodeNumber() {
	return this.nodeNumber;
}




public boolean getIsTipCart() {
	return isTipCart;
}




public void setIsTipCart(final boolean isTipCard) {
	this.isTipCart = isTipCard;
}




public void setSingleDayVisitation(String visitationDaySymbol) {
	if(visitationDaySymbol.equals("M")) {
		//		isVisitMonday = true;
		daysVisited[0] = true;
		//		getFrequency();
		setFrequency(getFrequency() + 1);
	}
	else if(visitationDaySymbol.equals("T")) {
		//		isVisitTuesday = true;
		daysVisited[1] = true;
		setFrequency(getFrequency() + 1);
	}
	else if(visitationDaySymbol.equals("W")) {
		//		isVisitWednesday = true;
		daysVisited[2] = true;
		setFrequency(getFrequency() + 1);
	}
	else if(visitationDaySymbol.equals("R")) {
		//		isVisitThursday = true;
		daysVisited[3] = true;
		setFrequency(getFrequency() + 1);
	}
	else if(visitationDaySymbol.equals("F")) {
		//		isVisitFriday = true;
		daysVisited[4] = true;
		setFrequency(getFrequency() + 1);
	}
	else if(visitationDaySymbol.equals("Sat")) {
		//		isVisitSaturday = true;
		daysVisited[5] = true;
		setFrequency(getFrequency() + 1);
	}
	else if(visitationDaySymbol.equals("None")) {
	}
	else {
		System.out.println("CAN'T FIND VALID DAY");

	}

}




public void setPickupOrder(final String pickupOrder) {
	if(pickupOrder.equals("None")) {
		isPickupOrder = false;
	}
	else {
	}
}

}
