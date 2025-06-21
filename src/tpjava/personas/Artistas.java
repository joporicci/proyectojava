

package tpjava.personas;
import tpjava.excepciones.ExcepcionEscenarioNoExiste;
import tpjava.zonas.Festival;
import tpjava.zonas.ZonaRestringida;


public class Artistas extends Personas{  /* NUM: 1 */
	public Artistas(String id, String name, ZonaRestringida... zonasRestr) throws tpjava.personas.ExcepcionEscenarioNoExiste, ExcepcionEscenarioNoExiste {
		super(id,name);
		obtenerListaZonas().add(Festival.devolver_Escenario(this));
		for(ZonaRestringida zonaActual : zonasRestr) 
			obtenerListaZonas().add(zonaActual);
	}
	
	/* El equals de la clase base se hereda automáticamente. */
}

