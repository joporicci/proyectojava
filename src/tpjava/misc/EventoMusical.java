package tpjava.misc;

import tpjava.personas.Artistas;
import java.time.LocalDate;
import java.time.LocalTime;


public class EventoMusical implements Comparable<EventoMusical> {
    private LocalDate fecha;
    private LocalTime hora;
    private Artistas artista;

    public EventoMusical(LocalDate f, LocalTime h, Artistas a) {
        fecha = f;
        hora = h;
        artista = a;
    }

    public LocalDate obtener_fecha() {
        return fecha;
    }

    public LocalTime obtener_hora() {
        return hora;
    }

    public Artistas obtener_Artista() {
        return artista;
    }

    @Override
    public int compareTo(EventoMusical otro) {
        int compFecha = this.fecha.compareTo(otro.fecha);
        if (compFecha != 0) return compFecha;
        return this.hora.compareTo(otro.hora);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EventoMusical)) return false;
        EventoMusical otro = (EventoMusical) obj;
        return fecha.equals(otro.fecha) && hora.equals(otro.hora);
    }

    @Override
    public int hashCode() {
        return fecha.hashCode() + hora.hashCode();
    }
}
