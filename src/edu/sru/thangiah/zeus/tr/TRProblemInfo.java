//PVRP PROBLEM
//CPSC 464
//AARON ROCKBURN; JOSHUA SARVER

//***********	DECLARATION_S_OTHER
// **********************************************************************************\\
// FUNCTION_START >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//PACKAGE TITLE
package edu.sru.thangiah.zeus.tr;

//IMPORTS


import edu.sru.thangiah.zeus.core.CostFunctions;
import edu.sru.thangiah.zeus.core.ProblemInfo;


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
public class TRProblemInfo
        extends ProblemInfo {


    public final static int WORK_HOURS_PER_DAY              = 8;
    public static final int MAX_COMBINATIONS                = 20;
    public static final int MAX_HORIZON = 21;
    public static boolean areDaysFlexible = false;


//NEEDED TO OVERRIDE THE DEFAULT VALUE (PVRP HAD VALUES GREATER
//THAN VRP BY DR. SAM) -- WHAT THIS IS BASED ON
    /**
     * STATIC JUSTIFICATION
     * There is a cost function for each object
     * used in the hierarchy in base CostFunctions
     * but days the dayLinkedList didn't have one
     * <p/>
     * All file input and output paths and some file-
     * names are put here by default, so when adding
     * some inputs and outputs for comparison and long
     * solution it made sense to use it here
     * <p/>
     * We had to change the MAX_HORIZON simple because
     * our PVRP problems had a higher maximum horizon.
     */

//VARIABLES
    public static CostFunctions daysLLLevelCostF;
    //EACH OBJECT IN THE DEPOT HIERARCHY HAS ONE OF THESE
//EXCEPT, OUT OF WHAT WE USED, DAYS
    public static CostFunctions daysLevelCostF;
    //CONTAINS THE PATH TO OUR COMPARE-TO FILE
    public static String compareToInputPath      = new String();
    //CONTAINS THE PATH TO OUR COMPARE-TO FILENAME
    public static String compareToInputFile      = new String();
    //CONTAINS THE FILENAME OF OUR COMPARISON TO US OUTPUT
    public static String comparisonOutputFile    = new String();
    //CONTAINS THE NAME OF THE SHORT SOLUTION OUTPUT FILE
    public static String shortSolutionOutputFile = new String();
    //CONTAINS THE NAME OF THE SHORT SOLUTION INPUT FILE
    public static String longSolutionOutputFile  = new String();

    public static String penaltiesInputFile = new String();

    public static String delayTypesInputFile = new String();

    public static String problemFileName = new String();

    public static int startHour;
    public static int startMinute;
    public static int averageVelocity;

    public static TRPenaltiesList mainPenalties;

    public static boolean addPenaltyPerBin;

    public static String problemFormatType = new String();



    TRProblemInfo() {


    }


}
