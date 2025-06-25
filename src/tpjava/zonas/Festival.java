package tpjava.zonas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Map;
import java.util.Iterator;
import tpjava.personas.Personas;
import tpjava.personas.Artistas;
import tpjava.personas.Comerciantes;
import tpjava.excepciones.*;
import java.util.NoSuchElementException;
/**
 * Clase manejadora no instanciable de instancias de Zona y Personas, contiene todos los atributos y métodos para trabajar con las mismas.
 * @author grupo2
 */
public class Festival { 
	private static TreeSet<Zona> setZonas = new TreeSet<>();; /* Almacena TODAS las zonas (ordenadas alfabeticamente segun el codigo) */
	private static ArrayList<Escenario> listaEscenarios = new ArrayList<>();; /* Almacena solo los escenarios, para no tener que buscarlos cada vez que se inicializa un Asistente/Artista */
	private static ArrayList<Stand> listaStands = new ArrayList<>();; /* Almacena solo los Stands, para no tener que buscarlos cada vez que se inicializa un Comerciante */
	private static ArrayList<Personas> listaPersonas = new ArrayList<>(); ; /* Almacena las personas */
	
	/**
	 * Constructor privado de Festival, su única utilidad es hacerla no instanciable.
	 */
	private Festival() {
	}
	
	/**
	 * Agrega una instancia de Zona a mapaZonas y/o a listaEscenarios o listaStands de Festival.
	 * @param zonaNueva, objeto de clase Zona, es la zona a agregar.
	 */
	public static void agregar_Zona(Zona zonaNueva) {
		setZonas.add(zonaNueva);
		if(zonaNueva instanceof Escenario)
			listaEscenarios.add((Escenario)zonaNueva);
		else
			if(zonaNueva instanceof Stand)
				listaStands.add((Stand)zonaNueva);
	}
	
	/**
	 * Agrega una instancia de Personas a listaPersonas.
	 * @param personaNueva
	 */
	public static void agregar_Persona(Personas personaNueva) {
		listaPersonas.add(personaNueva);
	}
	
	/**
	 * Devuelve el escenario en donde actúa un artista.
	 * @param artista instancia de Artistas, contiene al artista que actúa en el escenario buscado.
	 * @return objeto de clase Escenario, escenario donde actúa artista.
	 * @throws ExcepcionEscenarioNoExiste excepción extendida de Exception, se lanza cuando no se encuentra el escenario que se busca.
	 */
	public static Escenario devolver_Escenario(Artistas artista) throws ExcepcionEscenarioNoExiste {  
		/* Devuelve la referencia al escenario de el Artista ingresado como parámetro. */
		Iterator<Escenario> iterador = listaEscenarios.iterator();
		Escenario aux;
		boolean seEncontro = false;
		try {
			aux = listaEscenarios.getFirst();
		    while(iterador.hasNext() && !seEncontro) {
			    if(aux.estaArtista(artista))
			    	seEncontro = true;
			    else
				    aux = iterador.next();
		    }
		    if (seEncontro || aux.estaArtista(artista)) // Si el Artista se encuentra en el ciclo de busqueda o en el último nodo, lo devuelve...
			    return aux;
		    else // ... si no, lanza una ExcepcionEscenarioNoExiste.
			    throw new ExcepcionEscenarioNoExiste("No se encontro escenario alguno en el que el artista actue.");
		}
		catch(NoSuchElementException eElemento) {
			throw new ExcepcionEscenarioNoExiste("No hay ningún escenario en el Festival.");
		}
	}
	
	/**
	 * Devuelve todas las zonas no comunes de Festival en una ArrayList.
	 * (esto es asi para asi evitar redundancia, ya que a las zonas comunes pueden acceder todos)
	 * @return instancia de ArrayList<Zona> con todas las zonas no comunes de mapaZonas.
	 */
	public static TreeSet<Zona> devolver_TODAS_ZonasNOComunes() {  
		return new TreeSet<Zona>(setZonas);
	}
	
