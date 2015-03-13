package gui;

import gui.controllers.DelayController;
import gui.controllers.MenuBarController;
import gui.controllers.OccupancyMapController;
import gui.controllers.SimulationController;
import gui.views.OccupancyMapView;
import gui.views.SimulationView;
import models.environment.Environment;
import models.virtualmap.OccupancyMap;
import utils.ANSI;
import utils.Debugger;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public static void main(String[] args) {
        System.out.println(ANSI.ANSI_BLUE + "Mobile Robot");
        Debugger.debug = true;
        MainFrame runner = new MainFrame();
        runner.init();
    }

    public void init() {
        if (this.isVisible()) {
            this.setContentPane(new JPanel());
        }

        OccupancyMap map = new OccupancyMap();
        Environment environment = new Environment(map);
        map.setEnvironment(environment);

        SimulationController occupancyMenu = new SimulationController(environment.getRobot(), this);
        OccupancyMapController simulationMenu = new OccupancyMapController(environment);
        DelayController settingsMenu = new DelayController(environment);

        JMenuBar menuBar = new MenuBarController(
                new JMenu[]{
                        occupancyMenu,
                        simulationMenu,
                        settingsMenu
                }
        );

        SimulationView simulationView = new SimulationView(environment);
        simulationView.validate();

        OccupancyMapView mapView = new OccupancyMapView(map);
        mapView.validate();

        map.addActionListener(mapView);
        environment.addActionListener(simulationView);

        JTabbedPane left = new JTabbedPane();
        left.add(mapView, "Map view");

        JTabbedPane right = new JTabbedPane();
        right.add(simulationView, "Simulation view");

        this.setLayout(new GridLayout(1, 0));
        this.setJMenuBar(menuBar);
        this.add(left);
        this.add(right);

        left.validate();
        right.validate();
        left.setVisible(true);
        right.setVisible(true);

        this.setSize(1024, 560);
        this.validate();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Mobile Robot Explorer ~ by Nils Berlijn & Tom Broenink (2015), based on Davide Brugali (2002)");
        this.setVisible(true);
    }

}
