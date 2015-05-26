//PVRP PROBLEM
//CPSC 464
//AARON ROCKBURN; JOSHUA SARVER

//***********	DECLARATION_S_OTHER
// **********************************************************************************\\
// FUNCTION_START >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//PACKAGE TITLE

package edu.sru.thangiah.zeus.tr;


import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Selection.TRClosestDistanceToDepot;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Selection.TRSmallestAngleClosestDistanceToDepot;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Selection.TRSmallestAngleToDepot;
import edu.sru.thangiah.zeus.tr.trCostFunctions.*;
import edu.sru.thangiah.zeus.tr.trReadFile.TRReadFormat;
import edu.sru.thangiah.zeus.tr.trWriteFile.TRWriteFormat;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;


/**
 * PVRP (Periodic Vehicle Routing)
 * Routes vehicles to various nodes over a given number of days
 * <p/>
 * Version 1.0
 * <p/>
 * 10/17/14
 * <p/>
 * AUTHORS
 * Aaron Rockburn and Joshua Sarver
 * Slippery Rock University of Pa
 * CPSC 464 - Fall 2014
 * <p/>
 * COPYLEFT
 * Attribution-ShareAlike 4.0 International
 * <p/>
 * BASED ON
 * Dr. Sam R. Thangiah's work at Slippery Rock University of Pa
 * A Heuristic for the Periodic Vehicle Routing Problem by M. Gaudioso, G. Paletta
 * Chou
 * The Periodic Vehicle Routing Problem by S. Coene, A. Arnout and F. Spieksma
 * A Variable Neighborhood Search Heuristic for Periodic Routing Problems by Vera C. Hemmelmayr, Karl F. Doerner§,
 * Richard F. Hartl
 * <p/>
 * Methods are generally sorted by breadth-first order
 */

//CLASS
public class TRRoot {


    //VARIABLES
    private final String  FS                 = System.getProperty("file.separator");
    private       String  problemName        = "";
    private       boolean isMakeSeparateFile = false;
    private       String  userInput          = "";
    private Object selectionTypeObject;

//allows us to work with DOS based or Unix/Unix-like systems automagically




    public TRRoot()
            throws Exception {
        TRProblemInfo.nodesLLLevelCostF = new TRNodesListCost();
        TRProblemInfo.truckLevelCostF = new TRTruckCost();
        TRProblemInfo.truckLLLevelCostF = new TRTrucksListCost();
        TRProblemInfo.depotLevelCostF = new TRDepotCost();
        TRProblemInfo.depotLLLevelCostF = new TRDepotsListCost();
        TRProblemInfo.daysLLLevelCostF = new TRDaysListCost();
        TRProblemInfo.daysLevelCostF = new TRDayCost();


        TRProblemInfo.tempFileLocation = TRProblemInfo.workingDirectory + FS + "temp";                  //temp file location
        TRProblemInfo.inputPath = TRProblemInfo.workingDirectory + FS + "data" + FS + "TR" + FS + "problem" + FS;                                //input file location
        TRProblemInfo.outputPath = TRProblemInfo.workingDirectory + FS + "data" + FS + "TR" + FS + "results" + FS;                            //output file location
        TRProblemInfo.compareToInputPath = TRProblemInfo.workingDirectory + FS + "data" + FS + "TR" + FS + "compareTo" + FS;
//        TRProblemInfo.compareToInputFile = "old_five_day.xlsx";
        TRProblemInfo.compareToInputFile = "old_six_day.xlsx";
        TRProblemInfo.comparisonOutputFile = "Comparison_Results.xlsx";
        TRProblemInfo.longSolutionOutputFile = "All_Long_Solutions.xlsx";
        TRProblemInfo.shortSolutionOutputFile = "All_Short_Solutions.xlsx";
        TRProblemInfo.penaltiesInputFile = "Penalty.xlsx";
        TRProblemInfo.delayTypesInputFile = "DelayTimes.xlsx";

        TRProblemInfo.startHour = 8;
        TRProblemInfo.startMinute = 00;
        TRProblemInfo.averageVelocity = 12;
        TRProblemInfo.numDepots = 1;
        TRProblemInfo.addPenaltyPerBin = true;
        TRProblemInfo.mainPenalties = new TRPenaltiesList();
        TRProblemInfo.problemFileName = "TrashRoutes-Frequency.xlsx";
        new TR(/*"TrashRoutes-Frequency.xlsx", */false, new TRClosestDistanceToDepot(), TRReadFormat.class, TRWriteFormat.class);


    }//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




}


