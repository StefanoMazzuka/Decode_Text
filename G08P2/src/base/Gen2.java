package base;

import java.util.ArrayList;
import java.util.Collections;

public class Gen2 {
	private ArrayList<Character> alelos;

	public Gen2() {
		this.alelos = new ArrayList<Character>();

	    for (int i = 97; i <= 122; i++) {
	    	this.alelos.add((char) i);
	    }
	    Collections.shuffle(this.alelos);
	}
	public ArrayList<Character> getAlelos() {
		return this.alelos;
	}
	public void setAlelos(ArrayList<Character> alelos) {
		this.alelos = alelos;
	}
	public Gen2 copy() {
		ArrayList<Character> alelos = this.alelos;
		Gen2 g = new Gen2();
		g.setAlelos(alelos);
		return g;
	}
}