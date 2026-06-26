package logica.agm;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import logica.modelo.Arista;
import logica.modelo.Grafo;
import logica.modelo.Localidad;

public class AlgoritmoKruskalTest {

    private Localidad buenosAires;
    private Localidad rosario;
    private Localidad cordoba;
    private Localidad mendoza;
    private AlgoritmoKruskal<Localidad> kruskal;

    @Before
    public void setUp() {
        buenosAires = new Localidad("Buenos Aires", "Buenos Aires", -34.6, -58.4);
        rosario     = new Localidad("Rosario", "Santa Fe", -32.9, -60.7);
        cordoba     = new Localidad("Córdoba", "Córdoba", -31.4, -64.2);
        mendoza     = new Localidad("Mendoza", "Mendoza", -32.9, -68.8);
        kruskal     = new AlgoritmoKruskal<>();
    }

    @Test(expected = IllegalArgumentException.class)
    public void grafoNuloLanzaExcepcion() {
        kruskal.calcular(null);
    }

    @Test
    public void resultadoConDosNodosTieneUnaArista() {
        Arista<Localidad> a = new Arista<>(buenosAires, rosario, 300.0);
        Grafo<Localidad> grafo = new Grafo<>(Arrays.asList(buenosAires, rosario), Arrays.asList(a));
        
        // Ahora devuelve un Grafo
        Grafo<Localidad> resultado = kruskal.calcular(grafo);
        assertEquals(1, resultado.getAristas().size());
    }

    @Test
    public void resultadoTieneNMenosUnaAristas() {
        Grafo<Localidad> grafo = grafoCompleto4Nodos();
        Grafo<Localidad> resultado = kruskal.calcular(grafo);
        assertEquals(3, resultado.getAristas().size());
    }

    @Test
    public void costoTotalEsElMinimo() {
        Grafo<Localidad> grafo = grafoCompleto4Nodos();
        Grafo<Localidad> resultado = kruskal.calcular(grafo);
        
        assertEquals(900.0, GrafoTestHelper.calcularCostoTotal(resultado), 0.001);
    }

    @Test
    public void seSeleccionanLasAristasDeMenorCosto() {
        Grafo<Localidad> grafo = grafoCompleto4Nodos();
        Grafo<Localidad> resultado = kruskal.calcular(grafo);
        List<Arista<Localidad>> conexiones = resultado.getAristas();

        assertTrue(contieneArista(conexiones, rosario, cordoba, 200.0));
        assertTrue(contieneArista(conexiones, buenosAires, rosario, 300.0));
        assertTrue(contieneArista(conexiones, cordoba, mendoza, 400.0));
    }

    private Grafo<Localidad> grafoCompleto4Nodos() {
        List<Arista<Localidad>> aristas = Arrays.asList(
            new Arista<>(buenosAires, rosario,  300.0),
            new Arista<>(buenosAires, cordoba,  700.0),
            new Arista<>(buenosAires, mendoza,  900.0),
            new Arista<>(rosario,     cordoba,  200.0),
            new Arista<>(rosario,     mendoza,  600.0),
            new Arista<>(cordoba,     mendoza,  400.0)
        );
        return new Grafo<>(Arrays.asList(buenosAires, rosario, cordoba, mendoza), aristas);
    }

    
    private boolean contieneArista(List<Arista<Localidad>> aristas, Localidad v1, Localidad v2, double peso) {
        return aristas.stream().anyMatch(a ->
            ((a.getVertice1().equals(v1) && a.getVertice2().equals(v2)) ||
             (a.getVertice1().equals(v2) && a.getVertice2().equals(v1)))
            && a.getPeso() == peso
        );
    }
}