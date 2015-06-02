package edu.sru.thangiah.zeus.tr.trReadFile;

import com.sun.tools.javac.util.ArrayUtils;
import edu.sru.thangiah.zeus.tr.TRPenalty;
import edu.sru.thangiah.zeus.tr.TRProblemInfo;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.*;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.GenericCompositions.ContainsSubList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class TRReadFormat extends ReadFormat {
private String penaltiesPath;
private String penaltiesFileName;
private String delaysPath;
private String delaysFileName;

private TRDelayTypeList mainDelays;

public TRReadFormat(final TRShipmentsList mainShipments, final TRDepotsList mainDepots, final TRDelayTypeList mainDelays) {
	super(mainShipments, mainDepots);
	this.mainDelays = mainDelays;

	this.penaltiesPath = TRProblemInfo.inputPath;
	this.penaltiesFileName = TRProblemInfo.penaltiesInputFile;

	this.delaysPath = TRProblemInfo.inputPath;
	this.delaysFileName = TRProblemInfo.delayTypesInputFile;
}


public void readFiles() throws InvocationTargetException, InvalidFormatException, InstantiationException, IllegalAccessException, IOException {
	readPenaltiesFromFile();
	readDelayTypesFromFile();
	readDataFromFile();
}


public void setPenaltiesPathAndName(final String penaltiesPath, final String penaltiesFileName) {
	this.penaltiesPath = penaltiesPath;
	this.penaltiesFileName = penaltiesFileName;
}

public void setDelayPathAndName(final String delaysPath, final String delaysFileName) {
	this.delaysPath = delaysPath;
	this.delaysFileName = delaysFileName;
}


public boolean readDelayTypesFromFile() throws IOException {
	if(delaysPath == null || delaysFileName == null) {
		return false;
	}
	final int DELAY_NAME = 0;
	final int DELAY_LENGTH = 1;

	final FileInputStream file = new FileInputStream(new File(this.delaysPath + this.delaysFileName));

	XSSFWorkbook workbook = new XSSFWorkbook(file);
	XSSFSheet sheet = workbook.getSheetAt(0);
	Row row;
	Cell cell;

	for(int rowCounter = 1; rowCounter < sheet.getPhysicalNumberOfRows(); rowCounter++) {
		row = sheet.getRow(rowCounter);
		TRDelayType theDelay = new TRDelayType();
		for(int columnCounter = 0; columnCounter < row.getPhysicalNumberOfCells(); columnCounter++) {
			cell = row.getCell(columnCounter);
			switch(columnCounter) {
				case DELAY_NAME:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					theDelay.setDelayTypeName(cell.getStringCellValue());
					break;
				case DELAY_LENGTH:
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					theDelay.setDelayTimeInMinutes((int) cell.getNumericCellValue());
					break;
			}

		}
		mainDelays.insertAfterLastIndex(theDelay);
	}
	return true;
}


public boolean readPenaltiesFromFile() throws IOException {
	if(penaltiesFileName == null || penaltiesPath == null) {
		return false;
	}
	final int BUILDING_TYPE = 0;
	final int START_TIME = 1;
	final int END_TIME = 2;
	final int WEEKDAYS = 3;
	final int PENALTY = 4;

	final FileInputStream file = new FileInputStream(new File(penaltiesPath + penaltiesFileName));

	XSSFWorkbook workbook = new XSSFWorkbook(file);
	XSSFSheet sheet = workbook.getSheetAt(0);
	Row row;
	Cell cell;

	for(int rowCounter = 1; rowCounter < sheet.getPhysicalNumberOfRows(); rowCounter++) {
		row = sheet.getRow(rowCounter);
		TRPenalty thePenalty = new TRPenalty();
		for(int columnCounter = 0; columnCounter < row.getPhysicalNumberOfCells(); columnCounter++) {
			cell = row.getCell(columnCounter);
			switch(columnCounter) {
				case BUILDING_TYPE:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					thePenalty.setBuildingType(cell.getStringCellValue());
					break;
				case START_TIME:
					String startTime;
					if(DateUtil.isCellDateFormatted(cell)) {
						startTime = new DataFormatter().formatCellValue(cell);
					} else {
						startTime = String.valueOf((int) (cell.getNumericCellValue()));
					}
					String[] startTimeSplit = startTime.split(":");
					if(startTimeSplit.length == 2) {
						int hour = Integer.parseInt(startTimeSplit[0]);
						if(hour >= 0 && hour <= 23) {
							thePenalty.setStartHour(hour);
						}

						int minute = Integer.parseInt(startTimeSplit[1]);
						if(minute >= 0 && minute <= 59) {
							thePenalty.setStartMinute(minute);
						}
					} else {
						System.out.println("ERROR READING START TIME IN");
					}
					break;
				case END_TIME:
					String endTime;
					if(DateUtil.isCellDateFormatted(cell)) {
						startTime = new DataFormatter().formatCellValue(cell);
					} else {
						startTime = String.valueOf((int) (cell.getNumericCellValue()));
					}
					String[] endTimeSplit = startTime.split(":");
					if(endTimeSplit.length == 2) {
						int hour = Integer.parseInt(endTimeSplit[0]);
						if(hour >= 0 && hour <= 23) {
							thePenalty.setEndHour(hour);
						}

						int minute = Integer.parseInt(endTimeSplit[1]);
						if(minute >= 0 && minute <= 59) {
							thePenalty.setEndMinute(minute);
						}
					} else {
						System.out.println("ERROR READING END TIME IN");
					}
					break;
				case WEEKDAYS:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					String theDays = cell.getStringCellValue();
					for(int x = 0; x < theDays.length(); x++) {
						char aChar = theDays.charAt(x);
						switch(aChar) {
							case 'M':
								thePenalty.setMondayPenalty();
								break;
							case 'T':
								thePenalty.setTuesdayPenalty();
								break;
							case 'W':
								thePenalty.setWednesdayPenalty();
								break;
							case 'R':
								thePenalty.setThursdayPenalty();
								break;
							case 'F':
								thePenalty.setFridayPenalty();
								break;
						}
					}
//                    thePenalty.setStartTime(cell.getStringCellValue());
					break;
				case PENALTY:
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					thePenalty.setPenaltyInMinutes((int) cell.getNumericCellValue());
					break;
			}
		}
		TRProblemInfo.mainPenalties.insertAfterLastIndex(thePenalty);
	}
	return true;
}


public void readDataFromFile(/*final String TRFileName*/)
		throws IOException, InvalidFormatException, InstantiationException, IllegalAccessException,
		InvocationTargetException {

	this.numberDaysToMake = TRProblemInfo.NUMBER_DAYS_SERVICED;
	this.numberTrucksToMake = 1;
	this.numberDepotsToMake = 1;

	this.isDemandRestraint = false;
	this.isDistanceRestraint = false;

	if(this.problemFileName == null || this.problemPath == null) {
//			return false;
	}
	final int LOCATION = 0;
	final int PICKUP_POINT = 1;
	final int LATITUDE = 2;
	final int LONGITUDE = 3;
	final int FREQUENCY = 4;
	final int BINS = 5;
	final int DAY_ONE = 6;
	final int DAY_TWO = 7;
	final int DAY_THREE = 8;
	final int DAY_FOUR = 9;
	final int DAY_FIVE = 10;
	final int DAY_SIX = 11;
	final int BUILDING_TYPE = 12;
	final int CLASSES_NEARBY = 13;
	final int FOOD_NEARBY = 14;
	final int PICKUP_ORDER = 15;
	final int NUMBER_COLUMNS = 16;

	final int NUMBER_NON_NODE_ROWS = 2;

	final String TIP_CART = "tip-cart";

	final FileInputStream file = new FileInputStream(new File(this.problemPath + this.problemFileName));

	XSSFWorkbook workbook = new XSSFWorkbook(file);
	XSSFSheet sheet = workbook.getSheetAt(0);
	Row row;
	Cell cell;


	TRProblemInfo.noOfShips = sheet.getPhysicalNumberOfRows() - NUMBER_NON_NODE_ROWS;
	for(int rowCounter = 1; rowCounter < sheet.getPhysicalNumberOfRows(); rowCounter++) {

		row = sheet.getRow(rowCounter);
		TRShipment newShipment = new TRShipment();
		double latitude = Double.MAX_VALUE;

		for(int columnCounter = 0; columnCounter < NUMBER_COLUMNS; columnCounter++) {
			cell = row.getCell(columnCounter);

			switch(columnCounter) {
				case LOCATION:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					newShipment.setPickupPointName(cell.getStringCellValue());
					break;
				case PICKUP_POINT:
					//cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					//					int test = (int) cell.getNumericCellValue();
					cell.setCellType(Cell.CELL_TYPE_STRING);
					String pointName = cell.getStringCellValue();
					double point = Double.valueOf(pointName);
					newShipment.setNodeNumber((int) point);
					//					newShipment.setNodeNumber(test);
					break;
				case LATITUDE:
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					latitude = cell.getNumericCellValue();
					//					newShipment.setLatitude(cell.getNumericCellValue());
					break;
				case LONGITUDE:
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					double longitude = cell.getNumericCellValue();
					newShipment.getCoordinates().setCoordinates(longitude, latitude);
					break;
				case FREQUENCY:
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					newShipment.setVisitFrequency((int) cell.getNumericCellValue());
					break;
				case BINS:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					if(cell.getStringCellValue().equals(TIP_CART)) {
						newShipment.setIsTipCart(true);
						newShipment.setDelayType((TRDelayType) mainDelays.getByDelayName("Tipcart"));
						newShipment.setNumberOfBins(1);
					} else {
						newShipment.setIsTipCart(false);
						int numberBins = Integer.parseInt(cell.getStringCellValue());
						newShipment.setDelayType((TRDelayType) mainDelays.getByDelayName("Bin"));
						newShipment.setNumberOfBins(numberBins);
					}
					break;
				case DAY_ONE:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					newShipment.setSingleDayVisitation(cell.getStringCellValue());
					break;
				case DAY_TWO:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					newShipment.setSingleDayVisitation(cell.getStringCellValue());
					break;
				case DAY_THREE:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					newShipment.setSingleDayVisitation(cell.getStringCellValue());
					break;
				case DAY_FOUR:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					newShipment.setSingleDayVisitation(cell.getStringCellValue());
					break;
				case DAY_FIVE:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					newShipment.setSingleDayVisitation(cell.getStringCellValue());
					break;
				case DAY_SIX:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					newShipment.setSingleDayVisitation(cell.getStringCellValue());
					break;
				case BUILDING_TYPE:
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					newShipment.setBuildingType((int) cell.getNumericCellValue());
					break;
				case CLASSES_NEARBY:
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					newShipment.setNumberOfNearbyClasses((int) cell.getNumericCellValue());
					break;
				case FOOD_NEARBY:
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					newShipment.setNumberOfNearbyFoods((int) cell.getNumericCellValue());
					break;
				case PICKUP_ORDER:
					cell.setCellType(Cell.CELL_TYPE_STRING);
					if(cell.getStringCellValue().equals("None")) {
						newShipment.setPickupOrder(cell.getStringCellValue());
					} else {
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						newShipment.setRequiredPreviousPickupPoint((int) cell.getNumericCellValue());
					}
					break;
				default:
					break;
			}
		}

		if(newShipment.getVisitFrequency() > 0) {

			int[] combinations = new int[TRProblemInfo.MAX_COMBINATIONS];
			int currentCombination[][] = new int[TRProblemInfo.MAX_HORIZON+5][TRProblemInfo.MAX_COMBINATIONS];
			int combinationCounter = 0;
			final boolean[] visitationList = newShipment.getDaysVisited();
			boolean[] editableList = newShipment.getDaysVisited();
			boolean[] shiftedList = new boolean[visitationList.length];
			boolean isDoneShifting = false;
			while(!isDoneShifting){
//			for(int j = 0; j < this.numberDaysToMake; j++){
				shiftedList[0] = editableList[editableList.length - 1];
				for(int i = 1; i < editableList.length; i++) {
					shiftedList[i] = editableList[i - 1];
				}

				currentCombination[combinationCounter] = createVisitationCombination(shiftedList);
				combinationCounter++;

				for(int i = 0; i < editableList.length; i++){
					editableList[i] = shiftedList[i];
				}

				boolean status = true;
				for(int i = 0; i < editableList.length; i++){

//					if(visitationList[i] != shiftedList[i]){
//						break;
//					}
					status = status && (visitationList[i] == shiftedList[i]);
				}
				isDoneShifting = status;




			}

//			for(int l = 0; l < combinationCounter; l++) {
//				currentCombination[l] = mainShipments.getCurrentComb(combinations, l, this.numberDaysToMake);
//			}
			newShipment.setCurrentComb(currentCombination, combinationCounter);
			newShipment.chooseRandomVisitCombination();
			newShipment.setNoComb(combinationCounter);


//			if(newShipment.getVisitFrequency() != 0) {
				mainShipments.insertAfterLastIndex(newShipment);

//			}
		}
	}
	mainShipments.makeSchedule();
	System.out.println("Setting up depot");

	this.depotCoordinates = mainShipments.getLast().getCoordinates();
	mainShipments.removeByObject(mainShipments.getLast());

	this.createHierarchy();
}


private int[] createVisitationCombination(final boolean[] daysVisited){
	int[] newCombination = new int[daysVisited.length];
	for(int i = 0; i < daysVisited.length; i++){
		if(daysVisited[i]){
			newCombination[i] = 1;
		}
		else{
			newCombination[i] = 0;
		}
	}
	return newCombination;
}


}