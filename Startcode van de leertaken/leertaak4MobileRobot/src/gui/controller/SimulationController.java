package gui.controller;

import gui.MainFrame;
import model.robot.MobileRobot;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Title    :   The Mobile Robot Explorer Simulation Environment v2.0
 * Copyright:   GNU General Public License as published by the Free Software Foundation
 * Company  :   Hanze University of Applied Sciences
 *
 * @author Alexander Jeurissen  (2012)
 * @author Dustin Meijer        (2012)
 * @version 2.0
 */

public class SimulationController extends JMenu implements ActionListener {

	private final JMenuItem menuSimulationStartPause;
	private final JMenuItem menuSimulationReset;

	private final MobileRobot robot;
	private final MainFrame main;

	public SimulationController(MobileRobot robot, MainFrame main) {
		super("Simulation");

		this.robot = robot;
		this.main = main;
		// --------------------------- Menu ----------------------------------------

		// Menu Simulation Start
		this.menuSimulationStartPause = new JMenuItem("Start");
		this.menuSimulationStartPause.addActionListener(this);


		// Menu Simulation Reset
		this.menuSimulationReset = new JMenuItem("reset");
		this.menuSimulationReset.addActionListener(this);

		// Menu
		this.add(menuSimulationStartPause);
		this.add(menuSimulationReset);

	}


	/**
	 * Invoked when an action occurs.
	 */
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
