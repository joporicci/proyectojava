package tpjava.personas;
import java.util.ArrayList;

public class Credencial {
	private String ID, nombre;
	private ArrayList<Acceso> listaAccesos;
	private ArrayList<String> listaZonasAccesibles;
	public Credencial(String id, String name) {
		ID = id;
		nombre = name;
		listaAccesos = new ArrayList<>();
		listaZonasAccesibles = new ArrayList<>();		
	}
	public void agregar_Acceso(String z, String f, String h, float cm, boolean status) {
		listaAccesos.add(new Acceso(z,f,h,cm,status));
	}
}
