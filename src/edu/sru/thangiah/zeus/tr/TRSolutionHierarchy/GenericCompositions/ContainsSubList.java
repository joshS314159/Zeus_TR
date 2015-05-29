package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions;

/**
 * Created by jks1010 on 5/29/2015.
 */
public class ContainsSubList implements ContainsSubListInterface {
	Object subList;

	@Override
	public DoublyLinkedListInterface getSubList() {
		return this.subList;
	}

	@Override
	public void setSubList(final DoublyLinkedListInterface subList) {
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
