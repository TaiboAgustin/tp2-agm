package logica.modelo;

import org.junit.Test;
import static org.junit.Assert.*;

public class ParametrosPrecioTest {

    private static final float COSTO_KM_VALIDO             = 10.0f;
    private static final float TARIFA_INTERPROV_VALIDA      = 50.0f;
    private static final float INCREMENTO_VALIDO            = 1.5f;

    @Test
    public void seCreaCorrectamenteConValoresValidos() {
        ParametrosPrecio p = new ParametrosPrecio(COSTO_KM_VALIDO, TARIFA_INTERPROV_VALIDA, INCREMENTO_VALIDO);
        assertEquals(COSTO_KM_VALIDO,        p.getCostoPorKm(),                    0.001f);
        assertEquals(TARIFA_INTERPROV_VALIDA, p.getTarifaInterprovincial(),         0.001f);
        assertEquals(INCREMENTO_VALIDO,       p.getIncrementoCostoDistanciasLargas(), 0.001f);
    }

    @Test
    public void seCreaCorrectamenteConValoresMuyPequenosPositivos() {
        ParametrosPrecio p = new ParametrosPrecio(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
        assertEquals(Float.MIN_VALUE, p.getCostoPorKm(),                    0.0f);
        assertEquals(Float.MIN_VALUE, p.getTarifaInterprovincial(),         0.0f);
        assertEquals(Float.MIN_VALUE, p.getIncrementoCostoDistanciasLargas(), 0.0f);
    }

    @Test
    public void seCreaCorrectamenteConValoresMuyGrandes() {
        ParametrosPrecio p = new ParametrosPrecio(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
        assertEquals(Float.MAX_VALUE, p.getCostoPorKm(),                    0.0f);
        assertEquals(Float.MAX_VALUE, p.getTarifaInterprovincial(),         0.0f);
        assertEquals(Float.MAX_VALUE, p.getIncrementoCostoDistanciasLargas(), 0.0f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void costoPorKmCeroLanzaExcepcion() {
        new ParametrosPrecio(0.0f, TARIFA_INTERPROV_VALIDA, INCREMENTO_VALIDO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void costoPorKmNegativoLanzaExcepcion() {
        new ParametrosPrecio(-1.0f, TARIFA_INTERPROV_VALIDA, INCREMENTO_VALIDO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tarifaInterprovincialCeroLanzaExcepcion() {
        new ParametrosPrecio(COSTO_KM_VALIDO, 0.0f, INCREMENTO_VALIDO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tarifaInterprovincialNegativaLanzaExcepcion() {
        new ParametrosPrecio(COSTO_KM_VALIDO, -5.0f, INCREMENTO_VALIDO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void incrementoCostoDistanciasLargasCeroLanzaExcepcion() {
        new ParametrosPrecio(COSTO_KM_VALIDO, TARIFA_INTERPROV_VALIDA, 0.0f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void incrementoCostoDistanciasLargasNegativoLanzaExcepcion() {
        new ParametrosPrecio(COSTO_KM_VALIDO, TARIFA_INTERPROV_VALIDA, -0.5f);
    }

    @Test
    public void cadaGetterRetornaElValorDeSuPropioParametro() {
        float costo      = 12.5f;
        float tarifa     = 75.0f;
        float incremento = 2.0f;
        ParametrosPrecio p = new ParametrosPrecio(costo, tarifa, incremento);

        assertNotEquals(p.getCostoPorKm(),                    tarifa,     0.001f);
        assertNotEquals(p.getTarifaInterprovincial(),         costo,      0.001f);
        assertNotEquals(p.getIncrementoCostoDistanciasLargas(), costo,    0.001f);
    }
}