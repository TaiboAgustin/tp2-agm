package logica.modelo;

import java.util.ArrayList;
import java.util.List;

public class GeneradorDeGrafo {
	private static final double RADIO_TIERRA_KM = 6371.0;
    // Método principal que construye el grafo completo
    public static Grafo<Localidad> construirGrafoCompleto(List<Localidad> localidades, ParametrosPrecio param) {
        
        // Preparamos la lista donde vamos a guardar todas las conexiones
        List<Arista<Localidad>> aristas = new ArrayList<>();

        //  Doble bucle para calcular todas las combinaciones
        for (int i = 0; i < localidades.size(); i++) {
            for (int j = i + 1; j < localidades.size(); j++) {
                Localidad origen = localidades.get(i);
                Localidad destino = localidades.get(j);

                // Calculamos la matemática
                double costo = calcularCosto(origen, destino, param);
                
                // Guardamos la arista en nuestra lista temporal
                aristas.add(new Arista<>(origen, destino, costo));
            }
        }

        //  Instanciamos el grafo inmutable pasándole los nodos y las aristas ya calculadas
        return new Grafo<>(localidades, aristas);
    }

    // Lógica de negocio (Costos)
    public static double calcularCosto(Localidad loc1, Localidad loc2, ParametrosPrecio param) {
        double distancia = calcularDistancia(loc1, loc2);
        
        double costoTotal = distancia * param.getCostoPorKm();
        
        if (distancia > 300) {
            costoTotal += costoTotal * (param.getPorcentajeAumento() / 100.0);
        }
        
        // Asumiendo que Localidad tiene el método getProvincia()
        if (!loc1.getProvincia().equalsIgnoreCase(loc2.getProvincia())) {
            costoTotal += param.getCostoFijoInterprovincial();
        }
        
        return costoTotal;
    }

    // Matemática pura (Haversine)
    public static double calcularDistancia(Localidad loc1, Localidad loc2) {
        // Asumiendo que Localidad tiene getLatitud() y getLongitud()
        double lat1 = Math.toRadians(loc1.getLatitud());
        double lon1 = Math.toRadians(loc1.getLongitud());
        double lat2 = Math.toRadians(loc2.getLatitud());
        double lon2 = Math.toRadians(loc2.getLongitud());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                   Math.cos(lat1) * Math.cos(lat2) *
                   Math.pow(Math.sin(dLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIO_TIERRA_KM * c;
    }
}