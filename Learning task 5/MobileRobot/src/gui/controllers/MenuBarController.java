package gui.controllers;

import javax.swing.*;

public class MenuBarController extends JMenuBar {

    public MenuBarController(JMenu[] menus) {
        super();

        for (JMenu menu : menus) {
            this.add(menu);
        }
    }

}
