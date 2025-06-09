package tpjava.personas;

import java.util.ArrayList;
import tpjava.zonas.Zona;

public class Personas {
	private String ID, nombre;
	private ArrayList<Acceso> listaAccesos;
	private ArrayList<Zona> listaZonasAccesibles;
	public Personas(String id, String name) {
		ID = id;
		nombre = name;
		listaAccesos = new ArrayList<Acceso>();
		listaZonasAccesibles = new ArrayList<Zona>(); 
		/* Las zonas comunes no se añaden porque es redundante, todos pueden acceder */
	}
	
	public ArrayList<Zona> obtenerListaZonas(){
		return listaZonasAccesibles;
	}
}
