package main;

import graphs.ColoredDiGraph;
import graphs.ColoredGraphFactory;

public class multiVarColSubTree {

	public static void main(String[] args) {
		String path = args[0];
		ColoredDiGraph d = ColoredGraphFactory.coloredDiGraphFromFile(path);
		System.out.println("n="+d.vertexNum()+" m="+d.edgeNum()+" c="+d.colNum());
	}
	
}
