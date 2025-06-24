package tpjava.ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import tpjava.zonas.*;

public class VentanaListaStands extends JFrame {
	 
	private static final long serialVersionUID = 1L;
	private JTextArea areaTexto;
    
	// Constructor texto de titulo, tamaños, locacion y construccion del area de texto con editable en false. Una vez creada la pantalla se llama a la funcion que muestra las zonas
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

    private void mostrarStands() {
        try {
            areaTexto.setText(Festival.lista_StandsAlfabeticamente());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los Stands: " + e.getMessage());
        }
    }
} 
