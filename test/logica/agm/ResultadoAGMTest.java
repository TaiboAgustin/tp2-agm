package logica.agm;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import logica.modelo.Arista;
import logica.modelo.Localidad;

public class ResultadoAGMTest {

    private final Localidad buenosAires = new Localidad("Buenos Aires", "Buenos Aires", -34.6, -58.4);
    private final Localidad rosario = new Localidad("Rosario", "Santa Fe", -32.9, -60.7);
    private final Localidad cordoba = new Localidad("Córdoba", "Córdoba", -31.4, -64.2);

    @Test
    public void costoTotalEsLaSumaDeLasAristas() {
        Arista<Localidad> a1 = new Arista<>(buenosAires, rosario, 1000.0);
        Arista<Localidad> a2 = new Arista<>(rosario, cordoba, 500.0);
        ResultadoAGM<Localidad> resultado = new ResultadoAGM<>(Arrays.asList(a1, a2));
        assertEquals(1500.0, resultado.getCostoTotal(), 0.001);
    }

    @Test
    public void lasConexionesDelArbolSonNMenosUno() {
        Arista<Localidad> a1 = new Arista<>(buenosAires, rosario, 1000.0);
        Arista<Localidad> a2 = new Arista<>(rosario, cordoba, 500.0);
        ResultadoAGM<Localidad> resultado = new ResultadoAGM<>(Arrays.asList(a1, a2));
        assertEquals(2, resultado.getConexiones().size());
    }
}
