package tpjava.zonas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import tpjava.personas.Personas;
import tpjava.personas.Artistas;
import tpjava.personas.Comerciantes;
import tpjava.zonas.Stand;
import tpjava.excepciones.*;

public class Festival {  /* Clase manejadora de zonas & personas */
	private static HashMap<Zona, Integer> mapaZonas; /* Almacena TODAS las zonas + cantidad de gente que tiene c/u */
	private static ArrayList<Escenario> listaEscenarios; /* Almacena solo los escenarios, para no tener que buscarlos cada vez que se inicializa un Asistente/Artista */
	private static ArrayList<Stand> listaStands; /* Almacena solo los Stands, para no tener que buscarlos cada vez que se inicializa un Comerciante */
	private static ArrayList<Personas> listaPersonas; /* Almacena las personas */
	public Festival() {
		mapaZonas = new HashMap<>();
		listaEscenarios = new ArrayList<>();
		listaStands = new ArrayList<>();
		listaPersonas = new ArrayList<>(); 
	}
	public static void agregar_Zona(Zona zonaNueva) {
		/* Agrega una zona a mapaZonas y/o a listaEscenarios o listaStands de Festival. */
		mapaZonas.put(zonaNueva,0);
		if(zonaNueva instanceof Escenario)
			listaEscenarios.add((Escenario)zonaNueva);
		else
			if(zonaNueva instanceof Stand)
				listaStands.add((Stand)zonaNueva);
	}
	
	public static void agregar_Persona(Personas personaNueva) {
		/* Agrega una persona a listaPersonas de Festival. */
		listaPersonas.add(personaNueva);
	}
	
	public static Escenario devolver_Escenario(Artistas artista) throws ExcepcionEscenarioNoExiste {  /* Devuelve la referencia al escenario de ese artista */
		Iterator<Escenario> iterador = listaEscenarios.iterator();
		Escenario aux = listaEscenarios.getFirst();
		boolean seEncontro = aux.estaArtista(artista);
		while(iterador.hasNext() && !seEncontro) {
			if(aux.estaArtista(artista))
				seEncontro = true;
			else
				aux = iterador.next();
		}
		if (seEncontro)
			return aux;
		else
			throw new ExcepcionEscenarioNoExiste("No se encontro escenario alguno en el que el artista actue.");
	}
	
	public static ArrayList<Zona> devolver_TODAS_ZonasNOComunes() {  
		/* Devuelve una ArrayList con todas las zonas no comunes, para asi evitar redundancia. */
		return new ArrayList<Zona>(mapaZonas.keySet());
	}
	
	public static Stand devolver_Stand(String idResponsable) throws ExcepcionStandNoExiste {  
		/* Devuelve el Stand del comerciante ingresado */
		Iterator<Stand> iterador = listaStands.iterator();
		Stand aux = listaStands.getFirst();
		boolean seEncontro = aux.estaResponsable(idResponsable);
		while(iterador.hasNext() && !seEncontro) {
			if(aux.estaResponsable(idResponsable))
				seEncontro = true;
			else
				aux = iterador.next();
		}
		if(!seEncontro)
			throw new ExcepcionStandNoExiste("El comerciante ingresado no posee un Stand existente!");
		else
			return (Stand)aux;
			
	}
	
	public static ArrayList<Escenario> devolver_ListaEscenarios() { 
		/* Devuelve una lista con todos los Escenarios del festival. */
		return listaEscenarios;
	}
	
	public static ArrayList<Stand> devolver_listaStands(){
		/* Devuelve una lista con todos los Stands del festival. */
		return listaStands;
	}
	
	public static Personas devolver_Persona(String id) throws ExcepcionPersonaNoExiste {
		/* Devuelve la Persona del id ingresado como parámetro; si no se encuentra se tira una ExcepcionPersonaNoExiste. */
		Iterator<Personas> iterador = listaPersonas.iterator();
		Personas auxPersona = listaPersonas.getFirst(), personaBuscada = new Personas(id,"----");
		while(iterador.hasNext() && !auxPersona.equals(personaBuscada))
			auxPersona = iterador.next();
		if(auxPersona.equals(personaBuscada))
			return auxPersona;
		else
			throw new ExcepcionPersonaNoExiste("Persona no encontrada en la base de datos de Festival.");
	}
	
	public static void listar_ZonasPorConcurrencia(LocalDate fechaActual, LocalTime horaActual) {
		/* Lista todas las zonas descendentemente por concurrencia actual. */
		int cantTotalPersonas = 0;
		/* Como los TreeMap ordenan por Key y no por valores, creamos y ordenamos una lista de Entrys. */
		ArrayList<Map.Entry<Zona, Integer>> listaEntradasZonas = new ArrayList<>(mapaZonas.entrySet());
		listaEntradasZonas.sort(Map.Entry.<Zona, Integer>comparingByValue().reversed()); // Ordenamos cada entrada descendentemente (por eso lo revertimos) comparandolas según su concurrencia.
		for(Map.Entry<Zona, Integer> entradaActual : listaEntradasZonas) {
			cantTotalPersonas += entradaActual.getValue();
			System.out.println(entradaActual.getKey());
		}
		System.out.println("\nCANTIDAD TOTAL DE PERSONAS EN EL PREDIO = " + cantTotalPersonas);		
	}
	
	public static void mostrar_EmpleadosStand(Stand puesto) {
		String idResponsable = puesto.obtener_Responsable().obtenerID();
		for(Personas personaActual : listaPersonas) {
			if(personaActual instanceof Comerciantes)
				if(((Comerciantes) personaActual).es_EmpleadoDe(idResponsable))
					System.out.println("* " + personaActual.toString());
		}
	}
	
	public static void listar_StandsAlfabeticamente() {  
		/* Lista cada uno de los Stands ascendentemente por orden alfabético. */
		ArrayList<Stand> listaStands = devolver_listaStands();  
		Collections.sort(listaStands);  /* Se ordena la lista Stands ascendentemente */
		for(Stand standActual : listaStands)
			standActual.mostrar();
	}

    public static Personas buscarPersonaPorID(String personaID) throws ExcepcionPersonaNoExiste {
        for (Personas p : listaPersonas) {
            if (p.obtenerID().equals(personaID)) {
                return p;
            }
        }
        throw new ExcepcionPersonaNoExiste("ID de persona no registrado: " + personaID);
    }

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

        Personas persona = buscarPersonaPorID(personaID);
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

        Personas persona = buscarPersonaPorID(personaID);
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
