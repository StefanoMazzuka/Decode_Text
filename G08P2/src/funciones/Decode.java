package funciones;

import java.util.ArrayList;

import GestionArchivos.Leer;
import base.Cromosoma;
import base.Gen;
import base.Gen2;

public class Decode extends Cromosoma {

	private double min = 0;
	private double max = 32;
	private String texto;
	private Gen2 gen;
	
	public Decode(double precision, String nombreArchivo) {
		Leer l = new Leer();
		l.LeerArvhivo(nombreArchivo);
		this.texto = l.getTexto();
		this.setPrecision(precision);
		Gen2 gen = new Gen2();
		
		calcularFitness();
	}
	public void calcularFenotipo() {
//		double[] fenotipo = new double[1];
//		
//		fenotipo[0] = this.min + (this.max - this.min) * 
//				this.bin_dec(this.gen[0], 0) / (Math.pow(2, this.lGen[0]) - 1);
//		
//		this.setFenotipo(fenotipo);
	}
	public void calcularFitness() {
		String textoTraducido;
		ArrayList<Character> alelos = this.gen.getAlelos();
		int j;
		for (int i = 0; i < this.texto.length(); i++) {
			j = i;
			while (j < alelos.size() && !alelos.get(j).equals(this.texto.charAt(i))) {
				j++;
			}
			if (alelos.get(j).equals(this.texto.charAt(i))) this.texto.charAt(i) = (char) (i + 97);
		}
	}
//	public Cromosoma copy() {
//		Gen[] gen = new Gen[this.gen.length];
//		for (int i = 0; i < gen.length; i++) {
//			gen[i] = this.gen[i].copy();
//		}
//
//		double precision = this.precision;
//		double[] fenotipo = this.fenotipo;
//		double fitness = this.fitness;
//		int lGen[] = this.lGen;
//		int id = this.id;
//		
//		Cromosoma f = new Decode(this.precision, String nombreArchivo);
//		
//		f.setGen(gen);
//		f.setPrecision(precision);
//		f.setFenotipo(fenotipo);
//		f.setFitness(fitness);
//		f.setlGen(lGen);
//		f.setId(id);
//		
//		return f;
//	}
}
