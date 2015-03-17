package models.device;

import models.environment.Environment;
import models.environment.Obstacle;
import models.environment.Position;
import models.robot.MobileRobot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Class representing a sensor.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public abstract class Sensor extends Device {

    /**
     * The clockwise.
     */
    protected static final int CLOCKWISE = 1;

    /**
     * The range.
     */
    protected final int range = 100;

    /**
     * The scan measurements.
     */
    protected final ArrayList<Measurement> scanMeasurements;

    /**
     * The orientation.
     */
    protected int orientation = 1;

    /**
     * The rot step.
     */
    protected double rotStep = 1.0;

    /**
     * The num steps.
     */
    protected double numSteps = 0;

    /**
     * The detect.
     */
    protected boolean detect;

    /**
     * The scan.
     */
    protected boolean scan;

    /**
     * The detect measure.
     */
    protected Measurement detectMeasure;

    /**
     * Sensor constructor.
     * Creates a new sensor.
     *
     * @param name The name.
     * @param mobileRobot The mobileRobot.
     * @param localPosition The local position.
     * @param environment THe environment.
     */
    public Sensor(String name, MobileRobot mobileRobot, Position localPosition, Environment environment) {
        super(name, mobileRobot, localPosition, environment);

        this.detect = false;
        this.scan = false;

        this.scanMeasurements = new ArrayList<>();
    }

    /**
     * Reads the distance.
     *
     * @param first The first.
     * @return The read distance.
     */
    public double read(boolean first) {
        Point2D centre = new Point2D.Double(localPosition.getX(), localPosition.getY());
        Point2D front = new Point2D.Double(localPosition.getX() + range * Math.cos(localPosition.getT()), localPosition.getY() + range * Math.sin(localPosition.getT()));

        robot.readPosition(robotPosition);
        robotPosition.rotateAroundAxis(centre);
        robotPosition.rotateAroundAxis(front);

        double minDistance = -1.0;

        for (int i = 0; i < environment.getObstacles().size(); i++) {
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

        if (minDistance > 0) {
            return minDistance;
        }

        return -1.0;
    }

    /**
     * Calculates the point to an obstacle.
     *
     * @param polygon The polygon.
     * @param centre The centre.
     * @param front The front.
     * @param first The first.
     * @return The point to an obstacle.
     */
    public double pointToObstacle(Polygon polygon, Point2D centre, Point2D front, boolean first) {
        int j;
        double minDistance = -1.0;
        double dist;
        double px, py;
        double x1, y1, x2, y2;
        double m1, q1, m2, q2;
        Line2D.Double beam = new Line2D.Double(centre, front);

        for (int i = 0; i < polygon.npoints; i++) {
            j = i + 1;

            if (j == polygon.npoints) {
                j = 0;
            }

            x1 = polygon.xpoints[i];
            y1 = polygon.ypoints[i];
            x2 = polygon.xpoints[j];
            y2 = polygon.ypoints[j];

            if (beam.intersectsLine(x1, y1, x2, y2)) {
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

                dist = Point2D.Double.distance(centre.getX(), centre.getY(), px, py);

                if (minDistance == -1.0 || minDistance > dist) {
                    minDistance = dist;
                }

                if (first && minDistance > 0.0) {
                    return minDistance;
                }
            }
        }

        return minDistance;
    }

    /**
     * Executes the command.
     *
     * @param command The command.
     */
    public void executeCommand(String command) {
        if (command.equalsIgnoreCase("READ")) {
            writeOut("t=" + Double.toString(this.localPosition.getT()) + " d=" + Double.toString(this.read(true)));
        } else if (command.equalsIgnoreCase("SCAN")) {
            this.rotStep = 1.0;
            this.scanMeasurements.clear();
            this.numSteps = 360.0 / rotStep;
            this.orientation = CLOCKWISE;
            this.scan = true;
            this.commands.add("GETMEASURES");
            this.executingCommand = true;
        } else if (command.equalsIgnoreCase("GETMEASURES")) {
            Measurement measure;
            String measures = "SCAN";

            for (Measurement scanMeasure : scanMeasurements) {
                measure = scanMeasure;
                measures += " d=" + measure.distance + " t=" + measure.direction;
            }

            writeOut(measures);
        } else {
            writeOut("DECLINED");
        }
    }

    /**
     * Provides the next step.
     */
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
            System.out.println("detect = true");
            double distance = this.read(true);

            if (distance > -1.0) {
                if (detectMeasure == null) {
                    detectMeasure = new Measurement(distance, localPosition.getT());
                } else if (detectMeasure.distance > distance) {
                    detectMeasure.set(distance, localPosition.getT());
                }
            }
        } else if (scan) {
            double distance = this.read(false);
            if (distance > -1.0) {
                scanMeasurements.add(new Measurement(distance, localPosition.getT()));  // ??????????????
            }
        }
    }

}
