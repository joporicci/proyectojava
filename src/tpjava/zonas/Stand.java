package tpjava.zonas;

public class Stand extends ZonaRestringida {
	String ID, ubicacion, comerciante;
	public Stand(String cod, String desc, int capMax, String id, String ubi, String comerc) {
		super(cod,desc,capMax);
		ID = id;
		ubicacion = ubi;
		comerciante = comerc;
	}
	public boolean estaComerciante(String comerc) {
		return comerciante.equals(comerc);
	}
}
