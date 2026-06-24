package planificador;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Logica_Planificador.PlanificadorRed;
import logica.modelo.Localidad;
public class PlanificadorTest {

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
    public void configurarParametrosGuardaLosValoresCorrectamente() {

        PlanificadorRed.configurarParametros(
                10.0,
                50.0,
                1.5);

        assertNotNull(
                PlanificadorRed.getParametros());

        assertEquals(
                10.0,
                PlanificadorRed.getParametros().getCostoPorKm(),
                0.001);

        assertEquals(
                50.0,
                PlanificadorRed.getParametros().getCostoFijoInterprovincial(),
                0.001);

        assertEquals(
                1.5,
                PlanificadorRed.getParametros().getPorcentajeAumento(),
                0.001);
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
    public void resetearEliminaParametrosYLocalidades() {

        PlanificadorRed.agregarLocalidad(
                "Buenos Aires",
                "Buenos Aires",
                -34.6037,
                -58.3816);

        PlanificadorRed.configurarParametros(
                10.0,
                50.0,
                1.5);

        PlanificadorRed.resetear();

        assertEquals(
                0,
                PlanificadorRed.getLocalidades().size());

        assertNull(
                PlanificadorRed.getParametros());
    }
}
