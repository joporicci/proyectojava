package tpjava.ui; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import tpjava.zonas.Festival;
import tpjava.personas.Personas;
import tpjava.zonas.Zona;
import tpjava.excepciones.ExcepcionPersonaNoExiste;

public class VentanaBuscarPorPersona extends JFrame {
    
	private static final long serialVersionUID = 1L;
	private JTextField campoID;
    private JTextArea areaResultado;
    private JButton botonBuscar, botonCerrar;

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
            Personas persona = Festival.devolver_Persona(id);
            StringBuilder sb = new StringBuilder();
            sb.append("Nombre: ").append(persona.obtenerID()).append("\n");
            sb.append("Zonas accesibles:\n");

            for (Zona zona : persona.obtenerListaZonas()) {
                sb.append("- ").append(zona.getCodigoAlfanumerico()).append(": ").append(zona.getDescripcion()).append("\n");
            }

            areaResultado.setText(sb.toString());
            // En caso de que no se encuentre utilizo la excepcion ya creada
        } catch (ExcepcionPersonaNoExiste ex) {
            areaResultado.setText("Persona no encontrada: " + ex.getMessage());
        }
    }
}