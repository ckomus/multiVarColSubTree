package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ColoredDiGraph {
	private HashSet<ColoredVertex> vertices;
	private EdgeWeights weights;
	
	private HashMap<ColoredVertex,List<ColoredVertex>> successors;
	private HashMap<ColoredVertex,List<ColoredVertex>> predecessors;

	public ColoredDiGraph(){
		vertices = new HashSet<ColoredVertex>();
		weights = new EdgeWeights();
	}
	
	public void addVertex(ColoredVertex v){
		vertices.add(v);
		if (!successors.containsKey(v)){
			successors.put(v, new ArrayList<ColoredVertex>());
		}
		if (!predecessors.containsKey(v)){
			predecessors.put(v, new ArrayList<ColoredVertex>());
		}
	}
	
	public void addEdge(ColoredVertex tail, ColoredVertex head, Double weight){
		successors.get(tail).add(head);
		predecessors.get(head).add(tail);
		weights.setEdgeWeight(tail, head, weight);
	}

	public int vertexNum(){
		return vertices.size();
	}
	
	public int edgeNum(){
		int m = 0;
		for (ColoredVertex v : vertices) {
			m += successors.get(v).size();
		}
		return m;	
	}
	
}
