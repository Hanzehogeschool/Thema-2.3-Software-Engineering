package gui.controllers;

import models.environment.Environment;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class OccupancyMapController extends JMenu implements ActionListener {

    private final Environment environment;

    private JMenuItem menuFileOpenMap;
    private JMenuItem menuFileExit;

    public OccupancyMapController(Environment environment) {
        super("File");

        this.environment = environment;

        this.menuFileOpenMap = new JMenuItem();
        this.menuFileExit = new JMenuItem();

        this.menuFileOpenMap = new JMenuItem("Open Map");
        this.menuFileOpenMap.addActionListener(this);

        this.menuFileExit = new JMenuItem("Exit");
        this.menuFileExit.addActionListener(this);

        this.add(menuFileOpenMap);
        this.add(menuFileExit);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.menuFileOpenMap)) {
            JFileChooser chooser = new JFileChooser(new File("c:"));
            int returnVal = chooser.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                if (environment.loadMap(chooser.getSelectedFile())) {

                } else {
                    JOptionPane.showMessageDialog(null, "This is not a valid Map file!");
                }
            }
        } else if (e.getSource().equals(this.menuFileExit)) {
            System.exit(0);
        }
    }

}
