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

        double distancia = caba.calcularDistancia(cordoba);
        
        // Tolerancia de 5km por la curvatura de la tierra
        assertEquals(646.0, distancia, 5.0); 
    }
    @Test
    public void testCostoConTodosLosRecargos() {
        Localidad caba = new Localidad("CABA", "Buenos Aires", -34.6037, -58.3816);
        Localidad cordoba = new Localidad("Cordoba", "Cordoba", -31.4201, -64.1888);
        
        // Creamos los parámetros con valores de prueba (10.0 costo km, 50.0 fijo, 1.5% aumento)
        ParametrosPrecio param = new ParametrosPrecio(10.0, 50.0, 1.5);
        
        double distancia = caba.calcularDistancia(cordoba);
        
        // Cálculo manual para el "esperado"
        double costoBase = distancia * param.getCostoPorKm();
        double conAumento = costoBase * (1 + param.getPorcentajeAumento() / 100.0);
        double costoEsperado = conAumento + param.getCostoFijoInterprovincial();

        // Llamada al método de tu clase (asegurate de usar el nombre correcto de tu clase)
        double costoReal = GeneradorDeGrafo.calcularCosto(caba, cordoba, param);

        assertEquals(costoEsperado, costoReal, 1.0);
    }

    @Test
    public void testGrafoCompletoGeneraTodasLasConexiones() {
        Localidad loc1 = new Localidad("A", "Prov1", -34.0, -58.0);
        Localidad loc2 = new Localidad("B", "Prov1", -34.1, -58.1);
        Localidad loc3 = new Localidad("C", "Prov1", -34.2, -58.2);
        
        List<Localidad> lista = Arrays.asList(loc1, loc2, loc3);
        
        Grafo<Localidad> grafo = GeneradorDeGrafo.construirGrafoCompleto(lista, parametros);
        
        // 3 localidades deben generar exactamente 3 aristas en un grafo completo
        // Además, esto prueba implícitamente que la validación n*(n-1)/2
        assertEquals(3, grafo.getAristas().size());
    }
}