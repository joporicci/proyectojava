package tpjava.zonas;
import tpjava.misc.EventoMusical;
import java.util.TreeSet;
import java.util.Iterator;

public class Escenario extends Zona {
	private TreeSet<EventoMusical> setEventos;
	private int capacidadMaxima;
	public Escenario(String cod, String desc) {
		super(cod,desc);
		setEventos = new TreeSet<>();
	}
	/* Desarrollar metodo para agregar eventos. */
	public boolean estaArtista(String artista) {
		Iterator<EventoMusical> iterador = setEventos.iterator();
		EventoMusical auxActual = setEventos.first();
		while(iterador.hasNext() && auxActual.obtener_Artista().equals(artista))
			auxActual = iterador.next();
		return auxActual.obtener_Artista().equals(artista);
	}
}
