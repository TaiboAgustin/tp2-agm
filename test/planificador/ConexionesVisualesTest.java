package planificador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import Logica_Planificador.ConexionVisual;

public class ConexionesVisualesTest {

    @Test
    public void creacionCorrecta() {
        ConexionVisual conexion = new ConexionVisual(-34.6, -58.4, -31.4, -64.2);
        assertEquals(-34.6, conexion.getLat1(), 0.001);
        assertEquals(-58.4, conexion.getLon1(), 0.001);
        assertEquals(-31.4, conexion.getLat2(), 0.001);
        assertEquals(-64.2, conexion.getLon2(), 0.001);
    }

    @Test
    public void coordenadasDistintas() {
        ConexionVisual conexion = new ConexionVisual(1, 2, 3, 4);
        assertNotEquals(conexion.getLat1(), conexion.getLat2(), 0.001);
    }

    @Test
    public void valoresNegativos() {
        ConexionVisual conexion = new ConexionVisual(-90, -180, -45, -60);
        assertEquals(-90, conexion.getLat1(), 0.001);
        assertEquals(-180, conexion.getLon1(), 0.001);
    }

    @Test
    public void valoresCero() {
        ConexionVisual conexion = new ConexionVisual(0, 0, 0, 0);
        assertEquals(0, conexion.getLat1(), 0.001);
        assertEquals(0, conexion.getLon1(), 0.001);
    }
}
