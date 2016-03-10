package graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ColoredDiGraph {
	private HashSet<ColoredVertex> vertices;
	private EdgeWeights weights;
	private HashMap<ColoredVertex,List<ColoredVertex>> successors;
	private HashMap<ColoredVertex,List<ColoredVertex>> predecessors;

	public ColoredDiGraph(){
		vertices = new HashSet<ColoredVertex>();
		weights = new EdgeWeights();
		successors = new HashMap<>();
		predecessors = new HashMap<>();
	}
	
	public ColoredDiGraph(HashSet<ColoredVertex> vertexSet, Set<ColoredEdge> edgeSet, EdgeWeights edgeWeights) {
		this();
		
		for (ColoredVertex v : vertexSet) {
			this.vertices.add(v);
		}
		for (ColoredEdge e : edgeSet) {
			this.addEdge(e.getTail(), e.getHead(), edgeWeights.getWeight(e.getTail(), e.getHead()));
		}
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
	
	private void addVertexSet(Set<ColoredVertex> vertexSet) {
		for (ColoredVertex v : vertexSet) {
			this.addVertex(v);
		} 
	}
	
	public void addEdge(ColoredVertex tail, ColoredVertex head, Double weight){
		successors.get(tail).add(head);
		predecessors.get(head).add(tail);
		weights.setEdgeWeight(tail, head, weight);
	}
	
	public void removeVertex(ColoredVertex v){
		if (vertices.remove(v)){
			if (successors.containsKey(v)){
				List<ColoredVertex> outv = successors.get(v);
				successors.remove(v);
				for (ColoredVertex u : outv) {
					predecessors.get(u).remove(v);
				}
			}
			if (predecessors.containsKey(v)){
				List<ColoredVertex> inv = predecessors.get(v);
				predecessors.remove(v);
				for (ColoredVertex u : inv) {
					successors.get(u).remove(v);
				}
			}
		}
	}

	public Set<ColoredVertex> getSources(){
		Set<ColoredVertex> sources = new HashSet<ColoredVertex>();
		for (ColoredVertex v : vertices) {
			if (predecessors.get(v).isEmpty()){
				sources.add(v);
			}
		}
		return sources;
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

	public Double edgeWeightSum(){
		Double sum = 0.0;
		for (ColoredVertex v : vertices) {
			for (ColoredVertex u : successors.get(v)){
				sum += weights.getWeight(v,u);
			}
		}
		return sum;	
	}

	
	public HashSet<Integer> colors(){
		HashSet<Integer> colors = new HashSet<>();
		for (ColoredVertex v : vertices) {
			colors.add(v.getColor());
		}
		return colors;	
	}
	
	public int colNum(){
		return colors().size();
	}


	//getters and setters
	
	public HashSet<ColoredVertex> getVertices() {
		return vertices;
	}
	
	public EdgeWeights getWeights() {
		return weights;
	}

	//returns the incoming edge of MaximumWeight for a vertex v
	public ColoredEdge getMaxInEdge(ColoredVertex v) {
		Double currentMax = Double.NEGATIVE_INFINITY;
		ColoredEdge currentMaxEdge = null;
		for (ColoredVertex u : predecessors.get(v)) {
			Double vuWeight = weights.getWeight(u, v);
			if (vuWeight>currentMax) {
				currentMax = vuWeight;
				currentMaxEdge = new ColoredEdge(u, v);
			}
		}
		return currentMaxEdge;
	}
	
	public ColoredDiGraph contractCycle(LinkedList<ColoredEdge> cycle){
		if (cycle.isEmpty()) return null;
		ColoredDiGraph d = new ColoredDiGraph();
		Set<ColoredVertex> dVertices = new HashSet<ColoredVertex>(vertices);
		Set<ColoredVertex> cycleVertices = ColoredEdge.getEndpoints(cycle);
		dVertices.removeAll(cycleVertices);
		//pick one of the cycle vertices as replacement
		ColoredVertex c = cycleVertices.iterator().next();
		dVertices.add(c); 
		//add the vertices to d
		d.addVertexSet(dVertices);
		//add the edges not incident with c to d
		for (ColoredVertex v : dVertices) {
			if (v != c) {
				for (ColoredVertex u : successors.get(v)) {
					if (u!=c && dVertices.contains(u)) d.addEdge(v, u, weights.getWeight(v, u));
				} 
			} 
		}
		
		//add the edges incident with c to d
		
		for (ColoredVertex v : dVertices) {
			if (v != c) {
				Double cInWeight = Double.NEGATIVE_INFINITY;
				Double cOutWeight = Double.NEGATIVE_INFINITY;
				boolean inNeighbor = false;
				boolean outNeighbor = false;
				//compute in-weight if in-neighbor
				for (ColoredVertex u : successors.get(v)) {
					if (cycleVertices.contains(u)) {
						inNeighbor = true;
						Double uDiffWeight = weights.getWeight(v, u)-getInEdgeWeight(cycle, u);
						cInWeight = Math.max(cInWeight,uDiffWeight);
					}
				}
				//compute out-weight if out-neighbor
				for (ColoredVertex u : predecessors.get(v)) {
					if (cycleVertices.contains(u)) {
						outNeighbor = true;
						Double uDiffWeight = weights.getWeight(u, v);
						cOutWeight = Math.max(cInWeight,uDiffWeight);
					}
				}
				//add edges
				if (inNeighbor) {
					d.addEdge(v, c, cInWeight);
				}
				if (outNeighbor) {
					d.addEdge(c, v, cOutWeight);
				}
			} 
		}
		return d;
	}
	
	private Double getInEdgeWeight(Collection<ColoredEdge> edges, ColoredVertex v){
		for (ColoredEdge e : edges) {
			if (v==e.getHead()){
				return weights.getWeight(e.getTail(),v);
			}
		}
		return Double.NEGATIVE_INFINITY;
	}

	
}
