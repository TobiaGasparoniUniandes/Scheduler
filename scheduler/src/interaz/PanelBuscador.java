package interaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import data_structures.KeyValuePair;
import data_structures.LinkedList;
import mundo.Clase;
import mundo.Materia;
import mundo.Scheduler;

@SuppressWarnings("serial")
public class PanelBuscador extends JPanel implements ActionListener
{
	private InterfazScheduler principal;
	private JTextField writer;
	private JScrollPane areaScrollPane;
	private JList<String> lista;
	private String[] listaGeneral;
	private String[] listaActual;
	private JButton boton;
	private DefaultListModel<String> listModel;
	
	public PanelBuscador(InterfazScheduler pPrincipal, Scheduler mundo)
	{
		int contador = 0;
		principal = pPrincipal;
		
		listModel = new DefaultListModel<String>();
		setLayout(new BorderLayout());
		
		Iterator<KeyValuePair<String, Clase>> clases = mundo.darClases().iterator();
		while(clases.hasNext())
		{
			Iterator<KeyValuePair<String, Materia>> materias = clases.next().getValue().darMaterias().iterator();
			while(materias.hasNext())
			{
				materias.next();
				contador++;
			}
		}
		listaGeneral = new String[contador];
		listaActual = new String[contador];
		
		contador = 0;
		clases = mundo.darClases().iterator();
		while(clases.hasNext())
		{
			Iterator<KeyValuePair<String, Materia>> materias = clases.next().getValue().darMaterias().iterator();
			while(materias.hasNext())
			{
				KeyValuePair<String, Materia> actual = materias.next();
				listaGeneral[contador] = actual.getValue().darClase().darNombre() + " - " +actual.getKey() + " - " + actual.getValue().darNombre();
				contador++;
			}
		}
		
		writer = new JTextField();
		writer.addKeyListener(new KeyAdapter()
		{
	        @Override
	        public void keyReleased(KeyEvent e)
	        {
			  	listModel.removeAllElements();
			 	
				for(int i = 0; i < listaGeneral.length; i++)
				{
				    if(listaGeneral[i].contains(writer.getText()))
				    {
				      	listModel.addElement(listaGeneral[i]);
				    }
				}
		    }
	    });
		add(writer, BorderLayout.NORTH);

		for (int i=0; i<listaGeneral.length; i++)
		{
		  listModel.addElement(listaGeneral[i]);
		}
		lista = new JList<String>(listModel);
		areaScrollPane = new JScrollPane(lista);
		add(areaScrollPane, BorderLayout.CENTER);
		
		boton = new JButton("Agregar");
		boton.addActionListener(this);
		add(boton, BorderLayout.SOUTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if(lista.getSelectedValue() != null)
		{
			principal.agregarMateria(lista.getSelectedValue());
		}
		else
		{
			JOptionPane.showMessageDialog(this, "No se ha realizado una seleccion", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}