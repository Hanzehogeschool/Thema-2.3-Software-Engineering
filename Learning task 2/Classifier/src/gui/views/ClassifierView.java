package gui.views;

import gui.models.ClassifierModel;

import javax.swing.*;
import java.awt.*;

public class ClassifierView extends JPanel {

    private ClassifierModel classifierModel;

    public ClassifierView(ClassifierModel classifierModel) {
        this.classifierModel = classifierModel;
    }

    public void welcomeScreen() {
        setLayout(new GridLayout(2, 1));

        JTextField welcomeTextField = new JTextField("Welcome to Classifier.");
        welcomeTextField.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        welcomeTextField.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeTextField.setEditable(false);

        JTextField startTextField = new JTextField("Click start to begin.");
        startTextField.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        startTextField.setHorizontalAlignment(SwingConstants.CENTER);
        startTextField.setEditable(false);

        add(welcomeTextField);
        add(startTextField);
    }

    public void featureScreen(String name) {
        removeAll();

        setLayout(new BorderLayout());

        JTextField questionTextField = new JTextField("Does the car has " + name + "?");
        questionTextField.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        questionTextField.setHorizontalAlignment(SwingConstants.CENTER);
        questionTextField.setEditable(false);

        add(questionTextField);

        repaint();
        revalidate();
    }

    public void extraQuestionScreen(String name) {
        removeAll();

        setLayout(new BorderLayout());

        JTextField questionTextField = new JTextField("What is the " + name + " of the car?");
        questionTextField.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        questionTextField.setHorizontalAlignment(SwingConstants.CENTER);
        questionTextField.setEditable(false);

        add(questionTextField);

        repaint();
        revalidate();
    }

    public void resultScreen() {
        removeAll();

        setLayout(new BorderLayout());

        JTextField resultTextfield = new JTextField("Classifier result:");
        resultTextfield.setBorder(BorderFactory.createEmptyBorder(-15, 0, 0, 0));
        resultTextfield.setHorizontalAlignment(SwingConstants.CENTER);
        resultTextfield.setEditable(false);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField carCategoryTextField = new JTextField(classifierModel.getCategory());
        carCategoryTextField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        carCategoryTextField.setHorizontalAlignment(SwingConstants.CENTER);
        carCategoryTextField.setEditable(false);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField carPriceTextField = new JTextField("\u20AC " + classifierModel.getPrice() + " ,-");
        carPriceTextField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        carPriceTextField.setHorizontalAlignment(SwingConstants.CENTER);
        carPriceTextField.setEditable(false);

        JLabel colorLabel = new JLabel("Color:");
        colorLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        colorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField carColorTextField = new JTextField(classifierModel.getColor());
        carColorTextField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        carColorTextField.setHorizontalAlignment(SwingConstants.CENTER);
        carColorTextField.setEditable(false);

        JLabel colorWeight = new JLabel("Weight:");
        colorWeight.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        colorWeight.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField carWeightTextField = new JTextField(classifierModel.getWeight() + " kg");
        carWeightTextField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        carWeightTextField.setHorizontalAlignment(SwingConstants.CENTER);
        carWeightTextField.setEditable(false);

        JPanel container = new JPanel(new GridLayout(2, 1));
        JPanel resultContainer = new JPanel(new GridLayout(1, 1));
        JPanel resultsContainer = new JPanel(new GridLayout(4, 4));

        resultContainer.add(resultTextfield);

        resultsContainer.add(new JLabel());
        resultsContainer.add(categoryLabel);
        resultsContainer.add(carCategoryTextField);
        resultsContainer.add(new JLabel());

        resultsContainer.add(new JLabel());
        resultsContainer.add(priceLabel);
        resultsContainer.add(carPriceTextField);
        resultsContainer.add(new JLabel());

        resultsContainer.add(new JLabel());
        resultsContainer.add(colorLabel);
        resultsContainer.add(carColorTextField);
        resultsContainer.add(new JLabel());

        resultsContainer.add(new JLabel());
        resultsContainer.add(colorWeight);
        resultsContainer.add(carWeightTextField);
        resultsContainer.add(new JLabel());

        container.add(resultContainer);
        container.add(resultsContainer);

        add(container);

        repaint();
        revalidate();
    }

}
