package edu.sru.thangiah.zeus.tr.trWriteFile;

import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRDepot;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.TRDepotsList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import static java.lang.StrictMath.atan2;
import static java.lang.StrictMath.sqrt;

/**
 * Created by library-tlc on 5/26/15.
 */
public abstract class WriteFormat implements WriteFormatInterface{
protected TRDepotsList mainDepots;
protected boolean isMakeSeparateFile;
protected final int TYPE_STRING = 1;

protected String longSolutionPath;
protected String longSolutionFile;

protected String shortSolutionPath;
protected String shortSolutionFile;

protected String comparisonSolutionPath;
protected String comparisonSolutionFile;

protected final int TYPE_NUMERIC = 0;

public WriteFormat(){
	longSolutionPath = TRProblemInfo.outputPath;
	longSolutionFile = TRProblemInfo.longSolutionOutputFile;

	shortSolutionPath = TRProblemInfo.outputPath;
	shortSolutionFile = TRProblemInfo.shortSolutionOutputFile;

	comparisonSolutionPath = TRProblemInfo.outputPath;
	comparisonSolutionFile = comparisonSolutionFile;

}

protected Cell writeToNewCell(final Row row, final int newCellNumber, final String cellValue, final CellStyle cellStyle){
	Cell cell = row.createCell(newCellNumber);
	cell.setCellValue(cellValue);    //set text
	cell.setCellStyle(cellStyle);        //set style
	return cell;
}

protected Cell writeToNewCell(final Row row, final int newCellNumber, final String cellValue){
	Cell cell = row.createCell(newCellNumber);
	cell.setCellValue(cellValue);    //set text
	return cell;
}

protected Cell writeToNewCell(final Row row, final int newCellNumber, final int cellValue, final CellStyle cellStyle){
	Cell cell = row.createCell(newCellNumber);
	cell.setCellValue(cellValue);    //set text
	cell.setCellStyle(cellStyle);        //set style
	return cell;
}

protected Cell writeToNewCell(final Row row, final int newCellNumber, final int cellValue){
	Cell cell = row.createCell(newCellNumber);
	cell.setCellValue(cellValue);    //set text
	return cell;
}


protected Cell writeToNewCell(final Row row, final int newCellNumber, final double cellValue, final CellStyle cellStyle){
	Cell cell = row.createCell(newCellNumber);
	cell.setCellValue(cellValue);    //set text
	cell.setCellStyle(cellStyle);        //set style
	return cell;
}

protected Cell writeToNewCell(final Row row, final int newCellNumber, final double cellValue){
	Cell cell = row.createCell(newCellNumber);
	cell.setCellValue(cellValue);    //set text
	return cell;
}


protected Row createRowNumber(final XSSFSheet sheet, final int rowNumber){
	return sheet.createRow(rowNumber);
}


//METHOD
//this method calculates the euclidean distance between two points of data
protected double calculateDistance(double x1, double x2, double y1, double y2) {
	double xs = (x2 - x1) * (x2 - x1);        //no, this isn't haskell
	double ys = (y2 - y1) * (y2 - y1);
	return sqrt(xs + ys);                    //return the rooted solution
}//CALCULATE DISTANCE ENDS
// HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



//METHOD
//this method calculates the angle between two points of data
protected double calculateAngle(double x1, double x2, double y1, double y2) {
	double xs = (x2 - x1);        //no, this isn't haskell
	double ys = (y2 - y1);
	return atan2(ys, xs);
}//CALCULATE DISTANCE ENDS
// HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



}
