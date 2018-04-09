package base;

import java.util.Random;

public class Gen {
	boolean[] alelos;
	int lgen;

	public Gen(int lgen) {
		this.lgen = lgen;
		this.alelos = new boolean[lgen];
		
		Random r = new Random();
		int alelo;
		for (int i = 0; i < this.lgen; i++) {
			alelo = r.nextInt(2);
			if (alelo == 1) this.alelos[i] = true;
			else this.alelos[i] = false;
		}
	}
	public boolean[] getAlelos() {
		return alelos;
	}
	public void setAlelos(boolean[] alelos) {
		this.alelos = alelos;
	}
	public double getLgen() {
		return lgen;
	}
	public void setLgen(int lgen) {
		this.lgen = lgen;
	}
	public Gen copy() {
		boolean[] alelos = this.alelos;
		Gen g = new Gen(this.lgen);
		g.setAlelos(alelos);
		return g;
	}
}


