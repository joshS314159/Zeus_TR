package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions;

/**
 * Created by jks1010 on 5/29/2015.
 */
public class ObjectInList<B extends ObjectInListInterface<B> & ObjectInListCoreInterface<B>> implements ObjectInListInterface<B> {

	B outerClass;

	public ObjectInList(final B outerClass){
		this.outerClass = outerClass;
	}
	@Override
	public boolean insertAfterCurrent(final B insertMe) {
		if(outerClass.getNext() != null) {
			(insertMe).setPrevious(outerClass);
			(insertMe).setNext(outerClass.getNext());

			(outerClass).setNext(insertMe);
			(insertMe).getNext().setPrevious(insertMe);
			return true;
		}
		return false;
	}


	@Override
	public void linkAsHeadTail(final B linkTwo) {
		outerClass.setNext(linkTwo);
		(linkTwo).setPrevious(outerClass);

		outerClass.setPrevious(null);    //nothing comes before the head
		(linkTwo).setNext(null);        //nothing comes after the tail
	}

	@Override
	public boolean removeThisObject() {
		if(outerClass.getNext() != null || outerClass.getPrevious() != null) {

			(outerClass.getPrevious()).setNext(outerClass.getNext());
			(outerClass.getNext()).setPrevious(outerClass.getPrevious());

			outerClass.setPrevious(null);
			outerClass.setNext((B) null);
			return true;
		}
		return false;
	}


}
