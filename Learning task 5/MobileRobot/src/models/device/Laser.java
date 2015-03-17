package models.device;

import models.environment.Environment;
import models.environment.Position;
import models.robot.MobileRobot;

import java.awt.*;

/**
 * Class representing a laser.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class Laser extends Sensor {

    /**
     * Laser constructor.
     * Creates a new laser.
     *
     * @param name The name.
     * @param mobileRobot The robot.
     * @param localPosition The local position.
     * @param environment THe environment.
     */
    public Laser(String name, MobileRobot mobileRobot, Position localPosition, Environment environment) {
        super(name, mobileRobot, localPosition, environment);

        backgroundColor = Color.ORANGE;

        drawLaser();
    }

    /**
     * Draws the laser.
     */
    private void drawLaser() {
        this.addPoint(0, 2);
        this.addPoint(100, 2);
        this.addPoint(100, -2);
        this.addPoint(0, -2);
    }

}
