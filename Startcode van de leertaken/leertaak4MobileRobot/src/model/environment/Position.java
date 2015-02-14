package model.environment;

import java.awt.geom.Point2D;

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


public class Position {
    private double x = 0.0;
    private double y = 0.0;
    private double t = 0.0;

    public Position() {
        set(0.0, 0.0, 0.0);
    }

    public Position(double x, double y, double t) {
        set(x, y, t);
    }

    public void rotateAroundAxis(double da, double db, double dt) {
        this.x += da * Math.cos(this.t) - db * Math.sin(this.t);
        this.y += da * Math.sin(this.t) + db * Math.cos(this.t);
        this.t += Math.toRadians(dt);
        while (this.t > 2.0 * Math.PI){
            this.t -= 2.0 * Math.PI;
        }
        while (this.t < 0.0)  {
            this.t += 2.0 * Math.PI;
        }
    }

    public void rotateAroundAxis(Point2D point) {
        double px = point.getX() * Math.cos(t) - point.getY() * Math.sin(t) + x;
        double py = point.getX() * Math.sin(t) + point.getY() * Math.cos(t) + y;
        point.setLocation(px, py);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getT() {
        return t;
    }

    public void copyTo(Position position) {
        position.x = this.x;
        position.y = this.y;
        position.t = this.t;
    }

    public String toString() {
        return "(" + x + "," + y + "," + t + ")";
    }

    private void set(double x, double y, double t) {
        this.x = x;
        this.y = y;
        this.t = t;
        while (this.t > 2.0 * Math.PI)
            this.t -= 2.0 * Math.PI;
        while (this.t < 0.0)
            this.t += 2.0 * Math.PI;
    }
}
