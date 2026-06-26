package logica.agm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import logica.modelo.Arista;
import logica.modelo.Grafo;

public class AlgoritmoKruskal<T> {

    public Grafo<T> calcular(Grafo<T> grafo) {
        if (grafo == null)
            throw new IllegalArgumentException("El grafo no puede ser nulo");

        List<T> nodos = new ArrayList<>(grafo.getNodos());
        List<Arista<T>> aristas = new ArrayList<>(grafo.getAristas());
        
        aristas.sort(Comparator.comparingDouble(Arista::getPeso));

        Map<T, Integer> indice = new HashMap<>();
        for (int i = 0; i < nodos.size(); i++)
            indice.put(nodos.get(i), i);

        UnionFind uf = new UnionFind(nodos.size());
        List<Arista<T>> seleccionadas = new ArrayList<>();

        for (Arista<T> arista : aristas) {
            int u = indice.get(arista.getVertice1());
            int v = indice.get(arista.getVertice2());
            if (!uf.mismoGrupo(u, v)) {
                seleccionadas.add(arista);
                uf.union(u, v);
            }
        }

        return new Grafo<>(nodos, seleccionadas);
    }
}
