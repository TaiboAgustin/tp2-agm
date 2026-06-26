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
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Arista<?> arista = (Arista<?>) obj;
        

        if (Double.compare(arista.peso, peso) != 0) return false;
        return (vertice1.equals(arista.vertice1) && vertice2.equals(arista.vertice2)) ||
               (vertice1.equals(arista.vertice2) && vertice2.equals(arista.vertice1));
    }

    @Override
    public int hashCode() {
        return Objects.hash(peso, vertice1.hashCode() + vertice2.hashCode());
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