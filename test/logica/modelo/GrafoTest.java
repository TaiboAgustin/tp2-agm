package logica.modelo;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

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

    @Test
    public void agregarAristaIncrementaElConteo() {
        Arista<Localidad> a1 = new Arista<>(buenosAires, rosario, 300.0);
        Arista<Localidad> a2 = new Arista<>(rosario, cordoba, 200.0);
        Grafo<Localidad> g = new Grafo<>(Arrays.asList(buenosAires, rosario, cordoba), Arrays.asList(a1));
        g.agregarArista(a2);
        assertEquals(2, g.getAristas().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void agregarAristaConVerticeAjenoLanzaExcepcion() {
        Localidad mendoza = new Localidad("Mendoza", "Mendoza", -32.9, -68.8);
        Arista<Localidad> a1 = new Arista<>(buenosAires, rosario, 300.0);
        Grafo<Localidad> g = new Grafo<>(Arrays.asList(buenosAires, rosario), Arrays.asList(a1));
        g.agregarArista(new Arista<>(buenosAires, mendoza, 500.0));
    }

    @Test
    public void eliminarAristaExistenteRetornaTrue() {
        Arista<Localidad> a = new Arista<>(buenosAires, rosario, 300.0);
        Grafo<Localidad> g = new Grafo<>(Arrays.asList(buenosAires, rosario), Arrays.asList(a));
        assertTrue(g.eliminarArista(a));
        assertEquals(0, g.getAristas().size());
    }

    @Test
    public void eliminarAristaInexistenteRetornaFalse() {
        Arista<Localidad> a1 = new Arista<>(buenosAires, rosario, 300.0);
        Arista<Localidad> a2 = new Arista<>(buenosAires, rosario, 999.0);
        Grafo<Localidad> g = new Grafo<>(Arrays.asList(buenosAires, rosario), Arrays.asList(a1));
        assertFalse(g.eliminarArista(a2));
    }

    @Test
    public void getVecinosRetornaLosNodosAdyacentes() {
        Arista<Localidad> a1 = new Arista<>(buenosAires, rosario, 300.0);
        Arista<Localidad> a2 = new Arista<>(buenosAires, cordoba, 700.0);
        Grafo<Localidad> g = new Grafo<>(
            Arrays.asList(buenosAires, rosario, cordoba),
            Arrays.asList(a1, a2)
        );
        List<Localidad> vecinos = g.getVecinos(buenosAires);
        assertEquals(2, vecinos.size());
        assertTrue(vecinos.contains(rosario));
        assertTrue(vecinos.contains(cordoba));
    }

    @Test
    public void getVecinosDesdeAmbosSentidos() {
        Arista<Localidad> a = new Arista<>(buenosAires, rosario, 300.0);
        Grafo<Localidad> g = new Grafo<>(Arrays.asList(buenosAires, rosario), Arrays.asList(a));
        assertTrue(g.getVecinos(rosario).contains(buenosAires));
        assertTrue(g.getVecinos(buenosAires).contains(rosario));
    }

    @Test
    public void getVecinosNodoSinConexionesRetornaListaVacia() {
        Arista<Localidad> a = new Arista<>(buenosAires, rosario, 300.0);
        Grafo<Localidad> g = new Grafo<>(
            Arrays.asList(buenosAires, rosario, cordoba),
            Arrays.asList(a)
        );
        assertTrue(g.getVecinos(cordoba).isEmpty());
    }
}
