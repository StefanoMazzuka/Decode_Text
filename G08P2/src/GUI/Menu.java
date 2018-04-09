package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.math.plot.*;

import base.AlgoritmoGenetico;

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
	private String[] funciones = {"1", "2", "3", "4", "5"};
	private String[] selecciones = {"Ruleta", "Torneos", "Estocástico universal"};
	
	public Menu() {
		JComboBox funcion = new JComboBox(funciones);
		JComboBox seleccion = new JComboBox(selecciones);
		JTextField tamPob = new JTextField("100");
		JTextField numGen = new JTextField("100");
		JTextField porCruce = new JTextField("0.6");
		JTextField porMuta = new JTextField("0.05");
		JTextField preci = new JTextField("0.001");
		JCheckBox eliY = new JCheckBox("si", false);
		JCheckBox eliN = new JCheckBox("no", false);
		JLabel empty = new JLabel();
		JButton ok = new JButton("Ok");

		setSize(new Dimension(400, 300));
		setLocationRelativeTo(null); 
		setTitle("Genetic Algorithm*"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Terminar el programa al pulsar la X

		JPanel checkBoxPanel = new JPanel(new GridLayout(1, 2));
		checkBoxPanel.add(eliY);
		checkBoxPanel.add(eliN);
		eliN.setSelected(true);

		/*Menu Panel*/
		JPanel menuPanel = new JPanel(new GridLayout(9, 2));
		menuPanel.add(new JLabel("Tipo de función:"));
		menuPanel.add(funcion);
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
		menuPanel.add(checkBoxPanel);
		menuPanel.add(empty);
		menuPanel.add(ok);

		/*Grafica Panel*/

		// create your PlotPanel (you can use it as a JPanel)
		Plot2DPanel grafica = new Plot2DPanel();

		// define the legend position
		grafica.addLegend("SOUTH");

		// add a line plot to the PlotPanel

		setLayout(new BorderLayout());
		add(menuPanel, BorderLayout.WEST);
		add(grafica, BorderLayout.CENTER);
		pack();

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
					
					if (eliY.isSelected() == false && eliN.isSelected() == false) {
						JOptionPane.showMessageDialog(null, "Seleccione una opción elitista");
					}
					
					else if (eliY.isSelected() == true && eliN.isSelected() == true) {
						JOptionPane.showMessageDialog(null, "Seleccione solo una opción elitista.");
					}
					
					else if (eliY.isSelected() == true && eliN.isSelected() == false) {
						generacion = new double[numeroGeneraciones];
						mejoresFitnessAbsolutos = new double[numeroGeneraciones];
						mejoresFitness = new double[numeroGeneraciones];
						
						for (int i = 0; i < numeroGeneraciones; i++) {
							generacion[i] = i;
						}
						
						int tipoFuncion = (int) funcion.getSelectedIndex();
						int tipoSeleccion = (int) seleccion.getSelectedIndex();
						
						AlgoritmoGenetico ag = new AlgoritmoGenetico(tamañoPoblacion, precision, porcentajeCruce, 
								porcentajeMutacion, numeroGeneraciones, true, tipoFuncion, tipoSeleccion);

						ag.ejecutar();
						
						mejoresFitnessAbsolutos = ag.getListaFitnessMejorAbsoluto();
						mejoresFitness = ag.getListaFitnessMejor();
						listaMedias = ag.getListaMedias();
						
						grafica.setVisible(false);
						grafica.removeAllPlots();
						pintarGrafica(grafica, generacion, mejoresFitnessAbsolutos, "Mejor fitness absoluto");
						pintarGrafica(grafica, generacion, mejoresFitness, "Mejor fitness por generacion");
						pintarGrafica(grafica, generacion, listaMedias, "Media de fitness");
					}

					else if (eliY.isSelected() == false && eliN.isSelected() == true) {
						
						generacion = new double[numeroGeneraciones];
						mejoresFitnessAbsolutos = new double[numeroGeneraciones];
						mejoresFitness = new double[numeroGeneraciones];
						
						for (int i = 0; i < numeroGeneraciones; i++) {
							generacion[i] = i;
						}
						
						int tipoFuncion = (int) funcion.getSelectedIndex();
						int tipoSeleccion = (int) seleccion.getSelectedIndex();
						
						AlgoritmoGenetico ag = new AlgoritmoGenetico(tamañoPoblacion, precision, porcentajeCruce, 
								porcentajeMutacion, numeroGeneraciones, false, tipoFuncion, tipoSeleccion);
			
						ag.ejecutar();
						
						mejoresFitnessAbsolutos = ag.getListaFitnessMejorAbsoluto();
						mejoresFitness = ag.getListaFitnessMejor();
						listaMedias = ag.getListaMedias();
						
						grafica.setVisible(false);
						grafica.removeAllPlots();
						pintarGrafica(grafica, generacion, mejoresFitnessAbsolutos, "Mejor absoluto");
						pintarGrafica(grafica, generacion, mejoresFitness, "Mejor de la generación");
						pintarGrafica(grafica, generacion, listaMedias, "Media de la generación");	
					}
				} 
			}
		});	
	}
	
	public void pintarGrafica(Plot2DPanel grafica, double[] x, double[] y, String nombre) {
		// define the legend position
		grafica.setAxisLabel(0, "Generación");
		grafica.setAxisLabel(1, "Evaluación");

		// add a line plot to the PlotPanel
		grafica.addLinePlot(nombre, x, y);		
		add(grafica, BorderLayout.CENTER);	
		grafica.setVisible(true);
	}
}