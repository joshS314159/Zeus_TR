package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;

//import the parent class


import edu.sru.thangiah.zeus.core.Nodes;
import edu.sru.thangiah.zeus.tr.TR;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRCoordinates;


public class TRNode
		extends Nodes
		implements java.io.Serializable, Cloneable, ObjectInList {

	//	private TRShipment theShipment = new TRShipment();
	//private TRNode        next;
//private TRNode        previous;
	private TRAttributes attributes = new TRAttributes();
	private TRCoordinates homeDepotCoordinates;


	private int visitationHour = -1;
	private int visitationMinute = -1;


	public TRNode(final TRNode copyMe) {
		setShipment(new TRShipment(copyMe.getShipment()));
		setAttributes(new TRAttributes(copyMe.getAttributes()));
		setHomeDepotCoordinates(new TRCoordinates(copyMe.getHomeDepotCoordinates()));
		//	setSubList(new TRNodesList(null));
		setHomeDepotCoordinates(new TRCoordinates(copyMe.getHomeDepotCoordinates()));
//		setAttributes(new TRAttributes());
	}


	public TRNode(final TRCoordinates homeDepotCoordinates) {
		setAttributes(new TRAttributes());
		setShipment(new TRShipment());
		//	set
		//	setHomeDepotCoordinates(homeDepotCoordinates);
	}


	public TRNode(final TRShipment theShipment) {
		setShipment(theShipment);
	}


//final TRNode(final TR)

	public TRNode() {
		setAttributes(new TRAttributes());
		setShipment(new TRShipment());
	}

	public int getIndex() {
		return this.getShipment().getIndex();
	}

	public TRCoordinates getHomeDepotCoordinates() {
		return homeDepotCoordinates;
	}

	public void setHomeDepotCoordinates(final TRCoordinates homeDepotCoordinates) {
		this.homeDepotCoordinates = homeDepotCoordinates;
	}

	public int getVisitationHour() {
		return visitationHour;
	}

	public void setVisitationHour(final int visitationHour) {
		if (visitationHour >= 0 && visitationHour <= 23) {
			this.visitationHour = visitationHour;
		}
	}

	public int getVisitationMinute() {
		return visitationMinute;
	}

	public void setVisitationMinute(final int visitationMinute) {
		if (visitationMinute >= 0 && visitationMinute <= 59) {
			this.visitationMinute = visitationMinute;
		}
	}

	public TRCoordinates getCoordinates() {
		return this.getShipment().getCoordinates();
	}


	@Override
	public TRNodesList getSubList() {
		return null;
	}


	@Override
	public void setSubList(DoublyLinkedList subList) {
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
		if (this.getNext() != null) {
			(insertMe).setPrevious(this);
			(insertMe).setNext(this.getNext());

			(this).setNext(insertMe);
			(insertMe).getNext().setPrevious(insertMe);
			return true;
		}
		return false;
	}


	public TRNode getNext() {
		return (TRNode) super.getNext();
	}


	@Override
	public void setNext(final ObjectInList next) {
		super.setNext((TRNode) next);
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
		return (TRNode) super.getPrev();
	}


	@Override
	public void setPrevious(final ObjectInList previous) {
		super.setPrev((TRNode) previous);
	}


	@Override
	public boolean isSubListEmpty() {
		return true;
	}


	@Override
	public double getDistanceTravelledMiles() {
		return 0;
	}


	//@Override
	public TRShipment getShipment() {
		return (TRShipment) super.getShipment();
	}


	public void setShipment(final TRShipment theShipment) {
		this.theShipment = theShipment;
	}


}
