import java.util.ArrayList;
public class PriorityQueue {
	ArrayList<MNode> nodes;
	public PriorityQueue(){
		this.nodes = new ArrayList<MNode>();
	}
	public ArrayList<MNode> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<MNode> nodes) {
		this.nodes = nodes;
	}
	public MNode heapPop(){
		if(this.nodes.isEmpty())
			return null;
		int min = 0;
		for(int i = 0; i < this.nodes.size(); i++){
			if(this.nodes.get(i).getF() < this.nodes.get(min).getF())
				min = i;
		}
		MNode toReturn = this.getNodes().get(min);
		this.getNodes().remove(min);
		return toReturn;
	}

	public void heapPush(MNode start) {
		// TODO Auto-generated method stub
		this.nodes.add(start);
	}
}
