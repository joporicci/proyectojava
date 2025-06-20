package tpjava.personas;
import tpjava.zonas.ZonaRestringida;
import tpjava.zonas.Festival;
import tpjava.excepciones.ExcepcionEscenarioNoExiste;

public class Artistas extends Personas{  /* NUM: 1 */
	public Artistas(String id, String name, ZonaRestringida... zonasRestr) {
		super(id,name);
		try {
			obtenerListaZonas().add(Festival.devolver_Escenario(this));
		}
		catch(ExcepcionEscenarioNoExiste e) {
			System.err.println("Error al buscar escenario del artista: " + e);
		}
		for(ZonaRestringida zonaActual : zonasRestr) 
			obtenerListaZonas().add(zonaActual);
	}
	
	@Override
	public String toString() {
		return obtenerNombre();
	}
	/* El equals de la clase base se hereda automáticamente. */
}
