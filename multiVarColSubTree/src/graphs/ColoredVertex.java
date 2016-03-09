package graphs;

public class ColoredVertex {
	private int id;
	private int color;
	
	public ColoredVertex(int id, int color){
		this.id = id;
		this.color = color;
	}
	
	public int getColor() {
		return color;
	}
	
	public void setColor(int color) {
		this.color = color;
	}

	public int getId() {
		return id;
	}
}
