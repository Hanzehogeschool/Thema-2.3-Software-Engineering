package gui.controller;

import javax.swing.JMenuBar;
import javax.swing.JMenu;

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

/**
 * this class represents the menuBar that's visible on the mainframe
 */
public class MenuBarController extends JMenuBar {


	/**
	 * Constructor
	 * will add all JMenu items in menus to this menuBar
	 *
	 * @param menus JMenu[]
	 */
	public MenuBarController(JMenu[] menus) {
		super();
		for (JMenu menu : menus) {
			this.add(menu);
		}


	}


}
