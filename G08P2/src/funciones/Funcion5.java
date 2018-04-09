package funciones;

import base.Cromosoma;
import base.Gen;

public class Funcion5 extends Cromosoma {

	private double min = 0;
	private double max = Math.PI;
	private int n = 2;

	public Funcion5(double precision) {
		this.setPrecision(precision);
		this.gen = new Gen[n];
		this.lGen = new int[n];

		for (int i = 0; i < n; i++) {
			this.lGen[i] = (int) Math.ceil(Math.log((1 + ((this.max - this.min) / this.getPrecision()))) / Math.log(2));
			this.gen[i] = new Gen(this.lGen[i]);
		}
		
		calcularFitness();
	}
	public void calcularFenotipo() {
		double[] fenotipo = new double[n];

		for (int i = 0; i < n; i++) {
			fenotipo[i] = this.min + (this.max - this.min) * 
					this.bin_dec(this.gen[i], 0) / (Math.pow(2, this.lGen[i]) - 1);
		}

		this.setFenotipo(fenotipo);
	}
	public void calcularFitness() {
		calcularFenotipo();
		double[] fenotipo = this.getFenotipo();
		double fitness;
		
		double sum = 0.0;
		for (int i = 1; i <= n; i++) {
			sum += Math.sin(fenotipo[i - 1]) * Math.pow(Math.sin(((i + 1) * 
					Math.pow(fenotipo[i - 1], 2)) / Math.PI), 20);
		}
				
		fitness = -sum;
		
		this.setFitness(fitness);
	}
	public Cromosoma copy() {
		Gen[] gen = new Gen[this.gen.length];
		for (int i = 0; i < gen.length; i++) {
			gen[i] = this.gen[i].copy();
		}

		double precision = this.precision;
		double[] fenotipo = this.fenotipo;
		double fitness = this.fitness;
		int lGen[] = this.lGen;
		int id = this.id;
		
		Cromosoma f = new Funcion5(this.precision);
		
		f.setGen(gen);
		f.setPrecision(precision);
		f.setFenotipo(fenotipo);
		f.setFitness(fitness);
		f.setlGen(lGen);
		f.setId(id);
		
		return f;
	}
	public int getN() {
		return this.n;
	}
}