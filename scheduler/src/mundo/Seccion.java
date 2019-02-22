package mundo;

import data_structures.LinkedList;

public class Seccion
{
	private Materia materia;
	private int nrc;
	private int numSeccion;
	private String[] profesores;
	private LinkedList<Schedule> schedules;
	private LinkedList<Seccion> complementarias;
	
	public Seccion(Materia pMateria, int pNrc, int pNumSeccion, String[] pProfesores, LinkedList<Seccion> pComplementarias)
	{
		materia = pMateria;
		nrc = pNrc;
		numSeccion = pNumSeccion;
		profesores = pProfesores;
		schedules = new LinkedList<Schedule>();
		complementarias = pComplementarias;
	}
	
	public Materia darMateria()
	{
		return materia;
	}
	
	public int darNrc()
	{
		return nrc;
	}
	
	public int darNumSeccion()
	{
		return numSeccion;
	}
	
	public String[] darProfesores()
	{
		return profesores;
	}
	
	public LinkedList<Schedule> darSchedules()
	{
		return schedules;
	}
	
	public LinkedList<Seccion> darComplementarias()
	{
		return complementarias;
	}
}