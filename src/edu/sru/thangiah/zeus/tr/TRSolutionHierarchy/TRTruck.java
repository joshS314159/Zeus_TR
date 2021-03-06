package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;


import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.core.Truck;
import edu.sru.thangiah.zeus.pvrp.PVRPAttributes;
import edu.sru.thangiah.zeus.pvrp.PVRPDayLinkedList;
import edu.sru.thangiah.zeus.pvrp.PVRPTruckType;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRCoordinates;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.*;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.ObjectInList;
import edu.sru.thangiah.zeus.tr.TRTruckType;

//import the parent class


/**
 * PVRP (Periodic Vehicle Routing)
 * Routes vehicles to various nodes over a given number of days
 * <p>
 * Version 1.0
 * <p>
 * 10/17/14
 * <p>
 * AUTHORS
 * Aaron Rockburn and Joshua Sarver
 * Slippery Rock University of Pa
 * CPSC 464 - Fall 2014
 * <p>
 * COPYLEFT
 * Attribution-ShareAlike 4.0 International
 * <p>
 * BASED ON
 * Dr. Sam R. Thangiah's work at Slippery Rock University of Pa
 * A Heuristic for the Periodic Vehicle Routing Problem by M. Gaudioso, G. Paletta
 * Chou
 * The Periodic Vehicle Routing Problem by S. Coene, A. Arnout and F. Spieksma
 * A Variable Neighborhood Search Heuristic for Periodic Routing Problems by Vera C. Hemmelmayr, Karl F. Doerner§,
 * Richard F. Hartl
 * <p>
 * Methods are generally sorted by breadth-first order
 */

public class TRTruck
		extends Truck
		implements java.io.Serializable, Cloneable,
//		ObjectInList
		ObjectInListInterface<TRTruck>, ObjectInListCoreInterface<TRTruck>, ContainsSubListInterface<TRDaysList, TRDay>
{

	private boolean isTruckOnRoad = false;
	private TRCoordinates homeDepotCoordinates;

	private ObjectInList<TRTruck> objectInList = new ObjectInList<>(this);
	private ContainsSubList<TRDaysList, TRDay> subList = new ContainsSubList<TRDaysList, TRDay>();


	public TRTruck(final TRTruck copyMe) throws IllegalAccessException, InstantiationException {
		setIsTruckOnRoad(copyMe.getIsTruckOnRoad());
		setTruckType(new TRTruckType(copyMe.getTruckType()));
		setAttributes(new TRAttributes(copyMe.getAttributes()));
		setSubList(new TRDaysList(copyMe.getSubList()));
		setHomeDepotCoordinates(new TRCoordinates(copyMe.getHomeDepotCoordinates()));

	}


	public TRTruck(final TRCoordinates homeDepotCoordinates) {
		setAttributes(new TRAttributes());
		setHomeDepotCoordinates(homeDepotCoordinates);
		super.setTruckType(new TRTruckType());
		setSubList(new TRDaysList());
	}


	//CONSTRUCTOR
	public TRTruck() {
		setAttributes(new TRAttributes());
		super.setTruckType(new TRTruckType());
		setSubList(new TRDaysList());
	}

	public boolean getIsTruckOnRoad() {
		return this.isTruckOnRoad;
	}

	public void setIsTruckOnRoad(final boolean isTruckOnRoad) {
		this.isTruckOnRoad = isTruckOnRoad;
	}

	@Override
	public TRDaysList getSubList() {
		return subList.getSubList();
	}

	@Override
	public void setSubList(TRDaysList setMe) {
		subList.setSubList(setMe);
	}

	public TRCoordinates getHomeDepotCoordinates() {
		return homeDepotCoordinates;
	}

	public void setHomeDepotCoordinates(final TRCoordinates homeDepotCoordinates) {
		this.homeDepotCoordinates = homeDepotCoordinates;
	}

	@Override
	public TRTruckType getTruckType() {
		return (TRTruckType) super.getTruckType();
	}

	public void setTruckType(final TRTruckType truckType) {
		super.setTruckType(truckType);
	}

	public String toString() {
		String s = "#" + 1 + " "/* + " Time" + this.truckType.getMaxCapacity() + " Max Dist =" + this.truckType.getMaxDistance()*/;
		return s;
	}
//



	public TRAttributes getAttributes() {
		return (TRAttributes) super.getAttributes();
	}


	public void setAttributes(final TRAttributes attributes) {
		super.setAttributes(attributes);
	}


	public TRTruck getNext() {
		return (TRTruck) super.getNext();
	}

@Override
public TRTruck getPrevious() {
	return (TRTruck) super.getPrev();
}


@Override
	public boolean insertAfterCurrent(TRTruck insertMe) {
		return objectInList.insertAfterCurrent(insertMe);
	}

	@Override
	public void linkAsHeadTail(TRTruck linkTwo) {
		objectInList.linkAsHeadTail(linkTwo);
	}

	@Override
	public boolean removeThisObject() {
		return objectInList.removeThisObject();
	}


	@Override
	public void setNext(TRTruck next) {
		super.setNext(next);
	}

	@Override
	public void setPrevious(TRTruck previous) {
		super.setPrev(previous);
	}



	@Override
	public boolean isSubListEmpty() {
		return subList.isSubListEmpty();
	}

	@Override
	public int getSubListSize() {
		return subList.getSubListSize();
	}


	@Override
	public double getDistanceTravelledMiles() {
		return 0;
	}


}
