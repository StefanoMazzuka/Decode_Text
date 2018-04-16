package base;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;

import GestionArchivos.Leer;

public class nGramas {

	private HashMap<String, Double> frecuenciaMonogramas;
	private HashMap<String, Double> frecuenciaBigramas;
	private HashMap<String, Double> frecuenciaTrigramas;
	private HashMap<String, Double> frecuenciaQuadragramas;
	
	public nGramas() {
		this.frecuenciaMonogramas = new HashMap<String, Double>();
		this.frecuenciaBigramas = new HashMap<String, Double>();
		this.frecuenciaTrigramas = new HashMap<String, Double>();
		crearfrecuenciaMonogramas();
		crearFrecuenciaBigramas();
		crearFrecuenciaTrigramas();
		//crearFrecuenciaQuadragramas();
		
//		double x = 0.0;
//		for (HashMap.Entry<String, Double> entry : frecuenciaMonogramas.entrySet()) {
//			x += entry.getValue();
//		}
//		System.out.println("Monograma1: " + x);
//		x = 0.0;
//		for (HashMap.Entry<String, Double> entry : frecuenciaBigramas.entrySet()) {
//			x += entry.getValue();
//		}
//		System.out.println("Bigrama1: " + x);
//		x = 0.0;
//		for (HashMap.Entry<String, Double> entry : frecuenciaTrigramas.entrySet()) {
//			x += entry.getValue();
//		}
//		System.out.println("Trigrama1: " + x);
	}

	private void crearfrecuenciaMonogramas() {
		Leer l = new Leer();
		l.leerNgramas("Monogramas.txt");
		String[] texto = l.getTexto().split(" ");

		double total = 0.0;
		for (int i = 1; i < texto.length; i += 2) {
			total += Double.parseDouble(texto[i]);
		}

		for (int i = 0; i < texto.length; i += 2) {
			this.frecuenciaMonogramas.put(texto[i], ((Double.parseDouble(texto[i + 1])) / total));
		}
	}
	private void crearFrecuenciaBigramas() {
		Leer l = new Leer();
		l.leerNgramas("Bigramas.txt");
		String[] texto = l.getTexto().split(" ");

		double total = 0.0;
		for (int i = 1; i < texto.length; i += 2) {
			total += Double.parseDouble(texto[i]);
		}

		for (int i = 0; i < texto.length; i += 2) {
			this.frecuenciaBigramas.put(texto[i], ((Double.parseDouble(texto[i + 1])) / total));
		}
	}
	private void crearFrecuenciaTrigramas() {
		Leer l = new Leer();
		l.leerNgramas("Trigramas.txt");
		String[] texto = l.getTexto().split(" ");

		double total = 0.0;
		for (int i = 1; i < texto.length; i += 2) {
			total += Double.parseDouble(texto[i]);
		}

		for (int i = 0; i < texto.length; i += 2) {
			this.frecuenciaTrigramas.put(texto[i], ((Double.parseDouble(texto[i + 1])) / total));
		}
	}
	private void crearFrecuenciaQuadragramas() {
		Leer l = new Leer();
		l.leerNgramas("Quadragramas.txt");
		String[] texto = l.getTexto().split(" ");

		double total = 0.0;
		for (int i = 1; i < texto.length; i += 2) {
			total += Double.parseDouble(texto[i]);
		}

		double totalFinal = 0.0;
		for (int i = 0; i < texto.length; i += 2) {
			totalFinal += ((Double.parseDouble(texto[i + 1]) * 100) / total);
			this.frecuenciaQuadragramas.put(texto[i], ((Double.parseDouble(texto[i + 1])) / total));
		}
		System.out.println("Quadragrama: " + totalFinal);
	}
	public HashMap<String, Double> getFrecuenciaMonogramas() {
		return frecuenciaMonogramas;
	}
	public HashMap<String, Double> getFrecuenciaBigramas() {
		return frecuenciaBigramas;
	}
	public HashMap<String, Double> getFrecuenciaTrigramas() {
		return frecuenciaTrigramas;
	}
	
	// Este metodo solo se usó una vez para transformar los textos.
	private void transformarTexto() {
		Leer l = new Leer();
		l.leerNgramas("Qua.txt");
		String[] texto = l.getTexto().split(" ");

		double total = 0;
		for (int i = 1; i < texto.length; i += 2) {
			total += Double.parseDouble(texto[i]);
		}

		char[] nGrama;
		String nGramaStr;
		for (int i = 0; i < texto.length; i += 2) {
			nGrama = texto[i].toCharArray();
			for (int j = 0; j < nGrama.length; j++) {
				nGrama[j] = Character.toLowerCase(nGrama[j]);
			}
			nGramaStr = Character.toString(nGrama[0]) + Character.toString(nGrama[1]) + Character.toString(nGrama[2]) + Character.toString(nGrama[3]);
			this.frecuenciaTrigramas.put(nGramaStr, ((Double.parseDouble(texto[i + 1]) * 100) / total));
		}

		FileWriter fichero = null;
		PrintWriter pw = null;
		try
		{
			fichero = new FileWriter("C:/Quadragramas.txt");
			pw = new PrintWriter(fichero);

			for (HashMap.Entry<String, Double> entry : frecuenciaTrigramas.entrySet()) {
				pw.println(entry.getKey() + " " + entry.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Nuevamente aprovechamos el finally para 
				// asegurarnos que se cierra el fichero.
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
