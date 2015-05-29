package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions;

import edu.sru.thangiah.zeus.tr.TRAttributes;

/**
 * Created by jks1010 on 5/29/2015.
 */
public class ObjectInList<T extends ObjectInListInterface & ObjectInListCoreInterface> implements ObjectInListInterface<T> {

	T outerClass;

	public ObjectInList(final T outerClass){
		this.outerClass = outerClass;
	}
	@Override
	public boolean insertAfterCurrent(final T insertMe) {
		if(outerClass.getNext() != null) {
			(insertMe).setPrevious(this);
			(insertMe).setNext(outerClass.getNext());

			(outerClass).setNext(insertMe);
			(insertMe).getNext().setPrevious(insertMe);
			return true;
		}
		return false;
	}


	@Override
	public void linkAsHeadTail(final T linkTwo) {
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
			outerClass.setNext((edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.ObjectInList) null);
			return true;
		}
		return false;
	}


}