	/**
	 * Devuelve el Stand en Festival de un responsable cuya id pertenece a la ingresada en los parámetros.
	 * @param idResponsable objeto de clase String, contiene la id de un comerciante responsable.
	 * @return instancia de Stand de la que es responsable el comerciante.
	 * @throws ExcepcionStandNoExiste excepción extendida de Exception, se lanza cuando el Stand con dicho responsable no se encuentra.
	 */
	public static Stand devolver_Stand(String idResponsable) throws ExcepcionStandNoExiste {  
		/* Devuelve el Stand del comerciante cuya ID es ingresada como parámetro. */
		Iterator<Stand> iterador = listaStands.iterator();
		Stand aux;
		boolean seEncontro;
		try {
			aux = listaStands.getFirst();
			seEncontro = false;
		    while(iterador.hasNext() && !seEncontro) {
			    if(aux.estaResponsable(idResponsable))
			    	seEncontro = true;
			    else
				    aux = iterador.next();
		    }
		    if(!seEncontro && !aux.estaResponsable(idResponsable)) // Si el Stand NO se encuentra en el ciclo ni en el último nodo, se lanza una ExcepcionStandNoExiste...
			    throw new ExcepcionStandNoExiste("El comerciante ingresado no posee un Stand existente!");
		    else // si no, devuelve el Stand encontrado...
			    return (Stand)aux;
		}
		catch(NoSuchElementException eLista) {
			throw new ExcepcionStandNoExiste("La lista de Stands no se inicializó.");
		}
		catch(NullPointerException nP) {
			throw new ExcepcionStandNoExiste("La lista de Stands se encuentra vacía.");
		}
	}
	
	/**
	 * Devuelve una ArrayList con todos los escenarios del Festival.
	 * @return ArrayList<Escenario> listaEscenarios del Festival.
	 */
	public static ArrayList<Escenario> devolver_ListaEscenarios() { 
		return listaEscenarios;
	}
	
	/**
	 * Devuelve una ArrayList con todos los Stands del Festival.
	 * @return ArrayList<Stand> listaStands del Festival.
	 */
	public static ArrayList<Stand> devolver_listaStands(){
		/* Devuelve una lista con todos los Stands del festival. */
		return listaStands;
	}
	
	/**
	 * Devuelve la persona en listaPersonas de Festival cuya id ingresada como parámetro le pertenece.
	 * @param id objeto de clase String, contiene la id de la persona a buscar.
	 * @return instancia de Personas, persona a buscar
	 * @throws ExcepcionPersonaNoExiste excepcion extendida de Exception, se lanza cuando la persona buscada no se encuentra en listaPersonas.
	 */
	public static Personas devolver_Persona(String id) throws ExcepcionPersonaNoExiste {
		/* Devuelve la Persona del id ingresado como parámetro; si no se encuentra se lanza una ExcepcionPersonaNoExiste. */
		Iterator<Personas> iterador = listaPersonas.iterator();
		try {
		    Personas auxPersona = listaPersonas.getFirst(), personaBuscada = new Personas(id,"----");
		    while(iterador.hasNext() && !auxPersona.equals(personaBuscada))
			    auxPersona = iterador.next();
		    if(auxPersona.equals(personaBuscada)) // Si la persona encontrada es la persona buscada, la devuelve (se pregunta esto porque igual se podría haber salido del ciclo solamente porque iterador.hasNext() == false.
			    return auxPersona;
		    else // En cambio, si no lo es, se lanza una ExcepcionPersonaNoExiste.
			    throw new ExcepcionPersonaNoExiste("Persona no encontrada en la base de datos de Festival.");
		}
		catch(NoSuchElementException eLista) {
			throw new ExcepcionPersonaNoExiste("La lista de personas de Festival no fue inicializada.");				
		}
		catch(NullPointerException ePointer) {
			throw new ExcepcionPersonaNoExiste("La lista de personas de Festival está vacía.");
		}
	}
	
