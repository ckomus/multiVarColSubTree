package algorithms;

import java.util.HashSet;
import java.util.Set;

import graphs.ColoredDiGraph;
import graphs.ColoredVertex;

public class Instance {
	private ColoredDiGraph d;
	private Set<ColoredVertex> partialSolution;
	
	
	public ColoredDiGraph getD() {
		return d;
	}
	public void setD(ColoredDiGraph d) {
		this.d = d;
	}
	
	public void fixVertex(ColoredVertex v){
		partialSolution.add(v);
	}
	
	public Set<Integer> getFixedColors(){
		HashSet<Integer> colors =  new HashSet<>();
		for (ColoredVertex v: partialSolution) {
			colors.add(v.getColor());
		} 
		return colors;
	}
	
	public Set<ColoredVertex> getVertices(){
		return d.getVertices();
	}
	
	public Set<ColoredVertex> getFixedVertices(){
		return this.partialSolution;
	}
	
	public Set<ColoredVertex> getNonFixedVertices(){
		HashSet<ColoredVertex> nonFixed = new HashSet<>(); 
			nonFixed.addAll(this.getVertices());
			nonFixed.removeAll(this.getFixedVertices());
			return nonFixed;
	}	
}
