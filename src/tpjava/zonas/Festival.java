package tpjava.zonas;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.TreeSet;
import tpjava.personas.*;
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
	
	
	public static ArrayList<Zona> devolver_TODAS_Zonas() {  /* Añade todas las zonas no comunes. */
		ArrayList<Zona> lZonas = new ArrayList<>(); 
		for(Zona zonaActual : listaZonas) {
			if(!(zonaActual instanceof ZonaComun))
			    lZonas.add(zonaActual);
		}
		return lZonas;
	}
	
	public static Stand devolver_Stand(Comerciantes comerciante) throws ExcepcionStandNoExiste {
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
	
	public static ArrayList<Escenario> devolver_ListaEscenarios() {
		ArrayList<Escenario> listaEscenarios = new ArrayList<>();
		for(Zona zonaActual : listaZonas)
			if(zonaActual instanceof Escenario)
				listaEscenarios.add((Escenario)zonaActual);
		return listaEscenarios;
	}
}
