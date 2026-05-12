package UI;

import javax.swing.*;
import logica.modelo.Localidad;
import java.awt.event.ActionEvent;

public class AgregarLocalidad extends JDialog {

    private JTextField txtNombre;
    private JTextField txtProvincia;
    private JTextField txtLatitud;
    private JTextField txtLongitud;

    private Localidad localidadCreada = null;

    public AgregarLocalidad(JFrame parent) {
        super(parent, "Nueva Localidad", true);
        setBounds(150, 150, 400, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        buildForm();
        buildButtons();
    }

    public Localidad getLocalidadCreada() {
        return localidadCreada;
    }

    private void buildForm() {
        addLabel("Nombre:",   16);
        addLabel("Provincia:", 66);
        addLabel("Latitud:",  116);
        addLabel("Longitud:", 166);

        txtNombre = new JTextField();
        txtNombre.setBounds(150, 16, 230, 35);
        getContentPane().add(txtNombre);

        txtProvincia = new JTextField();
        txtProvincia.setBounds(150, 66, 230, 35);
        getContentPane().add(txtProvincia);

        txtLatitud = new JTextField();
        txtLatitud.setBounds(150, 116, 230, 35);
        getContentPane().add(txtLatitud);

        txtLongitud = new JTextField();
        txtLongitud.setBounds(150, 166, 230, 35);
        getContentPane().add(txtLongitud);
    }

    private void addLabel(String text, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(16, y, 130, 35);
        getContentPane().add(label);
    }

    private void buildButtons() {
        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(150, 210, 100, 30);
        btnLimpiar.addActionListener((ActionEvent e) -> limpiarCampos());
        getContentPane().add(btnLimpiar);

        JButton btnCrear = new JButton("Crear localidad");
        btnCrear.setBounds(260, 211, 120, 29);
        btnCrear.addActionListener((ActionEvent e) -> crearLocalidad());
        getContentPane().add(btnCrear);
    }

    private void crearLocalidad() {
        String nombre    = txtNombre.getText().trim();
        String provincia = txtProvincia.getText().trim();
        String latStr    = txtLatitud.getText().trim();
        String lonStr    = txtLongitud.getText().trim();

        if (nombre.isEmpty() || provincia.isEmpty() || latStr.isEmpty() || lonStr.isEmpty()) {
        	mostrarError("Todos los campos son obligatorios.");
            return;
        }

        try {
            double latitud  = Double.parseDouble(latStr);
            double longitud = Double.parseDouble(lonStr);

            localidadCreada = new Localidad(nombre, provincia, latitud, longitud);
            dispose();

        } catch (NumberFormatException ex) {
            mostrarError("Latitud (-90 a 90) y longitud (-180 a 180) deben ser números válidos.");
        }
    }
    
    protected void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtProvincia.setText("");
        txtLatitud.setText("");
        txtLongitud.setText("");
    }
}