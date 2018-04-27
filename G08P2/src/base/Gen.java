package base;

import java.util.ArrayList;
import java.util.Collections;

public class Gen {
	private ArrayList<Character> alelos;

	public Gen() {
		this.alelos = new ArrayList<Character>();
	}
	public void crearAlelos() {
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
	public Gen copy() {
		ArrayList<Character> alelos = this.alelos;
		Gen g = new Gen();
		g.setAlelos(alelos);
		return g;
	}
	public String concatenarAlelos() {
		String alelos = "";
		
		for (int i = 0; i < this.alelos.size() - 2; i++) {
			alelos += this.alelos.get(i) + "   ";
		}
		
		alelos += this.alelos.get(this.alelos.size() - 2) + "  ";
		alelos += this.alelos.get(this.alelos.size() - 1);
		
		return alelos;
	}
}