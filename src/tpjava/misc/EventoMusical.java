package tpjava.misc;

import tpjava.personas.Artistas;
import tpjava.zonas.Festival;
import tpjava.excepciones.ExcepcionPersonaNoExiste;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Clase que contiene los atributos y los métodos necesarios para trabajar con los objetos de clase EventoMusical. Implementa la interface Comparable.
 * @author grupo2
 */
public class EventoMusical implements Comparable<EventoMusical> {
    private LocalDate fecha;
    private LocalTime hora;
    private Artistas artista;

    /**
     * Construye un objeto de clase EventoMusical.
     * @param f objeto de clase LocalDate, contiene la fecha del evento musical.
     * @param h objeto de clase LocalTime, contiene la hora del evento musical.
     * @param idA objeto de clase String, contiene el id del Artista que actúa en el evento musical.
     */
    public EventoMusical(LocalDate f, LocalTime h, Artistas a) {
        fecha = f;
        hora = h;
        artista = a;
    }

    /**
     * Devuelve la fecha en la que ocurre el EventoMusical instanciado.
     * @return objeto de clase LocalDate, atributo fecha de instancia de EventoMusical.
     */
    public LocalDate obtener_fecha() {
        return fecha;
    }

    /**
     * Devuelve la hora en la que ocurre el EventoMusical instanciado.
     * @return objeto de clase LocalTime, atributo hora de instancia de EventoMusical.
     */
    public LocalTime obtener_hora() {
        return hora;
    }
    
    /**
     * Devuelve el artista cuya id se encuentra como atributo en la instancia de EventoMusical.
     * @return objeto de clase Artistas, contiene el artista al que le pertenece la idArtista.
     */
    public Artistas obtener_Artista() {
    	return artista;
    }

    /**
     * Reemplaza el artista actual de la instancia de EventoMusical por otro, útil en el Cargador_XML.
     * @param a objeto de clase Artistas, es el artista por el cual se reemplazara el artista de está instancia.
     */
    public void reemplazar_Artista(Artistas a) {
    	artista = a;
    }
    
    @Override
    /**
     * Devuelve la diferencia de un EventoMusical con otro.
     * @param otro objeto de clase EventoMusical, es el evento con el que se compara la instancia EventoMusical sobre la que se trabaja.
     * @return variable de tipo primitivo int, es la diferencia entre los dos Eventos Musicales.
     */
    public int compareTo(EventoMusical otro) {
        int compFecha = this.fecha.compareTo(otro.fecha);
        if (compFecha != 0) return compFecha;
            return this.hora.compareTo(otro.hora);
    }

    @Override
    /**
     * Devuelve un valor boolean que indica si EventoMusical y otro objeto son iguales.
     * @param obj objeto, es el objeto con el que se compara la instancia EventoMusical sobre la que se trabaja.
     * @return valor de tipo primitivo boolean, true si son iguales, false si no lo son.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof EventoMusical)) 
        	return false;
        EventoMusical otro = (EventoMusical) obj;
        return fecha.equals(otro.fecha) && hora.equals(otro.hora);
    }

    @Override
    /**
     * Devuelve el hashCode de la instancia de EventoMusical.
     * @return valor de tipo primitivo int, hashCode de la instancia de EventoMusical sobre la que se trabaja.
     */
    public int hashCode() {
        return fecha.hashCode() + hora.hashCode() + artista.hashCode();
    }
    
    @Override
    /**
     * Devuelve un String con los datos más importantes del evento musical.
     * @return objeto de clase String, contiene la fecha, hora y los datos del artista de la instancia de EventoMusical.
     */
    public String toString() {
    	return "FECHA: " + fecha + "\tHORA: " + hora + "\t\tARTISTA: " + artista.toString();
    }
}
