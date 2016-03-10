package graphs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ColoredGraphFactory {
	
	// TODO add method for constructing random colored DAG and random colored Digraph 
	
	public static ColoredDiGraph coloredDiGraphFromFile(String  path){
		ColoredDiGraph d = new ColoredDiGraph();
		HashMap<Integer,ColoredVertex> idtovert = new HashMap<Integer,ColoredVertex>();
		
		try{
			//BufferedReader of FileReader
			BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

        	String line;
        	while((line = bufferedReader.readLine()) != null) {
            	// lines starting with # are comments
        		if (!line.startsWith("#")){
        			String[] fields = line.split("\t");
        			if (fields.length==2) {
						int id = Integer.parseInt(fields[0]);
						int color = Integer.parseInt(fields[1]);
        				ColoredVertex v = new ColoredVertex(id, color);
						idtovert.put(id,v);
						d.addVertex(v);
        			}
        			else if (fields.length==3) {
        				ColoredVertex u = idtovert.get(Integer.parseInt(fields[0])); 
        				ColoredVertex v = idtovert.get(Integer.parseInt(fields[1])); 
        				Double weight = Double.parseDouble(fields[2]);
        				d.addEdge(u, v, weight);
            			}
            	}
        	}
        	bufferedReader.close();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		catch (NumberFormatException e){
			System.out.println(e.getMessage());			
		}

        return d;
	}
}
