// Ventana de ejemplo: pantalla principal con botones
package tpjava.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que constiene el constructor de la ventana MainMenuFrame y su atributo serialVersionUID. Esta ventana le permite al usuario elegir que acción quiere tomar.
 */
public class MainMenuFrame extends JFrame {


	private static final long serialVersionUID = 1L;

	/**
	 * Construye una instancia de MainMenuFrame.
	 */
	public MainMenuFrame() {
        setTitle("Festival de Música - Menú Principal");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(9, 1, 10, 10));

        JButton btnCargarXML = new JButton("Cargar XML");
        JButton btnVerZonas = new JButton("Listar Zonas según su concurrencia en una fecha y hora");
        JButton btnVerStands = new JButton("Lista de Stands");
        JButton btnVerPersonas = new JButton("Buscar datos de persona por ID");
        JButton btnModificarAcceso = new JButton("Modificar Acceso");
        JButton btnSalir = new JButton("Salir");

        add(btnCargarXML);
        add(btnVerZonas);
        add(btnVerStands);
        add(btnVerPersonas);
        add(btnModificarAcceso);
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

        btnModificarAcceso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new VentanaModificarAcceso();
            }
        });

        btnVerStands.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		new VentanaListaStands();
        	}
        });

        btnSalir.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
}

