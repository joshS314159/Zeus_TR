package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy;

import edu.sru.thangiah.zeus.tr.TRAttributes;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.ObjectInListCoreInterface;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.ObjectInListInterface;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.ObjectInList;

/**
 * Created by jks1010 on 3/21/2015.
 */
public class TRDelayType implements //ObjectInList
		ObjectInListInterface<TRDelayType>, ObjectInListCoreInterface<TRDelayType>

{
private TRDelayType next;
private TRDelayType previous;
private String delayTypeName;
private int delayTimeInMinutes;
private ObjectInList<TRDelayType> objectInList = new ObjectInList<>(this);



public TRDelayType(final String delayTypeName, final int delayTimeInMinutes) {
	this.setDelayTypeName(delayTypeName);
	this.setDelayTimeInMinutes(delayTimeInMinutes);
}

public boolean setDelayTypeName(final String delayTypeName) {
	if(delayTypeName.length() > 0) {
		this.delayTypeName = delayTypeName;
		return true;
	}
	return false;
}

public boolean setDelayTimeInMinutes(final int delayTimeInMinutes) {
	if(delayTimeInMinutes > 0) {
		this.delayTimeInMinutes = delayTimeInMinutes;
		return true;
	}
	return false;
}


public TRDelayType(final TRDelayType copyMe) {
	setNext(null);
	setPrevious(null);
	setDelayTypeName(copyMe.getDelayTypeName());
	setDelayTimeInMinutes(copyMe.getDelayTimeInMinutes());
}

public String getDelayTypeName() {
	return this.delayTypeName;
}

public int getDelayTimeInMinutes() {
	return delayTimeInMinutes;
}

public TRDelayType() {
}




public TRDelayType getNext() {
	return this.next;
}

@Override
public TRDelayType getPrevious() {
	return this.previous;
}

@Override
public void setNext(final TRDelayType next) {
	this.next = next;
}

@Override
public void setPrevious(final TRDelayType previous) {
	this.previous = previous;
}



@Override
public boolean insertAfterCurrent(final TRDelayType insertMe) {
	return objectInList.insertAfterCurrent(insertMe);
}

@Override
public void linkAsHeadTail(final TRDelayType linkTwo) {
	objectInList.linkAsHeadTail(linkTwo);
}

@Override
public boolean removeThisObject() {
	return objectInList.removeThisObject();
}


}
