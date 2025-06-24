package tpjava.personas;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalUnit;
import tpjava.zonas.Zona;

/**
 * Esta clase contiene los atributos de Acceso y métodos necesarios para trabajar con objetos de ésta.
 * @author grupo2
 */
public class Acceso {
	private Zona zona; /* Formato de fecha: dia/mes/año */
	private LocalDate fecha;
	private LocalTime hora;
	private long cantidad_minutos;
	private boolean estado; /* Autorizado? */
	
	/**
	 * Construye un objeto de clase Acceso.
	 * @param z objeto de clase Zona, es la zona a la que se accedió.
	 * @param f objeto de clase LocalDate, es la fecha en la cual ocurrió el Acceso.
	 * @param h objeto de clase LocalTime, es la hora en la cual ocurrió el Acceso.
	 * @param cm variable de tipo primitivo long, es la cantidad de minutos que la persona permaneció en la Zona z.
	 * @param status variable de tipo primitivo boolean, es el estado de autorización del Acceso a la Zona z.
	 */
	public Acceso(Zona z, LocalDate f, LocalTime h, long cm, boolean status) {
		zona = z;
		fecha = f;
		hora = h;
		cantidad_minutos = cm;
		estado = status;
	}
	
	@Override
	
	
	/**
	 * Devuelve un objeto de clase String con los atributos que mejor identifican al Acceso.
	 * @return objeto de clase String, contiene los atributos que mejor identifican al Acceso.
	 */
	public String toString() {
		return zona.toString() + "  FECHA y HORA: " + fecha.toString() + " " + hora.toString() + "  CANTIDAD MINUTOS: " + cantidad_minutos + "  AUTORIZADO:  " + Boolean.toString(estado); 
	}
	
	/**
	 * Devuelve un valor boolean que indica si una persona se encuentra actualmente en la Zona accedida en el objeto de clase Acceso.
	 * @param f objeto de clase LocalDate, es la fecha actual.
	 * @param h objeto de clase LocalTime, es la hora actual.
	 * @return boolean true (cuando la persona se encuentra en la zona) o false (en el caso opuesto).
	 */
	public boolean es_enLaHora(LocalDate f, LocalTime h) {
		return (hora.isBefore(h) && h.isBefore(hora.plusMinutes(cantidad_minutos))) || h.equals(hora.plusMinutes(cantidad_minutos)) && fecha.equals(f);
	}
	/**
	 * Devuelve el atributo zona del objeto de clase Acceso
	 * @return Zona zona (atributo de Acceso)
	 */
	public Zona obtener_Zona() {
		return zona;
	}
}
