package tpjava.zonas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import tpjava.excepciones.*;
import tpjava.personas.Artistas;
import tpjava.personas.Comerciantes;
import tpjava.personas.Personas;

public class Festival {

    private static ArrayList<Zona> listaZonas = new ArrayList<>();
    private static ArrayList<Personas> listaPersonas = new ArrayList<>();

    public static void agregar_Zona(Zona zona) {
        listaZonas.add(zona);
    }

    public static void agregarPersona(Personas persona) {
        listaPersonas.add(persona);
    }

    public static Escenario devolver_Escenario(Artistas artista) throws ExcepcionEscenarioNoExiste {
        for (Zona z : listaZonas) {
            if (z instanceof Escenario esc && esc.estaArtista(artista)) {
                return esc;
            }
        }
        throw new ExcepcionEscenarioNoExiste("No se encontró un escenario para el artista.");
    }

    public static ArrayList<Zona> devolver_TODAS_Zonas() {
        ArrayList<Zona> lZonas = new ArrayList<>();
        for (Zona zonaActual : listaZonas) {
            if (!(zonaActual instanceof ZonaComun))
                lZonas.add(zonaActual);
        }
        return lZonas;
    }

    public static Stand devolver_Stand(Comerciantes comerciante) throws ExcepcionStandNoExiste {
        for (Zona z : listaZonas) {
            if (z instanceof Stand stand && stand.estaComerciante(comerciante)) {
                return stand;
            }
        }
        throw new ExcepcionStandNoExiste("El comerciante ingresado no posee un Stand existente.");
    }

    public static ArrayList<Escenario> devolver_ListaEscenarios() {
        ArrayList<Escenario> listaEscenarios = new ArrayList<>();
        for (Zona z : listaZonas) {
            if (z instanceof Escenario esc) {
                listaEscenarios.add(esc);
            }
        }
        return listaEscenarios;
    }

    public static Personas buscarPersonaPorID(String personaID) throws ExcepcionPersonaNoExiste {
        for (Personas p : listaPersonas) {
            if (p.obtenerID().equals(personaID)) {
                return p;
            }
        }
        throw new ExcepcionPersonaNoExiste("ID de persona no registrado: " + personaID);
    }

    public static Zona buscarZonaPorID(String zonaID) throws ExcepcionZonaNoExiste {
        for (Zona z : listaZonas) {
            if (z.getCodigoAlfanumerico().equals(zonaID)) {
                return z;
            }
        }
        throw new ExcepcionZonaNoExiste("ID de zona no registrado: " + zonaID);
    }

    public static int contarPersonasEnZona(Zona zona) {
        int contador = 0;
        for (Personas p : listaPersonas) {
            if (p.obtenerListaZonas().contains(zona)) {
                contador++;
            }
        }
        return contador;
    }

    public static boolean superaCapacidad(Zona zona) {
        if (zona instanceof ZonaRestringida zonaRest) {
            return contarPersonasEnZona(zonaRest) > zonaRest.getCapacidad_maxima();
        }
        return false;
    }

    public static void modificarAcceso(String personaID, String zonaID, float minutos)
            throws ExcepcionPersonaNoExiste, ExcepcionZonaNoExiste {

        Personas persona = buscarPersonaPorID(personaID);
        Zona zona = buscarZonaPorID(zonaID);
        LocalDate fecha = LocalDate.now();
        LocalTime hora = LocalTime.now();

        boolean autorizado = false;
        if (persona.puedeAcceder(zona) && !superaCapacidad(zona)) {
            autorizado = true;
        }

        persona.agregarAcceso(zonaID, fecha, hora, minutos, autorizado);

        if (autorizado) {
            System.out.println("Acceso AUTORIZADO a zona " + zonaID + " para persona " + personaID);
        } else {
            System.out.println("Acceso DENEGADO a zona " + zonaID + " para persona " + personaID);
        }
    }

    public static void modificarZonaAccesible(String personaID, String zonaActualID, String zonaNuevaID)
            throws ExcepcionPersonaNoExiste, ExcepcionZonaNoExiste {

        Personas persona = buscarPersonaPorID(personaID);
        Zona zonaActual = buscarZonaPorID(zonaActualID);
        Zona zonaNueva = buscarZonaPorID(zonaNuevaID);

        if (!persona.obtenerListaZonas().contains(zonaActual)) {
            throw new IllegalArgumentException("La persona no tiene acceso a la zona especificada.");
        }

        persona.obtenerListaZonas().remove(zonaActual);
        persona.obtenerListaZonas().add(zonaNueva);
    }

    public static ArrayList<Personas> devolverListaPersonas() {
        return new ArrayList<>(listaPersonas); // Retorno copia defensiva
    }

    public static ArrayList<Zona> devolverListaZonas() {
        return new ArrayList<>(listaZonas); // Retorno copia defensiva
    }
}
