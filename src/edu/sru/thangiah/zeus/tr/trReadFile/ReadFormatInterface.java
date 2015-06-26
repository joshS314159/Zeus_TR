package edu.sru.thangiah.zeus.tr.trReadFile;

import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.*;
import edu.sru.thangiah.zeus.tr.TRTruckType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by library-tlc on 5/26/15.
 */
public interface ReadFormatInterface {


public void readFiles() throws InvocationTargetException, InvalidFormatException, InstantiationException, IllegalAccessException, IOException;
public void readDataFromFile() throws IOException, InvalidFormatException, InstantiationException, IllegalAccessException, InvocationTargetException;

	String getProblemPath();

	void setProblemPath(final String problemPath);

	String getProblemFileName();

	void setProblemFileName(final String problemFileName);

	void createHierarchy();
}


