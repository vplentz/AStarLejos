import lejos.nxt.Button;

public class Main {
	public static void main(String[] args) throws Exception {
		AStarr astar = new AStarr(new MyPair(2,0), new MyPair(0,1), 3);
		Button.ENTER.waitForPressAndRelease();
	}
}
