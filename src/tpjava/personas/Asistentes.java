package tpjava.personas;

import tpjava.excepciones.ExcepcionPersonaNoExiste;
import tpjava.zonas.Festival;

public class Asistentes extends Personas {  /* NUM: 0 */
	public Asistentes(String id, String name) {
		super(id,name);
		try {
			obtenerListaZonas().addAll(Festival.devolver_ListaEscenarios());
		} catch (ExcepcionPersonaNoExiste e) {
			e.printStackTrace();
		} 
	}
	
}
