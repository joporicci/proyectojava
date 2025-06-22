package tpjava.personas;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.time.LocalDate;
import java.time.LocalTime;
import tpjava.zonas.Zona;
import tpjava.zonas.ZonaComun;
import tpjava.excepciones.ExcepcionPersonaSeFue;

/**
 * Clase que contiene los atributos que tienen los objetos de clase Personas y los métodos necesarios para trabajar con los mismos.
 * @author grupo2
 */
public class Personas{ // En la clase festival manejo las personas. Aca tengo datos indivuales y lista de zonas accesibles
	private String ID, nombre;
	private LinkedHashSet<Acceso> setAccesos;  /* set de Accesos ordenados por el orden de inserción, por lo que el último acceso es el más reciente. */
	private ArrayList<Zona> listaZonasAccesibles;
	
	/**
	 * Construye un objeto de clase Personas.
	 * @param id objeto de clase String, contiene el identificador de la Persona.
	 * @param name objeto de clase String, contiene el nombre de la Persona.
	 */
	public Personas(String id, String name) {
		ID = id;
		nombre = name;
		setAccesos = new LinkedHashSet<Acceso>();
		listaZonasAccesibles = new ArrayList<Zona>(); 
		/* Las zonas comunes no se añaden porque es redundante, todos pueden acceder */
	}
	
	/**
	 * Devuelve una ArrayList con las Zonas accesibles para la Persona.
	 * @return ArrayList<Zona> listaZonasAccesibles de la instancia de Personas
	 */
	public ArrayList<Zona> obtenerListaZonas(){
		return listaZonasAccesibles;
	}
	
	/**
	 * Devuelve la identificación de la Persona.
	 * @return String ID de la instancia de Personas
	 */
	public String obtenerID() {
		return ID;
	}
	
	/**
	 * Devuelve el nombre de la Persona
	 * @return String nombre de la instancia de Personas
	 */
	public String obtenerNombre() {
		return nombre;
	}
	
	/**
	 * Agrega un acceso a la lista de accesos del objeto de clase Persona sobre el cual se trabaja.
	 * @param zona objeto de clase Zona, contiene la zona a la cual se va agregar un Acceso.
	 * @param fecha objeto de clase LocalDate, contiene la fecha a la cual se realiza el Acceso.
	 * @param hora objeto de clase LocalTime, contiene la hora a la cual se realiza el Acceso.
	 * @param cantMins variable de tipo primitivo long, contiene la cantidad de minutos que permanece la persona en la Zona z.
	 * @param estado variable ed tipo primitivo estado, contiene el estado de autorización para el Acceso a la Zona z.
	 */
	public void agregarAcceso(Zona zona, LocalDate fecha, LocalTime hora, long cantMins, boolean estado) {
		/* Agrega un acceso al setAccesos de la Persona, inicializado con los parámetros ingresados. */
		setAccesos.add(new Acceso(zona,fecha,hora,cantMins,estado));
	}

	@Override
	/**
	 * Devuelve un valor boolean que indica si el objeto de clase Personas sobre el cual se trabaja es equivalente al objeto pasado como parametro.
	 * @param o objeto con el cual se compara.
	 * @return boolean true (si son iguales) o false (si son distintos)
	 */
	public boolean equals(Object o) {
		/* Compara a la Persona con otro objeto. */
		if(o == null || o.getClass() != getClass()) // Si se trata de un objeto de clase distinta, devuelve false.
			return false; 
		else // Si se trata de un objeto de clase Personas, devuelve true si tiene la misma ID, y false si no.
			return ID.equals(((Personas)o).ID);
	}

	/**
	 * Devuelve un valor boolean que indica si el objeto de clase Personas sobre el cual se trabaja puede acceder a una Zona.
	 * @param zona objeto de clase Zona, es la zona a la que se quiere saber si la persona puede acceder.
	 * @return boolean true (en el caso de que esté en su lista de zonas accesibles) o false (en el caso de que no)
	 */
	public boolean puedeAcceder(Zona zona) {
	    /* Devuelve un valor boolean que es = true si una persona puede acceder a la Zona ingresada como parámetro, y es = false si no. */
	    if (zona instanceof ZonaComun)
	    	return true;

	    
	    for (Zona z : listaZonasAccesibles) {
	        if (z.getCodigoAlfanumerico().equals(zona.getCodigoAlfanumerico())) {
	            return true;
	        }
	    }

	    return false;
    }

	/**
	 * Imprime todos los atributos de la instancia de Personas.
	 */
	public void imprime_DatosCompletos() {
		/* Imprime todos los datos de la Persona. */
		System.out.println("\nID: " + ID + "\tNOMBRE: " + nombre + "\n\tLISTA DE ZONAS ACCESIBLES\n");
		for(Zona zonaActual : listaZonasAccesibles) // Recorre la listaZonasAccesibles e imprime cada Zona de ésta.
			System.out.println("-> " + zonaActual.toString());
		System.out.println("\n\tLISTA DE ACCESOS\n");
		for(Acceso accesoActual : setAccesos) // Recorre el setAcceso e imprime cada Acceso de éste.
			System.out.println("-> " + accesoActual.toString());
	}
	
	/**
	 * Devuelve la zopa en la cual la persona se encuentra ahora mismo, si es que está en el festival.
	 * @param fecha objeto de clase LocalDate, contiene la fecha actual.
	 * @param hora objeto de clase LocalTime, contiene la hora actual.
	 * @return Zona zona en la que se encuentra la persona
	 * @throws ExcepcionPersonaSeFue excepcion extendida de Exception, se lanza si la cantidad de minutos de permanencia no coinciden con la fecha y hora del último acceso y la fecha y hora actuales.
	 */
	public Zona devolver_ZonaConcurrida(LocalDate fecha, LocalTime hora) throws ExcepcionPersonaSeFue {
		/* Devuelve la zona en la cual la Persona se encuentra actualmente (en la fecha & hora ingresados como parámetro), */
		Acceso ultimoAcceso = setAccesos.getLast();
		if(ultimoAcceso.es_enLaHora(fecha, hora)) /* Si el la fecha, hora y la cantMinutos de permanencia del ultimo Acceso en el setAccesos 
		                                          * concuerdan con la fecha & hora actuales, devuelve la Zona de este Acceso .*/
			return ultimoAcceso.obtener_Zona();
		else // En cambio, si estos no concuerdan, lanza una ExcepcionPersonaSeFue, se asume que la persona ya no se encuentra en el Festival.
			throw new ExcepcionPersonaSeFue("La ubicacion actual de la persona es indefinida.");
	}
}
