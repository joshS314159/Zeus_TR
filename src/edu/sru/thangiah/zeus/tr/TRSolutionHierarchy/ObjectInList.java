package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;


import edu.sru.thangiah.zeus.tr.TRAttributes;


/**
 * Created by joshuasarver on 1/14/15.
 */
public interface ObjectInList {


//    public boolean setCoordinates(final TRCoordinates coordinates);


//    public TRCoordinates getCoordinates();


//    public void setHasPhysicalLocation();

//    public boolean getHasPhysicalLocation();


public DoublyLinkedList getSubList();


public void setSubList(final DoublyLinkedList subList);


//    public boolean createSubList();


public TRAttributes getAttributes();


public void setAttributes(final TRAttributes attributes);


public boolean insertAfterCurrent(final ObjectInList insertMe);


public ObjectInList getNext();


public void setNext(final ObjectInList next);


public void linkAsHeadTail(final ObjectInList linkTwo);


public boolean removeThisObject();


public ObjectInList getPrevious();


public void setPrevious(final ObjectInList previous);


public boolean isSubListEmpty();

//    public double getSubListSize();


public double getDistanceTravelledMiles();

//    public void removeNextPreviousReferences(final ObjectInList removeMe)
//public boolean replaceThisWith(final ObjectInList replaceWith);

}
