package tpjava.personas;

import tpjava.zonas.Festival;

public class StaffOrganizador extends Personas{  /* NUM: 2 */
	public StaffOrganizador(String id, String name) {
		super(id,name);
		obtenerListaZonas().addAll(Festival.devolver_TODAS_ZonasNOComunes()); /* Añade todas las zonas como accesibles a la lista del Staff */
	}
}
