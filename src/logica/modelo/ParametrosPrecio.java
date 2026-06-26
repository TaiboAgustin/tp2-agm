package logica.modelo;

public class ParametrosPrecio {

    private final double costoKm;
    private final double costoFijoInterprovincial;
    private final double porcentajeAumento;

    public ParametrosPrecio(double costoKm, double costoFijoInterprovincial, double porcentajeAumento) {
        validarPrecio(costoKm);
        validarPrecio(costoFijoInterprovincial);
        validarPrecio(porcentajeAumento);
        this.costoKm = costoKm;
        this.costoFijoInterprovincial = costoFijoInterprovincial;
        this.porcentajeAumento = porcentajeAumento;
    }

    public double getCostoPorKm() { return costoKm; }
    public double getCostoFijoInterprovincial() { return costoFijoInterprovincial; }
    public double getPorcentajeAumento() { return porcentajeAumento; }

    private void validarPrecio(double precio) {
        if (precio <= 0)
            throw new IllegalArgumentException("El precio debe ser mayor a cero");
    }
}
