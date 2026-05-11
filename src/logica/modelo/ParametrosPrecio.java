package logica.modelo;

public class ParametrosPrecio {
	private double costoKm;
	private double costoFijoInterprovincial;
	private double porcentajeAumento;
	
	public ParametrosPrecio(double costoKm, double costoFijoInterprovincial, double porcentajeAumento) throws IllegalArgumentException {
		Validador.validarPrecio(costoKm);
		Validador.validarPrecio(costoFijoInterprovincial);
		Validador.validarPrecio(porcentajeAumento);
		
		this.costoKm = costoKm;
		this.costoFijoInterprovincial = costoFijoInterprovincial;
		this.porcentajeAumento = porcentajeAumento;
	}
	
	public double getCostoPorKm() {
		return costoKm;
	}
		
	public double getCostoFijoInterprovincial() {
		return costoFijoInterprovincial;
	}
		
	public double getPorcentajeAumento() {
		return porcentajeAumento;
	}
	
}
