package tpjava.personas;

import tpjava.zonas.Festival;

/**
 * Clase que contiene los atributos (son los derivados de Personas) y los métodos derivados de Personas + el constructor de todos los objetos
 * de clase StaffOrganizador derivada de Personas.
 * @author grupo2
 */
public class StaffOrganizador extends Personas{ 
	/**
	 * Construye un objeto de clase StaffOrganizador.
	 * @param id objeto de clase String, contiene el identificador del StaffOrganizador.
	 * @param name objeto de clase String, contiene el nombre del StaffOrganizador.
	 */
	public StaffOrganizador(String id, String name) {
		super(id,name);
		obtenerListaZonas().addAll(Festival.devolver_TODAS_ZonasNOComunes()); /* Añade todas las zonas como accesibles a la lista del Staff */
	}
}
