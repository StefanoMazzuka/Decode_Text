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
	private HashMap<String, Double> frecuenciaMonogramasTexto;
	private HashMap<String, Double> frecuenciaBigramasTexto;
	private HashMap<String, Double> frecuenciaTrigramasTexto;
	private HashMap<String, Double> frecuenciaMonogramas;
	private HashMap<String, Double> frecuenciaBigramas;
	private HashMap<String, Double> frecuenciaTrigramas;

	public Decode(char[] texto, HashMap<String, Double> frecuenciaMonogramas, 
			HashMap<String, Double> frecuenciaBigramas, 
			HashMap<String, Double> frecuenciaTrigramas) {

		this.frecuenciaMonogramas = frecuenciaMonogramas;
		this.frecuenciaBigramas = frecuenciaBigramas;
		this.frecuenciaTrigramas = frecuenciaTrigramas;
		this.textoOriginal = texto;
		this.texto = texto;
		this.gen = new Gen2();
		this.alelos = new ArrayList<Character>();
		this.alelos = this.gen.getAlelos();

		inicializarFrecuencias();
		calcularFitness();
	}
	@Override
	public void calcularFenotipo() {
		// TODO Auto-generated method stub
	}
	private void inicializarFrecuencias() {
		this.frecuenciaMonogramasTexto = new HashMap<String, Double>();
		this.frecuenciaBigramasTexto = new HashMap<String, Double>();
		this.frecuenciaTrigramasTexto = new HashMap<String, Double>();

		for (HashMap.Entry<String, Double> entry : this.frecuenciaMonogramas.entrySet()) {
			this.frecuenciaMonogramasTexto.put(entry.getKey(), 0.0);
		}

		for (HashMap.Entry<String, Double> entry : this.frecuenciaBigramas.entrySet()) {
			this.frecuenciaBigramasTexto.put(entry.getKey(), 0.0);
		}

		for (HashMap.Entry<String, Double> entry : this.frecuenciaTrigramas.entrySet()) {
			this.frecuenciaTrigramasTexto.put(entry.getKey(), 0.0);
		}
	}
	public void calcularFitness() {
		pasarAMinusculas();
		pasarClaveGen();
		calcularFrecuencias();

		double fitMonograma = 0.0;
		double fitBigrama = 0.0;
		double fitTrigrama = 0.0;
		
		for (HashMap.Entry<String, Double> entry : frecuenciaMonogramasTexto.entrySet()) {
			fitMonograma += Math.pow((this.frecuenciaMonogramas.get(entry.getKey()) - entry.getValue()), 2);
		}
		
		for (HashMap.Entry<String, Double> entry : frecuenciaBigramasTexto.entrySet()) {
			fitBigrama += Math.pow((this.frecuenciaBigramas.get(entry.getKey()) - entry.getValue()), 2);
		}
		
		for (HashMap.Entry<String, Double> entry : frecuenciaTrigramasTexto.entrySet()) {
			fitTrigrama += Math.pow((this.frecuenciaTrigramas.get(entry.getKey()) - entry.getValue()), 2);
		}

		this.fitness = fitMonograma + fitBigrama + fitTrigrama;
		System.out.println(this.fitness);
		//		// Mostramos el texto
		//		for (int i = 0; i < texto.length; i++) {
		//			System.out.print(this.texto[i]);
		//		}
		//		System.out.println();
		//		// Mostramos las frecuencias de Monogramas
		//		for (HashMap.Entry<String, Integer> entry : this.frecuenciaMonogramasTexto.entrySet()) {
		//			System.out.println(entry.getKey() + " - " + entry.getValue());
		//		}
		//		System.out.println();
		//		// Mostramos las frecuencias de Bigramas
		//		for (HashMap.Entry<String, Integer> entry : this.frecuenciaBigramasTexto.entrySet()) {
		//			System.out.println(entry.getKey() + " - " + entry.getValue());
		//		}
		//		System.out.println();
		//		// Mostramos las frecuencias de Trigramas
		//		for (HashMap.Entry<String, Integer> entry : this.frecuenciaTrigramasTexto.entrySet()) {
		//			System.out.println(entry.getKey() + " - " + entry.getValue());
		//		}
	}
	private void pasarAMinusculas() {
		for (int i = 0; i < this.texto.length; i++) {
			this.texto[i] = Character.toLowerCase(this.texto[i]);
		}
	}
	private void pasarClaveGen() {
		//		// Mostramos los alelos
		//		for (int i = 0; i < alelos.size(); i++) {
		//			System.out.print(alelos.get(i));
		//		}
		//		System.out.println();

		for (int i = 0; i < this.textoOriginal.length; i++) {
			if (this.alelos.contains(this.textoOriginal[i]))
				this.texto[i] = (char) ((this.alelos.indexOf(this.texto[i])) + 97);
		}
	}
	private void calcularFrecuencias() {
		// Contador de veces que aparecen los nGramas
		String nGrama = "";
		String monoGrama;
		String biGrama;
		String triGrama;
		int contMonograma = 0;
		int contBigrama = 0;
		int contTrigrama = 0;
		for (int i = 0; i < this.texto.length; i++) {
			if (((int) this.texto[i]) < 97 || ((int) this.texto[i]) > 122) nGrama = "";
			else {
				nGrama += Character.toString(this.texto[i]);

				monoGrama = Character.toString(nGrama.charAt(nGrama.length() - 1));
				if (this.frecuenciaMonogramasTexto.containsKey(monoGrama)) {
					this.frecuenciaMonogramasTexto.put(monoGrama,
							(this.frecuenciaMonogramasTexto.get(monoGrama) + 1));
					contMonograma++;
				}


				if (nGrama.length() > 1) {
					biGrama = Character.toString(nGrama.charAt(nGrama.length() - 2)) + 
							Character.toString(nGrama.charAt(nGrama.length() - 1));
					if (this.frecuenciaBigramasTexto.containsKey(biGrama)) {
						this.frecuenciaBigramasTexto.put(biGrama, 
								(this.frecuenciaBigramasTexto.get(biGrama) + 1));
						contBigrama++;
					}
				}

				if (nGrama.length() > 2) {
					triGrama = Character.toString(nGrama.charAt(nGrama.length() - 3)) + 
							Character.toString(nGrama.charAt(nGrama.length() - 2)) +
							Character.toString(nGrama.charAt(nGrama.length() - 1));
					if (this.frecuenciaTrigramasTexto.containsKey(triGrama)) {
						this.frecuenciaTrigramasTexto.put(triGrama, 
								(this.frecuenciaTrigramasTexto.get(triGrama) + 1));
						contTrigrama++;
					}
				}
			}
		}

		for (HashMap.Entry<String, Double> entry : frecuenciaMonogramasTexto.entrySet()) {
			this.frecuenciaMonogramasTexto.put(entry.getKey(), (entry.getValue() / contMonograma));
		}
		for (HashMap.Entry<String, Double> entry : frecuenciaBigramasTexto.entrySet()) {
			this.frecuenciaBigramasTexto.put(entry.getKey(), (entry.getValue() / contBigrama));
		}
		for (HashMap.Entry<String, Double> entry : frecuenciaTrigramasTexto.entrySet()) {
			this.frecuenciaTrigramasTexto.put(entry.getKey(), (entry.getValue() / contTrigrama));
		}

		// Mostramos los valores de las frecuencias
		//		double x = 0.0;
		//		for (HashMap.Entry<String, Double> entry : frecuenciaMonogramasTexto.entrySet()) {
		//			x += entry.getValue();
		//		}
		//		System.out.println("Monograma: " + x);
		//		x = 0.0;
		//		for (HashMap.Entry<String, Double> entry : frecuenciaBigramasTexto.entrySet()) {
		//			x += entry.getValue();
		//		}
		//		System.out.println("Bigrama: " + x);
		//		x = 0.0;
		//		for (HashMap.Entry<String, Double> entry : frecuenciaTrigramasTexto.entrySet()) {
		//			x += entry.getValue();
		//		}
		//		System.out.println("Trigrama: " + x);
	}
	public Cromosoma copy() {
		Gen2 gen = new Gen2();
		gen = this.gen.copy();
		double fitness = this.fitness;
		int id = this.id;
		HashMap<String, Double> frecuenciaMonogramas = new HashMap<String, Double>();
		HashMap<String, Double> frecuenciaBigramas = new HashMap<String, Double>();
		HashMap<String, Double> frecuenciaTrigramas = new HashMap<String, Double>();

		Cromosoma f = new Decode(this.textoOriginal, 
				frecuenciaMonogramas, 
				frecuenciaBigramas, 
				frecuenciaTrigramas);

		f.setGen2(gen);
		f.setFitness(fitness);
		f.setId(id);

		return f;
	}

	// Getters and Setters
	public String getTexto() {
		String texto = new String(this.texto);
		return texto;
	}
}
