package tpjava.personas;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.time.LocalDate;
import java.time.LocalTime;
import tpjava.zonas.Zona;
import tpjava.zonas.ZonaComun;
import tpjava.excepciones.ExcepcionPersonaSeFue;


public class Personas{ // En la clase festival manejo las personas. Aca tengo datos indivuales y lista de zonas accesibles
	private String ID, nombre;
	private LinkedHashSet<Acceso> setAccesos;  /* set de Accesos ordenados por el orden de inserción, por lo que el último acceso es el más reciente. */
	private ArrayList<Zona> listaZonasAccesibles;
	public Personas(String id, String name) {
		ID = id;
		nombre = name;
		setAccesos = new LinkedHashSet<Acceso>();
		listaZonasAccesibles = new ArrayList<Zona>(); 
		/* Las zonas comunes no se añaden porque es redundante, todos pueden acceder */
	}
	
	public ArrayList<Zona> obtenerListaZonas(){
		/* Devuelve la listaZonasAccesibles de la Persona. */
		return listaZonasAccesibles;
	}
	
	public String obtenerID() {
		/* Devuelve la ID de la Persona. */
		return ID;
	}
	
	public String obtenerNombre() {
		/* Devuelve el nombre ed la Persona */
		return nombre;
	}
	
	public void agregarAcceso(Zona zona, LocalDate fecha, LocalTime hora, long cantMins, boolean estado) {
		/* Agrega un acceso al setAccesos de la Persona, inicializado con los parámetros ingresados. */
		setAccesos.add(new Acceso(zona,fecha,hora,cantMins,estado));
	}
	
	@Override
	public boolean equals(Object o) {
		/* Compara a la Persona con otro objeto. */
		if(o == null || o.getClass() != getClass()) // Si se trata de un objeto de clase distinta, devuelve false.
			return false; 
		else // Si se trata de un objeto de clase Personas, devuelve true si tiene la misma ID, y false si no.
			return ID.equals(((Personas)o).ID);
	}

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

	public void imprime_DatosCompletos() {
		/* Imprime todos los datos de la Persona. */
		System.out.println("\nID: " + ID + "\tNOMBRE: " + nombre + "\n\tLISTA DE ZONAS ACCESIBLES\n");
		for(Zona zonaActual : listaZonasAccesibles) // Recorre la listaZonasAccesibles e imprime cada Zona de ésta.
			System.out.println("-> " + zonaActual.toString());
		System.out.println("\n\tLISTA DE ACCESOS\n");
		for(Acceso accesoActual : setAccesos) // Recorre el setAcceso e imprime cada Acceso de éste.
			System.out.println("-> " + accesoActual.toString());
	}
	
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
