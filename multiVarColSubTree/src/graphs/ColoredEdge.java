package graphs;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ColoredEdge {
	private ColoredVertex tail;
	private ColoredVertex head;

	public ColoredEdge(ColoredVertex tail, ColoredVertex head){
		this.tail=tail;
		this.head=head;
	}
	
	public ColoredVertex getTail() {
		return tail;
	}
	
	public ColoredVertex getHead() {
		return head;
	}
	
	public static Set<ColoredVertex> getEndpoints(Collection<ColoredEdge> edges){
		HashSet<ColoredVertex> endpoints = new HashSet<ColoredVertex>();
		for (ColoredEdge e : edges) {
			endpoints.add(e.getTail());	
			endpoints.add(e.getHead());	
		}
		return endpoints;
	}	
}
