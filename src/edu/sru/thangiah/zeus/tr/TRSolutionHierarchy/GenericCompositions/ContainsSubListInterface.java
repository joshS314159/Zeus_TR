package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions;

/**
 * Created by jks1010 on 5/29/2015.
 */
public interface ContainsSubListInterface<C extends DoublyLinkedListCoreInterface<D> & DoublyLinkedListInterface<D>, D extends ObjectInListInterface<D> & ObjectInListCoreInterface<D>> {

//	C getNewC();

	C getSubList();

	void setSubList(final C subList);

	boolean isSubListEmpty();

    int getSubListSize();

	double getDistanceTravelledMiles();
}
