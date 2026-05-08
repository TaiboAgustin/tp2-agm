package DTO.PERSISTENCIA;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import DTO.PersistenciaENJson;
import logica.modelo.Localidad;

public class PersistenciaENJsonTest {
	@Test
    public void testGuardarYCargarLocalidades() {

        String archivo = "localidades.json";

        // 1. Crear datos
        List<Localidad> lista = new ArrayList<>();
        lista.add(new Localidad("Buenos Aires", "Buenos Aires", -34.6, -58.4));
        lista.add(new Localidad("Cordoba", "Cordoba", -31.4, -64.2));

        // 2. Guardar
        PersistenciaENJson.guardarLocalidades(lista, archivo);

        // 3. Cargar
        List<Localidad> cargadas = PersistenciaENJson.cargarLocalidades(archivo);

        // 4. Verificaciones
        assertNotNull(cargadas);
        assertEquals(2, cargadas.size());
        assertEquals("Buenos Aires", cargadas.get(0).getNombre());

}
}