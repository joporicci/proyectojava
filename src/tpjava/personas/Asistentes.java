package tpjava.personas;

import tpjava.zonas.Festival;

/**
 * Esta clase contiene el constructor específico de los objetos de clase Asistentes derivada de Personas.
 * @autor grupo2
 */
public class Asistentes extends Personas {  /* NUM: 0 */
	/**
	 * Construye un objeto de clase Asistentes.
	 * @param id objeto de clase String, contiene el identificador del Asistente.
	 * @param name objeto de clase String, contiene el nombre del Asistente.
	 */
	public Asistentes(String id, String name) {
		super(id,name);
		obtenerListaZonas().addAll(Festival.devolver_ListaEscenarios());
	}
	
}
