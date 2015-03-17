package gui.views;

import models.device.Device;
import models.environment.Environment;
import models.environment.Obstacle;

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
        model.getRobot().readPosition(d.getRobotPosition());
        Polygon currentShape = d.getShape();
        Polygon globalShape = new Polygon();
        Point2D point = new Point2D.Double();

        for (int i = 0; i < currentShape.npoints; i++) {
            point.setLocation(currentShape.xpoints[i], currentShape.ypoints[i]);
            d.getLocalPosition().rotateAroundAxis(point);
            d.getRobotPosition().rotateAroundAxis(point);
            globalShape.addPoint((int) Math.round(point.getX()), (int) Math.round(point.getY()));
        }

        g.setColor(d.getBackgroundColor());
        g.fillPolygon(globalShape);
        g.setColor(d.getForegroundColor());
        g.drawPolygon(globalShape);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Environment) {
            Environment model = (Environment) e.getSource();
            this.obstacles = model.getObstacles();
        }

        repaint();
    }

}
