package gui.controllers;

import gui.TreeGUI;
import gui.models.ClassifierModel;
import gui.views.ClassifierView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClassifierController extends JPanel implements ActionListener {

    private ClassifierModel classifierModel;
    private ClassifierView classifierView;
    private ButtonGroup buttonGroup;
    private ButtonModel buttonModel;
    private JTextField answerTextField;

    public ClassifierController(ClassifierModel classifierModel, ClassifierView classifierView) throws Exception {
        this.classifierModel = classifierModel;
        this.classifierView = classifierView;

        buttonGroup = null;
        buttonModel = null;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton jButton = (JButton) actionEvent.getSource();
        String name = jButton.getName();
        String[] features = classifierModel.getFeatures();

        if (buttonGroup != null) {
            buttonModel = buttonGroup.getSelection();
        }

        if (!name.equals("Color") && !name.equals("Weight") && !name.equals("Result") && !name.equals("Tree") && !name.equals("Exit")) {
            for (int i = 0; i < features.length; i++) {
                if (name.equals(features[i])) {
                    if (i != features.length - 1) {
                        classifierModel.addItem(buttonModel, i);
                        classifierView.featureScreen(features[i]);
                        featureScreen(features[i + 1]);
                    } else {
                        classifierModel.addItem(buttonModel, i);
                        classifierView.featureScreen(features[features.length - 1]);
                        featureScreen("Color");
                    }
                }
            }
        } else if (name.equals("Color")) {
            classifierModel.addItem(buttonModel, features.length);
            classifierView.extraQuestionScreen("Color");
            extraQuestionScreen("Weight", "Red", "color");
        } else if (name.equals("Weight")) {
            classifierModel.setColor(answerTextField.getText());
            classifierView.extraQuestionScreen("Weight");
            extraQuestionScreen("Result", "1000", "kg");
        } else if (name.equals("Result")) {
            classifierModel.setCategory(classifierModel.buildCategory());
            classifierModel.setPrice(classifierModel.buildPrice());
            classifierModel.setWeight(Integer.parseInt(answerTextField.getText()));
            classifierView.resultScreen();
            resultScreen();
        } else if (name.equals("Tree")) {
            try {
                TreeGUI treeGUI = new TreeGUI(classifierModel);
                treeGUI.buildGUI();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            exit();
        }
    }

    public void welcomeScreen() {
        JButton startButton = new JButton("Start");
        startButton.setName("Turbo");
        startButton.addActionListener(this);

        add(startButton);
    }

    public void featureScreen(String nextName) {
        removeAll();

        JRadioButton yesButton = new JRadioButton("Yes");
        yesButton.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        yesButton.setHorizontalAlignment(SwingConstants.CENTER);
        yesButton.setActionCommand("yes");

        JRadioButton noButton = new JRadioButton("No");
        noButton.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        noButton.setHorizontalAlignment(SwingConstants.CENTER);
        noButton.setActionCommand("no");
        noButton.setSelected(true);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(yesButton);
        buttonGroup.add(noButton);

        JButton nextButton = new JButton("Next");
        nextButton.setHorizontalAlignment(SwingConstants.CENTER);
        nextButton.setName(nextName);
        nextButton.addActionListener(this);

        JPanel container = new JPanel(new GridLayout(4, 1));
        JPanel buttonsContainer = new JPanel(new GridLayout(1, 2));
        JPanel nextButtonContainer = new JPanel(new BorderLayout());

        buttonsContainer.add(yesButton);
        buttonsContainer.add(noButton);

        nextButtonContainer.add(nextButton);

        container.add(buttonsContainer);
        container.add(new JLabel());
        container.add(new JLabel());
        container.add(nextButtonContainer);

        add(container);

        repaint();
        revalidate();
    }

    public void extraQuestionScreen(String nextName, String defaultValue, String curtly) {
        removeAll();

        answerTextField = new JTextField(defaultValue);
        answerTextField.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        answerTextField.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField typeTextField = new JTextField(curtly);
        typeTextField.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        typeTextField.setHorizontalAlignment(SwingConstants.CENTER);
        typeTextField.setEditable(false);

        JButton nextButton = new JButton("Next");
        nextButton.setHorizontalAlignment(SwingConstants.CENTER);
        nextButton.setName(nextName);
        nextButton.addActionListener(this);

        JPanel container = new JPanel(new GridLayout(4, 1));
        JPanel answerTextContainer = new JPanel(new GridLayout(1, 2));
        JPanel nextButtonContainer = new JPanel(new BorderLayout());

        answerTextContainer.add(answerTextField);
        answerTextContainer.add(typeTextField);

        nextButtonContainer.add(nextButton);

        container.add(answerTextContainer);
        container.add(new JLabel());
        container.add(new JLabel());
        container.add(nextButtonContainer);

        add(container);

        repaint();
        revalidate();
    }

    public void resultScreen() {
        removeAll();

        JButton treeButton = new JButton("Tree");
        treeButton.setName("Tree");
        treeButton.addActionListener(this);

        JButton exitButton = new JButton("Exit");
        exitButton.setName("Exit");
        exitButton.addActionListener(this);

        add(treeButton);
        add(exitButton);

        repaint();
        revalidate();
    }

    public void exit() {
        System.exit(0);
    }

}
