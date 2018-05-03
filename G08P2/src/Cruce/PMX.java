package Cruce;

import java.util.ArrayList;
import java.util.Random;

import Base.Cromosoma;
import Base.Gen;

public class PMX extends Cruce {

	private double pCruce;
	private ArrayList<Integer> posicionesPadres;
	private ArrayList<Cromosoma> poblacion;
	private ArrayList<Cromosoma> poblacionFinal;
	private ArrayList<Cromosoma> poblacionACruzar;
	private Gen genCruzadoUno;
	private Gen genCruzadoDos;
	
	public PMX(double pCruce) {
		this.pCruce = pCruce;
	}

	public ArrayList<Cromosoma> cruzar(ArrayList<Cromosoma> poblacion) {

		this.posicionesPadres = new ArrayList<Integer>();
		this.poblacion = poblacion;
		this.poblacionFinal = new ArrayList<Cromosoma>();
		this.poblacionACruzar = new ArrayList<Cromosoma>();
		this.genCruzadoUno = new Gen();
		this.genCruzadoDos = new Gen();

		cualCruza();

		for (int i = 0; i < poblacionACruzar.size(); i += 2) {
			cruzarCromosomas(i, i + 1, poblacionACruzar.get(i), poblacionACruzar.get(i + 1));
		}

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
		Gen padreUGen = padreUno.getGen();
		Gen padreDGen = padreDos.getGen();

		// Elegimos los puntos de corte
		int puntoCorte1 = 0, puntoCorte2 = 0;
		Random r = new Random();
		puntoCorte1 = r.nextInt(26);
		puntoCorte2 = r.nextInt(26);

		// Colocamos correctamente 
		int aux;
		if (puntoCorte1 > puntoCorte2) {
			aux = puntoCorte2;
			puntoCorte2 = puntoCorte1;
			puntoCorte1 = aux;
		}

		cruzarGenes(puntoCorte1, puntoCorte2, padreUGen.copy(), padreDGen.copy());

		padreUno.setGen(this.genCruzadoUno);
		padreDos.setGen(this.genCruzadoDos);

 		padreUno.calcularFitness();
		padreDos.calcularFitness();
		
		this.poblacionACruzar.set(pos1, padreUno);
		this.poblacionACruzar.set(pos2, padreDos);
	}
	public void cruzarGenes(int puntoCorte1, int puntoCorte2, Gen padreUno, Gen padreDos) {
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
		for (int i = puntoCorte1; i <= puntoCorte2; i++) {
			hijoUno.set(i, padreD.get(i));
			hijoDos.set(i, padreU.get(i));
		}

		// HIJO1
		char letra;
		int i = puntoCorte2 + 1;
		while (i != puntoCorte1) {
			if (i == 26)
				i = 0;
			if (i != puntoCorte1) {
				letra = padreU.get(i);
				while (hijoUno.contains((letra))) {
					letra = padreU.get(hijoUno.indexOf(letra));
				}
				hijoUno.set(i, letra);
				i++;
			}
		}

		// HIJO2
		i = puntoCorte2 + 1;
		while (i != puntoCorte1) {
			if (i == 26)
				i = 0;
			if (i != puntoCorte1) {
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