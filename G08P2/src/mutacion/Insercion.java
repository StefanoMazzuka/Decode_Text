package mutacion;

import java.util.ArrayList;
import java.util.Random;

import base.AlgoritmoGenetico;
import base.Cromosoma;
import base.Gen;
import funciones.Decode;

public class Insercion extends Mutacion{
	private double probMutacion;
	private AlgoritmoGenetico agCopy;
	ArrayList<Cromosoma> poblacion;
	private int lPoblacion;

	public Insercion(double probMutacion) {
		this.probMutacion = probMutacion;
	}

	public void mutar(AlgoritmoGenetico ag) {

		this.agCopy = ag.copy();	
		this.poblacion = this.agCopy.getPoblacion();
		this.lPoblacion = this.agCopy.getlPoblacion();

		Cromosoma c;

		double prob;
		for (int i = 0; i < this.lPoblacion; i++){
			prob = Math.random();

			if(prob < this.probMutacion) {
				c = mutarCromosoma(this.poblacion.get(i).copy());
				this.poblacion.set(i, c);
			}
		}
	}

	private Cromosoma mutarCromosoma(Cromosoma c) {
		
		Gen g;
		ArrayList<Character> alelos = new ArrayList<Character>();
		g = c.getGen().copy();
		alelos = g.getAlelos();

		int alelosAMutar;
		Random r = new Random();
		alelosAMutar = r.nextInt(26);
		
		int posAlelo = r.nextInt(26);	
		while (posAlelo == 0) {
			posAlelo = r.nextInt(26);
		}
		
		int posAIntercambiar = r.nextInt(posAlelo);
		char letra = alelos.get(posAlelo);
		for (int i = 0; i < alelosAMutar; i++) {
			for (int j = posAlelo; j > posAIntercambiar; j--) {
				alelos.set(j, alelos.get(j - 1));
			}
			alelos.set(posAIntercambiar, letra);
			posAlelo = r.nextInt(26);
			while (posAlelo == 0) {
				posAlelo = r.nextInt(26);
			}
			
			posAIntercambiar = r.nextInt(posAlelo);
			letra = alelos.get(posAlelo);
		}

		g.setAlelos(alelos);
		c.setGen(g);
		c.calcularFitness();
		
		return c;
	}
}
