package edu.sru.thangiah.zeus.tr.trWriteFile;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by library-tlc on 5/26/15.
 */
public interface WriteFormatInterface {

public void writeLongSolution() throws IOException;
public void writeShortSolution() throws IOException;
public void writeComparisonResults() throws IOException;
public void writeAll() throws IOException;
}
