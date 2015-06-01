package edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions;

/**
 * Created by jks1010 on 5/29/2015.
 */
public interface ContainsSubObjectInterface<C extends ObjectInListInterface<C> & ObjectInListCoreInterface<C>> {
	public C getSubObject();

	public void setSubobject(final C subobject);

	public double getDistanceTravelledMiles();
}
