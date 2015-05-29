package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions;

import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.*;

/**
 * Created by jks1010 on 5/29/2015.
 */
public interface ObjectInListCoreInterface<A extends ObjectInListInterface & ObjectInListCoreInterface, B extends DoublyLinkedListInterface & DoublyLinkedListCoreInterface> {

	A getNext();

	A getPrevious();

	void setNext(final A next);

	void setPrevious(final A previous);
}
