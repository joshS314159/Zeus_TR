package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;


import edu.sru.thangiah.zeus.tr.TRAttributes;


/**
 * Created by joshuasarver on 1/14/15.
 */
abstract public interface DoublyLinkedList {


//private final Constructor<? extends T> ctor;
//private T field;

//    public T            head;
//    public T            tail;
//    public TRAttributes attributes;
//    public Class<T>     clazz;
//    public int size = 0;


public void setUpHeadTail();

public void linkHeadTail();

public void setUpHeadTail(final ObjectInList head, final ObjectInList tail);


public TRAttributes getAttributes();


public void setAttributes(final TRAttributes attributes);


public ObjectInList getHead();


public boolean setHead(final ObjectInList head);


public ObjectInList getFirst();


//@Override
public boolean insertAfterLastIndex(final ObjectInList theObject);


public ObjectInList getLast();

public boolean removeLast();

public boolean removeFirst();


public int getIndexOfObject(final ObjectInList findMe);


public ObjectInList getTail();


public boolean setTail(final ObjectInList tail);


public boolean isValidHeadTail();

public boolean insertShipment(final TRShipment insertShipment);


public boolean removeByIndex(final int index);


public int getSize();


public int getSizeWithHeadTail();

public boolean isEmpty();


public boolean removeByObject(final ObjectInList findMe);


public boolean insertAfterIndex(final ObjectInList insertMe, final int index);

public ObjectInList getAtIndex(final int index);


public boolean insertAfterObject(final ObjectInList insertMe, final ObjectInList insertAfter);


public double getDistanceTravelledMiles();

public boolean setHeadNext(final ObjectInList nextHead);

public boolean setTailPrevious(final ObjectInList previousTail);


//    public Object copyThis(final Object copyMe);

}
