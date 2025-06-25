package tpjava.Archivo;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.LocalTime;

import tpjava.zonas.*;
import tpjava.personas.*;
import tpjava.misc.EventoMusical;

import tpjava.excepciones.*;
import java.lang.NullPointerException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
/**
 * Clase que contiene los atributos y métodos necesarios para trabajar con los objetos de clase Cargador_XML, cuya función es cargar los datos
 * del programa a partir de un archivo XML.
 * @author grupo2
 */
public class Cargador_XML {
	
	private static ArrayList<String> errores; // Guarda todos los errores que ocurran durante la carga total de los datos.
	private static HashMap<EventoMusical, Artistas> mapaEventosPendientes; // Guarda pares de Eventos Musicales y Artistas para asignar cada artista a su evento musical a la hora de cargar las personas.
	/**
	 * Carga la listaPersonas y listaZonas de Festival y añade los errores que ocurran durante la carga a errores. 
	 */
	public static void cargar_Todo() {
		errores = new ArrayList<>(); 
		cargar_Zonas(); // Se cargan primero las zonas y se crea un mapaStandsPendientes...
		cargar_Personas(); // ... y se cargan luego las personas, para así poder asignar los comerciantes responsables de cada Stand con las personas ya cargadas.
	}
	/**
	 * Carga todas las Personas del archivo personas.xml a la listaPersonas de Festival. 
	 */
	private static void cargar_Personas() {
		try {
		    File archivo = new File("personas.xml");
		    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		    DocumentBuilder db = dbf.newDocumentBuilder();
		    Document documento = db.parse(archivo);
		
		    Element raiz = documento.getDocumentElement();
		    
		    NodeList nodoTipo, listaTipo;
		    Element elemento;
		    
		    String id, nombre;
		    
		    raiz.normalize();
		    
		    /* NOTA: Las personas, si falta algún elemento, se cargan igual pero sin el/los elementos faltantes. */
		    
		    nodoTipo = raiz.getElementsByTagName("asistentes");
		    if(nodoTipo.getLength() > 0) {  /* Si hay ASISTENTES... */
		    	listaTipo = ((Element) nodoTipo.item(0)).getElementsByTagName("asistente");
		    	Asistentes asistenteActual;
		    	for(int i = 0; i < listaTipo.getLength(); i++) {
		    		elemento = (Element) listaTipo.item(i);
		    		id = elemento.getElementsByTagName("id").item(0).getTextContent();
		    		nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent();
		    		chequear_Persona(id,nombre,i,"Asistente");
		    		asistenteActual = new Asistentes(id,nombre);
		    		try {
		    			agregar_AccesosAPersona(elemento, asistenteActual);		    			
		    		}
		    		catch(ExcepcionAccesoIncorrecto e) {
		    			errores.add(e.getMessage() + "Asistente - indice " + i);
		    		}
		    		Festival.agregar_Persona(asistenteActual);
		    	}
		    }
		    
		    nodoTipo = raiz.getElementsByTagName("artistas");
		    if(nodoTipo.getLength() > 0) {  /* Si hay ARTISTAS... */
		    	listaTipo = ((Element) nodoTipo.item(0)).getElementsByTagName("artista");
		    	NodeList listaIDSZonasR;
		    	Element elementoZonaR;
		    	ArrayList<ZonaRestringida> lZonasR;
		    	ZonaRestringida[] vectorZonasR;
		    	Artistas artistaAct;
		    	for(int i = 0; i < listaTipo.getLength(); i++) {
		    		elemento = (Element) listaTipo.item(i);
		    		id = elemento.getElementsByTagName("id").item(0).getTextContent();
		    		nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent();
		    		listaIDSZonasR = elemento.getElementsByTagName("zonaR");
		    		lZonasR = new ArrayList<>();
		    		for(int j = 0; j < listaIDSZonasR.getLength(); j++) {
		    			elementoZonaR = (Element) listaIDSZonasR.item(j);
		    			try {
		    			    lZonasR.add((ZonaRestringida)Festival.buscarZonaPorID(elementoZonaR.getElementsByTagName("id").item(0).getTextContent()));
		    			}
		    			catch(ExcepcionZonaNoExiste e) {
		    				errores.add("ZonaRestringida de Artista no encontrada - indice " + i + "-" + j + "\tERROR: " + e.getMessage());
		    			}
		    		}
		    		chequear_Persona(id,nombre,i,"Artista");
		    		artistaAct = null;  // Se inicializa artistaAct en null por si ocurre una excepción durante la inicialización efectiva.
		    		vectorZonasR = lZonasR.toArray(new ZonaRestringida[0]);
		    		artistaAct = new Artistas(id,nombre,vectorZonasR);
		    		try {
		    		   	agregar_AccesosAPersona(elemento, artistaAct);
		    	    }
		    	    catch(ExcepcionAccesoIncorrecto e) {
		    		    errores.add(e.getMessage() + "Artista - indice " + i);
		    	    }
	    		   	for(HashMap.Entry<EventoMusical, Artistas> entradaAct : mapaEventosPendientes.entrySet())  // Recorremos el mapa de eventos pendientes y...
	    		   		try {
	    		   		if(entradaAct.getValue().equals(artistaAct)) // ... si encontramos una entrada valor.equals(artistaAct), entonces reemplazamos el artista del Evento Musical por artistaAct.
	    		   			entradaAct.getKey().reemplazar_Artista(artistaAct);
	    		   		}
	    		   	    catch(NullPointerException e) {
	    		   	    	errores.add("ERROR GRAVE: Puntero nulo en valor Artista de entrada de EventoMusical " + entradaAct.getKey().toString() + "\tERROR: " + e.getMessage());
	    		   	    }
		    	    Festival.agregar_Persona(artistaAct);	
		    	}
		    }
		    
		    nodoTipo = raiz.getElementsByTagName("comerciantes");
		    if(nodoTipo.getLength() > 0) {  /* Si hay COMERCIANTES... */
		    	listaTipo = ((Element) nodoTipo.item(0)).getElementsByTagName("comerciante");
		    	NodeList listaIDSZonasR;
		    	Element elementoZonaR;
		    	ArrayList<ZonaRestringida> lZonasR;
		    	ZonaRestringida[] vectorZonasR;
		    	String idJefe;
		    	Comerciantes comercianteAct;
		    	for(int i = 0; i < listaTipo.getLength(); i++) {
		    		elemento = (Element) listaTipo.item(i);
		    		id = elemento.getElementsByTagName("id").item(0).getTextContent();
		    		idJefe = elemento.getElementsByTagName("idJefe").item(0).getTextContent();
		    		nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent();
		    		chequear_Persona(id,nombre,i,"Comerciante");
		    		listaIDSZonasR = elemento.getElementsByTagName("zonaR");
		    		lZonasR = new ArrayList<>();
		    		for(int j = 0; j < listaIDSZonasR.getLength(); j++) {
		    			elementoZonaR = (Element) listaIDSZonasR.item(j);
		    			try{
		    				lZonasR.add((ZonaRestringida)Festival.buscarZonaPorID(elementoZonaR.getElementsByTagName("id").item(0).getTextContent()));
		    			}
		    			catch(ExcepcionZonaNoExiste e) {
		    				errores.add("ZonaRestringida de Comerciante no encontrada - indice " + i + "-" + j + "\tERROR: " + e.getMessage());
		    			}
		    		}
		    		comercianteAct = null; // Se inicializa comercianteAct en null por si ocurre una excepción durante la inicialización efectiva.
		    		vectorZonasR = lZonasR.toArray(new ZonaRestringida[0]);
		    		if(idJefe == null || idJefe.isEmpty()) {
		    			errores.add("Comerciante sin idJefe, no se puede buscar Stand - indice " + i);
		    			comercianteAct = new Comerciantes(id,nombre,vectorZonasR);
		    		}
		    		else
		    			try {
		    				comercianteAct = new Comerciantes(id,nombre,idJefe,vectorZonasR);
		    				if (comercianteAct.es_EmpleadoDe(id))
		    					Festival.devolver_Stand(idJefe).cambiar_Responsable(comercianteAct); // Se reemplaza al comerciante auxiliar del Stand por el verdadero.
		    			}
		    		    catch(ExcepcionStandNoExiste e) {
		    		    	errores.add("Stand de Comerciante no encontrado - indice " + i + "\tERROR: " + e.getMessage());
		    		    }
		    		
		    		if(comercianteAct != null)
		    		    try {
		    		        agregar_AccesosAPersona(elemento,comercianteAct);
		    		        Festival.agregar_Persona(comercianteAct);
		    		    }
		    		    catch(ExcepcionAccesoIncorrecto e) {
		    		    	errores.add(e.getMessage() + "Comerciante - indice " + i);
		    		    }
		    	}
		    }
		    
		    nodoTipo = raiz.getElementsByTagName("staffs");
		    if(nodoTipo.getLength() > 0) {
		    	listaTipo = ((Element) nodoTipo.item(0)).getElementsByTagName("staff");
		    	StaffOrganizador staffActual;
		    	for(int i = 0; i < listaTipo.getLength(); i++) {
		    		elemento = (Element) listaTipo.item(i);
		    		id = elemento.getElementsByTagName("id").item(0).getTextContent();
		    		nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent();
		    		chequear_Persona(id, nombre, i,"StaffOrganizador");
		    		staffActual = new StaffOrganizador(id,nombre);
		    		try {
		    		    agregar_AccesosAPersona(elemento,staffActual);
		    		}
		    		catch(ExcepcionAccesoIncorrecto e) {
		    			errores.add(e.getMessage() + "StaffOrganizador - indice " + i);
		    		}
		    		Festival.agregar_Persona(staffActual);
		    	}
		    }
		}
		catch(IOException eIO) {
			System.err.println("ERROR, No se pudo cargar personas.xml: " + eIO.getMessage());
		}
		catch(ParserConfigurationException eParser) {
			System.err.println("ERROR, No se pudo crear el DocumentBuilder de personas.xml: " + eParser.getMessage());			
		}
		catch(SAXException eSAX) {
			System.err.println("ERROR, personas.xml está mal formado: " + eSAX.getMessage());
		}
	}
	
