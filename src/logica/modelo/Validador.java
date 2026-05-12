package logica.modelo;

public class Validador {
	private static final double LONGITUD_MAXIMA =  180d;
	private static final double LONGITUD_MINIMA = -180d;
	private static final double LATITUD_MAXIMA = 90d;
	private static final double LATITUD_MINIMA = -90d;
	
	public static void validarString(String textoAValidar) throws IllegalArgumentException {
        if (textoAValidar == null || textoAValidar.trim().isEmpty()) {
        	throw new IllegalArgumentException("El texto no puede ser nulo o vacío");
        }		
	}
	
	public static void validarCoordenadas (double latitud, double longitud) throws IllegalArgumentException {
        if (latitud < LATITUD_MINIMA || latitud > LATITUD_MAXIMA) {
            throw new IllegalArgumentException("La latitud debe estar entre -90 y 90");
        }
        if (longitud > LONGITUD_MAXIMA || longitud < LONGITUD_MINIMA) {
            throw new IllegalArgumentException("La longitud debe estar entre -180 y 180");		
        }
	}
	
	public static void validarPrecio (double precio) throws IllegalArgumentException {
		if (precio <= 0) {
			throw new IllegalArgumentException("El precio debe ser mayor a cero");
		}
	}
	
}
