package tpjava.zonas;

public class Zona {
	private String codigoAlfanumerico, descripcion;
	public Zona(String cod, String desc) {
		setCodigoAlfanumerico(cod);
		setDescripcion(desc);
	}

	public String getDescripcion() {
		return this.descripcion;
	}
	public void setDescripcion(String desc) {
		descripcion = desc; 
	}
	public String getCodigoAlfanumerico() {
		return this.codigoAlfanumerico;
	}
	public void setCodigoAlfanumerico(String codigoAlf) {
		codigoAlfanumerico = codigoAlf;
	}
	
	@Override
	public String toString() {
		return "CODIGO ALFANUMERICO: " + codigoAlfanumerico + "   DESCRIPCION: " + descripcion;
	}
	
}
