package Mutacion;

import java.util.ArrayList;
import java.util.Random;

import Base.Cromosoma;
import Base.Gen;
import Funciones.Decode;

public class Inversion extends Mutacion {
	
	private double probMutacion;
	ArrayList<Cromosoma> poblacion;
	private int lPoblacion;
	
	public Inversion(double probMutacion) {
		this.probMutacion = probMutacion;
	}
	
	public ArrayList<Cromosoma> mutar(ArrayList<Cromosoma> poblacion) {

		this.poblacion = poblacion;
		this.lPoblacion = this.poblacion.size();

		Cromosoma c = new Decode();

		double prob;
		for (int i = 0; i < this.lPoblacion; i++){
			prob = Math.random();

			if(prob < this.probMutacion) {
				c = mutarCromosoma(this.poblacion.get(i).copy());
				c.calcularFitness();
				this.poblacion.set(i, c);
			}
		}
		return this.poblacion;
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
		
		int aux;
		if (posI > posJ) {
			aux = posJ;
			posJ = posI;
			posI = aux;
		}
		
		char letra;
		int j = posJ;
		for (int i = posI; i < j; i++) {
			letra = alelos.get(j);
			alelos.set(j, alelos.get(i));
			alelos.set(i, letra);
			j--;
		}

		g.setAlelos(alelos);
		c.setGen(g);
		c.calcularFitness();
		
		return c;
	}
}