	/**
	 * Carga los datos principales de la clase base Persona, junto a todos sus accesos.
	 * @param elemento objeto de clase Element, contiene el elemento del XML de una persona.
	 * @param persona objeto de la clase Personas, es la persona a la que se le van a agregar los accesos.
	 * @throws ExcepcionAccesoIncorrecto excepcion extendida de Exception, se lanza cuando hay al menos un error de carga de Acceso a una Zona.
	 */
	private static void agregar_AccesosAPersona(Element elemento, Personas persona) throws ExcepcionAccesoIncorrecto{
	    String idZonaAcceso;
	    LocalDate fechaAcceso;
	    LocalTime horaAcceso;
	    long cantMinsAcceso;
	    boolean estadoAcceso, huboError = false;
	    
	    NodeList listaAccesos = elemento.getElementsByTagName("acceso");
	    Element elementoAcceso;
	    
		for(int j = 0; j < listaAccesos.getLength(); j++) {
			try {
			    elementoAcceso = (Element) listaAccesos.item(j);
			    idZonaAcceso = elementoAcceso.getElementsByTagName("idZona").item(0).getTextContent();
			    fechaAcceso = LocalDate.parse(elementoAcceso.getElementsByTagName("fecha").item(0).getTextContent());
			    horaAcceso = LocalTime.parse(elementoAcceso.getElementsByTagName("hora").item(0).getTextContent());
			    cantMinsAcceso = Long.parseLong(elementoAcceso.getElementsByTagName("cantMins").item(0).getTextContent());
			    estadoAcceso = Boolean.parseBoolean(elementoAcceso.getElementsByTagName("estado").item(0).getTextContent());
			    persona.agregarAcceso(Festival.buscarZonaPorID(idZonaAcceso), fechaAcceso, horaAcceso, cantMinsAcceso, estadoAcceso);	
			}
			catch(ExcepcionZonaNoExiste e) {
				errores.add("ERROR, Acceso a zona de indice " +  j + "no encontrada: " + e.getMessage());
				huboError = true;
			}
			catch(NullPointerException ePointer) {
				errores.add("ERROR, Elemento de Acceso de indice " + j + " no existe: " + ePointer.getMessage());
			}
		}
		
		if(huboError) {
			throw new ExcepcionAccesoIncorrecto("Los errores de carga de accesos de arriba pertenecen a:");
		}
	}
	
