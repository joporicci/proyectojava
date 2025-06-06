package tpjava.personas;
import java.util.ArrayList;

import tpjava.zonas.Festival;
import tpjava.zonas.Zona.NUMERO_ZONA;

import tpjava.personas.Personas.TIPO_PERSONA;
import java.lang.Enum;

public class Credencial {
	private String ID, nombre;
	private ArrayList<Acceso> listaAccesos;
	private ArrayList<String> listaZonasAccesibles;
	public Credencial(String id, String name) {
		ID = id;
		nombre = name;
		listaAccesos = new ArrayList<>();
		listaZonasAccesibles = new ArrayList<>();		
	}
	public void configurar_Credencial(TIPO_PERSONA tp) {
		switch(tp){ /* La constante suministrada indica el tipo de persona... ASIST: 0, ART: 1, STAFF: 2, COMERCIANTE: 3*/
	        case ASISTENTE:
	    	    Festival.devolver_Zonas(NUMERO_ZONA.ESCENARIO, listaZonasAccesibles);    	
	    	    Festival.devolver_Zonas(NUMERO_ZONA.ZONA_COMUN, listaZonasAccesibles);
	    	    break;
	        case ARTISTA:	
	        	Festival.devolver_Escenario(nombre);
	        	/* ¡¡¡¡¡¡Agregar que puedan acceder a determinadas zonas restringidas!!!!!! */
	        	break;
	        case STAFF:
	        	Festival.devolver_TODAS_Zonas(listaZonasAccesibles);	
	        case COMERCIANTE:
	        	Festival.devolver_Stand(nombre);
	        	/* ¡¡¡¡¡¡Agregar que puedan acceder a determinadas zonas restringidas!!!!!! x2 */
	        	break;	        	
	    }
	}
	public void agregar_Acceso(String z, String f, String h, float cm, boolean status) {
		listaAccesos.add(new Acceso(z,f,h,cm,status));
	}
}
