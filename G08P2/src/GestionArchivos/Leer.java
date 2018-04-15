package GestionArchivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Leer {

	private String rutaUsuario = System.getProperty("user.dir");
	private String separador = File.separator;
	private File archivo;
	private FileReader fr;
	private BufferedReader br;
	private String texto;

	public void leerArvhivo(String nombreArchivo) {
		String rutaArchivo = this.rutaUsuario + this.separador + "src" + this.separador + 
				"Archivos" + this.separador + nombreArchivo;
		this.archivo = new File(rutaArchivo);
		this.texto = "";

		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero
			String linea;
			while((linea = br.readLine()) != null)
				this.texto = texto + linea;
		}
		catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				if(null != fr)
					fr.close();
				if(null != br)  
					br.close();

			} catch (Exception e2) { 
				e2.printStackTrace();
			}
		}
	}
	
	public void leerNgramas(String nombreArchivo) {
		String rutaArchivo = this.rutaUsuario + this.separador + "src" + this.separador + 
				"Archivos" + this.separador + nombreArchivo;
		this.archivo = new File(rutaArchivo);
		this.texto = "";

		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero
			String linea;
			while((linea = br.readLine()) != null) {
				this.texto += (linea + " ");
				//System.out.println(this.texto);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				if(null != fr)
					fr.close();
				if(null != br)  
					br.close();

			} catch (Exception e2) { 
				e2.printStackTrace();
			}
		}
	}
	
	public void mostrarTexto() {
		System.out.println(this.texto);
	}
	
	public String getTexto() {
		return this.texto;
	}
}
