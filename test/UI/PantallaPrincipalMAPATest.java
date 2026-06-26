package UI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Test;
import org.openstreetmap.gui.jmapviewer.Coordinate;

public class PantallaPrincipalMAPATest {

    @Test
    public void generarLineaNoEsNula() {
        Coordinate origen = new Coordinate(-34.0, -58.0);
        Coordinate destino = new Coordinate(-35.0, -59.0);
        ArrayList<Coordinate> linea = PantallaPrincipalMAPA.generarLinea(origen, destino);
        assertNotNull(linea);
    }

    @Test
    public void generarLineaTienePuntos() {
        Coordinate origen = new Coordinate(-34.0, -58.0);
        Coordinate destino = new Coordinate(-35.0, -59.0);
        ArrayList<Coordinate> linea = PantallaPrincipalMAPA.generarLinea(origen, destino);
        assertFalse(linea.isEmpty());
    }

    @Test
    public void primerPuntoEsOrigen() {
        Coordinate origen = new Coordinate(-34.0, -58.0);
        Coordinate destino = new Coordinate(-35.0, -59.0);
        ArrayList<Coordinate> linea = PantallaPrincipalMAPA.generarLinea(origen, destino);
        assertEquals(origen.getLat(), linea.get(0).getLat(), 0.001);
        assertEquals(origen.getLon(), linea.get(0).getLon(), 0.001);
    }

    @Test
    public void ultimoPuntoEsDestino() {
        Coordinate origen = new Coordinate(-34.0, -58.0);
        Coordinate destino = new Coordinate(-35.0, -59.0);
        ArrayList<Coordinate> linea = PantallaPrincipalMAPA.generarLinea(origen, destino);
        Coordinate ultimo = linea.get(linea.size() - 1);
        assertEquals(destino.getLat(), ultimo.getLat(), 0.001);
        assertEquals(destino.getLon(), ultimo.getLon(), 0.001);
    }

    @Test
    public void cantidadDePuntosEs51() {
        Coordinate origen = new Coordinate(-34.0, -58.0);
        Coordinate destino = new Coordinate(-35.0, -59.0);
        ArrayList<Coordinate> linea = PantallaPrincipalMAPA.generarLinea(origen, destino);
        assertEquals(51, linea.size());
    }

    @Test
    public void generarLineaMismoPuntoProducePuntosDuplicados() {
        Coordinate c = new Coordinate(-34, -58);
        ArrayList<Coordinate> linea = PantallaPrincipalMAPA.generarLinea(c, c);
        assertEquals(51, linea.size());
    }
}
