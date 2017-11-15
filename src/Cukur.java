import java.util.Stack;

public class Cukur extends Stack<Object>{

	private String type = "cukur";
	private int uzun;

	public Cukur(int uzun) {
		this.uzun = uzun;
	}
	
	public int getUzun() {
		return uzun;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
