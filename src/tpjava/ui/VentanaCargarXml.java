package tpjava.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import tpjava.Archivo.Cargador_XML;

/**
 * Clase que contiene el constructor, un método que este requiere y los atributos de VentanaCargarXml para que permita al usuario cargar los datos del archivo XML. 
 */
public class VentanaCargarXml extends JFrame {

   
	private static final long serialVersionUID = 1L;
	private JButton botonCerrar;
    private JTextArea areaMensajes;

    /**
     * Construye una instancia de VentanaCargarXml.
     */
    public VentanaCargarXml() {
        setTitle("Cargar archivo XML");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel();
        botonCerrar = new JButton("Cerrar");
        panelSuperior.add(botonCerrar);
        add(panelSuperior, BorderLayout.NORTH);

        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaMensajes);
        add(scrollPane, BorderLayout.CENTER);
        
        
        // Evento de seleccion de archivo. Cargo el xml con la ruta completa
        botonCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
        cargarXML();
    }
    
    /**
     * Invoca métodos del Cargador_XML y devuelve mensajes de proceso, éxito, errores...
     */
    private void cargarXML() {
        areaMensajes.setText("Cargando archivo...\n");
        Cargador_XML.cargar_Todo();
        Cargador_XML.obtener_Errores().forEach((error) -> System.err.println(error));
        areaMensajes.append("\nArchivo cargado correctamente. Podés cerrar la ventana.\nRevisá la consola para ver errores o confirmaciones.\n");
    }
}
