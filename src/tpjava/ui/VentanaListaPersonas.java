package tpjava.ui;

import tpjava.personas.Personas;
import tpjava.zonas.Festival;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import tpjava.excepciones.*;
public class VentanaListaPersonas extends JFrame {

    
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
    
	// Constructor de posiciones, texto, posicion relativa, forma de cerrar
    public VentanaListaPersonas() {
        setTitle("Ingresar ID de persona a mostrar los datos");
        setSize(1000 , 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

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

    private void mostrarPersona(String id) {
        try {
            Personas p = Festival.devolver_Persona(id);
            textArea.setText(p.toString());
        } catch (ExcepcionPersonaNoExiste e) {
            textArea.setText("Error al cargar personas: " + e.getMessage());
        }
    }
}
