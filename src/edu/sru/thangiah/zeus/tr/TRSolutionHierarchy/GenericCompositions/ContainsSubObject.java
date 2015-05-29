package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions;

/**
 * Created by jks1010 on 5/29/2015.
 */
public class ContainsSubObject implements ContainsSubObjectInterface {
	Object subObject;

	@Override
	public Object getSubObject() {
		return subObject;
	}

	@Override
	public void setSubobject(final ObjectInListInterface subobject) {
		this.subObject = subobject;
	}

	@Override
	public double getDistanceTravelledMiles() {
		return 0;
	}
}
