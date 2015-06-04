package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;

//import the parent class


import edu.sru.thangiah.zeus.core.Nodes;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRCoordinates;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.*;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.ObjectInList;


public class TRNode
        extends Nodes
        implements java.io.Serializable, Cloneable,// ObjectInList
        ObjectInListInterface<TRNode>, ObjectInListCoreInterface<TRNode>, ContainsSubObjectInterface<TRShipment> {


    private TRAttributes attributes = new TRAttributes();
    private TRCoordinates homeDepotCoordinates;

    private ObjectInList<TRNode> objectInList = new ObjectInList<>(this);
    private ContainsSubObject<TRShipment> subObject = new ContainsSubObject<TRShipment>();


    private int visitationHour = -1;
    private int visitationMinute = -1;


    public TRNode(final TRNode copyMe) {
        setShipment(new TRShipment(copyMe.getShipment()));
        setAttributes(new TRAttributes(copyMe.getAttributes()));
        setHomeDepotCoordinates(new TRCoordinates(copyMe.getHomeDepotCoordinates()));
        setHomeDepotCoordinates(new TRCoordinates(copyMe.getHomeDepotCoordinates()));
        setSubobject(new TRShipment());
    }


    public TRNode(final TRCoordinates homeDepotCoordinates) {
        setAttributes(new TRAttributes());
        setShipment(new TRShipment());

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





    public TRAttributes getAttributes() {
        return this.attributes;
    }


//    @Override
    public void setAttributes(final TRAttributes attributes) {
        this.attributes = attributes;
    }





    public TRNode getNext() {
        return (TRNode) super.getNext();
    }

    @Override
    public TRNode getPrevious() {
        return (TRNode) super.getPrev();
    }


    @Override
    public boolean insertAfterCurrent(TRNode insertMe) {
        return objectInList.insertAfterCurrent(insertMe);
    }

    @Override
    public void linkAsHeadTail(TRNode linkTwo) {
        objectInList.linkAsHeadTail(linkTwo);
    }

    @Override
    public boolean removeThisObject() {
        return objectInList.removeThisObject();
    }


    @Override
    public void setNext(TRNode next) {
        super.setNext(next);
    }

    @Override
    public void setPrevious(TRNode previous) {
        super.setPrev(previous);
    }




    @Override
    public TRShipment getSubObject() {
        return subObject.getSubObject();
    }

    @Override
    public void setSubobject(TRShipment setMe) {
        subObject.setSubobject(setMe);
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
