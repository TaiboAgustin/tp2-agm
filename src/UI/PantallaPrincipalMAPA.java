package UI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import Logica_Planificador.ConexionVisual;
import Logica_Planificador.PlanificadorRed;
import logica.modelo.Arista;
import logica.modelo.Grafo;
import logica.modelo.Localidad;

public class PantallaPrincipalMAPA {

    private static final Color BG       = new Color(18, 18, 19);
    private static final Color GREEN    = new Color(83, 141, 78);
    private static final Color INPUT_BG = new Color(26, 26, 27);
    private static final Color BORDER   = new Color(58, 58, 60);
    private static final Color GRAY     = new Color(129, 131, 132);

    private JFrame frame;
    private JMapViewer mapa;
    private JTextField datoNombre;
    private JTextField datoProvincia;
    private JTextField datoLatitud;
    private JTextField datoLongitud;
    private JLabel lblResultado;
    private JTextArea areaConexiones;
    private final Action action   = new SwingAction();
    private final Action action_1 = new SwingAction_1();

    private final PlanificadorRed planificador;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PlanificadorRed p = new PlanificadorRed();
                    p.cargarDatos();
                    PantallaPrincipalMAPA window = new PantallaPrincipalMAPA(p);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public PantallaPrincipalMAPA(PlanificadorRed planificador) {
        this.planificador = planificador;
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Conectando Localidades — Mapa");
        frame.setBounds(100, 100, 1100, 680);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel contenedor = new JPanel();
        contenedor.setBackground(BG);
        contenedor.setLayout(null);
        frame.setContentPane(contenedor);

        buildSidebar(contenedor);
        buildMapa(contenedor);
        cargarLocalidadesEnMapa();
    }

