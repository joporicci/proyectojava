package tpjava.personas;

import tpjava.zonas.Festival;

public class Asistentes extends Personas {  /* NUM: 0 */
	public Asistentes(String id, String name) {
		super(id,name);
		obtenerListaZonas().addAll(Festival.devolver_ListaEscenarios()); /* Añade como zonas accesibles a todos los escenarios */
	}
	
}
