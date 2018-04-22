package mutacion;

import java.util.ArrayList;

import base.AlgoritmoGenetico;
import base.Cromosoma;

public class Intercambio extends Mutacion {
	private double probMutacion;
	private AlgoritmoGenetico agCopy;
	ArrayList<Cromosoma> poblacion;
	private int lGen;
	private int lPoblacion;
	
	public Intercambio(double probMutacion) {
		this.probMutacion = probMutacion;
		this.lGen = 26;
	}
	
	public void mutar(AlgoritmoGenetico ag) {
	
	}
}
