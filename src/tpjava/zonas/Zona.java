package tpjava.zonas;

import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Iterator;

public enum ZONAS{
	ESCENARIO, ZONA_COMUN, ZONA_RESTRINGIDA, STAND
}

public class Zona {
	private static TreeSet<String> listaZonas = new TreeSet<>(); /* Almacena los codigos, orden ascendente */
	private String codigoAlfanumerico, descripcion;
	public Zona(String cod, String desc) {
		codigoAlfanumerico = cod;
		descripcion = desc;
		listaZonas.add(cod); /* ESC: 0, ZC: 1, ZR: 2, STAND: 3  (empieza por ese num segun tipo) */
	}
	public static void devolver_Zonas(char tipo, ArrayList<String> lZonas) { /* tipo: ESC, ZC, ZR, STAND. */
		Iterator<String> iterador = listaZonas.iterator();
		String aux = listaZonas.first();
		if(aux.charAt(0) != tipo) {
		    while(iterador.hasNext() && iterador.toString().charAt(0) != tipo) {
			    aux = iterador.next();
		    }
			if(aux.charAt(0) == tipo)
				lZonas.add(aux);
		}
		else {
		    lZonas.add(listaZonas.first());
		    while(iterador.hasNext())
			    lZonas.add(iterador.next());
		}
		
	}
}
