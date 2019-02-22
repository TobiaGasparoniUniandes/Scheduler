package mundo;

public class Schedule
{
	private Seccion seccion;
	private int horaInicial;
	private int horaFinal;
	private String dateInicial;
	private String dateFinal;
	private int[] dias;
	
	public Schedule(Seccion pSeccion, String pHoraInicial, String pHoraFinal, int[] pDias, String pDateInicial, String pDateFinal)
	{
		seccion = pSeccion;
		try
		{
			horaInicial = Integer.parseInt(pHoraInicial);
			horaFinal = Integer.parseInt(pHoraFinal);
		}
		catch(NumberFormatException e)
		{
			horaInicial = 0;
			horaFinal = 0;
		}
		dateInicial = pDateInicial;
		dateFinal = pDateFinal;
		dias = pDias;
	}
	
	public Seccion darSeccion()
	{
		return seccion;
	}
	
	public int darHoraInicial()
	{
		return horaInicial;
	}
	
	public int darHoraFinal()
	{
		return horaFinal;
	}
	
	public String darDateInicial()
	{
		return dateInicial;
	}
	
	public String darDateFinal()
	{
		return dateFinal;
	}
	
	public int[] darDias()
	{
		return dias;
	}
}