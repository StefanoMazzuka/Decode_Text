package funciones;

import base.Cromosoma;
import base.Gen;

public class Funcion3 extends Cromosoma {

	private double min0 = -3.0;
	private double max0 = 12.1;
	private double min1 = 4.1;
	private double max1 = 5.8;

	public Funcion3(double precision) {
		this.setPrecision(precision);
		this.gen = new Gen[2];
		this.lGen = new int[2];

		this.lGen[0] = (int) Math.ceil(Math.log((1 + ((this.max0 - this.min0) / this.getPrecision()))) / Math.log(2));
		this.gen[0] = new Gen(this.lGen[0]);

		this.lGen[1] = (int) Math.ceil(Math.log((1 + ((this.max1 - this.min1) / this.getPrecision()))) / Math.log(2));
		this.gen[1] = new Gen(this.lGen[1]);

		calcularFitness();
	}
	public void calcularFenotipo() {
		double[] fenotipo = new double[2];

		fenotipo[0] = this.min0 + (this.max0 - this.min0) * 
				this.bin_dec(this.gen[0], 0) / (Math.pow(2, this.lGen[0]) - 1);
		fenotipo[1] = this.min1 + (this.max1 - this.min1) * 
				this.bin_dec(this.gen[1], 1) / (Math.pow(2, this.lGen[1]) - 1);

		this.setFenotipo(fenotipo);
	}
	public void calcularFitness() {
		calcularFenotipo();
		double[] fenotipo = this.getFenotipo();
		double fitness;

		fitness = 21.5 + fenotipo[0] * Math.sin(4 * Math.PI * fenotipo[0]) + 
				fenotipo[1] * Math.sin(20 * Math.PI * fenotipo[1]);
		
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
		
		Cromosoma f = new Funcion3(this.precision);
		
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