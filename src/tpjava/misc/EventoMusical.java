package tpjava.misc;

import tpjava.personas.Artistas;
import tpjava.zonas.Festival;
import tpjava.excepciones.ExcepcionPersonaNoExiste;

import java.time.LocalDate;
import java.time.LocalTime;


public class EventoMusical implements Comparable<EventoMusical> {
    private LocalDate fecha;
    private LocalTime hora;
    private String idArtista;  /* Es la ID y no el artista debido a que si no se dificulta la carga de datos. */

    public EventoMusical(LocalDate f, LocalTime h, String idA) {
        fecha = f;
        hora = h;
        idArtista = idA;
    }

    public LocalDate obtener_fecha() {
        return fecha;
    }

    public LocalTime obtener_hora() {
        return hora;
    }

    public Artistas obtener_Artista() {
    	try {
            return (Artistas)Festival.devolver_Persona(idArtista);
    	}
    	catch(ExcepcionPersonaNoExiste e) {
    		System.err.println("ERROR, El artista del evento no se encontro: " + e.getMessage());
    		return null;
    	}
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
