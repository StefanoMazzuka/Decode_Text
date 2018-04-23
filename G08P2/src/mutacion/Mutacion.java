package mutacion;

import java.util.ArrayList;

import base.AlgoritmoGenetico;
import base.Cromosoma;

public abstract class Mutacion {
	public abstract ArrayList<Cromosoma> mutar(AlgoritmoGenetico ag);
}