	/**
	 * Devuelve un HashMap con todas las zonas del Festival como claves, vinculadas cada una a la cantidad de gente que tienen en el momento como valores.	
	 * @param fechaActual objeto de clase LocalDame, es la fecha en la que se consultan las concurrencias.
	 * @param horaActual objeto de clase LocalTime, es la hora en la que se consultan las concurrencias.
	 * @return objeto colección de clase HashMap<Zona, Integer>, es el mapa con las zonas y cada una de sus concurrencias en el Festival.
	 */
	public static HashMap<Zona, Integer> devolverMapaConcurrencias (LocalDate fechaActual, LocalTime horaActual){
		HashMap<Zona, Integer> mapaContadorConcurrencias = new HashMap<>();
		Zona zonaActual; // La zona actual en el momento de agregar concurrencias a cada zona.
		setZonas.forEach((zona) -> {
			mapaContadorConcurrencias.put(zona, 0);  // Inicializamos mapaContadorConcurrencias con cada zona en concurrencia = 0.
		});
		for(Personas persona : listaPersonas)
			try {
				zonaActual = persona.devolver_ZonaConcurrida(fechaActual, horaActual);
			    mapaContadorConcurrencias.put(zonaActual, mapaContadorConcurrencias.get(zonaActual) + 1);
			}
	        catch(ExcepcionPersonaSeFue e) {
	    	    System.err.println(e.getMessage());
	        }
		return mapaContadorConcurrencias;
		
	}
	
	/**
	 * Lista todas las zonas del Festival ordenadas descendentemente por la concurrencia actual.
	 * @param fechaActual objeto de clase LocalDate, es la fecha actual.
	 * @param horaActual objeto de clase LocalTime, es la hora actual.
	 */
	public static String lista_ZonasPorConcurrencia(LocalDate fechaActual, LocalTime horaActual) {
		int cantTotalPersonas = 0;
		StringBuilder mensaje = new StringBuilder();
		/* Para ir contando la concurrencia actual de cada zona en la hora ingresada, creamos un mapa para contarla. */
		HashMap<Zona, Integer> mapaConcurrencias = devolverMapaConcurrencias(fechaActual, horaActual);
		/* Para ordenar por valores, creamos y ordenamos una lista de Entrys. */
		ArrayList<Map.Entry<Zona, Integer>> listaEntradasZonas = new ArrayList<>(mapaConcurrencias.entrySet());
		listaEntradasZonas.sort(Map.Entry.<Zona, Integer>comparingByValue().reversed()); // Ordenamos cada entrada descendentemente (por eso lo revertimos) comparandolas según su concurrencia.
		for(Map.Entry<Zona, Integer> entradaActual : listaEntradasZonas) {
			/* Agregamos la cantidad de personas de la entradaActual a cantTotalPersonas, e imprimimos las zonas en orden descendente según su concurrencia. */
			cantTotalPersonas += entradaActual.getValue();
			mensaje.append("\n" + entradaActual.getKey().toString());
		}
		mensaje.append("\n\n\n\t\t\tCANTIDAD TOTAL DE PERSONAS EN EL PREDIO = " + cantTotalPersonas);		
		return mensaje.toString();
	}
	
	/**
	 * Muestra una lista con todos los datos de los empleados de la instancia de Stand ingresada como parámetro. 
	 * @param puesto objeto de clase Stand, es el Stand del cual se quieren mostrar los empleados y sus datos.
	 */
	public static String lista_EmpleadosStand(Stand puesto) {
		StringBuilder mensaje = new StringBuilder();
		String idResponsable = puesto.obtener_Responsable().obtenerID();
		for(Personas personaActual : listaPersonas) {
			/* Recorre la listaPersonas completa. */
			if(personaActual instanceof Comerciantes) // Por cada persona que sea Comerciante...
				if(((Comerciantes) personaActual).es_EmpleadoDe(idResponsable)) // ... pregunta si esa persona es empleado del puesto ingresado como parámetro...
					mensaje.append("\n* " + ((Comerciantes)personaActual).toString()); // ... y si lo es, se agregan su nombre e ID al mensaje (usamos el toString de Comerciantes que redefine al de personas para evitar un bucle de recursión infinito).
		}
		return mensaje.toString();
	}
	
	/**
	 * Lista cada uno de los Stands y sus datos ascendentemente por orden alfabético de su código. 
	 */
	public static String lista_StandsAlfabeticamente() {  
		StringBuilder mensaje = new StringBuilder();
		ArrayList<Stand> listaStands = devolver_listaStands();  
		Collections.sort(listaStands);  /* Se ordena la lista Stands ascendentemente */
		for(Stand standActual : listaStands) // Por cada stand se muestran sus datos completos.
			mensaje.append(standActual.toString());
		return mensaje.toString();
	}

