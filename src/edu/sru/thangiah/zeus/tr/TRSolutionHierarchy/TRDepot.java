package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;


import edu.sru.thangiah.zeus.core.Attributes;
import edu.sru.thangiah.zeus.core.Depot;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRCoordinates;

//import the parent class


/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 *
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class TRDepot
		extends Depot
		implements java.io.Serializable, Cloneable, ObjectInList {


//VARIABLES
//private float maxDistance = -1;
//private float maxDemand   = -1;

	//private TRDepot next;
//private TRDepot previous;
	private TRTrucksList trucksSubList = new TRTrucksList();
//private TRAttributes  attributes = new TRAttributes();


	private TRCoordinates coordinates;
	private double maxDistance;
	private double maxDemand;
	private int nodeNumber;

	public TRDepot(final TRDepot copyMe) {
		setAttributes(new TRAttributes(copyMe.getAttributes()));
		setCoordinates(new TRCoordinates(copyMe.getCoordinates()));
		setSubList(new TRTrucksList(copyMe.getSubList()));
	}

	public TRDepot(final TRCoordinates coordinates) {
		this.coordinates = coordinates;
		setSubList(new TRTrucksList());
		setAttributes(new TRAttributes());
	}

	public TRDepot() {
		setAttributes(new TRAttributes());
	}

	public double getMaxDemand() {
		return maxDemand;
	}

	public void setMaxDemand(final double maxDemand) {
		this.maxDemand = maxDemand;
	}

	public double getMaxDistance() {
		return maxDistance;
	}
//private int depotNumber;

	public void setMaxDistance(final double maxDistance) {
		this.maxDistance = maxDistance;
	}

	public int getNodeNumber() {
		return nodeNumber;
	}

	public void setNodeNumber(final int nodeNumber) {
		this.nodeNumber = nodeNumber;
	}

	public TRCoordinates getCoordinates() {
		return this.coordinates;
	}

	private void setCoordinates(final TRCoordinates coordinates) {
		this.coordinates = coordinates;
	}

	public String toString() {
		return "#" + 1 + " (" + this.getCoordinates().getLongitude() + ", " + this.getCoordinates().getLatitude() + ")" + this.getAttributes();
	}

	@Override
	public TRTrucksList getSubList() {
		return this.trucksSubList;
	}

	@Override
	public void setSubList(final DoublyLinkedList trucksSubList) {
		this.trucksSubList = (TRTrucksList) trucksSubList;
	}


//public TRDepot(final TRCoordinates coordinates) {
//		super(edu.sru.thangiah.zeus.trGeneric.TRSolutionHierarchy.TRTrucksList.class);

//	this.setCoordinates(coordinates);
//	setAttributes(new TRAttributes());
//	trucksSubList = new TRTrucksList(coordinates);
//}

	public boolean insertShipment(final TRShipment theShipment) {
		boolean status = false;

		return this.getSubList().insertShipment(theShipment);
	}//END INSERT_SHIPMENT *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	public TRDepot getNext() {
		return (TRDepot) super.getNext();
	}

	@Override
	public void setNext(final ObjectInList next) {
		super.setNext((Depot) next);
	}


//	@Override
//	public boolean createSubList() {
//		return false;
//	}

	public TRAttributes getAttributes() {
		return (TRAttributes) super.getAttributes();
	}

	@Override
	public void setAttributes(final TRAttributes attributes) {
		super.setAttributes(attributes);
	}

	@Override
	public boolean insertAfterCurrent(final ObjectInList insertMe) {
		if (this.getNext() != null) {
			(insertMe).setPrevious(this);
			(insertMe).setNext(this.getNext());

			(this).setNext(insertMe);
			(insertMe).getNext().setPrevious(insertMe);
			return true;
		}
		return false;
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
		if (this.getNext() != null || this.getPrevious() != null) {

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
		return (TRDepot) super.getPrev();
	}


	@Override
	public void setPrevious(final ObjectInList previous) {
		super.setPrev((Depot) previous);
	}


	@Override
	public boolean isSubListEmpty() {
		if (getSubList() == null || getSubList().isEmpty()) {
			return true;
		}
		return false;
	}


	@Override
	public double getDistanceTravelledMiles() {
		return 0;
	}


}
