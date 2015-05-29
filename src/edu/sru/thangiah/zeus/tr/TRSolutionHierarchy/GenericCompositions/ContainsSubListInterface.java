package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions;

import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.DoublyLinkedList;

/**
 * Created by jks1010 on 5/29/2015.
 */
public interface ContainsSubListInterface<A extends ObjectInListInterface, B extends DoublyLinkedListCoreInterface> {

	public A getSubList();

	public void setSubList(final A subList);

	public boolean isSubListEmpty();

    public int getSubListSize();

	public double getDistanceTravelledMiles();
}
