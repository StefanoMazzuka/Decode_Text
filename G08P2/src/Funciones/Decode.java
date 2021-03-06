package Funciones;

import java.util.ArrayList;
import java.util.HashMap;

import Base.Cromosoma;
import Base.Gen;

public class Decode extends Cromosoma {
	
//	private HashMap<String, Double> frecuenciaMonogramasTextoInicial;
	private HashMap<String, Double> frecuenciaBigramasTextoInicial;
	private HashMap<String, Double> frecuenciaTrigramasTextoInicial;
//	private HashMap<String, Double> frecuenciaMonogramasTexto;
	private HashMap<String, Double> frecuenciaBigramasTexto;
	private HashMap<String, Double> frecuenciaTrigramasTexto;
//	private HashMap<String, Double> frecuenciaMonogramas;
	private HashMap<String, Double> frecuenciaBigramas;
	private HashMap<String, Double> frecuenciaTrigramas;

	public Decode(){};
	public Decode(String texto, 
			HashMap<String, Double> frecuenciaMonogramas, 
			HashMap<String, Double> frecuenciaBigramas, 
			HashMap<String, Double> frecuenciaTrigramas) {

//		this.frecuenciaMonogramas = frecuenciaMonogramas;
		this.frecuenciaBigramas = frecuenciaBigramas;
		this.frecuenciaTrigramas = frecuenciaTrigramas;
		this.textoOriginal = texto;
		this.gen = new Gen();
		this.gen.crearAlelos();

		inicializarFrecuencias();
		calcularFitness();
	}
	private void inicializarFrecuencias() {
//		this.frecuenciaMonogramasTextoInicial = new HashMap<String, Double>();
		this.frecuenciaBigramasTextoInicial = new HashMap<String, Double>();
		this.frecuenciaTrigramasTextoInicial = new HashMap<String, Double>();

//		for (HashMap.Entry<String, Double> entry : this.frecuenciaMonogramas.entrySet()) {
//			this.frecuenciaMonogramasTextoInicial.put(entry.getKey(), 0.0);
//		}

		for (HashMap.Entry<String, Double> entry : this.frecuenciaBigramas.entrySet()) {
			this.frecuenciaBigramasTextoInicial.put(entry.getKey(), 0.0);
		}

		for (HashMap.Entry<String, Double> entry : this.frecuenciaTrigramas.entrySet()) {
			this.frecuenciaTrigramasTextoInicial.put(entry.getKey(), 0.0);
		}
	}
	public void calcularFitness() {
		pasarClaveGen();
		calcularFrecuencias();

//		double fitMonograma = 0.0;
		double fitBigrama = 0.0;
		double fitTrigrama = 0.0;
		double fitReal = 0.0;
		double fitEsperado = 0.0;

//		for (HashMap.Entry<String, Double> entry : frecuenciaMonogramasTexto.entrySet()) {
//			if (entry.getValue() != 0) {
//				fitReal = entry.getValue();
//				fitEsperado = this.frecuenciaMonogramas.get(entry.getKey());
//				fitMonograma += Math.abs(fitReal * (Math.log(fitEsperado) / Math.log(2)));
//			}
//		}

		for (HashMap.Entry<String, Double> entry : frecuenciaBigramasTexto.entrySet()) {
			if (entry.getValue() != 0) {
				fitReal = entry.getValue();
				fitEsperado = this.frecuenciaBigramas.get(entry.getKey());
				fitBigrama += Math.abs(fitReal * (Math.log(fitEsperado) / Math.log(2)));
			}
		}

		for (HashMap.Entry<String, Double> entry : frecuenciaTrigramasTexto.entrySet()) {
			if (entry.getValue() != 0) {
				fitReal = entry.getValue();
				fitEsperado = this.frecuenciaTrigramasTexto.get(entry.getKey());
				fitTrigrama += Math.abs(fitReal * (Math.log(fitEsperado) / Math.log(2)));
			}
		} 
//		fitMonograma * 0.1 +
		this.fitness = fitBigrama * 0.3 + fitTrigrama * 0.7;
		//		System.out.println(this.fitness);
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
				// Mostramos las frecuencias de Trigramas
//				for (Entry<String, Double> entry : this.frecuenciaTrigramasTexto.entrySet()) {
//					System.out.println(entry.getKey() + " - " + entry.getValue());
//				}
	}
	private void pasarClaveGen() {
		// Mostramos los alelos
		//		for (int i = 0; i < alelos.size(); i++) {
		//			System.out.print(alelos.get(i));
		//		}
		//		System.out.println();
		ArrayList<Character> alelos = new ArrayList<Character>();
		char[] textoTraducido = this.textoOriginal.toCharArray();
		alelos = this.gen.getAlelos();
		for (int i = 0; i < textoTraducido.length; i++) {
			if (alelos.contains(textoTraducido[i]))
				textoTraducido[i] = (char) (alelos.indexOf(textoTraducido[i]) + 97);
		}
		this.textoCromosoma = new String(textoTraducido); 
	}
	private void calcularFrecuencias() {
		// Contador de veces que aparecen los nGramas
		String nGrama = "";
//		String monoGrama;
		String biGrama;
		String triGrama;
//		int contMonograma = 0;
		int contBigrama = 0;
		int contTrigrama = 0;

//		this.frecuenciaMonogramasTexto = new HashMap<String, Double>();
		this.frecuenciaBigramasTexto = new HashMap<String, Double>();
		this.frecuenciaTrigramasTexto = new HashMap<String, Double>();
//		this.frecuenciaMonogramasTexto = this.frecuenciaMonogramasTextoInicial;
		this.frecuenciaBigramasTexto = this.frecuenciaBigramasTextoInicial;
		this.frecuenciaTrigramasTexto = this.frecuenciaTrigramasTextoInicial;
		char[] texto = this.textoCromosoma.toCharArray();
		for (int i = 0; i < texto.length; i++) {
			if (((int) texto[i]) < 97 || ((int) texto[i]) > 122) nGrama = "";
			else {
				nGrama += texto[i];

//				monoGrama = "" + (nGrama.charAt(nGrama.length() - 1));
//				if (this.frecuenciaMonogramasTexto.containsKey(monoGrama)) {
//					this.frecuenciaMonogramasTexto.put(monoGrama,
//							(this.frecuenciaMonogramasTexto.get(monoGrama) + 1));
//					contMonograma++;
//				}

				if (nGrama.length() > 1) {
					biGrama = "" + (nGrama.charAt(nGrama.length() - 2)) + 
							(nGrama.charAt(nGrama.length() - 1));
					if (this.frecuenciaBigramasTexto.containsKey(biGrama)) {
						this.frecuenciaBigramasTexto.put(biGrama, 
								(this.frecuenciaBigramasTexto.get(biGrama) + 1));
						contBigrama++;
					}
				}

				if (nGrama.length() > 2) {
					triGrama = "" + (nGrama.charAt(nGrama.length() - 3)) + 
							(nGrama.charAt(nGrama.length() - 2)) +
							(nGrama.charAt(nGrama.length() - 1));
					if (this.frecuenciaTrigramasTexto.containsKey(triGrama)) {
						this.frecuenciaTrigramasTexto.put(triGrama,
								(this.frecuenciaTrigramasTexto.get(triGrama) + 1));
						contTrigrama++;
					}
				}
			}
		}

//		if (contMonograma > 0)
//			for (HashMap.Entry<String, Double> entry : frecuenciaMonogramasTexto.entrySet()) {
//				this.frecuenciaMonogramasTexto.put(entry.getKey(), (entry.getValue() / contMonograma));
//			}

		if (contBigrama > 0)
			for (HashMap.Entry<String, Double> entry : frecuenciaBigramasTexto.entrySet()) {
				this.frecuenciaBigramasTexto.put(entry.getKey(), (entry.getValue() / contBigrama));
			}

		if (contTrigrama > 0)
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
		Gen gen = new Gen();
		gen = this.gen.copy();
		double fitness = this.fitness;
		String textoCromosoma = this.textoCromosoma;
		String textoOriginal = this.textoOriginal;
//		HashMap<String, Double> frecuenciaMonogramasTextoInicial = new HashMap<String, Double>();
		HashMap<String, Double> frecuenciaBigramasTextoInicial = new HashMap<String, Double>();
		HashMap<String, Double> frecuenciaTrigramasTextoInicial = new HashMap<String, Double>();
//		HashMap<String, Double> frecuenciaMonogramasTexto = new HashMap<String, Double>();
//		HashMap<String, Double> frecuenciaBigramasTexto = new HashMap<String, Double>();
//		HashMap<String, Double> frecuenciaTrigramasTexto = new HashMap<String, Double>();
//		HashMap<String, Double> frecuenciaMonogramas = new HashMap<String, Double>();
		HashMap<String, Double> frecuenciaBigramas = new HashMap<String, Double>();
		HashMap<String, Double> frecuenciaTrigramas = new HashMap<String, Double>();

//		frecuenciaMonogramasTextoInicial = this.frecuenciaMonogramasTextoInicial;
		frecuenciaBigramasTextoInicial = this.frecuenciaBigramasTextoInicial;
		frecuenciaTrigramasTextoInicial = this.frecuenciaTrigramasTextoInicial;
//		frecuenciaMonogramasTexto = this.frecuenciaMonogramasTexto;
//		frecuenciaBigramasTexto = this.frecuenciaBigramasTexto;
//		frecuenciaTrigramasTexto = this.frecuenciaTrigramasTexto;
//		frecuenciaMonogramas = this.frecuenciaMonogramas;
		frecuenciaBigramas = this.frecuenciaBigramas;
		frecuenciaTrigramas = this.frecuenciaTrigramas;

		Cromosoma f = new Decode();

		f.setGen(gen);
		f.setFitness(fitness);
		f.setTextoCromosoma(textoCromosoma);
		f.setTextoOriginal(textoOriginal);
//		f.setFrecuenciaMonogramasTextoInicial(frecuenciaMonogramasTextoInicial);
		f.setFrecuenciaBigramasTextoInicial(frecuenciaBigramasTextoInicial);
		f.setFrecuenciaTrigramasTextoInicial(frecuenciaTrigramasTextoInicial);
//		f.setFrecuenciaMonogramasTexto(frecuenciaMonogramasTexto);
//		f.setFrecuenciaBigramasTexto(frecuenciaBigramasTexto);
//		f.setFrecuenciaTrigramasTexto(frecuenciaTrigramasTexto);
//		f.setFrecuenciaMonogramas(frecuenciaMonogramas);
		f.setFrecuenciaBigramas(frecuenciaBigramas);
		f.setFrecuenciaTrigramas(frecuenciaTrigramas);

		return f;
	}

