package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;


import edu.sru.thangiah.zeus.core.Depot;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRCoordinates;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.*;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.ObjectInList;

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
        implements java.io.Serializable, Cloneable,
        ObjectInListInterface<TRDepot>, ObjectInListCoreInterface<TRDepot>, ContainsSubListInterface<TRTrucksList, TRTruck> {


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
//	private int nodeNumber;


    private ObjectInList<TRDepot> objectInList = new ObjectInList<>(this);
    private ContainsSubList<TRTrucksList, TRTruck> subList = new ContainsSubList<TRTrucksList, TRTruck>();


    public TRDepot(final TRDepot copyMe) throws IllegalAccessException, InstantiationException {
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
        setSubList(new TRTrucksList());
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
        return super.getDepotNum();
    }

    public void setNodeNumber(final int nodeNumber) {
        super.setDepotNum(nodeNumber);
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


    public boolean insertShipment(final TRShipment theShipment) {
        boolean status = false;

        return this.getSubList().insertShipment(theShipment);
    }//END INSERT_SHIPMENT *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    public TRDepot getNext() {
        return (TRDepot) super.getNext();
    }

    @Override
    public TRDepot getPrevious() {
        return (TRDepot) super.getPrev();
    }

    @Override
    public void setNext(TRDepot next) {
        super.setNext(next);
    }

    public TRAttributes getAttributes() {
        return (TRAttributes) super.getAttributes();
    }

    public void setAttributes(final TRAttributes attributes) {
        super.setAttributes(attributes);
    }

    @Override
    public boolean insertAfterCurrent(final TRDepot insertMe) {
        return objectInList.insertAfterCurrent(insertMe);
    }




    @Override
    public void setPrevious(TRDepot previous) {
        super.setPrev(previous);
    }

    @Override
    public TRTrucksList getSubList() {
        return subList.getSubList();
    }

    @Override
    public void setSubList(TRTrucksList setMe) {
        subList.setSubList(setMe);
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


    @Override
    public void linkAsHeadTail(TRDepot linkTwo) {
        objectInList.linkAsHeadTail(linkTwo);
    }

    @Override
    public boolean removeThisObject() {
        return objectInList.removeThisObject();
    }
}
