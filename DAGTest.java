import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class DAGTest {

	@Test
	public void testAddEdge()
	{
		DAG dag = new DAG(3);
		assertTrue("Test add edge within boundaries of dag", dag.addEdge(0, 1));
		assertFalse("Test add edge outside boundaries of dag", dag.addEdge(-1, 2));
		assertFalse("Test add edge that creates self loop", dag.addEdge(1, 1));
		dag.addEdge(1, 2);
		assertFalse("Test add edge that creates directed cycle", dag.addEdge(2, 0));
	}

	@Test
	public void testDistance()
	{
		DAG dag = new DAG(6);
		//costruct DAG
		/*
		 * 				0
		 * 			/		\
		 * 		  1		      2
		 * 		/			/	
		 * 		3			4
		 * 	   /
		 * 	   5
		 */
		dag.addEdge(0, 1); dag.addEdge(0, 2); dag.addEdge(1, 3); dag.addEdge(2, 4); dag.addEdge(3, 5);
		assertEquals("Test root distance is 0", 0, dag.getDistance(0, 0));
		assertEquals("Test distance 1", 1, dag.getDistance(0, 1));
		assertEquals("Test distance 2", 2, dag.getDistance(0, 4));
		assertEquals("Test distance within dag", 2, dag.getDistance(1, 5));
	}
	
	@Test 
	public void testDFS()
	{
		
	}
	
	@Test
	public void testHasPath()
	{
		
	}
	
	@Test
	public void testLCAs()
	{
		DAG dag = new DAG(6);
		/*
		 * 			 0
		 * 		  /		\
		 * 		1		  2
		 * 	  /	 \	     /  
		 *   5	  \     3
		 *   	   \   /
		 *   		 4
		 */
		//construct DAG
		dag.addEdge(0,1);dag.addEdge(0,2);dag.addEdge(1,5);dag.addEdge(1,4);dag.addEdge(2,3);dag.addEdge(3, 4);
		assertEquals("Test where only one lca", (Integer) 0, dag.getLCA(1, 2).get(0));
		Integer result [] = {1, 2};
		assertArrayEquals("Test where multiple lcas", result, dag.getLCA(4, 5).toArray());
	}
}

