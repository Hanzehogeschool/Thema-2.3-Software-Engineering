package gui.views;

import multiformat.Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CounterView extends JPanel implements ActionListener {

    private JLabel labelCounter = new JLabel();
    private Calculator calculator;

    public CounterView(Calculator calculator) {
        this.setLayout(new FlowLayout());
        this.add(labelCounter);
        this.calculator = calculator;

        labelCounter.setText("Amount of calculations: " + calculator.getCounter());

        calculator.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        labelCounter.setText("Amount of calculations: " + calculator.getCounter());
    }

}
