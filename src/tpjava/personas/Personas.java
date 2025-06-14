package tpjava.personas;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
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
	
	public String obtenerID() {
		return ID;
	}
	
	public void agregarAcceso(String zona, LocalDate fecha, LocalTime hora, float cantMins, boolean estado) {
		listaAccesos.add(new Acceso(zona,fecha,hora,cantMins,estado));
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null || o.getClass() != getClass())
			return false;
		else
			return ID.equals(((Personas)o).ID);
	}
}
