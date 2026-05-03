package logica.agm;

import java.util.List;
import logica.modelo.Arista;

public class ResultadoAGM {

    private final List<Arista> conexiones;
    private final double costoTotal;

    public ResultadoAGM(List<Arista> conexiones) {
        this.conexiones = conexiones;
        this.costoTotal = conexiones.stream().mapToDouble(Arista::getCosto).sum();
    }

    public List<Arista> getConexiones() { return conexiones; }
    public double getCostoTotal() { return costoTotal; }
}
