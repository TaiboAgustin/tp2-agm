package logica.modelo;

import org.junit.Test;
import static org.junit.Assert.*;
import logica.modelo.Localidad;

public class LocalidadTest {

    @Test
    public void seCreaCorrectamente() {
        Localidad l = new Localidad("Buenos Aires", "Buenos Aires", -34.6, -58.4);
        assertEquals("Buenos Aires", l.getNombre());
        assertEquals("Buenos Aires", l.getProvincia());
        assertEquals(-34.6, l.getLatitud(), 0.001);
        assertEquals(-58.4, l.getLongitud(), 0.001);
    }
    
    // En los test de extremos la localidad debería crearse sin problemas. Si hay excepción, el test está fallando.
    @Test
    void creaLocalidadCorrectamenteEnExtremoSuperiorLatitud() {
    	new Localidad("Polo Norte", "Ártico", 90.0, 0.0);
    }
    
    @Test
    void creaLocalidadCorrectamenteEnExtremoInferiorLatitud() {
        new Localidad("Polo Sur", "Antártida", -90.0, 0.0);
    }
    
    @Test
    void creaLocalidadCorrectamenteEnExtremoSuperiorLongitud() {
        new Localidad("Meridiano Este", "Pacífico", 0.0, 180.0);
    }
    
    @Test
    void creaLocalidadCorrectamenteEnExtremoinferiorLongitud() {
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
    public void provinciaNulaLanzaExcepcion() {
        new Localidad("CABA", null, -34.6, -58.4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void provinciaVaciaLanzaExcepcion() {
        new Localidad("CABA", "", -34.6, -58.4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void latitudMenorAlLimiteInferiorLanzaExcepcion() {
        new Localidad("CABA", "Buenos Aires", -91.0, -58.4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void latitudMayorAlLimiteSuperiorLanzaExcepcion() {
        new Localidad("CABA", "Buenos Aires", 91.0, -58.4);
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
    void distanciaMismasCoordenadasEsCero() {
        Localidad loc1 = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        Localidad loc2 = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);

        assertEquals(0.0, loc1.calcularDistancia(loc2), 1e-9);
    }

    @Test
    void distanciaEsSimetrica() {
        Localidad buenosAires = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        Localidad cordoba     = new Localidad("Córdoba", "Córdoba", -31.4201, -64.1888);

        double distanciaIda    = buenosAires.calcularDistancia(cordoba);
        double distanciaVuelta = cordoba.calcularDistancia(buenosAires);

        assertEquals(distanciaIda, distanciaVuelta, 1e-6);
    }

    @Test
    void distanciaBairesCordobaCorrecta() {
        Localidad buenosAires = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        Localidad cordoba     = new Localidad("Córdoba", "Córdoba", -31.4201, -64.1888);

        double distancia = buenosAires.calcularDistancia(cordoba);

        assertEquals(646.0, distancia, 5.0);
    }

    @Test
    void distanciaEsPositivo() {
        Localidad buenosAires = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
        Localidad mendoza     = new Localidad("Mendoza", "Mendoza", -32.8908, -68.8272);

        double distancia = buenosAires.calcularDistancia(mendoza);

        assertTrue(distancia > 0.0);
    }

}
