package Logica_Planificador;

import java.util.ArrayList;
import java.util.List;

import DTO.PersistenciaENJson;
import logica.agm.AlgoritmoKruskal;
import logica.agm.ResultadoAGM;
import logica.modelo.Arista;
import logica.modelo.Grafo;
import logica.modelo.Localidad;

public class PlanificadorRed {

	private static List<Localidad> localidades = new ArrayList<>();
	private static CalculadorCosto calculador;

	public PlanificadorRed (double costoKm, double costoFijoInterprovincial, double porcentajeAumento) {
		this.calculador = new CalculadorCosto(costoKm, costoFijoInterprovincial, porcentajeAumento);
	}
	
	public PlanificadorRed() {
	}

	public static void agregarLocalidad(String nombre, String provincia, double latitud, double longitud) {

		Localidad loc = new Localidad(nombre, provincia, latitud, longitud);

		localidades.add(loc);
		guardarDatos();
	}

	public static void guardarDatos() {
		PersistenciaENJson.guardarLocalidades(localidades, "localidades.json");
	}

	public void cargarDatos() {
		localidades = PersistenciaENJson.cargarLocalidades("localidades.json");
	}

	public static List<Localidad> getLocalidades() {
		return localidades;
	}

	public static List<ConexionVisual> generarConexionesVisuales(ResultadoAGM<Localidad> resultado2) {
		List<ConexionVisual> conexiones = new ArrayList<>();

		for (Arista<Localidad> arista : resultado2.getConexiones()) {

			ConexionVisual conexion = new ConexionVisual(arista.getOrigen().getLatitud(),
					arista.getOrigen().getLongitud(), arista.getDestino().getLatitud(),
					arista.getDestino().getLongitud());

			conexiones.add(conexion);
		}

		return conexiones;
	}

	public static ResultadoAGM<Localidad> calcularAGM() {

		Grafo<Localidad> grafo = construirGrafoCompletoDeLocalidades(localidades);

		AlgoritmoKruskal<Localidad> kruskal = new AlgoritmoKruskal<>();

		return kruskal.calcular(grafo);
	}
	
	public static Grafo<Localidad> construirGrafoCompletoDeLocalidades(List<Localidad> localidades) {
        
        // Preparamos la lista donde vamos a guardar todas las conexiones
        List<Arista<Localidad>> aristas = new ArrayList<>();

        //  Doble bucle para calcular todas las combinaciones
        for (int i = 0; i < localidades.size(); i++) {
            for (int j = i + 1; j < localidades.size(); j++) {
                Localidad origen = localidades.get(i);
                Localidad destino = localidades.get(j);

                // Calculamos la matemática
                double costo = calculador.calcularCostoEntreDosLocalidades(origen, destino);
                
                // Guardamos la arista en nuestra lista temporal
                aristas.add(new Arista<>(origen, destino, costo));
            }
        }

        //  Instanciamos el grafo inmutable pasándole los nodos y las aristas ya calculadas
        return new Grafo<>(localidades, aristas);
    }


	private class CalculadorCosto {
		private double costoKm;
		private double costoFijoInterprovincial;
		private double porcentajeAumento;

		public CalculadorCosto(double costoKm, double costoFijoInterprovincial, double porcentajeAumento)
				throws IllegalArgumentException {
			validarPrecio(costoKm);
			validarPrecio(costoFijoInterprovincial);
			validarPrecio(porcentajeAumento);

			this.costoKm = costoKm;
			this.costoFijoInterprovincial = costoFijoInterprovincial;
			this.porcentajeAumento = porcentajeAumento;
		}

		// Lógica de negocio (Costos)
		public double calcularCostoEntreDosLocalidades(Localidad loc1, Localidad loc2) {
			double distancia = loc1.calcularDistancia(loc2);

			double costoTotal = distancia * this.costoKm;

			if (distancia > 300) {
				costoTotal += costoTotal * (this.porcentajeAumento / 100.0);
			}

			// Asumiendo que Localidad tiene el método getProvincia()
			if (!loc1.getProvincia().equalsIgnoreCase(loc2.getProvincia())) {
				costoTotal += this.costoFijoInterprovincial;
			}

			return costoTotal;
		}

		private void validarPrecio(double precio) throws IllegalArgumentException {
			if (precio <= 0) {
				throw new IllegalArgumentException("El precio debe ser mayor a cero");
			}
		}

	}

}