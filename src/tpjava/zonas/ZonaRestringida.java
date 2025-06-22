package tpjava.zonas;

/**
 * Clase que contiene todos los atributos y los métodos que se necesitan para trabajar con un objeto de clase ZonaRestringida derivada de Zona.
 */
public class ZonaRestringida extends Zona {
	private int capacidad_maxima;
	/**
	 * Construye un objeto de clase ZonaRestringida.
	 * @param cod objeto de clase String, contiene el código alfanumérico de la Zona Restringida.
	 * @param desc objeto de clase String, contiene la descripción de la ZonaRestringida.
	 * @param capMax variable de tipo primitivo int, contiene la capacidad máxima de la ZonaRestringida.
	 */
	public ZonaRestringida(String cod, String desc, int capMax) {
		super(cod,desc);
		setCapacidad_maxima(capMax);
	}
	
	/**
	 * Devuelve la capacidad_maxima de la instancia de ZonaRestringida.
	 * @return variable de tipo primitivo int = capacidad_maxima de la instancia de ZonaRestringida.
	 */
	public int getCapacidad_maxima() {
		return this.capacidad_maxima;
	}
	
	/**
	 * Define la capacidad_maxima de la instancia de ZonaRestringida.
	 * @param capMax variable de tipo primitivo int, contiene el valor que se le asignará a capacidad_maxima.
	 */
	public void setCapacidad_maxima(int capMax) {
		capacidad_maxima = capMax; 
	}
}
