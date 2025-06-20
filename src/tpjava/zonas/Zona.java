package tpjava.zonas;

public class Zona {
	private String codigoAlfanumerico, descripcion;
	public Zona(String cod, String desc) {
		codigoAlfanumerico = cod;
		descripcion = desc;
	}
	@Override
	public String toString() {
		return "CODIGO ALFANUMERICO: " + codigoAlfanumerico + "   DESCRIPCION: " + descripcion;
	}
	
}
