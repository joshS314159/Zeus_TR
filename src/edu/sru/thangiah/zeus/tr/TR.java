package edu.sru.thangiah.zeus.tr;

//IMPORTS


import edu.sru.thangiah.zeus.core.ProblemInfo;
import edu.sru.thangiah.zeus.core.Settings;
import edu.sru.thangiah.zeus.gui.ZeusGui;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.Heuristics.Insertion.TRGreedyInsertion;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.*;
import edu.sru.thangiah.zeus.tr.trQualityAssurance.TRQA;
import edu.sru.thangiah.zeus.tr.trReadFile.PVRPReadFormat;
import edu.sru.thangiah.zeus.tr.trReadFile.ReadFormat;
import edu.sru.thangiah.zeus.tr.trReadFile.TRReadFormat;
import edu.sru.thangiah.zeus.tr.trWriteFile.PVRPWriteFormat;
import edu.sru.thangiah.zeus.tr.trWriteFile.TRWriteFormat;
import edu.sru.thangiah.zeus.tr.trWriteFile.WriteFormat;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Vector;

import static java.lang.StrictMath.atan2;
import static java.lang.StrictMath.sqrt;


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
public class TR {

    private final int TYPE_NUMERIC = 0;
    //track the CPU processing time
    private final int TYPE_STRING  = 1;
    //CLASS VARIABLES
    private long startTime, endTime;

    private TRShipmentsList mainShipments = new TRShipmentsList();

    private TRDepotsList mainDepots = new TRDepotsList();
    private TRDelayTypeList mainDelays = new TRDelayTypeList();

    private ReadFormat mainReader;
	private WriteFormat mainWriter;

//    private TRPenaltiesList mainPenalties = new TRPenaltiesList();
//depots linked list for the VRP problem

    private boolean isMakeSeparateFile;


//CONSTRUCTOR
//this constructor will convert a width-delimited file into a XLSX file which will allow PVRP to read it




