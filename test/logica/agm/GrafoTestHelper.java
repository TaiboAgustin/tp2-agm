package logica.agm;

import logica.modelo.Arista;
import logica.modelo.Grafo;
import logica.modelo.Localidad;

public class GrafoTestHelper {
    public static double calcularCostoTotal(Grafo<Localidad> grafo) {
        return grafo.getAristas().stream().mapToDouble(Arista::getPeso).sum();
    }
}