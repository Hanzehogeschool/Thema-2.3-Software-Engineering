package gui;

/**
 * Title    :   The Mobile Robot Explorer Simulation Environment v2.0
 * Copyright:   GNU General Public License as published by the Free Software Foundation
 * Company  :   Hanze University of Applied Sciences
 *
 *
 * @author Alexander Jeurissen  (2012)
 * @author Dustin Meijer        (2012)
 *
 * @version 2.0
 */

import gui.controller.MenuBarController;
import gui.controller.DelayController;
import gui.controller.SimulationController;
import gui.controller.OccupancyMapController;

import gui.views.OccupancyMapView;
import gui.views.SimulationView;

import model.environment.Environment;
import model.virtualmap.OccupancyMap;

import javax.swing.*;

import java.awt.*;


/**
 * This class represents the JFrame where
 * all the controllers and views will be added to.
 */

public class MainFrame extends JFrame {
	/**
	 * Constructor of mainframe
	 */
	private MainFrame() {
		// JB: This application does NOT use the Model-View-Controller standard :-(.
		// This means you will have to figure out how the different classes interact for yourself.
		// Invest some time. Take a look at the book and draw a diagram.

		//@JB: That's why we decided to refactor this project and implement it in proper mvc structure
		//this class was originally named: TestApplication
	}


	public void init() {
		if (this.isVisible()) {
			this.setContentPane(new JPanel());
		}


//	Models:
		//OccupancyMap

		OccupancyMap map = new OccupancyMap();
		Environment environment = new Environment(map);
		map.setEnvironment(environment);

//  Controllers:
		// menus
		SimulationController occupancyMenu = new SimulationController(environment.getRobot(), this);
		OccupancyMapController simulationMenu = new OccupancyMapController(environment);
		DelayController settingsMenu = new DelayController(environment);
		// menubar
		JMenuBar menuBar = new MenuBarController(
				new JMenu[]{
						occupancyMenu,
						simulationMenu,
						settingsMenu
				}
		);


//	Views:
		// simulationView
		SimulationView simulationView = new SimulationView(environment);
		simulationView.validate();

		// occupancyMapView

		OccupancyMapView mapView = new OccupancyMapView(map);
		mapView.validate();

		map.addActionListener(mapView);
		environment.addActionListener(simulationView);

		JTabbedPane left = new JTabbedPane();
		left.add(mapView, "Map view");

		JTabbedPane right = new JTabbedPane();
		right.add(simulationView, "Simulation view");

		this.setLayout(new GridLayout(1, 0));
		this.setJMenuBar(menuBar);
		this.add(left);
		this.add(right);

		left.validate();
		right.validate();
		left.setVisible(true);
		right.setVisible(true);

		this.setSize(1024, 560);
		this.validate();    //double check size
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setTitle("Mobile Robot Explorer V2.0 ~ by Dustin Meijer & Alexander Jeurissen (2012), based on Davide Brugali (2002)");
		this.setVisible(true);

	}

	/**
	 * The static void main, this is where the program starts.
	 *
	 * @param args String[]
	 */
	public static void main(String[] args) {

		MainFrame runner = new MainFrame();
		runner.init();
	}


}