	/**
	 * Chequea que se hayan cargado correctamente id y nombre. Si no, añade los errores correspondientes a la lista, especificando
		 * el tipo de persona y el indice para poder localizarlos.
	 * @param id objeto de clase String, contiene la ID de la persona leída en el XML.
	 * @param nombre objeto de clase String, contiene el nombre de la persona leída en el XML.
	 * @param i variable de tipo primitivo int, contiene el indice del Asistente/Artista/Comerciante/StaffOrganizador.
	 * @param tipo objeto de clase String, contiene el tipo de persona.
	 */
	private static void chequear_Persona(String id, String nombre, int i, String tipo) {
		if(id == null || id.isEmpty())
			errores.add(tipo + " sin id - indice " + i);
		if(nombre == null || nombre.isEmpty())
			errores.add(tipo + " sin nombre - indice " + i);
	}
	
	/**
	 * Carga todas las Zonas del archivo zonas.xml a la listaZonas de Festival.
	 */
	private static void cargar_Zonas() {
		try {
		    File archivo = new File("zonas.xml");
		
		    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		    DocumentBuilder db = dbf.newDocumentBuilder();
		    Document documento = db.parse(archivo);
		    documento.getDocumentElement().normalize();
		    
		    Element raiz = documento.getDocumentElement();
		    
		    NodeList nodoTipo, listaTipo;
		    Element elemento;
		    
		    String codigoAlfanumerico, descripcion;
		    
		    /* Nota: las zonas, si falta algún elemento se cargan igual pero sin el/los elemento faltantes. */
		    
		    nodoTipo = raiz.getElementsByTagName("escenarios");
		    if(nodoTipo.getLength() > 0) {  /* Si hay ESCENARIOS... */
		    	listaTipo = ((Element) nodoTipo.item(0)).getElementsByTagName("escenario");
		    	Escenario escenarioAct;
		    	int capacidadMaxima;
		    	NodeList listaEventos;
		    	Element elementoEvento;
		    	
		    	LocalDate fechaEvento;
		    	LocalTime horaEvento;
		    	Artistas artistaEventoAux;
		    	EventoMusical eventoAct;
		    	
		    	mapaEventosPendientes = new HashMap<>();
		    	for(int i = 0; i < listaTipo.getLength(); i++) {
		    		/* Carga de Escenarios uno por uno */
		    		elemento = (Element) listaTipo.item(i);
		    		codigoAlfanumerico = elemento.getElementsByTagName("codigo").item(0).getTextContent();
		    		descripcion = elemento.getElementsByTagName("descripcion").item(0).getTextContent();
		    		chequear_Zona(codigoAlfanumerico,descripcion,i,"Escenario");
		    		try {
		    			/* Si existe capacidadMaxima, se asigna.,, */
		    		    capacidadMaxima = obtener_capacidadMaxima(elemento.getElementsByTagName("capacidad"));
		    		}
		    		catch(ExcepcionCapacidadIndefinida e) {
		    			/* ... en cambio, si no existe, se añade un error a la lista errores y se le asigna capacidadMaxima un valor x defecto (0). */
		    			errores.add("Escenario" + e.getMessage() + i);
		    			capacidadMaxima = 0;
		    		}
		    		escenarioAct = new Escenario(codigoAlfanumerico,descripcion,capacidadMaxima);
		    		listaEventos = elemento.getElementsByTagName("evento");
		    		for(int j = 0; j < listaEventos.getLength(); j++) {
		    			/* Carga de lista de Eventos Musicales del Escenario uno por uno. Si no se encuentra la fecha, la hora o el artista de un evento, este no se agrega a la lista. */
		    			elementoEvento = (Element) listaEventos.item(j);
		    			try {
		    			    fechaEvento = LocalDate.parse(elementoEvento.getElementsByTagName("fecha").item(0).getTextContent());
		    			    horaEvento = LocalTime.parse(elementoEvento.getElementsByTagName("hora").item(0).getTextContent());
		    			    artistaEventoAux = new Artistas(elementoEvento.getElementsByTagName("idArtista").item(0).getTextContent(),"NOMBRE INDEFINIDO");  // Se crea un artista auxiliar que luego se reemplazará por el verdadero al cargar las personas.
		    			    try {
		    			    	eventoAct = new EventoMusical(fechaEvento,horaEvento,artistaEventoAux);
		    			        escenarioAct.agregar_Evento(eventoAct);
		    			        mapaEventosPendientes.put(eventoAct, artistaEventoAux);
		    			    }
		    			    catch(ExcepcionEscenarioSuperpuesto eEVENTO) {
		    			    	errores.add("Advertencia: ya existe un evento musical como el del Escenario de indice " + i + "-" + j + "\tERROR: " + eEVENTO.getMessage());
		    			    }
		    			
		    			} 
		    			catch(NullPointerException eN) {
		    				errores.add("Fecha u hora de evento musical no encontrado - indice " + i + "-" + j + "\tERROR: " + eN.getMessage());		    				
		    			}
		    		}
		    		Festival.agregar_Zona(escenarioAct);
		    	}
		    }
		    
		    nodoTipo = raiz.getElementsByTagName("comunes");
		    if(nodoTipo.getLength() > 0) {  /* Si hay ZONAS COMUNES... */
		    	listaTipo = ((Element) nodoTipo.item(0)).getElementsByTagName("comun");
		    	for(int i = 0; i < listaTipo.getLength(); i++) {
		    		/* Carga de Zonas Comunes una por una... */
		    		elemento = (Element) listaTipo.item(i);
		    		codigoAlfanumerico = elemento.getElementsByTagName("codigo").item(0).getTextContent();
		    		descripcion = elemento.getElementsByTagName("descripcion").item(0).getTextContent();
		    		chequear_Zona(codigoAlfanumerico,descripcion,i,"ZonaComun");
		    		Festival.agregar_Zona(new ZonaComun(codigoAlfanumerico,descripcion));
		    	}
		    }
		    
		    nodoTipo = raiz.getElementsByTagName("restringidas");
		    if(nodoTipo.getLength() > 0) {  /* Si hay ZONAS RESTRINGIDAS... */
		    	listaTipo = ((Element) nodoTipo.item(0)).getElementsByTagName("restringida");
		    	int capacidadMaxima;
		    	for(int i = 0; i < listaTipo.getLength(); i++) {
		    		/* Carga de Zonas Restringidas una por una... */
		    		elemento = (Element) listaTipo.item(i);
		    		codigoAlfanumerico = elemento.getElementsByTagName("codigo").item(0).getTextContent();
		    		descripcion = elemento.getElementsByTagName("descripcion").item(0).getTextContent();
		    		chequear_Zona(codigoAlfanumerico,descripcion,i,"ZonaRestringida");
		    		try {
		    			/* Si existe capacidadMaxima, se asigna... */
		    		    capacidadMaxima = obtener_capacidadMaxima(elemento.getElementsByTagName("capacidad"));
		    		}
		    		catch(ExcepcionCapacidadIndefinida e) {
		    			/* ... en cambio, si no existe, se añade un error a la lista errores y se le asigna capacidadMaxima un valor x defecto (0). */
		    			errores.add("ZonaRestringida" + e.getMessage() + i);
		    			capacidadMaxima = 0;
		    		}
		    		Festival.agregar_Zona(new ZonaRestringida(codigoAlfanumerico,descripcion,capacidadMaxima));
		    	}
		    }
		    
		    nodoTipo = raiz.getElementsByTagName("stands");
		    if(nodoTipo.getLength() > 0) {  /* SI HAY STANDS... */
		    	listaTipo = ((Element) nodoTipo.item(0)).getElementsByTagName("stand");
		    	String ubicacion;
		    	int capacidadMaxima;
		    	String idResponsable;
		    	Comerciantes responsableAuxiliar;
		    	Stand standActual;
		    	for(int i = 0; i < listaTipo.getLength(); i++) {
		    		/* Carga de Stands uno por uno... */
		    		elemento = (Element) listaTipo.item(i);
		    		codigoAlfanumerico = elemento.getElementsByTagName("codigo").item(0).getTextContent();
		    		descripcion = elemento.getElementsByTagName("descripcion").item(0).getTextContent();
		    		chequear_Zona(codigoAlfanumerico,descripcion,i,"Stand");
		    		try {
		    			/* Si existe capacidadMaxima, se asigna... */
		    		    capacidadMaxima = obtener_capacidadMaxima(elemento.getElementsByTagName("capacidad"));
		    		}
		    		catch(ExcepcionCapacidadIndefinida e) {
		    			/* ... en cambio, si no existe, se añade un error a la lista errores y se le asigna capacidadMaxima un valor x defecto (0). */
		    			errores.add("ZonaRestringida" + e.getMessage() + i);
		    			capacidadMaxima = 0;
		    		}
		    		ubicacion = elemento.getElementsByTagName("ubicacion").item(0).getTextContent();
		    		if(ubicacion == null || ubicacion.isEmpty())
		    			errores.add("Ubicacion de Stand no encontrada - indice " + i);
		    		
		    		try {
		    			/* Se intenta buscar la id del Responsable del Stand, si se lo encuentra se guardan el Stand y se crea un comerciante responsable auxiliar que se reemplaza luego de cargar las personas... */
		    		    idResponsable = elemento.getElementsByTagName("idResponsable").item(0).getTextContent();
		    		    responsableAuxiliar = new Comerciantes(idResponsable,"NOMBRE INDEFINIDO");
		    		    standActual = new Stand(codigoAlfanumerico,descripcion,capacidadMaxima,ubicacion,responsableAuxiliar);
		    		    Festival.agregar_Zona(standActual);
		    		}
		    		catch(NullPointerException e) {
		    			/* ... en cambio, si no se la encuentra, se añade un error a la lista errores y se le asigna null a responsable. */
		    			errores.add("idResponsable de Stand no encontrada - indice " + i);
		    			Festival.agregar_Zona(new Stand(codigoAlfanumerico,descripcion,capacidadMaxima,ubicacion,null));	
		    		}
		    	}
		    }
		}
		catch(IOException eIO) {
			System.err.println("ERROR, No se pudo cargar zonas.xml: " + eIO.getMessage());
		}
		catch(ParserConfigurationException eParser) {
			System.err.println("ERROR, No se pudo crear el DocumentBuilder de zonas.xml: " + eParser.getMessage());			
		}
		catch(SAXException eSAX) {
			System.err.println("ERROR, zonas.xml está mal formado: " + eSAX.getMessage());
		}
	}
	
