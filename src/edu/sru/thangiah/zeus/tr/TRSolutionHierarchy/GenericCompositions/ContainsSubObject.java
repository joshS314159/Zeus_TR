package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions;

/**
 * Created by jks1010 on 5/29/2015.
 */
public class ContainsSubObject<C extends ObjectInListInterface<C> & ObjectInListCoreInterface<C>> implements ContainsSubObjectInterface<C> {
	C subObject;

	@Override
	public C getSubObject() {
		return subObject;
	}

	@Override
	public void setSubobject(final C subobject) {
		this.subObject = subobject;
	}

	@Override
	public double getDistanceTravelledMiles() {
		return 0;
	}
}
