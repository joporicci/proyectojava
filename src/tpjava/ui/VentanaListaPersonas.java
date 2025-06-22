package tpjava.ui;

import tpjava.personas.Personas;
import tpjava.zonas.Festival;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VentanaListaPersonas extends JFrame {

    
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
    
	// Constructor de posiciones, texto, posicion relativa, forma de cerrar
    public VentanaListaPersonas() {
        setTitle("Lista de Personas");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
    	
    	
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Personas cargadas en el sistema:");
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(label, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        panel.add(scroll, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnCerrar);
        panel.add(btnPanel, BorderLayout.SOUTH);

        cargarPersonas();
        add(panel);
    }

    private void cargarPersonas() {
        try {
            ArrayList<Personas> personas = Festival.devolverListaPersonas();
            if (personas.isEmpty()) {
                textArea.setText("No hay personas cargadas.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Personas p : personas) {
                    sb.append("ID: ").append(p.obtenerID())
                      .append(" | Nombre: ").append(p.obtenerNombre()).append("\n");
                }
                textArea.setText(sb.toString());
            }
        } catch (Exception e) {
            textArea.setText("Error al cargar personas: " + e.getMessage());
        }
    }
}
