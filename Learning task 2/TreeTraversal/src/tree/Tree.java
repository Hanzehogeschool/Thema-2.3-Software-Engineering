package tree;

import utils.ANSI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tree {

    private Integer treeSize;
    private Integer treeRootValue;
    private Integer treeMaxValue;
    private TreeNode treeNode;
    private List<Integer> treeValues;
    private String string;

    public Tree(Integer treeSize, Integer treeRootValue, Integer treeMaxValue) {
        this.treeSize = treeSize;
        this.treeRootValue = treeRootValue;
        this.treeMaxValue = treeMaxValue;
        this.treeNode = new TreeNode(0);
        this.treeValues = new ArrayList<Integer>();
        this.string = "";
    }

    public void generateTree() {
        treeNode = new TreeNode(treeRootValue);
        treeValues = generateTreeValues(new ArrayList<Integer>(treeSize));
        generateTreeNodes();
    }

    private List<Integer> generateTreeValues(List<Integer> treeValues) {
        Random random = new Random();

        for (int i = 1; i < treeSize; i++) {
            treeValues.add(random.nextInt(treeMaxValue) + treeRootValue);
        }

        return treeValues;
    }

    private void generateTreeNodes() {
        for (Integer treeValue : treeValues) {
            addTreeNode(treeNode, treeValue);
        }
    }

    private void addTreeNode(TreeNode treeNode, Integer value) {
        if (value < treeNode.getValue()) {
            if (treeNode.getLeft() != null) {
                addTreeNode(treeNode.getLeft(), value);
            } else {
                treeNode.setLeft(new TreeNode(value));
                string += ANSI.ANSI_GREEN + "Added a new tree node (" + value + ") to a left tree node (" + treeNode.getValue() + ")\n";
            }
        } else {
            if (treeNode.getRight() != null) {
                addTreeNode(treeNode.getRight(), value);
            } else {
                treeNode.setRight(new TreeNode(value));
                string += ANSI.ANSI_BLUE + "Added a new tree node (" + value + ") to a right tree node (" + treeNode.getValue() + ")\n";
            }
        }
    }

    public TreeNode getTreeNode() {
        return treeNode;
    }

    @Override
    public String toString() {
        return string;
    }

}
