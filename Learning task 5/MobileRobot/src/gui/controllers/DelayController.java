package gui.controllers;

import models.environment.Environment;
import models.robot.MobileRobot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DelayController extends JMenu implements ActionListener {

    private final Environment environment;
    private JTextField txtDelay;
    private JButton btnSet;
    private JButton btnIncrease;
    private JButton btnDecrease;
    private JLabel lblStepSize;
    private int delay;

    public DelayController(Environment environment) {
        super("Delay");

        this.environment = environment;
        this.delay = 5;

        JPanel pnlDelay = new JPanel();
        pnlDelay.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        lblStepSize = new JLabel("Robot delay (ms): ");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        pnlDelay.add(lblStepSize, c);

        txtDelay = new JTextField("" + this.delay);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 0;
        pnlDelay.add(txtDelay, c);

        btnSet = new JButton("set");
        btnSet.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        pnlDelay.add(btnSet, c);

        btnIncrease = new JButton("+");
        btnIncrease.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 1;
        pnlDelay.add(btnIncrease, c);

        btnDecrease = new JButton("-");
        btnDecrease.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 1;
        pnlDelay.add(btnDecrease, c);

        this.add(pnlDelay);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int increment = 0;

        if (this.delay < 10 && this.delay > 0) {
            increment = 1;
        } else if (this.delay >= 10 && this.delay < 50) {
            increment = 5;
        } else if (this.delay >= 50 && this.delay < 100) {
            increment = 10;
        } else if (this.delay >= 100) {
            increment = 20;
        }

        if (e.getSource() == btnSet) {
            try {
                this.delay = Integer.parseInt(txtDelay.getText());
            } catch (Exception exception) {
                this.delay = 50;
            }
        } else if (e.getSource() == btnIncrease) {
            this.delay += increment;
            txtDelay.setText("" + this.delay);
        } else if (e.getSource() == btnDecrease) {
            this.delay -= (this.delay > increment) ? increment : 0;
            txtDelay.setText("" + this.delay);
        }

        if (this.delay > 0) {
            MobileRobot.delay = this.delay;
        }
    }

}
