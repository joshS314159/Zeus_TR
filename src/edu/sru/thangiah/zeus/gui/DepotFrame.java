package edu.sru.thangiah.zeus.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.util.*;
import com.brunchboy.util.swing.relativelayout.*;
import edu.sru.thangiah.zeus.core.*;
import edu.sru.thangiah.zeus.pvrp.*;
import edu.sru.thangiah.zeus.tr.TRSolutionHierarchy.*;

import java.io.File;


/**
 * This Frame holds the Tree with the depot information
 * <p>Title: DepotFrame</p>
 * <p>Description: This Frame holds the Tree with the depot information</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Sam R. Thangiah
 * @version 2.0
 */

public class DepotFrame extends JInternalFrame {
    //Zeus Variables
    private DepotLinkedList mainDepots;

    //LinkedListInterface Variables
    private JPanel panel = new JPanel();
    private JToolBar toolbar = new JToolBar();
    private DefaultMutableTreeNode root = null;
    private JTree tree = null;
    private RelativeLayout layout = new RelativeLayout();
    private JScrollPane depotScrollPane = new JScrollPane();
    final String FS = System.getProperty("file.separator");

    /**
     * DepotFrame Constructor.  Accepts a DepotLinkedList to be displayed in the frame.
     * @param mD DepotLinkedList
     */
    public DepotFrame(DepotLinkedList mD) {
        super(ZeusGuiInfo.depotPaneTitle, true, false, false, true);
        mainDepots = mD;

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize graphical objects
     * @throws Exception error
     */
    private void jbInit() throws Exception {
        this.getContentPane().setLayout(layout);

        JButton expand = new JButton("Expand");
        expand.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    expandAllRoutes(tree, true);
                    //refreshDisplay(mainDepots);
                }
            });
        toolbar.add(expand);

        JButton collapse = new JButton("Collapse");
        collapse.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
                    //expandAllRoutes(tree, false);
                    refreshDisplay(mainDepots);
                }
            });
        toolbar.add(collapse);

        JButton rdisplay = new JButton("Route Display");
        rdisplay.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                  RouteDisplayFrame rdf = new RouteDisplayFrame();
                  rdf.setVisible(true);
                  rdf.setBounds(0, 0, rdf.getWidth(), rdf.getHeight());
                  ZeusGuiInfo.mainDesktop.add(rdf);
                  rdf.toFront();
                }
            });
        toolbar.add(rdisplay);

        tree = makeTree((TRDepotsList) mainDepots);
        depotScrollPane = new JScrollPane(tree);

        this.getContentPane().add(toolbar, "toolbar");
        this.getContentPane().add(depotScrollPane, "depotScrollPane");

        this.setSize(400, 600);
        readConstraints();
      }

  /**
   * readConstraints
   * Reads the constraints from the DepotFrameLayout.xml file to organize the layout of the objects in the depot frame.
   */
  public void readConstraints() {
    // Read the layout constraints
    XmlConstraintBuilder db_XMLBuilderLayout = new XmlConstraintBuilder();
    try {
      db_XMLBuilderLayout.addConstraints(new File("etc" + FS + "DepotFrameLayout.xml"), layout);   // Read the contraints from the layout XML file.
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  /**
     * Makes a tree from the depot linked list
     * @param d the depot linked list
     * @return a tree of the depot linked list
     */
    private JTree makeTree(TRDepotsList d) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(
                "The Depot Linked List");
        TRDepot tempDepot = d.getFirst();
        String pointInfo = "";
        int nextIndex = 0, prevIndex = 0;


        while (tempDepot != d.getTail()) {
            DefaultMutableTreeNode tempDNode = new DefaultMutableTreeNode(tempDepot.toString());
            TRTruck tempTruck = tempDepot.getSubList().getFirst();

            while (tempTruck != tempDepot.getSubList().getTail()) {
	            DefaultMutableTreeNode tempTNode = new DefaultMutableTreeNode(tempTruck.toString());
                if (!tempTruck.isSubListEmpty()) {
                    TRDaysList daysList = tempTruck.getSubList();
	                TRDay tempDay = daysList.getFirst();

	                while(tempDay != tempTruck.getSubList().getTail()) {
		                DefaultMutableTreeNode tempDayNode = new DefaultMutableTreeNode(tempDay.toString());


		                TRNode point = tempDay.getSubList().getHead();
		                int dayNumber = daysList.getIndexOfObject(tempDay);

		                while (true) {
			                if (point.getPrev() == tempDay.getSubList().getHead())
				                prevIndex = 0;
			                else if (point.getNext() == tempDay.getSubList().getTail())
				                nextIndex = -1;
			                else if (point == tempDay.getSubList().getHead()){
				                nextIndex = point.getNext().getShipment().getCustomerIndex();
				                prevIndex = -1;
			                }
			                else if (point == tempDay.getSubList().getTail()){
				                nextIndex = 0;
				                prevIndex = ((TRNode) point.getPrevious()).getIndex();
			                }
			                else {
				                nextIndex = point.getNext().getIndex();
				                prevIndex = ((TRNode) point.getPrevious()).getIndex();
			                }

			                pointInfo = "#" + point.getShipment().getCustomerIndex() + /*" | Demand: " +
					                point.getDemand() +*/ " | Prev: " + prevIndex +
					                " | Next: " + nextIndex;

			                DefaultMutableTreeNode tempPNode = new DefaultMutableTreeNode(pointInfo);

			                tempDayNode.add(tempPNode);
//			                DefaultMutableTreeNode tempPNode = new DefaultMutableTreeNode(pointInfo);
//			                tempTNode.add(tempPNode);

			                if(point == tempDay.getSubList().getTail()){
				                break;
			                }

			                point = point.getNext();
		                }
						tempTNode.add(tempDayNode);


		                tempDay = tempDay.getNext();
	                }
                }
	            tempDNode.add(tempTNode);
                tempTruck = tempTruck.getNext();
            }

            root.add(tempDNode);
            tempDepot = tempDepot.getNext();
        }

        JTree tree = new JTree(root);

        tree.setCellRenderer(new CustomCellRenderer());
        return tree;
    }

    /**
     * Expands all the objects in the depot JTree.
     * @param t JTree
     * @param ex boolean
     */
    private void expandAllRoutes(JTree t, boolean ex) {
      TreeNode root = (TreeNode)t.getModel().getRoot();

        // Traverse tree from root
        expandAll(tree, new TreePath(root), ex);
    }

    /**
     * Generic method to expand the components of a JTree object.
     * @param t JTree
     * @param parent TreePath
     * @param expand boolean
     */
    private void expandAll(JTree t, TreePath parent, boolean expand) {
        // Traverse children
        TreeNode node = (TreeNode)parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e=node.children(); e.hasMoreElements(); ) {
                TreeNode n = (TreeNode)e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(t, path, expand);
            }
        }

        // Expansion or collapse must be done bottom-up
        if (expand) {
            t.expandPath(parent);
        } else {
            t.collapsePath(parent);
        }
    }

    /**
     * Refreshes the depot linked list tree
     * @param mD the depot linked list
     */
    public void refreshDisplay(DepotLinkedList mD) {
        mainDepots = mD;
        tree = makeTree((TRDepotsList) mainDepots);
        depotScrollPane.setViewportView(tree);
    }
}
