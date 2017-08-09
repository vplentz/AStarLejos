import java.util.ArrayList;
import java.util.Stack;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class AStarr {
	PriorityQueue opened = new PriorityQueue();
	DifferentialPilot dPilot = new DifferentialPilot(1.5748f, 4.409449f, Motor.A, Motor.C);
	MySet closed = new MySet();
	MNode whereImI;
	ArrayList<MNode> notTraveledList = new ArrayList<MNode>();
	ArrayList<MNode> cells = new ArrayList<MNode>();
	int gridHeight = 6;
	int gridWidth = 6;
	int myDirection = 1; // 0 down, 1 up, 2 left, 3 right
	MNode start;
	MNode end;

	public AStarr(MyPair start, MyPair end, int myDirection) {
		dPilot.setRotateSpeed(100);
		dPilot.setTravelSpeed(10);
		dPilot.setAcceleration(20);
		this.myDirection = myDirection;
		initGrid(start, end);
		System.out.println("START X"+ this.start.getX() + " Y " +this.start.getY());
		System.out.println("END X"+ this.end.getX() + " Y " +this.end.getY());
		this.process();
	}

	public void initGrid(MyPair start, MyPair end) {
		ArrayList<MyPair> walls = new ArrayList<MyPair>();
		walls.add(new MyPair(0, 0));
		walls.add(new MyPair(0, 1));
		walls.add(new MyPair(0, 3));
		walls.add(new MyPair(1, 4));
		walls.add(new MyPair(2, 2));
		walls.add(new MyPair(3, 3));
		walls.add(new MyPair(4, 1));
		walls.add(new MyPair(4, 3));
		walls.add(new MyPair(4, 4));
		walls.add(new MyPair(4, 5));
		walls.add(new MyPair(5, 1));
		boolean flag = false;
		for (int x = 0; x < gridWidth; x++) {
			for (int y = 0; y < gridHeight; y++) {
				for (MyPair wall : walls) {
					boolean reachable = true;
					if (wall.x == x && wall.y == y) {
						reachable = false;
						flag = true;
						cells.add(new MNode(reachable, x, y));
						break;
					}
				}
				if (flag == false)
					cells.add(new MNode(true, x, y));
				flag = false;
			}
		}
		// for(MNode cell: cells){

		this.start = getCell(start.getX(), start.getY());
		this.end = getCell(end.getX(), end.getY());
		System.out.println("start x y " + this.start.getX() + this.start.getY() + this.start.isReachable());
		System.out.println("end x y " + this.end.getX() + this.end.getY() + this.end.isReachable());

	}

	public void updateCell(MNode adjacent, MNode cell) {
		adjacent.setG(cell.getG() + 10);
		adjacent.setH(this.getHeuristic(adjacent));
		adjacent.setParent(cell);
		adjacent.setF(adjacent.getH() + adjacent.getG());
	}

	public void process() {
		this.opened.heapPush(this.start);
		boolean isFirst = true;
		while (this.opened.getNodes().size() != 0) {
			MNode cell = opened.heapPop();
			if (isFirst) {
				isFirst = false;
				this.whereImI = cell;
				System.out.println("first X " + whereImI.getX() + " Y "+ whereImI.getY());
			}else{
				System.out.println("current X " + whereImI.getX() + " Y "+ whereImI.getY());
				System.out.println("next X " + cell.getX() + " Y "+ cell.getY());
				if(!areAdjacent(whereImI,cell)){
					System.out.println("RECURSION");
					new AStarr(new MyPair(whereImI.getX(), whereImI.getY()), new MyPair(cell.getX(), cell.getY()), myDirection);
					whereImI = cell;
				}else{
					moveIt(whereImI, cell);
					whereImI = cell;
				}
			}
			this.closed.add(cell);
			if (cell.equals(this.end)) {
				this.displayPath();
				break;
			}
			ArrayList<MNode> adj_cells = this.getAdjacentCells(cell);
			for (MNode adjacentCell : adj_cells) {
				if (adjacentCell.isReachable() && !this.closed.contains(adjacentCell)) {
					if (this.opened.getNodes().contains(adjacentCell)) {
						if (adjacentCell.getG() > cell.getG() + 10)
							this.updateCell(adjacentCell, cell);
					} else {
//						System.out.println("insertin in heap x "+ adjacentCell.getX() + " y "+ adjacentCell.getY());
						this.updateCell(adjacentCell, cell);
						opened.heapPush(adjacentCell);
					}
				}
			}
		}
	}
	public boolean areAdjacent(MNode myself, MNode wanted){
		if(this.getAdjacentCells(myself).contains(wanted))
			return true;
		return false;
	}
	public void moveIt(MNode whereImI, MNode whereToGo) {
		if (whereImI.getY() != whereToGo.getY()) {// left or right
			if (whereToGo.getY() - whereImI.getY() == 1) {// right

				if (myDirection == 1)// was up
					turnRobotRight(dPilot);
				else if (myDirection == 0)// was down
					turnRobotLeft(dPilot);
				else if (myDirection == 2) {// was left
					turnRobotRight(dPilot);
					turnRobotRight(dPilot);
				}

				System.out.println("right");
			//	System.out.println("where am i x " + whereImI.getX() + " where to go x " + whereToGo.getX());
				
				myDirection = 3;
			} else {// left
				if (myDirection == 1)// was up
					turnRobotLeft(dPilot);
				else if (myDirection == 0)// was down
					turnRobotRight(dPilot);
				else if (myDirection == 3) {// was right
					turnRobotRight(dPilot);
					turnRobotRight(dPilot);
				}
				myDirection = 2;

				System.out.println("left");

				//System.out.println("where am i x " + whereImI.getX() + " where to go x " + whereToGo.getX());
			}
		} else {// up or down
			if (whereToGo.getX() - whereImI.getX() == 1) {// down
				if (myDirection == 1) {// was up
					turnRobotRight(dPilot);
					turnRobotRight(dPilot);
				} else if (myDirection == 3)// was right
					turnRobotRight(dPilot);
				else if (myDirection == 2)// was left
					turnRobotLeft(dPilot);
				myDirection = 0;

				System.out.println("down");

				//System.out.println("where am i Y " + whereImI.getY() + " where to go Y " + whereToGo.getY());
			} else {// up
				if (myDirection == 0) {// was down
					turnRobotRight(dPilot);
					turnRobotRight(dPilot);
				} else if (myDirection == 3)// was right
					turnRobotLeft(dPilot);
				else if (myDirection == 2)// was left
					turnRobotRight(dPilot);
				myDirection = 1;

				System.out.println("up");

				//System.out.println("where am i Y " + whereImI.getY() + " where to go Y " + whereToGo.getY());
	
			}
		}
		dPilot.travel(7.87402f);
	}

	public static void turnRobotRight(DifferentialPilot dPilot) {
		// 21.676989
		dPilot.rotate(-180);
	}

	public static void turnRobotLeft(DifferentialPilot dPilot) {
		// 21.676989
		dPilot.rotate(180);
	}

	public void displayPath() {
		MNode cell = end;
		while (cell.getParent() != start) {
			cell = cell.getParent();
			System.out.println(cell.getX() + " , " + cell.getY());
		}
	}

	public ArrayList<MNode> getAdjacentCells(MNode cell) {
		ArrayList<MNode> cells = new ArrayList<MNode>();
		int x = cell.getX();
		int y = cell.getY();
		if (x < gridWidth - 1)
			cells.add(getCell(x + 1, y));
		if (y > 0)
			cells.add(getCell(x, y - 1));
		if (x > 0)
			cells.add(getCell(x - 1, y));
		if (y < gridHeight - 1)
			cells.add(getCell(x, y + 1));
		return cells;
	}

	int getHeuristic(MNode cell) {
		return 10 * (Math.abs(cell.getX() - end.getX()) + Math.abs(cell.getY() - end.getY()));
	}

	public PriorityQueue getOpened() {
		return opened;
	}

	public void setOpened(PriorityQueue opened) {
		this.opened = opened;
	}

	public int getGridWidth() {
		return gridWidth;
	}

	public void setGridWidth(int gridWidth) {
		this.gridWidth = gridWidth;
	}

	public MNode getStart() {
		return start;
	}

	public void setStart(MNode start) {
		this.start = start;
	}

	public MNode getEnd() {
		return end;
	}

	public void setEnd(MNode end) {
		this.end = end;
	}

	MNode getCell(int x, int y) {
		return cells.get((x * gridHeight) + y);
	}

	public MySet getClosed() {
		return closed;
	}

	public void setClosed(MySet closed) {
		this.closed = closed;
	}

	public ArrayList<MNode> getCells() {
		return cells;
	}

	public void setCells(ArrayList<MNode> cells) {
		this.cells = cells;
	}

	public int getGridHeight() {
		return gridHeight;
	}

	public void setGridHeight(int gridHeight) {
		this.gridHeight = gridHeight;
	}

	public int getGridWeight() {
		return gridWidth;
	}

	public void setGridWeight(int gridWeight) {
		this.gridWidth = gridWeight;
	}

}
