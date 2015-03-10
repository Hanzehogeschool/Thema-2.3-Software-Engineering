package gui.views;

import classifier.Node;
import gui.models.ClassifierModel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Map;

/**
 * Class representing a tree view.
 *
 * @author Nils Berlijn
 * @author Tom Broenink
 * @version 1.0
 */
public class TreeView extends JPanel {

    /**
     * The classifier model.
     */
    ClassifierModel classifierModel;

    /**
     * The node label list.
     */
    private LinkedList<Label> nodeLabelList;

    /**
     * The arc label list.
     */
    private LinkedList<Label> arcLabelList;

    /**
     * The x.
     */
    private Integer x;

    /**
     * The y.
     */
    private Integer y;

    /**
     * The level.
     */
    private Integer level;

    /**
     * Classifier tree constructor.
     * Creates a new classifier tree.
     *
     * @param classifierModel The classifier model.
     */
    public TreeView(ClassifierModel classifierModel) {
        this.classifierModel = classifierModel;

        nodeLabelList = new LinkedList<Label>();
        arcLabelList = new LinkedList<Label>();
        x = 1024;
        y = 20;
        level = 20;

        generateTree(this.classifierModel.getDecisionTree().getRoot());
    }

    /**
     * Generates the tree.
     *
     * @param node The node.
     */
    private void generateTree(Node node) {
        Map<String, Node> arcs = node.getArcs();

        if (!node.isLeaf()) {
            Label nodeLabel = new Label(node.getLabel());
            nodeLabel.setLocation(x, y);
            nodeLabelList.add(nodeLabel);

            for (String arc : arcs.keySet()) {
                Label arcLabel = new Label(arc);
                arcLabelList.add(arcLabel);

                if (arc.equals("yes")) {
                    arcLabel.setLocation(x - 50, y + 15);
                    x -= 50;
                } else if (arc.equals("no")) {
                    arcLabel.setLocation(x + 25, y + 15);
                    x += 25;
                }

                y += 40;
                generateTree(arcs.get(arc));
            }
        } else {
            level += 20;
            x = 1024 + (x / 3);
            y = 20 + level;
        }
    }

    /**
     * Draws the labels.
     *
     * @param graphics  The graphics.
     * @param color     The color.
     * @param labelList The label list.
     */
    private void drawLabels(Graphics graphics, Color color, LinkedList<Label> labelList) {
        for (Label label : labelList) {
            graphics.setColor(color);
            graphics.fillRect(label.getX(), label.getY(), 10, 10);
            graphics.setColor(Color.BLACK);
            graphics.drawString(label.getText(), label.getX() + 15, label.getY() + 10);
        }
    }

    /**
     * Paints the component.
     *
     * @param graphics The graphics.
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        setPreferredSize(new Dimension(2048, 2048));

        drawLabels(graphics, Color.BLUE, nodeLabelList);
        drawLabels(graphics, Color.GREEN, arcLabelList);
    }

}
