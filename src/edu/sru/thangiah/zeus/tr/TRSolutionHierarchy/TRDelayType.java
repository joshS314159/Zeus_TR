package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;

import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRCoordinates;

/**
 * Created by jks1010 on 3/21/2015.
 */
public class TRDelayType implements ObjectInList {
    private TRDelayType next;
    private TRDelayType previous;
//    private TRAttributes attributes = new TRAttributes();
    private String delayTypeName;
    private int delayTimeInMinutes;

//private int depotNumber;


    public TRDelayType(final String delayTypeName, final int delayTimeInMinutes) {
        this.setDelayTypeName(delayTypeName);
        this.setDelayTimeInMinutes(delayTimeInMinutes);
    }


    public TRDelayType(final TRDelayType copyMe) {
       setNext(null);
        setPrevious(null);
        setDelayTypeName(copyMe.getDelayTypeName());
        setDelayTimeInMinutes(copyMe.getDelayTimeInMinutes());
    }

    public TRDelayType() {
    }


    public boolean setDelayTypeName(final String delayTypeName){
        if(delayTypeName.length() > 0){
            this.delayTypeName = delayTypeName;
            return true;
        }
        return false;
    }


    public boolean setDelayTimeInMinutes(final int delayTimeInMinutes){
        if(delayTimeInMinutes > 0){
            this.delayTimeInMinutes = delayTimeInMinutes;
            return true;
        }
        return false;
    }


    public String getDelayTypeName(){
        return this.delayTypeName;
    }

    public int getDelayTimeInMinutes(){
        return delayTimeInMinutes;
    }





    @Override
    public TRTrucksList getSubList() {
        return null;
    }




    @Override
    public void setSubList(final DoublyLinkedList trucksSubList) {   }








    public boolean insertShipment(final TRShipment theShipment) {
        return false;
    }//END INSERT_SHIPMENT *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




    public TRDelayType getNext() {
        return this.next;
    }




    public TRAttributes getAttributes() {
        return null;
    }


//	@Override
//	public boolean createSubList() {
//		return false;
//	}




    @Override
    public void setAttributes(final TRAttributes attributes) {

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




    @Override
    public void setNext(final ObjectInList next) {
        this.next = (TRDelayType) next;
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
        this.previous = (TRDelayType) previous;
    }




    @Override
    public boolean isSubListEmpty() {
        if(getSubList() == null || getSubList().isEmpty()) {
            return true;
        }
        return false;
    }




    @Override
    public double getDistanceTravelledMiles() {
        return 0;
    }
    
    
    
    
}
