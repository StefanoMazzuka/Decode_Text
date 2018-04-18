package base;

import java.util.ArrayList;

import cruce.UnPunto;
import funciones.Decode;
import mutacion.Mutacion;
import seleccion.FactoriaSeleccion;
import seleccion.Seleccion;

import java.util.Arrays;
import java.util.HashMap;

import GestionArchivos.Leer;

public class AlgoritmoGenetico {

	private ArrayList<Cromosoma> poblacion;
	private int lPoblacion;
	private double precision;
	private double fitnessMejorAbsoluto;
	private double fitnessMejor;
	private double media;
	private double[] listaFitnessMejorAbsoluto;
	private double[] listaFitnessMejor;
	private double[] listaMedias;
	private int lCromosoma;
	private double porcentajeCruce;
	private double porcentajeMutacion;
	private int numeroGeneraciones;
	private boolean elitista;
	private double porcentajeEli;
	private int numElegidosEli;
	private ArrayList<Cromosoma> poblacionEli;
	private int tipoSeleccion;
	private boolean maximizar;
	private String nombreArchivo;
	private String textoMejor;
	private HashMap<String, Double> frecuenciaMonogramas;
	private HashMap<String, Double> frecuenciaBigramas;
	private HashMap<String, Double> frecuenciaTrigramas;

	public AlgoritmoGenetico(int lPoblacion, double precision, double porcentajeCruce, 
			double porcentajeMutacion, int numeroGeneraciones, boolean elitista, 
			int tipoSeleccion, String nombreArchivo, HashMap<String, Double> frecuenciaMonogramas,
			HashMap<String, Double> frecuenciaBigramas, HashMap<String, Double> frecuenciaTrigramas) {
		this.lPoblacion = lPoblacion;
		this.poblacion = new ArrayList<Cromosoma>(this.lPoblacion);
		this.precision = precision;
		this.porcentajeCruce = porcentajeCruce;
		this.porcentajeMutacion = porcentajeMutacion;
		this.numeroGeneraciones = numeroGeneraciones;
		this.listaFitnessMejorAbsoluto = new double[this.numeroGeneraciones];
		Arrays.fill(this.listaFitnessMejorAbsoluto, 0.0);
		this.listaFitnessMejor = new double[this.numeroGeneraciones];
		Arrays.fill(this.listaFitnessMejor, 0.0);
		this.listaMedias = new double[this.numeroGeneraciones];
		Arrays.fill(this.listaFitnessMejorAbsoluto, 0.0);
		this.elitista = elitista;
		this.tipoSeleccion = tipoSeleccion;
		this.nombreArchivo = nombreArchivo;
		this.frecuenciaMonogramas = frecuenciaMonogramas;
		this.frecuenciaBigramas = frecuenciaBigramas;
		this.frecuenciaTrigramas = frecuenciaTrigramas;
	}

