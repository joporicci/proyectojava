package tpjava.zonas;
import tpjava.misc.EventoMusical;
import tpjava.personas.Artistas;
import tpjava.excepciones.ExcepcionEscenarioSuperpuesto;

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
        for (EventoMusical e : setEventos) {
            if (e.obtener_Artista().equals(artista)) {
                return true;
            }
        }
        return false;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capMax) {
        capacidadMaxima = capMax;
    }

    public void agregar_Evento(EventoMusical nuevoEvento) throws ExcepcionEscenarioSuperpuesto {
        for (EventoMusical e : setEventos) {
            if (e.obtener_fecha().equals(nuevoEvento.obtener_fecha()) &&
                e.obtener_hora().equals(nuevoEvento.obtener_hora())) {
                throw new ExcepcionEscenarioSuperpuesto(
                    "Ya existe un evento en la misma fecha y hora en este escenario.");
            }
        }
        setEventos.add(nuevoEvento);
    }

    public TreeSet<EventoMusical> getEventos() {
        return setEventos;
    }
}
