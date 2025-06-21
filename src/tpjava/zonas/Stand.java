package tpjava.zonas;
import tpjava.personas.Comerciantes;

public class Stand extends ZonaRestringida implements Comparable<Stand>{
	String ID, ubicacion;
	Comerciantes comercianteResponsable;
	public Stand(String cod, String desc, int capMax, String id, String ubi, Comerciantes comerc) {
		super(cod,desc,capMax);
		ID = id;
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
		System.out.println("CODIGO: " + ID + "\tUBICACION: " + ubicacion + "\tEMPLEADOS: ");
		Festival.mostrar_EmpleadosStand(this);
		System.out.println("\nCOMERCIANTE RESPONSABLE: " + comercianteResponsable);		
	}
	
	@Override
	public int compareTo(Stand otroStand) {
		return Integer.valueOf(comercianteResponsable.obtenerNombre()) - Integer.valueOf(otroStand.obtener_Responsable().obtenerNombre());
	}
}
