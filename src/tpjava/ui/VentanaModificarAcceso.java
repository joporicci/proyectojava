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

public class VentanaModificarAcceso extends JFrame {
    private JTextField campoID, campoMinutos, campoFecha, campoHora;
    private JComboBox<String> comboZonaActual;
    private JComboBox<String> comboZonaNueva;
    private JButton botonBuscar, botonModificar, botonCerrar;
    JPanel panelZonas;
    private Personas personaActual;

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

    private void cargarPersona() {
        String id = campoID.getText().trim();
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
            Festival.modificarAcceso(personaActual.obtenerID(), zonaNueva, cantMinutos, LocalDate.parse(campoFecha.getText()), LocalTime.parse(campoHora.getText()));
            JOptionPane.showMessageDialog(this, "Acceso modificado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (ExcepcionAccesoIncorrecto ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar acceso: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
