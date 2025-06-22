package tpjava.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import tpjava.zonas.Festival;
import tpjava.personas.Personas;
import tpjava.zonas.Zona;
import tpjava.excepciones.*;

import java.util.ArrayList;

public class VentanaModificarAcceso extends JFrame {
    private JTextField campoID;
    private JComboBox<String> comboZonaActual;
    private JComboBox<String> comboZonaNueva;
    private JButton botonBuscar, botonModificar, botonCerrar;

    private Personas personaActual;

    public VentanaModificarAcceso() {
        super("Modificar Acceso de Persona");
        setLayout(new BorderLayout());

        // Panel de ingreso de ID y búsqueda
        JPanel panelID = new JPanel(new FlowLayout());
        panelID.add(new JLabel("ID de Persona:"));
        campoID = new JTextField(15);
        panelID.add(campoID);
        botonBuscar = new JButton("Buscar");
        panelID.add(botonBuscar);
        add(panelID, BorderLayout.NORTH);

        // Panel de selección de zonas
        JPanel panelZonas = new JPanel(new GridLayout(2, 2, 10, 10));
        panelZonas.setBorder(BorderFactory.createTitledBorder("Zonas"));

        comboZonaActual = new JComboBox<>();
        comboZonaNueva = new JComboBox<>();
        panelZonas.add(new JLabel("Zona Actual:"));
        panelZonas.add(comboZonaActual);
        panelZonas.add(new JLabel("Zona Nueva:"));
        panelZonas.add(comboZonaNueva);
        add(panelZonas, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        botonModificar = new JButton("Modificar Acceso");
        botonCerrar = new JButton("Cerrar");
        panelBotones.add(botonModificar);
        panelBotones.add(botonCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        botonBuscar.addActionListener(e -> cargarPersona());
        botonModificar.addActionListener(e -> modificarAcceso());
        botonCerrar.addActionListener(e -> dispose());

        setSize(400, 250);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cargarPersona() {
        String id = campoID.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID de persona", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            personaActual = Festival.buscarPersonaPorID(id);
            comboZonaActual.removeAllItems();
            for (Zona zona : personaActual.obtenerListaZonas()) {
                comboZonaActual.addItem(zona.getCodigoAlfanumerico());
            }

            comboZonaNueva.removeAllItems();
            for (Zona zona : Festival.devolver_TODAS_ZonasNOComunes()) {
                comboZonaNueva.addItem(zona.getCodigoAlfanumerico());
            }

        } catch (ExcepcionPersonaNoExiste ex) {
            JOptionPane.showMessageDialog(this, "Persona no encontrada: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarAcceso() {
        if (personaActual == null) {
            JOptionPane.showMessageDialog(this, "Primero debe buscar una persona.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String zonaVieja = (String) comboZonaActual.getSelectedItem();
        String zonaNueva = (String) comboZonaNueva.getSelectedItem();

        if (zonaVieja == null || zonaNueva == null || zonaVieja.equals(zonaNueva)) {
            JOptionPane.showMessageDialog(this, "Seleccione zonas válidas y diferentes.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Festival.modificarAcceso(personaActual.obtenerID(), zonaVieja, zonaNueva);
            JOptionPane.showMessageDialog(this, "Acceso modificado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar acceso: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
