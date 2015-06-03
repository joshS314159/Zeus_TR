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
    //VARIABLES
//private TRShipment previous;
//private TRShipment next;
//private int        customerIndex;
    private boolean canBeRouted = true;
    private TRAttributes attributes = new TRAttributes();
    private TRCoordinates homeDepotCoordinates;
    private TRCoordinates coordinates;
    //private int           numberOfBins;
    private boolean isTipCart;
    private boolean[] daysVisited = new boolean[TRProblemInfo.noOfDays];
    private int buildingType;
    private int numberOfNearbyClasses;
    private int numberOfNearbyFoods;
    private boolean isPickupOrder;
    private int nodeNumber;
    private boolean isAssigned;
    private int[] daysCurrentlyScheduled;
    private boolean isShipmentFullyScheduled = false;
    //private int     demand;
//private int     visitFrequency = 0;
//private String  pickupPointName;
    private int requiredPreviousPickupPoint;
    private TRDelayType delayType = new TRDelayType();
    private ArrayList<String> daysToVisitStop = new ArrayList<String>();
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
//        setSubList(new TRShipmentsList(null));
        setFrequency(copyMe.getFrequency());
        setDemand((int) copyMe.getDemand());
        setNumberOfBins(copyMe.getNumberOfBins());
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

//public TRShipment(final TRCoordinates homeDepotCoordinates){
//	setAttributes(new TRAttributes());
//	setHomeDepotCoordinates(homeDepotCoordinates);
//}
//	@Override

    public void setCurrentComb(final int[][] currentCombination, final int numberCombinations) {
        int combinationTemp[][] = new int[numberCombinations][TRProblemInfo.noOfDays];


        for (int i = 0; i < numberCombinations; i++) {
            for (int j = 0; j < TRProblemInfo.noOfDays; j++) {
                combinationTemp[i][j] = currentCombination[i][j];
            }
        }
        super.setCurrentComb(currentCombination);
        super.setNoComb(numberCombinations);
    }

    public void chooseRandomVisitCombination() {
        Random rand = new Random();
        int min = 0;
        int max = this.getNoComb() - 1;

        int randomNum = rand.nextInt((max - min) + 1) + min;

        int[] theRandomVisitation = this.getCurrentComb()[randomNum];
        setVisitComb(theRandomVisitation);
        daysCurrentlyScheduled = new int[theRandomVisitation.length];
        for(int i = 0; i < daysCurrentlyScheduled.length; i++){
            daysCurrentlyScheduled[i] = 0;
        }


        for (int i = 0; i < daysVisited.length; i++) {
            if (theRandomVisitation[i] == 1) {
                daysVisited[i] = true;
            } else {
                daysVisited[i] = false;
            }
        }
    }

    public boolean isShipmentFullyScheduled(){
        for(int i = 0; i < daysCurrentlyScheduled.length; i++){
            if(daysCurrentlyScheduled[i] != this.getVisitComb()[i]){
                return false;
            }
        }
        return true;
    }

    public boolean isDayAlreadyScheduled(final int day){
        if(daysCurrentlyScheduled[day] == 1){
            return true;
        }
        return false;
    }

    public boolean isDayAllowedToBeScheduled(final int day){
        if(this.getVisitComb()[day] == 1){
            return true;
        }
        return false;
    }

    public boolean setDayAsScheduled(final int day){
        if(!isDayAlreadyScheduled(day)){
            daysCurrentlyScheduled[day] = 1;
            return true;
        }
        return false;
    }

    public boolean chooseVisitCombination(final int combinationIndex) {
        if (combinationIndex >= 0 && combinationIndex < this.getNoComb()) {
            int[] visitationSchedule = this.getCurrentComb()[combinationIndex];

            for (int i = 0; i < visitationSchedule.length; i++) {
                if (visitationSchedule[i] == 1) {
                    daysVisited[i] = true;
                } else {
                    daysVisited[i] = false;
                }
            }
            return true;
        }
        return false;
    }

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


    public int getFrequency() {
        int counter = 0;
        for (int x = 0; x < daysVisited.length; x++) {
            if (daysVisited[x] == true) {
                counter++;
            }
        }
        return counter;
    }

    public int countFrequency(){
        int counter = 0;
        for (int x = 0; x < daysVisited.length; x++) {
            if (daysVisited[x] == true) {
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
        return (TRShipment) super.getNext();
    }

    @Override
    public void setNext(TRShipment next) {
//        objectInList.set
//        objectInList.set
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


//public int getIndex() {
//	return index;
//}


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


//public void setDaysVisited(boolean[] daysVisited) {
//	this.daysVisited = daysVisited;
//}


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
        //	this.index;
        //}
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
            //		isVisitMonday = true;
            daysVisited[0] = true;
            //		getFrequency();
//            setFrequency(countFrequency() + 1);
        } else if (visitationDaySymbol.equals("T")) {
            //		isVisitTuesday = true;
            daysVisited[1] = true;
//            setFrequency(countFrequency() + 1);
        } else if (visitationDaySymbol.equals("W")) {
            //		isVisitWednesday = true;
            daysVisited[2] = true;
//            setFrequency(countFrequency() + 1);
        } else if (visitationDaySymbol.equals("R")) {
            //		isVisitThursday = true;
            daysVisited[3] = true;
//            setFrequency(countFrequency() + 1);
        } else if (visitationDaySymbol.equals("F")) {
            //		isVisitFriday = true;
            daysVisited[4] = true;
//            setFrequency(countFrequency() + 1);
        } else if (visitationDaySymbol.equals("Sat")) {
            //		isVisitSaturday = true;
            daysVisited[5] = true;
//            setFrequency(countFrequency() + 1);
        } else if (visitationDaySymbol.equals("None")) {
        } else {
            System.out.println("CAN'T FIND VALID DAY");

        }
    }

    public void addDayToVisitationList(String dayCode) {

        if (!dayCode.equals("None")) {
            this.daysToVisitStop.add(dayCode);
            this.setFrequency(this.countFrequency() + 1);
        }

    }

    //if we are sticking to bound days, this method is to adjust for a 5 day schedule from the original 6 day one
    public void setVisitationSchedule() {
        //if we are running a 5 day schedule, saturday is never visited it. remove it from daysToVisitStop
        if (TRProblemInfo.noOfDays != TRProblemInfo.noOfDays && !TRProblemInfo.areDaysFlexible) {
            if (daysToVisitStop.contains("Sat")) {
                daysToVisitStop.remove("Sat");
                //if a shipment requires a visit all 6 days, we can only visit 5 days in a 5 day schedule, so decrease its frequency accordingly
                if (this.getFrequency() == TRProblemInfo.noOfDays)
                    this.setFrequency(this.getFrequency() - 1);
                    //if we do not need to visit the stop all days, but saturday is still on the schedule, check to see
                    //if friday is on the schedule. If not, schedule it.
                else if (!daysToVisitStop.contains("F")) {
                    daysToVisitStop.add(("F"));
                }
                //if friday is on the schedule, the only other schedule possible from our data is MWFS, so
                //TR are both open. Choose one at random and schedule it.
                else {
                    Random random = new Random();
                    int randInt = random.nextInt();
                    if (randInt == 0)
                        daysToVisitStop.add("T");
                    else
                        daysToVisitStop.add("R");
                }
            }
        }

        //take this new visitation schedule and set our daysVisited array using this new data
        for (String dayCode : daysToVisitStop) {
            setSingleDayVisitation(dayCode);
        }
    }

    //method used when creating an entirely new schedule for a shipment
    public void makeCustomVisitationSchedule() {
        //variable dictionary
        int weekLength = TRProblemInfo.noOfDays;
        int[] schedule = new int[weekLength];
        int period = weekLength / this.getVisitFrequency();            //a shipment requires a number of visits equal to its frequency. taking the length of our schedule and dividing it by the
        //frequency gives us a period during which at least 1 visitation is required.
        int excessDays = weekLength % this.getVisitFrequency();            //because division gives us a whole number, we need to capture the number of days that the shipment is not to be visited on.
        //for example, if a shipment has a freq of 4 on a 6 day schedule, its period would be 1, with an excessDay count of 2.
        int daysScheduled = 0;                                            //number of days scheduled so far
        boolean isNewScheduleMade = false;
        Random random = new Random();

        //if this shipment needs visited everyday, go ahead and schedule it. we are done here
        if (this.getVisitFrequency() >= weekLength) {
            for (int i = 0; i < weekLength; i++) {
                this.setSingleDayVisitation(daysToVisitStop.get(i));
            }
            return;
        }

        //having a shipment that does not need visited everyday is where the fun starts
        while (!isNewScheduleMade) {

            //check at the beginning of the loop to see if all days have been scheduled. if so, skip everything and exit the loop.
            if (daysScheduled == weekLength)
                isNewScheduleMade = true;

                //there are 3 possible cases for a shipment having a period of 1. it will either have and excess day count of either 0, 1, or 2
            else if (period == 1) {
                //may not actually need this if statement. having a period of 1 with not excess days is a full schedule, which was taken care of
                //in the first part of this method... @TODO
                if (excessDays >= 1) {
                    //this array will hold the days which will be skipped
                    int[] gapDays = new int[excessDays];
                    for (int i = 0; i < excessDays; i++) {
                        //get a random day of the week to skip
                        int rand = random.nextInt(weekLength);
                        //if this is the first gap day, go ahead and schedule it
                        if (i == 0)
                            gapDays[0] = rand;
                            //if this isn't the first gap day, we need to check to make sure that day is not already skipped
                        else {
                            //if the random day is already skipped, go 2 days forward and skip that day
                            if (gapDays[0] == rand)
                                gapDays[i] = (rand + 2) % weekLength;
                                //if not skipped, skip it
                            else
                                gapDays[i] = rand;
                        }
                    }
                    //check to make sure the days are in order so when scheduling we don't skip over a day that is to be skipped
                    if (excessDays == 2) {
                        if (gapDays[0] > gapDays[1]) {
                            int temp = gapDays[0];
                            gapDays[0] = gapDays[1];
                            gapDays[1] = temp;
                        }
                    }

                    //var keeps track of how many days we have skipped so far;
                    int gapDayCnt = 0;
                    boolean allGapsUsed = false;

                    //make the schedule now
                    for (int i = 0; i < weekLength; i++) {
                        boolean gap = false;
                        //as long as we still have gaps to schedule, check if this current day is a gap day
                        if (!allGapsUsed) {
                            if (i == gapDays[gapDayCnt]) {
                                schedule[i] = 0;
                                gapDayCnt++;
                                gap = true;
                                if (gapDayCnt == excessDays)
                                    allGapsUsed = true;
                            }
                        }
                        //if we have already scheduled a gap this loop, skip
                        if (!gap)
                            schedule[i] = 1;

                        daysScheduled++;
                    }
                }
            }

            //all shipments with a period != 1
            else {
                //pick a random day during a period to schedule
                int dayToSchedule = random.nextInt(period);
                //we are looping through a period here. once all periods have been scheduled, our while loop condition is met and we quit executing
                for (int i = 0; i < period; i++) {
                    //as long as we have a day scheduled, we need to check that the beginning of one period is not scheduled right after the end of another
                    //that's a little confusing. If we have a period of 2, and the first period is scheduled as [0,1], we need to make sure not to schedule [1,0] for the next
                    if (daysScheduled > 0) {
                        if (schedule[daysScheduled - 1] == 1 && dayToSchedule == i)
                            dayToSchedule++;
                    }
                    //schedule a day to be visited or not depending of what our random day is
                    if (i != dayToSchedule)
                        schedule[daysScheduled] = 0;
                    else
                        schedule[daysScheduled] = 1;

                    daysScheduled++;
                }
                //if we have extra days to skip, skip one now
                if (excessDays != 0) {
                    schedule[daysScheduled] = 0;
                    daysScheduled++;
                    excessDays--;
                }
            }

        }

        //clear any schedule already in the shipment
        daysToVisitStop.clear();
        //add our new schedule to the shipment
        for (int i = 0; i < schedule.length; i++) {
            switch (i) {
                case 0:
                    if (schedule[i] == 1)
                        daysToVisitStop.add("M");
                    break;
                case 1:
                    if (schedule[i] == 1)
                        daysToVisitStop.add("T");
                    break;
                case 2:
                    if (schedule[i] == 1)
                        daysToVisitStop.add("W");
                    break;
                case 3:
                    if (schedule[i] == 1)
                        daysToVisitStop.add("R");
                    break;
                case 4:
                    if (schedule[i] == 1)
                        daysToVisitStop.add("F");
                    break;
                case 5:
                    if (schedule[i] == 1)
                        daysToVisitStop.add("Sat");
                    break;
            }
        }

        //set the new schedule
        for (int i = 0; i < daysToVisitStop.size(); i++) {
            this.setSingleDayVisitation(daysToVisitStop.get(i));
        }

    }


    public void setPickupOrder(final String pickupOrder) {
        if (pickupOrder.equals("None")) {
            isPickupOrder = false;
        } else {
        }
    }

//    public createVisiatio

}
