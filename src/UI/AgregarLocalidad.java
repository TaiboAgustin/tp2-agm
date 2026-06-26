package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import logica.modelo.Localidad;

public class AgregarLocalidad extends JDialog {

    private static final Color BG       = new Color(18, 18, 19);
    private static final Color INPUT_BG = new Color(26, 26, 27);
    private static final Color BORDER   = new Color(58, 58, 60);
    private static final Color GREEN    = new Color(83, 141, 78);
    private static final Color GRAY     = new Color(129, 131, 132);

    private JTextField txtNombre;
    private JTextField txtProvincia;
    private JTextField txtLatitud;
    private JTextField txtLongitud;

    private Localidad localidadCreada = null;

    public AgregarLocalidad(JFrame parent) {
        super(parent, "Nueva Localidad", true);
        setBounds(150, 150, 460, 380);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(BG);
        getContentPane().setLayout(null);

        buildForm();
        buildButtons();
    }

    public Localidad getLocalidadCreada() {
        return localidadCreada;
    }

    private void buildForm() {
        txtNombre    = buildField("Nombre",    30);
        txtProvincia = buildField("Provincia", 100);
        txtLatitud   = buildField("Latitud",   170);
        txtLongitud  = buildField("Longitud",  240);
    }

    private JTextField buildField(String labelText, int y) {
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(GRAY);
        lbl.setBounds(50, y, 360, 18);
        getContentPane().add(lbl);

        JTextField field = new JTextField();
        field.setBackground(INPUT_BG);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        field.setBounds(50, y + 20, 360, 34);
        getContentPane().add(field);

        return field;
    }

    private void buildButtons() {
        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBackground(new Color(26, 26, 27));
        btnLimpiar.setForeground(GRAY);
        btnLimpiar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setBounds(50, 305, 110, 32);
        btnLimpiar.addActionListener((ActionEvent e) -> limpiarCampos());
        getContentPane().add(btnLimpiar);

        JButton btnCrear = new JButton("Crear localidad");
        btnCrear.setBackground(GREEN);
        btnCrear.setForeground(Color.WHITE);
        btnCrear.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCrear.setBorderPainted(false);
        btnCrear.setFocusPainted(false);
        btnCrear.setBounds(300, 305, 160, 32);
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
            localidadCreada= new Localidad(nombre,provincia,latitud,longitud);
            dispose();
        } catch (NumberFormatException ex) {
            mostrarError("Latitud y longitud deben ser números válidos.");
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
