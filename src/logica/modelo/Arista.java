package logica.modelo;

public class Arista<T> {

    private final T origen;
    private final T destino;
    private final double costo;

    public Arista(T origen, T destino, double costo) {
        if (origen == null)
            throw new IllegalArgumentException("El origen no puede ser nulo");
        if (destino == null)
            throw new IllegalArgumentException("El destino no puede ser nulo");
        if (origen.equals(destino))
            throw new IllegalArgumentException("El origen y el destino no pueden ser el mismo nodo");
        if (costo <= 0)
            throw new IllegalArgumentException("El costo debe ser mayor a 0");

        this.origen = origen;
        this.destino = destino;
        this.costo = costo;
    }

    public T getOrigen() { return origen; }
    public T getDestino() { return destino; }
    public double getCosto() { return costo; }
}
