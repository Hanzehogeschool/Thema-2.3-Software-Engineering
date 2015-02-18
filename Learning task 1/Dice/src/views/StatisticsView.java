package views;

import models.DiceModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StatisticsView extends JPanel implements ActionListener {

    DiceModel diceModel;
    private JLabel labelTotal = new JLabel();
    private JLabel labelEmpty = new JLabel();
    private JLabel labelOne = new JLabel();
    private JLabel labelTwo = new JLabel();
    private JLabel labelThree = new JLabel();
    private JLabel labelFour = new JLabel();
    private JLabel labelFive = new JLabel();
    private JLabel labelSix = new JLabel();

    public StatisticsView() {
        this.setLayout(new GridLayout(8, 2));
        this.add(labelTotal);
        this.add(labelEmpty);
        this.add(labelOne);
        this.add(labelTwo);
        this.add(labelThree);
        this.add(labelFour);
        this.add(labelFive);
        this.add(labelSix);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        diceModel = (DiceModel) actionEvent.getSource();
        labelTotal.setText(" " + diceModel.getTotalNumberOfRolls() + " worpen");
        labelOne.setText(String.format(" 1: " + diceModel.getNumberOfRolls(1) + " keer"));
        labelTwo.setText(String.format(" 2: " + diceModel.getNumberOfRolls(2) + " keer"));
        labelThree.setText(String.format(" 3: " + diceModel.getNumberOfRolls(3) + " keer"));
        labelFour.setText(String.format(" 4: " + diceModel.getNumberOfRolls(4) + " keer"));
        labelFive.setText(String.format(" 5: " + diceModel.getNumberOfRolls(5) + " keer"));
        labelSix.setText(String.format(" 6: " + diceModel.getNumberOfRolls(6) + " keer"));
    }

    public Dimension getPreferredSize() {
        return new Dimension(125, 50);
    }

}
