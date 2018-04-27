package Mutacion;

import java.util.ArrayList;

import Base.Cromosoma;

public abstract class Mutacion {
	public abstract ArrayList<Cromosoma> mutar(ArrayList<Cromosoma> poblacion);
}