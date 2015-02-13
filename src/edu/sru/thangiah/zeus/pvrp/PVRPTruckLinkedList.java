package edu.sru.thangiah.zeus.pvrp;

//import the parent class


import edu.sru.thangiah.zeus.core.*;


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

public class PVRPTruckLinkedList
		extends TruckLinkedList
		implements java.io.Serializable, java.lang.Cloneable {

	//VARIABLES
	private PVRPTruck head;
	private PVRPTruck tail;
	private PVRPAttributes attributes;


	//CONSTRUCTOR
	public PVRPTruckLinkedList() {
		this.head = new PVRPTruck();
		this.tail = new PVRPTruck();
		this.head.setNext(this.tail);
		this.tail.setPrevious(this.head);
		this.head.setPrevious(null);
		this.tail.setNext(null);

		this.attributes = new PVRPAttributes();
		//Assign the attributes
	}//END CONSTRUCTOR *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	public boolean insertShipment_Incomplete(PVRPShipment theShipment) {
		PVRPTruck theTruck = this.getHead();
		int[][] currentDemands = new int[PVRPProblemInfo.noOfVehs][PVRPProblemInfo.noOfDays];
		int truckCounter = -1;
		int dayCounter = -1;

		while (theTruck.getNext() != this.getTail()) {
			theTruck = theTruck.getNext();
			truckCounter++;

			PVRPDayLinkedList daysList = theTruck.getMainDays();
			PVRPDay theDay = daysList.getHead();

			dayCounter = -1;
			while (theDay.getNext() != daysList.getTail()) {
				theDay = theDay.getNext();
				dayCounter++;

				PVRPNodesLinkedList nodesList = theDay.getNodesLinkedList();
				PVRPNodes theNode = nodesList.getHead();
				int demandTemp = 0;
				while (theNode != nodesList.getTail()) {
					demandTemp += theNode.getShipment().getDemand();
					theNode = theNode.getNext();

				}
				currentDemands[truckCounter][dayCounter] = demandTemp;
			}
		}

		int bestTruck = -1;
		int bestCombo = -1;
		int bestTruckDemand = Integer.MAX_VALUE;
		int bestComboDemand = Integer.MAX_VALUE;
		int comboDemand = 0;
		int[][] combinations = theShipment.getCurrentComb();
		for (int comboNumber = 0; comboNumber < theShipment.getNoComb(); comboNumber++) {
			//cycle combo numbers

			for (int dayNumber = 0; dayNumber < PVRPProblemInfo.noOfDays; dayNumber++) {
				//cycle day numbers

				if (combinations[comboNumber][dayNumber] == 1) {
					//FINDS THE BEST TRUCK FOR A DAY
					bestTruckDemand = Integer.MAX_VALUE;
					for (int truckNumber = 0; truckNumber < PVRPProblemInfo.noOfVehs; truckNumber++) {
						int demand = currentDemands[truckNumber][dayNumber];
						if (bestTruckDemand < demand) {
							bestTruckDemand = demand;
							bestTruck = truckNumber;
						}
					}
					comboDemand += bestTruckDemand;
				}
			}
			if (comboDemand < bestComboDemand) {
				bestComboDemand = comboDemand;
				bestCombo = comboNumber;
			}
		}

		int truckDemand;
		bestTruckDemand = Integer.MAX_VALUE;
		for (int days = 0; days < PVRPProblemInfo.noOfDays; days++) {
			if (combinations[bestCombo][days] == 1) {
				for (int trucks = 0; trucks < PVRPProblemInfo.noOfVehs; trucks++) {
					truckDemand = currentDemands[trucks][days];
					if (bestTruckDemand < truckDemand) {
						bestTruck = trucks;
					}
				}

				//INSERT INTO CORRECT PLACE
				theTruck = this.getHead();
				for (int x = 0; x < bestTruck; x++) {
					theTruck = theTruck.getNext();
				}
				PVRPDayLinkedList daysList = theTruck.getMainDays();
				PVRPDay theDay = daysList.getHead();
				for (int x = 0; x < days; x++) {
					theDay = theDay.getNext();
				}
//			theDay.nod
				PVRPNodesLinkedList nodesList = theDay.getNodesLinkedList();
//					nodesList.insertShipment(theShipment);
//			nodesList.insertNodes(new PVRPNodes(theShipment));
//			nodesList.insertNodes();
			}

		}


		return true;
	}


	//METHOD
