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

        setupProblemInfoCostFunctions();
        setupProblemInfoFileLocations();
        //	askUserInputAndRun();


        new TR("TrashRoutes-Frequency.xlsx", false, new TRClosestDistanceToDepot());


    }//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




    private void setupProblemInfoCostFunctions() {
        //SETUP SOME PARAMETERS FOR THE PROBLEM INFO CLASS
        //these are all very important...the program will not function
        //properly without them
        TRProblemInfo.nodesLLLevelCostF = new TRNodesListCost();
        TRProblemInfo.truckLevelCostF = new TRTruckCost();
        TRProblemInfo.truckLLLevelCostF = new TRTrucksListCost();
        TRProblemInfo.depotLevelCostF = new TRDepotCost();
        TRProblemInfo.depotLLLevelCostF = new TRDepotsListCost();
        TRProblemInfo.daysLLLevelCostF = new TRDaysListCost();
        TRProblemInfo.daysLevelCostF = new TRDayCost();

    }




    private void setupProblemInfoFileLocations() {
        TRProblemInfo.tempFileLocation = TRProblemInfo.workingDirectory + FS +
                "temp";                  //temp file location
        TRProblemInfo.inputPath = TRProblemInfo.workingDirectory + FS + "data" + FS + "TR" + FS + "problem" +
                FS;                                //input file location
        TRProblemInfo.outputPath = TRProblemInfo.workingDirectory + FS + "data" + FS + "TR" + FS + "results" +
                FS;                            //output file location

        //PVRP SPECIFIC PARAMETERS
        //ADDED STATIC VARIABLES IN INHERITING PVRP CLASS
        //	TRProblemInfo.compareToInputPath = TRProblemInfo.workingDirectory + FS + "data" + FS + "TR" + FS +
        //											   "compareTo" + FS;
        //	TRProblemInfo.compareToInputFile = "Comparison_List.xlsx";
        //	TRProblemInfo.comparisonOutputFile = "Comparison_Results.xlsx";
        TRProblemInfo.longSolutionOutputFile = "All_Long_Solutions.xlsx";
        TRProblemInfo.shortSolutionOutputFile = "All_Short_Solutions.xlsx";

        TRProblemInfo.penaltiesInputFile = "Penalty.xlsx";
    }




    private void askUserInputAndRun()
            throws IllegalAccessException, InvalidFormatException, IOException, InstantiationException,
            NoSuchMethodException, InvocationTargetException {


        Scanner scanIn = new Scanner(System.in);
        File f = new File("thisFileProbablyDoesntExist.jpeg.exe");

        while(!f.exists()) {
            System.out.println("Enter a valid problem to solve (ex. p14):");
            userInput = scanIn.nextLine();
            f = new File(TRProblemInfo.inputPath + userInput + ".xlsx");
        }
        problemName = userInput + ".xlsx";


        userInput = "*";
        while(!userInput.equals("Y") && !userInput.equals("N")) {
            System.out.println("Would you like a separate output file created for this problem? (Y/N)");
            userInput = scanIn.nextLine().toUpperCase();
            if(userInput.equals("Y")) {
                isMakeSeparateFile = true;
            }
            else {
                isMakeSeparateFile = false;
            }
        }

        System.out.println("Choose an insertion heuristic: (1/2/3)");
        while(!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3")) {
            System.out.println("1. Next Closest Distance to Depot");
            System.out.println("2. Next Smallest Angle to Depot");
            System.out.println("3. Next Closest Angle and Distance to Depot");
            userInput = scanIn.nextLine();
        }

        if(userInput.equals("1")) {
            selectionTypeObject = new TRClosestDistanceToDepot();
        }
        else if(userInput.equals("2")) {
            selectionTypeObject = new TRSmallestAngleToDepot();
        }
        else if(userInput.equals("3")) {
            selectionTypeObject = new TRSmallestAngleClosestDistanceToDepot();
        }


    }


}


