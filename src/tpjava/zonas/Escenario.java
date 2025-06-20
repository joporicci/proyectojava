package tpjava.zonas;
import tpjava.misc.EventoMusical;
import java.util.TreeSet;
import java.util.Iterator;
import tpjava.personas.Artistas;

public class Escenario extends Zona {
	private TreeSet<EventoMusical> setEventos;
	private int capacidadMaxima;
	public Escenario(String cod, String desc) {
		super(cod,desc);
		setEventos = new TreeSet<>();
	}
	/* Desarrollar metodo para agregar eventos. */
	public boolean estaArtista(Artistas artista) {
		Iterator<EventoMusical> iterador = setEventos.iterator();
		EventoMusical auxActual = setEventos.first();
		boolean seEncontro = false;
		while(iterador.hasNext() && !seEncontro) {
			seEncontro = auxActual.obtener_Artista().equals(artista);
			
		}
			
		return seEncontro;
	}
	
	public void listar_EventosProgramados() {
		for(EventoMusical eventoActual : setEventos)
			System.out.println(eventoActual.toString());
	}
}
