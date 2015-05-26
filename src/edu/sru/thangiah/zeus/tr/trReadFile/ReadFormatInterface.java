package edu.sru.thangiah.zeus.tr.trReadFile;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by library-tlc on 5/26/15.
 */
public interface ReadFormatInterface {


public void readFiles() throws InvocationTargetException, InvalidFormatException, InstantiationException, IllegalAccessException, IOException;
public boolean readDataFromFile() throws IOException, InvalidFormatException, InstantiationException, IllegalAccessException, InvocationTargetException;

}
