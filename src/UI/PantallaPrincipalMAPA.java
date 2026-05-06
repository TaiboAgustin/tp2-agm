package UI;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;
public class PantallaPrincipalMAPA {

	private JFrame frame;
	private JMapViewer mapa;
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Planificador de Viajes");
		mapa = new JMapViewer();
		mapa.setZoomControlsVisible(false);
		//Zoom a la coordenada de san miguel
		//----------------------------------------------
		Coordinate coodenadasLaPlata=new Coordinate(-34.9214,-57.9544);
		
		mapa.setDisplayPosition(coodenadasLaPlata, 8);
		//----------------------------------------------
		
		Coordinate coodenadasUNGS=new Coordinate(-34.522226661358026, -58.70056343197235);
		//Agregar un marcador
		//----------------------------------------------
		
		MapMarker marca1= new MapMarkerDot("La Plata",coodenadasLaPlata);
		marca1.getStyle().setBackColor(Color.BLUE);			/* Relleno */
		marca1.getStyle().setColor(Color.WHITE); 			/* Contorno */
		

		//Agregar un marcador
		//----------------------------------------------
		
		MapMarker marca2= new MapMarkerDot("UNGS",coodenadasUNGS);
		marca2.getStyle().setBackColor(Color.BLUE);			/* Relleno */
		marca2.getStyle().setColor(Color.yellow); 			/* Contorno */
		
		//Agregar linea de la plata a la UNGS
		//----------------------------------------------
		MapPolygonImpl linea = new MapPolygonImpl(generarLinea(coodenadasLaPlata,coodenadasUNGS));
		linea.getStyle().setColor(Color.black);
		//----------------------------------------------
		//AGREGAR LA MARCA al mapa
		
		mapa.addMapMarker(marca1);
		mapa.addMapMarker(marca2);
		mapa.addMapPolygon(linea);
		//----------------------------------------------

		frame.getContentPane().add(mapa);
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
}
