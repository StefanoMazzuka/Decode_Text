package funciones;

import java.util.ArrayList;
import java.util.HashMap;

import base.Cromosoma;
import base.Gen2;
import base.nGramas;

public class Decode extends Cromosoma {

	private char[] textoOriginal;
	private char[] texto;
	private Gen2 gen;
	private ArrayList<Character> alelos;
	private HashMap<String, Integer> frecuenciaLetrasTexto;
	private HashMap<String, Integer> frecuenciaBigramasTexto;
	private HashMap<String, Integer> frecuenciaTrigramasTexto;
	private HashMap<String, Double> frecuenciaLetras;
	private HashMap<String, Double> frecuenciaBigramas;
	private HashMap<String, Double> frecuenciaTrigramas;

	public Decode(char[] texto) {
		this.textoOriginal = texto;
		this.texto = texto;
		this.gen = new Gen2();
		this.alelos = new ArrayList<Character>();
		this.alelos = this.gen.getAlelos();

		for (int i = 0; i < texto.length; i++) {
			System.out.print(this.texto[i]);
		}
		System.out.println();

		//		inicializarFreciencias();
		calcularFitness();
	}
	@Override
	public void calcularFenotipo() {
		// TODO Auto-generated method stub
	}
	//	private void inicializarFreciencias() {
	//		nGramas ng = new nGramas();
	//		this.frecuenciaLetras = ng.getFrecuenciaLetras();
	//		this.frecuenciaBigramas = ng.getFrecuenciaBigramas();
	//		this.frecuenciaTrigramas = ng.getFrecuenciaTrigramas();
	//
	//		for (HashMap.Entry<String, Double> entry : this.frecuenciaLetras.entrySet()) {
	//			this.frecuenciaLetrasTexto.put(entry.getKey(), 0);
	//		}
	//		
	//		for (HashMap.Entry<String, Double> entry : this.frecuenciaBigramas.entrySet()) {
	//			this.frecuenciaBigramasTexto.put(entry.getKey(), 0);
	//		}
	//		
	//		for (HashMap.Entry<String, Double> entry : this.frecuenciaTrigramas.entrySet()) {
	//			this.frecuenciaTrigramasTexto.put(entry.getKey(), 0);
	//		}
	//	}
	public void calcularFitness() {
		pasarAMinusculas();
		for (int i = 0; i < texto.length; i++) {
			System.out.print(this.texto[i]);
		}
		System.out.println();
		pasarClaveGen();
		for (int i = 0; i < texto.length; i++) {
			System.out.print(this.texto[i]);
		}
		System.out.println();
		//		crearFrecuenciaLetrasTexto();
		//		crearFrecuenciaBigramasTexto();
		//		crearFrecuenciaTrigramasTexto();
	}
	private void pasarAMinusculas() {
		for (int i = 0; i < this.texto.length; i++) {
			this.texto[i] = Character.toLowerCase(this.texto[i]);
		}
	}
	private void pasarClaveGen() {

		//		for (int i = 0; i < alelos.size(); i++) {
		//			System.out.print(alelos.get(i));
		//		}
		//		System.out.println();

		for (int i = 0; i < this.texto.length; i++) {
			if (this.alelos.contains(this.texto[i]))
				this.texto[i] = (char) ((this.alelos.indexOf(this.texto[i])) + 97);
		}
	}
	//	private void crearFrecuenciaLetrasTexto() {
	//		String letra;
	//		for (int i = 0; i < this.texto.length; i++) {
	//			letra = Character.toString(this.texto[i]);
	//			if (this.frecuenciaLetrasTexto.containsKey(letra))
	//				this.frecuenciaLetrasTexto.put(letra, 
	//						(this.frecuenciaLetrasTexto.get(letra) + 1));
	//		}
	//	}
	//	private void crearFrecuenciaBigramasTexto() {
	//		String bigrama;
	//		for (int i = 0; i < this.texto.length; i += 2) {
	//			bigrama = Character.toString(this.texto[i]) + 
	//					Character.toString(this.texto[i + 1]);
	//			if (this.frecuenciaBigramasTexto.containsKey(bigrama))
	//				this.frecuenciaBigramasTexto.put(bigrama, 
	//						(this.frecuenciaBigramasTexto.get(bigrama) + 1));
	//		}
	//	}
	//	private void crearFrecuenciaTrigramasTexto() {
	//		String trigrama;
	//		for (int i = 0; i < this.texto.length; i += 3) {
	//			trigrama = Character.toString(this.texto[i]) + 
	//					Character.toString(this.texto[i + 1]) + 
	//					Character.toString(this.texto[i + 2]);
	//			if (this.frecuenciaBigramasTexto.containsKey(trigrama))
	//				this.frecuenciaBigramasTexto.put(trigrama, 
	//						(this.frecuenciaBigramasTexto.get(trigrama) + 1));
	//		}
	//	}
	public Cromosoma copy() {
		Gen2 gen = new Gen2();
		gen = this.gen.copy();
		double fitness = this.fitness;
		int id = this.id;

		Cromosoma f = new Decode(this.textoOriginal);

		f.setGen2(gen);
		f.setFitness(fitness);
		f.setId(id);

		return f;
	}
}
