package DTO.PERSISTENCIA;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import DTO.PersistenciaENJson;
import logica.modelo.Localidad;

public class PersistenciaENJsonTest {
	@Test
    public void testGuardarYCargarLocalidades() {

        String archivo = "localidades_test.json";

        List<Localidad> lista = new ArrayList<>();

        lista.add(new Localidad(
                "Buenos Aires",
                "Buenos Aires",
                -34.6,
                -58.4
        ));

        lista.add(new Localidad(
                "Cordoba",
                "Cordoba",
                -31.4,
                -64.2
        ));

        PersistenciaENJson.guardarLocalidades(
                lista,
                archivo
        );

        List<Localidad> cargadas =
                PersistenciaENJson.cargarLocalidades(
                        archivo
                );

        assertNotNull(cargadas);

        assertEquals(2, cargadas.size());

        assertEquals(
                "Buenos Aires",
                cargadas.get(0).getNombre()
        );
    }

    @Test
    public void testGuardarListaVacia() {

        String archivo = "localidades_vacias.json";

        List<Localidad> lista =
                new ArrayList<>();

        PersistenciaENJson.guardarLocalidades(
                lista,
                archivo
        );

        List<Localidad> cargadas =
                PersistenciaENJson.cargarLocalidades(
                        archivo
                );

        assertNotNull(cargadas);

        assertTrue(cargadas.isEmpty());
    }

    @Test
    public void testCargarArchivoInexistente() {

        List<Localidad> cargadas =
                PersistenciaENJson.cargarLocalidades(
                        "archivo_que_no_existe.json"
                );

        assertNotNull(cargadas);
    }

    @Test
    public void testGuardarUnaLocalidad() {

        String archivo = "una_localidad.json";

        List<Localidad> lista =
                new ArrayList<>();

        lista.add(new Localidad(
                "Mendoza",
                "Mendoza",
                -32.8,
                -68.8
        ));

        PersistenciaENJson.guardarLocalidades(
                lista,
                archivo
        );

        List<Localidad> cargadas =
                PersistenciaENJson.cargarLocalidades(
                        archivo
                );

        assertEquals(1, cargadas.size());

        assertEquals(
                "Mendoza",
                cargadas.get(0).getNombre()
        );
    }

    @Test
    public void testDatosCorrectosDespuesDeCargar() {

        String archivo = "datos_correctos.json";

        List<Localidad> lista =
                new ArrayList<>();

        lista.add(new Localidad(
                "Salta",
                "Salta",
                -24.7,
                -65.4
        ));

        PersistenciaENJson.guardarLocalidades(
                lista,
                archivo
        );

        List<Localidad> cargadas =
                PersistenciaENJson.cargarLocalidades(
                        archivo
                );

        Localidad loc = cargadas.get(0);

        assertEquals("Salta", loc.getNombre());

        assertEquals(
                "Salta",
                loc.getProvincia()
        );

        assertEquals(
                -24.7,
                loc.getLatitud(),
                0.001
        );

        assertEquals(
                -65.4,
                loc.getLongitud(),
                0.001
        );
    }

    @Test
    public void testGuardarMuchasLocalidades() {

        String archivo = "muchas_localidades.json";

        List<Localidad> lista =
                new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            lista.add(
                    new Localidad(
                            "Ciudad" + i,
                            "Provincia" + i,
                            i,
                            i
                    )
            );
        }

        PersistenciaENJson.guardarLocalidades(
                lista,
                archivo
        );

        List<Localidad> cargadas =
                PersistenciaENJson.cargarLocalidades(
                        archivo
                );

        assertEquals(10, cargadas.size());
    }
    @Test
    public void testSobrescribirArchivo() {

        String archivo = "test.json";

        List<Localidad> lista1 =
                new ArrayList<>();

        lista1.add(
                new Localidad(
                        "A","B",1,2
                )
        );

        PersistenciaENJson
                .guardarLocalidades(
                        lista1,
                        archivo
                );

        List<Localidad> lista2 =
                new ArrayList<>();

        lista2.add(
                new Localidad(
                        "C","D",3,4
                )
        );

        PersistenciaENJson
                .guardarLocalidades(
                        lista2,
                        archivo
                );

        List<Localidad> cargadas =
                PersistenciaENJson
                        .cargarLocalidades(
                                archivo
                        );

        assertEquals(
                "C",
                cargadas.get(0).getNombre()
        );
    }
    @Test
    public void testProvinciaDespuesDeCargar() {

        String archivo = "provincia.json";

        List<Localidad> lista =
                new ArrayList<>();

        lista.add(
                new Localidad(
                        "Neuquen",
                        "Neuquen",
                        -38.9,
                        -68.0
                )
        );

        PersistenciaENJson
                .guardarLocalidades(
                        lista,
                        archivo
                );

        List<Localidad> cargadas =
                PersistenciaENJson
                        .cargarLocalidades(
                                archivo
                        );

        assertEquals(
                "Neuquen",
                cargadas.get(0).getProvincia()
        );
    }
    @Test
    public void testCoordenadasDespuesDeCargar() {

        String archivo = "coords.json";

        List<Localidad> lista =
                new ArrayList<>();

        lista.add(
                new Localidad(
                        "Ushuaia",
                        "Tierra del Fuego",
                        -54.8,
                        -68.3
                )
        );

        PersistenciaENJson
                .guardarLocalidades(
                        lista,
                        archivo
                );

        List<Localidad> cargadas =
                PersistenciaENJson
                        .cargarLocalidades(
                                archivo
                        );

        assertEquals(
                -54.8,
                cargadas.get(0).getLatitud(),
                0.001
        );

        assertEquals(
                -68.3,
                cargadas.get(0).getLongitud(),
                0.001
        );
    }
    @Test
    public void testGuardarMismoContenidoDosVeces() {

        String archivo = "repetido.json";

        List<Localidad> lista =
                new ArrayList<>();

        lista.add(
                new Localidad(
                        "A","B",1,2
                )
        );

        PersistenciaENJson
                .guardarLocalidades(
                        lista,
                        archivo
                );

        PersistenciaENJson
                .guardarLocalidades(
                        lista,
                        archivo
                );

        List<Localidad> cargadas =
                PersistenciaENJson
                        .cargarLocalidades(
                                archivo
                        );

        assertEquals(1, cargadas.size());
    }
    @Test
    public void testListaVaciaNoEsNull() {

        String archivo = "vacio.json";

        PersistenciaENJson
                .guardarLocalidades(
                        new ArrayList<>(),
                        archivo
                );

        List<Localidad> cargadas =
                PersistenciaENJson
                        .cargarLocalidades(
                                archivo
                        );

        assertNotNull(cargadas);
    }
}