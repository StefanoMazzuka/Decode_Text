package base;

import java.util.HashMap;

import GUI.Menu;
import GestionArchivos.Leer;
import funciones.Decode;
// https://people.cs.uct.ac.za/~jkenwood/JasonBrownbridge.pdf
// http://norvig.com/mayzner.html
// http://practicalcryptography.com/cryptanalysis/letter-frequencies-various-languages/english-letter-frequencies/
public class Main {
	public static void main(String[] args) {
		nGramas n = new nGramas();
		Menu menu = new Menu();
		menu.setVisible(true);
		Leer l = new Leer();
		l.LeerArvhivo("Dos.txt");
		Decode d = new Decode(l.getTexto().toCharArray());
	}
}
