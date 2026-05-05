package logica.agm;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import logica.modelo.Arista;
import logica.modelo.Grafo;
import logica.modelo.Localidad;

public class IntegracionAGMTest {

    private Localidad buenosAires;
    private Localidad rosario;
    private Localidad cordoba;
    private Localidad mendoza;
    private Localidad laPlata;
    private AlgoritmoKruskal<Localidad> kruskal;

    @Before
    public void setUp() {
        buenosAires = new Localidad("Buenos Aires", "Buenos Aires", -34.6, -58.4);
        rosario     = new Localidad("Rosario", "Santa Fe", -32.9, -60.7);
        cordoba     = new Localidad("Córdoba", "Córdoba", -31.4, -64.2);
        mendoza     = new Localidad("Mendoza", "Mendoza", -32.9, -68.8);
        laPlata     = new Localidad("La Plata", "Buenos Aires", -34.9, -57.9);
        kruskal     = new AlgoritmoKruskal<>();
    }

    @Test
    public void agmConDosLocalidadesTieneUnaConexion() {
        Grafo<Localidad> grafo = new Grafo<>(
            Arrays.asList(buenosAires, rosario),
            Arrays.asList(new Arista<>(buenosAires, rosario, 300.0))
        );
        ResultadoAGM<Localidad> resultado = kruskal.calcular(grafo);
        assertEquals(1, resultado.getConexiones().size());
    }

    @Test
    public void agmConTresLocalidadesEvitaLaAristaMasCara() {
        List<Arista<Localidad>> aristas = Arrays.asList(
            new Arista<>(buenosAires, rosario, 1.0),
            new Arista<>(rosario,     cordoba, 2.0),
            new Arista<>(buenosAires, cordoba, 10.0)
        );
        Grafo<Localidad> grafo = new Grafo<>(Arrays.asList(buenosAires, rosario, cordoba), aristas);
        ResultadoAGM<Localidad> resultado = kruskal.calcular(grafo);

        assertEquals(3.0, resultado.getCostoTotal(), 0.001);
        assertFalse(contieneArista(resultado.getConexiones(), buenosAires, cordoba));
    }

    @Test
    public void todosLosNodosAparecenEnElResultado() {
        Grafo<Localidad> grafo = grafoCompleto4Nodos();
        ResultadoAGM<Localidad> resultado = kruskal.calcular(grafo);

        Set<Localidad> nodosEnResultado = new HashSet<>();
        for (Arista<Localidad> a : resultado.getConexiones()) {
            nodosEnResultado.add(a.getOrigen());
            nodosEnResultado.add(a.getDestino());
        }

        assertTrue(nodosEnResultado.contains(buenosAires));
        assertTrue(nodosEnResultado.contains(rosario));
        assertTrue(nodosEnResultado.contains(cordoba));
        assertTrue(nodosEnResultado.contains(mendoza));
    }

    @Test
    public void costoDelAgmEsMenorQueArbolAlternativo() {
        Grafo<Localidad> grafo = grafoCompleto4Nodos();
        ResultadoAGM<Localidad> resultado = kruskal.calcular(grafo);

        // árbol alternativo: BsAs-Córdoba (700) + BsAs-Rosario (300) + BsAs-Mendoza (900) = 1900
        double costoAlternativo = 700.0 + 300.0 + 900.0;
        assertTrue(resultado.getCostoTotal() < costoAlternativo);
    }

    @Test
    public void agmConCincoLocalidadesTieneCuatroConexiones() {
        Grafo<Localidad> grafo = grafoCompleto5Nodos();
        ResultadoAGM<Localidad> resultado = kruskal.calcular(grafo);
        assertEquals(4, resultado.getConexiones().size());
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

    private Grafo<Localidad> grafoCompleto5Nodos() {
        List<Arista<Localidad>> aristas = Arrays.asList(
            new Arista<>(buenosAires, rosario,  300.0),
            new Arista<>(buenosAires, cordoba,  700.0),
            new Arista<>(buenosAires, mendoza,  900.0),
            new Arista<>(buenosAires, laPlata,   50.0),
            new Arista<>(rosario,     cordoba,  200.0),
            new Arista<>(rosario,     mendoza,  600.0),
            new Arista<>(rosario,     laPlata,  320.0),
            new Arista<>(cordoba,     mendoza,  400.0),
            new Arista<>(cordoba,     laPlata,  720.0),
            new Arista<>(mendoza,     laPlata,  950.0)
        );
        return new Grafo<>(Arrays.asList(buenosAires, rosario, cordoba, mendoza, laPlata), aristas);
    }

    private boolean contieneArista(List<Arista<Localidad>> aristas, Localidad origen, Localidad destino) {
        return aristas.stream().anyMatch(a ->
            a.getOrigen().equals(origen) && a.getDestino().equals(destino)
        );
    }
}
