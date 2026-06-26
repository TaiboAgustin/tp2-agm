package planificador;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Logica_Planificador.ConexionVisual;
import Logica_Planificador.PlanificadorRed;
import logica.modelo.Arista;
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

    private PlanificadorRed planificador;

    @Before
    public void setUp() {
        planificador = new PlanificadorRed();
    }

    @Test
    public void agregarLocalidadValidaRetornaTrue() {
        boolean resultado = planificador.agregarLocalidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        assertTrue(resultado);
        assertEquals(1, planificador.getLocalidades().size());
    }

    @Test
    public void agregarLocalidadDuplicadaRetornaFalse() {
        planificador.agregarLocalidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        boolean resultado = planificador.agregarLocalidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        assertFalse(resultado);
        assertEquals(1, planificador.getLocalidades().size());
    }

    @Test
    public void agregarLocalidadMismoNombreDistintaProvinciaEsValida() {
        boolean r1 = planificador.agregarLocalidad("San Martin", "Buenos Aires", -34.57, -58.53);
        boolean r2 = planificador.agregarLocalidad("San Martin", "Mendoza",      -33.08, -68.47);
        assertTrue(r1);
        assertTrue(r2);
        assertEquals(2, planificador.getLocalidades().size());
    }

    @Test
    public void empezarPlanificacionSinLocalidadesRetornaFalse() {
        assertFalse(planificador.empezarPlanificacion(10.0, 50.0, 1.5));
    }

    @Test
    public void empezarPlanificacionConLocalidadesRetornaTrue() {
        planificador.agregarLocalidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        assertTrue(planificador.empezarPlanificacion(10.0, 50.0, 1.5));
    }

    @Test
    public void limpiarLocalidadesDejaLaListaVacia() {
        planificador.agregarLocalidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        planificador.agregarLocalidad("Cordoba", "Cordoba", -31.4201, -64.1888);
        planificador.limpiarLocalidades();
        assertEquals(0, planificador.getLocalidades().size());
    }

    @Test
    public void resetearEliminaLocalidades() {
        planificador.agregarLocalidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        planificador.resetear();
        assertEquals(0, planificador.getLocalidades().size());
    }

    @Test
    public void grafoCompletoGeneraTodasLasConexiones() {
        planificador.empezarPlanificacion(100, 500, 20);
        List<Localidad> lista = Arrays.asList(
            new Localidad("A", "Prov1", -34.0, -58.0),
            new Localidad("B", "Prov1", -34.1, -58.1),
            new Localidad("C", "Prov1", -34.2, -58.2)
        );
        Grafo<Localidad> grafo = planificador.construirGrafoCompletoDeLocalidades(lista);
        assertEquals(3, grafo.getAristas().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void empezarPlanificacion_lanzaExcepcionConCostoKmCero() {
        planificador.empezarPlanificacion(0, 500, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void empezarPlanificacion_lanzaExcepcionConCostoKmNegativo() {
        planificador.empezarPlanificacion(-1, 500, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void empezarPlanificacion_lanzaExcepcionConCostoFijoCero() {
        planificador.empezarPlanificacion(100, 0, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void empezarPlanificacion_lanzaExcepcionConCostoFijoNegativo() {
        planificador.empezarPlanificacion(100, -50, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void empezarPlanificacion_lanzaExcepcionConPorcentajeCero() {
        planificador.empezarPlanificacion(100, 500, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void empezarPlanificacion_lanzaExcepcionConPorcentajeNegativo() {
        planificador.empezarPlanificacion(100, 500, -5);
    }

    @Test
    public void construirGrafo_costoIntraprovincialSinRecargo() {
        planificador.empezarPlanificacion(100, 500, 20);
        Grafo<Localidad> grafo = planificador.construirGrafoCompletoDeLocalidades(
                Arrays.asList(BUENOS_AIRES, LA_PLATA));
        double costo = grafo.getAristas().get(0).getPeso();
        double distanciaEsperada = BUENOS_AIRES.calcularDistancia(LA_PLATA);
        assertEquals(distanciaEsperada * 100, costo, 0.01);
    }

    @Test
    public void construirGrafo_costoInterprovincialConCargoFijo() {
        planificador.empezarPlanificacion(100, 500, 20);
        Grafo<Localidad> grafo = planificador.construirGrafoCompletoDeLocalidades(
                Arrays.asList(BUENOS_AIRES, ROSARIO));
        double costo = grafo.getAristas().get(0).getPeso();
        double distancia = BUENOS_AIRES.calcularDistancia(ROSARIO);
        double costoBase = distancia * 100;
        if (distancia > 300) costoBase += costoBase * 0.20;
        assertEquals(costoBase + 500, costo, 0.01);
    }

    @Test
    public void construirGrafo_recargoAplicaCuandoDistanciaSuperaUmbral() {
        planificador.empezarPlanificacion(100, 500, 20);
        Grafo<Localidad> grafo = planificador.construirGrafoCompletoDeLocalidades(
                Arrays.asList(BUENOS_AIRES, CORDOBA));
        double costoReal = grafo.getAristas().get(0).getPeso();
        double distancia = BUENOS_AIRES.calcularDistancia(CORDOBA);
        double costoBase = distancia * 100;
        costoBase += costoBase * 0.20;
        assertEquals(costoBase + 500, costoReal, 0.01);
    }

    @Test
    public void calcularAGM_tieneNmenosUnaAristas() {
        planificador.empezarPlanificacion(100, 500, 20);
        planificador.reemplazarLocalidades(Arrays.asList(BUENOS_AIRES, CORDOBA, LA_PLATA, ROSARIO));
        Grafo<Localidad> agm = planificador.calcularAGM();
        int n = planificador.getLocalidades().size();
        assertEquals(n - 1, agm.getAristas().size());
    }

    @Test
    public void calcularAGM_conectaTodosLosNodos() {
        planificador.empezarPlanificacion(100, 500, 20);
        planificador.reemplazarLocalidades(Arrays.asList(BUENOS_AIRES, CORDOBA, LA_PLATA, ROSARIO));
        Grafo<Localidad> agm = planificador.calcularAGM();
        List<String> nombresEsperados = Arrays.asList("Buenos Aires", "Córdoba", "La Plata", "Rosario");
        agm.getAristas().forEach(a -> {
            assertTrue(nombresEsperados.contains(a.getVertice1().getNombre()));
            assertTrue(nombresEsperados.contains(a.getVertice2().getNombre()));
        });
    }

    @Test
    public void calcularAGM_costoTotalMenorOIgualQueGrafoCompleto() {
        planificador.empezarPlanificacion(100, 500, 20);
        planificador.reemplazarLocalidades(Arrays.asList(BUENOS_AIRES, CORDOBA, LA_PLATA, ROSARIO));
        Grafo<Localidad> grafoCompleto = planificador.construirGrafoCompletoDeLocalidades(
                planificador.getLocalidades());
        Grafo<Localidad> agm = planificador.calcularAGM();
        double costoAGM = agm.getAristas().stream().mapToDouble(Arista::getPeso).sum();
        double costoCompleto = grafoCompleto.getAristas().stream().mapToDouble(a -> a.getPeso()).sum();
        assertTrue(costoAGM <= costoCompleto);
    }

    @Test
    public void generarConexionesVisuales_cantidadIgualAAristas() {
        planificador.empezarPlanificacion(100, 500, 20);
        Grafo<Localidad> grafo = planificador.construirGrafoCompletoDeLocalidades(
                Arrays.asList(BUENOS_AIRES, CORDOBA, LA_PLATA));
        List<ConexionVisual> conexiones = planificador.generarConexionesVisuales(grafo);
        assertEquals(grafo.getAristas().size(), conexiones.size());
    }

    @Test
    public void generarConexionesVisuales_coordenadasCoincidenConArista() {
        planificador.empezarPlanificacion(100, 500, 20);
        Grafo<Localidad> grafo = planificador.construirGrafoCompletoDeLocalidades(
                Arrays.asList(BUENOS_AIRES, LA_PLATA));
        ConexionVisual cv = planificador.generarConexionesVisuales(grafo).get(0);
        assertEquals(BUENOS_AIRES.getLatitud(),  cv.getLat1(), 0.1);
        assertEquals(BUENOS_AIRES.getLongitud(), cv.getLon1(), 0.1);
        assertEquals(LA_PLATA.getLatitud(),      cv.getLat2(), 0.1);
        assertEquals(LA_PLATA.getLongitud(),     cv.getLon2(), 0.1);
    }
}
