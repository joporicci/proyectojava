package tpjava.ui;

import tpjava.personas.Personas;
import tpjava.zonas.Festival;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import tpjava.excepciones.*;

/**
 * Clase que contiene los atributos, el constructor y los métodos necesarios para VentanaListaPersonas, que debe de permitir al usuario buscar una persona y recibir sus datos en una lista.
 */
public class VentanaListaPersonas extends JFrame {

    
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
    
	/**
	 * Construye una instancia de VentanaListaPersonas.
	 */
    public VentanaListaPersonas() {
        setTitle("Ingresar ID de persona a mostrar los datos");
        setSize(1000 , 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    /**
     * Inicializa la interfaz gráfica de VentanaListaPersonas.
     */
    private void initUI() {
    	
    	
        JPanel panel = new JPanel(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        panel.add(scroll, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        
        String idPersonaBuscar = JOptionPane.showInputDialog(this,"Ingresar ID de la persona a buscar:");
        
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnCerrar);
        panel.add(btnPanel, BorderLayout.SOUTH);
        
        mostrarPersona(idPersonaBuscar.trim());
        add(panel);
        setVisible(true);

    }

    /**
     * Método que pide la persona de la id ingresada a Festival y en el caso de que exista la devuelve. En el caso de que no, le informa al usuario del error.
     * @param id objeto de clase String, es la id de la persona que el usuario desea buscar.
     */
    private void mostrarPersona(String id) {
        try {
            Personas p = Festival.devolver_Persona(id);
            textArea.setText(p.devolver_muestra());
        } catch (ExcepcionPersonaNoExiste e) {
            textArea.setText("Error al cargar personas: " + e.getMessage());
        }
    }
}
