package seleccion;

import java.util.ArrayList;
import java.util.Random;

import base.AlgoritmoGenetico;
import base.Cromosoma;

public class Estocastico extends Seleccion {

	private double[] puntuacion;
	private double[] fitnessDesplazado;
	private double fitnessTotalPoblacion;

	@Override
	public void ejecutar(AlgoritmoGenetico ag) {
		// TODO Auto-generated method stub
		this.puntuacion = new double[ag.getlPoblacion()];
		this.fitnessDesplazado = new double[ag.getlPoblacion()];
		this.fitnessTotalPoblacion = 0;
		
		ArrayList<Cromosoma> pob = ag.getPoblacion();
		ArrayList<Cromosoma> pobSeleccionada = new ArrayList<Cromosoma>();
		
		double N = pob.size();
		double distMarcas = 1 / N;
		double primeraMarca = 0.0;
		double[] arrayDeMarcas = new double[pob.size()];
		double probAcumulada;
		
		Random r = new Random();
		
		/*
		 * Calculamos el fitnes total
		 */
		for (int i = 0; i < ag.getlPoblacion(); i++) {
			this.fitnessTotalPoblacion += pob.get(i).getFitness();
		}
		
		/*
		 * Calculamos el fitness de cada idividuo entre el fitness total
		 */
		for (int i = 0; i < ag.getlPoblacion(); i++) {
			this.puntuacion[i] = pob.get(i).getFitness() / this.fitnessTotalPoblacion;
		}
		
		/*
		 * Calculamos el empiece de la primera marca
		 */
		primeraMarca = r.nextDouble() % distMarcas;
		arrayDeMarcas[0] = primeraMarca;
		
		/*
		 * Calculamos el resto de marcas
		 */
		for (int i = 1; i < pob.size(); i++) {
			arrayDeMarcas[i] = arrayDeMarcas[i-1] + distMarcas;
		}
		
		/*
		 * Comprobamos a que elemento pertenece la marca y lo añadimos a la poblacion
		 */
		probAcumulada = this.puntuacion[0];
		
		for (int j = 0; j < arrayDeMarcas.length; j++) {
			probAcumulada = this.puntuacion[0];
			int k = 1;
			while (k < ag.getlPoblacion() && arrayDeMarcas[j] > probAcumulada) {
				probAcumulada += this.puntuacion[k];
				k++;
			}
			pobSeleccionada.add(pob.get(k - 1).copy());
		}
		
		ag.setPoblacion(pobSeleccionada);
	}
	public void desplazamiento(ArrayList<Cromosoma> pob) {
		double fitnessMejor = 0;
		for (int i = 0; i < pob.size(); i++) {
			if(fitnessMejor < pob.get(i).getFitness())
				fitnessMejor = pob.get(i).getFitness();
		}
		fitnessMejor = fitnessMejor * 1.05;
		for (int i = 0; i < pob.size(); i++) {
			this.fitnessDesplazado[i] = fitnessMejor - pob.get(i).getFitness();
		}
	}
}