//inserts shipment at the truck with the lowest current demand
	public boolean insertShipment(PVRPShipment theShipment) {
		/** @todo aaron comment */
		//lowestNLL has lowest demand. tempNLL is the NLL which is being compared

		//get the mainDaysLL from the current truck. Set the non-temp variables to the first item in their respective
		//lists. The following while loops go through each truck, as well as its days and their corresponding nodesLL.
		//If a nodesLL comes up with a current demand of 0, it is automatically assumed to be the lowest demand nodesLL
		//and the variables are set and the loops exit. If there is no total demand of 0, the variables are set to the
		//lowest total demand of all nodesLL

		boolean status = false;
		boolean lowestDemandFound = false;
		//int loopCount=0;

		PVRPTruck truck = this.getHead().getNext();        //get first truck in truck linked list
		PVRPDayLinkedList tempDaysLL, lowestDaysLL =
				null;            //finalDaysLL will hold the DaysLL that contains the current smallest
		//nodesLL. tempDaysLL is the DaysLL which is being compared to final
		PVRPDay currDay,
				lowestDemandDay;                            //lowestDemandDay has lowest total demand so far. currDay is
		// the comparison
		PVRPNodesLinkedList tempNLL, lowestNLL;

		tempDaysLL = truck.getMainDays();
		lowestDemandDay = tempDaysLL.getHead().getNext();
		int dayCount, lowestDemand = 0, currDemand, truckCount, lowestTruck = 0;
		PVRPNodesLinkedList daysToBeInserted[];
		int counter = 0;

		while (!status && counter < 1) {
			//so we don't get stuck in this loop
			counter++;
			int tempAry[][] = theShipment.getCurrentComb();
			int lowestDemandCombo[] = null;

			if (theShipment.getIndex() == 1) {
				lowestTruck = 0;
			}

			//check for all possible combinations
			for (int i = 0; i < theShipment.getNoComb(); i++) {
				if (i == 0) {
					lowestDemandCombo = tempAry[i];
					lowestTruck = 0;
					lowestDaysLL = tempDaysLL;
				}


				//test combination of current iteration
				int tempCombo[] = tempAry[i];

				truck = this.getHead().getNext();
				truckCount = 0;
				lowestDemand = 0;

				while (truck != this.getTail()) {
					currDemand = 0;
					dayCount = 0;
					tempDaysLL = truck.getMainDays();
					currDay = tempDaysLL.getHead().getNext();
					lowestDemandDay = lowestDaysLL.getHead().getNext();

					while (currDay != tempDaysLL.getTail() && dayCount != PVRPProblemInfo.noOfDays &&
							lowestDemandDay != lowestDaysLL.getTail()) {
						//needs to check for both trucks, but once the lowestDemandCombo is set,
						// it only checks the truck for which it was set

						if (tempCombo[dayCount] == 1) {
							tempNLL = currDay.getNodesLinkedList();
							currDemand += tempNLL.getTotalDemand();
						}

						if (lowestDemandCombo[dayCount] == 1 && truckCount == lowestTruck) {
							lowestNLL = lowestDemandDay.getNodesLinkedList();
							lowestDemand += lowestNLL.getTotalDemand();
						}

						dayCount++;
						currDay = currDay.getNext();
						lowestDemandDay = lowestDemandDay.getNext();

					}

					if (lowestDemand > currDemand) {
						lowestDemandCombo = tempCombo;
						lowestTruck = truckCount;
						lowestDaysLL = tempDaysLL;
						lowestDemand = currDemand;
					}

					truck = truck.getNext();
					truckCount++;

                        tempDaysLL = truck.getMainDays();
                    }


                }

                truck = this.getHead();
                truckCount = -1;
                while (lowestTruck != truckCount) {
                    truck = truck.getNext();
                    truckCount++;
                }

                lowestDaysLL = truck.getMainDays();
                lowestDemandDay = lowestDaysLL.getHead().getNext();


                for (int i = 0; i < lowestDemandCombo.length; i++) {

                    if (lowestDemandCombo[i] == 0) {
                        if (lowestDemandDay.getNext() != lowestDaysLL.getTail()) {
						lowestDemandDay = lowestDemandDay.getNext();
					}
				}
				if (lowestDemandCombo[i] == 1) {
					lowestNLL = lowestDemandDay.getNodesLinkedList();
					status = lowestNLL.insertShipment(theShipment);

					if (lowestDemandDay.getNext() != lowestDaysLL.getTail()) {
						lowestDemandDay = lowestDemandDay.getNext();
					}
				}
			}

            //brute force
            //first location that fits, throw it in there
           if(!status) {
                for (int x = 0; x < theShipment.getNoComb(); x++) {
                    int[] combinations = theShipment.getCurrentComb()[x];
                    int oneCounter = 0;
                    int successCounter = 0;
                    truck = this.getHead();
                    while (truck.getNext() != this.getTail()) {
                        truck = truck.getNext();
//                    status = true;
                        PVRPDayLinkedList daysList = truck.getMainDays();
                        PVRPDay theDay = daysList.getHead();
                        int i = 0;
                        while (theDay.getNext() != daysList.getTail()) {
                            theDay = theDay.getNext();
                            if (combinations[i] == 1) {
                                oneCounter++;
                                PVRPNodesLinkedList nodesList = theDay.getNodesLinkedList();
                                status = nodesList.insertShipment(theShipment);
                                if(status){
                                    System.out.print("");
                                    successCounter++;
                                }
                            }
                            i++;
                        }

                        if(oneCounter == successCounter){
                            System.out.print("");
                            x = theShipment.getNoComb();
                            break;
                        }


                    }
                }
            }

		}

		//can we create new trucks?
		if ((status == false) && (Settings.lockTrucks == false)) {
			//create a pointer to the last truck for reference
			PVRPTruck last = this.getTail().getPrevious();
			PVRPTruck first = this.getHead();

			//loop to find the correct truck type for this customer
			for (int i = 0; i < PVRPProblemInfo.truckTypes.size(); i++) {
				PVRPTruckType type = (PVRPTruckType) PVRPProblemInfo.truckTypes.elementAt(i);

				if (type.getServiceType().equals(theShipment.getTruckTypeNeeded())) {
					//create a new truck with the truck number of the last + 1, the matching truck type
					//and use the first customer in the last truck (the depot) to get the depot x,y
					PVRPTruck newTruck = new PVRPTruck(type, first.getNext().getDepotX(), first.getNext().getDepotY());


					//attempt to put this shipment into the new truck
					status = newTruck.getMainNodes().insertShipment(theShipment);

					if (status == true) {
						//the customer was inserted to the new truck, so insert the new truck to the linked list
						System.out.println("** Inserting new Truck");
						System.out.println(
								"Depot x and y is:" + first.getNext().getDepotX() + " " + first.getNext().getDepotY());
						this.insertTruckLast(newTruck);

						return status;
					} else {
						//customer could not be inserted into the new truck so return false
						//and dont insert the new truck into the linked list (garbage collector
						//will delete it)
						return status;
					}
				}
			}
		}

		return status;
	}//END INSERT_SHIPMENT *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	//METHOD
