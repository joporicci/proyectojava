package tpjava.personas;

public class Personas {
	private Credencial credencial;
	public Personas(String id, String name) {
		credencial = new Credencial(id,name);
	}
}
