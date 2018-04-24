package mutacion;

import java.util.ArrayList;

import base.Cromosoma;

public abstract class Mutacion {
	public abstract ArrayList<Cromosoma> mutar(ArrayList<Cromosoma> poblacion);
}