//inserts shipment at the truck with the lowest current demand
	public boolean insertShipment_Broken(PVRPShipment theShipment) {
		/** @aaron comment */
		//lowestNLL has lowest demand. tempNLL is the NLL which is being compared

		//get the mainDaysLL from the current truck. Set the non-temp variables to the first item in their respective
		//lists. The following while loops go through each truck, as well as its days and their corresponding nodesLL.
		//If a nodesLL comes up with a current demand of 0, it is automatically assumed to be the lowest demand nodesLL
		//and the variables are set and the loops exit. If there is no total demand of 0, the variables are set to the
		//lowest total demand of all nodesLL

		boolean status = false;
		boolean lowestDemandFound = false;
		//int loopCount=0;

		PVRPTruck truck = this.getHead().getNext();        //get first truck in truck linked list
		PVRPDayLinkedList tempDaysLL, lowestDaysLL = null;            //finalDaysLL will hold the DaysLL that contains the current smallest
		//nodesLL. tempDaysLL is the DaysLL which is being compared to final
		PVRPDay currDay, lowestDemandDay;                            //lowestDemandDay has lowest total demand so far. currDay is the comparison
		PVRPNodesLinkedList tempNLL, lowestNLL;

		tempDaysLL =  truck.getMainDays();
		int dayCount, lowestDemand = 0, currDemand, truckCount, lowestTruck = 0;

		int counter = 0;
		//only try this five times or else we'll end up
		//in an infinite loop
		while (!status && counter < 5) {
			counter++;
			int tempAry[][] = theShipment.getCurrentComb();
			int lowestDemandCombo[] = null;

			if (theShipment.getIndex() == 1) {
				lowestTruck = 0;
			}

			//check for all possible combinations
			for (int i = 0; i < theShipment.getNoComb(); i++) {
				if (i == 0) {
					lowestDemandCombo = tempAry[i];
					lowestTruck = 0;
					lowestDaysLL = tempDaysLL;
				}


				//test combination of current iteration
				int tempCombo[] = tempAry[i];

				truck = this.getHead().getNext();
				truckCount = 0;
				lowestDemand = 0;

				while (truck != this.getTail()) {
					currDemand = 0;
					dayCount = 0;
					tempDaysLL = truck.getMainDays();
					currDay = tempDaysLL.getHead().getNext();
					lowestDemandDay = lowestDaysLL.getHead().getNext();

					while (currDay != tempDaysLL.getTail() && dayCount != PVRPProblemInfo.noOfDays && lowestDemandDay != lowestDaysLL.getTail()) {
						//needs to check for both trucks, but once the lowestDemandCombo is set, it only checks the truck for which it was set

						if (tempCombo[dayCount] == 1) {
							tempNLL = currDay.getNodesLinkedList();
							currDemand += tempNLL.getTotalDemand();
						}

						if (lowestDemandCombo[dayCount] == 1 && truckCount == lowestTruck) {
							lowestNLL = lowestDemandDay.getNodesLinkedList();
							lowestDemand += lowestNLL.getTotalDemand();
						}
//                        if (currDay.getNext() != tempDaysLL.getTail()) {
//                            currDay = currDay.getNext();
//                        }
						if (theShipment.getIndex() == 358) {
//                            System.out.print("545654y65uj56y");
						}
//                        if (lowestDemandDay.getNext() != lowestDaysLL.getTail() && truckCount == lowestTruck) {
//                            lowestDemandDay = lowestDemandDay.getNext();
//                        }

						dayCount++;
						currDay = currDay.getNext();
						lowestDemandDay = lowestDemandDay.getNext();

					}

					if (lowestDemand > currDemand) {
						lowestDemandCombo = tempCombo;
						lowestTruck = truckCount;
						lowestDaysLL = tempDaysLL;
						lowestDemand = currDemand;
					}

					truck = truck.getNext();
					truckCount++;

					tempDaysLL = truck.getMainDays();
				}


			}

			truck = this.getHead().getNext();
			truckCount = 0;
			while (lowestTruck != truckCount) {
				truck = truck.getNext();
				truckCount++;
			}

			lowestDaysLL = truck.getMainDays();
			lowestDemandDay = lowestDaysLL.getHead().getNext();


			for (int i = 0; i < lowestDemandCombo.length; i++) {

				if (lowestDemandCombo[i] == 0) {
					if (lowestDemandDay.getNext() != lowestDaysLL.getTail())
						lowestDemandDay = lowestDemandDay.getNext();
				}
				if (lowestDemandCombo[i] == 1) {
					lowestNLL = lowestDemandDay.getNodesLinkedList();
					status = lowestNLL.insertShipment(theShipment);

					if (lowestDemandDay.getNext() != lowestDaysLL.getTail())
						lowestDemandDay = lowestDemandDay.getNext();
				}
			}

			boolean replacementFound = false;
			PVRPNodesLinkedList replacementNLL = null, bestNLL = null;
			PVRPNodes bestN = null, replacementN = null;
			PVRPDay bestD = null, repalcementD = null;
			PVRPTruck bestT = null, replacementT = null;
			float maxCap = this.getHead().getNext().getTruckType().getMaxCapacity();


			if (!status) {
				TruckLinkedList tempTLL = this;
				Truck tempT = tempTLL.getHead();
				while (tempT.getNext() != tempTLL.getTail()) {
					tempT = tempT.getNext();
					DaysLinkedList tempDLL = tempT.getMainDays();
					Day tempD = tempDLL.getHead();
					while (tempD.getNext() != tempDLL.getTail()) {
						tempD = tempD.getNext();
						NodesLinkedList tempNodesLL = tempD.getNodesLinkedList();
						Nodes tempN = tempNodesLL.getHead();
						while (tempN.getNext() != tempNodesLL.getTail()) {
							tempN = tempN.getNext();
							Truck swapTruck = tempT;
							while (swapTruck != tempTLL.getTail()) {
								DaysLinkedList swapDayLL = swapTruck.getMainDays();
								Day swapDay = swapDayLL.getHead().getNext();
								while (swapDay != swapDayLL.getTail()) {
									NodesLinkedList swapNLL = swapDay.getNodesLinkedList();
									Nodes swapN = swapNLL.getHead().getNext();
									while (swapN != swapNLL.getTail()) {
										float swapDem = ((PVRPNodesLinkedList) swapNLL).getTotalDemand() - swapN.getDemand() + tempN.getDemand();
										float tempDem = ((PVRPNodesLinkedList) tempNodesLL).getTotalDemand() - tempN.getDemand() + swapN.getDemand();

										if (swapDem <= maxCap && tempDem <= maxCap && tempNodesLL != swapNLL) {
											if (tempDem + theShipment.getDemand() <= maxCap) {
												replacementNLL = (PVRPNodesLinkedList) swapNLL;
												replacementN = (PVRPNodes) swapN;
												replacementT = (PVRPTruck) swapTruck;
												repalcementD = (PVRPDay) swapDay;
												bestNLL = (PVRPNodesLinkedList) tempNodesLL;
												bestD = (PVRPDay) tempD;
												bestN = (PVRPNodes) tempN;
												bestT = (PVRPTruck) tempT;
												replacementFound = true;
											} else if (swapDem + theShipment.getDemand() <= maxCap) {
												replacementNLL = (PVRPNodesLinkedList) tempNodesLL;
												replacementN = (PVRPNodes) tempN;
												replacementT = (PVRPTruck) tempT;
												repalcementD = (PVRPDay) tempD;
												bestNLL = (PVRPNodesLinkedList) swapNLL;
												bestD = (PVRPDay) swapDay;
												bestN = (PVRPNodes) swapN;
												bestT = (PVRPTruck) swapTruck;
												replacementFound = true;
											}
										}
										if (replacementFound)
											break;

										swapN = swapN.getNext();
									}
									if (replacementFound)
										break;

									swapDay = swapDay.getNext();
								}
								if (replacementFound)
									break;

								swapTruck = swapTruck.getNext();
							}
							if (replacementFound)
								break;
						}
						if (replacementFound)
							break;
					}
					if (replacementFound)
						break;
				}
			}


			if (replacementFound) {
				replacementN.getShipment().setIsAssigned(false);
				bestN.getShipment().setIsAssigned(false);
				replacementNLL.removeNodeByIndex(replacementN.getShipment().getIndex());
				bestNLL.removeNodeByIndex(bestN.getShipment().getIndex());

				int num = bestNLL.getTotalDemand();

				if (bestNLL.insertShipment(replacementN.getShipment())) {
					replacementN.getShipment().setIsAssigned(true);
				}

				num = bestNLL.getTotalDemand();

				if (replacementNLL.insertShipment(bestN.getShipment())) {
					bestN.getShipment().setIsAssigned(true);
				}

				int ajasdf = bestNLL.getTotalDemand();

				if (bestNLL.insertShipment(theShipment)) {
					status = true;
				} else {
					Settings.printDebug(Settings.COMMENT,
							"The Shipment: <" + theShipment.getIndex() +// " " + theShipment +
									"> could not be routed");
				}
			}

			if (!status) {
				TruckLinkedList tempTLL = this;
				Truck tempT = tempTLL.getHead();
				while (tempT.getNext() != tempTLL.getTail()) {
					tempT = tempT.getNext();
					DaysLinkedList tempDLL = tempT.getMainDays();
					Day tempD = tempDLL.getHead();
					while (tempD.getNext() != tempDLL.getTail()) {
						tempD = tempD.getNext();
						NodesLinkedList tempNodesLL = tempD.getNodesLinkedList();
						Nodes tempN = tempNodesLL.getHead();
						while (tempN.getNext() != tempNodesLL.getTail()) {
							tempN = tempN.getNext();
							Truck swapTruck = tempT;
							while (swapTruck != tempTLL.getTail()) {
								DaysLinkedList swapDayLL = swapTruck.getMainDays();
								Day swapDay = swapDayLL.getHead().getNext();
								while (swapDay != swapDayLL.getTail()) {
									NodesLinkedList swapNLL = swapDay.getNodesLinkedList();
									Nodes swapN = swapNLL.getHead().getNext();
									while (swapN != swapNLL.getTail()) {
										float swapDem = ((PVRPNodesLinkedList) swapNLL).getTotalDemand() + tempN.getDemand();
										float tempDem = ((PVRPNodesLinkedList) tempNodesLL).getTotalDemand() - tempN.getDemand();

										if (swapDem <= maxCap && tempDem <= maxCap && tempNodesLL != swapNLL) {
											if (tempDem + theShipment.getDemand() <= maxCap) {
												replacementNLL = (PVRPNodesLinkedList) swapNLL;
												replacementN = (PVRPNodes) swapN;
												bestNLL = (PVRPNodesLinkedList) tempNodesLL;
												bestN = (PVRPNodes) tempN;
												replacementFound = true;
											}
										}
										if (replacementFound)
											break;

										swapN = swapN.getNext();
									}
									if (replacementFound)
										break;

									swapDay = swapDay.getNext();
								}
								if (replacementFound)
									break;

								swapTruck = swapTruck.getNext();
							}
							if (replacementFound)
								break;
						}
						if (replacementFound)
							break;
					}
					if (replacementFound)
						break;
				}
				bestN.getShipment().setIsAssigned(false);
				bestNLL.removeNodeByIndex(bestN.getShipment().getIndex());
				if (!replacementNLL.insertShipment((PVRPShipment) bestN.getShipment())) {
					Settings.printDebug(Settings.COMMENT,
							"The Shipment: <" + bestN.getShipment().getIndex() +// " " + theShipment +
									"> could not be swapped");
				}
			}
		}

		//can we create new trucks?
		if ((status == false) && (Settings.lockTrucks == false)) {
			/** @todo Is this really needed */
			//create a pointer to the last truck for reference
			PVRPTruck last = (PVRPTruck) this.getTail().getPrev();
			PVRPTruck first = this.getHead();

			//loop to find the correct truck type for this customer
			for (int i = 0; i < PVRPProblemInfo.truckTypes.size(); i++) {
				PVRPTruckType type = (PVRPTruckType) PVRPProblemInfo.truckTypes.elementAt(i);

				if (type.getServiceType().equals(theShipment.getTruckTypeNeeded())) {
					//create a new truck with the truck number of the last + 1, the matching truck type
					//and use the first customer in the last truck (the depot) to get the depot x,y
					PVRPTruck newTruck = new PVRPTruck(type,
							first.getNext().getDepotX(),
							first.getNext().getDepotY());


					//attempt to put this shipment into the new truck
					PVRPNodesLinkedList nll = newTruck.getMainNodes();
					status = nll.insertShipment(theShipment);

					if (status == true) {
						//the customer was inserted to the new truck, so insert the new truck to the linked list
						System.out.println("** Inserting new Truck");
						System.out.println("Depot x and y is:" + first.getNext().getDepotX() + " " + first.getNext().getDepotY());
						this.insertTruckLast(newTruck);

						return status;
					} else {
						//customer could not be inserted into the new truck so return false
						//and dont insert the new truck into the linked list (garbage collector
						//will delete it)
						return status;
					}
				}
			}
		}

		return status;
	}//END INSERT_SHIPMENT *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	//GETTER
	public PVRPTruck getHead() {
		return this.head;
	}


	public void setHead(PVRPTruck head) {
		this.head = head;
	}


	//METHOD
