package tpjava.ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import tpjava.zonas.*;

public class VentanaListasZonas extends JFrame {
	 
	private static final long serialVersionUID = 1L;
	private JTextArea areaTexto;
	private JTextField campoFecha, campoHora;
    
	// Constructor texto de titulo, tamaños, locacion y construccion del area de texto con editable en false. Una vez creada la pantalla se llama a la funcion que muestra las zonas
    public VentanaListasZonas() {
    	JPanel panelEntrada = new JPanel();
    	panelEntrada.setLayout(new FlowLayout());
        setTitle("Listar Zonas x concurrencia en hora");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTexto);

        campoFecha = new JTextField(15);
    	campoHora = new JTextField(7);
    	
    	JButton btnMuestra = new JButton("Mostrar Zonas");
    	btnMuestra.addActionListener(e -> {
    		LocalDate fecha = LocalDate.parse(campoFecha.getText().trim());
    		LocalTime hora = LocalTime.parse(campoHora.getText().trim());
    		mostrarZonas(fecha,hora);    		
    	});
    	
    	panelEntrada.add(new JLabel("FECHA en formato (año - mes - dia):"));
    	panelEntrada.add(campoFecha);
    	panelEntrada.add(new JLabel("HORA en formato (hora:minutos):"));
    	panelEntrada.add(campoHora);
        panelEntrada.add(btnMuestra);
    	
    	add(panelEntrada, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private void mostrarZonas(LocalDate fecha, LocalTime hora) {
        try {
            areaTexto.setText(Festival.lista_ZonasPorConcurrencia(fecha,hora));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las zonas: " + e.getMessage());
        }
    }
} 
