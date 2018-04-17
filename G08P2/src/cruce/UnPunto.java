package cruce;

import java.util.ArrayList;
import java.util.Random;

import base.AlgoritmoGenetico;
import base.Cromosoma;
import base.Gen;

public class UnPunto {

	private AlgoritmoGenetico agCopy;
	
	private double pCruce;
	private int lGen;
	private ArrayList<Cromosoma> poblacion;
	private ArrayList<Cromosoma> poblacionACruzar;
	private Gen genCruzadoUno;
	private Gen genCruzadoDos;
	private int numElemACruzar;
	
	public UnPunto(double pCruce) {
		this.pCruce = pCruce;
	}
	public void cruzar(AlgoritmoGenetico ag) {

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
		agCopy.setPoblacion(this.poblacion);
		ag.setPoblacion(agCopy.getPoblacion());
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
		
		int pos = 0;
		Random r = new Random();
		pos = r.nextInt(25); // Sobre 26?
		cruzarGenes(pos, padreUGen.copy(), padreDGen.copy());
			
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
	public void cruzarGenes(int pos, Gen padreUno, Gen padreDos) {	
		ArrayList<Character> hijoUno = new ArrayList<Character>();
		ArrayList<Character> hijoDos = new ArrayList<Character>();
		ArrayList<Character> padreU = padreUno.getAlelos();
		ArrayList<Character> padreD = padreDos.getAlelos();

		for (int i = 0; i < pos; i++) {
			hijoUno.add(i, padreU.get(i));
			hijoDos.add(i, padreD.get(i));
		}

		for (int j = pos; j < this.lGen; j++) {			
			hijoUno.add(j, padreD.get(j));
			hijoDos.add(j, padreU.get(j));
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

