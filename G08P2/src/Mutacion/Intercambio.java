package Mutacion;

import java.util.ArrayList;
import java.util.Random;

import Base.Cromosoma;
import Base.Gen;
import Funciones.Decode;

public class Intercambio extends Mutacion {
	
	private double probMutacion;
	private ArrayList<Cromosoma> poblacion;
	private ArrayList<Cromosoma> poblacionFinal;
	private int lPoblacion;

	public Intercambio(double probMutacion) {
		this.probMutacion = probMutacion;
	}

	public ArrayList<Cromosoma> mutar(ArrayList<Cromosoma> poblacion) {
	
		this.poblacion = poblacion;
		this.poblacionFinal = new ArrayList<Cromosoma>();
		this.lPoblacion = this.poblacion.size();

		Cromosoma c = new Decode();

		double prob;
		for (int i = 0; i < this.lPoblacion; i++){
			prob = Math.random();
			this.poblacionFinal.add(this.poblacion.get(i).copy());
			if(prob < this.probMutacion) {
				c = mutarCromosoma(this.poblacion.get(i).copy());
				this.poblacionFinal.set(i, c);
			}			
		}
		return this.poblacionFinal;
	}
	private Cromosoma mutarCromosoma(Cromosoma c) {

		Gen g;
		ArrayList<Character> alelos = new ArrayList<Character>();
		g = c.getGen().copy();
		alelos = g.getAlelos();

		int posI;
		int posJ;
		Random r = new Random();
		posI = r.nextInt(26);
		posJ = r.nextInt(26);

		char letra;
		letra = alelos.get(posJ);
		alelos.set(posJ, alelos.get(posI));
		alelos.set(posI, letra);

		g.setAlelos(alelos);
		c.setGen(g);
		c.calcularFitness();

		return c;
	}
}