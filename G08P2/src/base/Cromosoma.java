package base;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Cromosoma {
	public Gen gen;
	public double precision;
	public double[] fenotipo;
	public double fitness;
	public int lGen = 26;
	public int id;
	public String textoCromosoma;

	public Cromosoma() {}
	
	public Cromosoma(double precision) {
		this.precision = precision;
	}
	public abstract Cromosoma copy();
	public abstract void calcularFenotipo();
	public abstract void calcularFitness();
	
	/*Getters y Setters*/
	public Gen getGen() {
		return gen;
	}
	public void setGen(Gen gen) {
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
	public int getlGen() {
		return lGen;
	}
	public void setlGen(int lGen) {
		this.lGen = lGen;
	}
	public String getTexto() {
		return this.textoCromosoma;
	}	
	public String getTextoCromosoma() {
		return textoCromosoma;
	}
	public void setTextoCromosoma(String textoCromosoma) {
		this.textoCromosoma = textoCromosoma;
	}

	// Setters externos
	public abstract void setTextoOriginal(char[] textoOriginal);
	public abstract void setTextoTraducido(char[] textoTraducido);
	public abstract void setAlelos(ArrayList<Character> alelos);
	public abstract void setFrecuenciaMonogramasTexto(HashMap<String, Double> frecuenciaMonogramasTexto);
	public abstract void setFrecuenciaBigramasTexto(HashMap<String, Double> frecuenciaBigramasTexto);
	public abstract void setFrecuenciaTrigramasTexto(HashMap<String, Double> frecuenciaTrigramasTexto);
	public abstract void setFrecuenciaMonogramas(HashMap<String, Double> frecuenciaMonogramas);
	public abstract void setFrecuenciaBigramas(HashMap<String, Double> frecuenciaBigramas);
	public abstract void setFrecuenciaTrigramas(HashMap<String, Double> frecuenciaTrigramas);
}
