import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AsansorFIFO {
	private List<Worker> asansorSirasi;
	private  double hizi = 5;

	public AsansorFIFO() {
		asansorSirasi = new ArrayList<>();
	}
	
	public int enQueueWorker(Worker worker) {
		asansorSirasi.add(worker);
		return asansorSirasi.size();
	}
	
	public Worker deQueueWorker() {
		if (!asansorSirasi.isEmpty()) {
			Worker worker =  asansorSirasi.get(0);
			asansorSirasi.remove(0);
			return worker;
		}
		return null;
	}
	
	public boolean isEmpty() {
		return asansorSirasi.isEmpty();
	}
	
	public double getHizi() {
		return hizi;
	}
	
	public void  setHizi(double hizi) {
		this.hizi = hizi;
	}  
	
	public int getSize() {
		return asansorSirasi.size();
	}
	
	public List<Worker> getAll() {
		List<Worker> results = new ArrayList(asansorSirasi);
		asansorSirasi.clear();
		return results;
		
	}
	
}
