package logica.modelo;

import java.util.Collections;
import java.util.List;

public class Grafo<T> {

    private final List<T> nodos;
    private final List<Arista<T>> aristas;

    public Grafo(List<T> nodos, List<Arista<T>> aristas) {
        if (nodos == null || nodos.size() < 2)
            throw new IllegalArgumentException("El grafo debe tener al menos 2 nodos");

        int n = nodos.size();
        int aristasEsperadas = n * (n - 1) / 2;
       /* if (aristas == null || aristas.size() != aristasEsperadas)
            throw new IllegalArgumentException("El grafo debe ser completo: se esperaban " + aristasEsperadas + " aristas");
*/
        this.nodos = nodos;
        this.aristas = aristas;
    }

    public List<T> getNodos() { return Collections.unmodifiableList(nodos); }
    public List<Arista<T>> getAristas() { return Collections.unmodifiableList(aristas); }
}
