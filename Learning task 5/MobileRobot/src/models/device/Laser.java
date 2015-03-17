package models.device;

import models.environment.Environment;
import models.environment.Position;
import models.robot.MobileRobot;

import java.awt.*;

public class Laser extends Sensor {

    public Laser(String name, MobileRobot robot, Position localPos, Environment environment) {
        super(name, robot, localPos, environment);

        backgroundColor = Color.ORANGE;

        drawLaser();
    }

    private void drawLaser() {
        this.addPoint(0, 2);
        this.addPoint(100, 2);
        this.addPoint(100, -2);
        this.addPoint(0, -2);
    }

}
