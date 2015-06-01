package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions;

/**
 * Created by jks1010 on 5/29/2015.
 */
public interface DoublyLinkedListInterface<B extends ObjectInListInterface<B> & ObjectInListCoreInterface<B>>{


	void setUpHeadTail() throws InstantiationException, IllegalAccessException;

	void linkHeadTail();

	void setUpHeadTail(final B head, final B tail);

//
//	public TRAttributes getAttributes();
//
//
//	public void setAttributes(final TRAttributes attributes);


//	public A getHead();


//	public boolean setHead(final A head);

//	boolean emptyList();

	B getFirst();


	//@Override
	boolean insertAfterLastIndex(final B theObject);


	B getLast();

	boolean removeLast();

	boolean removeFirst();


	int getIndexOfObject(final B findMe);


//	public A getTail();


//	public boolean setTail(final A tail);


	boolean isValidHeadTail();

//	public boolean insertShipment(final TRShipment insertShipment);


	boolean removeByIndex(final int index);


	int getSize();


	int getSizeWithHeadTail();

	boolean isEmpty();


	boolean removeByObject(final B findMe);


	boolean insertAfterIndex(final B insertMe, final int index);

	B getAtIndex(final int index);


	boolean insertAfterObject(final B insertMe, final B insertAfter);


	double getDistanceTravelledMiles();

	boolean setHeadNext(final B nextHead);

	boolean setTailPrevious(final B previousTail);


//    public Object copyThis(final Object copyMe);

}
