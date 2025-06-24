package tpjava.zonas;

/**
 * Clase que contiene los atributos y los métodos que se necesitan para trabajar con un objeto de clase Zona.
 * @author grupo2
 */
public class Zona implements Comparable<Zona>{
	private String codigoAlfanumerico, descripcion;
	
	/**
	 * Construye un objeto de clase Zona.
	 * @param cod objeto de clase String, contiene el código alfanumérico de la Zona.
	 * @param desc objeto de clase String, contiene la descripción de la Zona.
	 */
	public Zona(String cod, String desc) {
		setCodigoAlfanumerico(cod);
		setDescripcion(desc);
	}

	/**
	 * Devuelve la descripción de la Zona.
	 * @return objeto de clase String, contiene la descripción de la instancia de Zona.
	 */
	public String getDescripcion() {
		return this.descripcion;
	}
	
	/**
	 * Define la descripción de la Zona al parámetro que se le pasa.
	 * @param desc objeto de clase String, contiene la descripción que se le pondrá a la instancia de Zona.
	 */
	public void setDescripcion(String desc) {
		descripcion = desc; 
	}
	
	/**
	 * Devuelve el codigoAlfanumérico de la Zona.
	 * @return objeto de clase String, contiene el código alfanumérico de la instancia de Zona.
	 */
	public String getCodigoAlfanumerico() {
		return this.codigoAlfanumerico;
	}
	
	/**
	 * Define el codigoAlfanumérico de la Zona.
	 * @param codigoAlf objeto de clase String, contiene el codigo alfanumérico que se pondra a la instancia de Zona.
	 */
	public void setCodigoAlfanumerico(String codigoAlf) {
		codigoAlfanumerico = codigoAlf;
	}
	
	@Override
	/**
	 * Devuelve los atributos de la instancia de Zona que se quieren mostrar como String...
	 * @return objeto de clase String, contiene los atributos a mostrar.
	 */
	public String toString() {
		return "\nCODIGO ALFANUMERICO: " + codigoAlfanumerico + "\tDESCRIPCION: " + descripcion;
	}
	
	@Override
	/**
	 * Compara la instancia de Zona con otraZona según el orden alfabético de sus codigos alfanuméricos.
	 * @return valor de tipo primitivo int, contiene la diferencia entre las dos instancias de Zona (es la comparación de los Strings codigoAlfanumérico de cada zona)
	 */
	public int compareTo(Zona otraZona) {
		String codigoOtraZona = otraZona.getCodigoAlfanumerico();
		return codigoAlfanumerico.compareTo(codigoOtraZona);
	}

}
