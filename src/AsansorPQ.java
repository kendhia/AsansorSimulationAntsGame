import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class AsansorPQ extends PriorityQueue<Worker> {
	private int hizi = 2;
	
	public int getHizi() {
		return hizi;
	}
	
	public void  setHizi(int hizi) {
		this.hizi = hizi;
	} 
	
	public List<Worker> pollAll() {
		List<Worker> workers = new ArrayList<>();
		int size = size();
		for (int i = 0; i < size; i++) {
			workers.add(poll());
		}
		return workers;
	}
}
