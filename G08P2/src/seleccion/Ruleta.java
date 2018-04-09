package seleccion;

import java.util.ArrayList;
import java.util.Random;

import base.AlgoritmoGenetico;
import base.Cromosoma;

public class Ruleta extends Seleccion {	

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
		
		desplazamiento(pob);
		
		for (int i = 0; i < ag.getlPoblacion(); i++) {
			this.fitnessTotalPoblacion += this.fitnessDesplazado[i];
		}
		
		for (int i = 0; i < ag.getlPoblacion(); i++) {
			this.puntuacion[i] = this.fitnessDesplazado[i] / this.fitnessTotalPoblacion;
		}
		
		Random r = new Random();
		double probabilidad;
		double probAcumulada;
		for (int i = 0; i < ag.getlPoblacion(); i++) {
			probAcumulada = this.puntuacion[0];
			probabilidad = r.nextDouble() % 1;
			int j = 1;
			while (j < ag.getlPoblacion() && probabilidad > probAcumulada) {
				probAcumulada += this.puntuacion[j];
				j++;
			}
			pobSeleccionada.add(pob.get(j - 1).copy());
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
