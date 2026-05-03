package logica.modelo;

import java.util.List;

public class Grafo {

    private final List<Localidad> localidades;
    private final List<Arista> aristas;

    public Grafo(List<Localidad> localidades, List<Arista> aristas) {
        if (localidades == null || localidades.size() < 2)
            throw new IllegalArgumentException("El grafo debe tener al menos 2 localidades");

        int n = localidades.size();
        int aristasEsperadas = n * (n - 1) / 2;
        if (aristas == null || aristas.size() != aristasEsperadas)
            throw new IllegalArgumentException("El grafo debe ser completo: se esperaban " + aristasEsperadas + " aristas");

        this.localidades = localidades;
        this.aristas = aristas;
    }

    public List<Localidad> getLocalidades() { return localidades; }
    public List<Arista> getAristas() { return aristas; }
}
