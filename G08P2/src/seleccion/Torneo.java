package seleccion;

import java.util.ArrayList;
import java.util.Random;

import base.AlgoritmoGenetico;
import base.Cromosoma;

public class Torneo extends Seleccion {

	private double[] fitnessDesplazado;
	
	@Override
	public void ejecutar(AlgoritmoGenetico ag) {
		// TODO Auto-generated method stub
		ArrayList<Cromosoma> pob = ag.getPoblacion();
		ArrayList<Cromosoma> pobTrio = new ArrayList<Cromosoma>();
		ArrayList<Cromosoma> pobSeleccionada = new ArrayList<Cromosoma>();
		this.fitnessDesplazado = new double[ag.getlPoblacion()];
		
		double mejor = 0.0;
		Cromosoma mejorCromosoma = null;
		Random r = new Random();
		int cualToca = 0;
		
		/*
		 * Recorremos el tamaño de la poblacion. Añadimos al pobTrio el trio de cromosomas y comprobamos
		 * cual es el mejor de esos tres para añadirlo a la pobSeleccionada. 
		 */
		for (int j = 0; j < ag.getNumeroGeneraciones(); j++) {	
			/*
			 * Elegimos el trio al azar
			 */
			for (int i = 0; i < 3; i++) {
				cualToca = r.nextInt(ag.getlPoblacion()) ;
				pobTrio.add(pob.get(cualToca).copy());
				
				/*
				 * Comprobamos cual es el mejor elemento del trio
				 */
				desplazamiento(pobTrio);
				
				if (mejor < this.fitnessDesplazado[i]) {
					mejor = this.fitnessDesplazado[i];
					mejorCromosoma = pobTrio.get(i).copy();
				}
			}
			
			/*
			 * Añadimos el cromosoma a la poblacion seleccionada
			 */
			pobSeleccionada.add(mejorCromosoma);
			
			/*
			 * Limpiamos el array de pobTrio
			 */
			for (int i = 0; i < 3; i++) {
				pobTrio.remove(0);		
			}
			
			/*
			 * Seteamos la poblacion orginal
			 */
			ag.setPoblacion(pobSeleccionada);
		}	
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
