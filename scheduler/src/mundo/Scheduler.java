package mundo;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import data_structures.HashTableSeparateChaining;
import data_structures.KeyValuePair;
import data_structures.LinkedList;

public class Scheduler
{
	public static final String DATA = "./data/courses.json";
	
	private HashTableSeparateChaining<String, Clase> clases;
	
	@SuppressWarnings("unchecked")
	public Scheduler()
	{
		clases = new HashTableSeparateChaining<String, Clase>();
		
		JSONParser parser = new JSONParser();
		try
		{
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(DATA));
			JSONArray secciones = (JSONArray) jsonObject.get("records");
			Iterator<JSONObject> seccionesIterator = (Iterator<JSONObject>) secciones.iterator();
			while(seccionesIterator.hasNext())
			{
				JSONObject seccion = seccionesIterator.next();
				if(!clases.contains((String) seccion.get("class")))
				{
					clases.put((String) seccion.get("class"), new Clase((String) seccion.get("class")));
				}
				Clase clase = clases.get((String) seccion.get("class"));
				if(!clase.darMaterias().contains((String) seccion.get("course")))
				{
					clase.darMaterias().put((String) seccion.get("course"), 
							new Materia(clase, Integer.parseInt((String) seccion.get("credits")), (String) seccion.get("title")));
				}
				Materia materia = clase.darMaterias().get((String) seccion.get("course"));
				JSONArray profesores = (JSONArray) seccion.get("instructors");
				Iterator<JSONObject> profesoresIterator = (Iterator<JSONObject>) profesores.iterator();
				String[] profs = new String[profesores.size()];
				int cont = 0;
				while(profesoresIterator.hasNext())
				{
					profs[cont] = (String) profesoresIterator.next().get("name");
					cont++;
				}
				
				LinkedList<Seccion> complementarias = new LinkedList<Seccion>();
				JSONArray arrayCompl = (JSONArray) seccion.get("compl");
				Iterator<JSONObject> itCompl = arrayCompl.iterator();
				while(itCompl.hasNext())
				{
					JSONObject seccionCompl = itCompl.next();
					Seccion newCompl = new Seccion(null, Integer.parseInt((String) seccionCompl.get("nrc")), Integer.parseInt((String) seccionCompl.get("section")), new String[0], new LinkedList<Seccion>());
					JSONArray schedulesCompl = (JSONArray) seccion.get("schedules");
					Iterator<JSONObject> schedulesIteratorCompl = (Iterator<JSONObject>) schedulesCompl.iterator();
					while(schedulesIteratorCompl.hasNext())
					{
						JSONObject scheduleCompl = schedulesIteratorCompl.next();
						int[] dias = new int[7];
						if(scheduleCompl.get("L") != null)
							dias[0] = 1;
						if(scheduleCompl.get("M") != null)
							dias[1] = 1;
						if(scheduleCompl.get("I") != null)
							dias[2] = 1;
						if(scheduleCompl.get("J") != null)
							dias[3] = 1;
						if(scheduleCompl.get("V") != null)
							dias[4] = 1;
						if(scheduleCompl.get("S") != null)
							dias[5] = 1;
						if(scheduleCompl.get("D") != null)
							dias[6] = 1;
						
						Schedule scheduleX = new Schedule(newCompl, (String) scheduleCompl.get("time_ini"), (String) scheduleCompl.get("time_fin"), dias, (String) scheduleCompl.get("date_ini"), (String) scheduleCompl.get("date_fin"));
						newCompl.darSchedules().addAtHead(scheduleX);
					}
					complementarias.addAtHead(newCompl);
				}
				
				materia.darSecciones().put((String) seccion.get("nrc"),
						new Seccion(materia, Integer.parseInt((String) seccion.get("nrc")), Integer.parseInt((String) seccion.get("section")), profs, complementarias));
				Seccion seccionX = materia.darSecciones().get((String) seccion.get("nrc"));
				JSONArray schedules = (JSONArray) seccion.get("schedules");
				Iterator<JSONObject> schedulesIterator = (Iterator<JSONObject>) schedules.iterator();
				while(schedulesIterator.hasNext())
				{
					JSONObject schedule = schedulesIterator.next();
					int[] dias = new int[7];
					if(schedule.get("L") != null)
						dias[0] = 1;
					if(schedule.get("M") != null)
						dias[1] = 1;
					if(schedule.get("I") != null)
						dias[2] = 1;
					if(schedule.get("J") != null)
						dias[3] = 1;
					if(schedule.get("V") != null)
						dias[4] = 1;
					if(schedule.get("S") != null)
						dias[5] = 1;
					if(schedule.get("D") != null)
						dias[6] = 1;
					
					Schedule scheduleX = new Schedule(seccionX, (String) schedule.get("time_ini"), (String) schedule.get("time_fin"), dias, (String) schedule.get("date_ini"), (String) schedule.get("date_fin"));
					seccionX.darSchedules().addAtHead(scheduleX);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public HashTableSeparateChaining<String, Clase> darClases()
	{
		return clases;
	}
	
	public LinkedList<Horario> generarHorarios(LinkedList<Materia> materias, int horaMin, int horaMax)
	{
		LinkedList<Horario> horarios = new LinkedList<Horario>();
		
		int mayorNumSecciones = 0; //altoMatriz
		int anchoMatriz = materias.size();
		int[] limites = new int[materias.size()];
		
		Iterator<Materia> it = materias.iterator();
		int cont = 0;
		while(it.hasNext())
		{
			Materia actual = it.next();
			if(actual.darSecciones().getKeys().size() > mayorNumSecciones)
				mayorNumSecciones = actual.darSecciones().getKeys().size();
			limites[cont] = actual.darSecciones().getKeys().size();
			cont++;
		}
		
		Seccion[][] matrizSecciones = new Seccion[anchoMatriz][mayorNumSecciones];
		it = materias.iterator();
		cont = 0;
		while(it.hasNext())
		{
			Iterator<KeyValuePair<String,Seccion>> it2 = it.next().darSecciones().iterator();
			int cont2 = 0;
			while(it2.hasNext())
			{
				matrizSecciones[cont][cont2] = it2.next().getValue();
				cont2++;
			}
			cont++;
		}
		
		int[] contadorMasivo = new int[anchoMatriz];
		recursiveMethod(matrizSecciones, contadorMasivo, limites, horarios, horaMin, horaMax);
		
		generateReport(horarios, materias.size());
		
		return horarios;
	}
	
	public void recursiveMethod(Seccion[][] matrizSecciones, int[] contadorMasivo, int[] limites, LinkedList<Horario> horarios, int horaMin, int horaMax)
	{
		Horario horario = new Horario(this);
		boolean permitirNuevoHorario = true;
		for(int i = 0; i < contadorMasivo.length; i++)
		{
			for(int j = 0; j < matrizSecciones[i][contadorMasivo[i]].darSchedules().size(); j++)
			{
				Iterator<Schedule> it = matrizSecciones[i][contadorMasivo[i]].darSchedules().iterator();
				while(it.hasNext())
				{
					if(!horario.insertarSeccion(it.next(), horaMin, horaMax))
						permitirNuevoHorario = false;
				}
			}
		}
		if(permitirNuevoHorario)
			horarios.addAtHead(horario);
		
		boolean paraActualizar = false;
		for(int k = 0; k < contadorMasivo.length; k++)
		{
			if(contadorMasivo[k] + 1 != limites[k])
				paraActualizar = true;
		}
		
		if(paraActualizar)
		{
			for(int i = contadorMasivo.length - 1; i > -1; i--)
			{
				if(i == contadorMasivo.length - 1)
				{
					if(contadorMasivo[i] + 1 == limites[i])
						contadorMasivo[i] = 0;
					else
						contadorMasivo[i]++;
				}
				else if(contadorMasivo[i+1] == 0)
				{
					if(contadorMasivo[i] + 1 == limites[i])
						contadorMasivo[i] = 0;
					else
						contadorMasivo[i]++;
				}
			}
			recursiveMethod(matrizSecciones, contadorMasivo, limites, horarios, horaMin, horaMax);
		}
	}
	
	public void generateReport(LinkedList<Horario> horarios, int numMaterias)
	{
		File file = new File("./data/horarios.txt");
		try
		{
			PrintWriter writer = new PrintWriter(file);
			String text = "";
			Iterator<Horario> ithor = horarios.iterator();
			int contadorhor = 0;
			while(ithor.hasNext())
			{
				Horario horActual = ithor.next();
				if(horActual.darSecciones().size() == 8)
				{
					if(contadorhor != 0)
						text += "\n";
					contadorhor++;
					text += "------------------------\n";
					text += "Horario " + contadorhor + "\n";
					text += "------------------------\n";
					
					text += "Numero de clases: " + horActual.darSecciones().size();
					
					for(int i = 1; i <= 7; i++)
					{
						switch(i)
						{
							case 1:
								text += "\nLunes: ";
								break;
							case 2:
								text += "\nMartes: ";
								break;
							case 3:
								text += "\nMiercoles: ";
								break;
							case 4:
								text += "\nJueves: ";
								break;
							case 5:
								text += "\nViernes: ";
								break;
							case 6:
								text += "\nSabado: ";
								break;
							case 7:
								text += "\nDomingo: \n";
								break;
						}
						Iterator<Schedule> itSch = horActual.darSecciones().iterator();
						while(itSch.hasNext())
						{
							Schedule schActual = itSch.next();
							if(schActual.darDias()[i-1] == 1)
								text += schActual.darHoraInicial() + " - " + schActual.darHoraFinal() + " " + "\"" + schActual.darSeccion().darMateria().darNombre() + "\" ";
						}
					}
					text += "\n";
					Iterator<Schedule> itSch = horActual.darSecciones().iterator();
					while(itSch.hasNext())
					{
						Schedule schActual = itSch.next();
						text += schActual.darSeccion().darNrc() + " - " + schActual.darSeccion().darMateria().darNombre() + ": ";
						for(int i = 0 ; i < schActual.darSeccion().darProfesores().length; i++)
							text += schActual.darSeccion().darProfesores()[i] + ", ";
						text += "\n";
					}
				}
			}
//			System.out.println(text);
			writer.print(text);
			writer.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
