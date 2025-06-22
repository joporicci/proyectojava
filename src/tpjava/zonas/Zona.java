package tpjava.zonas;

public class Zona implements Comparable<Zona>{
	private String codigoAlfanumerico, descripcion;
	public Zona(String cod, String desc) {
		setCodigoAlfanumerico(cod);
		setDescripcion(desc);
	}

	public String getDescripcion() {
		/* Devuelve la descripción de la Zona. */
		return this.descripcion;
	}
	public void setDescripcion(String desc) {
		/* Define la descripción de la Zona. */
		descripcion = desc; 
	}
	public String getCodigoAlfanumerico() {
		/* Devuelve el codigoAlfanumérico de la Zona. */
		return this.codigoAlfanumerico;
	}
	public void setCodigoAlfanumerico(String codigoAlf) {
		/* Define el codigoAlfanumérico de la Zona. */
		codigoAlfanumerico = codigoAlf;
	}
	
	@Override
	public String toString() {
		/* Devuelve los atributos de la Zona que se quieren mostrar como String... */
		return "CODIGO ALFANUMERICO: " + codigoAlfanumerico + "   DESCRIPCION: " + descripcion;
	}
	
	@Override
	public int compareTo(Zona otraZona) {
		/* Compara la Zona con otraZona según el orden alfabético de sus codigos alfanuméricos. */
		String codigoOtraZona = otraZona.getCodigoAlfanumerico();
		return codigoAlfanumerico.compareTo(codigoOtraZona);
	}
}
