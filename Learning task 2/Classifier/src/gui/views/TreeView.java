package gui.views;

import gui.models.ClassifierModel;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class TreeView extends JPanel {

    ClassifierModel classifierModel;

    public TreeView(ClassifierModel classifierModel) {
        this.classifierModel = classifierModel;
    }

    public void tree() {
        String tree = classifierModel.getDecisionTree().toString();
        Scanner scanner = new Scanner(tree);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            JTextField treeTextField = new JTextField(line);
            treeTextField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            treeTextField.setHorizontalAlignment(SwingConstants.CENTER);
            treeTextField.setEditable(false);
            treeTextField.setBackground(Color.WHITE);

            add(treeTextField);
        }

        scanner.close();
    }

}
