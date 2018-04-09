package funciones;

import base.Cromosoma;
import base.Gen;

public class Funcion4 extends Cromosoma {

	private double min = -10;
	private double max = 10;

	public Funcion4(double precision) {
		this.setPrecision(precision);
		this.gen = new Gen[2];
		this.lGen = new int[2];

		this.lGen[0] = (int) Math.ceil(Math.log((1 + ((this.max - this.min) / this.getPrecision()))) / Math.log(2));
		this.gen[0] = new Gen(this.lGen[0]);

		this.lGen[1] = (int) Math.ceil(Math.log((1 + ((this.max - this.min) / this.getPrecision()))) / Math.log(2));
		this.gen[1] = new Gen(this.lGen[1]);

		calcularFitness();
	}
	public void calcularFenotipo() {
		double[] fenotipo = new double[2];

		for (int i = 0; i < 2; i++) {
			fenotipo[i] = this.min + (this.max - this.min) * 
					this.bin_dec(this.gen[i], 0) / (Math.pow(2, this.lGen[i]) - 1);
		}		

		this.setFenotipo(fenotipo);
	}
	public void calcularFitness() {
		calcularFenotipo();
		double[] fenotipo = this.getFenotipo();
		double fitness;

		double sum0 = 0.0;
		for (int i = 1; i < 6; i++) {
			sum0 += (i * Math.cos((i + 1) * fenotipo[0] + i));
		}

		double sum1 = 0.0;
		for (int i = 1; i < 6; i++) {
			sum1 += (i * Math.cos((i + 1) * fenotipo[1] + i));
		}

		fitness = sum0 * sum1;

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
		
		Cromosoma f = new Funcion4(this.precision);
		
		f.setGen(gen);
		f.setPrecision(precision);
		f.setFenotipo(fenotipo);
		f.setFitness(fitness);
		f.setlGen(lGen);
		f.setId(id);
		
		return f;
	}
}