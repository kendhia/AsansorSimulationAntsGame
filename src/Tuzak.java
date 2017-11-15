import java.util.Stack;

public class Tuzak extends Stack<Object>{
	private static String type = "tuzak";
	private static int uzun;

	public Tuzak(int uzun ) {
		this.uzun = uzun;
	}
	public static int getUzun() {
		return uzun;
	}

	public static  String getType() {
		return type;
	}

	public  void setType(String type) {
		this.type = type;
	}
	
	
}
