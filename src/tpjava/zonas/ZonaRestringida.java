package tpjava.zonas;

public class ZonaRestringida extends Zona {
	private int capacidad_maxima;
	public ZonaRestringida(String cod, String desc, int capMax) {
		super(cod,desc);
		capacidad_maxima = capMax;
	}
}
