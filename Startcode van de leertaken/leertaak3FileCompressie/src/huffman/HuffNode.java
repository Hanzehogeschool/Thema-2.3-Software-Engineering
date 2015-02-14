package huffman;

//Basic node in a Huffman coding tree.
public class HuffNode implements Comparable<HuffNode>
{
	public int value;
	public int weight;
	HuffNode left;
	HuffNode right;
	HuffNode parent;

	HuffNode( int v, int w, HuffNode lt, HuffNode rt, HuffNode pt )
	{
		value = v; 
		weight = w; 
		left = lt; 
		right = rt; 
		parent = pt;
	}
	public int compareTo(HuffNode rhs)
	{
		HuffNode other = rhs;
		return weight - other.weight;
	}
}