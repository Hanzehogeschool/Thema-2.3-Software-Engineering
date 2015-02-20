/*
 * (C) Copyright 2005 Davide Brugali, Marco Torchiano
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307  USA
 */
package classifier;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A node of a decision tree representing a feature. 
 * Note that Arc is not implemented as a separate class as shown 
 * in the book on figures 4.4 and 4.6. It is implemented in a simpler way (see  below).
 * 
 */
public class Node {
	// The label of this node.
	// For internal nodes this label describes the feature. (eg "Airco")
	// For external nodes (=leaves at the end of the tree)
	// this label describes a category (eg "High").
	private String label;

	// This map contains the children of this node.
	// The key in the map ("yes" or "no" for example) describes a value of 
	// the current feature . For example:
	// node "Airco" --"yes"--> node "High"
	//              --"no"---> node "Low"
	private Map<String, Node> arcs=new HashMap<String, Node>();

	/**
	 * @param label For internal nodes this label describes the feature.
	 *              For external nodes (=leaves at the end of the tree) 
	 *              this label describes a category.
	 */
	public Node(String label) {
		this.label = label;
	}

	public void addChild(String arcLabel, Node child){
		arcs.put(arcLabel,child);  
	}

	public boolean isLeaf() { return arcs.size()==0; }

	public String getLabel() { return label; }

	public Node follow(String arcLabel) {
		return (Node)arcs.get(arcLabel);
	}

	/**
	 * @return A string representation of the tree with this node at the root.
	 *         For example:
	 *         [root
	 *             (to the left)--> [left]
	 *             (to the right)--> [right]
	 *         ]
	 */
	public String toString(){ return toString(""); }

	private String toString(String indent){
		String indentStep="    ";
		StringBuffer buffer=new StringBuffer();
		buffer.append("[").append(label);
		if(!isLeaf()){
			buffer.append("\n");
			for (Iterator<String> iter = arcs.keySet().iterator(); iter.hasNext();) {
				String arcLabel = (String) iter.next();
				Node dest = (Node)arcs.get(arcLabel);
				buffer.append(indent)
				.append("  (")
				.append(arcLabel)
				.append(")--> ")
				.append(dest.toString(indent+indentStep));
			}
			buffer.append(indent);
		}
		buffer.append("]\n");
		return buffer.toString();
	}
}
