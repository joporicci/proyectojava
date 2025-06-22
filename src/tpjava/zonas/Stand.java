package tpjava.zonas;
import tpjava.personas.Comerciantes;

public class Stand extends ZonaRestringida implements Comparable<Stand>{
	String ubicacion;
	Comerciantes comercianteResponsable;
	public Stand(String cod, String desc, int capMax, String ubi, Comerciantes comerc) {
		super(cod,desc,capMax);
		ubicacion = ubi;
		comercianteResponsable = comerc;
	}
	public boolean estaResponsable(String idResponsable) {
		return comercianteResponsable.obtenerID().equals(idResponsable);
	}
	
	public Comerciantes obtener_Responsable() {
		return comercianteResponsable;
	}
		
	public void mostrar() {
		System.out.println("CODIGO: " + getCodigoAlfanumerico() + "\tUBICACION: " + ubicacion + "\tEMPLEADOS: ");
		Festival.mostrar_EmpleadosStand(this);
		System.out.println("\nCOMERCIANTE RESPONSABLE: " + comercianteResponsable);		
	}
	
	@Override
	public int compareTo(Stand otroStand) {
		return Integer.valueOf(comercianteResponsable.obtenerNombre()) - Integer.valueOf(otroStand.obtener_Responsable().obtenerNombre());

	}

}
