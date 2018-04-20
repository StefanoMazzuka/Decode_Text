package cruce;

import java.util.ArrayList;
import java.util.Random;

import base.AlgoritmoGenetico;
import base.Cromosoma;
import base.Gen;

public class PMX extends Cruce {

	// @Override
	// public void cruzar(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1,
	// Cromosoma hijo2) {
	// int puntoA, puntoB;
	// ArrayList<Integer> al1 = new ArrayList<Integer>();
	// ArrayList<Integer> al2 = new ArrayList<Integer>();
	// ArrayList<Integer> p1 = new ArrayList<Integer>();
	// ArrayList<Integer> p2 = new ArrayList<Integer>();
	// for(int i = 0; i < padre1.getNGenes(); i++)
	// {
	// al1.add(i, -1);
	// al2.add(i, -1);
	// p1.add(i, padre1.getGenes()[i].getAlelo());
	// p2.add(i, padre2.getGenes()[i].getAlelo());
	// }
	// // Calculo de puntos de cruce
	// Random rnd = new Random();
	// puntoA = rnd.nextInt(padre1.getNGenes());
	// puntoB = rnd.nextInt(padre1.getNGenes() - puntoA);
	// puntoB += puntoA+1;
	// for(int i = puntoA; i < puntoB; i++)
	// {
	// // Hijo 1
	// al1.set(i, padre2.getGenes()[i].getAlelo());
	// hijo1.getGenes()[i].setAlelo(padre2.getGenes()[i].getAlelo());
	// // Hijo 2
	// al2.set(i, padre1.getGenes()[i].getAlelo());
	// hijo2.getGenes()[i].setAlelo(padre1.getGenes()[i].getAlelo());
	// }
	// int longCadena = puntoB - puntoA;
	// int it = padre1.getNGenes() - longCadena;
	// int pos = puntoB;
	// for(int i = 0; i < it; i++)
	// {
	// if(pos >= padre1.getNGenes()) pos = 0;
	// // Hijo 1
	// if(!al1.contains(padre1.getGenes()[pos].getAlelo()))
	// {
	// al1.set(pos, padre1.getGenes()[pos].getAlelo());
	// hijo1.getGenes()[pos].setAlelo(padre1.getGenes()[pos].getAlelo());
	// }
	// else
	// {
	// int index = p2.indexOf(padre1.getGenes()[pos].getAlelo());
	// if(!al1.contains(padre1.getGenes()[index].getAlelo()))
	// {
	// hijo1.getGenes()[pos].setAlelo(padre1.getGenes()[index].getAlelo());
	// al1.set(pos, padre1.getGenes()[index].getAlelo());
	// }
	// else
	// {
	// int n = rnd.nextInt(padre1.getNGenes()) + 1;
	// while(al1.contains(n)) n = rnd.nextInt(padre1.getNGenes()) + 1;
	// hijo1.getGenes()[pos].setAlelo(n);
	// al1.set(pos, n);
	// }
	// }
	// // Hijo 2
	// if(!al2.contains(padre2.getGenes()[pos].getAlelo()))
	// {
	// al2.set(pos, padre2.getGenes()[pos].getAlelo());
	// hijo2.getGenes()[pos].setAlelo(padre2.getGenes()[pos].getAlelo());
	// }
	// else
	// {
	// int index = p1.indexOf(padre2.getGenes()[pos].getAlelo());
	// if(!al2.contains(padre2.getGenes()[index].getAlelo()))
	// {
	// hijo2.getGenes()[pos].setAlelo(padre2.getGenes()[index].getAlelo());
	// al2.set(pos, padre2.getGenes()[index].getAlelo());
	// }
	// else
	// {
	// int n = rnd.nextInt(padre1.getNGenes()) + 1;
	// while(al2.contains(n)) n = rnd.nextInt(padre1.getNGenes()) + 1;
	// hijo2.getGenes()[pos].setAlelo(n);
	// al2.set(pos, n);
	// }
	// }
	// pos++;
	// }
	// hijo1.setFitness_bruto(hijo1.evalua());
	// hijo2.setFitness_bruto(hijo2.evalua());
	// }

	public PMX(double pCruce) {
		this.pCruce = pCruce;
	}

	@Override
	public void cruzar(AlgoritmoGenetico ag) {

		this.agCopy = ag.copy();
		this.poblacion = this.agCopy.getPoblacion();
		this.lGen = this.poblacion.get(0).getlGen();
		this.poblacionACruzar = new ArrayList<Cromosoma>();
		this.genCruzadoUno = new Gen();
		this.genCruzadoDos = new Gen();

		cualCruza();

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
			this.poblacionACruzar.remove(this.poblacion.get(0)); // Era esta linea... el error.
			this.numElemACruzar--;
		}
	}

	public void cruzarCromosomas(Cromosoma padreUno, Cromosoma padreDos) {
		Gen padreUGen = padreUno.getGen();
		Gen padreDGen = padreDos.getGen();

		// Elegimos los puntos de corte
		int pos1 = 0, pos2 = 0, pmin = 0, pmax = 0;
		Random r = new Random();
		pos1 = r.nextInt(25);// Sobre 26?
		pos2 = r.nextInt(25);

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

		padreUno.calcularFenotipo();
		padreUno.calcularFitness();

		padreDos.calcularFenotipo();
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
			hijoUno.add((char) 0);
			hijoDos.add((char) 0);

		}

		// Hago el primer cruce entre los dos puntos de corte
		for (int i = posI; i <= posJ; i++) {
			hijoUno.set(i, padreD.get(i));
			hijoDos.set(i, padreU.get(i));
		}

		// HIJO1
		char letra;
		for (int i = posJ + 1; i < posI; i++) {
			letra = padreU.get(i);
			while (hijoUno.contains((letra))) {
				letra = padreU.get(hijoUno.indexOf(letra));
			}
			hijoUno.add(i, letra);
		}

		// HIJO2
		for (int i = posJ + 1; i < posI; i++) {
			letra = padreD.get(i);
			while (hijoDos.contains((letra))) {
				letra = padreD.get(hijoDos.indexOf(letra));
			}
			hijoDos.add(i, letra);
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