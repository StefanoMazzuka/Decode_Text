package Seleccion;

import java.util.ArrayList;
import java.util.Random;

import Base.Cromosoma;

public class Torneo extends Seleccion {

	private double[] fitnessDesplazado;
	ArrayList<Cromosoma> poblacion;
	ArrayList<Cromosoma> poblacionTrio;
	ArrayList<Cromosoma> poblacionSeleccionada;

	@Override
	public ArrayList<Cromosoma> ejecutar(ArrayList<Cromosoma> poblacion, int numGeneraciones) {
		// TODO Auto-generated method stub
		this.poblacion = poblacion;
		this.poblacionTrio = new ArrayList<Cromosoma>();
		this.poblacionSeleccionada = new ArrayList<Cromosoma>();
		this.fitnessDesplazado = new double[this.poblacion.size()];
		
		double mejor = 0.0;
		Cromosoma mejorCromosoma = null;
		Random r = new Random();
		int cualToca = 0;
		
		/*
		 * Recorremos el tamaño de la poblacion. Añadimos al pobTrio el trio de cromosomas y comprobamos
		 * cual es el mejor de esos tres para añadirlo a la pobSeleccionada. 
		 */
		for (int j = 0; j < this.poblacion.size(); j++) {	
			/*
			 * Elegimos el trio al azar
			 */
			mejor = Double.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                cualToca = r.nextInt(this.poblacion.size()) ;
                this.poblacionTrio.add(this.poblacion.get(cualToca).copy());

                /*
                 * Comprobamos cual es el mejor elemento del trio
                 */
                //desplazamiento(pobTrio);

                if (mejor > this.poblacionTrio.get(i).fitness) {
                    mejor = this.poblacionTrio.get(i).fitness;
                    mejorCromosoma = this.poblacionTrio.get(i).copy();
                }
            }
			
			/*
			 * Añadimos el cromosoma a la poblacion seleccionada
			 */
    		this.poblacionSeleccionada.add(mejorCromosoma);
			
			/*
			 * Limpiamos el array de pobTrio
			 */
			for (int i = 0; i < 3; i++) {
				this.poblacionTrio.remove(0);		
			}
			
			/*
			 * Seteamos la poblacion orginal
			 */	
		}
		return this.poblacionSeleccionada;
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