
public class MNode {
	private boolean reachable;
	private int x;
	private int y;
	private MNode parent;
	private int g;
	private int h;
	private int f;
	
	
	public MNode(boolean reachable, int x, int y) {
		this.parent = null;
		this.g = 0;
		this.h = 0;
		this.f = 0;
		this.reachable = reachable;
		this.x = x;
		this.y = y;
	}
	
	public boolean isReachable() {
		return reachable;
	}
	public void setReachable(boolean reachable) {
		this.reachable = reachable;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public MNode getParent() {
		return parent;
	}
	public void setParent(MNode parent) {
		this.parent = parent;
	}
	public int getG() {
		return g;
	}
	public void setG(int g) {
		this.g = g;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public int getF() {
		return f;
	}
	public void setF(int f) {
		this.f = f;
	}
	
}
