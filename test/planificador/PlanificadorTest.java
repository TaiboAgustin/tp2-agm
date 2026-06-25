package planificador;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Logica_Planificador.ConexionVisual;
import Logica_Planificador.PlanificadorRed;
import logica.modelo.Grafo;
import logica.modelo.Localidad;
public class PlanificadorTest {
	
    static final Localidad BUENOS_AIRES =
            new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);

    static final Localidad CORDOBA =
            new Localidad("Córdoba", "Córdoba", -31.4201, -64.1888);

    static final Localidad LA_PLATA =
            new Localidad("La Plata", "Buenos Aires", -34.9215, -57.9545);

    static final Localidad ROSARIO =
            new Localidad("Rosario", "Santa Fe", -32.9468, -60.6393);


    @Before
    public void setUp() {
        PlanificadorRed.resetear();
    }

    @Test
    public void agregarLocalidadValidaRetornaTrue() {

        boolean resultado =
                PlanificadorRed.agregarLocalidad(
                        "Buenos Aires",
                        "Buenos Aires",
                        -34.6037,
                        -58.3816);

        assertTrue(resultado);
        assertEquals(
                1,
                PlanificadorRed.getLocalidades().size());
    }

    @Test
    public void agregarLocalidadDuplicadaRetornaFalse() {

        PlanificadorRed.agregarLocalidad(
                "Buenos Aires",
                "Buenos Aires",
                -34.6037,
                -58.3816);

        boolean resultado =
                PlanificadorRed.agregarLocalidad(
                        "Buenos Aires",
                        "Buenos Aires",
                        -34.6037,
                        -58.3816);

        assertTrue(!resultado);

        assertEquals(
                1,
                PlanificadorRed.getLocalidades().size());
    }

    @Test
    public void agregarLocalidadMismoNombreDistintaProvinciaEsValida() {

        boolean resultado1 =
                PlanificadorRed.agregarLocalidad(
                        "San Martin",
                        "Buenos Aires",
                        -34.57,
                        -58.53);

        boolean resultado2 =
                PlanificadorRed.agregarLocalidad(
                        "San Martin",
                        "Mendoza",
                        -33.08,
                        -68.47);

        assertTrue(resultado1);
        assertTrue(resultado2);

        assertEquals(
                2,
                PlanificadorRed.getLocalidades().size());
    }

    @Test
    public void empezarPlanificacionSinLocalidadesRetornaFalse() {

        boolean resultado =
                PlanificadorRed.empezarPlanificacion(
                        10.0,
                        50.0,
                        1.5);

        assertFalse(resultado);
    }

    @Test
    public void empezarPlanificacionConLocalidadesRetornaTrue() {

        PlanificadorRed.agregarLocalidad(
                "Buenos Aires",
                "Buenos Aires",
                -34.6037,
                -58.3816);

        boolean resultado =
                PlanificadorRed.empezarPlanificacion(
                        10.0,
                        50.0,
                        1.5);

        assertTrue(resultado);
    }

    @Test
    public void limpiarLocalidadesDejaLaListaVacia() {

        PlanificadorRed.agregarLocalidad(
                "Buenos Aires",
                "Buenos Aires",
                -34.6037,
                -58.3816);

        PlanificadorRed.agregarLocalidad(
                "Cordoba",
                "Cordoba",
                -31.4201,
                -64.1888);

        PlanificadorRed.limpiarLocalidades();

        assertEquals(
                0,
                PlanificadorRed.getLocalidades().size());
    }

    @Test
    public void resetearEliminaLocalidades() {

        PlanificadorRed.agregarLocalidad(
                "Buenos Aires",
                "Buenos Aires",
                -34.6037,
                -58.3816);

        PlanificadorRed.resetear();

        assertEquals(
                0,
                PlanificadorRed.getLocalidades().size());
    }
    
    @Test
    public void testGrafoCompletoGeneraTodasLasConexiones() {
        Localidad loc1 = new Localidad("A", "Prov1", -34.0, -58.0);
        Localidad loc2 = new Localidad("B", "Prov1", -34.1, -58.1);
        Localidad loc3 = new Localidad("C", "Prov1", -34.2, -58.2);
        
        List<Localidad> lista = Arrays.asList(loc1, loc2, loc3);
        
        Grafo<Localidad> grafo = PlanificadorRed.construirGrafoCompletoDeLocalidades(lista);
        
        // 3 localidades deben generar exactamente 3 aristas en un grafo completo
        // Además, esto prueba implícitamente que la validación n*(n-1)/2
        assertEquals(3, grafo.getAristas().size());
    }
    

    @Test(expected = IllegalArgumentException.class)
    public void empezarPlanificacion_lanzaExcepcionConCostoKmCero() {
        PlanificadorRed.empezarPlanificacion(0, 500, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void empezarPlanificacion_lanzaExcepcionConCostoKmNegativo() {
        PlanificadorRed.empezarPlanificacion(-1, 500, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void empezarPlanificacion_lanzaExcepcionConCostoFijoCero() {
        PlanificadorRed.empezarPlanificacion(100, 0, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void empezarPlanificacion_lanzaExcepcionConCostoFijoNegativo() {
        PlanificadorRed.empezarPlanificacion(100, -50, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void empezarPlanificacion_lanzaExcepcionConPorcentajeCero() {
        PlanificadorRed.empezarPlanificacion(100, 500, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void empezarPlanificacion_lanzaExcepcionConPorcentajeNegativo() {
        PlanificadorRed.empezarPlanificacion(100, 500, -5);
    }
    
    @Test
    public void construirGrafo_costoIntraprovincialSinRecargo() {
        PlanificadorRed.empezarPlanificacion(100, 500, 20);
        List<Localidad> locs = Arrays.asList(BUENOS_AIRES, LA_PLATA);

        Grafo<Localidad> grafo = PlanificadorRed.construirGrafoCompletoDeLocalidades(locs);
        double costo = grafo.getAristas().get(0).getCosto();

        double distanciaEsperada = BUENOS_AIRES.calcularDistancia(LA_PLATA);
        double costoEsperado = distanciaEsperada * 100;
        
        assertEquals(costoEsperado, costo, 0.01);

    }

    @Test
    public void construirGrafo_costoInterprovincialConCargoFijo() {
        PlanificadorRed.empezarPlanificacion(100, 500, 20);
        List<Localidad> locs = Arrays.asList(BUENOS_AIRES, ROSARIO);

        Grafo<Localidad> grafo = PlanificadorRed.construirGrafoCompletoDeLocalidades(locs);
        double costo = grafo.getAristas().get(0).getCosto();

        double distancia = BUENOS_AIRES.calcularDistancia(ROSARIO);
        double costoBase = distancia * 100;
        if (distancia > 300) costoBase += costoBase * 0.20;
        double costoEsperado = costoBase + 500;

        assertEquals(costoEsperado, costo, 0.01);
    }

    @Test
    public void construirGrafo_recargoAplicaCuandoDistanciaSuperaUmbral() {
        PlanificadorRed.empezarPlanificacion(100, 500, 20);
        List<Localidad> locs = Arrays.asList(BUENOS_AIRES, CORDOBA);

        Grafo<Localidad> grafo = PlanificadorRed.construirGrafoCompletoDeLocalidades(locs);
        double costoReal = grafo.getAristas().get(0).getCosto();

        double distancia = BUENOS_AIRES.calcularDistancia(CORDOBA);
        double costoBase = distancia * 100;
        costoBase += costoBase * 0.20;
        double costoEsperado = costoBase + 500;

        assertEquals(costoEsperado, costoReal, 0.01);
    }

    @Test
    public void calcularAGM_tieneNmenosUnaAristas() {
        PlanificadorRed.empezarPlanificacion(100, 500, 20);
        PlanificadorRed.reemplazarLocalidades(Arrays.asList(BUENOS_AIRES, CORDOBA, LA_PLATA, ROSARIO));

        Grafo<Localidad> agm = PlanificadorRed.calcularAGM();

        int n = PlanificadorRed.getLocalidades().size();
        assertEquals(agm.getAristas(), n-1);
    }

    @Test
    public void calcularAGM_conectaTodosLosNodos() {
        PlanificadorRed.empezarPlanificacion(100, 500, 20);
        PlanificadorRed.reemplazarLocalidades(Arrays.asList(BUENOS_AIRES, CORDOBA, LA_PLATA, ROSARIO));

        Grafo<Localidad> agm = PlanificadorRed.calcularAGM();

        List<String> nombresEsperados = Arrays.asList("Buenos Aires", "Córdoba", "La Plata", "Rosario");
        agm.getAristas().forEach(a -> {
        	assertTrue(nombresEsperados.contains(a.getOrigen().getNombre()));
        	assertTrue(nombresEsperados.contains(a.getDestino().getNombre()));
        });
    }

    @Test
    public void calcularAGM_costoTotalMenorOIgualQueGrafoCompleto() {
        PlanificadorRed.empezarPlanificacion(100, 500, 20);
        PlanificadorRed.reemplazarLocalidades(Arrays.asList(BUENOS_AIRES, CORDOBA, LA_PLATA, ROSARIO));

        Grafo<Localidad> grafoCompleto = PlanificadorRed.construirGrafoCompletoDeLocalidades(
                PlanificadorRed.getLocalidades());
        Grafo<Localidad> agm = PlanificadorRed.calcularAGM();

        double costoAGM = agm.getAristas().stream().mapToDouble(a -> a.getCosto()).sum();
        double costoCompleto = grafoCompleto.getAristas().stream().mapToDouble(a -> a.getCosto()).sum();
        
        assertTrue(costoAGM <= costoCompleto);
    }
    
    @Test
    public void generarConexionesVisuales_cantidadIgualAAristas() {
        PlanificadorRed.empezarPlanificacion(100, 500, 20);
        Grafo<Localidad> grafo = PlanificadorRed.construirGrafoCompletoDeLocalidades(
                Arrays.asList(BUENOS_AIRES, CORDOBA, LA_PLATA));

        List<ConexionVisual> conexiones = PlanificadorRed.generarConexionesVisuales(grafo);
        int cantidadDeConexiones = conexiones.size();
        int cantidadAristas = grafo.getAristas().size();
        
        assertTrue(cantidadDeConexiones == cantidadAristas);
    }

    @Test
    public void generarConexionesVisuales_coordenadasCoincidenConArista() {
        PlanificadorRed.empezarPlanificacion(100, 500, 20);
        Grafo<Localidad> grafo = PlanificadorRed.construirGrafoCompletoDeLocalidades(
                Arrays.asList(BUENOS_AIRES, LA_PLATA));

        ConexionVisual cv = PlanificadorRed.generarConexionesVisuales(grafo).get(0);
        
        assertEquals(BUENOS_AIRES.getLatitud(), cv.getLat1(), 0.1);
        assertEquals(BUENOS_AIRES.getLongitud(), cv.getLon1(), 0.1);
        assertEquals(LA_PLATA.getLatitud(), cv.getLat2(), 0.1);
        assertEquals(LA_PLATA.getLongitud(), cv.getLon2(), 0.1);

    }

    
}
