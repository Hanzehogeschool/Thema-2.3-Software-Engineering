package model.environment;

import model.robot.MobileRobot;
import model.virtualmap.OccupancyMap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/**
 * Title    :   The Mobile Robot Explorer Simulation Environment v2.0
 * Copyright:   GNU General Public License as published by the Free Software Foundation
 * Company  :   Hanze University of Applied Sciences
 *
 * @author Dustin Meijer        (2012)
 * @author Alexander Jeurissen  (2012)
 * @author Davide Brugali       (2002)
 * @version 2.0
 */

@SuppressWarnings("serial")
public class Environment {
	private final ArrayList<Obstacle> obstacles;
	private final MobileRobot robot;

	private final ArrayList<ActionListener> actionListenerList;

	/**
	 * Construct the application
	 */
	public Environment(OccupancyMap map) {
		this.obstacles = new ArrayList<Obstacle>();
		robot = new MobileRobot("R1", 90, 200, 270, this, map);
		actionListenerList = new ArrayList<ActionListener>();
	}

	public boolean loadMap(File mapFile) {
		// removes all the obstacles already loaded
		obstacles.clear();

		// loads the new obstacles
		try {
			FileInputStream inStream = new FileInputStream(mapFile);
			BufferedReader lineReader = new BufferedReader(new InputStreamReader(inStream));
			String docLine;

			// Check the first line if this is a map-file
			docLine = lineReader.readLine();
			if ((docLine == null) || (!docLine.contains("<MAP>"))) {
				inStream.close();
				return false;
			}

			// reads the file line by line
			while ((docLine = lineReader.readLine()) != null) {
				if (docLine.contains("<OBSTACLE")) {
					Obstacle obstacle = new Obstacle();
					if (obstacle.parseXML(docLine, lineReader))
						obstacles.add(obstacle);
					else {
						inStream.close();
						return false;
					}
				} else {
				}
			}
			inStream.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
		return true;
	} // end loadMap()

	public MobileRobot getRobot() {
		return robot;
	}

	public String toString() {
		String xml = "<MAP>" + "\n";
		for (Obstacle obstacle : obstacles) {
			xml += obstacle.toString() + "\n";
		}
		xml += "</MAP>\n\n";
		return xml;
	}

	public ArrayList<Obstacle> getObstacles() {
		return this.obstacles;
	}

	/**
	 * This method allows other objects to register as ActionListeners.
	 *
	 * @param listener the ActionListener to add to the watchlist
	 */
	public void addActionListener(ActionListener listener) {
		actionListenerList.add(listener);
	}

	/**
	 * This method will remove the given ActionListener from the registered ActionListeners.
	 *
	 * @param listener the ActionListener to remove from the watchlist
	 */
	public void removeActionListener(ActionListener listener) {
		if (actionListenerList.contains(listener)) {
			actionListenerList.remove(listener);
		}
	}

	/**
	 * This method is intended to notify all ActionListeners of a event.
	 *
	 * @param event the event to be processed
	 */
	public void processEvent(ActionEvent event) {

		for (ActionListener listener : actionListenerList) {
			listener.actionPerformed(event);
		}
	}

}
