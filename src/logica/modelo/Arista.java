package logica.modelo;

public class Arista {

    private final Localidad origen;
    private final Localidad destino;
    private final double costo;

    public Arista(Localidad origen, Localidad destino, double costo) {
        if (origen == null)
            throw new IllegalArgumentException("El origen no puede ser nulo");
        if (destino == null)
            throw new IllegalArgumentException("El destino no puede ser nulo");
        if (origen == destino)
            throw new IllegalArgumentException("El origen y el destino no pueden ser la misma localidad");
        if (costo <= 0)
            throw new IllegalArgumentException("El costo debe ser mayor a 0");

        this.origen = origen;
        this.destino = destino;
        this.costo = costo;
    }

    public Localidad getOrigen() { return origen; }
    public Localidad getDestino() { return destino; }
    public double getCosto() { return costo; }
}