	public void ejecutar() {

		crearPoblacionDecode(this.nombreArchivo);

		// Creo una factoria de seleccion para elegir el metodo de seleccion que eloja el combo.

		Seleccion s = FactoriaSeleccion.getSeleccion("Ruleta");
		if (this.tipoSeleccion == 1) s = FactoriaSeleccion.getSeleccion("Torneo");
		else if (this.tipoSeleccion == 2) s = FactoriaSeleccion.getSeleccion("Estocastico");

		UnPunto p = new UnPunto(this.porcentajeCruce);
		Mutacion m = new Mutacion(this.porcentajeMutacion);

		if (this.elitista) {
			this.numElegidosEli = (int) Math.round((this.porcentajeEli * this.lPoblacion));
			this.poblacionEli = new ArrayList<Cromosoma>(this.numElegidosEli);
			ordenar();
			Cromosoma c;
			for (int i = 0; i < this.numElegidosEli; i++) {
				c = this.poblacion.get(i).copy();
				this.poblacionEli.add(i, c);
			}			
			ordenar();
		}

		for (int i = 0; i < this.numeroGeneraciones; i++) {

			s.ejecutar(this);
			p.cruzar(this);
			m.mutar(this);

			if (this.elitista) {
				insertarPobEli();
				seleccionarEli();
			}	

			this.media = this.calcularMediaGeneracion();
			this.fitnessMejor = this.calcularFitnessMejor();
			this.listaMedias[i] = media;
			this.listaFitnessMejor[i] = this.fitnessMejor;
			this.listaFitnessMejorAbsoluto[i] = this.fitnessMejorAbsoluto;	
		}
		ordenar();
		this.textoMejor = this.poblacion.get(this.lPoblacion - 1).getTexto();
	}
	public void crearPoblacionDecode(String nombreArchivo) {
		Decode d;
		for (int i = 0; i < lPoblacion; i++) {
			d = new Decode(nombreArchivo.toCharArray(),
					this.frecuenciaMonogramas,
					this.frecuenciaBigramas,
					this.frecuenciaTrigramas);
			d.setId(i);
			this.poblacion.add(i, d);
		}
	}
	public double calcularFitnessMejor() {
		double fitnessMejor;
		double fitness;

		if (this.maximizar) {
			fitnessMejor = 0;
			for (int i = 0; i < this.lPoblacion; i++) {
				fitness = this.poblacion.get(i).getFitness();
				if (fitnessMejor < fitness)
					fitnessMejor = fitness;
			}		
			
			if (fitnessMejorAbsoluto < fitnessMejor)
			this.fitnessMejorAbsoluto = fitnessMejor;
		}

		else {
			fitnessMejor = 99999999;
			for (int i = 0; i < this.lPoblacion; i++) {
				fitness = this.poblacion.get(i).getFitness();
				if (fitnessMejor > fitness)
					fitnessMejor = fitness;
			}		
			
			if (fitnessMejorAbsoluto > fitnessMejor)
			this.fitnessMejorAbsoluto = fitnessMejor;
		}
		
		return fitnessMejor;
	}
	public double calcularMediaGeneracion() {
		double media = 0.00;
		double sumatorio = 0.00;
		double fitness;

		for (int i = 0; i < this.lPoblacion; i++) {
			fitness = this.poblacion.get(i).getFitness();
			sumatorio += fitness;
		}

		media = sumatorio / this.lPoblacion;

		return media;
	}
	public void seleccionarEli() {	
		Cromosoma c;
		for (int i = 0; i < this.numElegidosEli; i++) {
			c = this.poblacion.get(i).copy();
			this.poblacionEli.set(i, c);
		}

		ordenar();
	}
	public void insertarPobEli() {
		Cromosoma c;
		for (int i = 0; i < this.numElegidosEli; i++) {
			c = this.poblacionEli.get(i).copy();
			this.poblacion.set((this.lPoblacion - 1) - i, c);
		}

		ordenar();
	}
	public void ordenar() {
		Cromosoma c1;
		Cromosoma c2;
		
		if(this.maximizar) {
			for (int i = 0; i < this.lPoblacion - 1; i++) {
				for (int j = i; j < this.lPoblacion; j++) {
					if (this.poblacion.get(i).getFitness() <
							this.poblacion.get(j).getFitness()) {
						c1 = this.poblacion.get(i).copy();
						c2 = this.poblacion.get(j).copy();

						this.poblacion.set(i, c2);
						this.poblacion.set(j, c1);
					}
				}
			}
		}

		else {
			for (int i = 0; i < this.lPoblacion - 1; i++) {
				for (int j = i; j < this.lPoblacion; j++) {
					if (this.poblacion.get(i).getFitness() >
							this.poblacion.get(j).getFitness()) {
						c1 = this.poblacion.get(i).copy();
						c2 = this.poblacion.get(j).copy();

						this.poblacion.set(i, c2);
						this.poblacion.set(j, c1);
					}
				}
			}
		}
	}
	public AlgoritmoGenetico copy() {
		ArrayList<Cromosoma> poblacion = this.poblacion;
		int lPoblacion = this.lPoblacion;
		double precision = this.precision;
		double fitnessMejorAbsoluto = this.fitnessMejorAbsoluto;
		int lCromosoma = this.lCromosoma;
		double porcentajeCruce = this.porcentajeCruce;
		double porcentajeMutacion = this.porcentajeMutacion;
		int numeroGeneraciones = this.numeroGeneraciones;
		boolean elitista = this.elitista;
		int tipoSeleccion = this.tipoSeleccion;
		String nombreArchivo = this.nombreArchivo;
		HashMap<String, Double> frecuenciaMonogramas = new HashMap<String, Double>();
		HashMap<String, Double> frecuenciaBigramas = new HashMap<String, Double>();
		HashMap<String, Double> frecuenciaTrigramas = new HashMap<String, Double>();
		
		frecuenciaMonogramas = this.frecuenciaMonogramas;
		frecuenciaBigramas = this.frecuenciaBigramas;
		frecuenciaTrigramas = this.frecuenciaTrigramas;
		
		AlgoritmoGenetico ag = new AlgoritmoGenetico(lPoblacion, precision, porcentajeCruce, 
				porcentajeMutacion, numeroGeneraciones, elitista, 
				tipoSeleccion, nombreArchivo, frecuenciaMonogramas, 
				frecuenciaBigramas, frecuenciaTrigramas);

		ag.setPoblacion(poblacion);
		ag.setlPoblacion(lPoblacion);
		ag.setPrecision(precision);
		ag.setFitnessMejorAbsoluto(fitnessMejorAbsoluto);
		ag.setlCromosoma(lCromosoma);
		ag.setPorcentajeCruce(porcentajeCruce);
		ag.setPorcentajeMutacion(porcentajeMutacion);
		ag.setNumeroGeneraciones(numeroGeneraciones);
		ag.setTipoSeleccion(tipoSeleccion);

		return ag;
	}

