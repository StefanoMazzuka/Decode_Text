package Seleccion;

import java.util.ArrayList;
import java.util.Random;

import Base.Cromosoma;

public class Ruleta extends Seleccion {	

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
		
		desplazamiento(poblacion);
		
		for (int i = 0; i < this.poblacion.size(); i++) {
			this.fitnessTotalPoblacion += this.fitnessDesplazado[i];
		}
		
		for (int i = 0; i < this.poblacion.size(); i++) {
			this.puntuacion[i] = this.fitnessDesplazado[i] / this.fitnessTotalPoblacion;
		}
		
		Random r = new Random();
		double probabilidad;
		double probAcumulada;
		for (int i = 0; i < this.poblacion.size(); i++) {
			probAcumulada = this.puntuacion[0];
			probabilidad = r.nextDouble() % 1;
			int j = 1;
			while (j < this.poblacion.size() && probabilidad > probAcumulada) {
				probAcumulada += this.puntuacion[j];
				j++;
			}
			pobSeleccionada.add(this.poblacion.get(j - 1).copy());
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