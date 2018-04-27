package Cruce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import Base.Cromosoma;
import Base.Gen;

public class Ordinal {
	int numElemACruzar;
	double pCruce;
	ArrayList<Cromosoma> poblacion = new ArrayList<Cromosoma>();
	ArrayList<Cromosoma> poblacionACruzar = new ArrayList<Cromosoma>();
	ArrayList<Character> vectorOrdinal = new ArrayList<Character>();
	Gen genCruzadoUno = new Gen();
	Gen genCruzadoDos = new Gen();
	
	public Ordinal(double pCruce) {
		this.pCruce = pCruce;
	}

	public ArrayList<Cromosoma> cruzar(ArrayList<Cromosoma> poblacion) {

		this.poblacion = poblacion;

	    for (int i = 97; i <= 122; i++)
	    	this.vectorOrdinal.add((char) i);
	    
	    Collections.shuffle(this.vectorOrdinal);
		
		cualCruza();

		for (int i = 0; i < numElemACruzar; i += 2)
			cruzarCromosomas(poblacionACruzar.get(i), poblacionACruzar.get(i + 1));

		poblacionFinal();
		
		return this.poblacion;
	}
	public void cualCruza() {

		double pc = 0;
		for (int i = 0; i < this.poblacion.size(); i++) {
			pc = Math.random();
			if (pc < pCruce) {
				this.poblacionACruzar.add(this.poblacion.get(i).copy());
			}
		}

		this.numElemACruzar = this.poblacionACruzar.size();

		if (this.poblacionACruzar.size() % 2 != 0) {
			this.poblacionACruzar.remove(this.poblacion.get(0));
			this.numElemACruzar--;
		}
	}
	public void cruzarCromosomas(Cromosoma padreUno, Cromosoma padreDos) {
		ArrayList<Character> padreU = padreUno.getGen().getAlelos();
		ArrayList<Character> padreD = padreDos.getGen().getAlelos();
		ArrayList<Integer>padreUOrdinal = new ArrayList<Integer>();
		ArrayList<Integer>padreDOrdinal = new ArrayList<Integer>();
		
		for (int i = 0; i < padreU.size(); i++) {
			padreUOrdinal.add(0);
			padreDOrdinal.add(0);
		}
		
		for (int i = 0; i < padreU.size(); i++) {
			padreUOrdinal.set(i, padreU.indexOf(vectorOrdinal.get(i)));
			
			padreDOrdinal.add();
		}
		
		
		//cruzarGenes(pmin, pmax, padreUGen.copy(), padreDGen.copy());

		padreUno.setGen(this.genCruzadoUno);
		padreDos.setGen(this.genCruzadoDos);

 		padreUno.calcularFitness();
		padreDos.calcularFitness();

		int i = 0;
		while (i < this.numElemACruzar && this.poblacionACruzar.get(i).getId() != padreUno.getId()) {
			i++;
		}

		this.poblacionACruzar.set(i, padreUno);

		while (i < this.numElemACruzar && this.poblacionACruzar.get(i).getId() != padreDos.getId()) {
			i++;
		}

		this.poblacionACruzar.set(i, padreDos);
	}
	public void cruzarGenes(int posI, int posJ, Gen padreUno, Gen padreDos) {
		ArrayList<Character> hijoUno = new ArrayList<Character>();
		ArrayList<Character> hijoDos = new ArrayList<Character>();
		ArrayList<Character> padreU = padreUno.getAlelos();
		ArrayList<Character> padreD = padreDos.getAlelos();

		// Inicializo los arrays auxiliares a 0
		for (int i = 0; i < 26; i++) {
			hijoUno.add((char) 96);
			hijoDos.add((char) 96);

		}

		// Hago el primer cruce entre los dos puntos de corte
		for (int i = posI; i <= posJ; i++) {
			hijoUno.set(i, padreD.get(i));
			hijoDos.set(i, padreU.get(i));
		}

		// HIJO1
		char letra;
		int i = posJ + 1;
		while (i != posI) {
			if (i == 26)
				i = 0;
			if (i != posI) {
				letra = padreU.get(i);
				while (hijoUno.contains((letra))) {
					letra = padreU.get(hijoUno.indexOf(letra));
				}
				hijoUno.set(i, letra);
				i++;
			}
		}

		// HIJO2
		i = posJ + 1;
		while (i != posI) {
			if (i == 26)
				i = 0;
			if (i != posI) {
				letra = padreD.get(i);
				while (hijoDos.contains((letra))) {
					letra = padreD.get(hijoDos.indexOf(letra));
				}
				hijoDos.set(i, letra);
				i++;
			}
		}

		padreUno.setAlelos(hijoUno);
		padreDos.setAlelos(hijoDos);

		this.genCruzadoUno = padreUno;
		this.genCruzadoDos = padreDos;
	}
	public void poblacionFinal() {

		for (int i = 0; i < this.numElemACruzar; i++) {
			this.poblacion.set(this.poblacionACruzar.get(i).getId(), this.poblacionACruzar.get(i));
		}
	}
}