	/** 
	 * Devuelve la zona cuyo códigoAlfanumérico fue ingresado como parámetro. Si no la encuentra, lanza ExcepciónZonaNoExiste.
	 * @param zonaID objeto de clase String, es el código de la zona a buscar.
	 * @return objeto de clase Zona, es la zona que se encontró.
	 * @throws ExcepcionZonaNoExiste excepcion extendida de Exception, se lanza cuando no se encuentra la zona con ese código.
	 */
    public static Zona buscarZonaPorID(String zonaID) throws ExcepcionZonaNoExiste {
    	TreeSet<Zona> setZonasAux = new TreeSet<>(setZonas);
    	Iterator<Zona> iterador = setZonasAux.iterator();
    	try {
    	    Zona aux = setZonasAux.getFirst();
            while(iterador.hasNext() && !aux.getCodigoAlfanumerico().equals(zonaID))
        	    aux = iterador.next();
            if(!aux.getCodigoAlfanumerico().equals(zonaID))
                throw new ExcepcionZonaNoExiste("ID de zona no registrado: " + zonaID);
            else
        	    return aux;
    	}
    	catch(NoSuchElementException e) {
    		throw new ExcepcionZonaNoExiste("El set de zonas se encuentra vacío.");
    	}
    }


    /**
     * Método que devuelve un valor boolean que indica si una zona está a su capacidad máxima.
     * @param zona objeto de clase Zona, es la zona a la que se le está consultando si recibe más personas.
     * @param fecha objeto de clase LocalDate, es la fecha en la que se hace la consulta.
     * @param hora objeto de clase LocalTime, es la hora en la que se hace la consulta.
     * @return valor de tipo primitivo boolean, true (si la zona ya no acepta más personas por su capacidad, o si no se sabe) o false (en el caso contrario). 
     */
    public static boolean superaCapacidad(Zona zona, LocalDate fecha, LocalTime hora) {
    	HashMap<Zona, Integer> mapaZonas = devolverMapaConcurrencias(fecha, hora);
        try {
        	if(zona instanceof ZonaRestringida)
        		return mapaZonas.get(zona) >= ((ZonaRestringida) zona).getCapacidad_maxima();
        	else
        		return false;
        }
        catch(NullPointerException ePointer) {
        	System.err.println("La zona cuya capacidad quiere comprobar si es superada no existe en el mapa de Zonas de Festival.");
        	return true;
        }
    }

    /**
     * Método que permite agregar el acceso de una persona a una zona.
     * @param persona objeto de clase Personas, contiene la persona sujeto del nuevo acceso.
     * @param zona objeto de la clase Zona, contiene la zona a la que la persona intenta acceder.
     * @param minutos variable de tipo primitivo long, es la cantidad de minutos de permanencia de la persona en la zona.
     * @param fecha objeto de clase LocalDate, es la fecha del nuevo acceso.
     * @param hora objeto de clase LocalTime, es la hora del nuevo acceso.
     * @throws ExcepcionAccesoIncorrecto excepcion extendida de Exception, se lanza cuando el acceso es denegado, la persona no existe en listaPersonas, o la zona no existe en setZonas.
     */
    public static void modificarAcceso(Personas persona, Zona zona, long minutos, LocalDate fecha, LocalTime hora)
            throws ExcepcionAccesoIncorrecto {
        boolean autorizado = false;
        if (persona.puedeAcceder(zona)) {
            autorizado = true;
        }
        persona.agregarAcceso(zona, fecha, hora, minutos, autorizado);
        if (autorizado) {
          	if(!superaCapacidad(zona,fecha,hora))
                System.out.println("Acceso AUTORIZADO a zona " + zona.toString() + " para persona " + persona.toString());
          	else
         		throw new ExcepcionAccesoIncorrecto("Acceso DENEGADO a zona " + zona.toString() + " para persona " + persona.toString() + "\t(ZONA LLENA)");
        } else {
            throw new ExcepcionAccesoIncorrecto("Acceso DENEGADO a zona " + zona.toString() + " para persona " + persona.toString() + "\t(NO ESTÁ AUTORIZADO)");
        }
    }

    public static ArrayList<Personas> devolverListaPersonas() {
        return new ArrayList<>(listaPersonas); // Retorno copia defensiva
    }

    public static ArrayList<Zona> devolverSetZonas() {
        return new ArrayList<>(setZonas); // Retorno copia defensiva
    }
    
}
