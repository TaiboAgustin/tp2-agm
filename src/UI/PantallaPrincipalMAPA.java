package UI;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;

import java.util.List;

import javax.swing.JFrame;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import Logica_Planificador.ConexionVisual;
import Logica_Planificador.PlanificadorRed;
import logica.agm.ResultadoAGM;
import logica.modelo.Localidad;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
public class PantallaPrincipalMAPA {

	private JFrame frame;
	private JMapViewer mapa;
	private JTextField datoNombre;
	private JTextField datoProvincia;
	private JTextField datoLatitud;
	private JTextField datoLongitud;
	private JLabel lblResultado = new JLabel();
	private final Action action = new SwingAction();
	private final Action action_1 = new SwingAction_1();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PantallaPrincipalMAPA window = new PantallaPrincipalMAPA();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PantallaPrincipalMAPA() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 652, 457);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Planificador de Viajes");
		
		//----------------------------------------------
		JPanel panel_mapa = new JPanel();
		panel_mapa.setBounds(137, 11, 287, 378);
		panel_mapa.setBackground(Color.black);
		//----------------------------------------------
		mapa = new JMapViewer();
		mapa.setBounds(226, 11, 400, 396);
		Coordinate centroArgentina = new Coordinate(-38.4161, -63.6167);
		mapa.setDisplayPosition(centroArgentina, 4);
		//----------------------------------------------
		PlanificadorRed Control = new PlanificadorRed();
		Control.cargarDatos();
		cargarLocalidadesEnMapa();
		//----------------------------------------------
		
		
		frame.setContentPane(panel_mapa);
		panel_mapa.setLayout(null);
		panel_mapa.add(mapa);
		mapa.setLayout(null);
		
		datoNombre = new JTextField();
		datoNombre.setBackground(new Color(0, 0, 0));
		datoNombre.setForeground(new Color(255, 255, 255));
		datoNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
		datoNombre.setBounds(99, 42, 74, 20);
		panel_mapa.add(datoNombre);
		datoNombre.setColumns(10);
		
		datoProvincia = new JTextField();
		datoProvincia.setForeground(Color.WHITE);
		datoProvincia.setBackground(new Color(0, 0, 0));
		datoProvincia.setFont(new Font("Tahoma", Font.PLAIN, 15));
		datoProvincia.setColumns(10);
		datoProvincia.setBounds(99, 73, 74, 20);
		panel_mapa.add(datoProvincia);
		
		datoLatitud = new JTextField();
		datoLatitud.setForeground(Color.WHITE);
		datoLatitud.setBackground(new Color(0, 0, 0));
		datoLatitud.setFont(new Font("Tahoma", Font.PLAIN, 15));
		datoLatitud.setColumns(10);
		datoLatitud.setBounds(99, 104, 74, 20);
		panel_mapa.add(datoLatitud);
		
		datoLongitud = new JTextField();
		datoLongitud.setForeground(Color.WHITE);
		datoLongitud.setBackground(new Color(0, 0, 0));
		datoLongitud.setFont(new Font("Tahoma", Font.PLAIN, 15));
		datoLongitud.setColumns(10);
		datoLongitud.setBounds(99, 135, 74, 20);
		panel_mapa.add(datoLongitud);
		
		JButton btnNewButton = new JButton("Agregar Localidad");
		btnNewButton.setAction(action);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setBounds(56, 165, 138, 23);
		panel_mapa.add(btnNewButton);
		
		JLabel lblNombre = new JLabel("Nombre :");
		lblNombre.setForeground(Color.WHITE);
		lblNombre.setBounds(10, 42, 58, 20);
		panel_mapa.add(lblNombre);
		
		JLabel lblProvincia = new JLabel("Provincia :");
		lblProvincia.setForeground(Color.WHITE);
		lblProvincia.setBounds(10, 73, 58, 20);
		panel_mapa.add(lblProvincia);
		
		JLabel lblLatitud = new JLabel("Latitud :");
		lblLatitud.setForeground(Color.WHITE);
		lblLatitud.setBounds(10, 104, 58, 20);
		panel_mapa.add(lblLatitud);
		
		JLabel lblLongitud = new JLabel("Longitud :");
		lblLongitud.setForeground(Color.WHITE);
		lblLongitud.setBounds(10, 135, 58, 20);
		panel_mapa.add(lblLongitud);
		
		JLabel lblPlanificadorDeViajes = new JLabel("Planificador de  Viajes");
		lblPlanificadorDeViajes.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlanificadorDeViajes.setForeground(Color.WHITE);
		lblPlanificadorDeViajes.setBounds(10, 11, 163, 20);
		panel_mapa.add(lblPlanificadorDeViajes);
		
		JButton btnNewButton_1 = new JButton("Generar Planificacion");
		btnNewButton_1.setAction(action_1);
		btnNewButton_1.setBounds(10, 199, 184, 36);
		panel_mapa.add(btnNewButton_1);
		
		JLabel lblNewLabel_1 = new JLabel("Resultado AGM");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(10, 269, 206, 36);
		panel_mapa.add(lblNewLabel_1);
		
		
		lblResultado.setForeground(Color.WHITE);
		lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultado.setBounds(10, 316, 206, 36);
		panel_mapa.add(lblResultado);
		
		
	
	}
	
	public static ArrayList<Coordinate> generarLinea(
            Coordinate origen,
            Coordinate destino) {

        ArrayList<Coordinate> linea = new ArrayList<>();

        // cantidad de puntos intermedios
        int pasos = 50;

        double latPaso = (destino.getLat() - origen.getLat()) / pasos;
        double lonPaso = (destino.getLon() - origen.getLon()) / pasos;

        for (int i = 0; i <= pasos; i++) {

            double nuevaLat = origen.getLat() + (latPaso * i);
            double nuevaLon = origen.getLon() + (lonPaso * i);

            linea.add(new Coordinate(nuevaLat, nuevaLon));
        }

        return linea;
    }
	private void cargarLocalidadesEnMapa() {

	    List<Localidad> localidades =
	            PlanificadorRed.getLocalidades();

	    for (Localidad loc : localidades) {

	        agregarMarcador(loc);
	    }
	}
	private void agregarMarcador(Localidad loc) {

	    Coordinate coord = new Coordinate(
	            loc.getLatitud(),
	            loc.getLongitud()
	    );

	    MapMarker marcador = new MapMarkerDot(
	            loc.getNombre(),
	            coord
	    );

	    marcador.getStyle().setBackColor(Color.black);
	    marcador.getStyle().setColor(Color.WHITE);

	    mapa.addMapMarker(marcador);
	}
	
	private void dibujarAGM(List<ConexionVisual> conexiones) {
	    for (ConexionVisual c : conexiones) {
	        Coordinate origen =
	                new Coordinate(
	                        c.getLat1(),
	                        c.getLon1()
	                );
	        Coordinate destino =
	                new Coordinate(c.getLat2(),c.getLon2());
	        MapPolygonImpl linea = new MapPolygonImpl(generarLinea(origen, destino ));
	        mapa.addMapPolygon(linea);
	    }
	}
	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Agregar Localidad");
			putValue(SHORT_DESCRIPTION, "Desea Agregar una nueva Localidad");
		}
		public void actionPerformed(ActionEvent e) {
			PlanificadorRed.agregarLocalidad(datoNombre.getText(),datoProvincia.getText(),Double.parseDouble(datoLatitud.getText()),Double.parseDouble(datoLongitud.getText()));
			mapa.removeAllMapMarkers();
			cargarLocalidadesEnMapa();	
		}
	}
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "Generacion de AGM");
			putValue(SHORT_DESCRIPTION, "AGM?");
		}
		public void actionPerformed(ActionEvent e) { 
			ResultadoAGM<Localidad> resultado = PlanificadorRed.calcularAGM();
			List<ConexionVisual> conexiones= PlanificadorRed.generarConexionesVisuales(resultado);
			mapa.removeAllMapPolygons();
			dibujarAGM(conexiones);
		
		    lblResultado.setText(
		            "Costo total: " +
		            resultado.getCostoTotal()
		    );
		}
	}
	public void mostrarVentana() {
		PantallaPrincipalMAPA window = new PantallaPrincipalMAPA();
		window.frame.setVisible(true);
		// TODO Auto-generated method stub
		
	}
}
