package tree;

import java.util.Stack;

/**
 * Class representing tree orders.
 *
 * @author Nils Berlijn
 * @author Tom Broeninkg
 * @version 1.0
 */
public class TreeOrders {

    /**
     * The root tree node.
     */
    private TreeNode root;

    /**
     * The stack.
     */
    private Stack<TreeNode> stack;

    /**
     * The string.
     */
    private String string;

    /**
     * A constructor.
     * Creates new tree orders.
     *
     * @param treeNode The tree node.
     */
    public TreeOrders(TreeNode treeNode) {
        this.root = treeNode;
        this.stack = new Stack<TreeNode>();
        this.string = "";
    }

    /**
     * Performs the in-order of the tree.
     */
    public void inOrder() {
        TreeNode current = root;
        string = "";

        while (current != null || !stack.empty()) {
            if (current != null) {
                stack.push(current);
                current = current.getLeft();
            } else {
                TreeNode treeNode = stack.pop();
                string += treeNode.getValue() + ", ";
                current = treeNode.getRight();
            }
        }
    }

    /**
     * Performs the pre-order of the tree.
     */
    public void preOrder() {
        TreeNode current = root;
        string = "";

        while (current != null || !stack.empty()) {
            if (current != null) {
                string += current.getValue() + ", ";

                if (current.getRight() != null) {
                    stack.push(current.getRight());
                }

                current = current.getLeft();
            } else {
                current = stack.pop();
            }
        }
    }

    /**
     * Performs the post-order of the tree.
     */
    public void postOrder() {
        TreeNode current = root;
        string = "";

        while (current != null || !stack.empty()) {
            if (current != null) {
                stack.push(current);
                current = current.getLeft();
            } else {
                current = stack.pop();

                if (current.getSecondPop()) {
                    string += current.getValue() + ", ";
                    current = null;
                } else {
                    current.setSecondPop(true);
                    stack.push(current);
                    current = current.getRight();
                }
            }
        }
    }

    /**
     * Returns a string representation the tree orders.
     *
     * @return The string.
     */
    @Override
    public String toString() {
        return string.substring(0, string.length() - 2);
    }

}
