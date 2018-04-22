package mutacion;

import java.util.ArrayList;
import java.util.Random;

import base.AlgoritmoGenetico;
import base.Cromosoma;
import base.Gen;

public class MutacionBinaria extends Mutacion {
	private double probMutacion;
	private AlgoritmoGenetico agCopy;
	ArrayList<Cromosoma> poblacion;
	private int lGen;
	private int lPoblacion;

	public MutacionBinaria(double probMutacion) {
		this.probMutacion = probMutacion;
	}
	public void mutar(AlgoritmoGenetico ag) {	

		this.agCopy = ag.copy();	
		this.poblacion = this.agCopy.getPoblacion();		
		this.lGen = this.poblacion.get(0).getlGen();
		this.lPoblacion = this.agCopy.getlPoblacion();

		Cromosoma c;
		Gen g;
		ArrayList<Character> a = new ArrayList<Character>();

		for (int i = 0; i < this.lPoblacion; i++) {
			c = this.poblacion.get(i);
			g = c.getGen();
			a = g.getAlelos();

			for (int k = 0; k < this.lGen; k++) {
				a = mutarAlelos(a, k);
			}
			
			g.setAlelos(a);
			c.setGen(g);
			c.calcularFenotipo();
			c.calcularFitness();
			this.poblacion.set(i, c);
		}

		ag.setPoblacion(this.poblacion);
	}
	private ArrayList<Character> mutarAlelos(ArrayList<Character> a, int k) {

		Random r = new Random();
		double porbAuxiliar = r.nextDouble();
		int letra;

		if (porbAuxiliar <= probMutacion) {
			letra = r.nextInt(26);
			letra += 97;
			a.set(k, (char) letra);
		}

		return a;
	}
}
