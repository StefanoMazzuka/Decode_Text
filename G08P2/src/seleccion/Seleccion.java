package seleccion;

import java.util.ArrayList;

import base.AlgoritmoGenetico;
import base.Cromosoma;

public abstract class Seleccion {
	
	public abstract void ejecutar(AlgoritmoGenetico ag);
	public abstract void desplazamiento(ArrayList<Cromosoma> pob);
}
