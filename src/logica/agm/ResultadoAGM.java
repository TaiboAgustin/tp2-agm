package logica.agm;

import java.util.Collections;
import java.util.List;
import logica.modelo.Arista;

public class ResultadoAGM<T> {

    private final List<Arista<T>> conexiones;
    private final double costoTotal;

    public ResultadoAGM(List<Arista<T>> conexiones) {
        if (conexiones == null)
            throw new IllegalArgumentException("Las conexiones no pueden ser nulas");
        this.conexiones = conexiones;
        this.costoTotal = conexiones.stream().mapToDouble(Arista::getCosto).sum();
    }

    public List<Arista<T>> getConexiones() { return Collections.unmodifiableList(conexiones); }
    public double getCostoTotal() { return costoTotal; }
}
