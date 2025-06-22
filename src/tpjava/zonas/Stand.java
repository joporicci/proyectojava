package tpjava.zonas;
import tpjava.personas.Comerciantes;

public class Stand extends ZonaRestringida{
	String ubicacion;
	Comerciantes comercianteResponsable;
	public Stand(String cod, String desc, int capMax, String ubi, Comerciantes comerc) {
		super(cod,desc,capMax);
		ubicacion = ubi;
		comercianteResponsable = comerc;
	}
	public boolean estaResponsable(String idResponsable) {
		/* Devuelve un valor boolean, si está el responsable devuelve true, si no, false. */
		return comercianteResponsable.obtenerID().equals(idResponsable);
	}
	
	public Comerciantes obtener_Responsable() {
		/* Devuelve el comercianteResponsable. */
		return comercianteResponsable;
	}
		
	public void mostrar() {
		/* Muestra el codigoAlfanumerico, la ubicacion, los empleados y el comercianteResponsable de un Stand. */
		System.out.println("CODIGO: " + getCodigoAlfanumerico() + "\tUBICACION: " + ubicacion + "\tEMPLEADOS: ");
		Festival.mostrar_EmpleadosStand(this);
		System.out.println("\nCOMERCIANTE RESPONSABLE: " + comercianteResponsable);		
	}
	
	@Override
	public int compareTo(Zona otraZona) {
		/* Compara dos Stands según el orden alfabético del nombre del comerciante Responsable. */
		String nombreOtroResponsable;
		if(otraZona instanceof Stand) {
			nombreOtroResponsable = ((Stand) otraZona).obtener_Responsable().obtenerNombre();
			return comercianteResponsable.obtenerNombre().compareTo(nombreOtroResponsable);
		}
		else
			/* Si en lugar de compararlo con otro Stand se lo compara con otro tipo de Zona, se emplea el compareTo de la clase Zona. */
			return super.compareTo(otraZona);
	}

}
