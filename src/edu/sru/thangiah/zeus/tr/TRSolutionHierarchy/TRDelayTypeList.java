package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;

import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;

/**
 * Created by jks1010 on 3/21/2015.
 */
public class TRDelayTypeList implements DoublyLinkedList {

    //VARIABLES
    private TRDelayType      head;     //takes precedence over the base class head and tail
    private TRDelayType      tail;
//    private TRAttributes attributes = new TRAttributes();     //takes precedence over the base class attributes


    //CONSTRUCTOR
    public TRDelayTypeList() {
        setUpHeadTail();
//        setAttributes(new TRAttributes());
    }//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    public TRDelayTypeList(final TRDelayTypeList copyMeList) {
        setUpHeadTail(new TRDelayType(copyMeList.getHead()), new TRDelayType(copyMeList.getTail()));

        TRDelayType copyMe = copyMeList.getHead();
        TRDelayType newDelay = getHead();
        while(copyMe.getNext() != copyMeList.getLast()) {
            copyMe = copyMe.getNext();
            newDelay.insertAfterCurrent(new TRDelayType(copyMe));
            newDelay = newDelay.getNext();
        }
    }



    @Override
    public void setUpHeadTail() {
        this.head = new TRDelayType();
        this.tail = new TRDelayType();
        //	setHead((ObjectInList) new TRDelayType());
        //	setTail((ObjectInList) new TRDelayType());
        linkHeadTail();
    }




    //GETTER
    @Override
    public TRDelayType getHead() {
        return this.head;
    }




    //GETTER
    @Override
    public TRDelayType getTail() {
        return this.tail;
    }




    //METHOD
//link the head and the tail together
    public void linkHeadTail() {
        getHead().linkAsHeadTail(getTail());
    }//END LINK_HEAD_TAIL *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




    @Override
    public TRAttributes getAttributes() {
        return null;
    }






    //SETTER
    @Override
    public void setAttributes(final TRAttributes attributes) {

    }




    @Override
    public boolean setHead(final ObjectInList head) {
        //	return getHead().replaceThisWith((TRDelayType) head);
        if(head != null) {
            head.setPrevious(getTail().getPrevious());
            head.getPrevious().setNext(head);
            getHead().setPrevious(null);
            getHead().setNext((ObjectInList) null);
            this.head = (TRDelayType) head;
            return true;
        }
        return false;
    }




    @Override
    public TRDelayType getFirst() {
        if(isEmpty() || !isValidHeadTail()) {
            System.out.println("ERROR: getFirst() is null/invalid");
            return null;
        }
        return getHead().getNext();
    }




    @Override
    public boolean insertAfterLastIndex(final ObjectInList theObject) {
        if(!isValidHeadTail()) {
            return false;
        }

        if(isEmpty()) {
            return getHead().insertAfterCurrent(theObject);
        }
        //otherwise we already got stuff in here
        return getLast().insertAfterCurrent(theObject);
    }




    @Override
    public TRDelayType getLast() {
        if(isEmpty() || !isValidHeadTail()) {
            return null;
        }
        return (TRDelayType) getTail().getPrevious();
    }




    @Override
    public boolean removeLast() {
        if(!isEmpty() && isValidHeadTail()) {
            return getTail().getPrevious().removeThisObject();
        }
        return false;
    }




    @Override
    public boolean removeFirst() {
        if(!isEmpty() && isValidHeadTail()) {
            return getHead().getNext().removeThisObject();
        }
        return false;
    }




    @Override
    public int getIndexOfObject(final ObjectInList findMe) {
        int counter = -1;
        TRDelayType theDay = this.getHead();

        if(!isEmpty() && isValidHeadTail()) {
            while(theDay != findMe) {
                theDay = theDay.getNext();
                counter++;
                if(theDay == tail) {
                    return -1;
                }
            }
            return counter;
        }
        return -1;
    }




    @Override
    public boolean setTail(final ObjectInList tail) {
        //	return getTail().replaceThisWith((TRDelayType) tail);
        if(tail != null) {
            tail.setPrevious(getTail().getPrevious());
            tail.getPrevious().setNext(tail);
            getTail().setPrevious(null);
            getTail().setNext((ObjectInList) null);
            this.tail = (TRDelayType) tail;
            return true;
        }
        return false;

    }




    @Override
    public boolean isValidHeadTail() {
        if(getHead() == null || getHead().getNext() == null || getHead().getPrevious() != null ||
                getTail().getPrevious() == null || getTail() == null || getTail().getNext() != null) {
            return false;
        }
        return true;
    }




    //METHOD
