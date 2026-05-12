package Logica_Planificador;

import java.util.ArrayList;
import java.util.List;

import DTO.PersistenciaENJson;
import logica.agm.AlgoritmoKruskal;
import logica.agm.ResultadoAGM;
import logica.modelo.Arista;
import logica.modelo.GeneradorDeGrafo;
import logica.modelo.Grafo;
import logica.modelo.Localidad;
import logica.modelo.ParametrosPrecio;

public class PlanificadorRed {

    private static List<Localidad> localidades = new ArrayList<>();

    public PlanificadorRed() {
    }

    public static void agregarLocalidad(
            String nombre,
            String provincia,
            double latitud,
            double longitud) {

        Localidad loc = new Localidad(
                nombre,
                provincia,
                latitud,
                longitud
        );

        localidades.add(loc);
        guardarDatos();
    }

    public static void guardarDatos() {
        PersistenciaENJson.guardarLocalidades(
                localidades,
                "localidades_test.json"
        );
    }

    public void cargarDatos() {
        localidades = PersistenciaENJson.cargarLocalidades(
                "localidades_test.json"
        );
    }

    public static List<Localidad> getLocalidades() {
        return localidades;
    }
    public static List<ConexionVisual> generarConexionesVisuales() {
    	 ResultadoAGM <Localidad> resultado = calcularAGM();        
		List<ConexionVisual> conexiones =
		        new ArrayList<>();
		
		 for (Arista<Localidad> arista : resultado.getConexiones()) {		
		
		    ConexionVisual conexion =
		            new ConexionVisual(
		                    arista.getOrigen().getLatitud(),
		                    arista.getOrigen().getLongitud(),
		                    arista.getDestino().getLatitud(),
		                    arista.getDestino().getLongitud()
		 );
		
		    conexiones.add(conexion);
		}
		
		return conexiones;
    } 
    public static ResultadoAGM<Localidad> calcularAGM() {

        ParametrosPrecio parametros =new ParametrosPrecio(
                        10,   // costo por km
                        20,   // porcentaje aumento
                        500   // costo fijo interprovincial
                );

        Grafo<Localidad> grafo =
                GeneradorDeGrafo.construirGrafoCompleto(
                        localidades,
                        parametros
                );

        AlgoritmoKruskal<Localidad> kruskal =
                new AlgoritmoKruskal<>();

        return kruskal.calcular(grafo);
    }
}