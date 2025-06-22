// Ventana de ejemplo: pantalla principal con botones
package tpjava.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainMenuFrame() {
        setTitle("Festival de Música - Menú Principal");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(9, 1, 10, 10));

        JButton btnCargarXML = new JButton("Cargar XML");
        JButton btnVerZonas = new JButton("Ver Zonas");
        JButton btnVerPersonas = new JButton("Ver Personas");
        JButton btnAsignarZona = new JButton("Asignar Zona");
        JButton btnModificarAcceso = new JButton("Modificar Acceso");
        JButton btnGenerarReporte = new JButton("Generar Reporte");
        JButton btnSalir = new JButton("Salir");

        add(btnCargarXML);
        add(btnVerZonas);
        add(btnVerPersonas);
        add(btnAsignarZona);
        add(btnModificarAcceso);
        add(btnGenerarReporte);
        add(btnSalir);

        // Acciones
        btnCargarXML.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new VentanaCargarXml();
            }
        });

        btnVerZonas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new VentanaListasZonas();
            }
        });

        btnVerPersonas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new VentanaListaPersonas();
            }
        });

        btnAsignarZona.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AgregarZonaFrame();
            }
        });

        btnModificarAcceso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new VentanaModificarAcceso();
            }
        });

        btnGenerarReporte.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new VentanaModificarAcceso();
            }
        });

        btnSalir.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainMenuFrame();
    }
}