//inserts a shipment
    @Override
    public boolean insertShipment(final TRShipment theShipment) {
        return false;
    }



    @Override
    public boolean removeByIndex(final int index) {
        int counter = -1;
        TRDelayType theDay = this.getHead();

        while(index >= 0 && index < getSize() && isValidHeadTail()) {
            theDay = theDay.getNext();
            counter++;
            if(counter == index) {
                return theDay.removeThisObject();
            }
        }
        return false;
    }




    public int getSize() {
        TRDelayType theDelay = getHead();
        int sizeCounter = 0;

        if(!isValidHeadTail()) {
            return -1;
        }

        while(theDelay.getNext() != getTail()) {
            theDelay = theDelay.getNext();
            sizeCounter++;
        }

        return sizeCounter;
    }




    @Override
    public int getSizeWithHeadTail() {
        if(isValidHeadTail()) {
            return getSize() + 2;
        }
        return -1;
    }




    public boolean isEmpty() {
        if(getSize() == 0) {
            return true;
        }
        return false;
    }




    @Override
    public boolean removeByObject(final ObjectInList findMe) {
        TRDelayType theDelay = getHead();
        while(theDelay.getNext() != getTail() && isValidHeadTail()) {
            theDelay = theDelay.getNext();
            if(theDelay == findMe) {
                theDelay.removeThisObject();
                return true;
            }
        }
        return false;
    }




    @Override
    public boolean insertAfterIndex(final ObjectInList insertMe, final int index) {
        int counter = -1;
        TRDelayType theDelay = getHead();

        while(index >= 0 && index < getSize() && !isEmpty() && isValidHeadTail()) {
            theDelay = theDelay.getNext();
            counter++;
            if(counter == index) {
                theDelay.insertAfterCurrent(insertMe);
                return true;
            }
        }
        return false;
    }




    @Override
    public ObjectInList getAtIndex(final int index) {
        int counter = -1;
        TRDelayType theDelay = getHead();

        while(index >= 0 && index < getSize() && !isEmpty() && isValidHeadTail()) {
            theDelay = theDelay.getNext();
            counter++;
            if(counter == index) {
                return theDelay;
            }
        }
        return null;
    }


    public ObjectInList getByDelayName(final String delayName) {
        int counter = -1;
        TRDelayType theDelay = getFirst();

        while(theDelay != this.getTail()){
            if(theDelay.getDelayTypeName().equals(delayName)){
                return theDelay;
            }
            theDelay = theDelay.getNext();
        }
        return null;
    }



    @Override
    public boolean insertAfterObject(final ObjectInList insertMe, final ObjectInList insertAfter) {
        TRDelayType theDelay = head;
        while(!isEmpty() && isValidHeadTail()) {
            theDelay = theDelay.getNext();
            if(theDelay == insertAfter) {
                return insertAfter.insertAfterCurrent(insertMe);
                //			return true;
            }
        }
        return false;
    }




    @Override
    public double getDistanceTravelledMiles() {
        return 0;
    }




    @Override
    public void setUpHeadTail(final ObjectInList head, final ObjectInList tail) {
        this.head = new TRDelayType();
        this.tail = new TRDelayType();
        linkHeadTail();
    }







    @Override
    public boolean setHeadNext(final ObjectInList nextHead) {
        if(this.getHead().getNext() == this.getTail()){
            return false;
        }
        this.getHead().setNext((ObjectInList) nextHead);
        return true;

    }

    @Override
    public boolean setTailPrevious(final ObjectInList nextTail){
        if(this.getTail().getPrevious() == this.getHead()){
            return false;
        }
        this.getTail().setPrevious((ObjectInList) nextTail);
        return true;

    }


}
