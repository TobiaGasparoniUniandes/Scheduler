package interaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import data_structures.HashTableSeparateChaining;
import data_structures.KeyValuePair;
import data_structures.LinkedList;

public class PanelRestricciones extends JPanel implements ActionListener
{	
	private static final String ELIMINAR = "Eliminar";
	private static final String GENERAR = "Generar";
	
	private InterfazScheduler principal;
	private HashTableSeparateChaining<String, String> tablaAgregados;

	private JPanel panelNorte;
	private JScrollPane areaScrollPane;
	private JList<String> lista;
	private DefaultListModel<String> listModel;
	private JButton eliminar;
	
	private JPanel panelSur;
	private JLabel horaMinima;
	private JTextField fieldHoraMinima;
	private JLabel horaMaxima;
	private JTextField fieldHoraMaxima;
	private JButton generar;
	
	public PanelRestricciones(InterfazScheduler pPrincipal)
	{
		principal = pPrincipal;
		tablaAgregados = new HashTableSeparateChaining<String, String>();
		setLayout(new BorderLayout());
		
		panelNorte = new JPanel();
		panelNorte.setLayout(new BorderLayout());
		listModel = new DefaultListModel<String>();
		lista = new JList<String>(listModel);
		areaScrollPane = new JScrollPane(lista);
		panelNorte.add(areaScrollPane, BorderLayout.CENTER);
		eliminar = new JButton("Eliminar");
		eliminar.addActionListener(this);
		eliminar.setActionCommand(ELIMINAR);
		panelNorte.add(eliminar, BorderLayout.SOUTH);
		add(panelNorte, BorderLayout.CENTER);
		
		
		panelSur = new JPanel();
		panelSur.setLayout(new GridLayout(5,1));
		horaMinima = new JLabel("Hora minima");
		panelSur.add(horaMinima);
		fieldHoraMinima = new JTextField();
		panelSur.add(fieldHoraMinima);
		horaMaxima = new JLabel("Hora maxima");
		panelSur.add(horaMaxima);
		fieldHoraMaxima = new JTextField();
		panelSur.add(fieldHoraMaxima);
		generar = new JButton("Generar horarios");
		generar.addActionListener(this);
		generar.setActionCommand(GENERAR);
		panelSur.add(generar);
		add(panelSur, BorderLayout.SOUTH);
		
		/*
		 * Materias agregadas por defecto
		 */
//		agregarMateria("FISI - 1028 - FISICA II");
//		agregarMateria("MATE - 1105 - ALGEBRA LINEAL 1");
//		agregarMateria("MATE - 1214 - CALCULO INTEGRAL CON ECUACIONES DIFERENCIALES");
		agregarMateria("FISI - 1029 - FISICA EXPERIMENTAL II");
//		agregarMateria("ISIS - 2304 - SISTEMAS TRANSACCIONALES");
//		agregarMateria("ISIS - 2603 - DESARROLLO DE SW EN EQUIPO");
//		agregarMateria("ISIS - 1106 - LENGUAJES Y MAQUINAS");
	}
	
	private void actualizarLista()
	{
		listModel.removeAllElements();
		Iterator<KeyValuePair<String, String>> it = tablaAgregados.iterator();
		while(it.hasNext())
		{
			listModel.addElement(it.next().getValue());
		}
	}
	
	public void agregarMateria(String materia)
	{
		if(!tablaAgregados.contains(materia))
		{
			tablaAgregados.put(materia, materia);
			actualizarLista();
		}
	}
	
	public void eliminarMateria()
	{
		if(lista.getSelectedValue() != null)
		{
			tablaAgregados.delete(lista.getSelectedValue());
			actualizarLista();
		}
		else
		{
			JOptionPane.showMessageDialog(this, "No se ha realizado una seleccion", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		if(event.getActionCommand().equals(ELIMINAR))
		{
			eliminarMateria();
		}
		else if(event.getActionCommand().equals(GENERAR))
		{
			int horaMin = 0;
			if(!fieldHoraMinima.getText().equals(""))
			{
				try
				{
					horaMin = Integer.parseInt(fieldHoraMinima.getText());
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			int horaMax = 0;
			if(!fieldHoraMaxima.getText().equals(""))
			{
				try
				{
					horaMax = Integer.parseInt(fieldHoraMaxima.getText());
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			LinkedList<String> list = new LinkedList<String>();
			for(int i = 0; i < listModel.size(); i++)
			{
				list.addAtHead(listModel.get(i));
			}
			principal.generarHorarios(list, horaMin, horaMax);
		}
	}
}
