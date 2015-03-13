package gui.controllers;

import gui.MainFrame;
import models.robot.MobileRobot;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationController extends JMenu implements ActionListener {

    private final JMenuItem menuSimulationStartPause;
    private final JMenuItem menuSimulationReset;

    private final MobileRobot robot;
    private final MainFrame main;

    public SimulationController(MobileRobot robot, MainFrame main) {
        super("Simulation");

        this.robot = robot;
        this.main = main;

        this.menuSimulationStartPause = new JMenuItem("Start");
        this.menuSimulationStartPause.addActionListener(this);

        this.menuSimulationReset = new JMenuItem("Reset");
        this.menuSimulationReset.addActionListener(this);

        this.add(menuSimulationStartPause);
        this.add(menuSimulationReset);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.menuSimulationStartPause)) {
            if (this.menuSimulationStartPause.getText().equals("Start")) {
                this.robot.start();
                this.menuSimulationStartPause.setText("Pause");
            } else {
                this.robot.quit();
                this.menuSimulationStartPause.setText("Start");
            }
        } else if (e.getSource().equals(this.menuSimulationReset)) {
            if (this.menuSimulationStartPause.getText().equals("Pause")) {
                this.robot.quit();
                this.menuSimulationStartPause.setText("Start");
                this.main.init();
            } else {
                this.main.init();
            }
        }
    }

}
