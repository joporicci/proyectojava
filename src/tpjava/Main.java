package tpjava;
import tpjava.excepciones.ExcepcionPersonaNoExiste;
import tpjava.personas.Personas;
import tpjava.zonas.Festival;


public class Main {
	public static void main(String args[]) {  /* MÉTODO PRINCIPAL */
		/* ACA VÁ LA CARGA DE ARCHIVOS. */
		try {
		    Festival.devolver_Persona(args[0]).imprime_DatosCompletos(); /* El primer argumento del Main es la id de una persona. */
		    
		    /* Permita mover una persona de una zona determinada a otra para la cual tenga acceso o emitir 
un error de acceso en caso de no estar autorizado o que la zona haya llegado a su capacidad 
máxima. En ambos casos debe registrarse el evento para poder tener la trazabilidad de cada 
persona. */
		    
		}
		catch(ExcepcionPersonaNoExiste e) {
			System.out.println("ERROR, la persona de la ID ingresada no existe: " + e.getMessage());
		}
	}
}
