import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class DAG 
{
	private final int V; //number of vertices
	private final ArrayList<Integer>[] adj;
	private final ArrayList<Integer>[] reverseAdj;

	//create an empty DAG with V vertices
	public DAG(int V)
	{
		this.V = V;
		adj = (ArrayList<Integer>[]) new ArrayList[V];
		reverseAdj = (ArrayList<Integer>[]) new ArrayList[V];
		for (int v = 0; v < V; v++)
		{
			adj[v] = new ArrayList<Integer>();
			reverseAdj[v] = new ArrayList<Integer>();
		}
	}

	//return number of vertices
	public int V()
	{ return V;}

	//return list of vertices pointing from v
	public ArrayList<Integer> adj(int v)
	{ return adj[v]; }

	//return reversed list of vertices pointing from v
	public ArrayList<Integer> reverseAdj(int v)
	{ return reverseAdj[v]; }
	
	//void addEdge(int v, int w) add a directed edge v->w 
	public boolean addEdge(int v, int w)
	{
		// check that new edge is within dag boundaries
		if(v >= this.V || w >= this.V || v < 0 || w < 0)
		{
			return false;
		}
		// check that new edge doesn't create self loop/directed cycle
		if(v != w && !hasPath(w, v) && !adj[v].contains(w))
		{
			adj[v].add(w);
			reverseAdj[w].add(v);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//returns true if has path, else false
	public boolean hasPath(int x, int y)
	{
		DFS dfs = new DFS(this, x);
		return dfs.visited(y);
	}

	//returns list of lcas of two vertices x and y
	public ArrayList<Integer> getLCA(int x, int y)
	{
		ArrayList<Integer> lcas = new ArrayList<Integer>();
		int max = Integer.MAX_VALUE;
		//return null if invalid
		if(x == y || x >= this.V || y >= this.V || x < 0 || y < 0) 
		{ 
			return null; 
		} 
		DFS dfs= new DFS(this, x);
		dfs.reverseDFS(this, x);
		int xDist, yDist;
	
		for(int v = 0; v < this.V; v++)
		{
			if(dfs.revVisited(v) && hasPath(v, y))
			{
				xDist = getDistance(v, x);
				yDist = getDistance(v, y);
				if(Integer.max(xDist, yDist) < max)
				{		
					lcas = new ArrayList<Integer>();
					lcas.add(v);
					max = Integer.max(xDist, yDist);
				}
				else if(Integer.max(xDist, yDist) == max)
				{
					lcas.add(v);
					max = Integer.max(xDist, yDist);
				}
			}
		}
		return lcas;
	}

	public int getDistance(int x, int target)
	{
		if( x == target) 
		{ 
			return 0; 
		}
		else 
		{
	        Queue<Integer> q = new LinkedList<Integer>();
	        int[] distTo = new int[this.V];
	        boolean[] marked = new boolean[this.V];
	        for (int v = 0; v < this.V(); v++)
	        {   
	        	distTo[v] = Integer.MAX_VALUE;
	        }
	        distTo[x] = 0;
	        marked[x] = true;
	        q.add(x);
	        while (!q.isEmpty()) 
	        {
	            int v = q.remove();
	            for (int w : this.adj(v))
	            {
	                if (!marked[w])
	             	{
	                	distTo[w] = distTo[v] + 1;
	                    marked[w] = true;
	                    q.add(w);
	                }
	            }
	        }
	        return distTo[target];
		}
	}

	//Class to create a DFS object on a DAG
	private class DFS
	{
		private boolean[] marked;
		private boolean[] revMarked;
		public DFS(DAG G, int s)
		{
			marked = new boolean[G.V()];
			revMarked = new boolean[G.V()];
			DFS(G, s);
		}
		//standard dfs - in flow of direction
		private void DFS(DAG G, int v)
		{
			marked[v] = true;
			for (int w : G.adj(v))
				if (!marked[w]) 
					DFS(G, w);
		}
		//dfs against flow of direction - to find all parents
		private void reverseDFS(DAG G, int v)
		{
			revMarked[v] = true;
			for (int w : G.reverseAdj(v))
				if (!revMarked[w]) 
					reverseDFS(G, w);
		}
		public boolean visited(int v)
		{ return marked[v]; }
		public boolean revVisited(int v)
		{ return revMarked[v]; }
	}
}