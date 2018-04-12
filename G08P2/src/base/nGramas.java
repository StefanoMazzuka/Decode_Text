package base;

import java.util.HashMap;

import GestionArchivos.Leer;

public class nGramas {

	private HashMap<String, Double> frecuenciaLetras;
	private HashMap<String, Double> frecuenciaBigramas;
	private HashMap<String, Double> frecuenciaTrigramas;

	public nGramas() {
		this.frecuenciaLetras = new HashMap<String, Double>();
		this.frecuenciaBigramas = new HashMap<String, Double>();
		this.frecuenciaTrigramas = new HashMap<String, Double>();
		crearFrecuenciaLetras();
		crearFrecuenciaBigramas();
		crearFrecuenciaTrigramas();
//		double x = 0.0;
//		for (HashMap.Entry<String, Double> entry : frecuenciaLetras.entrySet()) {
//			x += entry.getValue();
//		}
//		System.out.println(x);
//		x = 0.0;
//		for (HashMap.Entry<String, Double> entry : frecuenciaBigramas.entrySet()) {
//			x += entry.getValue();
//		}
//		System.out.println(x);
//		x = 0.0;
//		for (HashMap.Entry<String, Double> entry : frecuenciaTrigramas.entrySet()) {
//			x += entry.getValue();
//		}
//		System.out.println(x);
	}

