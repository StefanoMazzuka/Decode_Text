package base;

import GUI.Menu;
import GestionArchivos.Leer;
import funciones.Decode;
// https://people.cs.uct.ac.za/~jkenwood/JasonBrownbridge.pdf
// http://norvig.com/mayzner.html
// http://practicalcryptography.com/cryptanalysis/letter-frequencies-various-languages/english-letter-frequencies/
public class Main {
	public static void main(String[] args) {
		int j = 15;
		int fin = 14;
		while (j != fin) {
			System.out.println(j);
			j++;
			if (j == 26) {
				j = 0;
				//fin = 15;
			}
		}
		System.out.println(j);
		Menu menu = new Menu();
		menu.setVisible(true);
	}
}