    private void buildSidebar(JPanel contenedor) {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(BG);
        sidebar.setLayout(null);
        sidebar.setBounds(0, 0, 220, 650);
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER));
        contenedor.add(sidebar);

        JLabel lblTitulo = new JLabel("LOCALIDADES", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(GREEN);
        lblTitulo.setBounds(10, 14, 200, 24);
        sidebar.add(lblTitulo);

        JLabel sep1 = new JLabel();
        sep1.setBackground(BORDER);
        sep1.setOpaque(true);
        sep1.setBounds(10, 46, 200, 1);
        sidebar.add(sep1);

        datoNombre    = buildSidebarField(sidebar, "Nombre",    58);
        datoProvincia = buildSidebarField(sidebar, "Provincia", 118);
        datoLatitud   = buildSidebarField(sidebar, "Latitud",   178);
        datoLongitud  = buildSidebarField(sidebar, "Longitud",  238);

        JButton btnAgregar = new JButton();
        btnAgregar.setAction(action);
        styleButton(btnAgregar, GREEN);
        btnAgregar.setBounds(10, 300, 200, 36);
        sidebar.add(btnAgregar);

        JLabel sep2 = new JLabel();
        sep2.setBackground(BORDER);
        sep2.setOpaque(true);
        sep2.setBounds(10, 355, 200, 1);
        sidebar.add(sep2);

        JButton btnAGM = new JButton();
        btnAGM.setAction(action_1);
        styleButton(btnAGM, GREEN);
        btnAGM.setBounds(10, 370, 200, 36);
        sidebar.add(btnAGM);

        JLabel lblResultadoTitulo = new JLabel("Resultado AGM", SwingConstants.CENTER);
        lblResultadoTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblResultadoTitulo.setForeground(GRAY);
        lblResultadoTitulo.setBounds(10, 425, 200, 20);
        sidebar.add(lblResultadoTitulo);

        lblResultado = new JLabel("—", SwingConstants.CENTER);
        lblResultado.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblResultado.setForeground(Color.WHITE);
        lblResultado.setBounds(10, 450, 200, 24);
        sidebar.add(lblResultado);

        JLabel sep3 = new JLabel();
        sep3.setBackground(BORDER);
        sep3.setOpaque(true);
        sep3.setBounds(10, 496, 200, 1);
        sidebar.add(sep3);

        JButton btnVolver = new JButton("← Nueva planificación");
        styleButton(btnVolver, INPUT_BG);
        btnVolver.setBounds(10, 508, 200, 36);
        btnVolver.addActionListener(ev -> {
            new SolicitudDePlanificacion().setVisible(true);
            frame.dispose();
        });
        sidebar.add(btnVolver);

        JLabel sep4 = new JLabel();
        sep4.setBackground(BORDER);
        sep4.setOpaque(true);
        sep4.setBounds(10, 554, 200, 1);
        sidebar.add(sep4);

        JLabel lblConexionesTitulo = new JLabel("Costo por conexión", SwingConstants.CENTER);
        lblConexionesTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblConexionesTitulo.setForeground(GRAY);
        lblConexionesTitulo.setBounds(10, 560, 200, 16);
        sidebar.add(lblConexionesTitulo);

        areaConexiones = new JTextArea();
        areaConexiones.setBackground(INPUT_BG);
        areaConexiones.setForeground(Color.WHITE);
        areaConexiones.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        areaConexiones.setEditable(false);
        areaConexiones.setBorder(null);

        JScrollPane scrollConexiones = new JScrollPane(areaConexiones);
        scrollConexiones.setBounds(10, 580, 200, 58);
        scrollConexiones.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        scrollConexiones.getViewport().setBackground(INPUT_BG);
        sidebar.add(scrollConexiones);
    }

    private JTextField buildSidebarField(JPanel panel, String labelText, int y) {
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(GRAY);
        lbl.setBounds(10, y, 200, 18);
        panel.add(lbl);

        JTextField field = new JTextField();
        field.setBackground(INPUT_BG);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        field.setBounds(10, y + 20, 200, 32);
        panel.add(field);

        return field;
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
    }

    private void buildMapa(JPanel contenedor) {
        mapa = new JMapViewer();
        mapa.setBounds(220, 0, 875, 650);
        Coordinate centroArgentina = new Coordinate(-38.4161, -63.6167);
        mapa.setDisplayPosition(centroArgentina, 4);
        contenedor.add(mapa);
    }

    public static ArrayList<Coordinate> generarLinea(Coordinate origen, Coordinate destino) {
        ArrayList<Coordinate> linea = new ArrayList<>();
        int pasos = 50;
        double latPaso = (destino.getLat() - origen.getLat()) / pasos;
        double lonPaso = (destino.getLon() - origen.getLon()) / pasos;
        for (int i = 0; i <= pasos; i++) {
            linea.add(new Coordinate(
                    origen.getLat() + (latPaso * i),
                    origen.getLon() + (lonPaso * i)));
        }
        return linea;
    }

    private void cargarLocalidadesEnMapa() {
        for (Localidad loc : planificador.getLocalidades()) {
            agregarMarcador(loc);
        }
    }

    private void agregarMarcador(Localidad loc) {
        Coordinate coord = new Coordinate(loc.getLatitud(), loc.getLongitud());
        MapMarker marcador = new MapMarkerDot(loc.getNombre(), coord);
        marcador.getStyle().setBackColor(BG);
        marcador.getStyle().setColor(GREEN);
        mapa.addMapMarker(marcador);
    }

    private void dibujarAGM(List<ConexionVisual> conexiones) {
        for (ConexionVisual c : conexiones) {
            Coordinate origen  = new Coordinate(c.getLat1(), c.getLon1());
            Coordinate destino = new Coordinate(c.getLat2(), c.getLon2());
            mapa.addMapPolygon(new MapPolygonImpl(generarLinea(origen, destino)));
        }
    }

    public void mostrarVentana() {
        frame.setVisible(true);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private class SwingAction extends AbstractAction {
        public SwingAction() {
            putValue(NAME, "Agregar Localidad");
            putValue(SHORT_DESCRIPTION, "Agregar una nueva localidad al mapa");
        }
        public void actionPerformed(ActionEvent e) {
            try {
                double latitud  = Double.parseDouble(datoLatitud.getText().trim());
                double longitud = Double.parseDouble(datoLongitud.getText().trim());
                boolean agregada = planificador.agregarLocalidad(
                        datoNombre.getText().trim(),
                        datoProvincia.getText().trim(),
                        latitud, longitud);
                if (agregada) {
                    mapa.removeAllMapMarkers();
                    cargarLocalidadesEnMapa();
                } else {
                    mostrarError("La localidad ya existe.");
                }
            } catch (NumberFormatException ex) {
                mostrarError("Latitud y longitud deben ser números válidos.");
            } catch (IllegalArgumentException ex) {
                mostrarError(ex.getMessage());
            }
        }
    }

    private class SwingAction_1 extends AbstractAction {
        public SwingAction_1() {
            putValue(NAME, "Generar AGM");
            putValue(SHORT_DESCRIPTION, "Calcular y dibujar el árbol de mínima expansión");
        }
        public void actionPerformed(ActionEvent e) {
            try {
                Grafo<Localidad> resultado = planificador.calcularAGM();
                List<ConexionVisual> conexiones = planificador.generarConexionesVisuales(resultado);
                mapa.removeAllMapPolygons();
                dibujarAGM(conexiones);
                lblResultado.setText(String.format("$%.2f", resultado.getPesoTotal()));

                StringBuilder sb = new StringBuilder();
                for (Arista<Localidad> a : resultado.getAristas()) {
                    sb.append(a.getVertice1().getNombre())
                      .append(" ↔ ")
                      .append(a.getVertice2().getNombre())
                      .append(": $")
                      .append(String.format("%.2f", a.getPeso()))
                      .append("\n");
                }
                areaConexiones.setText(sb.toString().trim());
                areaConexiones.setCaretPosition(0);
            } catch (IllegalArgumentException | IllegalStateException ex) {
                mostrarError(ex.getMessage());
            }
        }
    }
}
