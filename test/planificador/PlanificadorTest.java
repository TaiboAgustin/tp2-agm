package planificador;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Logica_Planificador.PlanificadorRed;
import logica.modelo.Localidad;

public class PlanificadorTest {

    @Before
    public void resetearLista() {

        PlanificadorRed
                .getLocalidades()
                .clear();
    }

    @Test
    public void testAgregarLocalidad() {

        PlanificadorRed.agregarLocalidad(
                "Buenos Aires",
                "Buenos Aires",
                -34.6,
                -58.4
        );

        List<Localidad> lista =
                PlanificadorRed
                        .getLocalidades();

        assertEquals(
                1,
                lista.size()
        );

        assertEquals(
                "Buenos Aires",
                lista.get(0).getNombre()
        );
    }

    @Test
    public void testAgregarMuchasLocalidades() {

        PlanificadorRed.agregarLocalidad(
                "A",
                "A",
                1,
                1
        );

        PlanificadorRed.agregarLocalidad(
                "B",
                "B",
                2,
                2
        );

        PlanificadorRed.agregarLocalidad(
                "C",
                "C",
                3,
                3
        );

        assertEquals(
                3,
                PlanificadorRed
                        .getLocalidades()
                        .size()
        );
    }

    @Test
    public void testGetLocalidadesNoNulo() {

        assertNotNull(
                PlanificadorRed
                        .getLocalidades()
        );
    }

    @Test
    public void testListaComienzaVacia() {

        assertTrue(
                PlanificadorRed
                        .getLocalidades()
                        .isEmpty()
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarNombreVacio() {

        PlanificadorRed.agregarLocalidad(
                "",
                "Buenos Aires",
                -34,
                -57
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarProvinciaVacia() {

        PlanificadorRed.agregarLocalidad(
                "La Plata",
                "",
                -34,
                -57
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarLatitudInvalida() {

        PlanificadorRed.agregarLocalidad(
                "Test",
                "Test",
                200,
                -50
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarLongitudInvalida() {

        PlanificadorRed.agregarLocalidad(
                "Test",
                "Test",
                -50,
                500
        );
    }

    @Test
    public void testGuardarYRecuperarDatos() {

        PlanificadorRed.agregarLocalidad(
                "Cordoba",
                "Cordoba",
                -31.4,
                -64.2
        );

        PlanificadorRed planificador =
                new PlanificadorRed();

        planificador.cargarDatos();

        List<Localidad> lista =
                PlanificadorRed
                        .getLocalidades();

        assertFalse(
                lista.isEmpty()
        );

        assertEquals(
                "Cordoba",
                lista.get(0).getNombre()
        );
    }

    @Test
    public void testAgregarLocalidadesMantieneDatos() {

        PlanificadorRed.agregarLocalidad(
                "A",
                "A",
                1,
                1
        );

        new PlanificadorRed();

        PlanificadorRed.agregarLocalidad(
                "B",
                "B",
                2,
                2
        );

        assertEquals(
                2,
                PlanificadorRed
                        .getLocalidades()
                        .size()
        );
    }
}