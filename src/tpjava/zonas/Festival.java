package tpjava.zonas;
import java.util.ArrayList;
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
import tpjava.excepciones.*;

public class Festival {  /* Clase manejadora de zonas & personas */
	static private ArrayList<Zona> listaZonas; /* Almacena las zonas, orden ascendente */
	static private ArrayList<Personas> listaPersonas; /* Almacena las personas, orden ascendente */
	public Festival() {
		listaZonas = new ArrayList<>();
		listaPersonas = new ArrayList<>(); 
	}
	public void agregar_Zona(String cod, String desc) {
		listaZonas.add(new Zona(cod,desc));
	}
	public void agregar_Persona(String id, String name) {
		listaPersonas.add(new Personas(id,name));
	}
	
	public static Escenario devolver_Escenario(Artistas artista) throws ExcepcionEscenarioNoExiste {  /* Devuelve la referencia al escenario de ese artista */
		Iterator<Zona> iterador = listaZonas.iterator();
		Zona aux = listaZonas.getFirst();
		boolean seEncontro = false;
		while(iterador.hasNext() && !seEncontro) {
			if(aux instanceof Escenario) {
				if(((Escenario) aux).estaArtista(artista))
					seEncontro = true;
				else
					aux = iterador.next();
			}
		}
		if (seEncontro)
			return (Escenario)aux;
		else
			throw new ExcepcionEscenarioNoExiste("No se encontro escenario alguno en el que el artista actue.");
	}
	
	
	public static ArrayList<Zona> devolver_TODAS_Zonas() {  /* Devuelve una ArrayList con todas las zonas no comunes. */
		ArrayList<Zona> lZonas = new ArrayList<>(); 
		for(Zona zonaActual : listaZonas) {
			if(!(zonaActual instanceof ZonaComun))
			    lZonas.add(zonaActual);
		}
		return lZonas;
	}
	
	public static Stand devolver_Stand(Comerciantes comerciante) throws ExcepcionStandNoExiste {  /* Devuelve el Stand del comerciante ingresado */
		Iterator<Zona> iterador = listaZonas.iterator();
		Zona aux = listaZonas.getFirst();
		boolean seEncontro = false;
		if(aux instanceof Stand)
			seEncontro = ((Stand) aux).estaComerciante(comerciante);
		while(iterador.hasNext() && !seEncontro) {
			if(aux instanceof Stand)
				seEncontro = ((Stand) aux).estaComerciante(comerciante);
			else
				aux = iterador.next();
		}
		if(!seEncontro)
			throw new ExcepcionStandNoExiste("El comerciante ingresado no posee un Stand existente!");
		else
			return (Stand)aux;
			
	}
	
	public static ArrayList<Escenario> devolver_ListaEscenarios() { /* Devuelve una lista con todos los escenarios del festival */
		ArrayList<Escenario> listaEscenarios = new ArrayList<>();
		for(Zona zonaActual : listaZonas)
			if(zonaActual instanceof Escenario)
				listaEscenarios.add((Escenario)zonaActual);
		return listaEscenarios;
	}
	
	public static Personas devolver_Persona(String id) throws ExcepcionPersonaNoExiste {
		Iterator<Personas> iterador = listaPersonas.iterator();
		Personas auxPersona = listaPersonas.getFirst(), personaBuscada = new Personas(id,"----");
		while(iterador.hasNext() && !auxPersona.equals(personaBuscada))
			auxPersona = iterador.next();
		if(auxPersona.equals(personaBuscada))
			return auxPersona;
		else
			throw new ExcepcionPersonaNoExiste("Persona no encontrada en la base de datos de Festival.");
	}
	
	public static void listar_Zonas(LocalDate fechaActual, LocalTime horaActual) {
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
}
