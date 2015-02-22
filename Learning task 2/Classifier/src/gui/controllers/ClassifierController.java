package gui.controllers;

import config.Config;
import gui.models.ClassifierModel;
import gui.views.ClassifierView;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ClassifierController extends JFrame {

    ClassifierModel classifierModel;
    private ClassifierView classifierView;

    public ClassifierController() throws Exception {
        classifierModel = new ClassifierModel(new File(Config.FEATURE_VALUES), new File(Config.TRAINING_SET));
        classifierView = new ClassifierView(classifierModel);
        buildGUI();
    }

    public void buildGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane();
        this.setLayout(new BorderLayout());
        this.pack();
        this.setSize(400, 150);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setTitle("Classifier");
        this.add(classifierView, BorderLayout.CENTER);
    }

}
