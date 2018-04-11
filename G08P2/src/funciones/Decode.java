package funciones;

import java.util.ArrayList;

import GestionArchivos.Leer;
import base.Cromosoma;
import base.Gen;
import base.Gen2;

public class Decode extends Cromosoma {

	private double min = 0;
	private double max = 32;
	private char[] texto;
	private Gen2 gen;
	
	public Decode(double precision, String nombreArchivo) {
		Leer l = new Leer();
		l.LeerArvhivo(nombreArchivo);
		this.texto = l.getTexto().toCharArray();
		this.setPrecision(precision);
		this.gen = new Gen2();
		
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
		// Traducimos el texto
		ArrayList<Character> alelos = new ArrayList<Character>();
		alelos = this.gen.getAlelos();
		int j;
		boolean encontrado;
		for (int i = 0; i < this.texto.length; i++) {
			j = 0;
			encontrado = false;
			while (j < alelos.size() && !encontrado) {
				if (alelos.get(j).equals(this.texto[i])) {
					this.texto[i] = (char) (j + 97);
					encontrado = true;
				}
				j++;
			}
		}
		
		for (int i = 0; i < alelos.size(); i++) {
			System.out.print(alelos.get(i));
		}
		System.out.println();
		for (int i = 0; i < this.texto.length; i++) {
			System.out.print(this.texto[i]);
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
	@Override
	public Cromosoma copy() {
		// TODO Auto-generated method stub
		return null;
	}
}
