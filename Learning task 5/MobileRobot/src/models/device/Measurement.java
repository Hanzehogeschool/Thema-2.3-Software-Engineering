package models.device;

public class Measurement {

    protected double distance;
    protected double direction;

    protected Measurement(double distance, double direction) {
        this.set(distance, direction);
        this.processDirectionValue();
    }

    protected void set(double distance, double direction) {
        this.distance = distance;
        this.direction = direction;
    }

    private void processDirectionValue() {
        while (direction >= 2.0 * Math.PI) {
            direction -= 2.0 * Math.PI;
        }

        while (direction < 0.0) {
            direction += 2.0 * Math.PI;
        }
    }

}
