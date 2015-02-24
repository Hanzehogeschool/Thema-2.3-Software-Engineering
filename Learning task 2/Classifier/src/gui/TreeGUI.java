package gui;

import gui.models.ClassifierModel;
import gui.views.TreeView;

import javax.swing.*;
import java.awt.*;

/**
 * Class representing a tree gui.
 *
 * @author Nils Berlijn
 * @author Tom Broeninkg
 * @version 1.0
 */
public class TreeGUI extends JFrame {

    /**
     * The classifier model.
     */
    ClassifierModel classifierModel;

    /**
     * The tree view.
     */
    TreeView treeView;

    /**
     * Tree gui constructor.
     * Creates a new tree gui.
     *
     * @param classifierModel The classifier model.
     * @throws Exception
     */
    public TreeGUI(ClassifierModel classifierModel) throws Exception {
        this.classifierModel = classifierModel;
        this.treeView = new TreeView(this.classifierModel);
    }

    /**
     * Initializes the tree gui.
     */
    public void initializeGUI() {
        getContentPane();
        setLayout(new BorderLayout());
        pack();
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setTitle("Classifier Tree");

        JScrollPane scrollingArea = new JScrollPane(treeView);
        add(scrollingArea);
    }

}
