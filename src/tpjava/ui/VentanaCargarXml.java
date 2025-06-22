package tpjava.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import tpjava.Archivo.CargadorXML;

public class VentanaCargarXml extends JFrame {

   
	private static final long serialVersionUID = 1L;
	private JButton botonSeleccionarArchivo;
    private JLabel etiquetaArchivoSeleccionado;
    private JTextArea areaMensajes;

    public VentanaCargarXml() {
    	// Constructor de la pantalla con datos de titulo, tamaños, locaciones forma de cerrar la ventana. *ESTO SE REPITE EN TODAS LAS PANTALLAS CON DISTINTOS FORMATOS Y TEXTOS
        setTitle("Cargar archivo XML");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel();
        botonSeleccionarArchivo = new JButton("Seleccionar archivo XML");
        etiquetaArchivoSeleccionado = new JLabel("Ningún archivo seleccionado");
        panelSuperior.add(botonSeleccionarArchivo);
        panelSuperior.add(etiquetaArchivoSeleccionado);
        add(panelSuperior, BorderLayout.NORTH);

        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaMensajes);
        add(scrollPane, BorderLayout.CENTER);
       
        // Evento de seleccion de archivo. Cargo el xml con la ruta completa
        botonSeleccionarArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int resultado = fileChooser.showOpenDialog(null);
                if (resultado == JFileChooser.APPROVE_OPTION) {
                    File archivoSeleccionado = fileChooser.getSelectedFile();
                    etiquetaArchivoSeleccionado.setText(archivoSeleccionado.getName());
                    cargarXML(archivoSeleccionado.getAbsolutePath());
                }
            }
        });
    }
    
    // genero una instancia del cargador del xml y le paso la ruta. Una vez finalizado el proces, devuelvo un mensaje 
    private void cargarXML(String rutaArchivo) {
        CargadorXML cargador = new CargadorXML();
        areaMensajes.setText("Cargando archivo...\n");
        cargador.cargarDesdeArchivo(rutaArchivo);
        areaMensajes.append("\nArchivo cargado correctamente.\nRevisá la consola para ver errores o confirmaciones.\n");
    }
}
