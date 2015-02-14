package nl.hanze.dice.controllers;

import nl.hanze.dice.models.DiceModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DiceController extends JPanel implements ActionListener {

    DiceModel diceModel;
    private JButton higher = new JButton("hoger");
    private JButton lower = new JButton("lager");
    private JButton fling = new JButton("gooi");

    public DiceController(DiceModel diceModel) {
        this.diceModel = diceModel;
        this.add(higher);
        higher.addActionListener(this);
        this.add(lower);
        lower.addActionListener(this);
        this.add(fling);
        fling.addActionListener(this);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == higher) {
            diceModel.higher();
        }

        if (actionEvent.getSource() == lower) {
            diceModel.lower();
        }

        if (actionEvent.getSource() == fling) {
            diceModel.fling();
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(50, 50);
    }

}
