package edu.sru.thangiah.zeus.pvrp.pvrpAbstractHierarchy;

import edu.sru.thangiah.zeus.pvrp.PVRPAttributes;
import edu.sru.thangiah.zeus.pvrp.PVRPNodesLinkedList;

/**
 * Created by library-tlc on 12/18/14.
 */
abstract public class PVRPObject {


public PVRPObject(){}

public PVRPAttributes getAttributes() {
	return new PVRPAttributes();
}

public float getMaxDemand() {
	return -1;
}

public float getMaxDistance() {
	return -1;
}

public Object getNext() {
	return new Object();
}

public Object getPrevious() {
	return new Object();
}

public Object getPrev(){
	return new Object();
}

public void setAttributes(PVRPAttributes attributes) {

}

public void setNext(PVRPObject next) {

}

public void setPrevious(PVRPObject previous) {

}

public void setPrev(PVRPObject previous){

}


}
