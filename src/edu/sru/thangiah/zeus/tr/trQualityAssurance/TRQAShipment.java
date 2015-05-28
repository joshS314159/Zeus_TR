//TR PROBLEM
//CPSC 464
//AARON ROCKBURN; JOSHUA SARVER

//***********	DECLARATION_S_OTHER
// **********************************************************************************\\
// FUNCTION_START >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


//PACKAGE TITLE
package edu.sru.thangiah.zeus.tr.trQualityAssurance;


import edu.sru.thangiah.zeus.qualityassurance.QAShipment;


/**
 * TR (Periodic Vehicle Routing)
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
public class TRQAShipment
		extends QAShipment
		implements java.io.Serializable, Cloneable {

//VARIABLES
private int frequency;

	private boolean isCartesian;



	public boolean isCartesian() {
		return isCartesian;
	}

	public void setIsCartesian(boolean isCartesian) {
		this.isCartesian = isCartesian;
	}




//GETTER
public int getFrequency() {
	return this.frequency;
}




//SETTER
public void setFrequency(int frequency) {
	this.frequency = frequency;
}
}