//inserts truck into the last position (before tail)
	public boolean insertTruckLast(PVRPTruck truck) {
		if (this.head.getNext() == this.tail) {
			//if the list is empty
			this.head.setNext(truck);        //head next to passed
			this.tail.setPrevious(truck);    //tail previous to passed
			truck.setPrevious(this.head);    //previous passed to head
			truck.setNext(this.tail);        //next passed to tail
		} else {
			//if there's already stuff in this list
			truck.setNext(this.tail);                    //passed to tail
			truck.setPrevious(this.tail.getPrevious());    //passed to current previous
			this.tail.getPrevious().setNext(truck);        //current previous to passed
			this.tail.setPrevious(truck);                //tail previous to passed
		}
		return true;
	}//END INSERT_TRUCK_LAST *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	//GETTER
	public PVRPTruck getTail() {
		return this.tail;
	}


	//SETTER
	public void setTail(PVRPTruck tail) {
		this.tail = tail;
	}


	//METHOD
//links the head node to the tail node
//will wipe the list clean as all insert
//classes are anonymous
	public void linkHeadTail() {
		this.head.setNext(this.tail);        //head next to tail
		this.tail.setPrevious(this.head);    //tail previous to head
		this.head.setPrevious(null);        //head previous to null (firstmost)
		this.tail.setNext(null);            //tail next to null (lastmost_
	}//END LINKED_HEAD_TAIL *******************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<




	//GETTER
	public PVRPAttributes getAttributes() {
		return this.attributes;
	}


