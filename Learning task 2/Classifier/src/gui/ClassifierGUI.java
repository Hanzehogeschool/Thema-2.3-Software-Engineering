package gui;

import config.Config;
import gui.controllers.ClassifierController;
import gui.models.ClassifierModel;
import gui.views.ClassifierView;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ClassifierGUI extends JFrame {

    ClassifierModel classifierModel;
    ClassifierView classifierView;
    ClassifierController classifierController;

    public ClassifierGUI() throws Exception {
        classifierModel = new ClassifierModel(new File(Config.CATEGORIES_FILE), new File(Config.FEATURES_FILE), new File(Config.TRAINING_SET));
        classifierView = new ClassifierView(classifierModel);
        classifierController = new ClassifierController(classifierModel, classifierView);
        classifierView.welcomeScreen();
        classifierController.welcomeScreen();
    }

    public static void main(String[] args) throws Exception {
        ClassifierGUI classifierGUI = new ClassifierGUI();
        classifierGUI.buildGUI();
    }

    public void buildGUI() {
        getContentPane();
        setLayout(new BorderLayout());
        pack();
        setSize(400, 165);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setTitle("Classifier");

        add(classifierView, BorderLayout.NORTH);
        add(classifierController, BorderLayout.SOUTH);
    }

}
