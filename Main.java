import lejos.nxt.Button;

public class Main {
	public static void main(String[] args) throws Exception {
		AStarr astar = new AStarr();
		astar.process();
		Button.ENTER.waitForPressAndRelease();
	}
}
