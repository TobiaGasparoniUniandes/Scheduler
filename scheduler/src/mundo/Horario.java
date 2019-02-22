package mundo;

import java.util.Iterator;

import data_structures.LinkedList;
import data_structures.Node;

public class Horario
{
	private LinkedList<Schedule> secciones;
	private Scheduler mundo;
	
	public Horario(Scheduler pMundo)
	{
		secciones = new LinkedList<Schedule>();
		mundo = pMundo;
		/*
		 * Secciones fijas
		 */
		insertarSeccion(mundo.darClases().get("ISIS").darMaterias().get("2603").darSecciones().get("27388").darSchedules().iterator().next(), 0, 0);
		insertarSeccion(mundo.darClases().get("ISIS").darMaterias().get("2603L").darSecciones().get("27389").darSchedules().iterator().next(), 0, 0);
		insertarSeccion(mundo.darClases().get("ISIS").darMaterias().get("1106").darSecciones().get("18957").darSchedules().iterator().next(), 0, 0);
		insertarSeccion(mundo.darClases().get("MATE").darMaterias().get("1214").darSecciones().get("33847").darSchedules().iterator().next(), 0, 0);
		insertarSeccion(mundo.darClases().get("MATE").darMaterias().get("1214C").darSecciones().get("13584").darSchedules().iterator().next(), 0, 0);
		insertarSeccion(mundo.darClases().get("MATE").darMaterias().get("1105").darSecciones().get("36167").darSchedules().iterator().next(), 0, 0);

	}
	
	public LinkedList<Schedule> darSecciones()
	{
		return secciones;
	}
	
	public boolean insertarSeccion(Schedule newSeccion, int horaMin, int horaMax)
	{
		boolean sePudo = true;
		
		Iterator<Schedule> it = secciones.iterator();
		while(it.hasNext() && sePudo)
		{
			if((horaMin != 0 && newSeccion.darHoraInicial() < horaMin) || (horaMax != 0 && newSeccion.darHoraFinal() > horaMax))
			{
				sePudo = false;
			}
			try
			{
				if(newSeccion.darSeccion().darProfesores()[0].contains("TAKAHASHI RODRIGUEZ SILVIA") ||
						newSeccion.darSeccion().darProfesores()[0].contains("ROCHA RIMULO LEANDRO") || 
						newSeccion.darSeccion().darProfesores()[0].contains("DE LA VEGA SINISTERRA RAMIRO HERNANDO") ||
						newSeccion.darSeccion().darProfesores()[0].contains("DANN SUSANNA"))
					sePudo = false;
			}
			catch(IndexOutOfBoundsException e) {}
			Schedule actual = it.next();
			for(int i = 0; i < 7 && sePudo; i++)
			{
				if(actual.darDias()[i] == 1 && newSeccion.darDias()[i] == 1)
				{
					if((actual.darHoraInicial() >= newSeccion.darHoraInicial() && actual.darHoraFinal() <= newSeccion.darHoraFinal()) ||
							(actual.darHoraInicial() <= newSeccion.darHoraInicial() && actual.darHoraFinal() >= newSeccion.darHoraFinal()) ||
							(actual.darHoraInicial() <= newSeccion.darHoraInicial() && newSeccion.darHoraInicial() <= actual.darHoraFinal() && actual.darHoraFinal() <= newSeccion.darHoraFinal()) ||
							(actual.darHoraInicial() >= newSeccion.darHoraInicial() && newSeccion.darHoraInicial() >= actual.darHoraInicial() && actual.darHoraFinal() >= newSeccion.darHoraFinal()))
					{
						sePudo = false;
					}
				}
			}
		}
		if(sePudo)
		{
//			secciones.addAtEnd(newSeccion);
			if(secciones.size() == 0)
				secciones.addAtHead(newSeccion);
			else if(secciones.size() == 1)
			{
				if(newSeccion.darHoraInicial() < secciones.getHead().getElement().darHoraInicial())
					secciones.addAtHead(newSeccion);
				else
					secciones.addLast(newSeccion);
			}
			else if(secciones.size() == 2)
			{
				if(newSeccion.darHoraInicial() < secciones.getHead().getElement().darHoraInicial())
					secciones.addAtHead(newSeccion);
				else if(newSeccion.darHoraInicial() 
						> secciones.getLast().getElement().darHoraInicial())
					secciones.addLast(newSeccion);
				else
				{
					Node<Schedule> node = new Node<Schedule>(newSeccion);
					node.setPrevious(secciones.getHead());
					node.setNext(secciones.getLast());
					secciones.getHead().setNext(node);
					secciones.getLast().setPrevious(node);
				}
			}
			else
			{
				if(newSeccion.darHoraInicial() < secciones.getHead().getElement().darHoraInicial())
					secciones.addAtHead(newSeccion);
				else if(newSeccion.darHoraInicial() > secciones.getLast().getElement().darHoraInicial())
					secciones.addLast(newSeccion);
				else
				{
					Node<Schedule> actual = secciones.getHead();
					while(actual != null)
					{
						if(newSeccion.darHoraInicial() > actual.getElement().darHoraInicial() &&
								newSeccion.darHoraInicial() < actual.getNext().getElement().darHoraInicial())
						{
							Node<Schedule> node = new Node<Schedule>(newSeccion);
							node.setPrevious(actual);
							node.setNext(actual.getNext());
							actual.getNext().setPrevious(node);
							actual.setNext(node);
						}
						actual = actual.getNext();
					}
				}
			}
		}
		
		return sePudo;
	}
}
