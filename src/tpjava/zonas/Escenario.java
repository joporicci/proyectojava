package tpjava.zonas;
import tpjava.misc.EventoMusical;
import tpjava.personas.Artistas;
import tpjava.excepciones.ExcepcionEscenarioSuperpuesto;

import java.util.Iterator;
import java.util.TreeSet;

public class Escenario extends Zona {
    private TreeSet<EventoMusical> setEventos;
    private int capacidadMaxima;

    public Escenario(String cod, String desc, int capMax) {
        super(cod, desc);
        setEventos = new TreeSet<>();
        setCapacidadMaxima(capMax);
    }

	public boolean estaArtista(Artistas artista) {
		/* Devuelve un valor boolean, si el artista está en algún evento musical del evento, devuelve true, si no, false. */
		Iterator<EventoMusical> iterador = setEventos.iterator();
		EventoMusical auxActual = setEventos.first();
		boolean seEncontro = false;
		while(iterador.hasNext() && !seEncontro) {
			seEncontro = auxActual.obtener_Artista().equals(artista);
			auxActual = iterador.next();
		}
		return seEncontro;
	}

    public int getCapacidadMaxima() {
    	/* Devuelve la capacidadMaxima del Escenario. */
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capMax) {
    	/* Define la capacidadMaxima del Escenario. */
        capacidadMaxima = capMax;
    }

    public void agregar_Evento(EventoMusical nuevoEvento) throws ExcepcionEscenarioSuperpuesto {
    	/* Agrega un EventoMusical al Escenario.*/
        for (EventoMusical e : setEventos) {
        	/* Busca si el EventoMusical a añadir ya existe. Si existe, no lo agrega y lanza una ExcepcionEscenarioSuperpuesto. Si no existe, lo agrega. */
            if (e.obtener_fecha().equals(nuevoEvento.obtener_fecha()) &&
                e.obtener_hora().equals(nuevoEvento.obtener_hora())) {
                throw new ExcepcionEscenarioSuperpuesto(
                    "Ya existe un evento en la misma fecha y hora en este escenario.");
            }
        }
        setEventos.add(nuevoEvento);
    }

    public TreeSet<EventoMusical> getEventos() {
    	/* Devuelve el TreeSet de eventos del Escenario. */
        return setEventos;
    }
    
	public void listar_EventosProgramados() {
		/* Lista cada uno de los Eventos Musicales programados en el Escenario. */
		for(EventoMusical eventoActual : setEventos)
			System.out.println(eventoActual.toString());
	}
}
