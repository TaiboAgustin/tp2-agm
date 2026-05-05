package UI;

import javax.swing.*;

import logica.modelo.Localidad;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AgregarLocalidad extends JFrame {

    private JTextField txtNombre;
    private JTextField txtProvincia;
    private JTextField txtLatitud;
    private JTextField txtLongitud;

    public AgregarLocalidad(JFrame parent) {
        setTitle("Nueva Localidad");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 280);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout(10, 10));

        getContentPane().add(buildForm(), BorderLayout.CENTER);
        getContentPane().add(buildButtons(), BorderLayout.SOUTH);

        getRootPane().setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        initialize(parent);
    }

    private JPanel buildForm() {
        JPanel panel = new JPanel();

        txtNombre   = new JTextField();
        txtNombre.setBounds(197, 0, 189, 43);
        txtProvincia = new JTextField();
        txtProvincia.setBounds(197, 53, 189, 43);
        txtLatitud  = new JTextField();
        txtLatitud.setBounds(197, 106, 189, 43);
        txtLongitud = new JTextField();
        txtLongitud.setBounds(197, 159, 189, 43);
        panel.setLayout(null);

        JLabel label = new JLabel("Nombre:");
        label.setBounds(0, 0, 189, 43);
        panel.add(label);      
        panel.add(txtNombre);
        JLabel label_1 = new JLabel("Provincia:");
        label_1.setBounds(0, 53, 189, 43);
        panel.add(label_1);   
        panel.add(txtProvincia);
        JLabel label_2 = new JLabel("Latitud:");
        label_2.setBounds(0, 106, 189, 43);
        panel.add(label_2);     
        panel.add(txtLatitud);
        JLabel label_3 = new JLabel("Longitud:");
        label_3.setBounds(0, 159, 189, 43);
        panel.add(label_3);    
        panel.add(txtLongitud);

        return panel;
    }

    private JPanel buildButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		limpiarCampos();
        	}
        });
        JButton btnCrear   = new JButton("Crear localidad");
        btnCrear.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		crearLocalidad();
        	}
        });

        panel.add(btnLimpiar);
        panel.add(btnCrear);
        return panel;
    }

    private void crearLocalidad() {
        String nombre    = txtNombre.getText().trim();
        String provincia = txtProvincia.getText().trim();
        String latStr    = txtLatitud.getText().trim();
        String lonStr    = txtLongitud.getText().trim();

        if (nombre.isEmpty() || provincia.isEmpty() || latStr.isEmpty() || lonStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double latitud  = Double.parseDouble(latStr);
            double longitud = Double.parseDouble(lonStr);

            if (latitud < -90 || latitud > 90) throw new NumberFormatException();
            if (longitud < -180 || longitud > 180) throw new NumberFormatException();

            Localidad localidad = new Localidad(nombre, provincia, latitud, longitud);

            JOptionPane.showMessageDialog(this,
                "Localidad creada: " + localidad.getNombre(), "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Latitud (-90 a 90) y longitud (-180 a 180) deben ser números válidos.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtProvincia.setText("");
        txtLatitud.setText("");
        txtLongitud.setText("");
    }

    private void initialize(JFrame parent) {
    	buildForm();
    	buildButtons();
    	
    }
}
