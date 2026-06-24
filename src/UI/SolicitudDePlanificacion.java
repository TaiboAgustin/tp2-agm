package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Logica_Planificador.PlanificadorRed;
import logica.modelo.Localidad;
import logica.modelo.ParametrosPrecio;

public class SolicitudDePlanificacion extends JFrame {

    private static final Color BG       = new Color(18, 18, 19);
    private static final Color GREEN    = new Color(83, 141, 78);
    private static final Color INPUT_BG = new Color(26, 26, 27);
    private static final Color BORDER   = new Color(58, 58, 60);
    private static final Color GRAY     = new Color(129, 131, 132);

    
    private JTextField numCostoKm;
    private JTextField numTarifaInterprovincial;
    private JTextField numCostoDistanciasLargas;

    private DefaultListModel<String> listModel = new DefaultListModel<>();

    public SolicitudDePlanificacion() {
        setTitle("Conectando Localidades — Planificación");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 720, 640);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BG);
        getContentPane().setLayout(null);

        buildHeader();
        buildParams();
        buildLocalidadesList();
        buildButtons();
        cargarLocalidadesGuardadas();
    }

    private void buildHeader() {
        JLabel lblTitulo = new JLabel("NUEVA PLANIFICACIÓN", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(GREEN);
        lblTitulo.setBounds(0, 16, 720, 28);
        getContentPane().add(lblTitulo);

        JLabel sep = new JLabel();
        sep.setBackground(BORDER);
        sep.setOpaque(true);
        sep.setBounds(30, 52, 660, 1);
        getContentPane().add(sep);
    }

    private void buildParams() {
        numCostoKm               = buildField("Costo por kilómetro",           62);
        numTarifaInterprovincial = buildField("Tarifa interprovincial",        136);
        numCostoDistanciasLargas = buildField("Adicional por distancias largas", 210);
    }

    private JTextField buildField(String labelText, int y) {
        JLabel lbl = new JLabel(labelText, SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(110, y, 500, 22);
        getContentPane().add(lbl);

        JTextField field = new JTextField();
        field.setBackground(INPUT_BG);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setHorizontalAlignment(SwingConstants.CENTER);
        field.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        field.setBounds(235, y + 26, 250, 36);
        getContentPane().add(field);

        return field;
    }

    private void buildLocalidadesList() {
        JLabel sep = new JLabel();
        sep.setBackground(BORDER);
        sep.setOpaque(true);
        sep.setBounds(30, 295, 660, 1);
        getContentPane().add(sep);

        JLabel lblSeccion = new JLabel("LOCALIDADES AGREGADAS", SwingConstants.CENTER);
        lblSeccion.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSeccion.setForeground(GRAY);
        lblSeccion.setBounds(0, 305, 720, 18);
        getContentPane().add(lblSeccion);

        JList<String> jListLocalidades = new JList<>(listModel);
        jListLocalidades.setBackground(INPUT_BG);
        jListLocalidades.setForeground(Color.WHITE);
        jListLocalidades.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        jListLocalidades.setSelectionBackground(BORDER);

        JScrollPane scroll = new JScrollPane(jListLocalidades);
        scroll.setBounds(30, 328, 660, 195);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        scroll.getViewport().setBackground(INPUT_BG);
        getContentPane().add(scroll);
    }

    private void buildButtons() {
        JButton btnAgregarLocalidad = new JButton("Agregar localidad");
        btnAgregarLocalidad.setBackground(INPUT_BG);
        btnAgregarLocalidad.setForeground(Color.WHITE);
        btnAgregarLocalidad.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnAgregarLocalidad.setBorderPainted(false);
        btnAgregarLocalidad.setFocusPainted(false);
        btnAgregarLocalidad.setBounds(30, 540, 180, 36);
        btnAgregarLocalidad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AgregarLocalidad dialogo = new AgregarLocalidad(SolicitudDePlanificacion.this);
                dialogo.setVisible(true);
                Localidad nueva = dialogo.getLocalidadCreada();
                if (nueva != null) {
                	if(PlanificadorRed.agregarLocalidad(nueva.getNombre(),nueva.getProvincia(),nueva.getLatitud() ,nueva.getLongitud())) {
                		listModel.addElement(nueva.getNombre() + " — " + nueva.getProvincia());
                	}
                   mostrarMensaje("la localidad ya existe","Error");
                }
            }
        });

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBackground(INPUT_BG);
        btnLimpiar.setForeground(GRAY);
        btnLimpiar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setBounds(220, 540, 100, 36);
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        JButton btnGenerar = new JButton("Generar planificación");
        btnGenerar.setBackground(GREEN);
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGenerar.setBorderPainted(false);
        btnGenerar.setFocusPainted(false);
        btnGenerar.setBounds(480, 540, 210, 36);
        btnGenerar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                crearPlanificacion(numCostoKm,numTarifaInterprovincial,numCostoDistanciasLargas);
            }
        });

        getContentPane().add(btnAgregarLocalidad);
        getContentPane().add(btnLimpiar);
        getContentPane().add(btnGenerar);
    }

    private void cargarLocalidadesGuardadas() {
        new PlanificadorRed().cargarDatos();
        for (Localidad loc : PlanificadorRed.getLocalidades()) {
            
            listModel.addElement(loc.getNombre() + " — " + loc.getProvincia());
        }
    }

    private void limpiarCampos() {
        PlanificadorRed.limpiarLocalidades();
        listModel.clear();
        numCostoKm.setText("");
        numTarifaInterprovincial.setText("");
        numCostoDistanciasLargas.setText("");
    }


    private void crearPlanificacion(JTextField costoKm,JTextField tarifaInterprovincial,JTextField costoDistanciasLargas) {
    	if(PlanificadorRed.empezarPlanificacion(convertToDouble(costoKm),convertToDouble(tarifaInterprovincial),convertToDouble(costoDistanciasLargas))) {		
        PantallaPrincipalMAPA mapa = new PantallaPrincipalMAPA();
        mapa.mostrarVentana();
        dispose();
        }
    	else {
    		mostrarMensaje("Problema a la hora de Iniciar Planificacion","Error");
    	}
    }

    protected void mostrarMensaje(String mensaje, String tipo) {
        JOptionPane.showMessageDialog(this, mensaje, tipo, JOptionPane.ERROR_MESSAGE);
    }

    double convertToDouble(JTextField text) {
        return Double.valueOf(text.getText().trim());
    }
}
