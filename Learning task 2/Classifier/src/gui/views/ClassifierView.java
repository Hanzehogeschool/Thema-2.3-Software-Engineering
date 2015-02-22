package gui.views;

import classifier.Item;
import gui.models.ClassifierModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClassifierView extends JPanel implements ActionListener {

    ClassifierModel classifierModel;
    JPanel baseContainer; /* Main */
    private JPanel northContainer;
    private JPanel centerContainer;
    private JPanel southContainer; /* Controller */
    private ButtonGroup buttonGroup; /* Controller */
    private ButtonModel buttonModel; /* Controller */
    private Item item; /* Model */
    private String result; /* Model */

    public ClassifierView(ClassifierModel classifierModel) {
        this.classifierModel = classifierModel;
        this.setLayout(new BorderLayout()); /* Main */
        buildContainers();
        firstScreen();
        buttonGroup = null; /* Controller */
        buttonModel = null; /* Controller */
        item = classifierModel.createItem("car", classifierModel.getFeatureValues()); /* Controller */
        result = ""; /* Controller */
    }

    /* Controller */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton jButton = (JButton) actionEvent.getSource();
        String name = jButton.getName();
        String[] featureValues = classifierModel.getFeatureValues();

        if (buttonGroup != null) {
            buttonModel = buttonGroup.getSelection();
        }

        if (!name.equals("Result") && !name.equals("Exit")) {
            for (int i = 0; i < featureValues.length; i++) {
                if (name.equals(featureValues[i])) {
                    if (i != featureValues.length - 1) {
                        addItem(i);
                        optionScreen(featureValues[i], featureValues[i + 1]);
                    } else {
                        addItem(i);
                        optionScreen(featureValues[featureValues.length - 1], "Result");
                    }
                }
            }
        } else if (name.equals("Result")) {
            addItem(featureValues.length);
            result = classifierModel.getDecisionTree().assignCategory(item); /* Model */
            resultScreen();
        } else {
            exit();
        }
    }

    /* Controller */
    private void addItem(Integer i) {
        if (buttonModel != null) {
            item.setFeatureValue(classifierModel.getFeatureValues()[i - 1], buttonModel.getActionCommand());
        }
    }

    private void buildContainers() {
        baseContainer = new JPanel(new BorderLayout()); /* Main */
        northContainer = new JPanel(new GridLayout(3, 1));
        centerContainer = new JPanel(new GridLayout(1, 4));
        southContainer = new JPanel(new GridLayout(3, 3)); /* Controller */

        baseContainer.add(northContainer, BorderLayout.NORTH);
        baseContainer.add(centerContainer, BorderLayout.CENTER);
        baseContainer.add(southContainer, BorderLayout.SOUTH); /* Controller */
        this.add(baseContainer, BorderLayout.CENTER); /* Main */
    }

    private void clearContainers() {
        northContainer.removeAll();
        centerContainer.removeAll();
        southContainer.removeAll(); /* Controller */
    }

    private void rebuildContainers() {
        northContainer.repaint();
        northContainer.revalidate();
        centerContainer.repaint();
        centerContainer.revalidate();
        southContainer.repaint(); /* Controller */
        southContainer.revalidate(); /* Controller */
    }

    private void firstScreen() {
        JTextField welcomeTextField = new JTextField("Welcome to Classifier.");
        welcomeTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        welcomeTextField.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeTextField.setEditable(false);

        JTextField startTextField = new JTextField("Click start to begin.");
        startTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        startTextField.setHorizontalAlignment(SwingConstants.CENTER);
        startTextField.setEditable(false);

        JButton startButton = new JButton("Start");
        startButton.setName("Turbo");
        startButton.addActionListener(this);

        northContainer.add(new JLabel(""));
        northContainer.add(welcomeTextField);
        northContainer.add(new JLabel(""));

        centerContainer.add(new JLabel(""));
        centerContainer.add(startTextField);
        centerContainer.add(new JLabel(""));
        // centerContainer.add(new JLabel(""));

        southContainer.add(new JLabel(""));
        southContainer.add(new JLabel(""));
        southContainer.add(new JLabel(""));

        southContainer.add(new JLabel(""));
        southContainer.add(startButton);
        southContainer.add(new JLabel(""));

        southContainer.add(new JLabel(""));
        southContainer.add(new JLabel(""));
        southContainer.add(new JLabel(""));
    }

    private void optionScreen(String name, String nextName) {
        clearContainers();

        JTextField questionTextField = new JTextField("Does the car has " + name + "?");
        questionTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        questionTextField.setHorizontalAlignment(SwingConstants.CENTER);
        questionTextField.setEditable(false);

        JRadioButton yesButton = new JRadioButton("Yes");
        yesButton.setHorizontalAlignment(SwingConstants.CENTER);
        yesButton.setActionCommand("yes");

        JRadioButton noButton = new JRadioButton("No");
        noButton.setHorizontalAlignment(SwingConstants.CENTER);
        noButton.setActionCommand("no");
        noButton.setSelected(true);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(yesButton);
        buttonGroup.add(noButton);

        JButton nextButton = new JButton("Next");
        nextButton.setName(nextName);
        nextButton.addActionListener(this);

        northContainer.add(new JLabel(""));
        northContainer.add(questionTextField);
        northContainer.add(new JLabel(""));

        centerContainer.add(new JLabel(""));
        centerContainer.add(yesButton);
        centerContainer.add(noButton);
        centerContainer.add(new JLabel(""));

        southContainer.add(new JLabel(""));
        southContainer.add(new JLabel(""));
        southContainer.add(new JLabel(""));

        southContainer.add(new JLabel(""));
        southContainer.add(nextButton);
        southContainer.add(new JLabel(""));

        southContainer.add(new JLabel(""));
        southContainer.add(new JLabel(""));
        southContainer.add(new JLabel(""));

        rebuildContainers();
    }

    private void resultScreen() {
        clearContainers();

        JTextField titleTextField = new JTextField("Classifier result.");
        titleTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        titleTextField.setHorizontalAlignment(SwingConstants.CENTER);
        titleTextField.setEditable(false);

        JTextField resultTextField = new JTextField(result);
        resultTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        resultTextField.setHorizontalAlignment(SwingConstants.CENTER);
        resultTextField.setEditable(false);

        JTextField priceTextField = new JTextField("\u20AC 50 ,-");
        priceTextField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        priceTextField.setHorizontalAlignment(SwingConstants.CENTER);
        priceTextField.setEditable(false);

        JButton exitButton = new JButton("Exit");
        exitButton.setName("Exit");
        exitButton.addActionListener(this);

        northContainer.add(new JLabel(""));
        northContainer.add(titleTextField);
        northContainer.add(new JLabel(""));

        centerContainer.add(new JLabel(""));
        centerContainer.add(resultTextField);
        centerContainer.add(priceTextField);
        centerContainer.add(new JLabel(""));

        southContainer.add(new JLabel(""));
        southContainer.add(new JLabel(""));
        southContainer.add(new JLabel(""));

        southContainer.add(new JLabel(""));
        southContainer.add(exitButton);
        southContainer.add(new JLabel(""));

        southContainer.add(new JLabel(""));
        southContainer.add(new JLabel(""));
        southContainer.add(new JLabel(""));

        rebuildContainers();
    }

    private void exit() {
        System.exit(0);
    }

}
