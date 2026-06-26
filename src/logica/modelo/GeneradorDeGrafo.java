package logica.modelo;

import java.util.ArrayList;
import java.util.List;

public class GeneradorDeGrafo {

    public static Grafo<Localidad> construirGrafoCompleto(List<Localidad> localidades, ParametrosPrecio param) {
        List<Arista<Localidad>> aristas = new ArrayList<>();
        for (int i = 0; i < localidades.size(); i++) {
            for (int j = i + 1; j < localidades.size(); j++) {
                Localidad loc1 = localidades.get(i);
                Localidad loc2 = localidades.get(j);
                aristas.add(new Arista<>(loc1, loc2, calcularCosto(loc1, loc2, param)));
            }
        }
        return new Grafo<>(localidades, aristas);
    }

    public static double calcularCosto(Localidad loc1, Localidad loc2, ParametrosPrecio param) {
        double distancia = loc1.calcularDistancia(loc2);
        double costoTotal = distancia * param.getCostoPorKm();
        if (distancia > 300)
            costoTotal += costoTotal * (param.getPorcentajeAumento() / 100.0);
        if (!loc1.getProvincia().equalsIgnoreCase(loc2.getProvincia()))
            costoTotal += param.getCostoFijoInterprovincial();
        return costoTotal;
    }
}
