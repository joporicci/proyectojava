package tpjava.excepciones;

/**
 * Clase que contiene el constructor de las instancias de ExcepcionZonaNoExiste y sus atributos.
 * @author grupo2
 */
public class ExcepcionZonaNoExiste extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ExcepcionZonaNoExiste(String msj){
    	   super(msj);
       }
}
