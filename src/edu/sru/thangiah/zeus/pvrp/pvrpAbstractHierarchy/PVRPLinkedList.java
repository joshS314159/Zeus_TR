package edu.sru.thangiah.zeus.pvrp.pvrpAbstractHierarchy;

import edu.sru.thangiah.zeus.pvrp.PVRPAttributes;

/**
 * Created by library-tlc on 12/18/14.
 */
abstract public class PVRPLinkedList {



public void insertLast(Object insertMe){}

public void linkHeadTail(){}

public PVRPAttributes getAttributes(){
	return new PVRPAttributes();
}

public void setAttributes(PVRPAttributes attributes){}

public Object getHead(){
	return new Object();
}

public void setHead(Object head){
}

public Object getTail(){
	return new Object();
}

public void setTail(Object tail){}

public int getSize(){
	return -1;
}

public Object getFirstObject(){
	return new Object();
}

public Object getLastObject(){
	return new Object();
}

public boolean isEmpty(){
	return true;
}


public Object getObjectAtPosition(int position){
	return new Object();
}


}
