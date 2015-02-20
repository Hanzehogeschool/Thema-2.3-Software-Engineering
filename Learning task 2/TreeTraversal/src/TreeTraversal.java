import tree.Tree;
import tree.TreeOrders;
import utils.ANSI;

/**
 * Class representing a tree traversal.
 *
 * @author Nils Berlijn
 * @author Tom Broeninkg
 * @version 1.0
 */
public class TreeTraversal {

    /**
     * The size of the tree.
     */
    private static final Integer TREE_SIZE = 20;

    /**
     * The root value of the tree.
     */
    private static final Integer TREE_ROOT_VALUE = 20;

    /**
     * The max value of the tree.
     */
    private static final Integer TREE_MAX_VALUE = 80;

    /**
     * A constructor.
     * Creates a new tree traversal.
     *
     * @param treeSize      The size of the tree.
     * @param treeRootValue The root value of the tree.
     * @param treeMaxValue  The max value of the tree.
     */
    public TreeTraversal(Integer treeSize, Integer treeRootValue, Integer treeMaxValue) {
        System.out.println(ANSI.ANSI_BLUE + "Tree Traversal\n");

        System.out.println(ANSI.ANSI_YELLOW + "Initializing the tree...");
        Tree tree = new Tree(treeSize, treeRootValue, treeMaxValue);
        tree.generateTree();
        System.out.println(tree.toString());

        System.out.println(ANSI.ANSI_YELLOW + "Initializing the tree orders...");
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

    /**
     * The main method.
     * Creates a new tree traversal with or without arguments.
     *
     * @param args The arguments for the tee traversal.
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                new TreeTraversal(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            } catch (NumberFormatException numberFormatException) {
                System.err.println("Argument " + args[0] + " must be an integer.");
                System.exit(1);
            }
        } else {
            new TreeTraversal(TREE_SIZE, TREE_ROOT_VALUE, TREE_MAX_VALUE);
        }
    }

}
