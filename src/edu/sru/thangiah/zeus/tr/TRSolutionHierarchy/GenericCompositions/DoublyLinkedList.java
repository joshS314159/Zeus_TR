package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions;

import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.*;
//import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.ObjectInList;


/**
 * Created by jks1010 on 5/29/2015.
 */
public class DoublyLinkedList<A extends DoublyLinkedListCoreInterface<B> & DoublyLinkedListInterface<B>, B extends ObjectInListInterface<B> & ObjectInListCoreInterface<B>> implements DoublyLinkedListInterface<B> {

	private Class<B> newA;
	private A outerClass;

	public DoublyLinkedList(final A outerClass, final Class<B> newA){
		this.newA = newA;
		this.outerClass = outerClass;
	}

	public B getNewA() throws IllegalAccessException, InstantiationException {
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
//		outerClass.link
		outerClass.getHead().linkAsHeadTail(outerClass.getTail());
	}




	@Override
	public void setUpHeadTail(final B head, final B tail) {
		outerClass.setHead(head);
		outerClass.setTail(tail);
//	this.head = new TRDepot();
//	this.tail = new TRDepot();
		linkHeadTail();
	}
//
//@Override
//public boolean emptyList() {
//this.getHead().setNext((ObjectInList) this.getTail());
//this.getTail().setPrevious(this.getHead());
//
//this.getHead().setPrevious(null);
//this.getTail().setNext((ObjectInList) null);
//}


@Override
	public B getFirst() {
		if (isEmpty() || !isValidHeadTail()) {
			System.out.println("ERROR: getFirst() is null/invalid");
			return null;
		}

//		outerClass.getHead().getNext()
		return outerClass.getHead().getNext();
	}

	@Override
	public boolean insertAfterLastIndex(final B theObject) {
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
	public B getLast() {
		if (isEmpty() || !isValidHeadTail()) {
			return null;
		}
		return (B) outerClass.getTail().getPrevious();
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
	public int getIndexOfObject(final B findMe) {
		int counter = -1;
		B theGeneric = outerClass.getHead();

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
		B theGeneric = outerClass.getHead();

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
		B theGeneric = outerClass.getHead();
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
	public boolean removeByObject(final B findMe) {
		B theGeneric = outerClass.getHead();
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
	public boolean insertAfterIndex(final B insertMe, final int index) {
		int counter = -1;
		B theGeneric = outerClass.getHead();

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
	public B getAtIndex(final int index) {
		int counter = -1;
		B theGeneric = outerClass.getHead();

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
	public boolean insertAfterObject(final B insertMe, final B insertAfter) {
		B theGeneric = outerClass.getHead();
		while (isValidHeadTail() && theGeneric != outerClass.getTail()) {
			if (theGeneric == insertAfter) {
				return insertAfter.insertAfterCurrent(insertMe);
				//			return true;
			}
			theGeneric = theGeneric.getNext();
		}
		return false;
	}

	@Override
	public double getDistanceTravelledMiles() {
		return 0;
	}

	@Override
	public boolean setHeadNext(final B nextHead) {
		if (outerClass.getHead().getNext() == outerClass.getTail()) {
			return false;
		}
		outerClass.getHead().setNext((B) nextHead);
		return true;
	}

	@Override
	public boolean setTailPrevious(final B nextTail) {
		if (outerClass.getTail().getPrevious() == outerClass.getHead()) {
			return false;
		}
		outerClass.getTail().setPrevious((B) nextTail);
		return true;
	}
}
