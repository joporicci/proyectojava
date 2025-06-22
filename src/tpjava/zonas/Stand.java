package tpjava.zonas;
import tpjava.personas.Comerciantes;
import tpjava.excepciones.ExcepcionPersonaNoExiste;

/**
 * Clase que contiene todos los atributos y métodos para trabajar con los objetos de clase Stand derivada de ZonaRestringida.
 * @author grupo2
 */
public class Stand extends ZonaRestringida{
	String ubicacion;
	Comerciantes comercianteResponsable;
	
	/**
	 * Construye un objeto de clase Stand.
	 * @param cod objeto de clase String, contiene el código alfanumérico del Stand.
	 * @param desc objeto de clase String, contiene la descripción del Stand.
	 * @param capMax variable de tipo primitivo int, contiene la capacidad máxima del Stand.
	 * @param ubi objeto de clase String, contiene la ubicación del Stand.
	 * @param comerc objeto de clase Comerciantes, contiene al comerciante responsable del Stand.
	 */
	public Stand(String cod, String desc, int capMax, String ubi, Comerciantes comerc) {
		super(cod,desc,capMax);
		ubicacion = ubi;
		comercianteResponsable = comerc;
	}
	
	/**
	 * Busca una persona a la que le pertenece la ID puesta como parámetro y la asigna a comercianteResponsable de la instancia de Stand.
	 * @param idResponsable objeto de clase String, es la id del comerciante que se quiere poner como responsable del Stand.
	 */
	public void poner_Responsable(String idResponsable) {
		try {
		    comercianteResponsable = (Comerciantes)Festival.devolver_Persona(idResponsable);
		}
		catch(ExcepcionPersonaNoExiste e) { // Si no se encuentra a una persona con esta ID, comercianteResponsable será = null. 
			comercianteResponsable = null;
		}
	}
	
	/**
	 * Devuelve un valor boolean, si está el responsable devuelve true, si no, false.
	 * @param idResponsable objeto de clase String, contiene la ID del responsable buscado.
	 * @return variable de tipo primitivo boolean, true si el Stand es de este responsable, false si no.
	 */
	public boolean estaResponsable(String idResponsable) {
		return comercianteResponsable.obtenerID().equals(idResponsable);
	}
	
	/**
	 * Devuelve el comercianteResponsable de la instancia de Stand.
	 * @return objeto de clase Comerciantes, comercianteResponsable del Stand.
	 */
	public Comerciantes obtener_Responsable() {
		return comercianteResponsable;
	}
		
	/**
	 * Muestra el codigoAlfanumerico, la ubicacion, los empleados y el comercianteResponsable de un Stand.
	 */
	public void mostrar() {
		System.out.println("CODIGO: " + getCodigoAlfanumerico() + "\tUBICACION: " + ubicacion + "\tEMPLEADOS: ");
		Festival.mostrar_EmpleadosStand(this);
		System.out.println("\nCOMERCIANTE RESPONSABLE: " + comercianteResponsable);		
	}
	
	@Override
	/**
	 * Compara dos Stands según el orden alfabético del nombre del comerciante Responsable.
	 * @param otraZona objeto de clase Zona, es la zona a comparar con la instancia de Stand sobre la que se trabaja.
	 * @return variable de tipo primitivo int, es la diferencia en enteros de cada carácter del nombre del responsable del Stand, o la diferencia entre el Stand con otra Zona (en ese caso se usa el criterio del compareTo de Zona)
	 */
	public int compareTo(Zona otraZona) {
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
