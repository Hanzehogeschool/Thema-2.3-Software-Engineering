package tree;

public class TreeNode {

    private Integer value;
    private TreeNode left;
    private TreeNode right;
    private Boolean secondPop;

    public TreeNode(Integer value){
        setValue(value);
        setLeft(null);
        setRight(null);
        setSecondPop(false);
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public Boolean getSecondPop() {
        return secondPop;
    }

    public void setSecondPop(Boolean secondPop) {
        this.secondPop = secondPop;
    }

}
