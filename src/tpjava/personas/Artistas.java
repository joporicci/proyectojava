

package tpjava.personas;
import tpjava.excepciones.ExcepcionEscenarioNoExiste;
import tpjava.zonas.Festival;
import tpjava.zonas.ZonaRestringida;


public class Artistas extends Personas{  /* NUM: 1 */
	public Artistas(String id, String name, ZonaRestringida... zonasRestr) throws ExcepcionEscenarioNoExiste {
		super(id,name);
		obtenerListaZonas().add(Festival.devolver_Escenario(this));
		for(ZonaRestringida zonaActual : zonasRestr) 
			obtenerListaZonas().add(zonaActual);
	}
	
	@Override
	public String toString() {
		return obtenerNombre();
	}
	/* El equals de la clase base se hereda automáticamente. */
}

