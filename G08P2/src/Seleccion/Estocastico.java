package Seleccion;

import java.util.ArrayList;
import java.util.Random;

import Base.Cromosoma;

public class Estocastico extends Seleccion {

	private double[] puntuacion;
	private double[] fitnessDesplazado;
	private double fitnessTotalPoblacion;
	ArrayList<Cromosoma> poblacion;

	@Override
	public ArrayList<Cromosoma> ejecutar(ArrayList<Cromosoma> poblacion, int numGeneraciones) {
		// TODO Auto-generated method stub
		this.poblacion = poblacion;
		this.puntuacion = new double[this.poblacion.size()];
		this.fitnessDesplazado = new double[this.poblacion.size()];
		this.fitnessTotalPoblacion = 0;
		
		ArrayList<Cromosoma> pobSeleccionada = new ArrayList<Cromosoma>();
		
		double N = this.poblacion.size();
		double distMarcas = 1 / N;
		double primeraMarca = 0.0;
		double[] arrayDeMarcas = new double[this.poblacion.size()];
		double probAcumulada;
		
		Random r = new Random();
		
		/*
		 * Calculamos el fitnes total
		 */
		for (int i = 0; i < this.poblacion.size(); i++) {
			this.fitnessTotalPoblacion += this.poblacion.get(i).getFitness();
		}
		
		/*
		 * Calculamos el fitness de cada idividuo entre el fitness total
		 */
		for (int i = 0; i < this.poblacion.size(); i++) {
			this.puntuacion[i] = this.poblacion.get(i).getFitness() / this.fitnessTotalPoblacion;
		}
		
		/*
		 * Calculamos el empiece de la primera marca
		 */
		primeraMarca = r.nextDouble() % distMarcas;
		arrayDeMarcas[0] = primeraMarca;
		
		/*
		 * Calculamos el resto de marcas
		 */
		for (int i = 1; i < this.poblacion.size(); i++) {
			arrayDeMarcas[i] = arrayDeMarcas[i-1] + distMarcas;
		}
		
		/*
		 * Comprobamos a que elemento pertenece la marca y lo añadimos a la poblacion
		 */
		probAcumulada = this.puntuacion[0];
		
		for (int j = 0; j < arrayDeMarcas.length; j++) {
			probAcumulada = this.puntuacion[0];
			int k = 1;
			while (k < this.poblacion.size() && arrayDeMarcas[j] > probAcumulada) {
				probAcumulada += this.puntuacion[k];
				k++;
			}
			pobSeleccionada.add(this.poblacion.get(k - 1).copy());
		}
		
		return pobSeleccionada;
	}
	public void desplazamiento(ArrayList<Cromosoma> poblacion) {
		double fitnessMejor = 0;
		for (int i = 0; i < poblacion.size(); i++) {
			if(fitnessMejor < poblacion.get(i).getFitness())
				fitnessMejor = poblacion.get(i).getFitness();
		}
		fitnessMejor = fitnessMejor * 1.05;
		for (int i = 0; i < poblacion.size(); i++) {
			this.fitnessDesplazado[i] = fitnessMejor - poblacion.get(i).getFitness();
		}
	}
}