package logica.modelo;

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
        
        // Se elimina la validación (peso <= 0) para mantener la clase genérica y reutilizable.

        this.vertice1 = vertice1;
        this.vertice2 = vertice2;
        this.peso = peso;
    }

    public T getVertice1() { 
    	return vertice1; 
    	}
    public T getVertice2() { 
    	return vertice2; 
    	}
    public double getPeso() { 
    	return peso; 
    	}
}