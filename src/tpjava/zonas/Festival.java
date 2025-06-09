package tpjava.zonas;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.TreeSet;
import tpjava.personas.*;
import tpjava.zonas.Zona.NUMERO_ZONA;
import tpjava.excepciones.*;

public class Festival {  /* Clase manejadora de zonas & personas */
	static private TreeSet<Zona> setZonas; /* Almacena las zonas, orden ascendente */
	static private TreeSet<Personas> setPersonas; /* Almacena las personas, orden ascendente */
	public Festival() {
		setZonas = new TreeSet<>();
		setPersonas = new TreeSet<>(); 
	}
	public void agregar_Zona(String cod, String desc) {
		setZonas.add(new Zona(cod,desc));
	}
	public void agregar_Persona(String id, String name) {
		setPersonas.add(new Personas(id,name));
	}
	
	public static Escenario devolver_Escenario(String artista) throws ExcepcionEscenarioNoExiste {  /* Devuelve la referencia al escenario de ese artista */
		Iterator<Zona> iterador = setZonas.iterator();
		Zona aux = setZonas.first();
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
	
	
	public static void devolver_TODAS_Zonas(ArrayList<Zona> lZonas) {
		for(Zona zonaActual : setZonas) {
			lZonas.add(zonaActual);
		}
	}
	
	public static Stand devolver_Stand(Comerciantes comerciante) throws ExcepcionStandNoExiste {
		Iterator<Zona> iterador = setZonas.iterator();
		Zona aux = setZonas.first();
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
}
