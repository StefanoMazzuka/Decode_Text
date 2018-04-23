package cruce;

import java.util.ArrayList;
import java.util.Random;

import base.AlgoritmoGenetico;
import base.Cromosoma;
import base.Gen;

public class PMX extends Cruce {

	public PMX(double pCruce) {
		this.pCruce = pCruce;
	}

	public ArrayList<Cromosoma> cruzar(AlgoritmoGenetico ag) {

		this.agCopy = ag.copy();
		this.poblacion = this.agCopy.getPoblacion();
		this.lGen = this.poblacion.get(0).getlGen();
		this.poblacionACruzar = new ArrayList<Cromosoma>();
		this.genCruzadoUno = new Gen();
		this.genCruzadoDos = new Gen();

		cualCruza();

		for (int i = 0; i < numElemACruzar; i += 2) {
			cruzarCromosomas(poblacionACruzar.get(i), poblacionACruzar.get(i + 1));
		}

		poblacionFinal();
//		agCopy.setPoblacion(this.poblacion);
//		ag.setPoblacion(agCopy.getPoblacion());
		
		return this.poblacion;
	}
	public void cualCruza() {

		double pc = 0;
		for (int i = 0; i < this.agCopy.getlPoblacion(); i++) {
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
		Gen padreUGen = padreUno.getGen();
		Gen padreDGen = padreDos.getGen();

		// Elegimos los puntos de corte
		int pos1 = 0, pos2 = 0, pmin = 0, pmax = 0;
		Random r = new Random();
		pos1 = r.nextInt(26);// Sobre 26?
		pos2 = r.nextInt(26);

		// Colocamos correctamente pmin y pmax
		if (pos1 > pos2) {
			pmin = pos2;
			pmax = pos1;
		} else {
			pmin = pos1;
			pmax = pos2;

		}

		cruzarGenes(pmin, pmax, padreUGen.copy(), padreDGen.copy());

		padreUno.setGen(this.genCruzadoUno);
		padreDos.setGen(this.genCruzadoDos);

		//padreUno.calcularFenotipo();
 		padreUno.calcularFitness();

		//padreDos.calcularFenotipo();
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