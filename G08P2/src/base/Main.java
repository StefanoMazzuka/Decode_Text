package base;

import GUI.Menu;
import GestionArchivos.Leer;
// https://people.cs.uct.ac.za/~jkenwood/JasonBrownbridge.pdf
// http://norvig.com/mayzner.html
public class Main {
	public static void main(String[] args) {
		Leer l = new Leer("Cuatro.txt");
		l.mostrarTexto();
		nGramas n = new nGramas();
		Menu menu = new Menu();
		menu.setVisible(true);
	}
}
