package nl.hanze.dice;

import nl.hanze.dice.controllers.DiceController;
import nl.hanze.dice.views.DiceView;
import nl.hanze.dice.views.StatisticsView;
import nl.hanze.dice.models.DiceModel;
import nl.hanze.dice.views.TextView;

import javax.swing.*;
import java.awt.*;

public class Dice extends JApplet {

    DiceModel diceModel;
    DiceView diceView;
    DiceController diceController;
    TextView textView;
    StatisticsView statisticsView;

    public void init() {
        resize(250, 200);

        diceModel = new DiceModel();

        diceController = new DiceController(diceModel);
        diceController.setBackground(Color.cyan);
        getContentPane().add(diceController, BorderLayout.NORTH);

        diceView = new DiceView(Color.red);
        diceView.setBackground(Color.black);
        getContentPane().add(diceView, BorderLayout.CENTER);

        textView = new TextView();
        textView.setBackground(Color.green);
        getContentPane().add(textView, BorderLayout.SOUTH);

        statisticsView = new StatisticsView();
        statisticsView.setBackground(Color.white);
        getContentPane().add(statisticsView, BorderLayout.EAST);

        diceModel.addActionListener(diceView);
        diceModel.addActionListener(textView);
        diceModel.addActionListener(statisticsView);

        diceModel.fling();
    }

}
