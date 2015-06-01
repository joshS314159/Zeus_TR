package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions;

/**
 * Created by jks1010 on 5/29/2015.
 */
public class ContainsSubList<C extends DoublyLinkedListCoreInterface<D> & DoublyLinkedListInterface<D>, D extends ObjectInListInterface<D> & ObjectInListCoreInterface<D>> implements ContainsSubListInterface<C, D> {
	private C subList;
//	private Class<C> newC;
//
//public ContainsSubList(final Class<C> newC) throws InstantiationException, IllegalAccessException {
//	this.newC = newC;
//	this.subList = getNewC();
//}
//
//public C getNewC() throws IllegalAccessException, InstantiationException {
//	return newC.newInstance();
//}

	@Override
	public C getSubList() {
		return this.subList;
	}

	@Override
	public void setSubList(final C subList) {
		this.subList = subList;
	}

	@Override
	public boolean isSubListEmpty() {
		if(getSubList() == null || getSubList().isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public int getSubListSize() {
		return 0;
	}

	@Override
	public double getDistanceTravelledMiles() {
		return 0;
	}
}
