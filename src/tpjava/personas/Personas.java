package tpjava.personas;

import java.util.ArrayList;
import java.util.LinkedHashSet;
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
		return listaZonasAccesibles;
	}
	
	public String obtenerID() {
		return ID;
	}
	
	public String obtenerNombre() {
		return nombre;
	}

	public void agregarAcceso(Zona zona, LocalDate fecha, LocalTime hora, long cantMins, boolean estado) {
		setAccesos.add(new Acceso(zona,fecha,hora,cantMins,estado));
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null || o.getClass() != getClass())
			return false;
		else
			return ID.equals(((Personas)o).ID);
	}
	
	public boolean puedeAcceder(Zona zona) {
	    
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
		System.out.println("\nID: " + ID + "\tNOMBRE: " + nombre + "\n\tLISTA DE ZONAS ACCESIBLES\n");
		for(Zona zonaActual : listaZonasAccesibles)
			System.out.println("-> " + zonaActual.toString());
		System.out.println("\n\tLISTA DE ACCESOS\n");
		for(Acceso accesoActual : setAccesos)
			System.out.println("-> " + accesoActual.toString());
	}
	
	public Zona devolver_ZonaConcurrida(LocalDate fecha, LocalTime hora) throws ExcepcionPersonaSeFue {
		Acceso ultimoAcceso = setAccesos.getLast();
		if(ultimoAcceso.es_enLaHora(fecha, hora))
			return ultimoAcceso.obtener_Zona();
		else
			throw new ExcepcionPersonaSeFue("La ubicacion actual de la persona es indefinida.");
	}
}
