package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.math.plot.*;

import base.AlgoritmoGenetico;
import base.nGramas;

public class Menu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tamañoPoblacion;
	private int numeroGeneraciones;
	private double porcentajeCruce;
	private double porcentajeMutacion;
	private double precision;
	private double[] generacion;
	private double[] mejoresFitnessAbsolutos;
	private double[] mejoresFitness;
	private double[] listaMedias;
	private String[] selecciones = {"Ruleta", "Torneos", "Estocástico universal"};
	private HashMap<String, Double> frecuenciaMonogramas;
	private HashMap<String, Double> frecuenciaBigramas;
	private HashMap<String, Double> frecuenciaTrigramas;

	public Menu() {
		JComboBox seleccion = new JComboBox(selecciones);
		JTextField tamPob = new JTextField("100");
		JTextField numGen = new JTextField("100");
		JTextField porCruce = new JTextField("0.6");
		JTextField porMuta = new JTextField("0.05");
		JTextField preci = new JTextField("0.001");
		JCheckBox eli = new JCheckBox("", false);
		JLabel empty = new JLabel();
		JButton ok = new JButton("Ok");
		JLabel fitMejor = new JLabel("Fitness Mejor:");
		JTextArea textoOriginal = new JTextArea();
		JTextArea textoTraducido = new JTextArea();
		
		setSize(new Dimension(700, 500));
		setLocationRelativeTo(null); 
		setTitle("Genetic Algorithm"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Terminar el programa al pulsar la X
		setResizable(false);

		// Panel programa
		// Menu Panel
		JPanel menuPanel = new JPanel(new GridLayout(8, 2));
		menuPanel.add(new JLabel("Tipo de selección:"));
		menuPanel.add(seleccion);
		menuPanel.add(new JLabel("Tamaño de la población:"));
		menuPanel.add(tamPob);
		menuPanel.add(new JLabel("Número de generaciones:"));
		menuPanel.add(numGen);
		menuPanel.add(new JLabel("Porcentaje de cruce:"));
		menuPanel.add(porCruce);
		menuPanel.add(new JLabel("Porcentaje de mutaciones:"));
		menuPanel.add(porMuta);
		menuPanel.add(new JLabel("Precisión:"));
		menuPanel.add(preci);
		menuPanel.add(new JLabel("Elitismo:"));
		menuPanel.add(eli);
		menuPanel.add(empty);
		menuPanel.add(ok);

		// Grafica Panel
		Plot2DPanel grafica = new Plot2DPanel();

		grafica.addLegend("SOUTH");

		JPanel graficaPanel = new JPanel();
		graficaPanel.setLayout(new BorderLayout());
		graficaPanel.add(grafica, BorderLayout.CENTER);
		graficaPanel.add(fitMejor, BorderLayout.SOUTH);
		
		JPanel programa = new JPanel();
		programa.setLayout(new BorderLayout());
		programa.add(menuPanel, BorderLayout.WEST);
		programa.add(graficaPanel, BorderLayout.CENTER);
		// Fin panel programa
		
		// Panel areaTexto
		// TextoOriginal Panel
		JPanel areaTextoOriginal = new JPanel();
		areaTextoOriginal.setLayout(new BorderLayout());
		areaTextoOriginal.add(new JLabel("Original"), BorderLayout.NORTH);
		areaTextoOriginal.add(textoOriginal, BorderLayout.CENTER);
		
		// TextoTraducido Panel
		JPanel areaTextoTraducido = new JPanel();
		areaTextoTraducido.setLayout(new BorderLayout());
		areaTextoTraducido.add(new JLabel("Traducido"), BorderLayout.NORTH);
		areaTextoTraducido.add(textoTraducido, BorderLayout.CENTER);
		
		// AreaTexto Panel
		JPanel areaTexto = new JPanel();
		areaTexto.setLayout(new GridLayout(1, 2));
		areaTexto.add(areaTextoOriginal);
		areaTexto.add(areaTextoTraducido);
		// Fin panel areaTexto		
		
		setLayout(new BorderLayout());
		add(programa, BorderLayout.NORTH);
		add(areaTexto, BorderLayout.CENTER);

		nGramas ng = new nGramas();
		this.frecuenciaMonogramas = ng.getFrecuenciaMonogramas();
		this.frecuenciaBigramas = ng.getFrecuenciaBigramas();
		this.frecuenciaTrigramas = ng.getFrecuenciaTrigramas();
		
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (tamPob.getText().equals("") || numGen.getText().equals("") || porCruce.getText().equals("") ||
						porMuta.getText().equals("") || preci.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Por favor introduzca todos los datos.");
				}

				else {

					tamañoPoblacion = Integer.parseInt(tamPob.getText());
					numeroGeneraciones = Integer.parseInt(numGen.getText());
					porcentajeCruce = Double.parseDouble(porCruce.getText());
					porcentajeMutacion = Double.parseDouble(porMuta.getText());
					precision = Double.parseDouble(preci.getText());


					if (eli.isSelected() == true) {
						generacion = new double[numeroGeneraciones];
						mejoresFitnessAbsolutos = new double[numeroGeneraciones];
						mejoresFitness = new double[numeroGeneraciones];

						for (int i = 0; i < numeroGeneraciones; i++) {
							generacion[i] = i;
						}

						int tipoSeleccion = (int) seleccion.getSelectedIndex();

						AlgoritmoGenetico ag = new AlgoritmoGenetico(tamañoPoblacion, precision, porcentajeCruce, 
								porcentajeMutacion, numeroGeneraciones, true, 
								tipoSeleccion, textoOriginal.getText(), frecuenciaMonogramas,
								frecuenciaBigramas, frecuenciaTrigramas);

						ag.ejecutar();

						mejoresFitnessAbsolutos = ag.getListaFitnessMejorAbsoluto();
						mejoresFitness = ag.getListaFitnessMejor();
						listaMedias = ag.getListaMedias();

						grafica.setVisible(false);
						grafica.removeAllPlots();
						pintarGrafica(graficaPanel, grafica, generacion, mejoresFitnessAbsolutos, "Mejor fitness absoluto");
						pintarGrafica(graficaPanel, grafica, generacion, mejoresFitness, "Mejor fitness por generacion");
						pintarGrafica(graficaPanel, grafica, generacion, listaMedias, "Media de fitness");
						
						fitMejor.setText("Fitness Mejor: " + mejoresFitnessAbsolutos[mejoresFitnessAbsolutos.length - 1]);
						textoTraducido.setText(ag.getTextoMejor());
					}

					else {

						generacion = new double[numeroGeneraciones];
						mejoresFitnessAbsolutos = new double[numeroGeneraciones];
						mejoresFitness = new double[numeroGeneraciones];

						for (int i = 0; i < numeroGeneraciones; i++) {
							generacion[i] = i;
						}

						int tipoSeleccion = (int) seleccion.getSelectedIndex();

						AlgoritmoGenetico ag = new AlgoritmoGenetico(tamañoPoblacion, precision, porcentajeCruce, 
								porcentajeMutacion, numeroGeneraciones, false, 
								tipoSeleccion, textoOriginal.getText(), frecuenciaMonogramas,
								frecuenciaBigramas, frecuenciaTrigramas);

						ag.ejecutar();

						mejoresFitnessAbsolutos = ag.getListaFitnessMejorAbsoluto();
						mejoresFitness = ag.getListaFitnessMejor();
						listaMedias = ag.getListaMedias();

						grafica.setVisible(false);
						grafica.removeAllPlots();
						pintarGrafica(graficaPanel, grafica, generacion, mejoresFitnessAbsolutos, "Mejor absoluto");
						pintarGrafica(graficaPanel, grafica, generacion, mejoresFitness, "Mejor de la generación");
						pintarGrafica(graficaPanel, grafica, generacion, listaMedias, "Media de la generación");
						
						fitMejor.setText("Fitness Mejor: " + mejoresFitnessAbsolutos[mejoresFitnessAbsolutos.length - 1]);
						textoTraducido.setText(ag.getTextoMejor());
					}
				} 
			}
		});	
	}

	public void pintarGrafica(JPanel graficaPanel, Plot2DPanel grafica, double[] x, double[] y, String nombre) {
		// define the legend position
		grafica.setAxisLabel(0, "Generación");
		grafica.setAxisLabel(1, "Evaluación");

		// add a line plot to the PlotPanel
		grafica.addLinePlot(nombre, x, y);
		graficaPanel.add(grafica, BorderLayout.CENTER);
		grafica.setVisible(true);
	}
}