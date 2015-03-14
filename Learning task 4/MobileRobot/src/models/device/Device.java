package models.device;

import models.environment.Environment;
import models.environment.Position;
import models.robot.MobileRobot;
import utils.ANSI;

import java.awt.*;
import java.io.PrintWriter;
import java.util.ArrayList;

public abstract class Device implements Runnable {

    protected final Environment environment;
    protected final MobileRobot robot;
    protected final Position localPosition;
    protected final ArrayList<String> commands;
    private final Object lock = new Object();
    private final String name;
    private final Polygon shape;
    protected Position robotPosition;
    protected Color backgroundColor = Color.GRAY;
    protected Color foregroundColor = Color.BLACK;
    protected boolean running;
    protected boolean executingCommand;
    private PrintWriter output;

    protected Device(String name, MobileRobot robot, Position local, Environment environment) {
        this.name = name;
        this.robot = robot;
        this.localPosition = local;
        this.environment = environment;

        this.shape = new Polygon();
        this.robotPosition = new Position();

        this.running = true;
        this.executingCommand = false;

        this.commands = new ArrayList<>();
        this.output = null;
        robot.readPosition(this.robotPosition);
    }

    protected void addPoint(int x, int y) {
        shape.addPoint(x, y);
    }

    public boolean sendCommand(String command) {
        commands.add(command);

        synchronized (lock) {
            lock.notify();
        }

        return true;
    }

    protected synchronized void writeOut(String data) {
        if (output != null) {
            output.println(data);
        } else {
            System.out.println(this.name + " output not initialized");
        }

        System.out.println(ANSI.ANSI_YELLOW + data);
    }

    public void setOutput(PrintWriter output) {
        this.output = output;
    }

    public void run() {
        System.out.println(ANSI.ANSI_GREEN + "Device " + this.name + " running");

        do {
            try {
                if (executingCommand) {
                    synchronized (this) {
                        Thread.sleep(MobileRobot.delay);
                    }
                } else if (commands.size() > 0) {
                    String command = commands.remove(0);
                    executeCommand(command);
                } else {
                    synchronized (lock) {
                        lock.wait();
                    }
                }

                nextStep();
            } catch (InterruptedException ie) {
                System.err.println("Device: Run was interrupted.");
            }
        } while (this.running);
    }

    public Position getRobotPosition() {
        return robotPosition;
    }

    public Position getLocalPosition() {
        return localPosition;
    }

    public Polygon getShape() {
        return shape;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public String getName() {
        return name;
    }

    protected abstract void executeCommand(String command);

    protected abstract void nextStep();

}
