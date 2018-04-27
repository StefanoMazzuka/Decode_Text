package Base;

import java.util.ArrayList;

import Cruce.*;
import Funciones.*;
import Mutacion.*;
import Seleccion.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class AlgoritmoGenetico {

	private ArrayList<Cromosoma> poblacion;
	private int lPoblacion;
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
	private int tipoCruce;
	private int tipoMutacion;
	private String texto;
	private String textoMejor;
	private HashMap<String, Double> frecuenciaMonogramas;
	private HashMap<String, Double> frecuenciaBigramas;
	private HashMap<String, Double> frecuenciaTrigramas;
	private String genMejor;

	public AlgoritmoGenetico(int lPoblacion, double porcentajeCruce, 
			double porcentajeMutacion, int numeroGeneraciones, boolean elitista, 
			int tipoSeleccion, int tipoCruce, int tipoMutacion, String texto, 
			HashMap<String, Double> frecuenciaMonogramas,
			HashMap<String, Double> frecuenciaBigramas, 
			HashMap<String, Double> frecuenciaTrigramas) {
		this.fitnessMejorAbsoluto = Double.MAX_VALUE;
		this.lPoblacion = lPoblacion;
		this.poblacion = new ArrayList<Cromosoma>(this.lPoblacion);
		this.porcentajeCruce = porcentajeCruce;
		this.porcentajeMutacion = porcentajeMutacion;
		this.porcentajeEli = 0.05;
		this.numeroGeneraciones = numeroGeneraciones;
		this.listaFitnessMejorAbsoluto = new double[this.numeroGeneraciones];
		Arrays.fill(this.listaFitnessMejorAbsoluto, 0.0);
		this.listaFitnessMejor = new double[this.numeroGeneraciones];
		Arrays.fill(this.listaFitnessMejor, 0.0);
		this.listaMedias = new double[this.numeroGeneraciones];
		Arrays.fill(this.listaFitnessMejorAbsoluto, 0.0);
		this.elitista = elitista;
		this.tipoSeleccion = tipoSeleccion;
		this.tipoCruce = tipoCruce;
		this.tipoMutacion = tipoMutacion;
		this.texto = pasarAMinusculas(texto.toCharArray());
		this.frecuenciaMonogramas = frecuenciaMonogramas;
		this.frecuenciaBigramas = frecuenciaBigramas;
		this.frecuenciaTrigramas = frecuenciaTrigramas;
	}

	public void ejecutar() {

		crearPoblacionDecode(this.texto);

		// Creo una factoria de seleccion para elegir el metodo de seleccion que eloja el combo.

		Seleccion seleccion = new Ruleta();
		if (this.tipoSeleccion == 1) seleccion = new Torneo();
		else if (this.tipoSeleccion == 2) seleccion = new Estocastico();
		
		Cruce cruce = new OX(this.porcentajeCruce);
		if (this.tipoCruce == 1) cruce = new PMX(this.porcentajeCruce);
		else if (this.tipoCruce == 2) cruce = new Ordinal(this.porcentajeCruce);
		
		Mutacion mutacion = new Insercion(this.porcentajeMutacion);
		if (this.tipoMutacion == 1) mutacion = new Intercambio(this.porcentajeMutacion);
		else if (this.tipoMutacion == 2) mutacion = new Inversion(this.porcentajeMutacion);
		else if (this.tipoMutacion == 3) mutacion = new Heuristica(this.porcentajeMutacion);

		if (this.elitista) {
			this.numElegidosEli = (int) Math.round((this.porcentajeEli * this.lPoblacion));
			this.poblacionEli = new ArrayList<Cromosoma>(this.numElegidosEli);
			ordenar();
			Cromosoma c;
			for (int i = 0; i < this.numElegidosEli; i++) {
				c = this.poblacion.get(i).copy();
				this.poblacionEli.add(i, c);
			}
		}

		for (int i = 0; i < this.numeroGeneraciones; i++) {

			this.poblacion = seleccion.ejecutar(this.poblacion, this.numeroGeneraciones);
			Collections.shuffle(this.poblacion);
			this.poblacion = cruce.cruzar(this.poblacion);
			this.poblacion = mutacion.mutar(this.poblacion);
			Collections.shuffle(this.poblacion);

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
		this.textoMejor = this.poblacion.get(0).getTextoCromosoma();
		this.genMejor = this.poblacion.get(0).getGen().concatenarAlelos();
	}
	private String pasarAMinusculas(char[] texto) {
		for (int i = 0; i < texto.length; i++) {
			texto[i] = Character.toLowerCase(texto[i]);
		}
		return this.texto = new String(texto);
	}
	public void crearPoblacionDecode(String texto) {
		Decode d;
		for (int i = 0; i < lPoblacion; i++) {
			d = new Decode(texto,
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
		
		fitnessMejor = Double.MAX_VALUE;
		for (int i = 0; i < this.lPoblacion; i++) {
			fitness = this.poblacion.get(i).getFitness();
			if (fitnessMejor > fitness)
				fitnessMejor = fitness;
		}		

		if (fitnessMejorAbsoluto > fitnessMejor)
			this.fitnessMejorAbsoluto = fitnessMejor;

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
		ordenar();
		Cromosoma c;
		for (int i = 0; i < this.numElegidosEli; i++) {
			c = this.poblacion.get(i).copy();
			this.poblacionEli.set(i, c);
		}
	}
	public void insertarPobEli() {
		Cromosoma c;
		for (int i = 0; i < this.numElegidosEli; i++) {
			c = this.poblacionEli.get(i).copy();
			this.poblacion.set((this.lPoblacion - 1) - i, c);
		}
	}
	public void ordenar() {
		Cromosoma c1;
		Cromosoma c2;

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
//	public AlgoritmoGenetico copy() {
//		ArrayList<Cromosoma> poblacion = this.poblacion;
//		int lPoblacion = this.lPoblacion;
//		double precision = this.precision;
//		double fitnessMejorAbsoluto = this.fitnessMejorAbsoluto;
//		int lCromosoma = this.lCromosoma;
//		double porcentajeCruce = this.porcentajeCruce;
//		double porcentajeMutacion = this.porcentajeMutacion;
//		int numeroGeneraciones = this.numeroGeneraciones;
//		boolean elitista = this.elitista;
//		int tipoSeleccion = this.tipoSeleccion;
//		int tipoCruce = this.tipoCruce;
//		int tipoMutacion = this.tipoMutacion;
//		String texto = this.texto;
//		HashMap<String, Double> frecuenciaMonogramas = new HashMap<String, Double>();
//		HashMap<String, Double> frecuenciaBigramas = new HashMap<String, Double>();
//		HashMap<String, Double> frecuenciaTrigramas = new HashMap<String, Double>();
//
//		frecuenciaMonogramas = this.frecuenciaMonogramas;
//		frecuenciaBigramas = this.frecuenciaBigramas;
//		frecuenciaTrigramas = this.frecuenciaTrigramas;
//
//		AlgoritmoGenetico ag = new AlgoritmoGenetico(lPoblacion, precision, porcentajeCruce, 
//				porcentajeMutacion, numeroGeneraciones, elitista, tipoSeleccion, tipoCruce,
//				tipoMutacion, texto, frecuenciaMonogramas, frecuenciaBigramas, frecuenciaTrigramas);
//
//		ag.setPoblacion(poblacion);
//		ag.setlPoblacion(lPoblacion);
//		ag.setPrecision(precision);
//		ag.setFitnessMejorAbsoluto(fitnessMejorAbsoluto);
//		ag.setlCromosoma(lCromosoma);
//		ag.setPorcentajeCruce(porcentajeCruce);
//		ag.setPorcentajeMutacion(porcentajeMutacion);
//		ag.setNumeroGeneraciones(numeroGeneraciones);
//		ag.setTipoSeleccion(tipoSeleccion);
//
//		return ag;
//	}

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
	public String getGenMejor() {
		return genMejor;
	}
	public void setGenMejor(String genMejor) {
		this.genMejor = genMejor;
	}
}