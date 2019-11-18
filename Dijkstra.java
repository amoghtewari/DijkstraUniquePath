//DO NOT CHANGE ANY EXISTING CODE IN THIS FILE
//DO NOT CHANGE THE NAMES OF ANY EXISTING FUNCTIONS
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.util.*;

class Graph {
	public ArrayList<Vertice> vertices;
	public Graph(int numVertices){
		vertices = new ArrayList<Vertice>(numVertices);
		for(int i=1;i<numVertices+1;i++){ // indexes are 1 less than vertice value.
			vertices.add(new Vertice(i));
		}
	}
	
	public void addEdge(int src, int dest, int weight){
		Vertice s = vertices.get(src-1);// indexes are 1 less than vertice value.
		Vertice d = vertices.get(dest-1);// indexes are 1 less than vertice value.
		
		//add edges with weight into attachedTO list of of each vertice. 
		Edge new_edge1 = new Edge(d,weight);
		s.attachedTO.add(new_edge1);
		
		Edge new_edge2 = new Edge(s,weight);
		d.attachedTO.add(new_edge2);
	}
	
	public ArrayList<Vertice> getVertices() {
		return vertices;
	}
	
	public Vertice getVertice(int vert){
		return vertices.get(vert-1);
	}
}

class Edge{
	public final Vertice goingTO; // ending point of the edge
	public final double weight; // weight of wedge
	public Edge(Vertice goingTO, double weight){
		this.goingTO = goingTO;
		this.weight = weight;
	}
}


class Vertice implements Comparable<Vertice> {
	public int v; // integer vertice values
	public double d; // d[Vertice]
	public int usp; // Whether a unique shortest path exist from start to Vertice, 
					// 1=True, 0= False
	public ArrayList<Edge> attachedTO; // list of Edges that this Vertice is attachedTo.
	

	Vertice(int v) {
		this.v = v;
		this.d = Double.MAX_VALUE; // initially distnace to each vertice from start is infinite.
		this.usp = 0; // initially there are no unique shortest paths between start and vertice.
		attachedTO = new ArrayList<Edge>();
	}

	@Override // heap is maintained on the d values, which allows minheap to give the vertice with least d.
	public int compareTo(Vertice other){
		return Double.compare(d,other.d);		
  	}
}

public class Dijkstra {
	
	    public static int [][] Dijkstra_alg( int n, int e, int mat[][], int s)
	    {
	    	 //Write your code here to calculate shortest paths and usp values from source to all vertices
			 //This method will have four inputs (Please see testcase file)
			 //This method should return a two dimensional array as output (Please see testcase file)
			
			// Add edges from matrice to graph.
			Graph g = new Graph(n);
			for(int i=0; i<e; i++){
				g.addEdge(mat[i][0], mat[i][1], mat[i][2]);
			}
			
			// Set of vertices that have d assigned
			Set<Integer> S = new HashSet<Integer>();
			
			Vertice source = g.getVertice(s);
			S.add(source.v);
			
			//starting values of d and usp for source.
			source.d = 0;
			source.usp = 1;
			
			
			PriorityQueue<Vertice> queue = new PriorityQueue<Vertice>();
			
			queue.add(source);
			
			while(!queue.isEmpty()){
				Vertice u = queue.poll();
				//S.add(u.v);
				for(Edge e1:u.attachedTO){
					System.out.println(S + " " + e1.goingTO.v);
					if(!S.contains(e1.goingTO.v)){
						Double newD = u.d + e1.weight;
						//if distance is updated(improved), usp = 1
						if(e1.goingTO.d > newD){
							// update its d and usp.
							queue.remove(e1.goingTO);
							e1.goingTO.d = newD;
							S.add(u.v); // add u to S
							e1.goingTO.usp = 1;
							queue.add(e1.goingTO);	
						//if distance is not updated(improved), yet we find the equal distance again, usp = 0
						} else if (e1.goingTO.d == newD){
							// update its usp
							queue.remove(e1.goingTO);
							e1.goingTO.usp = 0;
							queue.add(e1.goingTO);
							}
					}					
				}
			}
			
			
			// result is n*2 matrice, assign values from the vertices and return result
			int[][] result = new int[n][2];
			
			ArrayList<Vertice> v = new ArrayList<Vertice>(); 
			v = g.getVertices();
			
			for(int i=0; i<n; i++){
				result[i][0] = (int) v.get(i).d;
				result[i][1] = v.get(i).usp;
			}
			
			return result;
		}
			
			
	
	
	
		public static void main(String []args) {
	
			Result result = JUnitCore.runClasses(DijkstraTest.class);
			if(result.wasSuccessful())
				System.out.println("All test cases passed");
	
			for (Failure failure : result.getFailures()) {
         		System.out.println(failure.toString());
      		}
	}
}
