package models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class DiceModel {

    private int value;
    private ArrayList<ActionListener> actionListenerList = new ArrayList<ActionListener>();
    private int totalNumberOfRolls = 0;
    private int[] numberOfRolls = new int[6];

    public DiceModel() {
        value = (int) (Math.random() * 6 + 1);
    }

    public int getValue() {
        return value;
    }

    public void higher() {
        value++;
        if (value > 6) value = 1;
        addTotalNumberOfRolls();
        addNumberOfRolls();
        processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
    }

    public void lower() {
        value--;
        if (value < 1) value = 6;
        addTotalNumberOfRolls();
        addNumberOfRolls();
        processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
    }

    public void fling() {
        value = (int) (Math.random() * 6 + 1);
        addTotalNumberOfRolls();
        addNumberOfRolls();
        processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
    }

    public void addActionListener(ActionListener l) {
        actionListenerList.add(l);
    }

    public void removeActionListener(ActionListener l) {
        if (actionListenerList.contains(l))
            actionListenerList.remove(l);
    }

    private void processEvent(ActionEvent e) {
        for (ActionListener l : actionListenerList) {
            l.actionPerformed(e);
        }
    }

    public int getTotalNumberOfRolls() {
        return totalNumberOfRolls;
    }

    public int getNumberOfRolls(int i) {
        i--;

        if (i >= 0 && i <= 5) {
            return numberOfRolls[i];
        } else {
            return 0;
        }
    }

    private void addTotalNumberOfRolls() {
        totalNumberOfRolls++;
    }

    private void addNumberOfRolls() {
        numberOfRolls[value - 1]++;
    }

}
