package tpjava.excepciones;

/**
 * Clase que contiene el constructor de las instancias de ExcepcionEscenarioSuperpuesto y sus atributos.
 * @author grupo2
 */
public class ExcepcionEscenarioSuperpuesto extends Exception {
    
	// Serializacion de clase con id unico 
	private static final long serialVersionUID = 1L;

	public ExcepcionEscenarioSuperpuesto(String mensaje) {
        super(mensaje);
    }
}
