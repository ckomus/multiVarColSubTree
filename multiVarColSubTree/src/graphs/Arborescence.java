package graphs;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Arborescence {
	public ColoredDiGraph computeOptimalArborescence(ColoredDiGraph d){
		//initialize current best solutions
		ColoredDiGraph arb = null;
		Double currentMax = Double.NEGATIVE_INFINITY;
		//initialize set of arborescence roots to try
		Set<ColoredVertex> roots = d.getSources();
		if (roots.isEmpty()) {
			roots = d.getVertices();
		}
		//try each possible root
		for (ColoredVertex root : roots) {
			ColoredDiGraph rootedArb = computeOptimalArborescence(root,d);
			Double rootedArbWeights = rootedArb.edgeWeightSum();
			if (rootedArbWeights> currentMax){
				arb = rootedArb;
				currentMax = rootedArbWeights;
			}
		}
		return arb;
	}
	
	private ColoredDiGraph computeOptimalArborescence(ColoredVertex root, ColoredDiGraph d) {
		Set<ColoredEdge> maxEdges = new HashSet<ColoredEdge>();
		for (ColoredVertex v : d.getVertices()) {
			maxEdges.add(d.getMaxInEdge(v));
		}
		if (!hasCycle(maxEdges)){
			return new ColoredDiGraph(d.getVertices(),maxEdges,d.getWeights());
		}
		else {
			
			return null;
		}
	}

	private HashSet<ColoredEdge> findCycle(Set<ColoredEdge> edges){
		HashSet<ColoredEdge> edgeCopy = new HashSet<ColoredEdge>(edges);
		boolean reduced = true;
		while (reduced){
			reduced = false;
			for (ColoredEdge e : edgeCopy) {
				edgeCopy.remove(e);
				if (hasCycle(edgeCopy)){
					reduced = true;
				}
				else edgeCopy.add(e);
			}
		}
		return edgeCopy;
	}
	
	private boolean hasCycle(Set<ColoredEdge> edges){
		if (edges.isEmpty()) return false;
		//get the end points and compare vertex number to edge number
		Set<ColoredVertex> endpoints = ColoredEdge.getEndpoints(edges);
		if (edges.size()>=endpoints.size()) return true;
		//pick arbitrary vertex and get the edges of its weakly connected component in graph induced by edges
		ColoredVertex v = endpoints.iterator().next();
		Set<ColoredEdge> reachable = getReachableEdges(v, edges);
		//recurse on this set of edges and the complement
		if (hasCycle(reachable)) return true;
		Set<ColoredEdge> nonreachable = new HashSet<ColoredEdge>(edges);
		nonreachable.removeAll(reachable);		
		if (hasCycle(nonreachable)) return true;
		//both cases don't give a cycle
		return false;
	}

		
	
	
	

	
	private Set<ColoredEdge> getReachableEdges(ColoredVertex v, Collection<ColoredEdge> edges){
		//initialize solution
		HashSet<ColoredEdge> reachable =  new HashSet<ColoredEdge>();
		//queue for DFS
		LinkedList<ColoredVertex> queue = new LinkedList<ColoredVertex>();
		queue.add(v);
		//use set visited to check that every vertex is considered only once
		Set<ColoredVertex> visited = new HashSet<ColoredVertex>();
		visited.add(v);
		//expand from head of queue
		while (!queue.isEmpty()){
			ColoredVertex s = queue.remove();
			for (ColoredEdge e : edges) {
				if (e.getTail()==s){
					reachable.add(e);
					ColoredVertex t = e.getHead();
					if (!visited.contains(t)){
						queue.add(t);
						visited.add(t);
					}
				}
				else if (e.getHead()==s){
					reachable.add(e);
					ColoredVertex t = e.getTail();
					if (!visited.contains(t)){
						queue.add(t);
						visited.add(t);
					}
				}
			}
		}
		return reachable;
	}
}
	
	
	
	

