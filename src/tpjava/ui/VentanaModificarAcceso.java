package tpjava.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.time.LocalDate;

import tpjava.zonas.Festival;
import tpjava.personas.Personas;
import tpjava.zonas.Zona;
import tpjava.excepciones.*;

import java.util.ArrayList;

/**
 * Clase que contiene los atributos, el constructor y los métodos que este requiere de VentanaModificarAcceso. Ésta debe permitirle al usuario agregarle un nuevo acceso a una persona en el festival ingresando de que zona
 * quiere moverlo hasta cual, la cantidad de minutos de permanencia en la zona, y la fecha y la hora en la que ocure el mismo.
 */
public class VentanaModificarAcceso extends JFrame {
    private JTextField campoID, campoMinutos, campoFecha, campoHora;
    private JComboBox<String> comboZonaActual;
    private JComboBox<String> comboZonaNueva;
    private JButton botonBuscar, botonModificar, botonCerrar;
    JPanel panelZonas;
    private Personas personaActual;
    String id;

    /**
     * Construye una intancia de VentanaModificarAcceso.
     */
    public VentanaModificarAcceso() {
        super("Modificar Acceso de Persona");
        setLayout(new BorderLayout());

        // Panel de ingreso de ID, cantMinutos y búsqueda
        JPanel panelID = new JPanel(new GridLayout(4,4,10,10));
        panelID.add(new JLabel("ID de Persona:"));
        campoID = new JTextField(15);
        panelID.add(campoID);
        botonBuscar = new JButton("Buscar");
        panelID.add(botonBuscar);
        add(panelID, BorderLayout.NORTH);
        
        botonBuscar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		cargarPersona();
        	}
        });

        // Panel de selección de zonas
        panelZonas = new JPanel(new GridLayout(12, 4, 10, 5));
        panelZonas.setBorder(BorderFactory.createTitledBorder("Zonas"));
        campoMinutos = new JTextField(5);
        campoFecha = new JTextField(15);
        campoHora = new JTextField(10);
        comboZonaActual = new JComboBox<>();
        comboZonaNueva = new JComboBox<>();
        botonModificar = new JButton("Modificar Acceso");
        botonCerrar = new JButton("Cerrar");
        panelZonas.add(new JLabel("Zona Actual:"));
        panelZonas.add(comboZonaActual);
        panelZonas.add(new JLabel("Zona Nueva:"));
        panelZonas.add(comboZonaNueva);
        panelZonas.add(new JLabel("Minutos a modificar: "));
        panelZonas.add(campoMinutos);
        panelZonas.add(new JLabel ("Ingrese fecha (AÑO-MES-DIA):"));
        panelZonas.add(campoFecha);
        panelZonas.add(new JLabel("Ingrese hora (HORA:MINUTOS):"));
        panelZonas.add(campoHora);
        panelZonas.add(botonModificar);
        panelZonas.add(botonCerrar);
        add(panelZonas, BorderLayout.SOUTH);
        



        // Eventos
        botonModificar.addActionListener(e -> modificarAcceso());
        botonCerrar.addActionListener(e -> dispose());

        setSize(400, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        
        panelZonas.setVisible(false);
    }

    /**
     * Busca la persona del ID ingresado y habilita los selectores de la zona anterior, la zona nueva, la cantidad de minutos, la fecha y la hora, ademas del botón para proseguir después de ingresar los datos.
     */
    private void cargarPersona() {
        id = campoID.getText().trim();
        if (id == null || id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID de persona", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            personaActual = Festival.devolver_Persona(id);
            JOptionPane.showMessageDialog(this, "Persona encontrada: " + personaActual.obtenerNombre(), "Aviso", JOptionPane.INFORMATION_MESSAGE);
            comboZonaActual.removeAllItems();
            for (Zona zona : personaActual.obtenerSetZonas()) {
                comboZonaActual.addItem(zona.getCodigoAlfanumerico());
            }

            comboZonaNueva.removeAllItems();
            for (Zona zona : Festival.devolver_TODAS_ZonasNOComunes()) {
                comboZonaNueva.addItem(zona.getCodigoAlfanumerico());
            }
            
            panelZonas.setVisible(true);

        } catch (ExcepcionPersonaNoExiste ex) {
            JOptionPane.showMessageDialog(this, "Persona no encontrada: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Agrega un nuevo acceso a la lista de accesos de la persona con los datos ingresados por el usuario. Se agrega aunque no esté autorizado o la Zona esté llena para tener la trazabilidad de cada persona.
     */
    private void modificarAcceso() {
        if (personaActual == null) {
            JOptionPane.showMessageDialog(this, "Primero debe buscar una persona.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String zonaVieja = (String) comboZonaActual.getSelectedItem();
        String zonaNueva = (String) comboZonaNueva.getSelectedItem();
        long cantMinutos = Long.parseLong(campoMinutos.getText());

        if (zonaVieja == null || zonaNueva == null || zonaVieja.equals(zonaNueva)) {
            JOptionPane.showMessageDialog(this, "Seleccione zonas válidas y diferentes.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Festival.modificarAcceso(Festival.devolver_Persona(id), Festival.buscarZonaPorID(zonaNueva), cantMinutos, LocalDate.parse(campoFecha.getText()), LocalTime.parse(campoHora.getText()));
            JOptionPane.showMessageDialog(this, "Acceso modificado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (ExcepcionAccesoIncorrecto ex) {
            
        }
        catch(ExcepcionPersonaNoExiste eP) {
        	JOptionPane.showMessageDialog(this, "Error al modificar acceso: " + eP.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);;
        }
        catch(ExcepcionZonaNoExiste eZ) {
        	JOptionPane.showMessageDialog(this, "Error al modificar acceso: " + eZ.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);;
        }
    }
}
