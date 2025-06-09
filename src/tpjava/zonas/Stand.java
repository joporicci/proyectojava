package tpjava.zonas;
import tpjava.personas.Comerciantes;

public class Stand extends ZonaRestringida {
	String ID, ubicacion;
	Comerciantes comerciante;
	public Stand(String cod, String desc, int capMax, String id, String ubi, Comerciantes comerc) {
		super(cod,desc,capMax);
		ID = id;
		ubicacion = ubi;
		comerciante = comerc;
	}
	public boolean estaComerciante(Comerciantes comerc) {
		return comerciante.equals(comerc);
	}
}
