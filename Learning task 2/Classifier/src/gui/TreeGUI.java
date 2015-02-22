package gui;

import config.Config;
import gui.controllers.ClassifierController;
import gui.models.ClassifierModel;
import gui.views.ClassifierView;
import gui.views.TreeView;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TreeGUI extends JFrame {

    ClassifierModel classifierModel;
    TreeView treeView;

    public TreeGUI() throws Exception {
        classifierModel = new ClassifierModel(new File(Config.CATEGORIES_FILE), new File(Config.FEATURES_FILE), new File(Config.TRAINING_SET));
        treeView = new TreeView(classifierModel);
        treeView.tree();
    }

    public void buildGUI() throws Exception {
        getContentPane();
        setLayout(new BorderLayout());
        pack();
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setTitle("Classifier Tree");

        add(treeView, BorderLayout.CENTER);
    }

}
