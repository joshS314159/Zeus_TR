package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;


import edu.sru.thangiah.zeus.core.Day;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRCoordinates;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.*;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.ObjectInList;


public class TRDay
        extends Day
        implements java.io.Serializable, Cloneable,
        ObjectInListInterface<TRDay>, ObjectInListCoreInterface<TRDay>, ContainsSubListInterface<TRNodesList, TRNode> {


    //VARIABLES
    private TRAttributes attributes = new TRAttributes();            //takes precedence over the base class attributes type
    //private TRDay        previous;    //takes precedence over the base class previous and next type
//private TRDay        next;
//    private TRNodesList nodesSubList = new TRNodesList();
    private TRCoordinates homeDepotCoordinates;
//private int           dayNumber;


    private int numberOfNodes;
    private int numberOfTrucks;
    private int maxDistance;
    private int maxDemand;


    private int daysServicedOver;

    private ObjectInList<TRDay> objectInList = new ObjectInList<>(this);
    private ContainsSubList<TRNodesList, TRNode> subList = new ContainsSubList<>();

/// /private int


    //CONSTUCTOR
//this method creates a new day based
//on passed attributes (usually more useful than the above
//constructor)
//	public PVRPDay(int nodes, int numberTrucks, int planningPeriod, float maxDistance, float maxDemand,
//	               double depX, double depY, int dayNumber) {
//
//
//		this.attributes = new PVRPAttributes();
//
//		this.nodes = nodes;
//		this.numberTrucks = numberTrucks;
//		this.planningPeriod = planningPeriod;
//		this.maxDistance = maxDistance;
//		this.maxDemand = maxDemand;
//		this.dayNumber = dayNumber;
//
//		setNodesLinkedList(new PVRPNodesLinkedList(depX, depY));
//
//
//		setAttributes(new PVRPAttributes());
//
//	}//CONSTRUCTOR ENDS
//// HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    public TRDay(final TRDay copyMe) throws IllegalAccessException, InstantiationException {
        setAttributes(new TRAttributes(copyMe.getAttributes()));
        setHomeDepotCoordinates(new TRCoordinates(copyMe.getHomeDepotCoordinates()));
        setSubList(new TRNodesList(copyMe.getSubList()));
        setHomeDepotCoordinates(new TRCoordinates(copyMe.getHomeDepotCoordinates()));
        setDayOfWeek(copyMe.getDayOfWeek());
    }

    public TRAttributes getAttributes() {
        return this.attributes;
    }

    public TRCoordinates getHomeDepotCoordinates() {
        return homeDepotCoordinates;
    }

    public void setHomeDepotCoordinates(final TRCoordinates homeDepotCoordinates) {
        this.homeDepotCoordinates = homeDepotCoordinates;
    }

    @Override
    public TRNodesList getSubList() {
        return subList.getSubList();
    }

    @Override
    public void setSubList(TRNodesList setMe) {
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

    public void setAttributes(final TRAttributes attributes) {
        this.attributes = attributes;
    }

    //USED FOR PVRP FILE VERSION ONLY
    public TRDay(final TRNodesList nodes, final int numberOfTrucks, final int daysServicedOver, final float maxDistance, final float maxDemand, final double xCoordinate, final double yCoordinate, final int dayNumber, final boolean isCartesian) {
        subList.setSubList(nodes);
//        this.nodesSubList = nodes;
        this.numberOfTrucks = numberOfTrucks;
        this.daysServicedOver = daysServicedOver;
        this.maxDistance = (int) maxDistance;
        this.maxDemand = (int) maxDemand;

        TRCoordinates tempCoordinates = new TRCoordinates(xCoordinate, yCoordinate);
        tempCoordinates.setIsCartesian(isCartesian);
        this.homeDepotCoordinates = tempCoordinates;
        setDayNumber(dayNumber);
//	this.dayNumber = dayNumber;


//        subList = new ContainsSubList<>(TRNodesList.class);
    }

    public boolean setDayNumber(final int dayNumber) {
        if (dayNumber >= 0) {
            setIndex(dayNumber);
//		this.dayNumber = dayNumber;
            return true;
        }
        return false;
    }

    public TRDay() { setAttributes(new TRAttributes());
        setSubList(new TRNodesList());
    }

    public TRDay(final TRCoordinates homeDepotCoordinates) {
        setAttributes(new TRAttributes());
        setSubList(new TRNodesList(homeDepotCoordinates));
    }

    public TRDay(final TRCoordinates coordinates, final int dayNumber) {
        setHomeDepotCoordinates(coordinates);
        setDayNumber(dayNumber);
        setSubList(new TRNodesList());
    }

    public int getDaysServicedOver() { return daysServicedOver; }

    public void setDaysServicedOver(final int daysServicedOver) { this.daysServicedOver = daysServicedOver; }

    public int getNumberOfNodes() { return numberOfNodes; }

    public void setNumberOfNodes(int numberOfNodes) { this.numberOfNodes = numberOfNodes; }

    public int getNumberOfTrucks() { return numberOfTrucks; }

    public void setNumberOfTrucks(int numberOfTrucks) { this.numberOfTrucks = numberOfTrucks; }

    public int getMaxDistance() { return maxDistance; }

    public void setMaxDistance(int maxDistance) { this.maxDistance = maxDistance; }

    public int getMaxDemand() { return maxDemand; }

    public void setMaxDemand(int maxDemand) {
        this.maxDemand = maxDemand;
    }

    public String toString() {
        String s = "#" + getDayNumber();

        return s;
    }

    public int getDayNumber() {
        return getIndex();
    }

    public TRDay getNext() {
        return (TRDay) super.getNext();
    }

    @Override
    public void setNext(final TRDay next) {
        super.setNext(next);
    }

    @Override
    public TRDay getPrevious() {
        return (TRDay) super.getPrev();
    }

    @Override
    public void setPrevious(final TRDay previous) {
        super.setPrev(previous);
    }

    public boolean insertShipment(final TRShipment theShipment) {
        boolean status = false;

        return this.getSubList().insertShipment(theShipment);
    }//END INSERT_SHIPMENT *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public boolean insertAfterCurrent(TRDay insertMe) {
        return objectInList.insertAfterCurrent(insertMe);
    }

    @Override
    public void linkAsHeadTail(TRDay linkTwo) {
        objectInList.linkAsHeadTail(linkTwo);

    }

    @Override
    public boolean removeThisObject() {
        return objectInList.removeThisObject();
    }
}




