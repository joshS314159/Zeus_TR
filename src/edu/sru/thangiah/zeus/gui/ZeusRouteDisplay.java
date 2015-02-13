package edu.sru.thangiah.zeus.gui;

import edu.sru.thangiah.zeus.core.Day;
import edu.sru.thangiah.zeus.core.Depot;
import edu.sru.thangiah.zeus.core.Nodes;
import edu.sru.thangiah.zeus.core.Truck;
import edu.sru.thangiah.zeus.gui.checkboxtree.CheckTreeNode;
import edu.sru.thangiah.zeus.gui.checkboxtree.JCheckTree;
import edu.sru.thangiah.zeus.pvrp.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * <p>Title: ICNN</p>
 * <p/>
 * <p>Description: Intelligent Clustering with Neural Networks</p>
 * <p/>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p/>
 * <p>Company: Slippery Rock University</p>
 *
 * @author Sam R. Thangiah
 * @version 1.0
 */
public class ZeusRouteDisplay
		extends JPanel {
	private JCheckTree jCheckTree;
	private int bigX;
	private int smallX;
	private int bigY;
	private int smallY;

	/**
	 * Create a new display
	 *
	 * @param jct JCheckTree
	 */
	public ZeusRouteDisplay(JCheckTree jct) {
		jCheckTree = jct;

		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the visual elements of the display
	 *
	 * @throws Exception something went wrong
	 */
	private void jbInit() throws Exception {
		this.setBackground(Color.white);
		this.setSize(new Dimension(400, 400));
	}


	/**
	 * Paint the routes
	 *
	 * @param g graphics class
	 */
	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(2, 2, this.getWidth() - 4, this.getHeight() - 4);

		//loop through the depot linked list and find the biggest x and y
		bigX = 0;
		bigY = 0;
		smallX = Integer.MAX_VALUE;
		smallY = Integer.MAX_VALUE;

		CheckTreeNode root = (CheckTreeNode) jCheckTree.getModel().getRoot();
		PVRPDepotLinkedList mainDepots = (PVRPDepotLinkedList) root.getUserObject();

		for (int i = 0; i < root.getChildCount(); i++) {
			CheckTreeNode dnode = (CheckTreeNode) root.getChildAt(i);
			PVRPDepot depot = (PVRPDepot) dnode.getUserObject();

			if (depot.getXCoord() > bigX) {
				bigX = (int) depot.getXCoord();
			}

			if (depot.getYCoord() > bigY) {
				bigY = (int) depot.getYCoord();
			}

			if (depot.getXCoord() < smallX) {
				smallX = (int) depot.getXCoord();
			}

			if (depot.getYCoord() > smallY) {
				smallY = (int) depot.getYCoord();
			}

			PVRPTruck truck = depot.getMainTrucks().getHead().getNext();

			while (truck != depot.getMainTrucks().getTail()) {
				PVRPDay day = truck.getMainDays().getHead().getNext();

				while (day != truck.getMainDays().getTail()) {
					PVRPNodes cell = day.getNodesLinkedList().getHead().getNext();

					while (cell != day.getNodesLinkedList().getTail()) {
						if (cell.getShipment().getXCoord() > bigX) {
							bigX = (int) cell.getShipment().getXCoord();
						}

						if (cell.getShipment().getYCoord() > bigY) {
							bigY = (int) cell.getShipment().getYCoord();
						}

						if (cell.getShipment().getXCoord() < smallX) {
							smallX = (int) cell.getShipment().getXCoord();
						}

						if (cell.getShipment().getYCoord() < smallY) {
							smallY = (int) cell.getShipment().getYCoord();
						}

						cell = cell.getNext();
					}
					day = day.getNext();
				}
				truck = truck.getNext();
			}

			depot = depot.getNext();
		}

		int colorCounter = 0;

		//loop through the depot linked list again and paint the points
		for (int i = 0; i < root.getChildCount(); i++) {
			CheckTreeNode dnode = (CheckTreeNode) root.getChildAt(i);

			if (dnode.isSelected()) {
				Depot depot = (Depot) dnode.getUserObject();
				int x = transX((int) depot.getXCoord());
				int y = transY((int) depot.getYCoord());
				int[] xs = {
						x, x + 7, x - 7};
				int[] ys = {
						y - 7, y, y};

				//draw a triangle for the depot
				g.setColor(Color.black);
				g.fillPolygon(new Polygon(xs, ys, 3));

				for (int j = 0; j < dnode.getChildCount(); j++) {
					CheckTreeNode tnode = (CheckTreeNode) dnode.getChildAt(j);
//					Truck truck = (Truck) tnode.getUserObject();

					if (tnode.isSelected()) {
//						g.setColor(tno));
//						g.setColor(Color.pink);

						for (int k = 0; k < tnode.getChildCount(); k++) {
//						Day day = truck.getMainDays().getHead().getNext();
//						while (day != truck.getMainDays().getTail()) {
							CheckTreeNode dyNode = (CheckTreeNode) tnode.getChildAt(k);
							Day day = (Day) dyNode.getUserObject();
//							Random rand = new Random();
//							g.setColor(new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
//							g.setColor(Color.orange);
//							CheckTreeNode dyNode = (CheckTreeNode) tnode.getChildAt(k);
							Random rand = new Random();
							if(!dyNode.isColorSet()) {
								dyNode.setColor(new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
							}
//															g.setColor(Color.black);

							if (dyNode.isSelected()) {
								Nodes cell = day.getNodesLinkedList().getHead().getNext();
								// Java 'Color' class takes 3 floats, from 0 to 1.

								g.setColor(dyNode.getColor());
//								dyNode.setColor(Color.BLUE);
								while (cell != day.getNodesLinkedList().getTail()) {
									//dont redraw the cell again if its a depot
									if (cell.getIndex() > 0) {
										x = transX((int) cell.getShipment().getXCoord());
										y = transY((int) cell.getShipment().getYCoord());
										g.fillOval(x - 4, y - 4, 8, 8);
									}

									Nodes next = cell.getNext();

									if (next != day.getNodesLinkedList().getTail()) {
										//draw the path
										g.drawLine(transX((int) cell.getShipment().getXCoord()),
												transY((int) cell.getShipment().getYCoord()),
												transX((int) next.getShipment().getXCoord()),
												transY((int) next.getShipment().getYCoord()));
									}

									cell = next;
								}
							}
							day = day.getNext();
						}
					}
//					}
				}
			}
		}
		if (ZeusGuiInfo.showFeatureOverlay && ZeusGuiInfo.useSpaceFillingCurves)
			paintSpaceFillingCurve(g);
	}

	/**
	 * paintSpaceFillingCurve
	 *
	 * @param g Graphics
	 */
	private void paintSpaceFillingCurve(Graphics g) {
		double[] points = ZeusGuiInfo.mainFeatures;
		int i, x, y;
		g.setColor(Color.lightGray);

		for (i = 0; i < points.length; i = i + 2) {
			x = transX((int) points[i]);
			y = transY((int) points[i + 1]);
			g.fillOval(x - 2, y - 2, 4, 4);
			if (i > 0) {
				g.drawLine(transX((int) points[i]),
						transY((int) points[i + 1]),
						transX((int) points[i - 2]),
						transY((int) points[i - 1]));
			}
		}

		//x = transX( (int) cell.getShipment().getXCoord());
		//y = transY( (int) cell.getShipment().getYCoord());
		//g.fillOval(x - 4, y - 4, 8, 8);

	}

	/**
	 * will translate the X coordinate from Zeus into the X coordinate for the
	 * display
	 *
	 * @param x x coordinate to translate
	 * @return x coordinate to draw
	 */
	private int transX(int x) {
		float negOffset = 0;
		if (smallX < 0) {
			negOffset = smallX;
		}
		return (int) (((x - negOffset) * (this.getWidth() - 10)) / (bigX - negOffset));
	}


	/**
	 * Will translate the y coordinate from Zeus into the X coordinate for the
	 * display.
	 *
	 * @param y y coordinate to translate
	 * @return y coordinate to draw
	 */
	private int transY(int y) {
		float negOffset = 0;
		if (smallY < 0) {
			negOffset = smallY;
		}
		return (int) (((y - negOffset) * (this.getHeight() - 10)) / (bigY - negOffset));
	}

	public int checkMouseLocation(int x, int y) {
		int currX, currY;
		CheckTreeNode root = (CheckTreeNode) jCheckTree.getModel().getRoot();
		//loop through the depot linked list and check each node
		for (int i = 0; i < root.getChildCount(); i++) {
			CheckTreeNode dnode = (CheckTreeNode) root.getChildAt(i);

			if (dnode.isSelected()) {
				Depot depot = (Depot) dnode.getUserObject();
				for (int j = 0; j < dnode.getChildCount(); j++) {
					CheckTreeNode tnode = (CheckTreeNode) dnode.getChildAt(j);
					Truck truck = (Truck) tnode.getUserObject();

					if (tnode.isSelected()) {
						for (int k = 0; k < tnode.getChildCount(); k++) {
							CheckTreeNode dyNode = (CheckTreeNode) tnode.getChildAt(k);
							if (dyNode.isSelected()) {

							for (int l = 0; l < dyNode.getChildCount(); l++) {
								Day day = (Day) dyNode.getUserObject();

									Nodes cell = day.getNodesLinkedList().getHead();
									while (cell != null) {
										//dont redraw the cell again if its a depot
										if (cell.getIndex() > 0) {
											currX = transX((int) cell.getShipment().getXCoord());
											currY = transY((int) cell.getShipment().getYCoord());
											if ((currX - 4 <= x &&
													currX + 4 >= x) &&
													(currY - 4 <= y &&
															currY + 4 >= y))
												return cell.getShipment().getIndex();
										}
										Nodes next = cell.getNext();
										cell = next;
									}
								}
							}
						}
					}
				}
			}
		}
		return -1;
	}


}
