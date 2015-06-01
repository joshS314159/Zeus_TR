package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;

import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.*;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.DoublyLinkedList;

/**
 * Created by jks1010 on 3/21/2015.
 */
public class TRDelayTypeList implements //DoublyLinkedList
		DoublyLinkedListCoreInterface<TRDelayType>, DoublyLinkedListInterface<TRDelayType> {

//VARIABLES
private TRDelayType head;     //takes precedence over the base class head and tail
private TRDelayType tail;
//    private TRAttributes attributes = new TRAttributes();     //takes precedence over the base class attributes
private DoublyLinkedList<TRDelayTypeList, TRDelayType> doublyLinkedList = new DoublyLinkedList<>(this, TRDelayType
		.class);

//CONSTRUCTOR
public TRDelayTypeList() {
	setUpHeadTail();
//        setAttributes(new TRAttributes());
}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

@Override
public void setUpHeadTail() {
	this.head = new TRDelayType();
	this.tail = new TRDelayType();
	//	setHead((ObjectInList) new TRDelayType());
	//	setTail((ObjectInList) new TRDelayType());
	linkHeadTail();
}

//GETTER
@Override
public TRDelayType getHead() {
	return this.head;
}

//GETTER
@Override
public TRDelayType getTail() {
	return this.tail;
}


//METHOD
//link the head and the tail together
public void linkHeadTail() {
	doublyLinkedList.linkHeadTail();
}//END LINK_HEAD_TAIL *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

@Override
public void setUpHeadTail(final TRDelayType head, final TRDelayType tail) {
	doublyLinkedList.setUpHeadTail(head, tail);
}

@Override
public TRDelayType getFirst() {
	return doublyLinkedList.getFirst();
}

@Override
public boolean insertAfterLastIndex(final TRDelayType theObject) {
	return doublyLinkedList.insertAfterLastIndex(theObject);
}

@Override
public TRDelayType getLast() {
	return doublyLinkedList.getLast();
}

@Override
public boolean removeLast() {
	return doublyLinkedList.removeLast();
}

@Override
public boolean removeFirst() {
	return doublyLinkedList.removeFirst();
}

@Override
public int getIndexOfObject(final TRDelayType findMe) {
	return doublyLinkedList.getIndexOfObject(findMe);
}

@Override
public boolean isValidHeadTail() {
	return doublyLinkedList.isValidHeadTail();
}

@Override
public boolean removeByIndex(final int index) {
	return doublyLinkedList.removeByIndex(index);
}

public int getSize() {
	return doublyLinkedList.getSize();
}

@Override
public int getSizeWithHeadTail() {
	return doublyLinkedList.getSizeWithHeadTail();
}

public boolean isEmpty() {
	return doublyLinkedList.isEmpty();
}

@Override
public boolean removeByObject(final TRDelayType findMe) {
	return doublyLinkedList.removeByObject(findMe);
}

@Override
public boolean insertAfterIndex(final TRDelayType insertMe, final int index) {
	return doublyLinkedList.insertAfterIndex(insertMe, index);
}

@Override
public TRDelayType getAtIndex(final int index) {
	return doublyLinkedList.getAtIndex(index);
}

@Override
public boolean insertAfterObject(final TRDelayType insertMe, final TRDelayType insertAfter) {
	return doublyLinkedList.insertAfterObject(insertMe, insertAfter);
}

@Override
public double getDistanceTravelledMiles() {
	return doublyLinkedList.getDistanceTravelledMiles();
}

@Override
public boolean setHeadNext(final TRDelayType nextHead) {
	return doublyLinkedList.setHeadNext(nextHead);
}

@Override
public boolean setTailPrevious(final TRDelayType previousTail) {
	return doublyLinkedList.setTailPrevious(previousTail);
}


public TRDelayTypeList(final TRDelayTypeList copyMeList) {
	setUpHeadTail(new TRDelayType(copyMeList.getHead()), new TRDelayType(copyMeList.getTail()));

	TRDelayType copyMe = copyMeList.getHead();
	TRDelayType newDelay = getHead();
	while(copyMe.getNext() != copyMeList.getLast()) {
		copyMe = copyMe.getNext();
		newDelay.insertAfterCurrent(new TRDelayType(copyMe));
		newDelay = newDelay.getNext();
	}
}


public void setHead(final TRDelayType head) {
	//	return getHead().replaceThisWith((TRDelayType) head);
	if(head != null) {
		head.setPrevious(getTail().getPrevious());
		head.getPrevious().setNext(head);
		getHead().setPrevious(null);
		getHead().setNext( null);
		this.head = (TRDelayType) head;
//		return true;
	}
//	return false;
}

public void setTail(final TRDelayType tail) {
	//	return getTail().replaceThisWith((TRDelayType) tail);
	if(tail != null) {
		tail.setPrevious(getTail().getPrevious());
		tail.getPrevious().setNext(tail);
		getTail().setPrevious(null);
		getTail().setNext( null);
		this.tail =  tail;
//		return true;
	}
//	return false;

}


public TRDelayType getByDelayName(final String delayName) {
	int counter = -1;
	TRDelayType theDelay = getFirst();

	while(theDelay != this.getTail()) {
		if(theDelay.getDelayTypeName().equals(delayName)) {
			return theDelay;
		}
		theDelay = theDelay.getNext();
	}
	return null;
}


}
