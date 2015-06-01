package edu.sru.thangiah.zeus.tr;

import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.DoublyLinkedListCoreInterface;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.DoublyLinkedListInterface;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.DoublyLinkedList;

/**
 * Created by joshuasarver on 3/19/15.
 */
public class TRPenaltiesList implements //DoublyLinkedList
		DoublyLinkedListCoreInterface<TRPenalty>, DoublyLinkedListInterface<TRPenalty> {

private TRPenalty head;
private TRPenalty tail;
private DoublyLinkedList<TRPenaltiesList, TRPenalty> doublyLinkedList = new DoublyLinkedList<>(this, TRPenalty.class);

public TRPenaltiesList(final TRPenaltiesList copyMeList) {
	setUpHeadTail(new TRPenalty(copyMeList.getHead()), new TRPenalty(copyMeList.getTail()));

	TRPenalty copyMe = copyMeList.getHead();
	TRPenalty newPenalty = getHead();
	while(copyMe.getNext() != copyMeList.getLast()) {
		copyMe = copyMe.getNext();
		newPenalty.insertAfterCurrent(new TRPenalty(copyMe));
		newPenalty = newPenalty.getNext();
	}
}

public TRPenaltiesList() {
	setUpHeadTail();
}


@Override
public void setUpHeadTail() {
	this.head = new TRPenalty();
	this.tail = new TRPenalty();
	//	setHead((ObjectInList) new TRPenalty());
	//	setTail((ObjectInList) new TRPenalty());
	linkHeadTail();
}


//GETTER
@Override
public TRPenalty getHead() {
	return this.head;
}


//GETTER
@Override
public TRPenalty getTail() {
	return this.tail;
}

@Override
public void setTail(final TRPenalty tail) {
	this.tail = tail;
}



//METHOD
//link the head and the tail together
public void linkHeadTail() {
	doublyLinkedList.linkHeadTail();
}//END LINK_HEAD_TAIL *********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

@Override
public void setUpHeadTail(final TRPenalty head, final TRPenalty tail) {
	doublyLinkedList.setUpHeadTail(head, tail);
}

@Override
public TRPenalty getFirst() {
	return doublyLinkedList.getFirst();
}

@Override
public boolean insertAfterLastIndex(final TRPenalty theObject) {
	return doublyLinkedList.insertAfterLastIndex(theObject);
}

@Override
public TRPenalty getLast() {
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
public int getIndexOfObject(final TRPenalty findMe) {
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
public boolean removeByObject(final TRPenalty findMe) {
	return doublyLinkedList.removeByObject(findMe);
}

@Override
public boolean insertAfterIndex(final TRPenalty insertMe, final int index) {
	return doublyLinkedList.insertAfterIndex(insertMe, index);
}

@Override
public TRPenalty getAtIndex(final int index) {
	return doublyLinkedList.getAtIndex(index);
}


@Override
public boolean insertAfterObject(final TRPenalty insertMe, final TRPenalty insertAfter) {
	return doublyLinkedList.insertAfterObject(insertMe, insertAfter);
}

@Override
public double getDistanceTravelledMiles() {
	return doublyLinkedList.getDistanceTravelledMiles();
}

@Override
public boolean setHeadNext(final TRPenalty nextHead) {
	return doublyLinkedList.setHeadNext(nextHead);
}

@Override
public boolean setTailPrevious(final TRPenalty previousTail) {
	return doublyLinkedList.setTailPrevious(previousTail);
}



public void setHead(final TRPenalty head) {
	this.head = head;
}

public int getPenaltyTimeForRange(final int startHour, final int startMinute, final int endHour, final int endMinute) {
	TRPenalty thePenalty = this.getFirst();
	int totalPenalties = 0;
	while(thePenalty != this.getTail()) {
		if(!thePenalty.isPenaltyInEffect() && thePenalty.isPenaltyApplicable(startHour, startMinute, endHour, endMinute)) {
			thePenalty.setPenaltyInEffect(true);
			totalPenalties += thePenalty.getPenaltyInMinutes();
		}
		thePenalty = thePenalty.getNext();
	}


	return totalPenalties;
}


}
