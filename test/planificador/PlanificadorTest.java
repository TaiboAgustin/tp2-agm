package planificador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import Logica_Planificador.PlanificadorRed;
import logica.modelo.Localidad;

public class PlanificadorTest {
	private PlanificadorRed planificador;

    @Before
    public void setUp() {
        PlanificadorRed Control = new PlanificadorRed();
    }

    @Test
    public void testAgregarLocalidad() {

        PlanificadorRed.agregarLocalidad(
                "La Plata",
                "Buenos Aires",
                -34.9214,
                -57.9544
        );

        assertEquals(1, PlanificadorRed.getLocalidades().size());
    }

    @Test
    public void testLocalidadGuardadaCorrectamente() {

        PlanificadorRed.agregarLocalidad(
                "Córdoba",
                "Córdoba",
                -31.4167,
                -64.1833
        );

        Localidad loc = PlanificadorRed.getLocalidades().get(0);

        assertEquals("Córdoba", loc.getNombre());
        assertEquals("Córdoba", loc.getProvincia());

        assertEquals(-31.4167, loc.getLatitud(), 0.001);
        assertEquals(-64.1833, loc.getLongitud(), 0.001);
    }

    @Test
    public void testListaInicialmenteVacia() {

        assertTrue(PlanificadorRed.getLocalidades().isEmpty());
    }

    @Test
    public void testAgregarMultiplesLocalidades() {

        PlanificadorRed.agregarLocalidad(
                "Buenos Aires",
                "Buenos Aires",
                -34.6037,
                -58.3816
        );

        PlanificadorRed.agregarLocalidad(
                "Mendoza",
                "Mendoza",
                -32.8895,
                -68.8458
        );

        assertEquals(2, PlanificadorRed.getLocalidades().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLatitudInvalida() {

        PlanificadorRed.agregarLocalidad(
                "Test",
                "Test",
                200,
                -50
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNombreVacio() {

        PlanificadorRed.agregarLocalidad(
                "",
                "Buenos Aires",
                -34,
                -57
        );
    }
}
