package tpjava.excepciones;

/**
 * Clase que contiene el constructor de las instancias de ExcepcionStandNoExiste y sus atributos.
 * @author grupo2
 */
public class ExcepcionStandNoExiste extends Exception{
	private static final long serialVersionUID = 1L;

	public ExcepcionStandNoExiste(String msj) {
		super(msj);
	}
}
