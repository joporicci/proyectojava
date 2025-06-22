package tpjava.zonas;

/**
 * Clase que contiene los atributos (todos derivados de Zona) y los métodos que se necesitan para trabajar con un objeto de clase ZonaComun derivada de Zona.
 * @author grupo2
 */
public class ZonaComun extends Zona{
	/**
	 * Construye un objeto de clase ZonaComun.
	 * @param cod objeto de clase String, contiene el código alfanumérico de la Zona Común.
	 * @param desc objeto de clase String, contiene el código alfanumérico de la Zona Común.
	 */
	public ZonaComun(String cod, String desc) {
		super(cod,desc);
	}
}
