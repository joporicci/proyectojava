package tpjava.personas;
import java.time.LocalDate;
import java.time.LocalTime;
import tpjava.zonas.Zona;

public class Acceso {
	private Zona zona; /* Formato de fecha: dia/mes/año */
	private LocalDate fecha;
	private LocalTime hora;
	private long cantidad_minutos;
	private boolean estado; /* Autorizado? */
	public Acceso(Zona z, LocalDate f, LocalTime h, long cm, boolean status) {
		zona = z;
		fecha = f;
		hora = h;
		cantidad_minutos = cm;
		estado = status;
	}
	
	@Override
	public String toString() {
		/* Devuelve los atributos del Acceso que se quieren mostrar como String... */
		return "ZONA: " + zona.toString() + "  FECHA y HORA: " + fecha.toString() + " " + hora.toString() + "  CANTIDAD MINUTOS: " + cantidad_minutos + "  AUTORIZADO:  " + Boolean.toString(estado); 
	}
	
	public boolean es_enLaHora(LocalDate f, LocalTime h) {
		/* Devuelve un valor boolean que es = true si el acceso de la persona es el último y la persona está actualmente en la Zona accedida, si no se cumple esto, es = false. */
		return hora.equals(h.minusMinutes(cantidad_minutos)) && fecha.equals(f);
	}
	
	public Zona obtener_Zona() {
		/* Devuelve el atributo zona del Acceso- */
		return zona;
	}
}
