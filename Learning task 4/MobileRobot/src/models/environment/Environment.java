package models.environment;

import models.robot.MobileRobot;
import models.virtualmap.OccupancyMap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Environment {

    private final ArrayList<Obstacle> obstacles;
    private final MobileRobot robot;

    private final ArrayList<ActionListener> actionListenerList;

    public Environment(OccupancyMap map) {
        this.obstacles = new ArrayList<>();
        robot = new MobileRobot("R1", 90, 200, 270, this, map);
        actionListenerList = new ArrayList<>();
    }

    public boolean loadMap(File mapFile) {
        obstacles.clear();

        try {
            FileInputStream inStream = new FileInputStream(mapFile);
            BufferedReader lineReader = new BufferedReader(new InputStreamReader(inStream));
            String docLine;

            docLine = lineReader.readLine();

            if ((docLine == null) || (!docLine.contains("<MAP>"))) {
                inStream.close();
                return false;
            }

            while ((docLine = lineReader.readLine()) != null) {
                if (docLine.contains("<OBSTACLE")) {
                    Obstacle obstacle = new Obstacle();
                    if (obstacle.parseXML(docLine, lineReader))
                        obstacles.add(obstacle);
                    else {
                        inStream.close();
                        return false;
                    }
                } else {

                }
            }

            inStream.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        return true;
    }

    public MobileRobot getRobot() {
        return robot;
    }

    public String toString() {
        String xml = "<MAP>" + "\n";

        for (Obstacle obstacle : obstacles) {
            xml += obstacle.toString() + "\n";
        }

        xml += "</MAP>\n\n";

        return xml;
    }

    public ArrayList<Obstacle> getObstacles() {
        return this.obstacles;
    }

    public void addActionListener(ActionListener listener) {
        actionListenerList.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        if (actionListenerList.contains(listener)) {
            actionListenerList.remove(listener);
        }
    }

    public void processEvent(ActionEvent event) {
        for (ActionListener listener : actionListenerList) {
            listener.actionPerformed(event);
        }
    }

}
