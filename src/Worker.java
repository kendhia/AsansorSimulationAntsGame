
public class Worker implements Comparable<Worker>{

	private String name;
	private int num;
	
	
	public Worker() {
		new Worker("", 0);
	}
	
	public Worker(String name, int num) {
		this.name = name;
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}


	@Override
	public int compareTo(Worker other) {
		// TODO Auto-generated method stub
		if (this.num == other.getNum()) return 0;
		else if (this.num > other.getNum()) return 1;
		else return -1;
	}

}
