package logica.modelo;

import org.junit.Test;
import static org.junit.Assert.*;

public class AristaTest {

    private final Localidad buenosAires = new Localidad("Buenos Aires", "Buenos Aires", -34.6, -58.4);
    private final Localidad rosario = new Localidad("Rosario", "Santa Fe", -32.9, -60.7);

    @Test
    public void seCreaCorrectamente() {
        Arista<Localidad> a = new Arista<>(buenosAires, rosario, 1000.0);
        assertEquals(buenosAires, a.getOrigen());
        assertEquals(rosario, a.getDestino());
        assertEquals(1000.0, a.getCosto(), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void origenNuloLanzaExcepcion() {
        new Arista<>(null, rosario, 1000.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void destinoNuloLanzaExcepcion() {
        new Arista<>(buenosAires, null, 1000.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void origenIgualDestinoLanzaExcepcion() {
        new Arista<>(buenosAires, buenosAires, 1000.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void costoNegativoLanzaExcepcion() {
        new Arista<>(buenosAires, rosario, -1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void costoCeroLanzaExcepcion() {
        new Arista<>(buenosAires, rosario, 0.0);
    }
}
