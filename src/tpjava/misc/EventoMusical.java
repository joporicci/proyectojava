package tpjava.misc;
import tpjava.personas.Artistas;
import java.time.LocalDate;
import java.time.LocalTime;

public class EventoMusical {
	LocalDate fecha;
	LocalTime hora;
	Artistas artista;
	public EventoMusical(LocalDate f, LocalTime h, Artistas a) {
		fecha = f;
		hora = h;
		artista = a;
	}
	public Artistas obtener_Artista() {
		return artista;
	}
	
	@Override
	public String toString() {
		return "FECHA: " + fecha.toString() + "\tHORA: " + hora.toString() +  "\tARTISTA: " + artista.toString();  
	}
}
