package funciones;

import java.util.ArrayList;
import java.util.HashMap;

import base.Cromosoma;
import base.Gen;

public class Decode extends Cromosoma {

	private char[] textoOriginal;
	private char[] textoTraducido;
	private ArrayList<Character> alelos;
	private HashMap<String, Double> frecuenciaMonogramasTexto;
	private HashMap<String, Double> frecuenciaBigramasTexto;
	private HashMap<String, Double> frecuenciaTrigramasTexto;
	private HashMap<String, Double> frecuenciaMonogramas;
	private HashMap<String, Double> frecuenciaBigramas;
	private HashMap<String, Double> frecuenciaTrigramas;

	public Decode(){};
	public Decode(char[] texto, HashMap<String, Double> frecuenciaMonogramas, 
			HashMap<String, Double> frecuenciaBigramas, 
			HashMap<String, Double> frecuenciaTrigramas) {

		this.frecuenciaMonogramas = frecuenciaMonogramas;
		this.frecuenciaBigramas = frecuenciaBigramas;
		this.frecuenciaTrigramas = frecuenciaTrigramas;
		this.textoOriginal = texto;
		this.textoTraducido = texto;
		this.gen = new Gen();
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

		this.textoCromosoma = new String(this.textoTraducido);

		calcularFrecuencias();

		double fitMonograma = 0.0;
		double fitBigrama = 0.0;
		double fitTrigrama = 0.0;
		double fitReal = 0.0;
		double fitEsperado = 0.0;

		for (HashMap.Entry<String, Double> entry : frecuenciaMonogramasTexto.entrySet()) {
			if (entry.getValue() != 0) {
			fitReal = entry.getValue();
			fitEsperado = this.frecuenciaMonogramas.get(entry.getKey());
			fitMonograma += Math.abs(fitReal * (Math.log(fitEsperado) / Math.log(2)));
			}
		}

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

		this.fitness = fitMonograma * 0.1 + fitBigrama * 0.3 + fitTrigrama * 0.6;
		//		System.out.println(this.fitness);1
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
		for (int i = 0; i < this.textoTraducido.length; i++) {
			this.textoTraducido[i] = Character.toLowerCase(this.textoTraducido[i]);
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
				this.textoTraducido[i] = (char) ((this.alelos.indexOf(this.textoTraducido[i])) + 97);
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
		for (int i = 0; i < this.textoTraducido.length; i++) {
			if (((int) this.textoTraducido[i]) < 97 || ((int) this.textoTraducido[i]) > 122) nGrama = "";
			else {
				nGrama += Character.toString(this.textoTraducido[i]);

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

		if (contMonograma > 0)
			for (HashMap.Entry<String, Double> entry : frecuenciaMonogramasTexto.entrySet()) {
				this.frecuenciaMonogramasTexto.put(entry.getKey(), (entry.getValue() / contMonograma));
			}

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
		Gen gen2 = new Gen();
		gen2 = this.gen.copy();
		double fitness = this.fitness;
		int id = this.id;
		String texto = this.textoCromosoma;
		char[] textoOriginal = this.textoOriginal;
		char[] textoTraducido = this.textoOriginal;
		ArrayList<Character> alelos = new ArrayList<>();
		HashMap<String, Double> frecuenciaMonogramasTexto = new HashMap<String, Double>();
		HashMap<String, Double> frecuenciaBigramasTexto = new HashMap<String, Double>();
		HashMap<String, Double> frecuenciaTrigramasTexto = new HashMap<String, Double>();
		HashMap<String, Double> frecuenciaMonogramas = new HashMap<String, Double>();
		HashMap<String, Double> frecuenciaBigramas = new HashMap<String, Double>();
		HashMap<String, Double> frecuenciaTrigramas = new HashMap<String, Double>();

		alelos = this.alelos;
		frecuenciaMonogramasTexto = this.frecuenciaMonogramasTexto;
		frecuenciaBigramasTexto = this.frecuenciaBigramasTexto;
		frecuenciaTrigramasTexto = this.frecuenciaTrigramasTexto;
		frecuenciaMonogramas = this.frecuenciaMonogramas;
		frecuenciaBigramas = this.frecuenciaBigramas;
		frecuenciaTrigramas = this.frecuenciaTrigramas;

		Cromosoma f = new Decode();

		f.setGen(gen2);
		f.setFitness(fitness);
		f.setId(id);
		f.setTextoCromosoma(texto);
		f.setTextoOriginal(textoOriginal);
		f.setTextoTraducido(textoTraducido);
		f.setAlelos(alelos);
		f.setFrecuenciaMonogramasTexto(frecuenciaMonogramasTexto);
		f.setFrecuenciaBigramasTexto(frecuenciaBigramasTexto);
		f.setFrecuenciaTrigramasTexto(frecuenciaTrigramasTexto);
		f.setFrecuenciaMonogramas(frecuenciaMonogramas);
		f.setFrecuenciaBigramas(frecuenciaBigramas);
		f.setFrecuenciaTrigramas(frecuenciaTrigramas);
		
		return f;
	}

	// Getters and Setters
	public char[] getTextoOriginal() {
		return textoOriginal;
	}
	public void setTextoOriginal(char[] textoOriginal) {
		this.textoOriginal = textoOriginal;
	}
	public char[] getTextoTraducido() {
		return this.textoTraducido;
	}
	public void setTextoTraducido(char[] textoTraducido) {
		this.textoTraducido = textoTraducido;
	}
	public ArrayList<Character> getAlelos() {
		return alelos;
	}
	public void setAlelos(ArrayList<Character> alelos) {
		this.alelos = alelos;
	}
	public HashMap<String, Double> getFrecuenciaMonogramasTexto() {
		return frecuenciaMonogramasTexto;
	}
	public void setFrecuenciaMonogramasTexto(
			HashMap<String, Double> frecuenciaMonogramasTexto) {
		this.frecuenciaMonogramasTexto = frecuenciaMonogramasTexto;
	}
	public HashMap<String, Double> getFrecuenciaBigramasTexto() {
		return frecuenciaBigramasTexto;
	}
	public void setFrecuenciaBigramasTexto(
			HashMap<String, Double> frecuenciaBigramasTexto) {
		this.frecuenciaBigramasTexto = frecuenciaBigramasTexto;
	}
	public HashMap<String, Double> getFrecuenciaTrigramasTexto() {
		return frecuenciaTrigramasTexto;
	}
	public void setFrecuenciaTrigramasTexto(
			HashMap<String, Double> frecuenciaTrigramasTexto) {
		this.frecuenciaTrigramasTexto = frecuenciaTrigramasTexto;
	}
	public HashMap<String, Double> getFrecuenciaMonogramas() {
		return frecuenciaMonogramas;
	}
	public void setFrecuenciaMonogramas(HashMap<String, Double> frecuenciaMonogramas) {
		this.frecuenciaMonogramas = frecuenciaMonogramas;
	}
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
}
