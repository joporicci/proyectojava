package tpjava.zonas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.Map;
import java.util.Iterator;
import tpjava.personas.Personas;
import tpjava.personas.Artistas;
import tpjava.personas.Comerciantes;
import tpjava.excepciones.*;

/**
 * Clase manejadora no instanciable de instancias de Zona y Personas, contiene todos los atributos y métodos para trabajar con las mismas.
 * @author grupo2
 */
public class Festival { 
	private static TreeMap<Zona, Integer> mapaZonas = new TreeMap<>();; /* Almacena TODAS las zonas (ordenadas alfabeticamente segun el codigo) + cantidad de gente que tiene c/u */
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
		mapaZonas.put(zonaNueva,0);
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
		Escenario aux = listaEscenarios.getFirst();
		boolean seEncontro = aux.estaArtista(artista);
		while(iterador.hasNext() && !seEncontro) {
			if(aux.estaArtista(artista))
				seEncontro = true;
			else
				aux = iterador.next();
		}
		if (seEncontro) // Si el Artista se encuentra, lo devuelve...
			return aux;
		else // ... si no, lanza una ExcepcionEscenarioNoExiste.
			throw new ExcepcionEscenarioNoExiste("No se encontro escenario alguno en el que el artista actue.");
	}
	
	/**
	 * Devuelve todas las zonas no comunes de Festival en una ArrayList.
	 * (esto es asi para asi evitar redundancia, ya que a las zonas comunes pueden acceder todos)
	 * @return instancia de ArrayList<Zona> con todas las zonas no comunes de mapaZonas.
	 */
	public static ArrayList<Zona> devolver_TODAS_ZonasNOComunes() {  
		return new ArrayList<Zona>(mapaZonas.keySet());
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
		Stand aux = listaStands.getFirst();
		boolean seEncontro = aux.estaResponsable(idResponsable);
		while(iterador.hasNext() && !seEncontro) {
			if(aux.estaResponsable(idResponsable))
				seEncontro = true;
			else
				aux = iterador.next();
		}
		if(!seEncontro) // Si el Stand NO se encuentra, se lanza una ExcepcionStandNoExiste...
			throw new ExcepcionStandNoExiste("El comerciante ingresado no posee un Stand existente!");
		else // si no, devuelve el Stand encontrado...
			return (Stand)aux;
			
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
		Personas auxPersona = listaPersonas.getFirst(), personaBuscada = new Personas(id,"----");
		while(iterador.hasNext() && !auxPersona.equals(personaBuscada))
			auxPersona = iterador.next();
		if(auxPersona.equals(personaBuscada)) // Si la persona encontrada es la persona buscada, la devuelve (se pregunta esto porque igual se podría haber salido del ciclo solamente porque iterador.hasNext() == false.
			return auxPersona;
		else // En cambio, si no lo es, se lanza una ExcepcionPersonaNoExiste.
			throw new ExcepcionPersonaNoExiste("Persona no encontrada en la base de datos de Festival.");
	}
	
	/**
	 * Lista todas las zonas del Festival ordenadas descendentemente por la concurrencia actual.
	 * @param fechaActual objeto de clase LocalDate, es la fecha actual.
	 * @param horaActual objeto de clase LocalTime, es la hora actual.
	 */
	public static void listar_ZonasPorConcurrencia(LocalDate fechaActual, LocalTime horaActual) {
		int cantTotalPersonas = 0;
		/* Como los TreeMap ordenan por Key y no por valores, creamos y ordenamos una lista de Entrys. */
		ArrayList<Map.Entry<Zona, Integer>> listaEntradasZonas = new ArrayList<>(mapaZonas.entrySet());
		listaEntradasZonas.sort(Map.Entry.<Zona, Integer>comparingByValue().reversed()); // Ordenamos cada entrada descendentemente (por eso lo revertimos) comparandolas según su concurrencia.
		for(Map.Entry<Zona, Integer> entradaActual : listaEntradasZonas) {
			/* Agregamos la cantidad de personas de la entradaActual a cantTotalPersonas, e imprimimos las zonas en orden descendente según su concurrencia. */
			cantTotalPersonas += entradaActual.getValue();
			System.out.println(entradaActual.getKey().toString());
		}
		System.out.println("\nCANTIDAD TOTAL DE PERSONAS EN EL PREDIO = " + cantTotalPersonas);		
	}
	
	/**
	 * Muestra una lista con todos los datos de los empleados de la instancia de Stand ingresada como parámetro. 
	 * @param puesto objeto de clase Stand, es el Stand del cual se quieren mostrar los empleados y sus datos.
	 */
	public static void mostrar_EmpleadosStand(Stand puesto) {
		String idResponsable = puesto.obtener_Responsable().obtenerID();
		for(Personas personaActual : listaPersonas) {
			/* Recorre la listaPersonas completa. */
			if(personaActual instanceof Comerciantes) // Por cada persona que sea Comerciante...
				if(((Comerciantes) personaActual).es_EmpleadoDe(idResponsable)) // ... pregunta si esa persona es empleado del puesto ingresado como parámetro...
					System.out.println("* " + personaActual.toString()); // ... y si lo es, se imprimen sus datos.
		}
	}
	
	/**
	 * Lista cada uno de los Stands y sus datos ascendentemente por orden alfabético de su código. 
	 */
	public static void listar_StandsAlfabeticamente() {  
		ArrayList<Stand> listaStands = devolver_listaStands();  
		Collections.sort(listaStands);  /* Se ordena la lista Stands ascendentemente */
		for(Stand standActual : listaStands) // Por cada stand se muestran sus datos completos.
			standActual.mostrar();
	}

	/** 
	 * Devuelve la zona cuyo códigoAlfanumérico fue ingresado como parámetro. Si no la encuentra, lanza ExcepciónZonaNoExiste.
	 * @param zonaID objeto de clase String, es el código de la zona a buscar.
	 * @return objeto de clase Zona, es la zona que se encontró.
	 * @throws ExcepcionZonaNoExiste excepcion extendida de Exception, se lanza cuando no se encuentra la zona con ese código.
	 */
    public static Zona buscarZonaPorID(String zonaID) throws ExcepcionZonaNoExiste {
    	ArrayList<Zona> listaZonasAux = new ArrayList<>(mapaZonas.keySet());
    	Iterator<Zona> iterador = listaZonasAux.iterator();
    	Zona aux = listaZonasAux.getFirst();
        while(iterador.hasNext() && !aux.getCodigoAlfanumerico().equals(zonaID))
        	aux = iterador.next();
        if(!aux.getCodigoAlfanumerico().equals(zonaID))
            throw new ExcepcionZonaNoExiste("ID de zona no registrado: " + zonaID);
        else
        	return aux;
    }

    public static int contarPersonasEnZona(Zona zona) {
        int contador = 0;
        for (Personas p : listaPersonas) {
            if (p.obtenerListaZonas().contains(zona)) {
                contador++;
            }
        }
        return contador;
    }

    public static boolean superaCapacidad(Zona zona) {
        if (zona instanceof ZonaRestringida zonaRest) {
            return contarPersonasEnZona(zonaRest) > zonaRest.getCapacidad_maxima();
        }
        return false;
    }

    public static void modificarAcceso(String personaID, String zonaID, long minutos)
            throws ExcepcionPersonaNoExiste, ExcepcionZonaNoExiste {

        Personas persona = devolver_Persona(personaID);
        Zona zona = buscarZonaPorID(zonaID);
        LocalDate fecha = LocalDate.now();
        LocalTime hora = LocalTime.now();

        boolean autorizado = false;
        if (persona.puedeAcceder(zona) && !superaCapacidad(zona)) {
            autorizado = true;
        }
        persona.agregarAcceso(zona, fecha, hora, minutos, autorizado);
        if (autorizado) {
            System.out.println("Acceso AUTORIZADO a zona " + zonaID + " para persona " + personaID);
        } else {
            System.out.println("Acceso DENEGADO a zona " + zonaID + " para persona " + personaID);
        }
    }

    public static void modificarZonaAccesible(String personaID, String zonaActualID, String zonaNuevaID)
            throws ExcepcionPersonaNoExiste, ExcepcionZonaNoExiste {

        Personas persona = devolver_Persona(personaID);
        Zona zonaActual = buscarZonaPorID(zonaActualID);
        Zona zonaNueva = buscarZonaPorID(zonaNuevaID);

        if (!persona.obtenerListaZonas().contains(zonaActual)) {
            throw new IllegalArgumentException("La persona no tiene acceso a la zona especificada.");
        }

        persona.obtenerListaZonas().remove(zonaActual);
        persona.obtenerListaZonas().add(zonaNueva);
    }

    public static ArrayList<Personas> devolverListaPersonas() {
        return new ArrayList<>(listaPersonas); // Retorno copia defensiva
    }

    public static ArrayList<Zona> devolverListaZonas() {
        return new ArrayList<>(mapaZonas.keySet()); // Retorno copia defensiva
    }
}
