package funciones;

import base.Cromosoma;
import base.Gen;

public class Funcion1 extends Cromosoma {

	private double min = 0;
	private double max = 32;

	public Funcion1(double precision) {
		this.setPrecision(precision);
		this.gen = new Gen[1];
		this.lGen = new int[1];
		
		this.lGen[0] = (int) Math.ceil(Math.log((1 + ((this.max - this.min) / this.getPrecision()))) / Math.log(2));
		this.gen[0] = new Gen(this.lGen[0]);
		
		calcularFitness();
	}
	public void calcularFenotipo() {
		double[] fenotipo = new double[1];
		
		fenotipo[0] = this.min + (this.max - this.min) * 
				this.bin_dec(this.gen[0], 0) / (Math.pow(2, this.lGen[0]) - 1);
		
		this.setFenotipo(fenotipo);
	}
	public void calcularFitness() {
		calcularFenotipo();
		double[] fenotipo = this.getFenotipo();
		double fitness;
		
		fitness = 20 + Math.E - 20 * 
				Math.pow(Math.E, (-0.2 * Math.abs(fenotipo[0]))) - 
				Math.pow(Math.E, Math.cos(2 * Math.PI * fenotipo[0]));
		
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
		
		Cromosoma f = new Funcion1(this.precision);
		
		f.setGen(gen);
		f.setPrecision(precision);
		f.setFenotipo(fenotipo);
		f.setFitness(fitness);
		f.setlGen(lGen);
		f.setId(id);
		
		return f;
	}
}
