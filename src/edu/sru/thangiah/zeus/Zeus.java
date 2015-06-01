
package edu.sru.thangiah.zeus;

import edu.sru.thangiah.zeus.pvrp.PVRPRoot;
import edu.sru.thangiah.zeus.tr.TRRoot;


/**
 * Calls the main functions of Zeus.
 * Title: Zeus
 * Description: This class calls the main root method for the problem to be solved
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class Zeus {
  /**
   * Main function for Zeus
   * @param args command line arguments (not used)
 * @throws Exception 
   */
  //DR. SAM's MAIN

	//PVRP GROUP 06 MAIN
	public static void main(String[] args) throws Exception
	{
//		new TRRoot().TRFormat();
		new TRRoot().PVRPFormat();



	}


}
