package edu.sru.thangiah.zeus.tr.trReadFile;

import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRDepotsList;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRShipmentsList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by library-tlc on 5/26/15.
 */
public class PVRPReadFormat extends ReadFormat  {
public PVRPReadFormat(TRShipmentsList mainShipments, TRDepotsList mainDepots) {
	super(mainShipments, mainDepots);
}

@Override
public void readFiles() throws InvocationTargetException, InvalidFormatException, InstantiationException, IllegalAccessException, IOException {

}

@Override
public boolean readDataFromFile() throws IOException, InvalidFormatException, InstantiationException, IllegalAccessException, InvocationTargetException {
	return false;
}
}