	private void crearFrecuenciaLetras() {
		Leer l = new Leer();
		l.LeerNgramas("Letras.txt");
		String[] texto = l.getTexto().split("\\t");

		double total = 0;
		for (int i = 1; i < texto.length; i += 2) {
			total += Double.parseDouble(texto[i]);
		}

		for (int i = 0; i < texto.length; i += 2) {
			this.frecuenciaLetras.put(texto[i], ((Double.parseDouble(texto[i + 1]) * 100) / total));
		}

		//		this.frecuenciaLetras.put('e', 0.1249);
		//		this.frecuenciaLetras.put('t', 0.0928);
		//		this.frecuenciaLetras.put('a', 0.0804);
		//		this.frecuenciaLetras.put('o', 0.0764);
		//		this.frecuenciaLetras.put('i', 0.0757);
		//		this.frecuenciaLetras.put('n', 0.0723);
		//		this.frecuenciaLetras.put('s', 0.0651);
		//		this.frecuenciaLetras.put('r', 0.0628);
		//		this.frecuenciaLetras.put('h', 0.0505);
		//		this.frecuenciaLetras.put('l', 0.0407);
		//		this.frecuenciaLetras.put('d', 0.0382);
		//		this.frecuenciaLetras.put('c', 0.0334);
		//		this.frecuenciaLetras.put('u', 0.0273);
		//		this.frecuenciaLetras.put('m', 0.0251);
		//		this.frecuenciaLetras.put('f', 0.0240);
		//		this.frecuenciaLetras.put('p', 0.0214);
		//		this.frecuenciaLetras.put('g', 0.0187);
		//		this.frecuenciaLetras.put('w', 0.0168);
		//		this.frecuenciaLetras.put('y', 0.0166);
		//		this.frecuenciaLetras.put('b', 0.0148);
		//		this.frecuenciaLetras.put('v', 0.0105);
		//		this.frecuenciaLetras.put('k', 0.0054);
		//		this.frecuenciaLetras.put('x', 0.0023);
		//		this.frecuenciaLetras.put('j', 0.0016);
		//		this.frecuenciaLetras.put('q', 0.0012);
		//		this.frecuenciaLetras.put('z', 0.0009);
	}
	private void crearFrecuenciaBigramas() {
		Leer l = new Leer();
		l.LeerNgramas("Bigramas.txt");
		String[] texto = l.getTexto().split("\\t");

		double total = 0;
		for (int i = 1; i < texto.length; i += 2) {
			total += Double.parseDouble(texto[i]);
		}

		for (int i = 0; i < texto.length; i += 2) {
			this.frecuenciaBigramas.put(texto[i], ((Double.parseDouble(texto[i + 1]) * 100) / total));
		}

		//		this.frecuenciaBigramas.put("th", 0.0356);
		//		this.frecuenciaBigramas.put("he", 0.0307);
		//		this.frecuenciaBigramas.put("in", 0.0243);
		//		this.frecuenciaBigramas.put("er", 0.0205);
		//		this.frecuenciaBigramas.put("an", 0.0199);
		//		this.frecuenciaBigramas.put("re", 0.0185);
		//		this.frecuenciaBigramas.put("on", 0.0176);
		//		this.frecuenciaBigramas.put("at", 0.0149);
		//		this.frecuenciaBigramas.put("en", 0.0145);
		//		this.frecuenciaBigramas.put("nd", 0.0135);
		//		this.frecuenciaBigramas.put("ti", 0.0134);
		//		this.frecuenciaBigramas.put("es", 0.0134);
		//		this.frecuenciaBigramas.put("or", 0.0128);
		//		this.frecuenciaBigramas.put("te", 0.0120);
		//		this.frecuenciaBigramas.put("of", 0.0117);
		//		this.frecuenciaBigramas.put("ed", 0.0117);
		//		this.frecuenciaBigramas.put("is", 0.0113);
		//		this.frecuenciaBigramas.put("it", 0.0112);
		//		this.frecuenciaBigramas.put("al", 0.0109);
		//		this.frecuenciaBigramas.put("ar", 0.0107);
		//		this.frecuenciaBigramas.put("st", 0.0105);
		//		this.frecuenciaBigramas.put("to", 0.0104);
		//		this.frecuenciaBigramas.put("nt", 0.0104);
		//		this.frecuenciaBigramas.put("ng", 0.0095);
		//		this.frecuenciaBigramas.put("se", 0.0093);
		//		this.frecuenciaBigramas.put("ha", 0.0093);
		//		this.frecuenciaBigramas.put("as", 0.0087);
		//		this.frecuenciaBigramas.put("ou", 0.0087);
		//		this.frecuenciaBigramas.put("io", 0.0083);
		//		this.frecuenciaBigramas.put("le", 0.0083);
		//		this.frecuenciaBigramas.put("ve", 0.0083);
		//		this.frecuenciaBigramas.put("co", 0.0079);
		//		this.frecuenciaBigramas.put("me", 0.0079);
		//		this.frecuenciaBigramas.put("de", 0.0076);
		//		this.frecuenciaBigramas.put("hi", 0.0076);
		//		this.frecuenciaBigramas.put("ri", 0.0073);
		//		this.frecuenciaBigramas.put("ro", 0.0073);
		//		this.frecuenciaBigramas.put("ic", 0.0070);
		//		this.frecuenciaBigramas.put("ne", 0.0069);
		//		this.frecuenciaBigramas.put("ea", 0.0069);
		//		this.frecuenciaBigramas.put("ra", 0.0069);
		//		this.frecuenciaBigramas.put("ce", 0.0065);
		//		this.frecuenciaBigramas.put("li", 0.0062);
		//		this.frecuenciaBigramas.put("ch", 0.0060);
		//		this.frecuenciaBigramas.put("ll", 0.0058);
		//		this.frecuenciaBigramas.put("be", 0.0058);
		//		this.frecuenciaBigramas.put("ma", 0.0057);
		//		this.frecuenciaBigramas.put("si", 0.0055);
		//		this.frecuenciaBigramas.put("om", 0.0055);
		//		this.frecuenciaBigramas.put("ur", 0.0054);
	}
	private void crearFrecuenciaTrigramas() {
		Leer l = new Leer();
		l.LeerNgramas("Trigramas.txt");
		String[] texto = l.getTexto().split("\\t");

		double total = 0;
		for (int i = 1; i < texto.length; i += 2) {
			total += Double.parseDouble(texto[i]);
		}

		for (int i = 0; i < texto.length; i += 2) {
			this.frecuenciaTrigramas.put(texto[i], ((Double.parseDouble(texto[i + 1]) * 100) / total));
		}
	}
	public HashMap<String, Double> getFrecuenciaLetras() {
		return frecuenciaLetras;
	}
	public HashMap<String, Double> getFrecuenciaBigramas() {
		return frecuenciaBigramas;
	}
	public HashMap<String, Double> getFrecuenciaTrigramas() {
		return frecuenciaTrigramas;
	}
}
