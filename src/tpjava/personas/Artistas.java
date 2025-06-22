

package tpjava.personas;
import tpjava.excepciones.ExcepcionEscenarioNoExiste;
import tpjava.zonas.Festival;
import tpjava.zonas.ZonaRestringida;

/**
 * Esta clase contiene el constructor específico y método toString para trabajar con los objetos de clase Artistas derivada de Personas,
 * @author grupo2
 */
public class Artistas extends Personas{
	/**
	 * Construye un objeto de clase Artistas
	 * @param id objeto de clase String, contiene el identificador del Artista.
	 * @param name objeto de clase String, contiene el nombre del Artista.
	 * @param zonasRestr varargs de clase ZonaRestringida, contienen las zonasRestringidas a las que puede acceder el Artista.
	 * @throws ExcepcionEscenarioNoExiste excepción extendida de Exception, se lanza cuando no se encuentra un escenario por artista.
	 */
	public Artistas(String id, String name, ZonaRestringida... zonasRestr) throws ExcepcionEscenarioNoExiste {
		super(id,name);
		for(ZonaRestringida zonaActual : zonasRestr) 
			obtenerListaZonas().add(zonaActual);
		try {
		    obtenerListaZonas().add(Festival.devolver_Escenario(this));
		}
		catch(ExcepcionEscenarioNoExiste e) {
			throw e; // Relanza la ExcepcionEscenarioNoExiste.
		}
	}
	
	@Override
	/**
	 * Devuelve el nombre del Artista, para que cuando se quiera pasarlo a String muestre solo éste.
	 * @return objeto de clase String, contiene el nombre del Artista.
	 */
	public String toString() {
		return obtenerNombre();
	}
	/* El equals de la clase base se hereda automáticamente. */
}

