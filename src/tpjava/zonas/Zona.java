package tpjava.zonas;

import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Iterator;

public class Zona {
	private static TreeSet<String> listaZonas = new TreeSet<>(); 
	private String codigoAlfanumerico, descripcion;
	public Zona(String cod, String desc) {
		codigoAlfanumerico = cod;
		descripcion = desc;
		listaZonas.add(cod); /* ESC: 0, ZC: 1, ZR: 2, STAND: 3  (empieza por ese num segun tipo) */
	}
	public String obtener_codigo() {
		return codigoAlfanumerico;
	}		
}
