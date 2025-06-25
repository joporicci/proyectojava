package tpjava.ui; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import tpjava.zonas.Festival;
import tpjava.personas.Personas;
import tpjava.zonas.Zona;
import tpjava.excepciones.ExcepcionPersonaNoExiste;

/**
 * Clase que contiene los atributos, el constructor de VentanaBuscarPorPersona y un método que el mismo usa. Esta ventana debe permitir al usuario recibir los datos de la persona cuyo ID ingrese en un campo de texto.
 */
public class VentanaBuscarPorPersona extends JFrame {
    
	private static final long serialVersionUID = 1L;
	private JTextField campoID;
    private JTextArea areaResultado;
    private JButton botonBuscar, botonCerrar;

    /**
     * Construye una instancia de VentanaBuscarPorPersona.
     */
    public VentanaBuscarPorPersona() {
        super("Buscar Persona por ID");
        setLayout(new BorderLayout());

  
        JPanel panelEntrada = new JPanel();
        panelEntrada.setLayout(new FlowLayout());
        panelEntrada.add(new JLabel("ID de persona:"));
        campoID = new JTextField(15);
        panelEntrada.add(campoID);

        botonBuscar = new JButton("Buscar");
        panelEntrada.add(botonBuscar);

        add(panelEntrada, BorderLayout.NORTH);

        // Área de resultado
        areaResultado = new JTextArea(10, 30);
        areaResultado.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaResultado);
        add(scroll, BorderLayout.CENTER);

        // Botón cerrar
        botonCerrar = new JButton("Cerrar");
        add(botonCerrar, BorderLayout.SOUTH);

        // Acción botón Buscar 
        setVisible(true);
    }

    /**
     * Permite buscar una persona mediante el texto que se introdujo en el campo de la ventana.
     */
    private void buscarPersona() {
    	
       // Pido un id, hasta que no lo 
       String id = campoID.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else 
        	if(id.length()!=6) {
        		JOptionPane.showMessageDialog(this, "Ingrese un ID de seis caracteres", "Error", JOptionPane.ERROR_MESSAGE);
        	}
        try {
        	// Me conecto con la clase manejadora festival y busco las persona por su id mostrando el listado de zonas accesibles de la misma
            areaResultado.setText(Festival.devolver_Persona(id).devolver_muestra());
            // En caso de que no se encuentre utilizo la excepcion ya creada
        } catch (ExcepcionPersonaNoExiste ex) {
            areaResultado.setText("Persona no encontrada: " + ex.getMessage());
        }
    }
}