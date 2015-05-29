package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions;

/**
 * Created by jks1010 on 5/29/2015.
 */
public interface DoublyLinkedListCoreInterface<A extends ObjectInListCoreInterface & ObjectInListInterface, B extends DoublyLinkedListInterface & DoublyLinkedListCoreInterface> {
	A getHead();

	A getTail();

	void setTail(final A tail);

	void setHead(final A head);
}
