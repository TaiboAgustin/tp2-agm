package logica.modelo;

import java.util.Objects;

public class Arista<T> {

    private final T vertice1;
    private final T vertice2;
    private final double peso;

    public Arista(T vertice1, T vertice2, double peso) {
        if (vertice1 == null)
            throw new IllegalArgumentException("El vértice 1 no puede ser nulo");
        if (vertice2 == null)
            throw new IllegalArgumentException("El vértice 2 no puede ser nulo");
        if (vertice1.equals(vertice2))
            throw new IllegalArgumentException("Los vértices no pueden ser el mismo nodo");

        this.vertice1 = vertice1;
        this.vertice2 = vertice2;
        this.peso = peso;
    }

    public T getVertice1() { return vertice1; }
    public T getVertice2() { return vertice2; }
    public double getPeso() { return peso; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Arista)) return false;
        Arista<?> otra = (Arista<?>) obj;
        if (Double.compare(peso, otra.peso) != 0) return false;
        return (Objects.equals(vertice1, otra.vertice1) && Objects.equals(vertice2, otra.vertice2))
            || (Objects.equals(vertice1, otra.vertice2) && Objects.equals(vertice2, otra.vertice1));
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(vertice1) + Objects.hashCode(vertice2) + Double.hashCode(peso);
    }
}