import utils.ANSI;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Enumeration;

/**
 * Class representing a tree builder.
 *
 * @author Nils Berlijn
 * @author Tom Broeninkg
 * @version 1.0
 */
public class TreeBuilder {

    /**
     * The person.
     */
    DefaultMutableTreeNode person;

    /**
     * The employee.
     */
    DefaultMutableTreeNode employee;

    /**
     * The customer.
     */
    DefaultMutableTreeNode customer;

    /**
     * The us customer.
     */
    DefaultMutableTreeNode usCustomer;

    /**
     * A constructor.
     * Creates a new tree builder.
     */
    public TreeBuilder() {
        System.out.println(ANSI.ANSI_BLUE + "Tree Builder\n");

        System.out.println(ANSI.ANSI_YELLOW + "Initializing the tree...\n");
        employee = new DefaultMutableTreeNode("employee");
        employee.add(new DefaultMutableTreeNode("sales_rep"));
        employee.add(new DefaultMutableTreeNode("engineer"));

        usCustomer = new DefaultMutableTreeNode("us_customer");
        usCustomer.add(new DefaultMutableTreeNode("local_customers"));
        usCustomer.add(new DefaultMutableTreeNode("regional_customers"));

        customer = new DefaultMutableTreeNode("customer");
        customer.add(usCustomer);
        customer.add(new DefaultMutableTreeNode("non_us_customer"));

        person = new DefaultMutableTreeNode("person");
        person.add(employee);
        person.add(customer);

        System.out.println(ANSI.ANSI_YELLOW + "Initializing the tree orders...");
        System.out.println(ANSI.ANSI_MAGENTA + "Breadth-order: " + order(person.breadthFirstEnumeration()));
        System.out.println(ANSI.ANSI_CYAN + "Pre-order: " + order(person.preorderEnumeration()));
        System.out.println(ANSI.ANSI_WHITE + "Post-order: " + order(person.postorderEnumeration()));
    }

    /**
     * The main method.
     * Creates a new tree builder with or without arguments.
     *
     * @param args The arguments for the tee builder.
     */
    public static void main(String[] args) {
        new TreeBuilder();
    }

    /**
     * Order the tree.
     *
     * @param enumeration The enumeration.
     * @return An ordered tree.
     */
    private String order(Enumeration<?> enumeration) {
        String order = "";

        while(enumeration.hasMoreElements()) {
            order += enumeration.nextElement() + " ";
        }

        return order;
    }

}
