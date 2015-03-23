package edu.sru.thangiah.zeus.tr;

import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.DoublyLinkedList;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.ObjectInList;

/**
 * Created by joshuasarver on 3/19/15.
 */
public class TRPenalty  implements ObjectInList  {

    private TRPenalty next;



    private TRPenalty previous;
    private String buildingType;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private boolean[] daysOfWeek = new boolean[5];
    private int penaltyInMinutes;



    private boolean isPenaltyInEffect = false;



    public TRPenalty(final int startHour, final int startMinute, final int endHour, final int endMinute, final int penaltyInMinutes){
        setStartHour(startHour);
        setEndHour(endHour);
        setEndHour(endHour);
        setEndMinute(endMinute);

        this.penaltyInMinutes = penaltyInMinutes;

        for(int x = 0; x < daysOfWeek.length; x++){
            daysOfWeek[x] = false;
        }
    }

    public TRPenalty(){}


    public TRPenalty(final TRPenalty copyMe) {
        setNext(null);
        setPrevious(null);
        setBuildingType(copyMe.getBuildingType());
        setStartHour(copyMe.getStartHour());
        setStartMinute(copyMe.getStartMinute());
        setEndHour(copyMe.getEndHour());
        setEndMinute(copyMe.getEndMinute());
        setDaysOfWeek(copyMe.getDaysOfWeek());
        setPenaltyInMinutes(copyMe.getPenaltyInMinutes());
    }

    public boolean isPenaltyInEffect() {
        return isPenaltyInEffect;
    }

    public void setPenaltyInEffect(final boolean isPenaltyInEffect) {
        this.isPenaltyInEffect = isPenaltyInEffect;
    }

    public void setMondayPenalty(){
        daysOfWeek[0] = true;
    }

    public void setTuesdayPenalty(){
        daysOfWeek[1] = true;
    }

    public void setWednesdayPenalty() {
        daysOfWeek[2] = true;
    }

    public void setThursdayPenalty(){
        daysOfWeek[3] = true;
    }

    public void setFridayPenalty(){
        daysOfWeek[4] = true;
    }


    public boolean setStartHour(final int startHour){
        if(startHour >= 0 && startHour <= 23){
            this.startHour = startHour;
            return true;
        }
        return false;
    }

    public int getPenaltyInMinutes(){
        return this.penaltyInMinutes;
    }

    public boolean isPenaltyApplicable(final int startRouteHour, final int startRouteMinute, final int endRouteHour, final int endRouteMinute){
        if(startRouteHour >= 0 && startRouteHour <= 23 && endRouteHour >= 0 && endRouteHour <= 23) {
            if (startRouteMinute >= 0 && startRouteMinute <= 59 && endRouteMinute >= 0 && endRouteMinute <= 59) {
                final float startRouteTime = hourMinuteToDecimal(startRouteHour, startRouteMinute);
                final float endRouteTime = hourMinuteToDecimal(endRouteHour, endRouteMinute);
                final float startPenaltyTime = hourMinuteToDecimal(this.startHour, this.startMinute);
                final float endPenaltyTime = hourMinuteToDecimal(this.endHour, this.endMinute);
                if (startRouteTime < endRouteTime) {
                    if ((startPenaltyTime >= startRouteTime && startPenaltyTime <= endRouteTime) || (endPenaltyTime >= startRouteTime && endPenaltyTime <= endRouteTime)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }



    private float hourMinuteToDecimal(final int hour, final int minute){
        float decimalTime = (float) (hour + (minute / 100.00));
        return decimalTime;
    }

    public boolean setStartMinute(final int startMinute){
        if(startMinute >= 0 && startMinute <= 59){
            this.startMinute = startMinute;
            return true;
        }
        return false;
    }


    public boolean setEndHour(final int endHour){
        if(endHour >= 0 && endHour <= 23 && endHour >= startHour){
            this.endHour = endHour;
            return true;
        }
        return false;
    }

    public void setNext(TRPenalty next) {
        this.next = next;
    }

    public void setPrevious(TRPenalty previous) {
        this.previous = previous;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public boolean[] getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(boolean[] daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }


    public boolean setEndMinute(final int endMinute){
        if(endMinute >= 0 && endMinute <= 59){
            if(endHour == startHour && endMinute > startMinute || endHour > startHour) {
                this.endMinute = endMinute;
                return true;
            }
        }
        return false;
    }


    public void setBuildingType(final String buildingType){
        this.buildingType = buildingType;
    }

    public String getBuildingType(){
        return this.buildingType;
    }

    public void setStartTime(final String startTime){
        //
    }

    public void setEndTime(final String endTime){

    }


    public void setPenaltyInMinutes(final int penaltyInMinutes){
        this.penaltyInMinutes = penaltyInMinutes;
    }






    public TRPenalty getNext() {
        return this.next;
    }


    @Override
    public DoublyLinkedList getSubList() {
        return null;
    }

    @Override
    public void setSubList(DoublyLinkedList subList) {

    }

    public TRAttributes getAttributes() {
        return null;
    }



    @Override
    public void setAttributes(final TRAttributes attributes) {    }




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




    @Override
    public void setNext(final ObjectInList next) {
        this.next = (TRPenalty) next;
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
        this.previous = (TRPenalty) previous;
    }




    @Override
    public boolean isSubListEmpty() {
        return true;
    }




    @Override
    public double getDistanceTravelledMiles() {
        return -999;
    }

//    public void setDaysOfWeek


}

