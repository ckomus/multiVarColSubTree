package algorithms;

import java.util.Set;

import graphs.ColoredVertex;

public class ReductionRules {

	public enum Rules {FixedColorRule};

	Rules[] ruleSet;
	
	public ReductionRules(){
		this.ruleSet = Rules.values();
	}
	
	public void removeFixedColors(Instance i){
		Set<Integer> colors = i.getFixedColors();
		for (ColoredVertex v: i.getNonFixedVertices()) {
			if (colors.contains(v.getColor())) {
				i.getD().removeVertex(v);
			} 
		} 
	}
	
	public void reduce(Instance i){
		for (Rules rule : ruleSet) {
			switch (rule) {
			case FixedColorRule: removeFixedColors(i); break;
			default: break;
			}
		} 
	}
	
}
