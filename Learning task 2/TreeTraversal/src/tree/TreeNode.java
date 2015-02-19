package tree;

/**
 * Class representing a tree node.
 *
 * @author Nils Berlijn
 * @author Tom Broeninkg
 * @version 1.0
 */
public class TreeNode {

    /**
     * The value of the tree node.
     */
    private Integer value;

    /**
     * The left tree node.
     */
    private TreeNode left;

    /**
     * The right tree node.
     */
    private TreeNode right;

    /**
     * The second pop of a tree node.
     */
    private Boolean secondPop;

    /**
     * A constructor.
     * Creates a new tree node.
     *
     * @param value The value of the tree node.
     */
    public TreeNode(Integer value){
        setValue(value);
        setLeft(null);
        setRight(null);
        setSecondPop(false);
    }

    /**
     * Get the value of the tree node.
     *
     * @return The value of the tree node.
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Set the value of the tree node.
     *
     * @param value The value of the tree node.
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     * Get the left tree node.
     *
     * @return The left tree node.
     */
    public TreeNode getLeft() {
        return left;
    }

    /**
     * Set The right tree node.
     *
     * @param left The left tree node.
     */
    public void setLeft(TreeNode left) {
        this.left = left;
    }

    /**
     * Get the right position of a tree node.
     *
     * @return The right position of a tree node.
     */
    public TreeNode getRight() {
        return right;
    }

    /**
     * Set the right position of a tree node.
     *
     * @param right The right position of a tree node.
     */
    public void setRight(TreeNode right) {
        this.right = right;
    }

    /**
     * Get the second pop of a tree node.
     *
     * @return The second pop of a tree node.
     */
    public Boolean getSecondPop() {
        return secondPop;
    }

    /**
     * Set the second pop of a tree node.
     *
     * @param secondPop The second pop of a tree node.
     */
    public void setSecondPop(Boolean secondPop) {
        this.secondPop = secondPop;
    }

}
