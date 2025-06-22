package tpjava.excepciones;

/**
 * Clase que contiene el constructor de las instancias de ExcepcionPersonaNoExiste y sus atributos.
 * @author grupo2
 */
public class ExcepcionPersonaNoExiste extends Exception{
	   private static final long serialVersionUID = 1;
       public ExcepcionPersonaNoExiste(String msj) {
    	   super(msj);
       }
}
