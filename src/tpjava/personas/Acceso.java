package tpjava.personas;
import java.time.LocalDate;
import java.time.LocalTime;

public class Acceso {
	private String zona; /* Formato de fecha: dia/mes/año */
	private LocalDate fecha;
	private LocalTime hora;
	private float cantidad_minutos;
	private boolean estado; /* Autorizado? */
	public Acceso(String z, LocalDate f, LocalTime h, float cm, boolean status) {
		zona = z;
		fecha = f;
		hora = h;
		cantidad_minutos = cm;
		estado = status;
	}
	
}
