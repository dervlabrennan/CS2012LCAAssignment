import static org.junit.Assert.*;

import org.junit.Test;

public class LCATest 
{
	/**
	 * 				 1
	 * 			   /   \
	 * 		     2		 3
	 * 		   /  \     /  \
	 * 		  4    5   6    7
	 */
	@Test
	public void test() 
	{
		LCA.BT tree = new LCA.BT();
		
		//test on empty tree
		assertNull(tree.findLCA(0, 0));
		
		tree.root = new LCA.Node(-1);
		tree.root.left = new LCA.Node(2);
		
		//test on tree consisting of only 2 nodes
		assertEquals(tree.findLCA(-1, 2), Integer.valueOf(-1));
		
		tree.root.right = new LCA.Node(3);
		tree.root.left.left = new LCA.Node(4);
		tree.root.left.right = new LCA.Node(5);
		tree.root.right.left = new LCA.Node(6);
		tree.root.right.right = new LCA.Node(7);
		
		//test on tree of multiple nodes
		assertEquals(tree.findLCA(4, 5), (Integer) 2);
		assertEquals(tree.findLCA(6, 7), (Integer) 3);
		assertEquals(tree.findLCA(2, 7), Integer.valueOf(-1));
		
		//test where lca is one of the nodes itself
		assertEquals(tree.findLCA(2, 4), (Integer) 2);
		//test where one node is not in tree
		assertNull(tree.findLCA(50, 2));
		//test where neither node is in tree
		assertNull(tree.findLCA(50, 100));
	}
	

}
