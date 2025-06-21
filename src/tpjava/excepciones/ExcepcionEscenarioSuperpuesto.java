package tpjava.excepciones;

public class ExcepcionEscenarioSuperpuesto extends Exception {
    
	// Serializacion de clase con id unico 
	private static final long serialVersionUID = 1L;

	public ExcepcionEscenarioSuperpuesto(String mensaje) {
        super(mensaje);
    }
}
