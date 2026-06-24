package logica.modelo;

import org.junit.Test;
import static org.junit.Assert.*;

public class AristaTest {

    private final Localidad buenosAires = new Localidad("Buenos Aires", "Buenos Aires", -34.6, -58.4);
    private final Localidad rosario = new Localidad("Rosario", "Santa Fe", -32.9, -60.7);

    @Test
    public void seCreaCorrectamente() {
        Arista<Localidad> a = new Arista<>(buenosAires, rosario, 1000.0);
        assertEquals(buenosAires, a.getVertice1());
        assertEquals(rosario, a.getVertice2());
        assertEquals(1000.0, a.getPeso(), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void vertice1NuloLanzaExcepcion() {
        new Arista<>(null, rosario, 1000.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void vertice2NuloLanzaExcepcion() {
        new Arista<>(buenosAires, null, 1000.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void verticesIgualesLanzaExcepcion() {
        new Arista<>(buenosAires, buenosAires, 1000.0);
    }

    @Test
    public void aceptaPesoNegativo() {
        Arista<Localidad> a = new Arista<>(buenosAires, rosario, -1.0);
        assertEquals(-1.0, a.getPeso(), 0.001);
    }

    @Test
    public void aceptaPesoCero() {
        Arista<Localidad> a = new Arista<>(buenosAires, rosario, 0.0);
        assertEquals(0.0, a.getPeso(), 0.001);
    }
}