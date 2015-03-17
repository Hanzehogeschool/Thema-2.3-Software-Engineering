package models.robot;

import models.device.*;
import models.environment.Environment;
import models.environment.Position;
import models.virtualmap.OccupancyMap;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MobileRobot {

    public static int delay = 5;
    private final String name;
    private final Position position;
    private final Platform platform;
    private final ArrayList<Device> sensors;
    private final MobileRobotAI intelligence;
    private PrintWriter output;
    private ThreadPoolExecutor executor;

    public MobileRobot(String name, double x, double y, double t, Environment environment, OccupancyMap map) {
        this.sensors = new ArrayList<>();
        this.name = name;
        this.position = new Position(x, y, Math.toRadians(t));
        this.platform = new Platform("P1", this, environment);
        this.sensors.add(new Laser("L1", this, new Position(20.0, 0.0, 0.0), environment));
        this.sensors.add(new Sonar("S1", this, new Position(10.0, 0.0, 0.0), environment));
        this.intelligence = new MobileRobotAI(this, map);
    }

    public void readPosition(Position position) {
        synchronized (this.position) {
            this.position.copyTo(position);
        }
    }

    public void writePosition(Position position) {
        synchronized (this.position) {
            position.copyTo(this.position);
        }
    }

    public void start() {
        this.executor = new ThreadPoolExecutor(10, 100, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        this.executor.execute(platform);

        for (Device sensor : sensors) {
            this.executor.execute(sensor);
        }

        this.executor.execute(this.intelligence);
    }

    public void quit() {
        this.executor.shutdownNow();
    }

    public boolean sendCommand(String p_command) {
        int indexInit = p_command.indexOf(".");

        if (indexInit < 0) {
            return false;
        }

        String deviceName = p_command.substring(0, indexInit);
        String command = p_command.substring(indexInit + 1);

        if (deviceName.equals(this.name) && command.equalsIgnoreCase("GETPOS")) {
            writeOut("GETPOS X=" + position.getX() + " Y=" + position.getY() + " DIR=" + Math.toDegrees(position.getT()));
        } else if (deviceName.equals(platform.getName())) {
            platform.sendCommand(command);

            return true;
        } else {
            for (Device sensor : sensors) {
                if (deviceName.equals(sensor.getName())) {
                    sensor.sendCommand(command);

                    return true;
                }
            }
        }

        return false;
    }

    public void setOutput(PrintWriter output) {
        this.output = output;
        platform.setOutput(output);

        for (Device sensor : sensors) {
            sensor.setOutput(output);
        }
    }

    public Platform getPlatform() {
        return platform;
    }

    public ArrayList<Device> getSensors() {
        return sensors;
    }

    private synchronized void writeOut(String data) {
        if (output != null) {
            output.println(data);
        } else {
            System.out.println(this.name + " output not initialized");
        }
    }

}
