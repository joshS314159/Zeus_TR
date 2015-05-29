package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions;

import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.*;
//import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.ObjectInList;


/**
 * Created by jks1010 on 5/29/2015.
 */
public class DoublyLinkedList<A extends ObjectInListInterface & ObjectInListCoreInterface, B extends DoublyLinkedListCoreInterface & DoublyLinkedListInterface> implements DoublyLinkedListInterface<A, B> {

	private Class<A> newA;
	private B outerClass;

	public DoublyLinkedList(final Class<A> newA, final B outerClass){
		this.newA = newA;
		this.outerClass = outerClass;
	}

	public A getNewA() throws IllegalAccessException, InstantiationException {
		return newA.newInstance();
	}



	@Override
	public void setUpHeadTail() throws InstantiationException, IllegalAccessException {
		outerClass.setHead(getNewA());
		outerClass.setTail(getNewA());
//		super.setAttributes(null);
		//	setHead((A) new TRDepot());
		//	setTail((A) new TRDepot());
		linkHeadTail();
	}

	@Override
	public void linkHeadTail() {
		outerClass.getHead().linkAsHeadTail(outerClass.getTail());
	}




	@Override
	public void setUpHeadTail(final A head, final A tail) {
		outerClass.setHead(head);
		outerClass.setTail(tail);
//	this.head = new TRDepot();
//	this.tail = new TRDepot();
		linkHeadTail();
	}



	@Override
	public A getFirst() {
		if (isEmpty() || !isValidHeadTail()) {
			System.out.println("ERROR: getFirst() is null/invalid");
			return null;
		}

//		outerClass.getHead().getNext()
		return outerClass.getHead().getNext();
	}

	@Override
	public boolean insertAfterLastIndex(final A theObject) {
		if (!isValidHeadTail()) {
			return false;
		}

		if (isEmpty()) {
			return outerClass.getHead().insertAfterCurrent(theObject);
		}
		//otherwise we already got stuff in here
		return getLast().insertAfterCurrent(theObject);
	}

	@Override
	public A getLast() {
		if (isEmpty() || !isValidHeadTail()) {
			return null;
		}
		return (TRDepot) outerClass.getTail().getPrevious();
	}

	@Override
	public boolean removeLast() {
		if (!isEmpty() && isValidHeadTail()) {
			return outerClass.getTail().getPrevious().removeThisObject();
		}
		return false;
	}

	@Override
	public boolean removeFirst() {
		if (!isEmpty() && isValidHeadTail()) {
			return outerClass.getHead().getNext().removeThisObject();
		}
		return false;
	}

	@Override
	public int getIndexOfObject(final A findMe) {
		int counter = -1;
		A theGeneric = outerClass.getHead();

		if (!isEmpty() && isValidHeadTail()) {
			while (theGeneric != findMe) {
				theGeneric = theGeneric.getNext();
				counter++;
				if (theGeneric == outerClass.getTail()) {
					return -1;
				}
			}
			return counter;
		}
		return -1;
	}


	@Override
	public boolean isValidHeadTail() {
		if (outerClass.getHead() == null || outerClass.getHead().getNext() == null || outerClass.getHead().getPrevious() != null ||
				outerClass.getTail().getPrevious() == null || outerClass.getTail() == null || outerClass.getTail().getNext() != null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean removeByIndex(final int index) {
		int counter = -1;
		A theGeneric = outerClass.getHead();

		while (index >= 0 && index < getSize() && isValidHeadTail()) {
			theGeneric = theGeneric.getNext();
			counter++;
			if (counter == index) {
				return theGeneric.removeThisObject();
			}
		}
		return false;
	}

	@Override
	public int getSize() {
		A theGeneric = outerClass.getHead();
		int sizeCounter = 0;

		if (!isValidHeadTail()) {
			return -1;
		}

		while (theGeneric.getNext() != outerClass.getTail()) {
			theGeneric = theGeneric.getNext();
			sizeCounter++;
		}

		return sizeCounter;
	}

	@Override
	public int getSizeWithHeadTail() {
		if (isValidHeadTail()) {
			return getSize() + 2;
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		if (getSize() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean removeByObject(final A findMe) {
		A theGeneric = outerClass.getHead();
		while (theGeneric.getNext() != outerClass.getTail() && isValidHeadTail()) {
			theGeneric = theGeneric.getNext();
			if (theGeneric == findMe) {
				theGeneric.removeThisObject();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean insertAfterIndex(final A insertMe, final int index) {
		int counter = -1;
		A theGeneric = outerClass.getHead();

		while (index >= 0 && index < getSize() && !isEmpty() && isValidHeadTail()) {
			theGeneric = theGeneric.getNext();
			counter++;
			if (counter == index) {
				theGeneric.insertAfterCurrent(insertMe);
				return true;
			}
		}
		return false;
	}

	@Override
	public A getAtIndex(final int index) {
		int counter = -1;
		A theGeneric = outerClass.getHead();

		while (index >= 0 && index < getSize() && !isEmpty() && isValidHeadTail()) {
			theGeneric = theGeneric.getNext();
			counter++;
			if (counter == index) {
				return theGeneric;
			}
		}
		return null;
	}

	@Override
	public boolean insertAfterObject(final A insertMe, final A insertAfter) {
		A theGeneric = outerClass.getHead();
		while (!isEmpty() && isValidHeadTail()) {
			theGeneric = theGeneric.getNext();
			if (theGeneric == insertAfter) {
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
	public boolean setHeadNext(final A nextHead) {
		if (outerClass.getHead().getNext() == outerClass.getTail()) {
			return false;
		}
		outerClass.getHead().setNext((A) nextHead);
		return true;
	}

	@Override
	public boolean setTailPrevious(final A nextTail) {
		if (outerClass.getTail().getPrevious() == outerClass.getHead()) {
			return false;
		}
		outerClass.getTail().setPrevious((A) nextTail);
		return true;
	}
}
