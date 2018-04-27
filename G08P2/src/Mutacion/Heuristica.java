package Mutacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import Base.Cromosoma;
import Base.Gen;
import Funciones.Decode;

public class Heuristica extends Mutacion {
	private double probMutacion;
	ArrayList<Cromosoma> poblacion;
	ArrayList<Character> alelosElegidos;
	ArrayList<String> permutaciones;
	private int lPoblacion;
	private static final int n = 3;
	private double fitnessMejor = Double.MAX_VALUE;
	private int[] lugares; //Guardo posiciones del cromosoma seleccionadas para mutar

	public Heuristica(double probMutacion) {
		this.probMutacion = probMutacion;
		lugares = new int[n];
	}

	public ArrayList<Cromosoma> mutar(ArrayList<Cromosoma> poblacion) {

		this.poblacion = poblacion;
		this.lPoblacion = this.poblacion.size();

		Cromosoma c = new Decode();

		double prob;
		for (int i = 0; i < this.lPoblacion; i++){
			prob = Math.random();

			if(prob < this.probMutacion) {
				c = elegirMejorMutacion(this.poblacion.get(i).copy());
				this.poblacion.set(i, c);
			}
		}		
		return this.poblacion;
	}
	private Cromosoma elegirMejorMutacion(Cromosoma c) {

		this.fitnessMejor = Double.MAX_VALUE;

		Gen g;
		ArrayList<Character> alelos = new ArrayList<Character>();
		this.alelosElegidos = new ArrayList<Character>();
		this.permutaciones = new ArrayList<String>();

		g = c.getGen().copy();
		alelos = g.getAlelos();
		Random r = new Random();
		int elegido;
		for(int i = 0; i < n; i++) {
			elegido = r.nextInt(26);
			if (!this.alelosElegidos.contains(alelos.get(elegido))) {
				alelosElegidos.add(alelos.get(elegido));
				this.lugares[i] = elegido;
			}
			else
				i--;
		}

		int numPermutaciones = factorial(n);
		for (int i = 0; i < numPermutaciones; i++) {
			this.permutaciones.add("-");
		}

		String cadena = "";
		for (int j = 0; j < n; j++) {
			cadena += this.alelosElegidos.get(j);
		}

		for (int i = 0; i < numPermutaciones; i++) {
			while (permutaciones.contains(cadena)) {
				cadena = "";
				Collections.shuffle(this.alelosElegidos);
				for (int j = 0; j < n; j++) {
					cadena += this.alelosElegidos.get(j);
				}
			}
			this.permutaciones.set(i, cadena);
		}

		Cromosoma cromosomaMejor = new Decode();
		for (int i = 0; i < numPermutaciones; i++) {
			cadena = this.permutaciones.get(i);
			for (int j = 0; j < n; j++) {
				alelos.set(this.lugares[j], cadena.charAt(j));
			}
			g.setAlelos(alelos);
			c.setGen(g);
			c.calcularFitness();

			if (c.getFitness() < this.fitnessMejor) {
				this.fitnessMejor = c.getFitness();
				cromosomaMejor = c;
			}
		}

		return cromosomaMejor;
	}
	public static int factorial(int n) {
		int resultado = 1;
		for (int i = 1; i <= n; i++) {
			resultado *= i;
		}
		return resultado;
	}
}