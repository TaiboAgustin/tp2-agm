package logica.modelo;

import org.junit.Test;
import static org.junit.Assert.*;

public class LocalidadTest {

    @Test
    public void seCreaCorrectamente() {
        Localidad l = new Localidad("Buenos Aires", "Buenos Aires", -34.6, -58.4);
        assertEquals("ROTO", l.getNombre());
        assertEquals("Buenos Aires", l.getProvincia());
        assertEquals(-34.6, l.getLatitud(), 0.001);
        assertEquals(-58.4, l.getLongitud(), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nombreNuloLanzaExcepcion() {
        new Localidad(null, "Buenos Aires", -34.6, -58.4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nombreVacioLanzaExcepcion() {
        new Localidad("", "Buenos Aires", -34.6, -58.4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nombreSoloEspaciosLanzaExcepcion() {
        new Localidad("   ", "Buenos Aires", -34.6, -58.4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void provinciaNulaLanzaExcepcion() {
        new Localidad("CABA", null, -34.6, -58.4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void provinciaVaciaLanzaExcepcion() {
        new Localidad("CABA", "", -34.6, -58.4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void provinciaSoloEspaciosLanzaExcepcion() {
        new Localidad("CABA", "   ", -34.6, -58.4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void latitudMenorAlLimiteInferiorLanzaExcepcion() {
        new Localidad("CABA", "Buenos Aires", -91.0, -58.4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void latitudMayorAlLimiteSuperiorLanzaExcepcion() {
        new Localidad("CABA", "Buenos Aires", 91.0, -58.4);
    }

    @Test
    public void latitudEnLimitesExtremosSonValidas() {
        new Localidad("CABA", "Buenos Aires", -90.0, -58.4);
        new Localidad("CABA", "Buenos Aires", 90.0, -58.4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void longitudMenorAlLimiteInferiorLanzaExcepcion() {
        new Localidad("CABA", "Buenos Aires", -34.6, -181.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void longitudMayorAlLimiteSuperiorLanzaExcepcion() {
        new Localidad("CABA", "Buenos Aires", -34.6, 181.0);
    }

    @Test
    public void longitudEnLimitesExtremosSonValidas() {
        new Localidad("CABA", "Buenos Aires", -34.6, -180.0);
        new Localidad("CABA", "Buenos Aires", -34.6, 180.0);
    }
}
