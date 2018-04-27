package Cruce;

import java.util.ArrayList;
import java.util.Random;

import Base.Cromosoma;
import Base.Gen;

public class OX extends Cruce {

	int numElemACruzar;
	double pCruce;
	ArrayList<Cromosoma> poblacion;
	ArrayList<Cromosoma> poblacionACruzar;
	Gen genCruzadoUno;
	Gen genCruzadoDos;
	int lGen = 26;
	
	public OX(double pCruce) {
		this.pCruce = pCruce;
	}

	public ArrayList<Cromosoma> cruzar(ArrayList<Cromosoma> poblacion) {

		this.poblacion = poblacion;
		this.poblacionACruzar = new ArrayList<Cromosoma>();
		this.genCruzadoUno = new Gen();
		this.genCruzadoDos = new Gen();

		cualCruza();

		for (int i = 0; i < numElemACruzar; i += 2) {
			cruzarCromosomas(poblacionACruzar.get(i), poblacionACruzar.get(i + 1));
		}

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
			this.poblacionACruzar.remove(this.poblacion.get(0)); // Era esta linea... el error.
			this.numElemACruzar--;
		}
	}
	public void cruzarCromosomas(Cromosoma padreUno, Cromosoma padreDos) {
		Gen padreUGen = padreUno.getGen();
		Gen padreDGen = padreDos.getGen();

		int pos1 = 0, pos2 = 0, pmin = 0, pmax = 0;
		
		Random r = new Random();
		pos1 = r.nextInt(26);
		pos2 = r.nextInt(26);

		// Colocamos correctamente pmin y pmax
		if (pos1 > pos2) {
			pmin = pos2;
			pmax = pos1;
		} else {
			pmin = pos1;
			pmax = pos2;

		}
		
		cruzarGenes(pmin,pmax, padreUGen.copy(), padreDGen.copy());

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
	public void cruzarGenes(int pmin,int pmax, Gen padreUno, Gen padreDos) {
		ArrayList<Character> hijoUno = new ArrayList<Character>();
		ArrayList<Character> hijoDos = new ArrayList<Character>();
		ArrayList<Character> padreU = padreUno.getAlelos();
		ArrayList<Character> padreD = padreDos.getAlelos();
		
		pmin = 1;
		
		pmax = 25;
		
		for (int i = 0; i < 26; i++) {
			hijoUno.add((char) 0);
			hijoDos.add((char) 0);
		}
		
		//Swap de la parte central
		for(int i = pmin; i <= pmax; i++) {
			hijoUno.set(i, padreD.get(i));
			hijoDos.set(i, padreU.get(i));
		}
		
		// Hijo uno
		int i = pmax + 1;
		int j = pmax + 1;
		while (i != pmin) {
			if (i == lGen)
				i = 0;
			if (j == lGen)
				j = 0;
			
			if (i != pmin) {
				while (j != pmax && hijoUno.contains(padreU.get(j))) {
					j++;
					if (j == lGen)
						j = 0;
				}
				
				hijoUno.set(i, padreU.get(j));
				j++;
				i++;
			}
		}
		
		i = pmax + 1;
		j = pmax + 1;
		while (i != pmin) {
			if (i == lGen)
				i = 0;
			if (j == lGen)
				j = 0;
			
			if (i != pmin) {
				while (j != pmax && hijoDos.contains(padreD.get(j))) {
					j++;
					if (j == lGen)
						j = 0;
				}
				
				hijoDos.set(i, padreD.get(j));
				j++;
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