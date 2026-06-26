package logica.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grafo<T> {

    private final List<T> nodos;
    private final List<Arista<T>> aristas;

    public Grafo(List<T> nodos, List<Arista<T>> aristas) {
        if (nodos == null || nodos.size() < 2)
            throw new IllegalArgumentException("El grafo debe tener al menos 2 nodos");
        if (aristas == null)
            throw new IllegalArgumentException("Las aristas no pueden ser nulas");

        this.nodos = new ArrayList<>(nodos);
        this.aristas = new ArrayList<>(aristas);
    }

    public void agregarArista(Arista<T> arista) {
        if (arista == null)
            throw new IllegalArgumentException("La arista no puede ser nula");
        if (!nodos.contains(arista.getVertice1()) || !nodos.contains(arista.getVertice2()))
            throw new IllegalArgumentException("Los vértices de la arista deben pertenecer al grafo");
        aristas.add(arista);
    }

    public boolean eliminarArista(Arista<T> arista) {
        return aristas.remove(arista);
    }

    public List<T> getVecinos(T nodo) {
        List<T> vecinos = new ArrayList<>();
        for (Arista<T> a : aristas) {
            if (a.getVertice1().equals(nodo)) vecinos.add(a.getVertice2());
            else if (a.getVertice2().equals(nodo)) vecinos.add(a.getVertice1());
        }
        return vecinos;
    }

    public List<T> getNodos() { return Collections.unmodifiableList(nodos); }
    public List<Arista<T>> getAristas() { return Collections.unmodifiableList(aristas); }

    public double getPesoTotal() {
        return aristas.stream().mapToDouble(Arista::getPeso).sum();
    }
}
