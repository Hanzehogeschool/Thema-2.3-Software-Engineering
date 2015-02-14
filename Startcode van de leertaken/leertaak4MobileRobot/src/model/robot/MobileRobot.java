package model.robot;

import model.device.Device;
import model.device.Laser;
import model.device.Platform;
import model.environment.Environment;
import model.environment.Position;
import model.virtualmap.OccupancyMap;

import java.io.PrintWriter;

import java.util.ArrayList;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


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


public class MobileRobot {

    public static int delay;

    private final String name;
	private final Position position;
	private final Platform platform;
	private final ArrayList<Device> sensors;

    private final MobileRobotAI intelligence;

	private PrintWriter output;
	private ThreadPoolExecutor executor;

	public MobileRobot(String name, double x, double y, double t, Environment environment,OccupancyMap map) {
		this.sensors = new ArrayList<Device>();
		this.name = name;
		this.position = new Position(x, y, Math.toRadians(t));
		this.platform = new Platform("P1", this, environment);
		this.sensors.add(new Laser("L1", this, new Position(20.0, 0.0, 0.0), environment));
		delay = 50;

		this.intelligence = new MobileRobotAI(this,map);

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
		this.executor = new ThreadPoolExecutor(10, 100,
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		this.executor.execute(platform);

		for (Device sensor : sensors) {
			this.executor.execute(sensor);

		}
		this.executor.execute(this.intelligence);



	}

	public void quit(){
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
			writeOut("GETPOS X=" + position.getX() +
					" Y=" + position.getY() +
					" DIR=" + Math.toDegrees(position.getT()));
		} else if (deviceName.equals(platform.getName())) {
			platform.sendCommand(command);
			return true;
		} else {
			for (Device sensor : sensors) {
				if (deviceName.equals(sensor.getName())){
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


	public void test() {
//		platform.start();
//		platform.println("P1.MOVEFW 50");
//		platform.println("P1.ROTATERIGHT 45");
//		platform.println("P1.MOVEFW 100");
		Laser laser = (Laser) sensors.get(0);
//		laser.start();
		laser.sendCommand("L1.SCAN");
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
