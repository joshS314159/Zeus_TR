package edu.sru.thangiah.zeus.tr;

import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.DoublyLinkedList;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.ObjectInList;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRShipment;

/**
 * Created by joshuasarver on 3/19/15.
 */
public class TRPenaltiesList implements DoublyLinkedList {

    private TRPenalty head;
    private TRPenalty tail;


    public TRPenaltiesList(final TRPenaltiesList copyMeList) {
        setUpHeadTail(new TRPenalty(copyMeList.getHead()), new TRPenalty(copyMeList.getTail()));

        TRPenalty copyMe = copyMeList.getHead();
        TRPenalty newPenalty = getHead();
        while(copyMe.getNext() != copyMeList.getLast()) {
            copyMe = copyMe.getNext();
            newPenalty.insertAfterCurrent(new TRPenalty(copyMe));
            newPenalty = newPenalty.getNext();
        }
    }

    public  TRPenaltiesList(){
        setUpHeadTail();
    }


    @Override
    public void setUpHeadTail() {
        this.head = new TRPenalty();
        this.tail = new TRPenalty();
        //	setHead((ObjectInList) new TRPenalty());
        //	setTail((ObjectInList) new TRPenalty());
        linkHeadTail();
    }




    //GETTER
    @Override
    public TRPenalty getHead() {
        return this.head;
    }




    //GETTER
    @Override
    public TRPenalty getTail() {
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
    public void setAttributes(final TRAttributes attributes) { }




    @Override
    public boolean setHead(final ObjectInList head) {
        //	return getHead().replaceThisWith((TRPenalty) head);
        if(head != null) {
            head.setPrevious(getTail().getPrevious());
            head.getPrevious().setNext(head);
            getHead().setPrevious(null);
            getHead().setNext((ObjectInList) null);
            this.head = (TRPenalty) head;
            return true;
        }
        return false;
    }




    @Override
    public TRPenalty getFirst() {
        if(isEmpty() || !isValidHeadTail()) {
            System.out.println("ERROR: getFirst() is null/invalid");
            return null;
        }
        return getHead().getNext();
    }


    public int getPenaltyTimeForRange(final int startHour, final int startMinute, final int endHour, final int endMinute){
        TRPenalty thePenalty = this.getFirst();
        int totalPenalties = 0;
        while(thePenalty != this.getTail()){
            if(!thePenalty.isPenaltyInEffect() && thePenalty.isPenaltyApplicable(startHour, startMinute, endHour, endMinute)){
                thePenalty.setPenaltyInEffect(true);
                totalPenalties += thePenalty.getPenaltyInMinutes();
            }
            thePenalty = thePenalty.getNext();
        }


        return totalPenalties;
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
    public TRPenalty getLast() {
        if(isEmpty() || !isValidHeadTail()) {
            return null;
        }
        return (TRPenalty) getTail().getPrevious();
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
        TRPenalty theDay = this.getHead();

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
        //	return getTail().replaceThisWith((TRPenalty) tail);
        if(tail != null) {
            tail.setPrevious(getTail().getPrevious());
            tail.getPrevious().setNext(tail);
            getTail().setPrevious(null);
            getTail().setNext((ObjectInList) null);
            this.tail = (TRPenalty) tail;
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

    @Override
    public boolean insertShipment(TRShipment insertShipment) {
        return false;
    }


    @Override
    public boolean removeByIndex(final int index) {
        int counter = -1;
        TRPenalty theDay = this.getHead();

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
        TRPenalty theDepot = getHead();
        int sizeCounter = 0;

        if(!isValidHeadTail()) {
            return -1;
        }

        while(theDepot.getNext() != getTail()) {
            theDepot = theDepot.getNext();
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
        TRPenalty theDepot = getHead();
        while(theDepot.getNext() != getTail() && isValidHeadTail()) {
            theDepot = theDepot.getNext();
            if(theDepot == findMe) {
                theDepot.removeThisObject();
                return true;
            }
        }
        return false;
    }




    @Override
    public boolean insertAfterIndex(final ObjectInList insertMe, final int index) {
        int counter = -1;
        TRPenalty theDepot = getHead();

        while(index >= 0 && index < getSize() && !isEmpty() && isValidHeadTail()) {
            theDepot = theDepot.getNext();
            counter++;
            if(counter == index) {
                theDepot.insertAfterCurrent(insertMe);
                return true;
            }
        }
        return false;
    }




    @Override
    public ObjectInList getAtIndex(final int index) {
        int counter = -1;
        TRPenalty theDepot = getHead();

        while(index >= 0 && index < getSize() && !isEmpty() && isValidHeadTail()) {
            theDepot = theDepot.getNext();
            counter++;
            if(counter == index) {
                return theDepot;
            }
        }
        return null;
    }




    @Override
    public boolean insertAfterObject(final ObjectInList insertMe, final ObjectInList insertAfter) {
        TRPenalty theDepot = head;
        while(!isEmpty() && isValidHeadTail()) {
            theDepot = theDepot.getNext();
            if(theDepot == insertAfter) {
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
        this.head = new TRPenalty();
        this.tail = new TRPenalty();
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
