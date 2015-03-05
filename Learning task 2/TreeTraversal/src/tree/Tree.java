package tree;

import utils.ANSI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing a tree.
 *
 * @author Nils Berlijn
 * @author Tom Broeninkg
 * @version 1.0
 */
public class Tree {

    /**
     * The size of the tree.
     */
    private Integer treeSize;

    /**
     * The root value of the tree.
     */
    private Integer treeRootValue;

    /**
     * The max value of the tree.
     */
    private Integer treeMaxValue;

    /**
     * The tree node.
     */
    private TreeNode treeNode;

    /**
     * The tree values.
     */
    private List<Integer> treeValues;

    /**
     * The string.
     */
    private String string;

    /**
     * Tree constructor.
     * Creates a new tree.
     *
     * @param treeSize      The size of the tree.
     * @param treeRootValue The root value of the tree.
     * @param treeMaxValue  The max value of the tree.
     */
    public Tree(Integer treeSize, Integer treeRootValue, Integer treeMaxValue) {
        this.treeSize = treeSize;
        this.treeRootValue = treeRootValue;
        this.treeMaxValue = treeMaxValue;
        this.treeNode = new TreeNode(0);
        this.treeValues = new ArrayList<Integer>();
        this.string = "";
    }

    /**
     * Generates the tree.
     */
    public void generateTree() {
        treeNode = new TreeNode(treeRootValue);
        string += ANSI.ANSI_MAGENTA + "Added a new root (" + treeNode.getValue() + ")\n";
        treeValues = generateTreeValues(new ArrayList<Integer>(treeSize));
        generateTreeNodes();
    }

    /**
     * Generates the tree values.
     *
     * @param treeValues The tree values.
     * @return The tree values.
     */
    private List<Integer> generateTreeValues(List<Integer> treeValues) {
        Random random = new Random();

        for (int i = 1; i < treeSize; i++) {
            treeValues.add(random.nextInt(treeMaxValue) + treeRootValue);
        }

        return treeValues;
    }

    /**
     * Generates the tree nodes.
     */
    private void generateTreeNodes() {
        for (Integer treeValue : treeValues) {
            addTreeNode(treeNode, treeValue);
        }
    }

    /**
     * Adds a new tree node.
     *
     * @param treeNode The tree node.
     * @param value    The value of the tree node.
     */
    private void addTreeNode(TreeNode treeNode, Integer value) {
        if (value < treeNode.getValue()) {
            if (treeNode.getLeft() != null) {
                addTreeNode(treeNode.getLeft(), value);
            } else {
                treeNode.setLeft(new TreeNode(value));
                string += ANSI.ANSI_CYAN + "Added a new left tree node (" + value + ") to a existing tree node (" + treeNode.getValue() + ")\n";
            }
        } else {
            if (treeNode.getRight() != null) {
                addTreeNode(treeNode.getRight(), value);
            } else {
                treeNode.setRight(new TreeNode(value));
                string += ANSI.ANSI_WHITE + "Added a new right tree node (" + value + ") to a existing tree node (" + treeNode.getValue() + ")\n";
            }
        }
    }

    /**
     * Gets the tree node.
     *
     * @return The tree node.
     */
    public TreeNode getTreeNode() {
        return treeNode;
    }

    /**
     * The string representation of the tree.
     *
     * @return The string.
     */
    @Override
    public String toString() {
        return string;
    }

}
