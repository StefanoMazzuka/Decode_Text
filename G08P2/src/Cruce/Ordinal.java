package Cruce;

import java.util.ArrayList;
import java.util.Random;

import Base.Cromosoma;
import Base.Gen;

public class Ordinal extends Cruce {
	private int numElemACruzar;
	private double pCruce;
	private ArrayList<Integer> posicionesPadres;
	private ArrayList<Cromosoma> poblacion;
	private ArrayList<Cromosoma> poblacionFinal;
	private ArrayList<Cromosoma> poblacionACruzar;
	private ArrayList<Character> vectorOrdinal;
	
	public Ordinal(double pCruce) {
		this.pCruce = pCruce;
	}

	public ArrayList<Cromosoma> cruzar(ArrayList<Cromosoma> poblacion) {

		this.posicionesPadres = new ArrayList<Integer>();
		this.poblacion = new ArrayList<Cromosoma>();
		this.poblacion = poblacion;
		this.poblacionFinal = new ArrayList<Cromosoma>();
		this.poblacionACruzar = new ArrayList<Cromosoma>();
		this.vectorOrdinal = new ArrayList<Character>();

	    for (int i = 97; i <= 122; i++)
	    	this.vectorOrdinal.add((char) i);
		
		cualCruza();

		for (int i = 0; i < numElemACruzar; i += 2)
			cruzarCromosomas(i, i + 1, poblacionACruzar.get(i), poblacionACruzar.get(i + 1));

		poblacionFinal();
		
		return this.poblacionFinal;
	}
	public void cualCruza() {
		double pc = 0;
		for (int i = 0; i < this.poblacion.size(); i++) {
			pc = Math.random();
			if (pc < pCruce) {
				this.poblacionACruzar.add(this.poblacion.get(i).copy());
				this.posicionesPadres.add(i);
			}
		}

		if (this.poblacionACruzar.size() % 2 != 0) {
			this.poblacionACruzar.remove(this.poblacionACruzar.size() - 1);
			this.posicionesPadres.remove(this.posicionesPadres.size() - 1);
		}
	}
	public void cruzarCromosomas(int pos1, int pos2, Cromosoma padreUno, Cromosoma padreDos) {
		ArrayList<Character> padreU = padreUno.getGen().getAlelos();
		ArrayList<Character> padreD = padreDos.getGen().getAlelos();
		ArrayList<Integer> padreUOrdinal = new ArrayList<Integer>();
		ArrayList<Integer> padreDOrdinal = new ArrayList<Integer>();
		ArrayList<Character> abcU = this.vectorOrdinal;
		ArrayList<Character> abcD = this.vectorOrdinal;
		
		// Rellenamos los padres ordinales
		for (int i = 0; i < 26; i++) {
			padreUOrdinal.add(abcU.indexOf(padreU.get(i)));
			abcU.remove(padreU.get(i));
			padreDOrdinal.add(abcD.indexOf(padreD.get(i)));
			abcD.remove(padreD.get(i));
		}

		// Cruzamos los hijos
		ArrayList<Integer> hijoUOrdinal = new ArrayList<Integer>();
		ArrayList<Integer> hijoDOrdinal = new ArrayList<Integer>();		
		Random r = new Random();
		int pos = r.nextInt(26);
		for (int i = 0; i < pos; i++) {
			hijoUOrdinal.add(padreUOrdinal.get(i));
			hijoDOrdinal.add(padreDOrdinal.get(i));
		}

		for (int j = pos; j < 26; j++) {
			hijoUOrdinal.add(padreDOrdinal.get(j));
			hijoDOrdinal.add(padreUOrdinal.get(j));
		}

		// Transformamos los hijos a alelos
		abcU = this.vectorOrdinal;
		abcD = this.vectorOrdinal;
		ArrayList<Character> hijoU = new ArrayList<Character>();
		ArrayList<Character> hijoD = new ArrayList<Character>();
		for (int i = 0; i < 26; i++) {
			hijoU.add(abcU.get(hijoUOrdinal.get(i)));
			abcU.remove(hijoUOrdinal.get(i));
			hijoD.add(abcD.get(hijoDOrdinal.get(i)));
			abcD.remove(hijoDOrdinal.get(i));
		}
		
		Gen genU = new Gen();
		genU.setAlelos(hijoU);
		Gen genD = new Gen();
		genD.setAlelos(hijoD);
		padreUno.setGen(genU);
		padreDos.setGen(genD);

 		padreUno.calcularFitness();
		padreDos.calcularFitness();
		
		this.poblacionACruzar.set(pos1, padreUno);
		this.poblacionACruzar.set(pos2, padreDos);
	}
	public void poblacionFinal() {
		int j = 0;
		for (int i = 0; i < this.poblacion.size(); i++) {
			if (this.posicionesPadres.contains(i)) {
				this.poblacionFinal.add(i, this.poblacionACruzar.get(j));
				j++;
			}
			else 
				this.poblacionFinal.add(i, this.poblacion.get(i));
		}
	}
}
