package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions;

/**
 * Created by jks1010 on 5/29/2015.
 */
public interface DoublyLinkedListCoreInterface<B extends ObjectInListCoreInterface<B> & ObjectInListInterface<B>> {
	B getHead();

	B getTail();

	void setTail(final B tail);

	void setHead(final B head);
}
