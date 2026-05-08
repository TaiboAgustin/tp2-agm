package UI;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;

import Logica_Planificador.PlanificadorRed;
import logica.modelo.Localidad;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
public class PantallaPrincipalMAPA {

	private JFrame frame;
	private JMapViewer mapa;
	private JTextField DatoNombre;
	private JTextField datoProvincia;
	private JTextField datoLatitud;
	private JTextField datoLongitud;
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
		
		
		//----------------------------------------------
		PlanificadorRed Control = new PlanificadorRed();
		Control.cargarDatos();
		cargarLocalidadesEnMapa();
		//----------------------------------------------
		
		
		frame.setContentPane(panel_mapa);
		panel_mapa.setLayout(null);
		panel_mapa.add(mapa);
		mapa.setLayout(null);
		
		DatoNombre = new JTextField();
		DatoNombre.setBackground(new Color(0, 0, 0));
		DatoNombre.setForeground(new Color(255, 255, 255));
		DatoNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
		DatoNombre.setBounds(99, 42, 74, 20);
		panel_mapa.add(DatoNombre);
		DatoNombre.setColumns(10);
		
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
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setBounds(75, 165, 119, 23);
		panel_mapa.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Nombre :");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 42, 58, 20);
		panel_mapa.add(lblNewLabel);
		
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
		btnNewButton_1.setBounds(10, 199, 184, 36);
		panel_mapa.add(btnNewButton_1);
		
		JLabel lblNewLabel_1 = new JLabel("Resultado AGM");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(10, 269, 206, 36);
		panel_mapa.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("//Aqui va el resultado");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(10, 316, 206, 36);
		panel_mapa.add(lblNewLabel_2);
		
		
	
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

	    marcador.getStyle().setBackColor(Color.BLUE);
	    marcador.getStyle().setColor(Color.WHITE);

	    mapa.addMapMarker(marcador);
	}
}