//METHOD
//gets a truck at a specific position


	//SETTER
	public void setAttributes(PVRPAttributes attributes) {
		this.attributes = attributes;
	}


public PVRPTruck getTruckAtPosition(int position){
	PVRPTruck theTruck = this.getHead();
	int count = -1;

	if(position >= 0 && position < this.getSize()) {
		while (count != position) {
			theTruck = theTruck.getNext();
			count++;
			if (theTruck == this.getTail()) {
				return null;
			}
		}
	}
	else{
		return null;
	}
	return theTruck;
}

public int getSize(){
	PVRPTruck theTruck = this.getHead();
	int sizeCounter = 0;

//	if(!isEmpty()) {
		while (theTruck.getNext() != this.getTail()) {
			theTruck = theTruck.getNext();
			sizeCounter++;
		}
		return sizeCounter;
//	}
//	return 0;
}


public PVRPTruck getFirstTruck(){
	if(!isEmpty()){
		return this.getHead().getNext();
	}
	return null;

}

public PVRPTruck getLastTruck(){
	if(!isEmpty()) {
		return this.getTail().getPrev();
	}
	return null;
}

public boolean isEmpty(){
	if(this.getHead().getNext() == this.getTail() || this.getHead().getNext() == null){
		return true;
	}
	return false;
}


}
