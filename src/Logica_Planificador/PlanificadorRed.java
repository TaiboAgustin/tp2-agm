package Logica_Planificador;

import java.util.ArrayList;
import java.util.List;

import DTO.PersistenciaENJson;
import logica.modelo.Localidad;

public class PlanificadorRed {

    private static List<Localidad> localidades;

    public PlanificadorRed() {
        localidades = new ArrayList<>();
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
}