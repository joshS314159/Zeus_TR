package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions;

import edu.sru.thangiah.zeus.tr.TRAttributes;

/**
 * Created by jks1010 on 5/29/2015.
 */
public interface ObjectInListInterface<T extends ObjectInListInterface & ObjectInListCoreInterface> {

//    public void setHasPhysicalLocation();

//    public boolean getHasPhysicalLocation();


//
//	public TRAttributes getAttributes();

//
//	public void setAttributes(final TRAttributes attributes);


	public boolean insertAfterCurrent(final T insertMe);


//	public ObjectInList getNext();


//	public void setNext(final ObjectInList next);


	public void linkAsHeadTail(final T linkTwo);


	public boolean removeThisObject();


//	public ObjectInList getPrevious();


//	public void setPrevious(final ObjectInList previous);




}
