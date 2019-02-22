package mundo;

import data_structures.HashTableSeparateChaining;

public class Clase
{
	private String nombre;
	
	private HashTableSeparateChaining<String, Materia> materias;
	
	public Clase(String pNombre)
	{
		nombre = pNombre;
		materias = new HashTableSeparateChaining<String, Materia>();
	}
	
	public String darNombre()
	{
		return nombre;
	}
	
	public HashTableSeparateChaining<String, Materia> darMaterias()
	{
		return materias;
	}
}
