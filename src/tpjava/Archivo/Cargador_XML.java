package tpjava.Archivo;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

import java.io.File;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;

import tpjava.zonas.*;
import tpjava.personas.*;
import tpjava.misc.EventoMusical;

import tpjava.excepciones.*;
import java.lang.NullPointerException;

public class Cargador_XML {
	
	private static ArrayList<String> errores;
	
	public static void cargar_Todo() {
		/* Carga la listaPersonas y listaZonas de Festival y añade los errores que ocurran durante la carga a errores. */
		errores = new ArrayList<>(); 
		cargar_Personas();
		cargar_Zonas();
	}
	
	private static void cargar_Personas() {
		/* Carga todas las Personas del archivo personas.xml a la listaPersonas de Festival. */
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
		    		try{
		    			artistaAct = new Artistas(id,nombre,(ZonaRestringida[])lZonasR.toArray());
		    		}
		    		catch(ExcepcionEscenarioNoExiste e) {
		    			errores.add("Escenario de Artista no encontrado - indice " + i + "\tERROR: " + e.getMessage());
		    		}
		    		if(artistaAct != null)
		    		    try {
		    		    	agregar_AccesosAPersona(elemento, artistaAct);
		    		    }
		    		    catch(ExcepcionAccesoIncorrecto e) {
		    			    errores.add(e.getMessage() + "Artista - indice " + i);
		    		    }
		    		else
		    			errores.add("No se pudo inicializar Artista de indice " + i);
		    		Festival.agregar_Persona(artistaAct);		    			
		    	}
		    }
		    
		    nodoTipo = raiz.getElementsByTagName("comerciantes");
		    if(nodoTipo.getLength() > 0) {  /* Si hay COMERCIANTES... */
		    	listaTipo = ((Element) nodoTipo.item(0)).getElementsByTagName("comerciante");
		    	NodeList listaIDSZonasR;
		    	Element elementoZonaR;
		    	ArrayList<ZonaRestringida> lZonasR;
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
		    		if(idJefe == null || idJefe.isEmpty())
		    			errores.add("Comerciante sin idJefe, no se puede buscar Stand - indice " + i);
		    		else
		    			try {
		    				comercianteAct = new Comerciantes(id,nombre,idJefe,(ZonaRestringida[])lZonasR.toArray());
		    			}
		    		    catch(ExcepcionStandNoExiste e) {
		    		    	errores.add("Stand de Comerciante no encontrado - indice " + i + "\tERROR: " + e.getMessage());
		    		    }
		    		
		    		try {
		    		    agregar_AccesosAPersona(elemento,comercianteAct);
		    		}
		    		catch(ExcepcionAccesoIncorrecto e) {
		    			errores.add(e.getMessage() + "Comerciante - indice " + i);
		    		}
		    		Festival.agregar_Persona(comercianteAct);
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
		catch(Exception e) {
			System.out.println("ERROR, No se pudo cargar personas.xml: " + e.getMessage());
		}
	}
	
	private static void agregar_AccesosAPersona(Element elemento, Personas persona) throws ExcepcionAccesoIncorrecto{
		/* Carga los datos principales de la clase base Persona, junto a todos sus accesos. */
	    String idZonaAcceso;
	    LocalDate fechaAcceso;
	    LocalTime horaAcceso;
	    long cantMinsAcceso;
	    boolean estadoAcceso, huboError = false;
	    
	    NodeList listaAccesos = elemento.getElementsByTagName("acceso");
	    Element elementoAcceso;
	    
		for(int j = 0; j < listaAccesos.getLength(); j++) {
			elementoAcceso = (Element) listaAccesos.item(j);
			idZonaAcceso = elementoAcceso.getElementsByTagName("idZona").item(0).getTextContent();
			fechaAcceso = LocalDate.parse(elementoAcceso.getElementsByTagName("fecha").item(0).getTextContent());
			horaAcceso = LocalTime.parse(elementoAcceso.getElementsByTagName("hora").item(0).getTextContent());
			cantMinsAcceso = Long.parseLong(elementoAcceso.getElementsByTagName("cantMins").item(0).getTextContent());
			estadoAcceso = Boolean.parseBoolean(elementoAcceso.getElementsByTagName("estado").item(0).getTextContent());
			try {
			    persona.agregarAcceso(Festival.buscarZonaPorID(idZonaAcceso), fechaAcceso, horaAcceso, cantMinsAcceso, estadoAcceso);	
			}
			catch(ExcepcionZonaNoExiste e) {
				errores.add("ERROR, Acceso a zona de indice " +  j + "no encontrada: " + e.getMessage());
				huboError = true;
			}
		}
		
		if(huboError) {
			throw new ExcepcionAccesoIncorrecto("Los errores de carga de accesos de arriba pertenecen a:");
		}
	}
	
	private static void chequear_Persona(String id, String nombre, int i, String tipo) {
		/* Chequea que se hayan cargado correctamente id y nombre. Si no, añade los errores correspondientes a la lista, especificando
		 * el tipo de persona y el indice para poder localizarlos. */ 
		if(id == null || id.isEmpty())
			errores.add(tipo + " sin id - indice " + i);
		if(nombre == null || nombre.isEmpty())
			errores.add(tipo + " sin nombre - indice " + i);
	}
	
	private static void cargar_Zonas() {
		/* Carga todas las Zonas del archivo zonas.xml a la listaZonas de Festival. */
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
		    	Artistas artistaEvento;
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
		    			    artistaEvento = (Artistas)Festival.buscarPersonaPorID(elementoEvento.getElementsByTagName("idArtista").item(0).getTextContent());
		    			    escenarioAct.agregar_Evento(new EventoMusical(fechaEvento,horaEvento,artistaEvento.obtenerID()));
		    			}
		    			catch(NullPointerException eN) {
		    				errores.add("Fecha u hora de evento musical no encontrado - indice " + i + "-" + j + "\tERROR: " + eN.getMessage());		    				
		    			}
		    			catch(ExcepcionPersonaNoExiste e) {
		    				errores.add("Artista de evento musical no encontrado - indice " + i + "-" + j + "\tERROR: " + e.getMessage());
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
		    	Comerciantes responsable;
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
		    			/* Se intenta buscar al responsable del Stand, si se lo encuentra se asigna a responsable... */
		    		    responsable = (Comerciantes)Festival.buscarPersonaPorID(elemento.getElementsByTagName("idResponsable").item(0).getTextContent());
		    		}
		    		catch(ExcepcionPersonaNoExiste e) {
		    			/* ... en cambio, si no se lo encuentra, se añade un error a la lista errores y se le asigna null a responsable. */
		    			errores.add("Responsable de Stand no encontrado - indice " + i);
		    			responsable = null; 
		    		}
		    		Festival.agregar_Zona(new Stand(codigoAlfanumerico,descripcion,capacidadMaxima,ubicacion,responsable));	
		    	}
		    }
		}
		catch(Exception e) {
			System.err.println("ERROR, No se pudo cargar zonas.xml: " + e.getMessage());
		}
	}
	
	private static void chequear_Zona(String codigo, String desc, int i, String tipo) {
		/* Chequea que se hayan cargado correctamente codigoAlfanumerico y descripcion. Si no, añade los errores correspondientes a la lista, especificando
		 * el tipo de zona y el indice para poder localizarlos. */ 
		if(codigo == null || codigo.isEmpty())
			errores.add(tipo + " sin codigoAlfanumerico - indice " + i);
		if(desc == null || desc.isEmpty())
			errores.add(tipo + " sin descripcion - indice " + i);
	}
	
	private static int obtener_capacidadMaxima(NodeList auxCapacidad) throws ExcepcionCapacidadIndefinida{
		/* Obtiene la capacidadMaxima en el elemento capacidadMaxima, si no existe este elemento dentro de la Zona, lanza ExcepcionCapacidadIndefinida. */
		if(auxCapacidad.getLength() > 0)
			/* Si existe capacidadMaxima, la devuelve. */
			return Integer.parseInt(auxCapacidad.item(0).getTextContent());
		else {
			/* Si no existe capacidadMaxima, lanza ExcepciónCapacidadIndefinida */
			throw new ExcepcionCapacidadIndefinida(" sin capacidadMaxima definida - indice ");
		}
	}
	
	public static ArrayList<String> obtener_Errores(){
		return errores;
	}
}