	/**
	 * Chequea que se hayan cargado correctamente codigoAlfanumerico y descripcion. Si no, añade los errores correspondientes a la lista, especificando
		 * el tipo de zona y el indice para poder localizarlos.
	 * @param codigo objeto de clase String, contiene el codigoAlfanumerico de la zona que se leyó en el XML.
	 * @param desc objeto de clase String, contiene la descripcion de la zona que se leyó en el XML.
	 * @param i variable de tipo primitivo int, contiene el índice del Escenario/Stand/ZonaComun/ZonaRestringida.
	 * @param tipo objeto de tipo String, contiene el tipo de Zona.
	 */
	private static void chequear_Zona(String codigo, String desc, int i, String tipo) {
		if(codigo == null || codigo.isEmpty())
			errores.add(tipo + " sin codigoAlfanumerico - indice " + i);
		if(desc == null || desc.isEmpty())
			errores.add(tipo + " sin descripcion - indice " + i);
	}
	
	/**
	 * Obtiene la capacidadMaxima en el elemento capacidadMaxima, si no existe este elemento dentro de la Zona, lanza ExcepcionCapacidadIndefinida.
	 * @param auxCapacidad objeto de clase NodeList, contiene la lista de nodos de "capacidad" de una ZonaRestringida o Escenario
	 * @return variable de tipo int, capacidad_maxima de la instancia de ZonaRestringida o Escenario cargada.
	 * @throws ExcepcionCapacidadIndefinida excepcion extendida de Exception, se lanza cuando no se encuentra el elemento "capacidad".
	 */
	private static int obtener_capacidadMaxima(NodeList auxCapacidad) throws ExcepcionCapacidadIndefinida{
		if(auxCapacidad.getLength() > 0)
			/* Si existe capacidadMaxima, la devuelve. */
			return Integer.parseInt(auxCapacidad.item(0).getTextContent());
		else {
			/* Si no existe capacidadMaxima, lanza ExcepciónCapacidadIndefinida */
			throw new ExcepcionCapacidadIndefinida(" sin capacidadMaxima definida - indice ");
		}
	}
	
	/**
	 * Obtiene la lista de mensajes de error que ocurrieron durante la carga de datos.
	 * @return ArrayList<String> de mensajes de error.
	 */
	public static ArrayList<String> obtener_Errores(){
		return errores;
	}
}
