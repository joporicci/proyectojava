package tpjava.zonas;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Iterator;
import java.time.LocalDate;
import java.time.LocalTime;
import tpjava.personas.Personas;
import tpjava.personas.Acceso;
import tpjava.personas.Artistas;
import tpjava.personas.Comerciantes;
import tpjava.zonas.Stand;
import tpjava.excepciones.*;

public class Festival {  /* Clase manejadora de zonas & personas */
	static private ArrayList<Zona> listaZonas; /* Almacena TODAS las zonas */
	static private ArrayList<Escenario> listaEscenarios; /* Almacena solo los escenarios, para no tener que buscarlos cada vez que se inicializa un Asistente/Artista */
	static private ArrayList<Stand> listaStands; /* Almacena solo los Stands, para no tener que buscarlos cada vez que se inicializa un Comerciante */
	static private ArrayList<Personas> listaPersonas; /* Almacena las personas */
	public Festival() {
		listaZonas = new ArrayList<>();
		listaEscenarios = new ArrayList<>();
		listaStands = new ArrayList<>();
		listaPersonas = new ArrayList<>(); 
	}
	public void agregar_Zona(Zona zonaNueva) {
		/* Agrega una persona a listaZonas y/o a listaEscenarios o listaStands de Festival. */
		listaZonas.add(zonaNueva);
		if(zonaNueva instanceof Escenario)
			listaEscenarios.add((Escenario)zonaNueva);
		else
			if(zonaNueva instanceof Stand)
				listaStands.add((Stand)zonaNueva);
	}
	public void agregar_Persona(Personas personaNueva) {
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
		ArrayList<Zona> lZonas = new ArrayList<>(); 
		for(Zona zonaActual : listaZonas) {
			if(!(zonaActual instanceof ZonaComun))
			    lZonas.add(zonaActual);
		}
		return lZonas;
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
		HashMap<Zona, Integer> mapaCantidades = new HashMap<>();  /* Mapa de zonas y su cantidad desordenadas */
		Zona zonaAux;
		int cantTotalPersonas = 0;
		ArrayList<HashMap.Entry<Zona, Integer>> listaEntradasDeZonas; /* Mapa de las de las entradas de las zonas para ordenarlas segun la cantidad a partir de mapaCantidades */
		for(Personas personaActual : listaPersonas) {
			try {
			zonaAux = personaActual.devolver_ZonaConcurrida(fechaActual, horaActual);
			mapaCantidades.put(zonaAux, mapaCantidades.getOrDefault(zonaAux,0) + 1);  /* Si la zona no se agrego, cantidad = 1, si ya está, cantidad = cantidad + 1. */
			}
			catch(ExcepcionPersonaSeFue e) {
				System.err.println("ERROR, ¿la persona de ID " + personaActual.obtenerID() + " se fue?: " + e.getMessage());
			}
		}
		listaEntradasDeZonas = new ArrayList<>(mapaCantidades.entrySet());  /* Le asignamos las entradas de mapaCantidades a listaEntradasDeZonas */
		listaEntradasDeZonas.sort(HashMap.Entry.<Zona, Integer>comparingByValue().reversed());  /* Ordenamos las entradas almacenadas en listaEntradasDeZonas usando el Comparator que vendría a ser según el valor de cada una PERO revirtiendo su orden para hacerlo descendentemente. */
		System.out.println("\tZONAS ORDENADAS DE MAYOR CONCURRENCIA A MENOR\n");
		for(HashMap.Entry<Zona, Integer> entradaMapaActual : listaEntradasDeZonas) {  /* Contamos eficientemente las personas en el predio y listamos las zonas */
			cantTotalPersonas += entradaMapaActual.getValue();
			zonaAux = entradaMapaActual.getKey();
			System.out.println(zonaAux.toString());
			if(zonaAux instanceof Escenario)
				((Escenario) zonaAux).listar_EventosProgramados();
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
}
