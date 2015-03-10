package gui;

import config.Config;
import gui.controllers.ClassifierController;
import gui.models.ClassifierModel;
import gui.views.ClassifierView;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Class representing a classifier gui.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class ClassifierGUI extends JFrame {

    /**
     * The classifier model.
     */
    ClassifierModel classifierModel;

    /**
     * The classifier view.
     */
    ClassifierView classifierView;

    /**
     * The classifier controller.
     */
    ClassifierController classifierController;

    /**
     * Classifier gui constructor.
     * Creates a new classifier gui.
     *
     * @throws Exception
     */
    public ClassifierGUI() throws Exception {
        classifierModel = new ClassifierModel(new File(Config.CATEGORIES_FILE), new File(Config.FEATURES_FILE), new File(Config.TRAINING_SET_FILE));
        classifierView = new ClassifierView(classifierModel);
        classifierController = new ClassifierController(classifierModel, classifierView);

        classifierView.welcomeScreen();
        classifierController.welcomeScreen();
    }

    /**
     * Initializes the classifier gui.
     */
    public void initializeGUI() {
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
