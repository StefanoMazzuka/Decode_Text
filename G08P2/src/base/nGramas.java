package base;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;

import GestionArchivos.Leer;

public class nGramas {

	private HashMap<String, Double> frecuenciaMonogramas;
	private HashMap<String, Double> frecuenciaBigramas;
	private HashMap<String, Double> frecuenciaTrigramas;

	public nGramas() {
		this.frecuenciaMonogramas = new HashMap<String, Double>();
		this.frecuenciaBigramas = new HashMap<String, Double>();
		this.frecuenciaTrigramas = new HashMap<String, Double>();
		crearfrecuenciaMonogramas();
		//crearFrecuenciaBigramas();
		//crearFrecuenciaTrigramas();
		double x = 0.0;
		for (HashMap.Entry<String, Double> entry : frecuenciaMonogramas.entrySet()) {
			x += entry.getValue();
		}
		System.out.println(x);
		x = 0.0;
		for (HashMap.Entry<String, Double> entry : frecuenciaBigramas.entrySet()) {
			x += entry.getValue();
		}
		System.out.println(x);
		x = 0.0;
		for (HashMap.Entry<String, Double> entry : frecuenciaTrigramas.entrySet()) {
			x += entry.getValue();
		}
		System.out.println(x);
	}

	private void crearfrecuenciaMonogramas() {
		Leer l = new Leer();
		l.leerNgramas("Qua.txt");
		String[] texto = l.getTexto().split(" ");
		

		double total = 0;

		char[] nGrama;
		String nGramaStr;
		
		FileWriter fichero = null;
        PrintWriter pw = null;

		total = 0;
		for (int i = 1; i < texto.length; i += 2) {
			total += Double.parseDouble(texto[i]);
		}

		for (int i = 0; i < texto.length; i += 2) {
			nGrama = texto[i].toCharArray();
			for (int j = 0; j < nGrama.length; j++) {
				nGrama[j] = Character.toLowerCase(nGrama[j]);
			}
			nGramaStr = Character.toString(nGrama[0]) + Character.toString(nGrama[1]) + Character.toString(nGrama[2]) + Character.toString(nGrama[3]);
			this.frecuenciaTrigramas.put(nGramaStr, ((Double.parseDouble(texto[i + 1]) * 100) / total));
		}
		
	fichero = null;
       pw = null;
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

		//		this.frecuenciaMonogramas.put('e', 0.1249);
		//		this.frecuenciaMonogramas.put('t', 0.0928);
		//		this.frecuenciaMonogramas.put('a', 0.0804);
		//		this.frecuenciaMonogramas.put('o', 0.0764);
		//		this.frecuenciaMonogramas.put('i', 0.0757);
		//		this.frecuenciaMonogramas.put('n', 0.0723);
		//		this.frecuenciaMonogramas.put('s', 0.0651);
		//		this.frecuenciaMonogramas.put('r', 0.0628);
		//		this.frecuenciaMonogramas.put('h', 0.0505);
		//		this.frecuenciaMonogramas.put('l', 0.0407);
		//		this.frecuenciaMonogramas.put('d', 0.0382);
		//		this.frecuenciaMonogramas.put('c', 0.0334);
		//		this.frecuenciaMonogramas.put('u', 0.0273);
		//		this.frecuenciaMonogramas.put('m', 0.0251);
		//		this.frecuenciaMonogramas.put('f', 0.0240);
		//		this.frecuenciaMonogramas.put('p', 0.0214);
		//		this.frecuenciaMonogramas.put('g', 0.0187);
		//		this.frecuenciaMonogramas.put('w', 0.0168);
		//		this.frecuenciaMonogramas.put('y', 0.0166);
		//		this.frecuenciaMonogramas.put('b', 0.0148);
		//		this.frecuenciaMonogramas.put('v', 0.0105);
		//		this.frecuenciaMonogramas.put('k', 0.0054);
		//		this.frecuenciaMonogramas.put('x', 0.0023);
		//		this.frecuenciaMonogramas.put('j', 0.0016);
		//		this.frecuenciaMonogramas.put('q', 0.0012);
		//		this.frecuenciaMonogramas.put('z', 0.0009);
	}
	private void crearFrecuenciaBigramas() {
		Leer l = new Leer();
		l.leerNgramas("Bi.txt");
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
		l.leerNgramas("Tri.txt");
		String[] texto = l.getTexto().split("\\t");

		double total = 0;
		for (int i = 1; i < texto.length; i += 2) {
			total += Double.parseDouble(texto[i]);
		}

		for (int i = 0; i < texto.length; i += 2) {
			this.frecuenciaTrigramas.put(texto[i], ((Double.parseDouble(texto[i + 1]) * 100) / total));
		}
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
}
