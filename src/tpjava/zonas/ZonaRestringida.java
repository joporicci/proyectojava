package tpjava.zonas;

public class ZonaRestringida extends Zona {
	private int capacidad_maxima;
	public ZonaRestringida(String cod, String desc, int capMax) {
		super(cod,desc);
		setCapacidad_maxima(capMax);
	}
	public int getCapacidad_maxima() {
		return this.capacidad_maxima;
	}
	public void setCapacidad_maxima(int capMax) {
		capacidad_maxima = capMax; 
	}
}
