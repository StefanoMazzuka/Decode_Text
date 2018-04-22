package cruce;

import java.util.ArrayList;

import base.AlgoritmoGenetico;
import base.Cromosoma;
import base.Gen;

public abstract class Cruce {
	protected AlgoritmoGenetico agCopy;
	protected double pCruce;
	protected int lGen;
	protected ArrayList<Cromosoma> poblacion;
	protected ArrayList<Cromosoma> poblacionACruzar;
	protected Gen genCruzadoUno;
	protected Gen genCruzadoDos;
	protected int numElemACruzar;
	
	
	public abstract void cruzar(AlgoritmoGenetico ag);	
}
