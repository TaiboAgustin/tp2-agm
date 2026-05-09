package logica.modelo;

import org.junit.Test;
import static org.junit.Assert.*;

public class LocalidadTest {

    @Test
    public void seCreaCorrectamente() {
        Localidad l = new Localidad("Buenos Aires", "Buenos Aires", -34.6, -58.4);
        assertEquals("Buenos Aires", l.getNombre());
        assertEquals("Buenos Aires", l.getProvincia());
        assertEquals(-34.6, l.getLatitud(), 0.001);
        assertEquals(-58.4, l.getLongitud(), 0.001);
    }

    @Test
    public void creaLocalidadCorrectamenteEnExtremoSuperiorLatitud() {
        new Localidad("Polo Norte", "Ártico", 90.0, 0.0);
    }

    @Test
    public void creaLocalidadCorrectamenteEnExtremoInferiorLatitud() {
        new Localidad("Polo Sur", "Antártida", -90.0, 0.0);
    }

    @Test
    public void creaLocalidadCorrectamenteEnExtremoSuperiorLongitud() {
        new Localidad("Meridiano Este", "Pacífico", 0.0, 180.0);
    }

    @Test
    public void creaLocalidadCorrectamenteEnExtremoInferiorLongitud() {
        new Localidad("Meridiano Oeste", "Pacífico", 0.0, -180.0);
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

    @Test
    public void distanciaMismasCoordenadasEsCero() {
        Localidad loc1 = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        Localidad loc2 = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        assertEquals(0.0, loc1.calcularDistancia(loc2), 1e-9);
    }

    @Test
    public void distanciaEsSimetrica() {
        Localidad buenosAires = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        Localidad cordoba     = new Localidad("Córdoba", "Córdoba", -31.4201, -64.1888);
        assertEquals(buenosAires.calcularDistancia(cordoba),
                     cordoba.calcularDistancia(buenosAires), 1e-6);
    }

    @Test
    public void distanciaBairesCordobaCorrecta() {
        Localidad buenosAires = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        Localidad cordoba     = new Localidad("Córdoba", "Córdoba", -31.4201, -64.1888);
        assertEquals(646.0, buenosAires.calcularDistancia(cordoba), 5.0);
    }

    @Test
    public void distanciaBairesMendozaCorrecta() {
        Localidad buenosAires = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        Localidad mendoza     = new Localidad("Mendoza", "Mendoza", -32.8908, -68.8272);
        assertEquals(940.0, buenosAires.calcularDistancia(mendoza), 10.0);
    }

    @Test(expected = NullPointerException.class)
    public void distanciaConLocalidadNulaLanzaExcepcion() {
        Localidad buenosAires = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        buenosAires.calcularDistancia(null);
    }

    @Test
    public void dosLocalidadesConMismosDatosSonIguales() {
        Localidad l1 = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        Localidad l2 = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        assertEquals(l1, l2);
    }

    @Test
    public void localidadesConDistintaCoordenadaNoSonIguales() {
        Localidad l1 = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        Localidad l2 = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.0000);
        assertNotEquals(l1, l2);
    }

    @Test
    public void localidadesIgualesTienenMismoHashCode() {
        Localidad l1 = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        Localidad l2 = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        assertEquals(l1.hashCode(), l2.hashCode());
    }

    @Test
    public void localidadNoEsIgualANull() {
        Localidad l = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        assertNotEquals(null, l);
    }

    @Test
    public void localidadEsIgualASiMisma() {
        Localidad l = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        assertEquals(l, l);
    }
}