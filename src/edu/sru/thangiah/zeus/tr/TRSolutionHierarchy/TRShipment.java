package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;

//import the parent class


import edu.sru.thangiah.zeus.core.Shipment;
import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRCoordinates;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.ObjectInListCoreInterface;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.ObjectInListInterface;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.ObjectInList;

import java.util.ArrayList;
import java.util.Random;


public class TRShipment
        extends Shipment
        implements java.io.Serializable, Cloneable,// ObjectInList
        ObjectInListInterface<TRShipment>, ObjectInListCoreInterface<TRShipment>

{


    final boolean isVisitSunday = false;
    private ObjectInList<TRShipment> objectInList = new ObjectInList<TRShipment>(this);
    private boolean canBeRouted = true;
    private TRAttributes attributes = new TRAttributes();
    private TRCoordinates homeDepotCoordinates;
    private TRCoordinates coordinates;
    private boolean isTipCart;
    private boolean[] daysVisited = new boolean[TRProblemInfo.noOfDays];
    private int buildingType;
    private int numberOfNearbyClasses;
    private int numberOfNearbyFoods;
    private boolean isPickupOrder;
    private boolean isAssigned;

    private int requiredPreviousPickupPoint;
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
        daysVisited = new boolean[TRProblemInfo.noOfDays];
        for (int x = 0; x < TRProblemInfo.noOfDays; x++) {
            daysVisited[x] = false;
        }

    }//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public TRShipment(final TRShipment copyMe) {
        setAttributes(new TRAttributes(copyMe.getAttributes()));
        setHomeDepotCoordinates(new TRCoordinates(copyMe.getHomeDepotCoordinates()));
        setCoordinates(new TRCoordinates(copyMe.getCoordinates()));
        setCanBeRouted(copyMe.getCanBeRouted());
        setCustomerIndex(copyMe.getCustomerIndex());
        setFrequency(copyMe.getFrequency());
        setDemand((int) copyMe.getDemand());
        setNumberOfBins(copyMe.getNumberOfBins());
    }
    public TRShipment(final TRCoordinates coordinates) {
        setCoordinates(coordinates);
    }
    public TRShipment() {
        setAttributes(new TRAttributes());
        setCoordinates(new TRCoordinates());
        setFrequency(0);
    }

    @Override
    public boolean insertAfterCurrent(TRShipment insertMe) {
        return objectInList.insertAfterCurrent(insertMe);
    }

    @Override
    public void linkAsHeadTail(TRShipment linkTwo) {
        objectInList.linkAsHeadTail(linkTwo);
    }

    @Override
    public boolean removeThisObject() {
        return objectInList.removeThisObject();
    }


    public void setCurrentComb(final int[][] currentCombination, final int numberCombinations) {
        super.setCurrentComb(currentCombination);
        super.setNoComb(numberCombinations);
    }
//
    public TRDelayType getDelayType() {
        return this.delayType;
    }

    public void setDelayType(final TRDelayType delayType) {
        this.delayType = delayType;
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

    public void setCanBeRouted(boolean canBeRouted) {
        this.canBeRouted = canBeRouted;
    }

    //SETTER
    public boolean setCustomerIndex(final int index) {
        if (index >= 0) {
            super.setIndex(index);
            super.setCustId(index);
            return true;
        }
        return false;
    }

    //GETTER
    public int getCustomerIndex() {
        return super.getIndex();
    }


    public boolean getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(final boolean isAssigned) {
        this.isAssigned = isAssigned;
    }

    public TRShipment getNext() {
        return (TRShipment) super.getNext();
    }

    @Override
    public void setNext(TRShipment next) {

        super.setNext(next);
    }

    @Override
    public TRShipment getPrevious() {
        return (TRShipment) super.getPrev();
    }

    @Override
    public void setPrevious(TRShipment previous) {
        super.setPrev(previous);
    }

    public double getDistanceTravelledMiles() {
        return 0;
    }

    public String getPickupPointName() {
        return pickupPointName;
    }

    public void setPickupPointName(final String setPickupPointName) {
        this.pickupPointName = setPickupPointName;
    }




    public TRAttributes getAttributes() {
        return this.attributes;
    }


//    @Override
    public void setAttributes(final TRAttributes attributes) {
        this.attributes = attributes;
    }



    public int getVisitFrequency() {
        return super.getFrequency();
    }



    public boolean setVisitFrequency(final int visitFrequency) {
        if (visitFrequency >= 0) {
            super.setFrequency(visitFrequency);
            return true;
        }
        return false;
    }


    public int getNumberOfBins() {
        return super.getDemand();
    }


    public void setNumberOfBins(final int numberOfBins) {
        if (numberOfBins >= 0) {
            super.setDemand(numberOfBins);
            this.setDemand(numberOfBins);
        }
    }


    public boolean[] getDaysVisited() {
        return daysVisited;
    }




    public int getBuildingType() {
        return buildingType;
    }


    public void setBuildingType(final int buildingType) {
        if (buildingType >= 0) {
            this.buildingType = buildingType;
        }
    }


    public int getNumberOfNearbyClasses() {
        return numberOfNearbyClasses;
    }


    public void setNumberOfNearbyClasses(final int numberOfNearbyClasses) {
        if (numberOfNearbyClasses >= 0) {
            this.numberOfNearbyClasses = numberOfNearbyClasses;
        }
    }


    public int getNumberOfNearbyFoods() {
        return numberOfNearbyFoods;
    }


    public void setNumberOfNearbyFoods(final int numberOfNearbyFoods) {
        if (numberOfNearbyFoods >= 0) {
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
        if (pickupOrder >= 0 && pickupOrder <= TRProblemInfo.noOfShips) {
            isPickupOrder = true;
            requiredPreviousPickupPoint = pickupOrder;
        }
    }


    public boolean setNodeNumber(final int nodeNumber) {
        if (nodeNumber > 0) {
//		this.nodeNumber = nodeNumber;
            super.setIndex(nodeNumber);
            super.setCustId(nodeNumber);
            return true;
        }
        return false;

    }


    public int getNodeNumber() {
        return super.getIndex();
    }


    public boolean getIsTipCart() {
        return isTipCart;
    }


    public void setIsTipCart(final boolean isTipCard) {
        this.isTipCart = isTipCard;
    }


    public void setSingleDayVisitation(String visitationDaySymbol) {
        if (visitationDaySymbol.equals("M")) {
            daysVisited[0] = true;
        } else if (visitationDaySymbol.equals("T")) {
            daysVisited[1] = true;
        } else if (visitationDaySymbol.equals("W")) {
            daysVisited[2] = true;
        } else if (visitationDaySymbol.equals("R")) {
            daysVisited[3] = true;
        } else if (visitationDaySymbol.equals("F")) {
            daysVisited[4] = true;
        } else if (visitationDaySymbol.equals("Sat")) {
            daysVisited[5] = true;
        } else if (visitationDaySymbol.equals("None")) {
        } else {
            System.out.println("CAN'T FIND VALID DAY");

        }
    }



    public void setPickupOrder(final String pickupOrder) {
        if (pickupOrder.equals("None")) {
            isPickupOrder = false;
        } else {
        }
    }


}
