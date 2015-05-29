package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions;

import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.DoublyLinkedList;

/**
 * Created by jks1010 on 5/29/2015.
 */
public interface ContainsSubObjectInterface<ListObject extends ObjectInListInterface> {
	public Object getSubObject();

	public void setSubobject(final ListObject subobject);

	public double getDistanceTravelledMiles();
}
