package edu.sru.thangiah.zeus.tr.trReadFile;

import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRShipmentsList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by library-tlc on 5/26/15.
 */
public interface ReadFormatInterface {


public void readFiles() throws InvocationTargetException, InvalidFormatException, InstantiationException, IllegalAccessException, IOException;
public void readDataFromFile() throws IOException, InvalidFormatException, InstantiationException, IllegalAccessException, InvocationTargetException;

	public String getProblemPath();

	public void setProblemPath(final String problemPath);

	public String getProblemFileName();

	public void setProblemFileName(final String problemFileName);

}
