package views;

import models.DiceModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiceView extends JPanel implements ActionListener {

    DiceModel diceModel;
    private int value;
    private Color color;

    public DiceView(Color color) {
        this.color = color;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        diceModel = (DiceModel) actionEvent.getSource();
        value = diceModel.getValue();
        repaint();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(color);
        graphics.fillRoundRect(5, 5, 70, 70, 10, 10);
        graphics.setColor(Color.black);

        if (value == 1) {
            graphics.fillOval(34, 34, 10, 10);
        } else if (value == 2) {
            graphics.fillOval(10, 10, 10, 10);
            graphics.fillOval(60, 60, 10, 10);
        } else if (value == 3) {
            graphics.fillOval(10, 10, 10, 10);
            graphics.fillOval(34, 34, 10, 10);
            graphics.fillOval(60, 60, 10, 10);
        } else if (value == 4) {
            graphics.fillOval(10, 10, 10, 10);
            graphics.fillOval(10, 60, 10, 10);
            graphics.fillOval(60, 10, 10, 10);
            graphics.fillOval(60, 60, 10, 10);
        } else if (value == 5) {
            graphics.fillOval(10, 10, 10, 10);
            graphics.fillOval(10, 60, 10, 10);
            graphics.fillOval(60, 10, 10, 10);
            graphics.fillOval(60, 60, 10, 10);
            graphics.fillOval(34, 34, 10, 10);
        } else if (value == 6) {
            graphics.fillOval(10, 10, 10, 10);
            graphics.fillOval(10, 60, 10, 10);
            graphics.fillOval(60, 10, 10, 10);
            graphics.fillOval(60, 60, 10, 10);
            graphics.fillOval(10, 34, 10, 10);
            graphics.fillOval(60, 34, 10, 10);
        } else ; // meer waarden zijn er niet
    }

    public Dimension getPreferredSize() {
        return new Dimension(80, 80);
    }

}
