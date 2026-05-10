package UI;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import UI.PantallaPrincipalMAPA;
public class PantallaPrincipalMAPATest {

    @Test
    public void testGenerarLineaNoNula() {

        Coordinate origen =
                new Coordinate(-34.0, -58.0);

        Coordinate destino =
                new Coordinate(-35.0, -59.0);

        ArrayList<Coordinate> linea =
                PantallaPrincipalMAPA.generarLinea(
                        origen,
                        destino
                );

        assertNotNull(linea);
    }

    @Test
    public void testGenerarLineaTienePuntos() {

        Coordinate origen =
                new Coordinate(-34.0, -58.0);

        Coordinate destino =
                new Coordinate(-35.0, -59.0);

        ArrayList<Coordinate> linea =
                PantallaPrincipalMAPA.generarLinea(
                        origen,
                        destino
                );

        assertFalse(linea.isEmpty());
    }

    @Test
    public void testPrimerPuntoEsOrigen() {

        Coordinate origen =
                new Coordinate(-34.0, -58.0);

        Coordinate destino =
                new Coordinate(-35.0, -59.0);

        ArrayList<Coordinate> linea =
                PantallaPrincipalMAPA.generarLinea(
                        origen,
                        destino
                );

        Coordinate primero = linea.get(0);

        assertEquals(
                origen.getLat(),
                primero.getLat(),
                0.001
        );

        assertEquals(
                origen.getLon(),
                primero.getLon(),
                0.001
        );
    }

    @Test
    public void testUltimoPuntoEsDestino() {

        Coordinate origen =
                new Coordinate(-34.0, -58.0);

        Coordinate destino =
                new Coordinate(-35.0, -59.0);

        ArrayList<Coordinate> linea =
                PantallaPrincipalMAPA.generarLinea(
                        origen,
                        destino
                );

        Coordinate ultimo =
                linea.get(linea.size() - 1);

        assertEquals(
                destino.getLat(),
                ultimo.getLat(),
                0.001
        );

        assertEquals(
                destino.getLon(),
                ultimo.getLon(),
                0.001
        );
    }

    @Test
    public void testCantidadDePuntos() {

        Coordinate origen =
                new Coordinate(-34.0, -58.0);

        Coordinate destino =
                new Coordinate(-35.0, -59.0);

        ArrayList<Coordinate> linea =
                PantallaPrincipalMAPA.generarLinea(
                        origen,
                        destino
                );

        assertEquals(51, linea.size());
    }
    @Test
    public void testGenerarLineaMismoPunto() {

        Coordinate c =
                new Coordinate(-34,-58);

        ArrayList<Coordinate> linea =
                PantallaPrincipalMAPA
                        .generarLinea(c, c);

        assertEquals(51, linea.size());
    }
}
