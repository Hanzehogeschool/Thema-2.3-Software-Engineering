package model.device;

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


import model.environment.Environment;
import model.environment.Obstacle;
import model.environment.Position;
import model.robot.MobileRobot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Laser extends Device {
	// JB: The fact that this code has a lot of comments with question marks in it (see below)
	// does not give a lot of confidence. I have chosen to let the comments in, as they could be
	// indicators where to look if things break down.
	private static final int CLOCKWISE = 1;
	private static final int ANTICLOCKWISE = -1;

	// maximum range
	private final int range = 100;

	private int orientation = 1;

	private double rotStep = 1.0;     // one degree
	private double numSteps = 0;

	// JB: The use of the booleans detect and scan (and Device.running) makes the code very complex
	// and easy to break. See executeCommand() and nextStep(). This could do with a decent refactoring!
	private boolean detect;
	private boolean scan;

	private LaserMeasurement detectMeasure;
	private final ArrayList<LaserMeasurement> scanMeasurements;

	public Laser(String name, MobileRobot robot, Position localPos, Environment environment) {
		super(name, robot, localPos, environment);

		this.detect = false;
		this.scan = false;

		this.scanMeasurements = new ArrayList<LaserMeasurement>();

		backgroundColor = Color.cyan;
		this.addPoint(0, 2);
		this.addPoint(100, 2);
		this.addPoint(100, -2);
		this.addPoint(0, -2);
	}

	double read(boolean first) {
		Point2D centre = new Point2D.Double(localPosition.getX(), localPosition.getY());
		Point2D front = new Point2D.Double(localPosition.getX() + range * Math.cos(localPosition.getT()),
				localPosition.getY() + range * Math.sin(localPosition.getT()));
		// reads the robot's position
		robot.readPosition(robotPosition);
		// center's coordinates according to the robot position
		robotPosition.rotateAroundAxis(centre);
		// front's coordinates according to the robot position
		robotPosition.rotateAroundAxis(front);

		double minDistance = -1.0;
		for (int i = 0; i < environment.getObstacles().size(); i++) {
			// This is really dirty: the laser uses direct access to environment's obstacles
			Obstacle obstacle = environment.getObstacles().get(i);
			if (!obstacle.getOpaque()) {
				double dist = pointToObstacle(obstacle.getPolygon(), centre, front, first);
				if (minDistance == -1.0 || (dist > 0 && dist < minDistance)) {
					minDistance = dist;
					if (minDistance > -1 && first) {
						return minDistance;
					}
				}
			}
		}
		if (minDistance > 0)
			return minDistance;
		return -1.0;
	}

	// receives the vertex coordinates of segment beam;
	// if segment beam intersects an edge of this PhysicalShape, it returns
	// the distance of the first vertex of beam from the closest edge
	// if beam does not intersect the PhysicalShape, the return value is -1.0
	double pointToObstacle(Polygon polygon, Point2D centre, Point2D front, boolean first) {
		int j;
		double minDistance = -1.0;
		double dist;
		double px, py;
		double x1, y1, x2, y2;
		double m1, q1, m2, q2;
		Line2D.Double beam = new Line2D.Double(centre, front);

		for (int i = 0; i < polygon.npoints; i++) {
			j = i + 1;
			if (j == polygon.npoints)
				j = 0;
			x1 = polygon.xpoints[i];
			y1 = polygon.ypoints[i];
			x2 = polygon.xpoints[j];
			y2 = polygon.ypoints[j];
			if (beam.intersectsLine(x1, y1, x2, y2)) {
				// calculates the intersection point
				if (centre.getX() == front.getX()) {
					px = centre.getX();
					py = (y2 - y1) / (x2 - x1) * (px - x1) + y1;
				} else if (x1 == x2) {
					px = x1;
					py = (front.getY() - centre.getY()) / (front.getX() - centre.getX()) * (px - centre.getX()) + centre.getY();
				} else {
					m1 = (y2 - y1) / (x2 - x1);
					q1 = y1 - m1 * x1;
					m2 = (front.getY() - centre.getY()) / (front.getX() - centre.getX());
					q2 = centre.getY() - m2 * centre.getX();
					px = (q2 - q1) / (m1 - m2);
					py = m1 * px + q1;
				}
				// calculates the distance between (cx, cy) and the intersection point
				dist = Point2D.Double.distance(centre.getX(), centre.getY(), px, py);
				if (minDistance == -1.0 || minDistance > dist)
					minDistance = dist;
				if (first && minDistance > 0.0)
					return minDistance;
			}
		}
		return minDistance;
	}

	public void executeCommand(String command) {
		if (command.contains("ROTATETO")) {
			this.rotStep = 4.0;
			double direction = Math.abs(Double.parseDouble(command.trim().substring(9).trim()));

			while (direction < 0.0)
				direction += 360.0;
			while (direction > 360.0)
				direction -= 360.0;
			double dirDiff = direction - Math.toDegrees(localPosition.getT());   // ??????????????
			if (dirDiff >= 0.0 && dirDiff <= 180.0) {
				this.numSteps = dirDiff / rotStep;
				this.orientation = CLOCKWISE;
			} else if (dirDiff >= 0.0 && dirDiff > 180.0) {
				this.numSteps = (360.0 - dirDiff) / rotStep;
				this.orientation = ANTICLOCKWISE;
			} else if (dirDiff < 0.0 && -dirDiff <= 180.0) {
				this.numSteps = -dirDiff / rotStep;
				this.orientation = ANTICLOCKWISE;
			} else if (dirDiff < 0.0 && -dirDiff > 180.0) {
				this.numSteps = (360.0 + dirDiff) / rotStep;
				this.orientation = CLOCKWISE;
			}
			this.executingCommand = true;
		} else if (command.equalsIgnoreCase("READ")) {
			writeOut("t=" + Double.toString(this.localPosition.getT()) + " d=" + Double.toString(this.read(true)));
			// ??????????????
		} else if (command.equalsIgnoreCase("SCAN")) {
			this.rotStep = 1.0;
			this.scanMeasurements.clear();
			this.numSteps = 360.0 / rotStep;
			this.orientation = CLOCKWISE;
			this.scan = true;
			// send the list of measures
			this.commands.add("GETMEASURES");
			this.executingCommand = true;
		} else if (command.equalsIgnoreCase("GETMEASURES")) {
			LaserMeasurement measure;
			String measures = "SCAN";
			for (LaserMeasurement scanMeasure : scanMeasurements) {
				measure = scanMeasure;
				measures += " d=" + measure.distance + " t=" + measure.direction;
			}
			writeOut(measures);
		} else if (command.equalsIgnoreCase("DETECT")) {
			detect = true;
			rotStep = 8.0;
			if (detectMeasure != null) {
				writeOut("LASER DETECT d=" + detectMeasure.distance + " t=" + detectMeasure.direction);
				detectMeasure = null;
			} else if (localPosition.getT() == Math.toRadians(45.0)) {   // ?????????????
				// move the laser to the left position
				commands.add("ROTATETO 315");
				// repeats this command
				commands.add("DETECT");
			} else if (localPosition.getT() == Math.toRadians(315.0)) {  // ??????????????
				// move the laser to the right position
				commands.add("ROTATETO 45");
				// repeats this command
				commands.add("DETECT");
			} else {
				// move the laser to the right position
				commands.add("ROTATETO 45");
				// repeats this command
				commands.add("DETECT");
			}
		} else
			writeOut("DECLINED");
	}


	public void nextStep() {
		if (this.executingCommand && numSteps > 0.0) {
			if (numSteps < 1.0) {
				localPosition.rotateAroundAxis(0.0, 0.0, orientation * numSteps * rotStep);
			} else {
				localPosition.rotateAroundAxis(0.0, 0.0, orientation * rotStep);
			}
			environment.processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
			numSteps -= 1.0;
			this.executingCommand = true;

		} else if (this.executingCommand) {
			this.executingCommand = false;
			if (!detect && !scan) {
				writeOut("LASER ARRIVED");
			}

		}

		if (detect) {
			double distance = this.read(true);
			if (distance > -1.0) {
				if (detectMeasure == null) {
					detectMeasure = new LaserMeasurement(distance, localPosition.getT());  // ?????????????
				} else if (detectMeasure.distance > distance) {
					detectMeasure.set(distance, localPosition.getT());  // ????????????
				}
			}
		} else if (scan) {
			double distance = this.read(false);
			if (distance > -1.0) {
				scanMeasurements.add(new LaserMeasurement(distance, localPosition.getT()));  // ??????????????
			}
		}
	}
}