    //CONSTRUCTOR
//this constructor is called by PVRPRoot and launches reading in data, routing vehicles, and printing out information
    public TR(/*String dataFile, */boolean isMakeSeparateFile, Object selectionTypeObject, Class<? extends ReadFormat> classReader, Class<? extends WriteFormat> classWriter)
            throws IOException, InvalidFormatException, IllegalAccessException, InvocationTargetException,
            InstantiationException, NoSuchMethodException {

        this.isMakeSeparateFile = isMakeSeparateFile;
		if(classReader == TRReadFormat.class){
			this.mainReader = new TRReadFormat(mainShipments, mainDepots, mainDelays);
		}
		else if(classReader == PVRPReadFormat.class){

		}

		if(classWriter == TRWriteFormat.class){
			this.mainWriter = new TRWriteFormat(isMakeSeparateFile, mainDepots);
		}
		else if(classWriter == PVRPWriteFormat.class){

		}


        //VARIABLES
        TRProblemInfo.truckTypes = new Vector();
        //Truck types are placed into a vector

        //READ DATA

        printDataToConsole();
        //prints the routes to the console
        System.out.println("reading penalties");
//        readPenaltiesFromFile();
//		mainReader.readPenaltiesFromFile();
        System.out.println("reading delays");
//        readDelayTypesFromFile();
//		mainReader.readDelayTypesFromFile();

        Settings.printDebug(Settings.COMMENT,
                "Read Data File: " + TRProblemInfo.inputPath + TRProblemInfo.problemFileName);    //store some debug data
//        readDataFromFile(TRProblemInfo.inputPath + dataFile);                          //reads data from file
//		mainReader.readDataFromFile();
		((TRReadFormat) mainReader).readFiles();


        //PROCESS PREPARATION

        if(mainShipments.getHead() == null) {
            Settings.printDebug(Settings.ERROR, "TR: Shipment linked list is empty");
        }
        //If the shipments linked list (our problem data) isn't null


        TRProblemInfo.selectShipType = selectionTypeObject;

        //		Settings.printDebug(Settings.COMMENT, selectionTypeObject.WhoAmI());

        //	PVRPProblemInfo.selectShipType = new PVRPClosestEuclideanDistToDepot();
        //	Settings.printDebug(Settings.COMMENT, PVRPClosestEuclideanDistToDepot.WhoAmI());
        //This sets our heuristic to choose the next shipment
        //there are several more commented out under this

        //	PVRPProblemInfo.selectShipType = new PVRPSmallestPolarAngleToDepot();
        //	Settings.printDebug(Settings.COMMENT, PVRPSmallestPolarAngleToDepot.WhoAmI());

        //	PVRPProblemInfo.selectShipType = new PVRPSmallestPolarAngleShortestDistToDepot();
        //	Settings.printDebug(Settings.COMMENT, PVRPSmallestPolarAngleShortestDistToDepot.WhoAmI());


        TRProblemInfo.insertShipType = new TRGreedyInsertion();
        Settings.printDebug(Settings.COMMENT, TRGreedyInsertion.WhoAmI());
        //Sets up our shipment insertion type -- we only have one hueristic for this


        startTime = System.currentTimeMillis();
        //We wanna time how long it takes to create our solution
        //Start time records the starting time of processing


        //PROCESSING
        createInitialRoutes();
        printRoutesToConsole();
        //this creates our initial solution routes
        //although this code doesn't have it, local optimization
        //would improve upon the initial routes created here


        //QUALITY CHECK
        Settings.printDebug(Settings.COMMENT, "Created Initial Routes ");
        //Get the initial solution
        //Depending on the Settings status, display information on the routes
        //Trucks used, total demand, dist, travel time and cost


        System.out.println("Starting QA");
        //creates a new object that checks out quality
        if(new TRQA(mainDepots, mainShipments).runQA() == false) {
            //if QA failed -- bad quality
            Settings.printDebug(Settings.ERROR, "SOME QA FAILED!");
        }
        else {
            //if QA was a success -- solution created properly
            Settings.printDebug(Settings.COMMENT, "QA succeeded");
        }

        long endTime = System.currentTimeMillis();
        System.out.println(">>>>>>>>>>>>>>>>>>> RUN TIME\t" + (endTime - startTime) );
        //Check for the quality and integrity of the solution

        System.out.println("Routes that cannot be routed due to lack of local optimization:");
        TRShipment aShipment = mainShipments.getHead();
        while(aShipment != mainShipments.getTail()) {
            if(!aShipment.getCanBeRouted()) {
                System.out.println("Shipment " + aShipment.getIndex());
            }
            aShipment = aShipment.getNext();
        }


        System.out.println("\nWRITING SOLUTION AND COMPARISON FILES\n");
        //CLEAN UP AND GUI
//		mainWriter.writeShortSolution();
//		mainWriter.writeLongSolution();
        mainWriter.writeAll();
//        writeShortSolution(TRProblemInfo.problemFileName);
//        writeShortSolution(dataFile);
//        writeLongSolution(TRProblemInfo.problemFileName);
//        writeLongSolution(dataFile);
        //Write the short and long solutions to our problems in Excel format
        //long solution is far more detailed than short solution; both can be useful


//        compareResults(dataFile);
//        compareResults();
		mainWriter.writeComparisonResults();
        //writes an Excel file that compares our results to some results
        //from various research papers

        	System.out.println("\nLAUNCHING GUI\n");
        //
//        new ZeusGui(mainDepots, mainShipments);


    } //PVRP ENDS
// HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //METHOD
//prints all shipments to the console
    public void printDataToConsole() {
        try {
            //		mainShipments.printShipmentsToConsole();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }//PRINT DATA TO CONSOLE ENDS
// HERE*******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




    //METHOD
//creates the initial routes for our problem
//because we don't have any local optimization our
//create initial routes is our only route creation/manipulation
//we have
    public void createInitialRoutes()
            throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        Settings.lockTrucks = true;                //we already created all the trucks we are allowed

        //VARIABLES
        TRDepot currentUsedDepot = null;        //current depot
        TRShipment currentUsedShip = null;        //current shipment
        TRShipment theShipment = null;


        if(TRProblemInfo.selectShipType == null) {
            Settings.printDebug(Settings.ERROR, "No selection shipment type has been assigned");
            //check if selection and insertion type methods have been selected
        }
        if(TRProblemInfo.insertShipType == null) {
            Settings.printDebug(Settings.ERROR, "No insertion shipment type has been assigned");
            //check if we selected an insertion shipment algorithm
        }


        //while we haven't assigned all the shipments
        while(!mainShipments.isAllShipsAssigned()) {
            currentUsedDepot = mainDepots.getFirst();
            //we only have one depot so get next always gets the same depot

            theShipment = mainShipments.getNextInsertShipment(mainDepots, currentUsedDepot, mainShipments,
                    currentUsedShip);
            //		TRProblemInfo.selectShipType
            //Send the entire mainDepots and mainShipments to get the next shipment to be inserted including the current
            // depot


            if(theShipment.getFrequency() == 0) {
                //this node is in the list but wouldn't
                //like to be serviced... p11.xlsx, node 131
                //does this

                theShipment.setAssigned(true);
                continue;
            }

            if(theShipment == null) {
                //shipment is null, print error message
                //we couldn't choose shipment
                Settings.printDebug(Settings.COMMENT, "No shipment was selected");
            }

            if(!insertShipment(mainDepots, theShipment)){
                //The selected shipment couldn't be inserted in the selected location
                Settings.printDebug(Settings.COMMENT, "The Shipment: <" + theShipment.getNodeNumber() +
                        "> cannot be routed " +
                        "***********************************************");

            }
            else {
                Settings.printDebug(Settings.COMMENT, "The Shipment: <" + theShipment.getNodeNumber() +// " " + theShipment +
                        "> was routed");
                theShipment.setIsAssigned(true);    //this shipment has been assigned and we won't go back to it
                theShipment.setCanBeRouted(true);
            }


        }



        TRProblemInfo.depotLLLevelCostF.calculateTotalsStats(mainDepots);
        //	calculate all the cost, distance, demand, etc. information related to the solution we just created
    }//CREATE INITIAL ROUTES ENDS
// HERE*********<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    private boolean insertShipment(TRDepotsList depotsList, TRShipment insertMe){
        stepThroughDepots(depotsList, insertMe);
        return true;
    }

