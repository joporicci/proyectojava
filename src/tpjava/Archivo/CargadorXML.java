package tpjava.Archivo;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import tpjava.personas.*;
import tpjava.zonas.*;
import tpjava.misc.EventoMusical;
import tpjava.zonas.Festival;
import tpjava.excepciones.ExcepcionEscenarioSuperpuesto;

public class CargadorXML {

    private ArrayList<String> errores = new ArrayList<>();

    public void cargarDesdeArchivo(String rutaArchivo) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // DOM builder
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(rutaArchivo));
            doc.getDocumentElement().normalize();

            cargarZonas(doc);
            cargarPersonas(doc);
            cargarEventos(doc);
            imprimirErrores();
        } catch (Exception e) {
            System.out.println("Error general al leer el archivo XML: " + e.getMessage());
        }
    }

    private void cargarZonas(Document doc) {
        NodeList zonas = doc.getElementsByTagName("zona");

        for (int i = 0; i < zonas.getLength(); i++) {
            Node nodo = zonas.item(i);
            if (nodo.getNodeType() != Node.ELEMENT_NODE) continue;

            Element elemento = (Element) nodo;
            String tipo = elemento.getAttribute("tipo");
            String cod = elemento.getAttribute("codigo");
            String desc = elemento.getAttribute("descripcion");

            if (cod.isEmpty() || desc.isEmpty()) {
                errores.add("Zona con atributos incompletos en posición " + i);
                continue;
            }

            try {
                Zona zona = null;
                switch (tipo) {
                    case "comun":
                        zona = new ZonaComun(cod, desc);
                        break;
                    case "restringida":
                        int capRes = Integer.parseInt(elemento.getAttribute("capacidad"));
                        zona = new ZonaRestringida(cod, desc, capRes);
                        break;
                    case "escenario":
                        int capEsc = Integer.parseInt(elemento.getAttribute("capacidad"));
                        Escenario escenario = new Escenario(cod, desc, capEsc);
                        NodeList eventosMusicales = elemento.getElementsByTagName("eventoMusical");

                        for (int j = 0; j < eventosMusicales.getLength(); j++) {
                            Element ev = (Element) eventosMusicales.item(j);
                            String fechaStr = ev.getAttribute("fecha");
                            String horaStr = ev.getAttribute("hora");
                            String artistaID = ev.getAttribute("artistaID");

                            if (fechaStr.isEmpty() || horaStr.isEmpty() || artistaID.isEmpty()) {
                                errores.add("Evento musical incompleto en escenario " + cod);
                                continue;
                            }

                            LocalDate fecha = LocalDate.parse(fechaStr);
                            LocalTime hora = LocalTime.parse(horaStr);
                            Artistas artista = new Artistas(artistaID, ""); // TEMP hasta resolver por ID real
                            EventoMusical evento = new EventoMusical(fecha, hora, artista);

                            try {
                                escenario.agregar_Evento(evento);
                            } catch (ExcepcionEscenarioSuperpuesto ex) {
                                errores.add("Evento superpuesto en escenario " + cod + ": " + ex.getMessage());
                            }
                        }

                        zona = escenario;
                        break;
                    case "stand":
                        int cap = Integer.parseInt(elemento.getAttribute("capacidad"));
                        String idStand = elemento.getAttribute("id");
                        String ubicacion = elemento.getAttribute("ubicacion");
                        String comercianteID = elemento.getAttribute("comercianteID");

                        if (idStand.isEmpty() || ubicacion.isEmpty() || comercianteID.isEmpty()) {
                            errores.add("Stand con atributos incompletos en posición " + i);
                            continue;
                        }

                        Stand stand = new Stand(cod, desc, cap, idStand, ubicacion, null);
                        zona = stand;
                        break;
                    default:
                        errores.add("Tipo de zona desconocido: " + tipo);
                        continue;
                }

                Festival.agregar_Zona(zona);
            } catch (Exception e) {
                errores.add("Error al crear zona " + cod + ": " + e.getMessage());
            }
        }
    }

    private void cargarPersonas(Document doc) {
        NodeList personas = doc.getElementsByTagName("persona");

        for (int i = 0; i < personas.getLength(); i++) {
            Node nodo = personas.item(i);
            if (nodo.getNodeType() != Node.ELEMENT_NODE) continue;

            Element p = (Element) nodo;
            String tipo = p.getAttribute("tipo");
            String id = p.getAttribute("id");
            String nombre = p.getAttribute("nombre");

            if (id.isEmpty() || nombre.isEmpty()) {
                errores.add("Persona con atributos faltantes en posición " + i);
                continue;
            }

            try {
                Personas persona = null;

                switch (tipo) {
                    case "asistente":
                        persona = new Asistentes(id, nombre);
                        break;
                    case "artista":
                        Artistas artista = new Artistas(id, nombre);
                        NodeList zonasAcceso = p.getElementsByTagName("zonaAcceso");

                        for (int j = 0; j < zonasAcceso.getLength(); j++) {
                            Element zonaElem = (Element) zonasAcceso.item(j);
                            String codZona = zonaElem.getAttribute("codigo");
                            if (codZona.isEmpty()) {
                                errores.add("Zona de acceso vacía para artista " + id);
                                continue;
                            }

                            Zona zona = Festival.buscarZonaPorID(codZona);
                            if (zona != null) {
                                artista.agregarZonaAcceso(zona);
                            } else {
                                errores.add("Zona con código " + codZona + " no encontrada para artista " + id);
                            }
                        }

                        persona = artista;
                        break;
                    case "comerciante":
                        Comerciantes comerciante = new Comerciantes(id, nombre);
                        NodeList zonasAccesoCom = p.getElementsByTagName("zonaAcceso");

                        for (int j = 0; j < zonasAccesoCom.getLength(); j++) {
                            Element zonaElem = (Element) zonasAccesoCom.item(j);
                            String codZona = zonaElem.getAttribute("codigo");
                            if (codZona.isEmpty()) {
                                errores.add("Zona de acceso vacía para comerciante " + id);
                                continue;
                            }

                            Zona zona = Festival.buscarZonaPorID(codZona);
                            if (zona != null) {
                                comerciante.agregarZonaAcceso(zona);
                                if (zona instanceof Stand) {
                                    Stand stand = (Stand) zona;
                                    if (stand.obtener_comerciante() == null) {
                                        stand.setComerciante(comerciante);
                                        comerciante.setStand(stand);
                                    }
                                }
                            } else {
                                errores.add("Zona con código " + codZona + " no encontrada para comerciante " + id);
                            }
                        }

                        persona = comerciante;
                        break;
                    case "staff":
                        persona = new StaffOrganizador(id, nombre);
                        break;
                    default:
                        errores.add("Tipo de persona desconocido: " + tipo);
                        continue;
                }

                Festival.agregarPersona(persona);
            } catch (Exception e) {
                errores.add("Error al crear persona " + id + ": " + e.getMessage());
            }
        }
    }

    private void cargarEventos(Document doc) {
        NodeList listaEventos = doc.getElementsByTagName("evento");

        for (int i = 0; i < listaEventos.getLength(); i++) {
            Node nodo = listaEventos.item(i);
            if (nodo.getNodeType() != Node.ELEMENT_NODE) continue;

            Element elemento = (Element) nodo;

            try {
                String personaID = elemento.getAttribute("personaID");
                String zona = elemento.getAttribute("zona");
                boolean autorizado = Boolean.parseBoolean(elemento.getAttribute("autorizado"));
                LocalDate fecha = LocalDate.parse(elemento.getAttribute("fecha"));
                LocalTime hora = LocalTime.parse(elemento.getAttribute("hora"));
                int cantMinutos = Integer.parseInt(elemento.getAttribute("cantminutos"));

                Personas persona = Festival.buscarPersonaPorID(personaID);

                if (persona != null) {
                    persona.agregarAcceso(zona,fecha,hora,cantMinutos,autorizado);
                } else {
                    errores.add("Persona con ID " + personaID + " no encontrada.");
                }

            } catch (Exception e) {
                errores.add("Error al cargar evento en índice " + i + ": " + e.getMessage());
            }
        }
    }

    private void imprimirErrores() {
        if (errores.isEmpty()) {
            System.out.println("Carga completada sin errores.");
        } else {
            System.out.println("Errores encontrados durante la carga:");
            errores.forEach(System.out::println);
        }
    }
}

