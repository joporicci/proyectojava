package tpjava.personas;

public class Personas {
	public enum TIPO_PERSONA{
		ASISTENTE,
		ARTISTA,
		STAFF,
		COMERCIANTE
	}
	private Credencial credencial;
	public Personas(String id, String name) {
		credencial = new Credencial(id,name);
	}
	public void configurar_Credencial(TIPO_PERSONA tp) {
		credencial.configurar_Credencial(tp);
	}
	
	
}