	// Getters and Setters
//	public HashMap<String, Double> getFrecuenciaMonogramasTexto() {
//		return frecuenciaMonogramasTexto;
//	}
//	public void setFrecuenciaMonogramasTexto(
//			HashMap<String, Double> frecuenciaMonogramasTexto) {
//		this.frecuenciaMonogramasTexto = frecuenciaMonogramasTexto;
//	}
//	public HashMap<String, Double> getFrecuenciaBigramasTexto() {
//		return frecuenciaBigramasTexto;
//	}
//	public void setFrecuenciaBigramasTexto(
//			HashMap<String, Double> frecuenciaBigramasTexto) {
//		this.frecuenciaBigramasTexto = frecuenciaBigramasTexto;
//	}
//	public HashMap<String, Double> getFrecuenciaTrigramasTexto() {
//		return frecuenciaTrigramasTexto;
//	}
//	public void setFrecuenciaTrigramasTexto(
//			HashMap<String, Double> frecuenciaTrigramasTexto) {
//		this.frecuenciaTrigramasTexto = frecuenciaTrigramasTexto;
//	}
//	public HashMap<String, Double> getFrecuenciaMonogramas() {
//		return frecuenciaMonogramas;
//	}
//	public void setFrecuenciaMonogramas(HashMap<String, Double> frecuenciaMonogramas) {
//		this.frecuenciaMonogramas = frecuenciaMonogramas;
//	}
	public HashMap<String, Double> getFrecuenciaBigramas() {
		return frecuenciaBigramas;
	}
	public void setFrecuenciaBigramas(HashMap<String, Double> frecuenciaBigramas) {
		this.frecuenciaBigramas = frecuenciaBigramas;
	}
	public HashMap<String, Double> getFrecuenciaTrigramas() {
		return frecuenciaTrigramas;
	}
	public void setFrecuenciaTrigramas(HashMap<String, Double> frecuenciaTrigramas) {
		this.frecuenciaTrigramas = frecuenciaTrigramas;
	}
//	public void setFrecuenciaMonogramasTextoInicial(HashMap<String, Double> frecuenciaMonogramasTextoInicial) {
//		this.frecuenciaMonogramasTextoInicial = frecuenciaMonogramasTextoInicial;
//	}
	public void setFrecuenciaBigramasTextoInicial(HashMap<String, Double> frecuenciaBigramasTextoInicial) {
		this.frecuenciaBigramasTextoInicial = frecuenciaBigramasTextoInicial;
	}
	public void setFrecuenciaTrigramasTextoInicial(HashMap<String, Double> frecuenciaTrigramasTextoInicial) {
		this.frecuenciaTrigramasTextoInicial = frecuenciaTrigramasTextoInicial;
	}
}