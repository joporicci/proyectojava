package tpjava.excepciones;

public class ExcepcionPersonaNoExiste extends Exception{
	   private static final long serialVersionUID = 1;
       public ExcepcionPersonaNoExiste(String msj) {
    	   super(msj);
       }
}
