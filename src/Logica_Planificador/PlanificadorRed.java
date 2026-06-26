package Logica_Planificador;

import java.util.ArrayList;
import java.util.List;

import DTO.PersistenciaENJson;
import logica.agm.AlgoritmoKruskal;
import logica.modelo.Arista;
import logica.modelo.Grafo;
import logica.modelo.Localidad;

public class PlanificadorRed {

    private List<Localidad> localidades = new ArrayList<>();
    private CalculadorCosto calculador;

    public PlanificadorRed() {
    }

    public boolean agregarLocalidad(String nombre, String provincia, double latitud, double longitud) {
        if (existeLocalidad(nombre, provincia)) {
            return false;
        }
        localidades.add(new Localidad(nombre, provincia, latitud, longitud));
        guardarDatos();
        return true;
    }

    private boolean existeLocalidad(String nombre, String provincia) {
        return localidades.stream().anyMatch(l ->
            l.getNombre().equalsIgnoreCase(nombre) && l.getProvincia().equalsIgnoreCase(provincia));
    }

    public void guardarDatos() {
        PersistenciaENJson.guardarLocalidades(localidades, "localidades.json");
    }

    public void cargarDatos() {
        localidades = PersistenciaENJson.cargarLocalidades("localidades.json");
    }

    public List<Localidad> getLocalidades() {
        return new ArrayList<>(localidades);
    }

    public List<ConexionVisual> generarConexionesVisuales(Grafo<Localidad> resultado) {
        List<ConexionVisual> conexiones = new ArrayList<>();
        for (Arista<Localidad> arista : resultado.getAristas()) {
            conexiones.add(new ConexionVisual(
                arista.getVertice1().getLatitud(),
                arista.getVertice1().getLongitud(),
                arista.getVertice2().getLatitud(),
                arista.getVertice2().getLongitud()
            ));
        }
        return conexiones;
    }

    public Grafo<Localidad> calcularAGM() {
        Grafo<Localidad> grafo = construirGrafoCompletoDeLocalidades(localidades);
        AlgoritmoKruskal<Localidad> kruskal = new AlgoritmoKruskal<>();
        return kruskal.calcular(grafo);
    }

    public void reemplazarLocalidades(List<Localidad> nuevasLocalidades) {
        localidades.clear();
        localidades.addAll(nuevasLocalidades);
        guardarDatos();
    }

    public void resetear() {
        localidades.clear();
    }

    public boolean empezarPlanificacion(double costoKm, double costoFijoInterprovincial, double porcentajeAumento) {
        calculador = new CalculadorCosto(costoKm, costoFijoInterprovincial, porcentajeAumento);
        return !localidades.isEmpty();
    }

    public void limpiarLocalidades() {
        localidades.clear();
        guardarDatos();
    }

    public Grafo<Localidad> construirGrafoCompletoDeLocalidades(List<Localidad> localidades) {
        if (calculador == null)
            throw new IllegalStateException("Debe llamarse empezarPlanificacion antes de construir el grafo");

        List<Arista<Localidad>> aristas = new ArrayList<>();
        for (int i = 0; i < localidades.size(); i++) {
            for (int j = i + 1; j < localidades.size(); j++) {
                Localidad loc1 = localidades.get(i);
                Localidad loc2 = localidades.get(j);
                double peso = calculador.calcularCostoEntreDosLocalidades(loc1, loc2);
                aristas.add(new Arista<>(loc1, loc2, peso));
            }
        }
        return new Grafo<>(localidades, aristas);
    }

    private static class CalculadorCosto {
        private final double costoKm;
        private final double costoFijoInterprovincial;
        private final double porcentajeAumento;

        public CalculadorCosto(double costoKm, double costoFijoInterprovincial, double porcentajeAumento) {
            validarPrecio(costoKm);
            validarPrecio(costoFijoInterprovincial);
            validarPrecio(porcentajeAumento);
            this.costoKm = costoKm;
            this.costoFijoInterprovincial = costoFijoInterprovincial;
            this.porcentajeAumento = porcentajeAumento;
        }

        public double calcularCostoEntreDosLocalidades(Localidad loc1, Localidad loc2) {
            double distancia = loc1.calcularDistancia(loc2);
            double costoTotal = distancia * this.costoKm;
            if (distancia > 300) {
                costoTotal += costoTotal * (this.porcentajeAumento / 100.0);
            }
            if (!loc1.getProvincia().equalsIgnoreCase(loc2.getProvincia())) {
                costoTotal += this.costoFijoInterprovincial;
            }
            return costoTotal;
        }

        private void validarPrecio(double precio) {
            if (precio <= 0)
                throw new IllegalArgumentException("El precio debe ser mayor a cero");
        }
    }
}
