package logica.modelo;

import java.util.ArrayList;
import java.util.List;

public class GeneradorDeGrafo {
    // Método principal que construye el grafo completo
    public static Grafo<Localidad> construirGrafoCompleto(List<Localidad> localidades, ParametrosPrecio param) {
       
        List<Arista<Localidad>> aristas = new ArrayList<>();

     
        for (int i = 0; i < localidades.size(); i++) {
            for (int j = i + 1; j < localidades.size(); j++) {
                Localidad origen = localidades.get(i);
                Localidad destino = localidades.get(j);

                double costo = calcularCosto(origen, destino, param);
                
                aristas.add(new Arista<>(origen, destino, costo));
            }
        }

        return new Grafo<>(localidades, aristas);
    }

    public static double calcularCosto(Localidad loc1, Localidad loc2, ParametrosPrecio param) {
        double distancia = loc1.calcularDistancia(loc2);
        
        double costoTotal = distancia * param.getCostoPorKm();
        
        if (distancia > 300) {
            costoTotal += costoTotal * (param.getPorcentajeAumento() / 100.0);
        }
        
     
        if (!loc1.getProvincia().equalsIgnoreCase(loc2.getProvincia())) {
            costoTotal += param.getCostoFijoInterprovincial();
        }
        if (costoTotal <= 0) {
            throw new IllegalArgumentException("El costo de la conexión por fibra óptica debe ser mayor a 0.");
        }
        
        return costoTotal;
    }
}