package tpjava.ui;

import tpjava.zonas.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AgregarZonaFrame extends JFrame {

   
	private static final long serialVersionUID = 1L;
	private JTextField campoCodigo, campoDescripcion, campoCapacidad;
    private JComboBox<String> comboTipo;
    private JButton botonAgregar;

    public AgregarZonaFrame() {
        setTitle("Agregar Zona");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Código:"));
        campoCodigo = new JTextField();
        panel.add(campoCodigo);

        panel.add(new JLabel("Descripción:"));
        campoDescripcion = new JTextField();
        panel.add(campoDescripcion);

        panel.add(new JLabel("Tipo:"));
        comboTipo = new JComboBox<>(new String[]{"comun", "restringida", "escenario", "stand"});
        panel.add(comboTipo);

        panel.add(new JLabel("Capacidad (si aplica):"));
        campoCapacidad = new JTextField();
        panel.add(campoCapacidad);

        botonAgregar = new JButton("Agregar Zona");
        panel.add(botonAgregar);

        botonAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarZona();
            }
        });

        add(panel);
    }

    private void agregarZona() {
        String codigo = campoCodigo.getText().trim();
        String descripcion = campoDescripcion.getText().trim();
        String tipo = (String) comboTipo.getSelectedItem();
        String capacidadTexto = campoCapacidad.getText().trim();

        if (codigo.isEmpty() || descripcion.isEmpty() || tipo == null) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Zona nuevaZona;
            switch (tipo) {
                case "comun":
                    nuevaZona = new ZonaComun(codigo, descripcion);
                    break;
                case "restringida":
                    int capRestr = Integer.parseInt(capacidadTexto);
                    nuevaZona = new ZonaRestringida(codigo, descripcion, capRestr);
                    break;
                case "escenario":
                    int capEsc = Integer.parseInt(capacidadTexto);
                    nuevaZona = new Escenario(codigo, descripcion, capEsc);
                    break;
                case "stand":
                    int capStand = Integer.parseInt(capacidadTexto);
                    nuevaZona = new Stand(codigo, descripcion, capStand, "", null);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo desconocido");
            }

            Festival.agregar_Zona(nuevaZona);
            JOptionPane.showMessageDialog(this, "Zona agregada correctamente.");
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Capacidad debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}