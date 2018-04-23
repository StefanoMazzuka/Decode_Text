package seleccion;

import java.util.ArrayList;

import base.AlgoritmoGenetico;
import base.Cromosoma;

public abstract class Seleccion {
	
	public abstract ArrayList<Cromosoma> ejecutar(AlgoritmoGenetico ag);
	public abstract void desplazamiento(ArrayList<Cromosoma> pob);
}