package nl.hanze.dice.views;

import nl.hanze.dice.models.DiceModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextView extends JPanel implements ActionListener {

    DiceModel diceModel;
    private JTextField redStoneField = new JTextField();

    public TextView() {
        this.setLayout(new FlowLayout());
        this.add(redStoneField);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        diceModel = (DiceModel) actionEvent.getSource();
        redStoneField.setText("" + diceModel.getValue());
    }

    public Dimension getPreferredSize() {
        return new Dimension(50, 50);
    }

}
