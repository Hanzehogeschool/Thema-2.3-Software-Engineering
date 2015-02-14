/*
 * (C) Copyright 2005 Davide Brugali, Marco Torchiano
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307  USA
 */

package gui.views;

/**
 * Title:
 * Description:  This window shows the entire model and the robot moving in it.
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import model.device.Device;
import model.environment.Environment;
import model.environment.Obstacle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class SimulationView extends JPanel implements ActionListener {
	private final Environment model;
	private ArrayList<Obstacle> obstacles;

	/**
	 * Construct the frame
	 */
	public SimulationView(Environment model) {
		this.model = model;
		this.obstacles = new ArrayList<Obstacle>();

		this.setLayout(new BorderLayout());
		this.setBackground(SystemColor.window);

	}

	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		Image img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics image = img.getGraphics();

		for (Obstacle obstacle : obstacles) {
			paintObstacle(image, obstacle);
		}

		paintMobileRobot(image);

		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), SystemColor.window, null);

	}

	private void paintObstacle(Graphics g, Obstacle obstacle) {
		Polygon polygon = obstacle.getPolygon();
		boolean opaque = obstacle.getOpaque();

		if (opaque) {
			g.setColor(obstacle.getOpaqueBackgroundColor());
			g.fillPolygon(polygon);
			g.setColor(obstacle.getOpaqueForegroundColor());
			g.drawPolygon(polygon);
		} else {
			g.setColor(obstacle.getBackgroundColor());
			g.fillPolygon(polygon);
			g.setColor(obstacle.getForegroundColor());
			g.drawPolygon(polygon);
		}

	}

	private void paintMobileRobot(Graphics g) {

		paintDevice(g, model.getRobot().getPlatform());

		for (Device sensor : model.getRobot().getSensors()) {
			paintDevice(g, sensor);
		}

	}

	private void paintDevice(Graphics g, Device d) {
		// reads the robot's current position

		model.getRobot().readPosition(d.getRobotPosition());
		Polygon currentShape = d.getShape();
		// draws the shape
		Polygon globalShape = new Polygon();
		Point2D point = new Point2D.Double();
		for (int i = 0; i < currentShape.npoints; i++) {
			point.setLocation(currentShape.xpoints[i], currentShape.ypoints[i]);
			// calculates the coordinates of the point according to the local position
			d.getLocalPosition().rotateAroundAxis(point);
			// calculates the coordinates of the point according to the robot position
			d.getRobotPosition().rotateAroundAxis(point);
			// adds the point to the global shape
			globalShape.addPoint((int) Math.round(point.getX()), (int) Math.round(point.getY()));
		}
		g.setColor(d.getBackgroundColor());
		g.fillPolygon(globalShape);
		g.setColor(d.getForegroundColor());
		g.drawPolygon(globalShape);
	}


	/**
	 * Invoked when an action occurs.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Environment) {
			Environment model = (Environment) e.getSource();
			this.obstacles = model.getObstacles();

		}
		repaint();

	}


}
