package mundo;

import data_structures.HashTableSeparateChaining;

public class Materia
{
	//EJEMPLO: MEL
	private Clase clase;
	private int creditos;
	private String nombre;
	private HashTableSeparateChaining<String, Seccion> secciones;
	
	public Materia(Clase pClase, int pCreditos, String pNombre)
	{
		clase = pClase;
		creditos = pCreditos;
		nombre = pNombre;
		secciones = new HashTableSeparateChaining<String, Seccion>();
	}
	
	public Clase darClase()
	{
		return clase;
	}
	
	public int darCreditos()
	{
		return creditos;
	}
	
	public String darNombre()
	{
		return nombre;
	}
	
	public HashTableSeparateChaining<String, Seccion> darSecciones()
	{
		return secciones;
	}
}
