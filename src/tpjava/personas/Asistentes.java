package tpjava.personas;

public class Asistentes extends Personas {  /* NUM: 0 */
	public Asistentes(String id, String name) {
		super(id,name);		
		configurar_Credencial(TIPO_PERSONA.ASISTENTE);
	}
	
}
