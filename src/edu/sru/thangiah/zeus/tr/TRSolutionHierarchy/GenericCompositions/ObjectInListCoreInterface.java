package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions;

import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.*;

/**
 * Created by jks1010 on 5/29/2015.
 */
public interface ObjectInListCoreInterface<B extends ObjectInListCoreInterface<B> & ObjectInListInterface<B>> {

	B getNext();

	B getPrevious();

	void setNext(final B next);

	void setPrevious(final B previous);
}
