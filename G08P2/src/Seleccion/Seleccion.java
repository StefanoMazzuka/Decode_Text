package Seleccion;

import java.util.ArrayList;

import Base.Cromosoma;

public abstract class Seleccion {
	public abstract ArrayList<Cromosoma> ejecutar(ArrayList<Cromosoma> poblacion, int numGeneraciones);
	public abstract void desplazamiento(ArrayList<Cromosoma> pob);
}