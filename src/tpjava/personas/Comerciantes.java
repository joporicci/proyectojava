package tpjava.personas;
import tpjava.zonas.Festival;
import tpjava.zonas.ZonaRestringida;
import tpjava.zonas.Zona;
import tpjava.excepciones.ExcepcionStandNoExiste;

public class Comerciantes extends Personas{  /* NUM: 3 */
	public Comerciantes(String id, String name, ZonaRestringida... zonasRestr) {
		super(id,name);
		try {
		    obtenerListaZonas().add(Festival.devolver_Stand(this)); /* Añade el Stand correspondiente al comerciante */
		}
		catch(ExcepcionStandNoExiste e) {
			System.err.println("Error al buscar Stand de comerciante: " + e); /* Si no lo encuentra, ExcepcionStandNoExiste ocurre */
		}
		for(ZonaRestringida zona : zonasRestr) /* Añade las zonas restringidas determinadas. */
	    	obtenerListaZonas().add((Zona)zona);
	}
}


