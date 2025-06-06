package tpjava.zonas;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;
import tpjava.personas.*;
import tpjava.zonas.Zona.NUMERO_ZONA;

public class Festival {  /* Clase manejadora de zonas & personas */
	static private TreeSet<Zona> setZonas; /* Almacena las zonas, orden ascendente */
	static private TreeSet<Personas> setPersonas; /* Almacena las personas, orden ascendente */
	public Festival() {
		setZonas = new TreeSet<>();
		setPersonas = new TreeSet<>(); 
	}
	public void agregar_Zona(String cod, String desc) {
		setZonas.add(new Zona(cod,desc));
	}
	public void agregar_Persona(String id, String name) {
		setPersonas.add(new Personas(id,name));
	}
	public static String devolver_Escenario(String artista) {  /* Devuelve el codigo alfanumerico del escenario de ese artista */
		Iterator<Zona> iterador = setZonas.iterator();
		Zona aux = setZonas.first();
		boolean seEncontro = false;
		final char tipoEscenario = (char)(NUMERO_ZONA.ESCENARIO.ordinal() + '0');  /* Constante para no llamar a .ordinal() cada ciclo del while. */
		while(iterador.hasNext() && aux.obtener_codigo().charAt(0) != tipoEscenario) {
			aux = iterador.next();			
		}
		if(aux.obtener_codigo().charAt(0) == tipoEscenario)
			while(iterador.hasNext() && aux.obtener_codigo().charAt(0) == tipoEscenario && !seEncontro) {
				if(aux instanceof Escenario)
					seEncontro = ((Escenario) aux).estaArtista(artista);
				else
					aux = iterador.next();
			}
		return seEncontro ? aux.obtener_codigo() : "NO EXISTE";
	}
	public static void devolver_Zonas(NUMERO_ZONA tipoEnum, ArrayList<String> lZonas) { /* tipo: ESC, ZC, ZR, STAND. */
		final char tipo = (char)(tipoEnum.ordinal() + '0');
		Iterator<Zona> iterador = setZonas.iterator();
		String aux = setZonas.first().obtener_codigo();
		if(aux.charAt(0) != tipo) {
		    while(iterador.hasNext() && aux.charAt(0) != tipo) {  /* NOTA: Chequear por ineficiencia antes de entregar. */
			    aux = iterador.next().obtener_codigo();
		    }
			if(aux.charAt(0) == tipo) {
				lZonas.add(aux);
				while(iterador.hasNext() && aux.charAt(0) == tipo) {
					aux = iterador.next().obtener_codigo();					
				    lZonas.add(aux);
				}
			}
		}
		else {
		    lZonas.add(aux);
		    while(iterador.hasNext() && aux.charAt(0) == tipo)
		    	aux = iterador.next().obtener_codigo();
			    lZonas.add(aux);
		}
	}
	public static void devolver_TODAS_Zonas(ArrayList<String> lZonas) {
		for(Zona zonaActual : setZonas) {
			lZonas.add(zonaActual.obtener_codigo());
		}
	}
	public static String devolver_Stand(String comerciante) {
		final char tipoStand = (char)(NUMERO_ZONA.STAND.ordinal() + '0');
		Iterator<Zona> iterador = setZonas.iterator();
		Zona aux = setZonas.first();
		boolean seEncontro = false;
		if(aux.obtener_codigo().charAt(0) != tipoStand) {
			while(iterador.hasNext() && aux.obtener_codigo().charAt(0) != tipoStand)
				aux = iterador.next();
			if(aux.obtener_codigo().charAt(0) == tipoStand) {
				while(iterador.hasNext() && aux.obtener_codigo().charAt(0) == tipoStand && !seEncontro)
					if(aux instanceof Stand)
						seEncontro = ((Stand) aux).estaComerciante(comerciante);
					else
						aux = iterador.next();
			}
		}
		return seEncontro ? aux.obtener_codigo() : "NO EXISTE";
	}
}
