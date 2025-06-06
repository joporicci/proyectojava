package tpjava.misc;

public class EventoMusical {
	String fecha, hora, artista;
	public EventoMusical(String f, String h, String a) {
		fecha = f;
		hora = h;
		artista = a;
	}
	public String obtener_Artista() {
		return artista;
	}
}
