package tpjava.carga;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import tpjava.personas.*;
import tpjava.zonas.*;
import tpjava.misc.EventoMusical;

public class CargadorXML {

    private ArrayList<String> errores = new ArrayList<>();

    public void cargarDesdeArchivo(String festival.xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(festival.xml));
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
            Element elemento = (Element) zonas.item(i);
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
                        int cap = Integer.parseInt(elemento.getAttribute("capacidad"));
                        zona = new ZonaRestringida(cod, desc, cap);
                        break;
                    case "escenario":
                        zona = new Escenario(cod, desc);
                        break;
                    case "stand":
                        // Se asigna luego con comerciantes
                        zona = new Stand(cod, desc, 0, "", "", null);
                        break;
                    default:
                        errores.add("Tipo de zona desconocido: " + tipo);
                        continue;
                }

                Festival.agregarZona(zona);
            } catch (Exception e) {
                errores.add("Error al crear zona " + cod + ": " + e.getMessage());
            }
        }
    }

    private void cargarPersonas(Document doc) {
        NodeList personas = doc.getElementsByTagName("persona");

        for (int i = 0; i < personas.getLength(); i++) {
            Element p = (Element) personas.item(i);
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
                        persona = new Artistas(id, nombre); // Zonas se asignan automáticamente por Festival
                        break;
                    case "comerciante":
                        persona = new Comerciantes(id, nombre); // Stand se asigna luego
                        break;
                    case "staff":
                        persona = new StaffOrganizador(id, nombre);
                        break;
                    default:
                        errores.add("Tipo de persona desconocido: " + tipo);
                        continue;
                }

                // Cargar accesos (opcional)
                NodeList accesos = p.getElementsByTagName("acceso");
                for (int j = 0; j < accesos.getLength(); j++) {
                    Element acc = (Element) accesos.item(j);
                    String zona = acc.getAttribute("zona");
                    LocalDate fecha = LocalDate.parse(acc.getAttribute("fecha"));
                    LocalTime hora = LocalTime.parse(acc.getAttribute("hora"));
                    float mins = Float.parseFloat(acc.getAttribute("minutos"));
                    boolean estado = Boolean.parseBoolean(acc.getAttribute("estado"));
                    persona.agregarAcceso(zona, fecha, hora, mins, estado);
                }

                Festival.agregarPersona(persona);
            } catch (Exception e) {
                errores.add("Error al crear persona " + id + ": " + e.getMessage());
            }
        }
    }

    private void cargarEventos(Document doc) {
        NodeList eventos = doc.getElementsByTagName("evento");

        for (int i = 0; i < eventos.getLength(); i++) {
            Element e = (Element) eventos.item(i);
            String idArtista = e.getAttribute("id_artista");
            String fechaStr = e.getAttribute("fecha");
            String horaStr = e.getAttribute("hora");

            if (idArtista.isEmpty() || fechaStr.isEmpty() || horaStr.isEmpty()) {
                errores.add("Evento con datos faltantes en posición " + i);
                continue;
            }

            try {
                Artistas artista = (Artistas) Festival.devolver_TODAS_Personas().stream()
                        .filter(p -> p instanceof Artistas && p.obtenerID().equals(idArtista))
                        .findFirst().orElse(null);

                if (artista == null) {
                    errores.add("Evento con artista no encontrado: " + idArtista);
                    continue;
                }

                LocalDate fecha = LocalDate.parse(fechaStr);
                LocalTime hora = LocalTime.parse(horaStr);
                EventoMusical evento = new EventoMusical(fecha, hora, artista);

                Escenario escenario = Festival.devolver_Escenario(artista);
                // escenario.agregarEvento(evento);

            } catch (Exception ex) {
                errores.add("Error al crear evento con artista " + idArtista + ": " + ex.getMessage());
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
