package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions;

/**
 * Created by jks1010 on 5/29/2015.
 */
public interface DoublyLinkedListInterface<A extends ObjectInListInterface & ObjectInListCoreInterface, B extends DoublyLinkedListInterface & DoublyLinkedListCoreInterface>{


	public void setUpHeadTail() throws InstantiationException, IllegalAccessException;

	public void linkHeadTail();

	public void setUpHeadTail(final A head, final A tail);

//
//	public TRAttributes getAttributes();
//
//
//	public void setAttributes(final TRAttributes attributes);


//	public A getHead();


//	public boolean setHead(final A head);


	public A getFirst();


	//@Override
	public boolean insertAfterLastIndex(final A theObject);


//	public A getLast();

	public boolean removeLast();

	public boolean removeFirst();


	public int getIndexOfObject(final A findMe);


//	public A getTail();


//	public boolean setTail(final A tail);


	public boolean isValidHeadTail();

//	public boolean insertShipment(final TRShipment insertShipment);


	public boolean removeByIndex(final int index);


	public int getSize();


	public int getSizeWithHeadTail();

	public boolean isEmpty();


	public boolean removeByObject(final A findMe);


	public boolean insertAfterIndex(final A insertMe, final int index);

	public A getAtIndex(final int index);


	public boolean insertAfterObject(final A insertMe, final A insertAfter);


	public double getDistanceTravelledMiles();

	public boolean setHeadNext(final A nextHead);

	public boolean setTailPrevious(final A previousTail);


//    public Object copyThis(final Object copyMe);

}
