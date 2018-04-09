package mutacion;

import java.util.ArrayList;
import java.util.Random;

import base.AlgoritmoGenetico;
import base.Cromosoma;
import base.Gen;

public class Mutacion {
	private double probMutacion;
	private AlgoritmoGenetico agCopy;
	ArrayList<Cromosoma> poblacion;
	private int[] lGen;
	private int lCromosoma;
	private int lPoblacion;

	public Mutacion(double probMutacion) {
		this.probMutacion = probMutacion;
	}
	public void mutar (AlgoritmoGenetico ag) {	

		this.agCopy = ag.copy();	
		this.poblacion = this.agCopy.getPoblacion();		
		this.lGen = this.poblacion.get(0).getlGen();
		this.lCromosoma = this.agCopy.getlCromosoma();
		this.lPoblacion = this.agCopy.getlPoblacion();

		Cromosoma c;
		Gen[] g;
		boolean[] a;
		for (int i = 0; i < this.lPoblacion; i++) {
			c = this.poblacion.get(i);
			g = c.getGen();
			for (int j = 0; j < this.lCromosoma; j++) {
				a = g[j].getAlelos();
				for (int k = 0; k < this.lGen[j]; k++) {
					a = mutarAlelos(a, k);
				}
				g[j].setAlelos(a);
			}
			c.setGen(g);
			c.calcularFenotipo();
			c.calcularFitness();
			this.poblacion.set(i, c);
		}
		
		ag.setPoblacion(this.poblacion);
	}
	private boolean[] mutarAlelos(boolean[] a, int k) {

		Random r = new Random();
		double porbAuxiliar = r.nextDouble();

		if (porbAuxiliar <= probMutacion) {
			if (a[k] == true) a[k] = false;
			else a[k] = true;
		}

		return a;
	}
}
