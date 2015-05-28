//PVRP PROBLEM
//CPSC 464
//AARON ROCKBURN; JOSHUA SARVER

//***********	DECLARATION_S_OTHER
// **********************************************************************************\\
// FUNCTION_START >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//PACKAGE TITLE

package edu.sru.thangiah.zeus.pvrp;


import edu.sru.thangiah.zeus.pvrp.pvrpcostfunctions.*;
//import edu.sru.thangiah.zeus.trGeneric.TRSolutionHierarchy.TRShipmentsList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
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
public class PVRPRoot {


//VARIABLES
final String FS = System.getProperty("file.separator");
//allows us to work with DOS based or Unix/Unix-like systems automagically



public PVRPRoot() throws Exception {

		//SETUP SOME PARAMETERS FOR THE PROBLEM INFO CLASS
		//these are all very important...the program will not function
		//properly without them
		PVRPProblemInfo.nodesLLLevelCostF = new PVRPNodesLLCostFunctions();
		PVRPProblemInfo.truckLevelCostF = new PVRPTruckCostFunctions();
		PVRPProblemInfo.truckLLLevelCostF = new PVRPTruckLLCostFunctions();
		PVRPProblemInfo.depotLevelCostF = new PVRPDepotCostFunctions();
		PVRPProblemInfo.depotLLLevelCostF = new PVRPDepotLLCostFunctions();
		PVRPProblemInfo.daysLLLevelCostF = new PVRPDaysLLCostFunctions();
		PVRPProblemInfo.daysLevelCostF = new PVRPDaysCostFunctions();
		PVRPProblemInfo.tempFileLocation = PVRPProblemInfo.workingDirectory + FS +
												   "temp";                  //temp file location
		PVRPProblemInfo.inputPath = PVRPProblemInfo.workingDirectory + FS + "data" + FS + "PVRP" + FS + "data" +
											FS;                                //input file location
		PVRPProblemInfo.outputPath = PVRPProblemInfo.workingDirectory + FS + "data" + FS + "PVRP" + FS + "results" +
											 FS;                            //output file location

		//PVRP SPECIFIC PARAMETERS
		//ADDED STATIC VARIABLES IN INHERITING PVRP CLASS
		PVRPProblemInfo.MAX_HORIZON = 14;
		PVRPProblemInfo.compareToInputPath = PVRPProblemInfo.workingDirectory + FS + "data" + FS + "PVRP" + FS +
													 "compareTo" + FS;
		PVRPProblemInfo.compareToInputFile = "Comparison_List.xlsx";
		PVRPProblemInfo.comparisonOutputFile = "Comparison_Results.xlsx";
		PVRPProblemInfo.longSolutionOutputFile = "All_Long_Solutions.xlsx";
		PVRPProblemInfo.shortSolutionOutputFile = "All_Short_Solutions.xlsx";


//ONE-PROBLEM (BETTER)
//	new PVRP(PROBLEM_NAME + ".xlsx");

//	for(int x = 15; x <= 32; x++){
//		new PVRP("p"+x+".xlsx");
//	}

		String problemName;
		boolean isMakeSeparateFile = false;
		String userInput = "";

		Scanner scanIn = new Scanner(System.in);
		File f = new File("thisFileProbablyDoesntExist.jpeg.exe");

		while (!f.exists()) {
			System.out.println("Enter a valid problem to solve (ex. p14):");
			userInput = scanIn.nextLine();
			f = new File(PVRPProblemInfo.inputPath + userInput + ".xlsx");
		}
		problemName = userInput + ".xlsx";


		userInput = "*";
		while (!userInput.equals("Y") && !userInput.equals("N")) {
			System.out.println("Would you like a separate output file created for this problem? (Y/N)");
			userInput = scanIn.nextLine().toUpperCase();
			if (userInput.equals("Y")) {
				isMakeSeparateFile = true;
			} else {
				isMakeSeparateFile = false;
			}
		}

		System.out.println("Choose an insertion heuristic: (1/2/3)");
		while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3")) {
			System.out.println("1. Next Closest Distance to Depot");
			System.out.println("2. Next Smallest Angle to Depot");
			System.out.println("3. Next Closest Angle and Distance to Depot");
			userInput = scanIn.nextLine();
		}

		if (userInput.equals("1")) {
			new PVRP(problemName, isMakeSeparateFile, new PVRPClosestEuclideanDistToDepot());
		} else if (userInput.equals("2")) {
			new PVRP(problemName, isMakeSeparateFile, new PVRPSmallestPolarAngleShortestDistToDepot());
		} else if (userInput.equals("3")) {
			new PVRP(problemName, isMakeSeparateFile, new PVRPSmallestPolarAngleToDepot());
		}





}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

public PVRPRoot(PVRPShipmentLinkedList nodes) throws IOException, InvalidFormatException {
		//SETUP SOME PARAMETERS FOR THE PROBLEM INFO CLASS
		//these are all very important...the program will not function
		//properly without them
		PVRPProblemInfo.nodesLLLevelCostF = new PVRPNodesLLCostFunctions();
		PVRPProblemInfo.truckLevelCostF = new PVRPTruckCostFunctions();
		PVRPProblemInfo.truckLLLevelCostF = new PVRPTruckLLCostFunctions();
		PVRPProblemInfo.depotLevelCostF = new PVRPDepotCostFunctions();
		PVRPProblemInfo.depotLLLevelCostF = new PVRPDepotLLCostFunctions();
		PVRPProblemInfo.daysLLLevelCostF = new PVRPDaysLLCostFunctions();
		PVRPProblemInfo.daysLevelCostF = new PVRPDaysCostFunctions();
		PVRPProblemInfo.tempFileLocation = PVRPProblemInfo.workingDirectory + FS +
												   "temp";                  //temp file location
		PVRPProblemInfo.inputPath = PVRPProblemInfo.workingDirectory + FS + "data" + FS + "TR" + FS + "problem" +
											FS;                                //input file location
		PVRPProblemInfo.outputPath = PVRPProblemInfo.workingDirectory + FS + "data" + FS + "TR" + FS + "results" +
											 FS;                            //output file location

		//PVRP SPECIFIC PARAMETERS
		//ADDED STATIC VARIABLES IN INHERITING PVRP CLASS
		PVRPProblemInfo.MAX_HORIZON = 7;
//		PVRPProblemInfo.compareToInputPath = PVRPProblemInfo.workingDirectory + FS + "data" + FS + "PVRP" + FS +
//													 "compareTo" + FS;
//		PVRPProblemInfo.compareToInputFile = "Comparison_List.xlsx";
//		PVRPProblemInfo.comparisonOutputFile = "Comparison_Results.xlsx";
		PVRPProblemInfo.longSolutionOutputFile = "All_Long_Solutions.xlsx";
		PVRPProblemInfo.shortSolutionOutputFile = "All_Short_Solutions.xlsx";



		String problemName;
		boolean isMakeSeparateFile = false;
		String userInput = "";

		problemName = "TrashRoutes-Frequency.xlsx";
		isMakeSeparateFile = true;
		userInput = "1";

//		if (userInput.equals("1")) {
//			new PVRP(problemName, isMakeSeparateFile, new PVRPClosestEuclideanDistToDepot(), nodes);
//		} else if (userInput.equals("2")) {
//			new PVRP(problemName, isMakeSeparateFile, new PVRPSmallestPolarAngleToDepot(), nodes);
//		} else if (userInput.equals("3")) {
//			new PVRP(problemName, isMakeSeparateFile, new PVRPSmallestPolarAngleShortestDistToDepot(), nodes);
//		}
	}

}


