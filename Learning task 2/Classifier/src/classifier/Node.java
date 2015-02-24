package classifier;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Node {

    private String label;
    private Map<String, Node> arcs = new HashMap<String, Node>();

    public Node(String label) {
        this.label = label;
    }

    public void addChild(String arcLabel, Node child) {
        arcs.put(arcLabel, child);
    }

    public boolean isLeaf() {
        return arcs.size() == 0;
    }

    public Node follow(String arcLabel) {
        return (Node) arcs.get(arcLabel);
    }

    public String toString() {
        return toString("");
    }

    private String toString(String indent) {
        String indentStep = "    ";
        StringBuffer buffer = new StringBuffer();
        buffer.append("[").append(label);

        if (!isLeaf()) {
            buffer.append("\n");

            for (Iterator<String> iter = arcs.keySet().iterator(); iter.hasNext(); ) {
                String arcLabel = (String) iter.next();
                Node dest = (Node) arcs.get(arcLabel);
                buffer.append(indent)
                        .append("  (")
                        .append(arcLabel)
                        .append(")--> ")
                        .append(dest.toString(indent + indentStep));
            }
            buffer.append(indent);
        }

        buffer.append("]\n");

        return buffer.toString();
    }

    public String getLabel() {
        return label;
    }

    public Map<String, Node> getArcs() {
        return arcs;
    }

}