	/*Getters and Setters*/
	public double getFitnessMejorAbsoluto() {
		return fitnessMejorAbsoluto;
	}
	public ArrayList<Cromosoma> getPoblacion() {
		return poblacion;
	}
	public void setPoblacion(ArrayList<Cromosoma> poblacion) {
		this.poblacion = poblacion;
	}
	public int getlPoblacion() {
		return lPoblacion;
	}
	public void setlPoblacion(int lPoblacion) {
		this.lPoblacion = lPoblacion;
	}
	public double getPrecision() {
		return precision;
	}
	public void setPrecision(double precision) {
		this.precision = precision;
	}
	public void setFitnessMejorAbsoluto(double fitnessMejorAbsoluto) {
		this.fitnessMejorAbsoluto = fitnessMejorAbsoluto;
	}
	public double getFitnessMejor() {
		return fitnessMejor;
	}
	public void setFitnessMejor(double fitnessMejor) {
		this.fitnessMejor = fitnessMejor;
	}
	public double[] getListaFitnessMejorAbsoluto() {
		return listaFitnessMejorAbsoluto;
	}
	public void setListaFitnessMejorAbsoluto(double[] listaFitnessMejorAbsoluto) {
		this.listaFitnessMejorAbsoluto = listaFitnessMejorAbsoluto;
	}
	public double[] getListaFitnessMejor() {
		return listaFitnessMejor;
	}
	public void setListaFitnessMejor(double[] listaFitnessMejor) {
		this.listaFitnessMejor = listaFitnessMejor;
	}
	public int getlCromosoma() {
		return lCromosoma;
	}
	public void setlCromosoma(int lCromosoma) {
		this.lCromosoma = lCromosoma;
	}
	public double getPorcentajeCruce() {
		return porcentajeCruce;
	}
	public void setPorcentajeCruce(double porcentajeCruce) {
		this.porcentajeCruce = porcentajeCruce;
	}
	public double getPorcentajeMutacion() {
		return porcentajeMutacion;
	}
	public void setPorcentajeMutacion(double porcentajeMutacion) {
		this.porcentajeMutacion = porcentajeMutacion;
	}
	public int getNumeroGeneraciones() {
		return numeroGeneraciones;
	}
	public void setNumeroGeneraciones(int numeroGeneraciones) {
		this.numeroGeneraciones = numeroGeneraciones;
	}
	public double[] getListaMedias() {
		return listaMedias;
	}
	public void setListaMedias(double[] listaMedias) {
		this.listaMedias = listaMedias;
	}
	public boolean isElitista() {
		return elitista;
	}
	public void setElitista(boolean elitista) {
		this.elitista = elitista;
	}
	public void setTipoSeleccion(int tipoSeleccion) {
		this.tipoSeleccion = tipoSeleccion;
	}
	public String getTextoMejor() {
		return this.textoMejor;
	}
}
