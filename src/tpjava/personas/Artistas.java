package tpjava.personas;

public class Artistas extends Personas{  /* NUM: 1 */
	public Artistas(String id, String name) {
		super(id,name);
		configurar_Credencial(TIPO_PERSONA.ARTISTA);
	}
}
