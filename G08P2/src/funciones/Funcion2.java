package funciones;

import base.Cromosoma;
import base.Gen;

public class Funcion2 extends Cromosoma {

	private double min = -512;
	private double max = 512;

	public Funcion2(double precision) {
		this.setPrecision(precision);
		this.gen = new Gen[2];
		this.lGen = new int[2];

		for (int i = 0; i < 2; i++) {
			this.lGen[i] = (int) Math.ceil(Math.log((1 + ((this.max - this.min) / this.getPrecision()))) / Math.log(2));
			this.gen[i] = new Gen(this.lGen[0]);
		}

		calcularFitness();
	}
	public void calcularFenotipo() {
		double[] fenotipo = new double[2];

		for (int i = 0; i < 2; i++) {
			fenotipo[i] = this.min + (this.max - this.min) * 
					this.bin_dec(this.gen[i], i) / (Math.pow(2, this.lGen[i]) - 1);
		}

		this.setFenotipo(fenotipo);
	}
	public void calcularFitness() {
		calcularFenotipo();
		double[] fenotipo = this.getFenotipo();
		double fitness;

		fitness = -(fenotipo[1] + 47) * 
				(Math.sin(Math.sqrt(Math.abs(fenotipo[1] + (fenotipo[0] / 2) + 47)))) - fenotipo[0] * 
						(Math.sin(Math.sqrt(Math.abs(fenotipo[0] - (fenotipo[1] + 47)))));

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
		
		Cromosoma f = new Funcion2(this.precision);
		
		f.setGen(gen);
		f.setPrecision(precision);
		f.setFenotipo(fenotipo);
		f.setFitness(fitness);
		f.setlGen(lGen);
		f.setId(id);
		
		return f;
	}
	@Override
	public String getTexto() {
		// TODO Auto-generated method stub
		return null;
	}
}
