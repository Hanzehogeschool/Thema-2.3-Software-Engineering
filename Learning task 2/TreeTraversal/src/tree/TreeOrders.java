package tree;

import java.util.Stack;

public class TreeOrders {

    private TreeNode root;
    private Stack<TreeNode> stack;
    private String string;

    public TreeOrders(TreeNode treeNode) {
        this.root = treeNode;
        this.stack = new Stack<TreeNode>();
        this.string = "";
    }

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

    @Override
    public String toString() {
        return string.substring(0, string.length() - 2);
    }

}
