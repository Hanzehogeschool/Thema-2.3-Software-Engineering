import tree.Tree;
import tree.TreeOrders;
import utils.ANSI;

public class TreeTraversal {

    private static final Integer TREE_SIZE = 20;
    private static final Integer TREE_ROOT_VALUE = 20;
    private static final Integer TREE_MAX_VALUE = 80;

    public TreeTraversal() {
        System.out.println(ANSI.ANSI_RED + "Tree Traversal\n");

        System.out.println(ANSI.ANSI_YELLOW + "Initializing tree...");
        Tree tree = new Tree(TREE_SIZE, TREE_ROOT_VALUE, TREE_MAX_VALUE);
        tree.generateTree();
        System.out.println(tree.toString());

        System.out.println(ANSI.ANSI_YELLOW + "Initializing tree orders...");
        TreeOrders treeOrders = new TreeOrders(tree.getTreeNode());

        System.out.print(ANSI.ANSI_MAGENTA + "In-order: ");
        treeOrders.inOrder();
        System.out.println(treeOrders.toString());

        System.out.print(ANSI.ANSI_CYAN + "Pre-order: ");
        treeOrders.preOrder();
        System.out.println(treeOrders.toString());

        System.out.print(ANSI.ANSI_WHITE + "Post-order: ");
        treeOrders.postOrder();
        System.out.println(treeOrders.toString());
    }

    public static void main(String[] args) {
        new TreeTraversal();
    }

}
