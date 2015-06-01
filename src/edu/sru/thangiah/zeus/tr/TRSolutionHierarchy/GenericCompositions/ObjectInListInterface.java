package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions;

import edu.sru.thangiah.zeus.tr.TRAttributes;

/**
 * Created by jks1010 on 5/29/2015.
 */
public interface ObjectInListInterface<B extends ObjectInListInterface<B> & ObjectInListCoreInterface<B>> {

//    public void setHasPhysicalLocation();

//    public boolean getHasPhysicalLocation();


//
//	public TRAttributes getAttributes();

//
//	public void setAttributes(final TRAttributes attributes);


	boolean insertAfterCurrent(final B insertMe);


//	public ObjectInList getNext();


//	public void setNext(final ObjectInList next);


	void linkAsHeadTail(final B linkTwo);


	boolean removeThisObject();


//	public ObjectInList getPrevious();


//	public void setPrevious(final ObjectInList previous);




}
