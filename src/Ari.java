
public class Ari {

	
	private int life;
	private String name;
	
	public Ari(int life, String name) {
		this.life = life;
		this.name = name;
	}

	
	public Ari(String name) {
		this.life = 1;
		this.name = name;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
}
