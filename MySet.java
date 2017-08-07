import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MySet implements Set{
	ArrayList<MNode> setList;
	public MySet(){
		this.setList = new ArrayList<MNode>();
	}
	@Override
	public boolean add(Object o) {
		if(!contains((MNode) o))
			setList.add((MNode) o);
		return true;
	}

	@Override
	public boolean addAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object o) {
		MNode obj = (MNode) o;
		for(MNode node: this.setList){
			if(node.getX() == obj.getX() && node.getY() == obj.getY()){
				return true;
			}
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray(Object[] a) {
		// TODO Auto-generated method stub
		return null;
	}

}
