package tpjava.ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import tpjava.zonas.*;

/**
 * Clase que contiene los atributos, el constructor y un método que el mismo usa para operar con VentanaListaStands. Esta debe permitirle al usuario recibir una lista de todos los Stands y sus datos completos ordenados alfabeticamente
 * por el nombre del comerciante responsable.
 */
public class VentanaListaStands extends JFrame {
	 
	private static final long serialVersionUID = 1L;
	private JTextArea areaTexto;
    
	/*
	 * Construye una instancia de VentanaListaStands.
	 */
    public VentanaListaStands() {
    	JPanel panelEntrada = new JPanel();
    	panelEntrada.setLayout(new FlowLayout());
        setTitle("Listado de Stands");
        setSize(1000, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTexto);
    	
        
        
    	add(panelEntrada, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        
        setVisible(true);
        mostrarStands();
    }

    /**
     * Recibe de Festival una cadena con la lista de Stands antes mencionada y la muestra en pantalla en la ventana. Si no la recibe, muestra un error en pantalla.
     */
    private void mostrarStands() {
        try {
            areaTexto.setText(Festival.lista_StandsAlfabeticamente());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los Stands: " + e.getMessage());
        }
    }
} 
