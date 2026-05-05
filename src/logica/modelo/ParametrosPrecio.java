package logica.modelo;

public class ParametrosPrecio {
	private float costoPorKm;
	private float tarifaInterprovincial;
	private float incrementoCostoDistanciasLargas;
	
	public ParametrosPrecio(float costoPorKm, float tarifaInterprovincial, float incrementoCostoDistanciasLargas) throws IllegalArgumentException {
		Validador.validarPrecio(costoPorKm);
		Validador.validarPrecio(tarifaInterprovincial);
		Validador.validarPrecio(incrementoCostoDistanciasLargas);
		
		this.costoPorKm = costoPorKm;
		this.tarifaInterprovincial = tarifaInterprovincial;
		this.incrementoCostoDistanciasLargas = incrementoCostoDistanciasLargas;
	}
	
	public float getCostoPorKm() {
		return costoPorKm;
	}
		
	public float getTarifaInterprovincial() {
		return tarifaInterprovincial;
	}
		
	public float getIncrementoCostoDistanciasLargas() {
		return incrementoCostoDistanciasLargas;
	}
	
}
