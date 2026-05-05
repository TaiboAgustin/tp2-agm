package logica.modelo;

import org.junit.Test;
import static org.junit.Assert.*;

class ParametrosPrecioTest {

    @Test
    void seCreanValoresCorrectamente() {
        ParametrosPrecio parametros = new ParametrosPrecio(10.5f, 200.0f, 15.0f);

        assertEquals(10.5f,  parametros.getCostoPorKm(), 000001);
        assertEquals(200.0f, parametros.getTarifaInterprovincial(), 000001);
        assertEquals(15.0f,  parametros.getIncrementoCostoDistanciasLargas(), 000001);
    }
    
    @Test
    void constructorNoFallaConDecimales() {
        new ParametrosPrecio(0.01f, 0.01f, 0.01f);
    }

    @Test(expected = IllegalArgumentException.class)
    void preciosEnCeroFalla() {
        new ParametrosPrecio(0f, 0f, 0f);
    }

    @Test(expected = IllegalArgumentException.class)
    void constructoPrecioPorKmNegativoFalla() {
    	new ParametrosPrecio(-1f, 200.0f, 15.0f);
    }

    @Test(expected = IllegalArgumentException.class)
    void constructorTarifaInterprovincialNegativaFalla() {
    	new ParametrosPrecio(10.5f, -1f, 15.0f);
    }

    @Test(expected = IllegalArgumentException.class)
    void constructorIncrementoLargaDistanciaFalla() {
    	new ParametrosPrecio(10.5f, 200.0f, -1f);
    }
}
