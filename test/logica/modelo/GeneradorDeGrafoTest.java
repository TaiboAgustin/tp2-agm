package logica.modelo;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

public class GeneradorDeGrafoTest {

    private ParametrosPrecio parametros;

    @Before
    public void setUp() {
        // Parámetros de prueba: $1000/km, 20% aumento si > 300km, $50000 fijo si cambia provincia
        parametros = new ParametrosPrecio(1000.0, 20.0, 50000.0);
    }

    @Test
    public void testDistanciaCorrecta() {
        // CABA a Córdoba Capital son aprox 646 km
        Localidad caba = new Localidad("CABA", "Buenos Aires", -34.6037, -58.3816);
        Localidad cordoba = new Localidad("Córdoba", "Córdoba", -31.4201, -64.1888);

        double distancia = GeneradorDeGrafo.calcularDistancia(caba, cordoba);
        
        // Tolerancia de 5km por la curvatura de la tierra
        assertEquals(646.0, distancia, 5.0); 
    }

    @Test
    public void testCostoConTodosLosRecargos() {
        // CABA a Córdoba: > 300km (aplica 20%) y distinta provincia (aplica $50000)
        Localidad caba = new Localidad("CABA", "Buenos Aires", -34.6037, -58.3816);
        Localidad cordoba = new Localidad("Córdoba", "Córdoba", -31.4201, -64.1888);
        
        double distancia = GeneradorDeGrafo.calcularDistancia(caba, cordoba);
        
        double costoEsperado = (distancia * 1000.0); // Costo base
        costoEsperado += costoEsperado * 0.20;       // +20% por distancia
        costoEsperado += 50000.0;                    // + Fijo por provincia

        double costoReal = GeneradorDeGrafo.calcularCosto(caba, cordoba, parametros);
        
        // Tolerancia de 0.1 pesos en el cálculo
        assertEquals(costoEsperado, costoReal, 0.1);
    }

    @Test
    public void testGrafoCompletoGeneraTodasLasConexiones() {
        Localidad loc1 = new Localidad("A", "Prov1", -34.0, -58.0);
        Localidad loc2 = new Localidad("B", "Prov1", -34.1, -58.1);
        Localidad loc3 = new Localidad("C", "Prov1", -34.2, -58.2);
        
        List<Localidad> lista = Arrays.asList(loc1, loc2, loc3);
        
        Grafo<Localidad> grafo = GeneradorDeGrafo.construirGrafoCompleto(lista, parametros);
        
        // 3 localidades deben generar exactamente 3 aristas en un grafo completo
        // Además, esto prueba implícitamente que la validación n*(n-1)/2 de tus compañeros funciona bien
        assertEquals(3, grafo.getAristas().size());
    }
}