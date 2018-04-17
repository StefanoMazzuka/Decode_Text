package base;

public abstract class Cromosoma {
	public Gen[] gen;
	public Gen2 gen2;
	public double precision;
	public double[] fenotipo;
	public double fitness;
	public int lGen[];
	public int id;
		
	public Cromosoma() {}
	
	public Cromosoma(double precision) {
		this.precision = precision;
	}
	protected double bin_dec(Gen gen, int pos) {		
		double dec = 0;
		boolean[] alelos = gen.getAlelos();
		for(int i = 0; i < lGen[pos]; i++) {
			if (alelos[i] == true) dec += Math.pow(2, i); 
		}
		
		return dec; 
	}
	public abstract Cromosoma copy();
	public abstract void calcularFenotipo();
	public abstract void calcularFitness();
	public abstract String getTexto();

	/*Getters y Setters*/
	public Gen[] getGen() {
		return gen;
	}
	public void setGen(Gen[] gen) {
		this.gen = gen;
	}
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public double[] getFenotipo() {
		return fenotipo;
	}
	public void setFenotipo(double[] fenotipo) {
		this.fenotipo = fenotipo;
	}
	public double getPrecision() {
		return precision;
	}
	public void setPrecision(double precision) {
		this.precision = precision;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int[] getlGen() {
		return lGen;
	}
	public void setlGen(int[] lGen) {
		this.lGen = lGen;
	}
	public Gen2 getGen2() {
		return this.gen2;
	}
	public void setGen2(Gen2 gen2) {
		this.gen2 = gen2;
	}
}
