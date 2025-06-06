package tpjava.personas;

public class StaffOrganizador extends Personas{  /* NUM: 2 */
	public StaffOrganizador(String id, String name) {
		super(id,name);
		configurar_Credencial(TIPO_PERSONA.STAFF);
	}
}
