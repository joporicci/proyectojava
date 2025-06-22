package tpjava.personas;
import tpjava.zonas.Festival;
import tpjava.zonas.Zona;
import tpjava.zonas.ZonaRestringida;
import tpjava.excepciones.ExcepcionStandNoExiste;

public class Comerciantes extends Personas {  /* NUM: 3 */
	private String IDJefe;
	/* En el caso de que el Comerciante SEA el jefe, el Comerciante es su propio jefe. */
	public Comerciantes(String id, String name, String idJefe, ZonaRestringida... zonasRestr) {
		/* Si el Comerciante es jefe, IDJefe = id */
		super(id,name);
		IDJefe = idJefe;  // Se pone la ID del jefe y no una referencia a este debido a que presenta dificultades. */
		try {
		    obtenerListaZonas().add(Festival.devolver_Stand(IDJefe)); /* Añade el Stand correspondiente al jefe. */
		}
		catch(ExcepcionStandNoExiste e) {
			System.err.println("Error al buscar Stand de comerciante: " + e); /* Si no lo encuentra, ExcepcionStandNoExiste ocurre */
		}
		for(ZonaRestringida zona : zonasRestr) /* Añade las zonas restringidas determinadas. */
	    	obtenerListaZonas().add((Zona)zona);
	}
	
	public boolean es_EmpleadoDe(String idJefe) {
		return IDJefe.equals(idJefe);
	}
	
	/* El equals de la clase base se hereda automáticamente. */
}


