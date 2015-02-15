package gui;

import gui.views.CalculatorView;
import gui.views.CounterView;
import multiformat.Calculator;

import javax.swing.*;
import java.awt.*;

public class CalculatorGUI extends JFrame {

    private Calculator calculator;
    private CalculatorView calculatorView;
    private CounterView counterView;

    public CalculatorGUI() {
        this.calculator = new Calculator();
        makeGUI();
    }

    public static void main(String[] args) {
        new CalculatorGUI();
    }

    private void makeGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.getContentPane();
        this.setLayout(new BorderLayout());

        calculatorView = new CalculatorView(calculator);
        this.add(calculatorView, BorderLayout.CENTER);

        counterView = new CounterView(calculator);
        this.add(counterView, BorderLayout.SOUTH);

        this.pack();
        this.setSize(500, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

}