    private void stepThroughDepots(TRDepotsList depotsList, TRShipment insertMe) {
        TRDepot theDepot = depotsList.getFirst();

        while (theDepot != depotsList.getTail()) {
            stepThroughTrucks(theDepot.getSubList(), insertMe);
            theDepot = theDepot.getNext();
        }
    }


    private void stepThroughTrucks(TRTrucksList trucksList, TRShipment insertMe) {
        TRTruck theTruck = trucksList.getFirst();

        while (theTruck != trucksList.getTail()) {
            stepThroughDays(theTruck.getSubList(), insertMe);
            theTruck = theTruck.getNext();
        }
    }

    private void stepThroughDays(TRDaysList daysList, TRShipment insertMe) {

        for(int i = 0; i < daysList.getSize(); i++){
            if(insertMe.getDaysVisited()[i]){
                appendShipment((TRNodesList) daysList.getAtIndex(i).getSubList(), insertMe);
            }
        }
    }

    private void appendShipment(TRNodesList nodesList, TRShipment insertMe){
        nodesList.insertShipment(insertMe);
    }











    public void printRoutesToConsole(){
        System.out.println("\n|_____THE ROUTE___________________________________________\n");

        final TRDepotsList depotsList = mainDepots;
        final TRDepot depotTail = depotsList.getTail();
        TRDepot depotStepper = depotsList.getFirst();

        while(depotStepper != depotTail){
            System.out.println("DEPOT #" + depotStepper.getDepotNum());

            final TRTrucksList truckList = depotStepper.getSubList();
            final TRTruck truckTail = truckList.getTail();
            TRTruck truckStepper = truckList.getFirst();

            while(truckStepper != truckTail){
                System.out.println("\tTRUCK #" + truckStepper.getTruckNum());

                final TRDaysList daysList = truckStepper.getSubList();
                final TRDay dayTail = daysList.getTail();
                TRDay dayStepper = daysList.getFirst();

                while(dayStepper != dayTail){
                    System.out.println("\t\tDAY #" + dayStepper.getDayNumber());

                    final TRNodesList nodesList = dayStepper.getSubList();
                    final TRNode nodeTail = nodesList.getTail();
                    TRNode nodeStepper = nodesList.getFirst();

                    System.out.print("\t\t\t");
                    while(nodeStepper != nodeTail && nodeStepper != null) {
                        System.out.print(nodeStepper.getShipment().getNodeNumber() + "-");
                        nodeStepper = nodeStepper.getNext();
                    }
                    System.out.println();
                    dayStepper = dayStepper.getNext();
                }
                truckStepper = truckStepper.getNext();
            }
            depotStepper = depotStepper.getNext();
        }


    }







} //End of PVRP file