package gui.controller;

import model.environment.Environment;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


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

public class OccupancyMapController extends JMenu implements ActionListener {

    private final Environment environment;

	private JMenuItem menuFileOpenMap;
	private JMenuItem menuFileExit;


	public OccupancyMapController(Environment environment) {
		super("File");

		this.environment = environment;

        this.menuFileOpenMap = new JMenuItem();
        this.menuFileExit = new JMenuItem();
		// --------------------------- Menu ----------------------------------------

		// Menu File Open Map
		this.menuFileOpenMap = new JMenuItem("Open Map");
        this.menuFileOpenMap.addActionListener(this);

		// Menu File Exit
        this.menuFileExit = new JMenuItem("Exit");
        this.menuFileExit.addActionListener(this);

		// Adds the menu components
		this.add(menuFileOpenMap);
		this.add(menuFileExit);


	}


	/**
	 * Invoked when an action occurs.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(this.menuFileOpenMap)) {
			JFileChooser chooser = new JFileChooser(new File("c:"));
			int returnVal = chooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				if (environment.loadMap(chooser.getSelectedFile())){

				} else {
					JOptionPane.showMessageDialog(null, "This is not a valid Map file!");
				}
			}
		} else if (e.getSource().equals(this.menuFileExit)) {
			System.exit(0);
		}
	}

}
