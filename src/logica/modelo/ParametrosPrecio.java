package logica.modelo;

public class ParametrosPrecio {
	private double costoKm;
	private double costoFijoInterprovincial;
	private double porcentajeAumento;
	
	public ParametrosPrecio(double costoPorKm, double tarifaInterprovincial, double incrementoCostoDistanciasLargas) throws IllegalArgumentException {
		Validador.validarPrecio(costoPorKm);
		Validador.validarPrecio(tarifaInterprovincial);
		Validador.validarPrecio(incrementoCostoDistanciasLargas);
		
		this.costoKm = costoPorKm;
		this.costoFijoInterprovincial = tarifaInterprovincial;
		this.porcentajeAumento = incrementoCostoDistanciasLargas;
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
