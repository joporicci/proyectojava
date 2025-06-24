package tpjava.personas;
import tpjava.zonas.Festival;
import tpjava.zonas.Zona;
import tpjava.zonas.ZonaRestringida;
import tpjava.excepciones.ExcepcionStandNoExiste;

/**
 * Esta clase contiene los atributos y métodos que se necesitan para trabajar con los objetos de clase Comerciantes derivada de Personas.
 * @author grupo2
 */
public class Comerciantes extends Personas {  /* NUM: 3 */
	private String IDJefe; // Se pone la ID del jefe y no una referencia a este debido a que presenta dificultades. */
	/**
	 * Construye un objeto de clase Comerciantes
	 * En el caso de que el Comerciante SEA el jefe, el Comerciante es su propio jefe.
	 * @param id objeto de clase String, contiene el identificador del Comerciante.
	 * @param name objeto de clase String, contiene el nombre del Comerciante.
	 * @param idJefe objeto de clase String, contiene el identificador del jefe del Comerciante.
	 * @param zonasRestr varargs de clase ZonaRestringida, contiene las Zonas Restringidas a las que puede acceder el Comerciante.
	 * @throws ExcepcionStandNoExiste excepción extendida de Exception, se lanza cuando no se encuentra un Stand liderado por un jefe con IDJefe.
	 */
	public Comerciantes(String id, String name, String idJefe, ZonaRestringida... zonasRestr) {
		/* Si el Comerciante es jefe, IDJefe = id */
		super(id,name);
		IDJefe = idJefe;  // Se pone la ID del jefe y no una referencia a este debido a que presenta dificultades. */
		
		for(ZonaRestringida zona : zonasRestr) /* Añade las zonas restringidas determinadas. */
	    	obtenerSetZonas().add((Zona)zona);
		
		try {
		    obtenerSetZonas().add(Festival.devolver_Stand(IDJefe)); /* Añade el Stand correspondiente al jefe. */
		}
		catch(ExcepcionStandNoExiste e) {
			System.err.println("ERROR: Stand de comerciante no encontrado.");
		}

	}
	
	/**
	 * Construye un objeto de clase Comerciantes, es el constructor alternativo por si no se suministra IDJefe, para crear un responsable auxiliar en el cargador xml.
	 * @param id objeto de clase String, contiene el identificador del Comerciante.
	 * @param name objeto de clase String, contiene el nombre del Comerciante.
	 * @param zonasRestr zonasRestr varargs de clase ZonaRestringida, contiene las Zonas Restringidas a las que puede acceder el Comerciante.
	 */
	public Comerciantes(String id, String name, ZonaRestringida... zonasRestr){
		/* Si el Comerciante es jefe, IDJefe = id */
		super(id,name);
		IDJefe = id;  
		
		for(ZonaRestringida zona : zonasRestr) /* Añade las zonas restringidas determinadas. */
	    	obtenerSetZonas().add((Zona)zona);

	}
	
	/**
	 * Devuelve un valor boolean que indica si un objeto de clase Comerciante es empleado del Comerciante cuya ID es un parámetro.
	 * @param idJefe objeto de clase String, contiene el identificador del posible jefe del Comerciante.
	 * @return boolean true (si el Comerciante es empleado del comerciante con esa id) o false (en el caso contrario)
	 */
	public boolean es_EmpleadoDe(String idJefe) {
		/* Devuelve un valor boolean que es = true si la ID ingresada pertenece al jefe del Comerciante, y que es = false si no. */
		return IDJefe.equals(idJefe);
	}
	
	/* El equals de la clase base se hereda automáticamente. */
}


