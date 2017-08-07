import java.util.ArrayList;
import java.util.Set;

import lejos.util.Delay;

public class AStarr {
	PriorityQueue opened = new PriorityQueue();
	MySet closed = new MySet();
	ArrayList<MNode> cells = new ArrayList<MNode>();
	int gridHeight = 6;
	int gridWidth = 6;
	MNode start;
	MNode end;
	public AStarr() {
		initGrid();
		
	}
	public void initGrid(){
		ArrayList<MyPair> walls = new ArrayList<MyPair>();
		walls.add(new MyPair(0, 5));
		walls.add(new MyPair(1, 0));
		walls.add(new MyPair(1, 1));
		walls.add(new MyPair(1, 5));
		walls.add(new MyPair(2, 3));
		walls.add(new MyPair(3, 1));
		walls.add(new MyPair(3, 2));
		walls.add(new MyPair(3, 5));
		walls.add(new MyPair(4, 1));
		walls.add(new MyPair(4, 4));
		walls.add(new MyPair(5, 1));
		boolean flag = false;
		for(int x = 0; x < gridWidth; x++){
			for(int y = 0; y < gridHeight; y++){
				for(MyPair wall : walls){
					boolean reachable = true;
					if(wall.x == x && wall.y == y){
						reachable = false;
						flag = true;
						cells.add(new MNode(reachable, x, y));
					}
				}
				if(flag == false)
					cells.add(new MNode(true, x, y));
				flag = false;
			}
		}
	//	for(MNode cell: cells){
		
		start =	getCell(0, 0);
		end = getCell(5, 5);
		System.out.println("start x y " + start.getX() + start.getY() + start.isReachable());
		System.out.println("end x y " + end.getX() + end.getY() + end.isReachable());
	
	}
	public void updateCell(MNode adjacent, MNode cell){
		adjacent.setG(cell.getG()+10);
		adjacent.setH(this.getHeuristic(adjacent));
		adjacent.setParent(cell);
		adjacent.setF(adjacent.getH() + adjacent.getG());
	}
	public void process(){
		this.opened.heapPush(this.start);
		while(this.opened.getNodes().size() != 0){
			MNode cell = opened.heapPop();
			this.closed.add(cell);
			if(cell.equals(this.end)){
				this.displayPath();
				break;
			}
			ArrayList<MNode>adj_cells = this.getAdjacentCells(cell);
			for(MNode adjacentCell : adj_cells){
				if(adjacentCell.isReachable() && !this.closed.contains(adjacentCell)){
					if(this.opened.getNodes().contains(adjacentCell)){
						if(adjacentCell.getG() > cell.getG()+10)
							this.updateCell(adjacentCell, cell);
					}else{
						this.updateCell(adjacentCell, cell);
						opened.heapPush(adjacentCell);
					}
				}
			}
		}
	}
	public void displayPath(){
		MNode cell = end;
		while (cell.getParent() != start){
			cell = cell.getParent();
			System.out.println("path: cell: "+ cell.getX() + " , " + cell.getY());
		}
	}
	public ArrayList<MNode> getAdjacentCells(MNode cell){
		ArrayList<MNode> cells = new ArrayList<MNode>();
		int x = cell.getX();
		int y = cell.getY();
		if(x < gridWidth -1)
			cells.add(getCell(x+1, y));
		if(y > 0)
			cells.add(getCell(x, y-1));
		if(x > 0)
			cells.add(getCell(x-1, y));
		if(y < gridHeight -1)
			cells.add(getCell(x, y+1));
		return cells;
	}
	int getHeuristic(MNode cell){
		return 10*(Math.abs(cell.getX() - end.getX()) + Math.abs(cell.getY() - end.getY()));
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
	MNode getCell(int x, int y){
		return cells.get((x*gridHeight)+y);
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
