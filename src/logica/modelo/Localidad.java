package logica.modelo;

import java.util.Objects;

public class Localidad {

    private final String nombre;
    private final String provincia;
    private final double latitud;
    private final double longitud;

    public Localidad(String nombre, String provincia, double latitud, double longitud) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        if (provincia == null || provincia.trim().isEmpty())
            throw new IllegalArgumentException("La provincia no puede ser nula o vacía");
        if (latitud < -90 || latitud > 90)
            throw new IllegalArgumentException("La latitud debe estar entre -90 y 90");
        if (longitud < -180 || longitud > 180)
            throw new IllegalArgumentException("La longitud debe estar entre -180 y 180");

        this.nombre = nombre;
        this.provincia = provincia;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getNombre() { return nombre; }
    public String getProvincia() { return provincia; }
    public double getLatitud() { return latitud; }
    public double getLongitud() { return longitud; }

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
