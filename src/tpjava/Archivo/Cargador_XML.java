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

public class Cargador_XML {
	public static void main(String[] args) {
		cargar_Personas();
		cargar_Zonas();
		
	}
	
	public static void cargar_Personas() {
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
		    
		    nodoTipo = raiz.getElementsByTagName("asistentes");
		    if(nodoTipo.getLength() > 0) {  /* Si hay ASISTENTES... */
		    	listaTipo = ((Element) nodoTipo.item(0)).getElementsByTagName("asistente");
		    	Asistentes asistenteActual;
		    	for(int i = 0; i < listaTipo.getLength(); i++) {
		    		elemento = (Element) listaTipo.item(i);
		    		id = elemento.getElementsByTagName("id").item(0).getTextContent();
		    		nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent();
		    		asistenteActual = new Asistentes(id,nombre);
		    		agregar_AccesosAPersona(elemento, asistenteActual);
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
		    			lZonasR.add((ZonaRestringida)Festival.buscarZonaPorID(elementoZonaR.getElementsByTagName("id").item(0).getTextContent()));
		    		}
		    		artistaAct = new Artistas(id,nombre,(ZonaRestringida[])lZonasR.toArray());
		    		agregar_AccesosAPersona(elemento, artistaAct);
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
		    		listaIDSZonasR = elemento.getElementsByTagName("zonaR");
		    		lZonasR = new ArrayList<>();
		    		for(int j = 0; j < listaIDSZonasR.getLength(); j++) {
		    			elementoZonaR = (Element) listaIDSZonasR.item(j);
		    			lZonasR.add((ZonaRestringida)Festival.buscarZonaPorID(elementoZonaR.getElementsByTagName("id").item(0).getTextContent()));
		    		}
		    		comercianteAct = new Comerciantes(id,nombre,idJefe,(ZonaRestringida[])lZonasR.toArray());
		    		agregar_AccesosAPersona(elemento,comercianteAct);
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
		    		staffActual = new StaffOrganizador(id,nombre);
		    		agregar_AccesosAPersona(elemento,staffActual);
		    		Festival.agregar_Persona(staffActual);
		    	}
		    }
		}
		catch(Exception e) {
			System.out.println("ERROR, No se pudo cargar personas.xml: " + e.getMessage());
		}
	}
	
	public static void agregar_AccesosAPersona(Element elemento, Personas persona) {
		/* Carga los datos principales de la clase base Persona, junto a todos sus accesos. */
	    String idZonaAcceso;
	    LocalDate fechaAcceso;
	    LocalTime horaAcceso;
	    long cantMinsAcceso;
	    boolean estadoAcceso;
	    
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
				System.err.println("ERROR, Zona no encontrada: " + e.getMessage());
			}
		}
	}
	
	
	public static void cargar_Zonas() {
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
		    		elemento = (Element) listaTipo.item(i);
		    		codigoAlfanumerico = elemento.getElementsByTagName("codigo").item(0).getTextContent();
		    		descripcion = elemento.getElementsByTagName("descripcion").item(0).getTextContent();
		    		capacidadMaxima = Integer.parseInt(elemento.getElementsByTagName("capacidad").item(0).getTextContent());
		    		escenarioAct = new Escenario(codigoAlfanumerico,descripcion,capacidadMaxima);
		    		listaEventos = elemento.getElementsByTagName("evento");
		    		for(int j = 0; j < listaEventos.getLength(); j++) {
		    			elementoEvento = (Element) listaEventos.item(j);
		    			fechaEvento = LocalDate.parse(elementoEvento.getElementsByTagName("fecha").item(0).getTextContent());
		    			horaEvento = LocalTime.parse(elementoEvento.getElementsByTagName("hora").item(0).getTextContent());
		    			artistaEvento = (Artistas)Festival.buscarPersonaPorID(elementoEvento.getElementsByTagName("idArtista").item(0).getTextContent());
		    			escenarioAct.agregar_Evento(new EventoMusical(fechaEvento,horaEvento,artistaEvento.obtenerID()));
		    		}
		    		Festival.agregar_Zona(escenarioAct);
		    	}
		    }
		    
		    nodoTipo = raiz.getElementsByTagName("comunes");
		    if(nodoTipo.getLength() > 0) {
		    	listaTipo = ((Element) nodoTipo.item(0)).getElementsByTagName("comun");
		    	ZonaComun zonaComunAct;
		    	for(int i = 0; i < listaTipo.getLength(); i++) {
		    		elemento = (Element) listaTipo.item(i);
		    		codigoAlfanumerico = elemento.getElementsByTagName("codigo").item(0).getTextContent();
		    		descripcion = elemento.getElementsByTagName("descripcion").item(0).getTextContent();
		    		Festival.agregar_Zona(new ZonaComun(codigoAlfanumerico,descripcion));
		    	}
		    }
		    
		    nodoTipo = raiz.getElementsByTagName("restringidas");
		    if(nodoTipo.getLength() > 0) {
		    	listaTipo = ((Element) nodoTipo.item(0)).getElementsByTagName("restringida");
		    	int capacidadMaxima;
		    	for(int i = 0; i < listaTipo.getLength(); i++) {
		    		elemento = (Element) listaTipo.item(i);
		    		codigoAlfanumerico = elemento.getElementsByTagName("codigo").item(0).getTextContent();
		    		descripcion = elemento.getElementsByTagName("descripcion").item(0).getTextContent();
		    		capacidadMaxima = Integer.parseInt(elemento.getElementsByTagName("capacidad").item(0).getTextContent());
		    		Festival.agregar_Zona(new ZonaRestringida(codigoAlfanumerico,descripcion,capacidadMaxima));
		    	}
		    }
		    
		    nodoTipo = raiz.getElementsByTagName("stands");
		    if(nodoTipo.getLength() > 0) {
		    	listaTipo = ((Element) nodoTipo.item(0)).getElementsByTagName("stand");
		    	String ubicacion;
		    	int capacidadMaxima;
		    	Comerciantes responsable;
		    	for(int i = 0; i < listaTipo.getLength(); i++) {
		    		elemento = (Element) listaTipo.item(i);
		    		codigoAlfanumerico = elemento.getElementsByTagName("codigo").item(0).getTextContent();
		    		descripcion = elemento.getElementsByTagName("descripcion").item(0).getTextContent();
		    		capacidadMaxima = Integer.parseInt(elemento.getElementsByTagName("capacidad").item(0).getTextContent());
		    		ubicacion = elemento.getElementsByTagName("ubicacion").item(0).getTextContent();
		    		responsable = (Comerciantes)Festival.buscarPersonaPorID(elemento.getElementsByTagName("idResponsable").item(0).getTextContent());
		    		Festival.agregar_Zona(new Stand(codigoAlfanumerico,descripcion,capacidadMaxima,ubicacion,responsable));	
		    	}
		    }
		}
		catch(Exception e) {
			System.err.println("ERROR, No se pudo cargar zonas.xml: " + e.getMessage());
		}
	}
}
