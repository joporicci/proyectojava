package tpjava.personas;

public class Acceso {
	private String zona, fecha, hora; /* Formato de fecha: dia/mes/año */
	private float cantidad_minutos;
	private boolean estado; /* Autorizado? */
	public Acceso(String z, String f, String h, float cm, boolean status) {
		zona = z;
		fecha = f;
		hora = h;
		cantidad_minutos = cm;
		estado = status;
	}
}
