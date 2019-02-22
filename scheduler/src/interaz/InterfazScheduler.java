package interaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import data_structures.KeyValuePair;
import data_structures.LinkedList;
import mundo.Horario;
import mundo.Materia;
import mundo.Schedule;
import mundo.Scheduler;
import mundo.Seccion;

public class InterfazScheduler extends JFrame
{
	private static final String DEPARTAMENTOS = "deps";
	private static final String MATERIAS = "mats";
	
	private static Scheduler mundo;
	private PanelBuscador panelBuscador;
	private PanelRestricciones panelRestricciones;
	private JTextArea info;
	
	public InterfazScheduler()
	{
		mundo = new Scheduler();
		
		setTitle("Explorador de cuevas");
		setSize(1000,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(1,2));
		setResizable(false);
		
		LinkedList<String> clases = mundo.darClases().getKeys();
		String[] clasesArray = new String[clases.size() + 1];
		clasesArray[0] = "Departamentos";
		int cont = 1;
		Iterator<String> it = clases.iterator();
		while(it.hasNext())
		{
			clasesArray[cont] = it.next();
			cont++;
		}
		
		panelBuscador = new PanelBuscador(this, mundo);
		add(panelBuscador);
		panelRestricciones = new PanelRestricciones(this);
		add(panelRestricciones);
	}

	public void agregarMateria(String materia)
	{
		panelRestricciones.agregarMateria(materia);
	}
	
	public void generarHorarios(LinkedList<String> nombres, int horaMin, int horaMax)
	{
		LinkedList<Materia> materias = new LinkedList<Materia>();
		Iterator<String> it = nombres.iterator();
		while(it.hasNext())
		{
			String[] arreglo = it.next().split(" - ");
			materias.addAtEnd(mundo.darClases().get(arreglo[0]).darMaterias().get(arreglo[1]));
		}
		mundo.generarHorarios(materias, horaMin, horaMax);
	}
	
	public static void main(String[] args)
	{
		InterfazScheduler interfaz = new InterfazScheduler( );
        interfaz.setVisible( true );
//        LinkedList<Materia> materias = new LinkedList<Materia>();
//        materias.addAtEnd(mundo.darClases().get("MATE").darMaterias().get("1105C"));
////        System.out.println(mundo.darClases().get("MATE").darMaterias().get("1105C") == null);
//        materias.addAtEnd(mundo.darClases().get("MATE").darMaterias().get("1214"));
////        System.out.println(mundo.darClases().get("MATE").darMaterias().get("1214") == null);
//        materias.addAtEnd(mundo.darClases().get("MATE").darMaterias().get("1214C"));
////        System.out.println(mundo.darClases().get("MATE").darMaterias().get("1214C") == null);
//        materias.addAtEnd(mundo.darClases().get("ISIS").darMaterias().get("2304"));
////        System.out.println(mundo.darClases().get("ISIS").darMaterias().get("2304") == null);
//        materias.addAtEnd(mundo.darClases().get("ISIS").darMaterias().get("2603"));
////        System.out.println(mundo.darClases().get("ISIS").darMaterias().get("2603") == null);
//        materias.addAtEnd(mundo.darClases().get("ISIS").darMaterias().get("1106"));
////        System.out.println(mundo.darClases().get("ISIS").darMaterias().get("1106") == null);
//        materias.addAtEnd(mundo.darClases().get("FISI").darMaterias().get("1028"));
////        System.out.println(mundo.darClases().get("FISI").darMaterias().get("1028") == null);
//        materias.addAtEnd(mundo.darClases().get("FISI").darMaterias().get("1029"));
////        System.out.println(mundo.darClases().get("FISI").darMaterias().get("1029") == null);
//        LinkedList<Horario> horarios = mundo.generarHorarios(materias);
	}
}