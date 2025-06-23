package tpjava.zonas;
import tpjava.misc.EventoMusical;

import tpjava.personas.Artistas;
import tpjava.excepciones.ExcepcionEscenarioSuperpuesto;
import java.util.NoSuchElementException;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * Clase que contiene los atributos y métodos necesarios para trabajar con todos los objetos de clase Escenario derivada de Zona.
 * @author grupo2
 */
public class Escenario extends Zona {
    private TreeSet<EventoMusical> setEventos;
    private int capacidadMaxima;

    /**
     * Construye un objeto de clase Escenario.
     * @param cod objeto de clase String, contiene el codigoAlfanumérico del Escenario.
     * @param desc objeto de clase String, contiene la descripcion del Escenario.
     * @param capMax variable de tipo primitivo int, contiene la capacidad maxima del Escenario.
     */
    public Escenario(String cod, String desc, int capMax) {
        super(cod, desc);
        setEventos = new TreeSet<>();
        setCapacidadMaxima(capMax);
    }

    /**
     * Devuelve un valor boolean que indica si el Escenario contiene Eventos Musicales en su setEventos que tengan como atributo de idArtista
     * la id de un Artista especifico.
     * @param artista objeto de clase Artista, contiene el Artista que se quiere buscar.
     * @return boolean true (si el Artista tiene un EventoMusical en este Escenario) o false (En el caso contrario)
     */
	public boolean estaArtista(Artistas artista) {
		Iterator<EventoMusical> iterador = setEventos.iterator();
		EventoMusical auxActual;
		boolean seEncontro = false;
		try {
			auxActual = setEventos.first();
		    while(iterador.hasNext() && !seEncontro) {
			    seEncontro = auxActual.obtener_Artista().equals(artista);
			    auxActual = iterador.next();
		    }
		    return seEncontro;
		}
		catch(NoSuchElementException eElemento) {
			return false;
		}
	}

	/**
	 * Devuelve la capacidad máxima del Escenario sobre el que se trabaja.
	 * @return int capacidadMaxima del Escenario instanciado.
	 */
    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    /**
     * Define la capacidad máxima del Escenario instanciado como el parámetro que se le pasa.
     * @param capMax valor de tipo primitivo int, contiene la nueva capacidad máxima para el Escenario.
     */
    public void setCapacidadMaxima(int capMax) {
    	/* Define la capacidadMaxima del Escenario. */
        capacidadMaxima = capMax;
    }

    /**
     * Agrega un evento al set de Eventos Musicales del Escenario instanciado, al menos que ya exista uno con esos mismos datos, caso en el que
     * lanza una excepción.
     * @param nuevoEvento objeto de clase EventoMusical, contiene el EventoMusical que se quiere agregar al atributo setEventos
     * @throws ExcepcionEscenarioSuperpuesto excepcion extendida de Exception, se lanza si ya existe un EventoMusical con los mismos datos programado para el mismo objeto de clase Escenario.
     */
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

    /**
     * Devuelve el atributo setEventos del Escenario instanciado.
     * @return TreeSet<EventoMusical> setEventos del Escenario instanciado.
     */
    public TreeSet<EventoMusical> getEventos() {
    	/* Devuelve el TreeSet de eventos del Escenario. */
        return setEventos;
    }
    
    /**
     * Lista cada uno de los Eventos Musicales programados en el Escenario instanciado.
     */
	public void listar_EventosProgramados() {
		for(EventoMusical eventoActual : setEventos)
			System.out.println(eventoActual.toString());
	}
}
