package models.device;

import models.environment.Environment;
import models.environment.Obstacle;
import models.environment.Position;
import models.robot.MobileRobot;
import utils.ANSI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;

/**
 * Class representing a sonar.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class Sonar extends Sensor {

    /**
     * Sonar constructor.
     * Creates a new sonar.
     *
     * @param name The name.
     * @param mobileRobot The mobileRobot.
     * @param localPosition The local position.
     * @param environment THe environment.
     */
    public Sonar(String name, MobileRobot mobileRobot, Position localPosition, Environment environment) {
        super(name, mobileRobot, localPosition, environment);

        backgroundColor = new Color(0, 255, 0, 50);

        drawSonar(10.0);
    }

    /**
     * Reads the distance.
     *
     * @param first The first.
     * @return The read distance.
     */
    @Override
    public double read(boolean first) {
        double minDistance = -1.0;

        Point2D centre = new Point2D.Double(localPosition.getX(), localPosition.getY());
        Point2D front = new Point2D.Double(localPosition.getX(), localPosition.getY() + numSteps);

        robot.readPosition(robotPosition);
        robotPosition.rotateAroundAxis(centre);
        robotPosition.rotateAroundAxis(front);

        for (int i = 0; i < environment.getObstacles().size(); i++) {
            Obstacle obstacle = environment.getObstacles().get(i);
            if (obstacle.getOpaque()) {
                obstacle.setOpaqueBackgroundColor(Color.PINK);
                double dist = pointToObstacle(obstacle.getPolygon(), centre, front, first);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                obstacle.setOpaqueBackgroundColor(Color.magenta);

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
    @Override
    public double pointToObstacle(Polygon polygon, Point2D centre, Point2D front, boolean first) {
        double minDistance = -1.0;
        double dist;
        int radius = (int) centre.distance(front) / 2;
        int x1, y1;

        for (int i = 0; i < polygon.npoints; i++) {
            x1 = polygon.xpoints[i];
            y1 = polygon.ypoints[i];

            if (geometry(x1, y1, centre, radius)) {
                Point2D p = new Point(x1, y1);
                dist = centre.distance(p);

                System.out.println(ANSI.ANSI_RED + "center x: " + centre.getX() + "\t x: " + x1 + " = " + (centre.getX() - x1) + "\t\t\t center y: " + centre.getY() + "\t y: " + y1 + " = " + (centre.getY() - y1) + "\t\t\t distance: " + dist);

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
    @Override
    public void executeCommand(String command) {
        if (command.equalsIgnoreCase("READ")) {
            writeOut("t=" + Double.toString(this.localPosition.getT()) + " d=" + Double.toString(this.read(true)));
        } else if (command.equalsIgnoreCase("SCAN")) {
            this.scanMeasurements.clear();
            this.numSteps = 10;
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
        } else
            writeOut("DECLINED");
    }

    /**
     * Provides the next step.
     */
    @Override
    public void nextStep() {
        if (this.executingCommand && numSteps < this.range) {
            if (numSteps >= this.range) {
                drawSonar(10);
            } else {
                drawSonar(numSteps);
            }

            environment.processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
            numSteps += 1.0;
            this.executingCommand = true;
        } else if (this.executingCommand) {
            this.executingCommand = false;
            drawSonar(10);

            if (!detect && !scan) {
                writeOut("SONAR ARRIVED");
            }
        }

        if (detect) {
            double distance = this.read(true);

            if (distance > -1.0) {
                if (detectMeasure == null) {
                    detectMeasure = new Measurement(distance, localPosition.getT());  // ?????????????
                } else if (detectMeasure.distance > distance) {
                    detectMeasure.set(distance, localPosition.getT());  // ????????????
                }
            }
        } else if (scan) {
            double distance = this.read(false);

            if (distance > -1.0) {
                scanMeasurements.add(new Measurement(distance, localPosition.getT()));  // ??????????????
            }
        }
    }

    /**
     * Draws the sonar.
     *
     * @param radius The radius.
     */
    private void drawSonar(double radius) {
        this.getShape().reset();

        double x = localPosition.getX();
        double y = localPosition.getY();

        double twoPi = (2 * Math.PI);
        double step = twoPi / 360.0;

        for (double i = 0.0; i < twoPi; i += step) {
            double rx = x + radius * Math.cos(i);
            double ry = y + radius * Math.sin(i);
            this.addPoint((int) rx, (int) ry);
        }
    }

    /**
     * The geometry.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param center The center.
     * @param radius The radius.
     * @return If the geometry is possible.
     */
    private boolean geometry(int x, int y, Point2D center, int radius) {
        double dx = Math.abs(x - center.getX());
        double dy = Math.abs(y - center.getY());

        if (dx > radius) {
            return false;
        } else if (dy > radius) {
            return false;
        } else if (dx + dy <= radius) {
            return true;
        } else if (Math.pow(dx, 2) + Math.pow(dy, 2) <= Math.pow(radius, 2)) {
            return true;
        } else {
            return false;
        }
    }

}
