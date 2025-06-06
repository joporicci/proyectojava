package tpjava.personas;

public class Comerciantes extends Personas{  /* NUM: 3 */
	public Comerciantes(String id, String name) {
		super(id,name);
		configurar_Credencial(TIPO_PERSONA.COMERCIANTE);
	}
}
