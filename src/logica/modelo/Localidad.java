package logica.modelo;

import java.util.Objects;

public class Localidad {

    private final String nombre;
    private final String provincia;
    private final double latitud;
    private final double longitud;

    public Localidad(String nombre, String provincia, double latitud, double longitud) throws IllegalArgumentException {
        Validador.validarString(nombre);
        Validador.validarString(provincia);
        Validador.validarCoordenadas(latitud, longitud);
        
        this.nombre = nombre;
        this.provincia = provincia;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getNombre() { return nombre; }
    public String getProvincia() { return provincia; }
    public double getLatitud() { return latitud; }
    public double getLongitud() { return longitud; }
    

    public double calcularDistancia(Localidad localidadExterna) {
    	double latLocalidadExterna = localidadExterna.getLatitud();
    	double lonLocalidadExterna = localidadExterna.getLongitud();
    	double radioTierra = 6371.0;
    	
    	double difLatitud = Math.toRadians(latLocalidadExterna - this.latitud);
    	double difLongitud = Math.toRadians(lonLocalidadExterna - this.longitud);
    	
        double a = Math.sin(difLatitud / 2) * Math.sin(difLatitud / 2)
                + Math.cos(Math.toRadians(this.latitud)) * Math.cos(Math.toRadians(latLocalidadExterna))
                * Math.sin(difLongitud / 2) * Math.sin(difLongitud / 2);

       double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

       return radioTierra * c;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Localidad)) return false;
        Localidad otra = (Localidad) obj;
        return Double.compare(latitud, otra.latitud) == 0
            && Double.compare(longitud, otra.longitud) == 0
            && Objects.equals(nombre, otra.nombre)
            && Objects.equals(provincia, otra.provincia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, provincia, latitud, longitud);
    }
}
