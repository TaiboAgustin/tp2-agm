package logica.modelo;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

public class GrafoTest {

    private final Localidad buenosAires = new Localidad("Buenos Aires", "Buenos Aires", -34.6, -58.4);
    private final Localidad rosario = new Localidad("Rosario", "Santa Fe", -32.9, -60.7);
    private final Localidad cordoba = new Localidad("Córdoba", "Córdoba", -31.4, -64.2);

    @Test
    public void seCreaCorrectamenteConDosNodos() {
        Arista<Localidad> a = new Arista<>(buenosAires, rosario, 1000.0);
        Grafo<Localidad> g = new Grafo<>(Arrays.asList(buenosAires, rosario), Arrays.asList(a));
        assertEquals(2, g.getNodos().size());
        assertEquals(1, g.getAristas().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void menosDeDosNodosLanzaExcepcion() {
        new Grafo<>(Arrays.asList(buenosAires), Arrays.asList());
    }
}
