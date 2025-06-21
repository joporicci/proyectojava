package tpjava.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import tpjava.zonas.*;

public class VentanaListasZonas extends JFrame {
	 
	private static final long serialVersionUID = 1L;
	private JTextArea areaTexto;
    
	// Constructor texto de titulo, tamaños, locacion y construccion del area de texto con editable en false. Una vez creada la pantalla se llama a la funcion que muestra las zonas
    public VentanaListasZonas() {
        setTitle("Listado de Zonas");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTexto);

        add(scrollPane, BorderLayout.CENTER);
        mostrarZonas();
    }

    private void mostrarZonas() {
        try {
            ArrayList<Zona> zonas = Festival.devolver_TODAS_Zonas();
            StringBuilder builder = new StringBuilder();
            for (Zona z : zonas) {
                builder.append("Código: ").append(z.getCodigoAlfanumerico()).append("\n")
                       .append("Descripción: ").append(z.getDescripcion()).append("\n\n");
            }
            areaTexto.setText(builder.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las zonas: " + e.getMessage());
        }
    }
} 
