package graphs;

import java.util.HashMap;

public class EdgeWeights {
	private HashMap<ColoredVertex, HashMap<ColoredVertex,Double>> weights;
	
	public EdgeWeights(){
		weights =  new HashMap<ColoredVertex, HashMap<ColoredVertex,Double>>();
	}
	
	public void setEdgeWeight(ColoredVertex tail, ColoredVertex head, Double weight){
		if (weights.containsKey(tail)) {
			weights.get(tail).put(head,weight);
		}
		else {
			HashMap<ColoredVertex,Double> wl = new HashMap<ColoredVertex,Double>();
			wl.put(head, weight);
			weights.put(tail, wl);
		}
	}
	
	public void removeEdgeWeight(ColoredVertex tail, ColoredVertex head){
		weights.get(tail).remove(head);
	}
	
	public Double getWeight(ColoredVertex tail, ColoredVertex head){
		return weights.get(tail).get(head);
	}
}