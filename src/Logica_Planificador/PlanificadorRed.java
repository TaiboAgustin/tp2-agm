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
    private static ParametrosPrecio parametros;
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
                "localidades.json"
        );
    }

    public void cargarDatos() {
        localidades = PersistenciaENJson.cargarLocalidades(
                "localidades.json"
        );
    }

    public static List<Localidad> getLocalidades() {
        return localidades;
    }
    public static List<ConexionVisual> generarConexionesVisuales(ResultadoAGM<Localidad> resultado2) {       
		List<ConexionVisual> conexiones =
		        new ArrayList<>();
		
		 for (Arista<Localidad> arista : resultado2.getConexiones()) {		
		
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
    	
        Grafo<Localidad> grafo =
                GeneradorDeGrafo.construirGrafoCompleto(
                        localidades,
                        parametros
                );

        AlgoritmoKruskal<Localidad> kruskal =
                new AlgoritmoKruskal<>();

        return kruskal.calcular(grafo);
    }
    
    public static void configurarParametros(double costoKm,double tarifaInterprovincial,double porcentajeAumento) {

        parametros = new ParametrosPrecio(costoKm , tarifaInterprovincial,porcentajeAumento );
    }
  
    
    public static ParametrosPrecio getParametros() {

        return parametros;
    }
    
    public static void reemplazarLocalidades(
            List<Localidad> nuevasLocalidades) {

        localidades.clear();
        localidades.addAll(nuevasLocalidades);
    }
    
    public static void resetear() {
        localidades.clear();
        parametros = null;
    }

	public static boolean EmpezarPlanificacion(List<Localidad> listLocalidades) {
		  if (parametros == null) return false;

          if (listLocalidades.isEmpty()) {
              return false;
          }

          PlanificadorRed.getLocalidades().clear();
          for (Localidad loc : listLocalidades) {
              PlanificadorRed.agregarLocalidad(
                      loc.getNombre(), loc.getProvincia(),
                      loc.getLatitud(), loc.getLongitud()); 
          }
		return true;
	}
}