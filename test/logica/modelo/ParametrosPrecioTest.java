package logica.modelo;

import org.junit.Test;
import static org.junit.Assert.*;

public class ParametrosPrecioTest {

    private static final double COSTO_KM_VALIDO             = 10.0d;
    private static final double TARIFA_INTERPROV_VALIDA      = 50.0d;
    private static final double INCREMENTO_VALIDO            = 1.5d;

    @Test
    public void seCreaCorrectamenteConValoresValidos() {
        ParametrosPrecio p = new ParametrosPrecio(COSTO_KM_VALIDO, TARIFA_INTERPROV_VALIDA, INCREMENTO_VALIDO);
        assertEquals(COSTO_KM_VALIDO,        p.getCostoPorKm(),                    0.001d);
        assertEquals(TARIFA_INTERPROV_VALIDA, p.getCostoFijoInterprovincial(),         0.001d);
        assertEquals(INCREMENTO_VALIDO,       p.getPorcentajeAumento(), 0.001f);
    }

    @Test
    public void seCreaCorrectamenteConValoresMuyPequenosPositivos() {
        ParametrosPrecio p = new ParametrosPrecio(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
        assertEquals(Float.MIN_VALUE, p.getCostoPorKm(),                    0.0d);
        assertEquals(Float.MIN_VALUE, p.getCostoFijoInterprovincial(),         0.0d);
        assertEquals(Float.MIN_VALUE, p.getPorcentajeAumento(), 0.0f);
    }

    @Test
    public void seCreaCorrectamenteConValoresMuyGrandes() {
        ParametrosPrecio p = new ParametrosPrecio(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
        assertEquals(Float.MAX_VALUE, p.getCostoPorKm(),                    0.0d);
        assertEquals(Float.MAX_VALUE, p.getCostoFijoInterprovincial(),         0.0d);
        assertEquals(Float.MAX_VALUE, p.getPorcentajeAumento(), 0.0d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void costoPorKmCeroLanzaExcepcion() {
        new ParametrosPrecio(0.0d, TARIFA_INTERPROV_VALIDA, INCREMENTO_VALIDO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void costoPorKmNegativoLanzaExcepcion() {
        new ParametrosPrecio(-1.0d, TARIFA_INTERPROV_VALIDA, INCREMENTO_VALIDO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tarifaInterprovincialCeroLanzaExcepcion() {
        new ParametrosPrecio(COSTO_KM_VALIDO, 0.0d, INCREMENTO_VALIDO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tarifaInterprovincialNegativaLanzaExcepcion() {
        new ParametrosPrecio(COSTO_KM_VALIDO, -5.0d, INCREMENTO_VALIDO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void incrementoCostoDistanciasLargasCeroLanzaExcepcion() {
        new ParametrosPrecio(COSTO_KM_VALIDO, TARIFA_INTERPROV_VALIDA, 0.0d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void incrementoCostoDistanciasLargasNegativoLanzaExcepcion() {
        new ParametrosPrecio(COSTO_KM_VALIDO, TARIFA_INTERPROV_VALIDA, -0.5d);
    }

    @Test
    public void cadaGetterRetornaElValorDeSuPropioParametro() {
    	double costo      = 12.5d;
    	double tarifa     = 75.0d;
    	double incremento = 2.0d;
        ParametrosPrecio p = new ParametrosPrecio(costo, tarifa, incremento);

        assertNotEquals(p.getCostoPorKm(),                    tarifa,     0.001d);
        assertNotEquals(p.getCostoFijoInterprovincial(),         costo,      0.001d);
        assertNotEquals(p.getPorcentajeAumento(), costo,    0.001d);
    }
}